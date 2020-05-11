package com.jake.main.main.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jake.main.main.Items.Item;
import com.jake.main.main.Items.ItemDef;
import com.jake.main.main.Items.Powerup;
import com.jake.main.main.MyController;
import com.jake.main.main.MyGame;
import com.jake.main.main.Sprites.Miner;
import com.jake.main.main.Tools.B2DWorldCreator;
import com.jake.main.main.Tools.WorldContactListener;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen  implements Screen {
    private MyGame game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Miner player;

    //Box2d variable
    private World world;
    private Box2DDebugRenderer b2dr;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;

    MyController controller;


    public PlayScreen(MyGame game) {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        atlas = new TextureAtlas("minerCharacter.pack");

        this.game = game;
        // create cam to follow character through map
        gamecam = new OrthographicCamera();
        // create a FitViewport to maintain virtual aspect ration despite device screen size
        gamePort = new FitViewport(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM, gamecam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("seventy.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        player = new Miner(world, this);

        world.setContactListener(new WorldContactListener());

        new B2DWorldCreator(this);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        cam = new OrthographicCamera();
        viewport = new FitViewport(400, 240, cam);
        stage = new Stage(viewport, MyGame.batch);
        Gdx.input.setInputProcessor(stage);

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();





        Table table = new Table();
        table.left().bottom();

        final ImageButton upImg = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("up.png"))));
        upImg.setSize(25,25);
        stage.addActor(upImg);
        upImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.debug("Button", "up");
                if(Powerup.gold)
                {
                    player.b2body.setLinearVelocity(new Vector2(0,2f));
                }
                else if (Powerup.coal)
                {
                    player.b2body.setLinearVelocity(new Vector2(0,0.5f));
                }
                else
                {
                    player.b2body.setLinearVelocity(new Vector2(0,1f));
                }


                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.b2body.setLinearVelocity(new Vector2(0,0));
            }
        });



        ImageButton rightImg = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("right.png"))));
        rightImg.setSize(25,25);
        stage.addActor(rightImg);
        rightImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.debug("Button", "right");
                if(Powerup.gold)
                {
                    player.b2body.setLinearVelocity(new Vector2(2f, 0));
                }
                else if (Powerup.coal)
                {
                    player.b2body.setLinearVelocity(new Vector2(0.5f,0));
                }
                else
                {
                    player.b2body.setLinearVelocity(new Vector2(1f, 0));
                }

                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                player.b2body.setLinearVelocity(new Vector2(0,0));
            }


        });

        ImageButton downImg = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("down.png"))));
        downImg.setSize(25,25);
        stage.addActor(downImg);
        downImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.debug("Button", "down");
                if(Powerup.gold)
                {
                    player.b2body.setLinearVelocity(new Vector2(0, -2));
                }
                else if (Powerup.coal)
                {
                    player.b2body.setLinearVelocity(new Vector2(0,-0.5f));
                }
                else
                {
                    player.b2body.setLinearVelocity(new Vector2(0,-1f));
                }

                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                player.b2body.setLinearVelocity(new Vector2(0,0));
            }
        });

        ImageButton leftImg = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("left.png"))));
        leftImg.setSize(25,25);
        stage.addActor(leftImg);
        leftImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.debug("Button", "left");
                if(Powerup.gold)
                {
                    player.b2body.setLinearVelocity(new Vector2(-2f, 0));
                }
                else if (Powerup.coal)
                {
                    player.b2body.setLinearVelocity(new Vector2(-0.5f,0));
                }
                else
                {
                    player.b2body.setLinearVelocity(new Vector2(-1f, 0));
                }

                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                player.b2body.setLinearVelocity(new Vector2(0,0));
            }
        });

       /* ImageButton aImg = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("A-Button.png"))));
        aImg.setSize(25,25);
        stage.addActor(aImg);
        leftImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.debug("Button", "A");

                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);


            }
        });

        ImageButton bImg = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("B-Button.png"))));
        bImg.setSize(25,25);
        stage.addActor(bImg);
        leftImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.debug("Button", "A");

                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);


            }
        });

        */



        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        //table.pad(0,50,0,0);
        //table.add();
        //table.add(bImg).size(bImg.getWidth(), bImg.getHeight());
        //table.add();
        table.row().pad(2,2,2,2);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.add();
        //table.add(aImg).size(aImg.getWidth(), aImg.getHeight());
        //table.add();
        //table.pad(0,45,0,0);
        //table.add();
        table.row().padBottom(2);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();
        table.pack();

        stage.addActor(table);

        /*
        Table ab = new Table();
        ab.right().bottom();

        ab.add();
        ab.add(bImg).size(bImg.getWidth(), bImg.getHeight());
        ab.add();
        ab.row().pad(4,4,2,4);
        ab.add(aImg).size(aImg.getWidth(), aImg.getHeight());
        ab.add();
        ab.pack();
        stage.addActor(table);

         */


    }


    public void spawnItem(ItemDef idef)
    {
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems()
    {
        if(!itemsToSpawn.isEmpty())
        {
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Powerup.class)
            {
                items.add(new Powerup(this, idef.position.x, idef.position.y));
            }
        }
    }






    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    @Override
    public void show()
    {

    }


    public void handleInput(float dt) {


    }

    public void update(float dt)
    {
        player.update(dt);
        handleInput(dt);
        handleSpawningItems();
        renderer.setView(gamecam);

        world.step(1/60f, 6,2);

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();

        for(Item item : items)
        {
            item.update(dt);
        }

    }

    @Override
    public void render(float delta)
    {
        update(delta);
        update(Gdx.graphics.getDeltaTime());

        if(Gdx.input.isTouched())
        {
            handleInput(delta);
        }

        // Clear the game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render our game map
        renderer.render();
        game.batch.setProjectionMatrix(gamecam.combined);
        b2dr.render(world, gamecam.combined);
        game.batch.begin();
        for (Item item : items)
        {
            item.draw(game.batch);
        }

        player.draw(game.batch);
        game.batch.end();

        stage.draw();
        stage.act();

    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width,height);
        viewport.update(width, height);
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();


    }



}

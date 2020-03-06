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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jake.main.main.MyController;
import com.jake.main.main.MyGame;
import com.jake.main.main.Sprites.Miner;
import com.jake.main.main.Tools.B2DWorldCreator;
import com.sun.org.apache.xpath.internal.operations.Or;

import sun.rmi.runtime.Log;


public class PlayScreen  implements Screen {
    private MyGame game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Miner player;

    //Box2d variable
    private World world;
    private Box2DDebugRenderer b2dr;

    public static MyController controller;


    public PlayScreen(MyGame game)
    {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        // create cam to follow character through map
        gamecam = new OrthographicCamera();
        // create a FitViewport to maintain virtual aspect ration despite device screen size
        gamePort = new FitViewport(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM,gamecam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map3.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()/2 , 0);

        world = new World(new Vector2(0,-10 ), true);
        b2dr = new Box2DDebugRenderer();

        player = new Miner(world, this);

        new B2DWorldCreator(world, map);

       controller = new MyController();
    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt)
    {
        if(controller.isUpPressed())
        {
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
        }
        if(controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
        {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(controller.isLeftPressed() && player.b2body.getLinearVelocity().x <= -2)
        {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(controller.isDownPressed())
        {
            player.b2body.applyLinearImpulse(new Vector2(0,-4f), player.b2body.getWorldCenter(), true);
        }


    }

    public void update(float dt)
    {
        handleInput(dt);
        player.update(dt);
        gamecam.update();
        renderer.setView(gamecam);

        world.step(1/60f, 6,2);

        gamecam.position.x = player.b2body.getPosition().x;
    }

    @Override
    public void render(float delta)
        {
            update(delta);

            // Clear the game screen with black
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // render our game map
            renderer.render();

            b2dr.render(world, gamecam.combined);

            if (Gdx.app.getType() == Application.ApplicationType.Android)
            {
                controller.draw();
            }

            game.batch.setProjectionMatrix(gamecam.combined);
            game.batch.begin();
            player.draw(game.batch);
            game.batch.end();
        }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width,height);
       controller.resize(width,height);
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

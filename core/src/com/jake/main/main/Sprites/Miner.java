package com.jake.main.main.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;

public class Miner extends Sprite {
    public enum State {RIGHT, LEFT, UP, DOWN, STANDING};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion minerStand;

    private Animation <TextureRegion> minerRunRight;
    private Animation <TextureRegion> minerRunLeft;
    private Animation <TextureRegion> minerRunUp;
    private Animation <TextureRegion> minerRunDown;

    private float stateTimer;
    private boolean runningRight;
    private boolean runningLeft;
    private boolean runningUp;
    private boolean runninDown;



    public Miner(World world, PlayScreen screen)
    {
        //super(screen.getAtlas().findRegion("Miner 2.0 walk right"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> rightFrames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
        {
            rightFrames.add(new TextureRegion(screen.getAtlas().findRegion("Miner 2.0 walk right"), i * 44, 0, 44, 68));
        }
        minerRunRight = new Animation(0.05f, rightFrames);

        Array<TextureRegion> leftFrames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
        {
            leftFrames.add(new TextureRegion(screen.getAtlas().findRegion("Miner 2.0 walk left"), i * 44, 0, 44, 68));
        }
        minerRunLeft = new Animation(0.05f, leftFrames);

        Array<TextureRegion> upFrames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
        {
            upFrames.add(new TextureRegion(screen.getAtlas().findRegion("Miner 2.0 walk up"), i * 44, 0, 44, 68));
        }
        minerRunUp = new Animation(0.05f, upFrames);

        Array<TextureRegion> downFrames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++)
        {
            downFrames.add(new TextureRegion(screen.getAtlas().findRegion("Miner 2.0 walk down"), i * 44, 0, 44, 68));
        }
        minerRunDown = new Animation(0.05f, downFrames);


        defineMiner();
        minerStand = new TextureRegion(screen.getAtlas().findRegion("Miner 2.0 walk right"), 0, 0, 44, 68);
        setBounds(0, 0, 16 / MyGame.PPM, 16/ MyGame.PPM);
        setRegion(minerStand);
    }

    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;
        switch(currentState)
        {
            case UP:
                region = minerRunUp.getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = minerRunDown.getKeyFrame(stateTimer, true);
                break;
            case LEFT:
                region = minerRunLeft.getKeyFrame(stateTimer, true);
                break;
            case RIGHT:
                region = minerRunRight.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = minerStand;
                break;

        }

        /*if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true,false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }

         */





        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState()
    {
        if (b2body.getLinearVelocity().y > 0)
        {
            return State.UP;
        }
        else if (b2body.getLinearVelocity().y < 0)
        {
            return State.DOWN;
        }
        else if (b2body.getLinearVelocity().x < 0)
        {
            return State.LEFT;
        }
        else if (b2body.getLinearVelocity().x > 0)
        {
            return State.RIGHT;
        }
        else
            return State.STANDING;
    }

    public void defineMiner()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGame.PPM,32 / MyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / MyGame.PPM);
        fdef.filter.categoryBits = MyGame.MINER_BIT;
        fdef.filter.maskBits = MyGame.DEFAULT_BIT | MyGame.BOULDER_BIT | MyGame.WALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-5 / MyGame.PPM, 8 / MyGame.PPM), new Vector2(5 / MyGame.PPM, 8 / MyGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

        EdgeShape right = new EdgeShape();
        right.set(new Vector2(8 / MyGame.PPM, -6 / MyGame.PPM), new Vector2(8 / MyGame.PPM, 6 / MyGame.PPM));
        fdef.shape = right;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("right");

        EdgeShape left = new EdgeShape();
        left.set(new Vector2(-8 / MyGame.PPM, -6 / MyGame.PPM), new Vector2(-8 / MyGame.PPM, 6 / MyGame.PPM));
        fdef.shape = left;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("left");

        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2(-5 / MyGame.PPM, -8 / MyGame.PPM), new Vector2(5 / MyGame.PPM, -8 / MyGame.PPM));
        fdef.shape = foot;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("foot");
    }

}

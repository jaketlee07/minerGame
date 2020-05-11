package com.jake.main.main.Items;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;
import com.jake.main.main.Sprites.Miner;

import java.util.Random;


public class Powerup extends Item
{


    public static boolean  gold = false;
    public static boolean diamond = false;
    public static boolean ruby = false;
    public static boolean emerald = false;
    public static boolean coal = false;



    public Powerup(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        gold = false;
        diamond = false;
        coal = false;

        Random rand = new Random();
        int randPowerup = rand.nextInt(5);

        if(randPowerup == 0)
        {
            gold = true;
        }
        else if (randPowerup == 1)
        {
            diamond = true;
        }
        else if (randPowerup == 2)
        {
            coal = true;
        }

        setRegion(screen.getAtlas().findRegion("powerups"), (32 * randPowerup), 0, 32 , 32);
        velocity = new Vector2(0, 0);

    }

    @Override
    public void defineItem()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGame.PPM);

        fdef.filter.categoryBits = MyGame.POWERUP_BIT;
        fdef.filter.maskBits = MyGame.MINER_BIT|
                MyGame.BOULDER_BIT |
                MyGame.WALL_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Miner miner)
    {
        destroy();
        //setRegion(screen.getAtlas().null);
    }



    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(velocity);

    }
}

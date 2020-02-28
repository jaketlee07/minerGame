package com.jake.main.main.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jake.main.main.MyGame;

public class Miner extends Sprite {
    public World world;
    public Body b2body;



    public Miner(World world)
    {
        this.world = world;
        defineMiner();
    }

    public void defineMiner()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGame.PPM,32 / MyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MyGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}

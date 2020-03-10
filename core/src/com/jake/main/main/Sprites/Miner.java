package com.jake.main.main.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;

public class Miner extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion minerStand;



    public Miner(World world, PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMiner();
        minerStand = new TextureRegion(getTexture(), 0, 11, 16, 16);
        setBounds(0, 0, 16 / MyGame.PPM, 16/ MyGame.PPM);
        setRegion(minerStand);
    }

    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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

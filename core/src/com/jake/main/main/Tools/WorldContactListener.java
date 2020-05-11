package com.jake.main.main.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jake.main.main.Items.Powerup;
import com.jake.main.main.MyGame;
import com.jake.main.main.Sprites.Miner;
import com.jake.main.main.TileObjects.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef)
        {
            case MyGame.POWERUP_BIT | MyGame.MINER_BIT:
                if (fixA.getFilterData().categoryBits == MyGame.POWERUP_BIT)
                    ((Powerup) fixA.getUserData()).use((Miner) fixB.getUserData());
                else
                    ((Powerup) fixB.getUserData()).use((Miner) fixA.getUserData());
                break;
            /*case MyGame.MINER_BIT | MyGame.WALL_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.MINER_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit(() fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit(() fixB.getUserData());
                break;

             */
        }

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head")
        {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        if(fixA.getUserData() == "right" || fixB.getUserData() == "right")
        {
            Fixture right = fixA.getUserData() == "right" ? fixA : fixB;
            Fixture object = right == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onRightHit();
            }
        }

        if(fixA.getUserData() == "left" || fixB.getUserData() == "left")
        {
            Fixture left = fixA.getUserData() == "left" ? fixA : fixB;
            Fixture object = left == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onLeftHit();
            }
        }

        if(fixA.getUserData() == "foot" || fixB.getUserData() == "foot")
        {
            Fixture foot = fixA.getUserData() == "foot" ? fixA : fixB;
            Fixture object = foot == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
                ((InteractiveTileObject) object.getUserData()).onFootHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact)
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}

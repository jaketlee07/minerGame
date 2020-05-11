package com.jake.main.main.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.jake.main.main.Items.ItemDef;
import com.jake.main.main.Items.Powerup;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;
import com.jake.main.main.Sprites.Miner;


public class Wall extends InteractiveTileObject{
    public Wall(PlayScreen screen, MapObject object)
    {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGame.WALL_BIT);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public void onHeadHit()
    {
        Gdx.app.debug("Wall", "Head Collision");

        if (Powerup.diamond)
        {
            setCategoryFilter(MyGame.DESTROYED_BIT);
            getCell().setTile(null);
        }
    }

    @Override
    public void onRightHit()
    {
        Gdx.app.debug("Wall", "Right Collision");

        if (Powerup.diamond)
        {
            setCategoryFilter(MyGame.DESTROYED_BIT);
            getCell().setTile(null);
        }
    }

    @Override
    public void onLeftHit()
    {
        Gdx.app.debug("Wall", "Left Collision");

        if (Powerup.diamond)
        {
            setCategoryFilter(MyGame.DESTROYED_BIT);
            getCell().setTile(null);
        }
    }

    @Override
    public void onFootHit()
    {
        Gdx.app.debug("Wall", "Foot Collision");

        if (Powerup.diamond)
        {
            setCategoryFilter(MyGame.DESTROYED_BIT);
            getCell().setTile(null);
        }
    }
}
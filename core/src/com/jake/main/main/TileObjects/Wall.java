package com.jake.main.main.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;

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
        //setCategoryFilter(MyGame.DESTROYED_BIT);
    }

    @Override
    public void onRightHit()
    {
        Gdx.app.debug("Wall", "Right Collision");
    }

    @Override
    public void onLeftHit()
    {
        Gdx.app.debug("Wall", "Left Collision");
    }

    @Override
    public void onFootHit()
    {
        Gdx.app.debug("Wall", "Foot Collision");
    }
}
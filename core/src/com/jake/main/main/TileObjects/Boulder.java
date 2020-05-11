package com.jake.main.main.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jake.main.main.Items.ItemDef;
import com.jake.main.main.Items.Powerup;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;
import com.jake.main.main.Sprites.Miner;

import java.awt.Rectangle;

public class Boulder extends InteractiveTileObject{
    public Boulder(PlayScreen screen, MapObject object)
    {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGame.BOULDER_BIT);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public void onHeadHit()
    {
        Gdx.app.debug("Boulder", "Head Collision");
        setCategoryFilter(MyGame.DESTROYED_BIT);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), Powerup.class));
        getCell().setTile(null);
    }

    @Override
    public void onRightHit()
    {
        Gdx.app.debug("Boulder", "Right Collision");
        setCategoryFilter(MyGame.DESTROYED_BIT);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), Powerup.class));
        getCell().setTile(null);
    }

    @Override
    public void onLeftHit()
    {
        Gdx.app.debug("Boulder", "Left Collision");
        setCategoryFilter(MyGame.DESTROYED_BIT);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), Powerup.class));
        getCell().setTile(null);
    }

    @Override
    public void onFootHit()
    {
        Gdx.app.debug("Boulder", "Foot Collision");
        setCategoryFilter(MyGame.DESTROYED_BIT);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y), Powerup.class));
        getCell().setTile(null);
    }
}



package com.jake.main.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jake.main.main.Screens.PlayScreen;

public class MyGame extends Game{
    public static final int V_WIDTH = 480;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short MINER_BIT = 2;
    public static final short BOULDER_BIT = 4;
    public static final short WALL_BIT = 8;
    public static final short DESTROYED_BIT = 16;

    public static SpriteBatch batch;
    public static MyController controller;

    @Override
    public void create () {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
        controller = new MyController();

    }


    @Override
    public void render () {
        super.render();
        //controller.draw();

    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
package com.jake.main.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jake.main.main.Screens.PlayScreen;

public class MyGame extends Game {
    public static SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this ));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
package com.jake.main.main.MainMenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.jake.main.main.MyGame;
import com.jake.main.main.Screens.PlayScreen;

public class MainMenu implements Screen {

    private MyGame game;

    private Texture pic1, pic2, pic3;

    public MainMenu(MyGame game){
        this.game = game;

        pic1 = new Texture("Level1");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(pic1, 600, 400, 400, 200);

        if(Gdx.input.isTouched()){
            game.setScreen(new PlayScreen(game));
        }

        game.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
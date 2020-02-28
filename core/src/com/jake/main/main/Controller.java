package com.jake.main.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Controller {

    Viewport viewport;
    Stage stage;
    boolean upPressed, downpPressed, leftPressed, rightPressed;
    OrthographicCamera cam;

    public Controller()
    {
        cam = new OrthographicCamera();
        viewport =new FitViewport (800, 480, cam);
        stage = new Stage(viewport, MyGame.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.right().bottom();

        Image upImg = new Image(new Texture("up.png"));
        upImg.setSize(50,50);
        upImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //return super.touchDown(event, x, y, pointer, button);

                upPressed = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                downpPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("right.png"));
        rightImg.setSize(50,50);
        rightImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //return super.touchDown(event, x, y, pointer, button);

                upPressed = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                downpPressed = false;
            }
        });

        Image downImg = new Image(new Texture("down.png"));
        downImg.setSize(50,50);
        downImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //return super.touchDown(event, x, y, pointer, button);

                upPressed = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                downpPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("left.png"));
        leftImg.setSize(50,50);
        leftImg.addListener(new InputListener()
        {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //return super.touchDown(event, x, y, pointer, button);

                upPressed = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //super.touchUp(event, x, y, pointer, button);

                downpPressed = false;
            }
        });

        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().pad(5,5,5,5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.add();
        table.row().padBottom(5);
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();

        stage.addActor(table);
    }

    public void draw()
    {
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownpPressed() {
        return downpPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }
}

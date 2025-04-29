package com.TrueWordHunt.Menu;

import com.TrueWordHunt.Util.Panner;
import com.TrueWordHunt.Util.Swoopable;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

// MenuScreen Class
public class MenuScreen implements Screen, Swoopable {
    // reference to the main game class, with all of its variables
    private final WordGame game;
    private Panner panner;

    // the Stage onto which our ui goes
    private Stage menuUIStage;

    private boolean isExiting;
    private Screen nextScreen;

    public MenuScreen(WordGame game) {
        this.game = game;
        menuUIStage = new MenuUIStage(new ExtendViewport(game.STARTING_WIDTH, game.STARTING_HEIGHT), game);

        panner = new Panner(menuUIStage.getViewport().getCamera());

        isExiting = false;
        nextScreen = null;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuUIStage);

        menuUIStage.getCamera().position.x -= menuUIStage.getViewport().getScreenWidth();

        panner.swoopFromLeft(1.5f);
    }

    @Override
    public void render(float delta) {

        // update whatever panning may be happening
        panner.update();
        // process input events
        menuUIStage.act(Gdx.graphics.getDeltaTime());
        // draw stage children
        menuUIStage.draw();

        if (isExiting && !panner.isPanning()) {
            if (nextScreen != null) {
                isExiting = false;
                game.setScreen(nextScreen);
                nextScreen = null;

            }
        }

        menuUIStage.getCamera().update();
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        menuUIStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        menuUIStage.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void swoopAndSwitch(Screen screen) {
        nextScreen = screen;
        panner.swoopToRight(1.5f);
        isExiting = true;
    }
}

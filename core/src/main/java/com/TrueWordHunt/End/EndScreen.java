package com.TrueWordHunt.End;

import com.TrueWordHunt.Game.GameEngine;
import com.TrueWordHunt.Util.StyleGenerator;
import com.TrueWordHunt.Util.Swoopable;
import com.TrueWordHunt.WordGame;
import com.TrueWordHunt.Util.Panner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class EndScreen implements Screen, Swoopable {

    private WordGame game;
    private GameEngine engine;
    private ScoreStage scoreStage;

    // Swoopable fields
    private Screen nextScreen;
    private boolean isExiting;
    private Panner panner;
    private OrthographicCamera camera;
    private ScreenViewport port;

    public EndScreen(WordGame game, GameEngine engine) {
        this.game = game;
        this.engine = engine;
        port = new ScreenViewport();
        camera = (OrthographicCamera) port.getCamera();
        panner = new Panner(camera);
        scoreStage = new ScoreStage(game, this, port);
    }

    @Override
    public void show() {
        // Swoop in from the left (camera starts offset to right)
        camera.position.x -= port.getScreenWidth();
        panner.swoopFromLeft(1.5f);
        Gdx.input.setInputProcessor(scoreStage);
    }

    private void dislayScore() {

    }

    @Override
    public void render(float delta) {
        panner.update();
        scoreStage.act(delta);
        scoreStage.draw();

        if (isExiting && !panner.isPanning()) {
            if (nextScreen != null) {
                isExiting = false;
                game.setScreen(nextScreen);
                nextScreen = null;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        scoreStage.getViewport().update(width, height, true);
        port.update(width, height, true);
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
        scoreStage.dispose();
    }

    public GameEngine getEngine() {
        return engine;
    }

    @Override
    public void swoopAndSwitch(Screen screen) {
        nextScreen = screen;
        panner.swoopToRight(1.5f);
        isExiting = true;
    }
}

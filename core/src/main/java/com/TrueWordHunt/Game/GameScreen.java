package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.Dictionary;
import com.TrueWordHunt.Util.Panner;
import com.TrueWordHunt.Util.Swoopable;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// MenuScreen Class
public class GameScreen implements Screen, Swoopable {
    // reference to the main game class, with all of its variables
    private final WordGame game;

    private GameUIStage gameUIStage;
    private GameStage gameStage;
    private GameEngine gameEngine;
    private GameRenderer gameRenderer;

    private ScreenViewport port;
    private OrthographicCamera camera;
    private Panner panner;

    private boolean isExiting;
    private Screen nextScreen;

    public GameScreen(WordGame game, Dictionary dictionary) {
        this.game = game;

        gameEngine = new GameEngine(4, dictionary);

        port = new ScreenViewport();
        camera = (OrthographicCamera) port.getCamera();
        panner = new Panner(camera);

        camera.position.x -= Gdx.graphics.getWidth();

        gameUIStage = new GameUIStage(port, game);
        gameStage = new GameStage(port, game, this);

        gameRenderer = new GameRenderer(this, gameStage);

        nextScreen = null;
    }


    @Override
    public void show() {
        camera.position.x -= port.getScreenWidth();
        panner.swoopFromLeft(1.5f);

        InputMultiplexer multiplexer = new InputMultiplexer();
        // priority matters!
        multiplexer.addProcessor(gameUIStage);
        multiplexer.addProcessor(gameStage);

        Gdx.input.setInputProcessor(multiplexer);


    }

    @Override
    public void render(float delta) {
        // Draw background, stage
        // ScreenUtils.clear(0.47f, 0.72f, 0.27f, 1f);

        //gameStage.act(delta);
        //gameStage.draw();


        // update whatever panning may be happening
        panner.update();

        gameUIStage.act(delta);
        gameUIStage.draw();

        gameStage.act(delta);
        gameStage.draw();

        gameRenderer.renderBoard();

        if (isExiting && !panner.isPanning()) {
            if (nextScreen != null) {
                isExiting = false;
                game.setScreen(nextScreen);
                nextScreen = null;

            }
        }

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        gameUIStage.getViewport().update(width, height, true);

        gameStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        gameUIStage.dispose();
        gameStage.dispose();
        gameRenderer.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public GameUIStage getGameUIStage() {
        return gameUIStage;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }


    public void swoopAndSwitch(Screen screen) {
        nextScreen = screen;
        panner.swoopToRight(1.5f);
        isExiting = true;
    }
}

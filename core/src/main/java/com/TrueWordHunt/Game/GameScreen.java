package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.Dictionary;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// MenuScreen Class
public class GameScreen implements Screen {
    // reference to the main game class, with all of its variables
    private final WordGame game;

    private GameUIStage gameUIStage;
    private GameStage gameStage;
    private GameEngine gameEngine;
    private GameRenderer gameRenderer;



    public GameScreen(WordGame game, Dictionary dictionary) {
        this.game = game;

        gameEngine = new GameEngine(4, dictionary);
        gameUIStage = new GameUIStage(new ScreenViewport(), game);
        gameStage = new GameStage(new ScreenViewport(), game, this);

        gameRenderer = new GameRenderer(this, gameStage);

    }


    @Override
    public void show() {
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

        gameUIStage.act(delta);
        gameUIStage.draw();

        gameStage.act(delta);
        gameStage.draw();

        gameRenderer.renderBoard();
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

}

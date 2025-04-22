package com.TrueWordHunt.End;

import com.TrueWordHunt.Game.GameEngine;
import com.TrueWordHunt.Util.StyleGenerator;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class EndScreen implements Screen {

    private WordGame game;
    private GameEngine engine;


    private ScoreStage scoreStage;

    public EndScreen(WordGame game, GameEngine engine) {
        this.game = game;
        this.engine = engine;
        scoreStage = new ScoreStage(game, new ScreenViewport());
    }

    @Override
    public void show() {




    }

    @Override
    public void render(float delta) {

        scoreStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.

        scoreStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}

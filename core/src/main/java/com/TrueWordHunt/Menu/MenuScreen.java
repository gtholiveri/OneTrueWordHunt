package com.TrueWordHunt.Menu;

import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

// MenuScreen Class
public class MenuScreen implements Screen {
    // reference to the main game class, with all of its variables
    private final WordGame game;

    // the Stage onto which our ui goes
    private Stage menuUIStage;


    public MenuScreen(WordGame game) {
        this.game = game;
        // Assuming 2:1 size
        menuUIStage = new MenuUIStage(new ExtendViewport(game.STARTING_WIDTH, game.STARTING_HEIGHT), game);
        // background = new Texture("menu_background.png");

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuUIStage);
    }

    @Override
    public void render(float delta) {
        // ScreenUtils.clear(0.39f, 0.58f, 0.93f, 1f);


        menuUIStage.act(Gdx.graphics.getDeltaTime());
        menuUIStage.draw();
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
}

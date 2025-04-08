package com.TrueWordHunt;

import com.TrueWordHunt.Game.GameScreen;
import com.TrueWordHunt.Menu.MenuScreen;
import com.TrueWordHunt.Util.BackgroundRenderer;
import com.TrueWordHunt.Util.Dictionary;
import com.TrueWordHunt.Util.StyleGenerator;
import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class WordGame extends Game {

    public MenuScreen menuScreen;
    private GameScreen gameScreen;
    public final float STARTING_WIDTH = 1000;
    public final float STARTING_HEIGHT = 1500;

    private Dictionary dictionary;
    private BackgroundRenderer bgRenderer;
    private StyleGenerator styleGenerator;

    @Override
    public void create() {
        VisUI.load();

        dictionary = new Dictionary(3, 16);
        bgRenderer = new BackgroundRenderer();
        styleGenerator = new StyleGenerator();


        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);

    }

    @Override
    public void resize(int width, int height) {
        this.getScreen().resize(width, height);
        bgRenderer.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        bgRenderer.renderBackground();

        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();

        VisUI.dispose();

        bgRenderer.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void newGame() {
        gameScreen = new GameScreen(this, dictionary);
        this.setScreen(gameScreen);
    }

    public StyleGenerator getStyleGenerator() {
        return styleGenerator;
    }
}

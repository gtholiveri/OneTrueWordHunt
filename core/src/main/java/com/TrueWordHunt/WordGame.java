package com.TrueWordHunt;

import com.TrueWordHunt.End.EndScreen;
import com.TrueWordHunt.Game.GameScreen;
import com.TrueWordHunt.Menu.MenuScreen;
import com.TrueWordHunt.Util.BackgroundRenderer;
import com.TrueWordHunt.Util.Dictionary;
import com.TrueWordHunt.Util.StyleGenerator;
import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class WordGame extends Game {

    // TODO Create an end screen class
    // TODO Create a timer class that will control entry to the end screen
    // TODO Create a FinalScoreStage class with methods to render a final score thing
    // TODO Get sizing to be DYNAMIC based on the screen dimensions
    // TODO Create a camera panner class that will be able to swoop things in and out to the left and right
    // TODO Maybe implement dynamic tile size and color as selected?


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

    public void gameOver() {

        if (getScreen() instanceof GameScreen) {
            setScreen(new EndScreen(this, ((GameScreen) getScreen()).getGameEngine()));
        }


    }

    public StyleGenerator getStyleGenerator() {
        return styleGenerator;
    }
}

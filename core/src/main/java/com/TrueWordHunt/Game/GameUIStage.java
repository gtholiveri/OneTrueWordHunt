package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.Timer;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class GameUIStage extends Stage {
    // the instance of the game
    private final WordGame game;


    // styles
    private VisLabel.LabelStyle scoreLabelStyle;
    private VisLabel.LabelStyle defaultLabelStyle;
    private VisTextButton.VisTextButtonStyle homeButtonStyle;

    // tables
    private VisTable infoTable;
    private VisTable wrapper;

    // the play button
    private VisTextButton homeButton;

    // game info labels
    private VisLabel scoreLabel;
    private VisLabel wordsFoundLabel;
    private VisLabel timeRemainingLabel;
    private VisLabel currWordLabel;

    // timer
    private Timer timer;

    private boolean firstPass;

    private ShapeRenderer shapeRenderer;

    private boolean timerHasFired;

    public GameUIStage(Viewport viewport, WordGame game) {
        super(viewport);
        this.game = game;

        // create all our styles
        Gdx.app.log("VisUI Status", VisUI.isLoaded() ? "TRUE" : "FALSE");

        scoreLabelStyle = game.getStyleGenerator().createLabelStyle(72, "bebas_extra_bold.ttf", Color.BLACK);
        defaultLabelStyle = game.getStyleGenerator().createLabelStyle(36, "bebas_extra_bold.ttf", Color.BLACK);
        homeButtonStyle = game.getStyleGenerator().createTextButtonStyle(36, "bebas_extra_bold.ttf", Color.WHITE);


        setupHomeButton();
        setupInfoBox();
        addButtonAction();

        setupFinishButton();

        timer = new Timer(60);

        firstPass = true;

        timerHasFired = false;
    }


    @Override
    public void draw() {
        Batch batch = getBatch();
        batch.begin();

        NinePatchDrawable bg = game.getStyleGenerator().getTableBG();
        Vector2 pos = infoTable.localToStageCoordinates(new Vector2(0, 0));
        float x = pos.x - 30;
        float y = pos.y - 30;
        float width = infoTable.getWidth() + 60;
        float height = infoTable.getHeight() + 40;


        if (firstPass) {
            firstPass = false;
        } else {
            bg.draw(batch, x, y, width, height);
        }
        batch.end();

        timeRemainingLabel.setText(timer.update());
        if (timer.isFinished() && !timerHasFired) {
            game.gameOver();
            timerHasFired = true;
        }

        super.draw();
    }

    private void setupHomeButton() {
        VisTable homeButtonTable = new VisTable();
        homeButtonTable.setFillParent(true);

        homeButtonTable.setTransform(true);          // Makes table respect pixel coordinates
        homeButtonTable.align(Align.top | Align.left); // Forces top-left anchor point


        // create the button, add it to a centered table, scale it to good-looking size
        homeButton = new VisTextButton("Home", homeButtonStyle);

        homeButtonTable.add(homeButton).width(100).height(50).pad(20);


        this.addActor(homeButtonTable);
    }

    private void setupFinishButton() {
        VisTable finishButtonTable = new VisTable();
        finishButtonTable.setFillParent(true);

        finishButtonTable.setTransform(true);
        finishButtonTable.align(Align.top | Align.right);


        VisTextButton finishButton = new VisTextButton("Finish", homeButtonStyle);
        finishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.gameOver();
            }
        });


        finishButtonTable.add(finishButton).width(100).height(50).pad(20);

        this.addActor(finishButtonTable);
    }

    /**
     * Subroutine that sets up all the elements in the main information box.
     * <p>
     * Includes score, words found, time remaining, and current word
     */
    private void setupInfoBox() {
        // create a wrapper that fills the whole screen
        wrapper = new VisTable();
        wrapper.setFillParent(true);
        wrapper.top();
        //wrapper.debug();


        // create the bit with just the info
        infoTable = new VisTable();
        infoTable.setFillParent(false);
        //infoTable.debug();

        scoreLabel = new VisLabel("Score: 0", scoreLabelStyle);
        wordsFoundLabel = new VisLabel("Words Found: 0", defaultLabelStyle);
        timeRemainingLabel = new VisLabel("", defaultLabelStyle);
        currWordLabel = new VisLabel("", defaultLabelStyle);

        infoTable.add(scoreLabel).left().row();
        infoTable.add(wordsFoundLabel).left().row();
        infoTable.add(timeRemainingLabel).left().row();
        infoTable.add(currWordLabel);

        infoTable.pack();

        // add the infoTable to the wrapper
        wrapper.add(infoTable).top().padTop(20);


        infoTable.pack();

        this.addActor(wrapper);


    }



    private void addButtonAction() {
        homeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.goHome();
            }

        });
    }

    @Override
    public void dispose() {
        super.dispose();

        // uiSkin.dispose();
    }

    // all the update methods for the various fields
    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void setWordsFound(int wordsFound) {
        wordsFoundLabel.setText("Words Found: " + wordsFound);
    }

    public void incrementWordsFound() {
        int wordsFound = Integer.parseInt(wordsFoundLabel.getText().substring(13)) + 1;
        //"Words Found: number"
        //"01234567890123 <-- start at index 13
        wordsFoundLabel.setText("Words Found: " + wordsFound);
    }

    public void setTime(float timeRemaining) {
        timeRemainingLabel.setText(secondsToString((int) timeRemaining));
    }

    public void setCurrWord(String word) {
        currWordLabel.setText(word);
    }

    public void incrementCurrWord(char c) {
        String currText = String.valueOf(currWordLabel.getText());
        currWordLabel.setText(currText + c);
    }

    public String secondsToString(int totalSecs) {
        String minutes = Integer.toString(totalSecs / 60);
        String seconds = Integer.toString(totalSecs % 60);

        return minutes + ":" + seconds;
    }

    public VisTable getInfoTable() {
        return infoTable;
    }

    public float getInfoTableDistFromTop() {
        return infoTable.getHeight();//Gdx.graphics.getHeight() - (infoTable.getY());
    }
}

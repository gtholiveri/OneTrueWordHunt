package com.TrueWordHunt.End;

import com.TrueWordHunt.Game.GameEngine;
import com.TrueWordHunt.Util.StyleGenerator;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.ArrayList;

public class ScoreStage extends Stage {


    // TODO create a 9-patch based on the wood tile, and create a 9-patch with a deeper-colored, recessed look for the background
    private WordGame game;

    private EndScreen screen;
    private GameEngine engine;

    private StyleGenerator styleGenerator;

    private VisTextButton homeButton;
    private VisTextButton.VisTextButtonStyle homeButtonStyle;



    public ScoreStage(WordGame game, EndScreen screen, Viewport viewport) {
        super(viewport);

        this.game = game;
        this.screen = screen;
        engine = screen.getEngine();

        styleGenerator = game.getStyleGenerator();

        // create the overarching table
        VisTable scoreTable = new VisTable();
        //scoreTable.debug();
        scoreTable.setFillParent(true);
        scoreTable.setTransform(true);
        scoreTable.align(Align.center);

        // get the words found
        ArrayList<String> wordsFound = engine.getWordsFound();

        // create the table holding the contents of the scroll pane
        VisTable scorePaneContents = new VisTable();
        //scorePaneContents.debug();

        // add the header to the contents
        Label.LabelStyle headerStyle = styleGenerator.createLabelStyle(52, "bebas_bold.ttf", Color.BLACK);
        scorePaneContents.add(new VisLabel("WORDS FOUND: ", headerStyle)).left().row();


        //String[] testWords = new String[] {"thing", "galadriel", "test", "minas tirith", "uruk hai", "lothlorien", "numenor", "got", "cooked", "etc", "tar valon", "cairhien", "andor", "tear", "row", "row", "row", "row", "row", "row", "row", "row"};
        // add every row to the contents
        Label.LabelStyle bodyStyle = styleGenerator.createLabelStyle(36, "bebas_medium.ttf", Color.BLACK);
        for (String word : wordsFound) {
            scorePaneContents.add(new VisLabel(word.toUpperCase(), bodyStyle)).left();
            scorePaneContents.add(new VisLabel(Integer.toString(engine.computeWordScore(word)), bodyStyle)).right().row();
        }



        VisScrollPane scorePane = new VisScrollPane(scorePaneContents);

        scoreTable.add(scorePane);

        this.addActor(scoreTable);

        homeButtonStyle = styleGenerator.createTextButtonStyle(36, "bebas_extra_bold.ttf", Color.WHITE);
        setupHomeButton();
        addButtonAction();

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

    private void addButtonAction() {
        homeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.goHome();
            }

        });
    }

}

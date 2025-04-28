package com.TrueWordHunt.End;

import com.TrueWordHunt.Game.GameEngine;
import com.TrueWordHunt.Util.StyleGenerator;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;

import java.util.ArrayList;

public class ScoreStage extends Stage {


    // TODO create a 9-patch based on the wood tile, and create a 9-patch with a deeper-colored, recessed look for the background
    private WordGame game;

    private EndScreen screen;
    private GameEngine engine;

    private StyleGenerator styleGenerator;

    public ScoreStage(WordGame game, EndScreen screen, Viewport viewport) {
        super(viewport);

        this.game = game;
        this.screen = screen;
        engine = screen.getEngine();

        styleGenerator = game.getStyleGenerator();

        // create the overarching table
        VisTable scoreTable = new VisTable();
        scoreTable.debug();
        scoreTable.setFillParent(true);
        scoreTable.setTransform(true);
        scoreTable.align(Align.center);

        // get the words found
        ArrayList<String> wordsFound = engine.getWordsFound();

        // create the table holding the contents of the scroll pane
        VisTable scorePaneContents = new VisTable();
        scorePaneContents.debug();

        // add the header to the contents
        Label.LabelStyle headerStyle = styleGenerator.createLabelStyle(52, "bebas_bold.ttf", Color.BLACK);
        scorePaneContents.add(new VisLabel("WORDS FOUND: ", headerStyle)).left().row();


        // String[] testWords = new String[] {"thing", "galadriel", "test", "minas tirith", "uruk hai", "lothlorien", "numenor", "got", "cooked", "etc", "tar valon", "cairhien", "andor", "tear"};
        // add every row to the contents
        Label.LabelStyle bodyStyle = styleGenerator.createLabelStyle(36, "bebas_medium.ttf", Color.BLACK);
        for (String word : wordsFound) {
            scorePaneContents.add(new VisLabel(word.toUpperCase(), bodyStyle)).left();
            scorePaneContents.add(new VisLabel(Integer.toString(engine.computeWordScore(word)), bodyStyle)).right().row();
        }



        VisScrollPane scorePane = new VisScrollPane(scorePaneContents);
        scorePane.setFadeScrollBars(false); // Prevent scrollbars from fading out
        scorePane.setForceScroll(false, true); // Force vertical scrollbar track to always show (doesn't guarantee scrolling works if content fits)


        scoreTable.add(scorePane);


        //(new VisTextButton("TEST SUCCESSFUL", styleGenerator.createTextButtonStyle(72, "bebas_bold.ttf", new Color(1f, 1f, 1f, 1f))));

        this.addActor(scoreTable);

    }

}

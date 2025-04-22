package com.TrueWordHunt.End;

import com.TrueWordHunt.Util.StyleGenerator;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class ScoreStage extends Stage {


    // TODO create a 9-patch based on the wood tile, and create a 9-patch with a deeper-colored, recessed look for the background
    private WordGame game;
    private StyleGenerator styleGenerator;

    public ScoreStage(WordGame game, Viewport viewport) {
        super(viewport);

        this.game = game;
        styleGenerator = game.getStyleGenerator();

        VisTable scoreTable = new VisTable();
        scoreTable.setFillParent(true);
        scoreTable.setTransform(true);
        scoreTable.align(Align.center);

        //genuinely horrendous line of code lol
        scoreTable.add(new VisTextButton("TEST SUCCESSFUL", styleGenerator.createTextButtonStyle(72, "bebas_bold.ttf", new Color(1f, 1f, 1f, 1f))));

        this.addActor(scoreTable);

    }

}

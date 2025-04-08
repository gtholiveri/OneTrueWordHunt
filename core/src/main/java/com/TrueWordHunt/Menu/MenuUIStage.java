package com.TrueWordHunt.Menu;

import com.TrueWordHunt.Game.GameScreen;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class MenuUIStage extends Stage {
    // the instance of the game
    private WordGame game;

    // the play button
    private VisTextButton playButton;

    // helps lay out elements
    VisTable table;

    // defines appearance of ui elements
    // private Skin uiSkin;


    public MenuUIStage(Viewport viewport, WordGame game) {
        super(viewport);

        this.game = game;

        table = new VisTable();

        // get the skin
        // uiSkin = new Skin(Gdx.files.internal("ui/sgx-ui.json"));


        setupUI();

        addButtonAction();
    }

    private void setupUI() {
        table.setFillParent(true);
        table.debug();


        // create the button, add it to a centered table
        playButton = new VisTextButton("NEW GAME", getPlayButtonStyle());

        table.add(playButton).width(400).height(100).center();

        this.addActor(table);
    }

    public VisTextButton.VisTextButtonStyle getPlayButtonStyle() {
        return game.getStyleGenerator().createTextButtonStyle(72, "bebas_extra_bold.ttf", Color.WHITE);
    }

    private void addButtonAction() {
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.newGame();
            }

        });
    }

}

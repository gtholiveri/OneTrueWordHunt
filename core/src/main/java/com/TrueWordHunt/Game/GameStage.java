package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.ClipDrawer;
import com.TrueWordHunt.Util.Swoopable;
import com.TrueWordHunt.WordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTable;

public class GameStage extends Stage {
    // the instance of the game
    private WordGame game;
    private GameScreen screen;
    private GameEngine gameEngine;
    private GameUIStage gameUI;

    private Tile[][] tiles;

    private int tileSize;


    public GameStage(Viewport viewport, WordGame game, GameScreen screen) {
        super(viewport);

        this.game = game;
        this.screen = screen;
        this.gameEngine = screen.getGameEngine();
        this.gameUI = screen.getGameUIStage();


        int size = gameEngine.getBoard().length;
        tiles = new Tile[size][size];

        tileSize = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) / 6;


        setupBoard();
        setupDragListeners();
    }

    private void setupDragListeners() {
        DragListener dragListener = new DragListener() {

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                for (Tile[] row : tiles) {
                    for (Tile tile : row) {
                        Vector2 local = tile.stageToLocalCoordinates(new Vector2(x, y));
                        if (tile.hit(local.x, local.y, true) != null) {
                            GameEngine engine = screen.getGameEngine();
                            GameUIStage ui = screen.getGameUIStage();

                            gameEngine.click(tile.getRow(), tile.getCol());
                            gameUI.setCurrWord(engine.getCurrWord(true));
                            gameUI.setScore(engine.getScore());
                        }
                    }
                }
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                gameEngine.unClick();
                gameUI.setCurrWord("");
                gameUI.setScore(screen.getGameEngine().getScore());
                gameUI.setWordsFound(gameEngine.getWordsFound().size());

            }
        };
        this.addListener(dragListener);
    }

    private void setupBoard() {
        VisTable boardTable = new VisTable();
        boardTable.setFillParent(true);
        boardTable.center();
        boardTable.defaults().space(tileSize / 10);


        VisImageTextButton.VisImageTextButtonStyle buttonStyle = game.getStyleGenerator().createImageTextButtonStyle((int) (0.9 * tileSize), "bebas_extra_bold.ttf", Color.BLACK);

        char[][] board = screen.getGameEngine().getBoard();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Tile curr = new Tile(Character.toString(board[row][col]), buttonStyle, row, col);
                tiles[row][col] = curr;

                boardTable.add(curr).size(tileSize);
            }
            boardTable.row();
        }

        //float infoTableHeight = screen.getGameUIStage().getInfoTable().getHeight();
        //float gameAreaMaxHeight = Gdx.graphics.getHeight() - infoTableHeight;

        this.addActor(boardTable);

    }

    @Override
    public void draw() {


        super.draw();
    }

    public Tile[][] getTiles() {
        return tiles;
    }


}

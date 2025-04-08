package com.TrueWordHunt.Game;

import com.TrueWordHunt.WordGame;
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

    private Tile[][] tiles;


    public GameStage(Viewport viewport, WordGame game, GameScreen screen) {
        super(viewport);

        this.game = game;
        this.screen = screen;
        int size = screen.getGameEngine().getBoard().length;
        tiles = new Tile[size][size];

        setupBoard();
        setupDragListener();
    }

    private void setupDragListener() {
        DragListener dragListener = new DragListener() {

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                for (Tile[] row : tiles) {
                    for (Tile tile : row) {
                        Vector2 local = tile.stageToLocalCoordinates(new Vector2(x, y));
                        if (tile.hit(local.x, local.y, true) != null) {
                            //System.out.println("Letter " + tile.getText() + " dragged at position (" + tile.getRow() + ", " + tile.getCol() + ")");
                            screen.getGameEngine().click(tile.getRow(), tile.getCol());
                        }
                    }
                }
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                //System.out.println("Drag terminated");
                screen.getGameEngine().unClick();
            }
        };


        this.addListener(dragListener);
    }

    private void setupBoard() {
        VisTable boardTable = new VisTable();
        boardTable.setFillParent(true);
        boardTable.center();
        boardTable.defaults().space(30f);
        boardTable.debug();


        VisImageTextButton.VisImageTextButtonStyle buttonStyle = game.getStyleGenerator().createImageTextButtonStyle(140, "bebas_extra_bold.ttf", Color.BLACK);

        char[][] board = screen.getGameEngine().getBoard();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Tile curr = new Tile(Character.toString(board[row][col]), buttonStyle, row, col);
                tiles[row][col] = curr;

                //                curr.addListener(new ClickListener() {
//
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        // Your logic here
//                        System.out.println("Board Tile: Letter " + curr.getText() + " clicked!");
//                    }
//                });
                boardTable.add(curr).size(200);
            }
            boardTable.row();
        }

        //float infoTableHeight = screen.getGameUIStage().getInfoTable().getHeight();
        //float gameAreaMaxHeight = Gdx.graphics.getHeight() - infoTableHeight;

        this.addActor(boardTable);

    }

    public Tile[][] getTiles() {
        return tiles;
    }

}

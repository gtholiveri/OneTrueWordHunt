package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.Dictionary;
import com.TrueWordHunt.Util.Position;
import com.badlogic.gdx.Gdx;


import java.util.ArrayList;

public class GameEngine {
    private ArrayList<Position> currPath;

    private char[][] board;
    private Dictionary dictionary;


    public GameEngine(int boardSize, Dictionary dictionary) {
        currPath = new ArrayList<Position>();

        board = new char[boardSize][boardSize];
        initBoard();

//        Runtime runtime = Runtime.getRuntime();
//        long before = runtime.totalMemory() - runtime.freeMemory();
        this.dictionary = dictionary;
//        long after = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("Dictionary currently using " + (after - before) / (1024 * 1024) + " MB");

    }

    public String getCurrWord() {
        StringBuilder sb = new StringBuilder();

        for (Position p: currPath) {
            sb.append(board[p.getRow()][p.getCol()]);
        }

        return sb.toString().toLowerCase();
    }

    public void click(int row, int col) {
        // if it's a valid position, add the current
        click(new Position(row, col));
    }

    public void click(Position clickedPos) {
        if (isValidNextPos(clickedPos)) {
            currPath.add(clickedPos);
        }
    }

    public void unClick() {
        currPath.clear();
    }

    private boolean isValidNextPos(Position currPos) {
        if (currPath.size() == 0) {
            // if this is the first selection, by default it's fine
            return true;
        }
        Position lastPos = currPath.get(currPath.size() - 1);

        if (currPath.contains(currPos)) {
            // this position has already been selected: not valid
            return false;
        }

        if (!inBounds(currPos)) {
            Gdx.app.log("CONCERNING", "Out-of-bounds index somehow passed to GameEngine isValidNextPos()");
            return false;
        }

        if (!(lastPos.nextTo(currPos))){
            // if it's not exactly 1 away from the last thing, not allowed
            return false;
        }


        return true;
    }

    private boolean isValidNextPos(int row, int col) {
        return isValidNextPos(new Position(row, col));
    }

    private boolean inBounds(int row, int col) {
        return inBounds(new Position(row, col));
    }

    private boolean inBounds(Position p) {
        int row = p.getRow();
        int col = p.getCol();
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    public char[][] getBoard() {
        return board;
    }

    private void initBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                board[r][c] = randUpperChar();
            }
        }
    }

    private char randUpperChar() {
        return (char) ('A' + Math.random() * 26);
    }

    public ArrayList<Position> getCurrPath() {
        return currPath;
    }

    public boolean isWord(String word) {
        return dictionary.hasWord(word);
    }

    public boolean startsWord(String word) {
        return dictionary.hasPrefix(word);
    }

}



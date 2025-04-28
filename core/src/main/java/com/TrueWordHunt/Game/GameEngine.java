package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.Dictionary;
import com.TrueWordHunt.Util.Position;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class GameEngine {
    private ArrayList<Position> currPath;

    private ArrayList<String> wordsFound;

    private final int[] wordScores = new int[]{0, 0, 0, 100, 400, 800, 1400, 1800, 2200, 3000, 3400, 3800, 4200, 4600, 5000, 5400, 5800, 6200, 10000};
    private char[][] board;

    private Dictionary dictionary;

    public GameEngine(int boardSize, Dictionary dictionary) {
        currPath = new ArrayList<Position>();
        wordsFound = new ArrayList<>();

        board = new char[boardSize][boardSize];
        initBoard();

        this.dictionary = dictionary;
    }

    public String getCurrWord() {
        StringBuilder sb = new StringBuilder();

        for (Position p : currPath) {
            sb.append(board[p.getRow()][p.getCol()]);
        }

        return sb.toString().toLowerCase();
    }

    public String getCurrWord(boolean uppercase) {
        if (uppercase) {
            return getCurrWord().toUpperCase();
        } else {
            return getCurrWord();
        }
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

        String currWord = getCurrWord();
        if (isWord(currWord) && !wordsFound.contains(currWord)) {
            wordsFound.add(currWord);
        }

        currPath.clear();
    }

    public int getScore() {
        int score = 0;
        for (String word : wordsFound) {
            score += wordScores[word.length()];
        }
        return score;
    }

    private boolean isValidNextPos(Position currPos) {
        if (currPath.size() == 0) {
            // if this is the first selection, by default it's fine
            return true;
        }

        if (currPath.contains(currPos)) {
            // this position has already been selected: not valid
            return false;
        }

        if (!inBounds(currPos)) {
            Gdx.app.log("CONCERNING", "Out-of-bounds index somehow passed to GameEngine isValidNextPos()");
            return false;
        }

        Position lastPos = currPath.get(currPath.size() - 1);
        if (!(lastPos.nextTo(currPos))) {
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

    public ArrayList<String> getWordsFound() {
        return wordsFound;
    }

    public int computeWordScore(String word) {
        int n = word.length();

        if (n > wordScores.length) {
            return wordScores[wordScores.length - 1];
        } else {
            return wordScores[n];
        }
    }
}



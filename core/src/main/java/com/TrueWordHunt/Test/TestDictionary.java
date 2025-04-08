package com.TrueWordHunt.Test;

import com.TrueWordHunt.Game.GameEngine;
import com.TrueWordHunt.Util.Dictionary;

public class TestDictionary {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary(3, 16, true);


        GameEngine gameEngine = new GameEngine(4, dictionary);

        System.out.println(gameEngine.isWord("rachiglossate"));
        System.out.println(gameEngine.startsWord("rachiglossate"));

    }

}

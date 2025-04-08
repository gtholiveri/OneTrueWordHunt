package com.TrueWordHunt.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.BufferedReader;

public class Dictionary {
    private PatriciaTrie<Boolean> trie; // Holds String keys with boolean values
    private int minLength;
    private int maxLength;

    public Dictionary(int minLength, int maxLength) {
        this.trie = new PatriciaTrie<>();
        this.minLength = minLength;
        this.maxLength = maxLength;


        FileHandle fileHandle = Gdx.files.internal("everyWord.txt");

        BufferedReader reader = new BufferedReader(fileHandle.reader());

        reader.lines().forEach(line -> {
            if (line.length() >= minLength && line.length() <= maxLength) {
                trie.put(line, true);
            }
        });

    }


    public Dictionary(int minLength, int maxLength, boolean test) {
        this.trie = new PatriciaTrie<>();
        this.minLength = minLength;
        this.maxLength = maxLength;


        FileHandle fileHandle = new FileHandle("assets/everyWord.txt");

        BufferedReader reader = new BufferedReader(fileHandle.reader());

        reader.lines().forEach(line -> {
            if (line.length() >= minLength && line.length() <= maxLength) {
                trie.put(line, true);
            }
        });

    }

    public boolean hasWord(String word) {
        return trie.containsKey(word.toLowerCase());
    }

    public boolean hasPrefix(String prefix) {
        // prefixMap(prefix) returns all keys that start with the given prefix
        // so we know that the current prefix leads to SOME word if
        // the map is NOT empty
        return !trie.prefixMap(prefix).isEmpty();
    }
}

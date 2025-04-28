package com.TrueWordHunt.Util;

import com.badlogic.gdx.Gdx;

public class Timer {
    private float secondsRemaining;

    public Timer(int startingSeconds) {
        secondsRemaining = (float) startingSeconds;
    }

    public String update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        secondsRemaining -= deltaTime;

        return toString();
    }

    @Override
    public String toString() {
        int min = (int) secondsRemaining / 60;

        int sec = (int) secondsRemaining % 60;
        return String.format("%02d:%02d", min, sec);
    }

    public float getSecondsRemaining() {
        return secondsRemaining;
    }

    public boolean isFinished() {
        return secondsRemaining <= 0;
    }
}

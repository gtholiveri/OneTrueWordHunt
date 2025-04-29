package com.TrueWordHunt.Util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.TimeUtils;

public class Panner {

    private final Camera camera; // The camera controlled by this Panner
    private boolean isPanning;
    private long startTime;
    private float duration; // Duration of the pan in seconds
    private float startX;
    private float targetX;
    private Interpolation interpolation;

    /**
     * Creates a Panner associated with a specific Camera.
     * @param camera The camera this Panner will control.
     */
    public Panner(Camera camera) {
        this.camera = camera;
        this.interpolation = Interpolation.smoother; // Default interpolation
        this.isPanning = false;
    }

    /**
     * Smoothly swoops the associated camera one viewport width to the left from its current position.
     * @param durationSeconds The duration of the swoop in seconds.
     */
    public void swoopToLeft(float durationSeconds) {
        startPan(camera.position.x - camera.viewportWidth, durationSeconds);
    }

    /**
     * Smoothly swoops the associated camera one viewport width to the right from its current position.
     * @param durationSeconds The duration of the swoop in seconds.
     */
    public void swoopToRight(float durationSeconds) {
        startPan(camera.position.x + camera.viewportWidth, durationSeconds);
    }

    /**
     * Smoothly swoops the associated camera back to the center from its current off-screen left position.
     * Assumes the camera was previously moved left by one viewport width.
     * @param durationSeconds The duration of the swoop in seconds.
     */
    public void swoopFromLeft(float durationSeconds) {
        startPan(/*camera.position.x +*/ camera.viewportWidth / 2, durationSeconds);
    }

    /**
     * Smoothly swoops the associated camera back to the center from its current off-screen right position.
     * Assumes the camera was previously moved right by one viewport width.
     * @param durationSeconds The duration of the swoop in seconds.
     */
    public void swoopFromRight(float durationSeconds) {
        startPan(camera.position.x - camera.viewportWidth, durationSeconds);
    }

    /**
     * Starts a pan animation to the target x position.
     * @param targetX The target x position for the camera.
     * @param durationSeconds The duration of the pan in seconds.
     */
    private void startPan(float targetX, float durationSeconds) {
        this.startX = camera.position.x;
        this.targetX = targetX;
        this.duration = durationSeconds;
        this.startTime = TimeUtils.nanoTime();
        this.isPanning = true;
    }

    /**
     * Updates the associated camera's position during a pan. Call this method continuously (e.g., in your render loop).
     */
    public void update() {
        if (!isPanning) return;

        float elapsedSeconds = (TimeUtils.nanoTime() - startTime) / 1000000000f;
        float progress = Math.min(elapsedSeconds / duration, 1f);
        float alpha = interpolation.apply(progress);

        float newX = startX + (targetX - startX) * alpha;
        camera.position.x = newX;
        camera.update();

        if (progress >= 1f) {
            isPanning = false;
        }
    }

    /**
     * Checks if the associated camera is currently in the process of panning.
     * @return true if panning is in progress, false otherwise.
     */
    public boolean isPanning() {
        return isPanning;
    }

    /**
     * Gets the camera associated with this Panner.
     * @return The Camera instance.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Sets the interpolation to be used for panning animations.
     * @param interpolation The Interpolation to use.
     */
    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    /**
     * Gets the current interpolation being used for panning animations.
     * @return The current Interpolation.
     */
    public Interpolation getInterpolation() {
        return interpolation;
    }
}

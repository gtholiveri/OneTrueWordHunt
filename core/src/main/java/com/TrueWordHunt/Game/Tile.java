package com.TrueWordHunt.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.widget.VisImageTextButton;

public class Tile extends VisImageTextButton {

    private int row;
    private int col;

    private float centerX;

    private float centerY;

    private final float WIDTH_TO_RADIUS_RATIO = 2f;

    /**
     * @param text Button text
     * @param style Button style
     */
    public Tile(String text, VisImageTextButtonStyle style, int row, int col) {
        super(text, style);
        this.row = row;
        this.col = col;

        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;

    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        // Calculate circle parameters
        float radius = getWidth() / WIDTH_TO_RADIUS_RATIO; // Diameter = width/2 → radius = width/4
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;

        // Check if point is within circle
        float dx = x - centerX;
        float dy = y - centerY;
        boolean isInside = (dx * dx + dy * dy) <= radius * radius;

        return isInside ? this : null;
    }


    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes); // Draw default debug

        // Draw circle hitbox
        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        shapes.circle(
            getX() + getWidth() / 2f,
            getY() + getHeight() / 2f,
            getWidth() / WIDTH_TO_RADIUS_RATIO
        );
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }
}

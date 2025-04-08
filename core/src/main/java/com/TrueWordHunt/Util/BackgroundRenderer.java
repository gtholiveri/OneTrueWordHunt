package com.TrueWordHunt.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BackgroundRenderer implements Disposable {

    private SpriteBatch bgBatch;
    private TextureRegion bgRegion;
    private Viewport bgVp;


    public BackgroundRenderer() {
        Texture bgTexture = new Texture(Gdx.files.internal("backgroundPattern.png"));
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);


        bgRegion = new TextureRegion(bgTexture);

        bgBatch = new SpriteBatch();

        bgVp = new ScreenViewport();

    }

    public void renderBackground() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // rendering the background
        float worldWidth = bgVp.getWorldWidth();
        float worldHeight = bgVp.getWorldHeight();


        // I'll be honest, I don't understand how this low-level openGL stuff works
        // but by experimenting, bigger TILE_SCALE_FACTOR = zoomed in background, smaller = zoomed out
        float TILE_SCALE_FACTOR = 0.25f;

        float tileFactorX = worldWidth / (TILE_SCALE_FACTOR * bgRegion.getTexture().getWidth());
        float tileFactorY = worldHeight / (TILE_SCALE_FACTOR * bgRegion.getTexture().getHeight());

        bgRegion.setRegion(0, 0, tileFactorX, tileFactorY);


        bgBatch.setProjectionMatrix(bgVp.getCamera().combined);


        // Draw tiled background
        bgBatch.begin();
        bgBatch.draw(bgRegion,0, 0, worldWidth, worldHeight);
        bgBatch.end();

    }

    public Viewport getViewport() {
        return bgVp;
    }

    public void dispose() {
        bgBatch.dispose();
        bgRegion.getTexture().dispose();

    }
}

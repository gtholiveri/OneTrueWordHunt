package com.TrueWordHunt.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera; // Import Camera
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Helper class to draw a tiled texture clipped by a NinePatch shape,
 * using the stencil buffer, and overlayed with a border NinePatch.
 * <p>
 * This class manages its own internal SpriteBatch, Camera reference, stencil clearing,
 * projection matrix setting, and disposal of loaded textures. It uses a single, common
 * split size for all four sides (left, right, top, bottom) of both the mask
 * and border NinePatches.
 * </p>
 */
public class ClipDrawer implements Disposable {

    private final Camera camera; // Reference to the camera for projection
    private final SpriteBatch internalBatch; // Owns and manages this batch
    private final Texture contentTexture;
    private final TextureRegion tiledRegion;
    private final NinePatch maskNinePatch;
    private final NinePatch borderNinePatch;

    // Flags for disposal logic (though default constructor always owns them)
    private final boolean ownsTextureContent = true;
    private final boolean ownsTextureMask = true;
    private final boolean ownsTextureBorder = true;

    /**
     * Creates a ClipDrawer instance, loading necessary textures and NinePatches,
     * and creating its own internal SpriteBatch.
     * Uses a single, common split size for all sides of both NinePatches.
     *
     * @param camera              The Camera used to set the projection matrix for drawing. Must not be null.
     * @param contentTexturePath  Path to the texture file to be tiled.
     * @param maskNinePatchPath   Path to the NinePatch texture file for the clipping mask shape.
     * @param borderNinePatchPath Path to the NinePatch texture file for the border overlay.
     * @param commonSplitSize     The distance (in pixels) from the edge to use for the splits on all four sides (left, right, top, bottom) of both the mask and border NinePatches. Must be non-negative.
     */
    public ClipDrawer(Camera camera,
                      String contentTexturePath,
                      String maskNinePatchPath,
                      String borderNinePatchPath,
                      int commonSplitSize) { // Changed back from auto-detect to common split size

        if (camera == null) {
            throw new IllegalArgumentException("Camera cannot be null.");
        }
        if (commonSplitSize < 0) {
            // Splits can technically be 0, but not negative.
            throw new IllegalArgumentException("commonSplitSize cannot be negative.");
        }
        this.camera = camera;
        this.internalBatch = new SpriteBatch(); // Create internal batch

        // --- Load content texture and setup tiling ---
        this.contentTexture = new Texture(Gdx.files.internal(contentTexturePath));
        this.contentTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.tiledRegion = new TextureRegion(this.contentTexture);

        // --- Load mask NinePatch ---
        // Use the constructor with explicit splits, providing commonSplitSize for all four values.
        Texture maskTexture = new Texture(Gdx.files.internal(maskNinePatchPath));
        this.maskNinePatch = new NinePatch(maskTexture, commonSplitSize, commonSplitSize, commonSplitSize, commonSplitSize);

        // --- Load border NinePatch ---
        // Use the constructor with explicit splits, providing commonSplitSize for all four values.
        Texture borderTexture = new Texture(Gdx.files.internal(borderNinePatchPath));
        this.borderNinePatch = new NinePatch(borderTexture, commonSplitSize, commonSplitSize, commonSplitSize, commonSplitSize);
    }

    /**
     * Draws the tiled background, clipped by the mask NinePatch, and overlaid with the border NinePatch.
     * <p>
     * This method handles its own SpriteBatch begin/end, projection matrix setting using the provided Camera,
     * and stencil buffer clearing.
     * </p>
     * <p>
     * <strong>Note on Efficiency:</strong> Clearing the stencil buffer within every individual draw call
     * (as done here for encapsulation) can be inefficient if drawing many distinct ClipDrawer instances
     * per frame. If performance becomes critical in such scenarios, consider managing stencil clearing
     * externally (once per frame) and using a shared SpriteBatch version of this drawer.
     * </p>
     *
     * @param position Bottom-left corner coordinates (x, y) for drawing the element. Must not be null.
     * @param width    The desired width of the drawn element. Must be positive.
     * @param height   The desired height of the drawn element. Must be positive.
     */
    public void draw(Vector2 position, float width, float height) {
        if (position == null) {
            throw new IllegalArgumentException("Position vector cannot be null.");
        }
        draw(position.x, position.y, width, height);
    }

    /**
     * Draws the tiled background, clipped by the mask NinePatch, and overlaid with the border NinePatch.
     * <p>
     * See {@link #draw(Vector2, float, float)} for notes on usage and efficiency.
     * </p>
     *
     * @param x      The x-coordinate of the bottom-left corner.
     * @param y      The y-coordinate of the bottom-left corner.
     * @param width  The desired width of the drawn element. Must be positive.
     * @param height The desired height of the drawn element. Must be positive.
     */
    public void draw(float x, float y, float width, float height) {
        if (width <= 0 || height <= 0) {
            return; // Nothing to draw
        }

        GL20 gl = Gdx.gl;

        // --- 0. Clear Stencil Buffer ---
        gl.glClearStencil(0);
        gl.glClear(GL20.GL_STENCIL_BUFFER_BIT); // Only clear stencil

        // --- Begin Drawing ---
        internalBatch.setProjectionMatrix(camera.combined);
        internalBatch.begin();
        internalBatch.setColor(Color.WHITE); // Ensure default white color
        internalBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Ensure standard alpha blending
        internalBatch.setProjectionMatrix(camera.combined);


        // --- Pass 1: Draw Mask NinePatch to Stencil Buffer ---
        _enableStencilWrite(gl);
        maskNinePatch.draw(internalBatch, x, y, width, height);
        internalBatch.flush();
        _enableStencilRead(gl);

        // --- Pass 2: Draw Tiled Content using Stencil Mask ---
        _drawTiledContent(x, y, width, height);
        internalBatch.flush();
        _disableStencil(gl);

        // --- Pass 3: Draw Border NinePatch On Top ---
        borderNinePatch.draw(internalBatch, x, y, width, height);

        // --- End Drawing ---
        internalBatch.end();
    }

    /** Configures OpenGL state to write to the stencil buffer (Pass 1). */
    private void _enableStencilWrite(GL20 gl) {
        gl.glEnable(GL20.GL_STENCIL_TEST);
        gl.glStencilFunc(GL20.GL_ALWAYS, 1, 0xFF);
        gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_REPLACE);
        gl.glColorMask(false, false, false, false);
    }

    /** Configures OpenGL state to draw using the stencil buffer as a mask (Pass 2). */
    private void _enableStencilRead(GL20 gl) {
        gl.glColorMask(true, true, true, true);
        gl.glStencilFunc(GL20.GL_EQUAL, 1, 0xFF);
        gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);
    }

    /** Disables the stencil test (after Pass 2). */
    private void _disableStencil(GL20 gl) {
        gl.glDisable(GL20.GL_STENCIL_TEST);
    }

    /** Calculates tiling UVs and draws the content texture region using the internal batch. */
    private void _drawTiledContent(float x, float y, float width, float height) {
        float u = x / (float) contentTexture.getWidth();
        float v = y / (float) contentTexture.getHeight();
        float u2 = (x + width) / (float) contentTexture.getWidth();
        float v2 = (y + height) / (float) contentTexture.getHeight();
        tiledRegion.setRegion(u, v, u2, v2);
        internalBatch.draw(tiledRegion, x, y, width, height); // Use internalBatch
    }

    /**
     * Releases resources (Textures, internal SpriteBatch) loaded/created by this instance.
     * The Camera passed in the constructor is NOT disposed.
     */
    @Override
    public void dispose() {
        if (internalBatch != null) {
            internalBatch.dispose(); // Dispose internal batch
        }
        // Dispose textures only if this instance loaded and owns them
        if (ownsTextureContent && contentTexture != null) {
            contentTexture.dispose();
        }
        // NinePatch doesn't own its texture, so get and dispose it if we own it
        if (ownsTextureMask && maskNinePatch != null && maskNinePatch.getTexture() != null) {
            maskNinePatch.getTexture().dispose();
        }
        if (ownsTextureBorder && borderNinePatch != null && borderNinePatch.getTexture() != null) {
            borderNinePatch.getTexture().dispose();
        }
        // The Camera is external and not disposed here.
    }
}

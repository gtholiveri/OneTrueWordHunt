package com.TrueWordHunt.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class StyleGenerator implements Disposable {

    private NinePatchDrawable infoTableBGDrawable;

    // constructor
    public StyleGenerator() {
        infoTableBGDrawable = null;
    }

    // image text button style maker
    public VisImageTextButton.VisImageTextButtonStyle createImageTextButtonStyle(int fontSize, String fontFilePath, Color textColor) {
        // Generate the font using the provided parameters
        BitmapFont buttonFont = generateFont(fontSize, fontFilePath, textColor);

        // Create a drawable for the button background using tileTexture.png
        Texture texture = new Texture(Gdx.files.internal("tileTexture.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Optional: For crisp scaling

        // Create a TextureRegionDrawable to apply the background texture
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(texture));


        // Create a new VisImageTextButtonStyle and set the font, background, and other button styles
        VisImageTextButton.VisImageTextButtonStyle style = new VisImageTextButton.VisImageTextButtonStyle();
        style.font = buttonFont;
        style.up = background; // Set the up state background
        style.down = background; // Optionally set a different background for the "down" state if needed
        style.checked = background; // Optionally set a different background for the "checked" state
        style.over = background; // Optionally set a background for the "over" (hovered) state if needed

        return style;

    }

    public NinePatchDrawable getTableBG() {
        if (infoTableBGDrawable == null) {
            initializeTableBG("infotablebackground.png");
        }

        return infoTableBGDrawable;
    }

    private void initializeTableBG(String imagePath) {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // For crisp scaling

        NinePatch ninePatch = new NinePatch(texture, 0, 0, 0, 0);


        infoTableBGDrawable = new NinePatchDrawable(ninePatch);
    }

    public VisTextButton.VisTextButtonStyle createTextButtonStyle(int fontSize, String fontFilePath, Color color) {

        BitmapFont buttonFont = generateFont(fontSize, fontFilePath, color);

        // Get VisUI's default button style as base
        VisTextButton.VisTextButtonStyle buttonStyle = new VisTextButton.VisTextButtonStyle(VisUI.getSkin().get(VisTextButton.VisTextButtonStyle.class));


        // Apply our custom font
        buttonStyle.font = buttonFont;
        //buttonStyle.downFontColor = color.cpy().mul(0.7f); // Darker when pressed
        //buttonStyle.overFontColor = color.cpy().mul(1.1f); // Brighter when hovered

        return buttonStyle;
    }

    public Label.LabelStyle createLabelStyle(int fontSize, String fontFileName, Color color) {

        BitmapFont customFont = generateFont(fontSize, fontFileName, color);

        // Get the default style as a base
        Label.LabelStyle style = new Label.LabelStyle(VisUI.getSkin().get(Label.LabelStyle.class));

        // Only override the font and color
        style.font = customFont;
        style.fontColor = color;

        return style;
    }

    private BitmapFont generateFont(int fontSize, String fontFileName, Color color) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + fontFileName));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = fontSize;
        params.color = color;

        BitmapFont customFont = fontGenerator.generateFont(params);
        fontGenerator.dispose(); // Important to avoid memory leaks

        return customFont;
    }

    @Override
    public void dispose() {
        infoTableBGDrawable.getPatch().getTexture().dispose();
    }

}

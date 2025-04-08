package com.TrueWordHunt.Game;

import com.TrueWordHunt.Util.Position;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameRenderer implements Disposable {
    private ShapeRenderer shapeRenderer;

    private GameScreen screen;
    private GameStage gameStage;

    private SpriteBatch batch;
    private TextureRegion region;
    private ShapeDrawer drawer;


// three states: valid word (green), possibly valid word (valid prefix) (white), and impossible word (not a prefix + not a word)

    public GameRenderer(GameScreen screen, GameStage gameStage) {
        shapeRenderer = new ShapeRenderer();

        this.screen = screen;
        this.gameStage = gameStage;

        batch = new SpriteBatch();
        region = new TextureRegion(new Texture(Gdx.files.internal("whitepixel.png")));

        drawer = new ShapeDrawer(batch, region);
    }

    public void renderBoard() {
        int pathLen = screen.getGameEngine().getCurrPath().size();
        if (pathLen != 0) {
            float[] vertices = new float[2 * pathLen];
            Array<Vector2> vertices2 = new Array<>();

            // need an array of all the positions (vertices)
            // need to go from the POSITIONS that we find in engine currPath to
            // accessing the getCenterX() and getCenterY() from the Tile[][] in gameStage

            for (Position p : screen.getGameEngine().getCurrPath()) {
                Tile currTile = gameStage.getTiles()[p.getRow()][p.getCol()];

                Vector2 stageCoords = currTile.localToStageCoordinates(new Vector2(currTile.getCenterX(), currTile.getCenterY()));
                vertices2.add(stageCoords);
            }


            if (vertices.length >= 4) {
                renderPolyLine(vertices2);
            }
        }
    }

    public void renderPolyLine(Array<Vector2> points) {
        batch.setProjectionMatrix(gameStage.getCamera().combined);
        batch.begin();
        drawer.setColor(getColor());


        drawer.path(points, 15f, JoinType.SMOOTH, true);


        batch.end();

    }

    private Color getColor() {
        String currWord = screen.getGameEngine().getCurrWord();
        GameEngine engine = screen.getGameEngine();



        if (engine.getWordsFound().contains(currWord)) {
            return new Color(1f, 1f, 0f, 0.5f);
        } else if (engine.isWord(currWord)) {
            // this is a valid word! color line green
            return new  Color(0f, 1.0f, 0f, 0.5f);
        } else if (screen.getGameEngine().startsWord(currWord)) {
            // this is a prefix of some valid word! set color white
            return new Color(1f, 1f, 1f, 0.5f);
        } else {
            // there's no way for this to be a valid word
            // set color red
            return new Color(1f, 0f, 0f, 0.5f);
        }
    }


    /**
     *
     * @param vertices
     * List of points [x0, y0, x1, y1, x2, y2, ...] through which to draw lines
     */
    public void renderPolyLine(float[] vertices) {

        batch.setProjectionMatrix(gameStage.getCamera().combined);
        batch.begin();
        drawer.setDefaultLineWidth(15f);

        drawer.setColor(getColor());


        for (int i = 0; i < vertices.length - 2; i += 2) {
            float x1 = vertices[i];
            float y1 = vertices[i+1];
            float x2 = vertices[i+2];
            float y2 = vertices[i+3];

            drawer.line(x1, y1, x2, y2);

        }



        batch.end();

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();

            region.getTexture().dispose();
    }

}

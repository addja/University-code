package GameObjectsMod;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import oteroavalle.daniel.asteroids.Asteroids;

/**
 * Class that implements an Asteroid for the classic game mode
 */
public class AsteroidMod extends GameObjectMod {

    private int type;
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    // score for breaking the asteroid
    private int score;

    // to generate the asteroid form
    private int numPoints;
    private float[] dists;
    private Random rand = new Random();

    private boolean remove;

    /**
     * Constructor number 1
     * @param  x    position on the canvas x edge
     * @param  y    position on the canvas y edge
     * @param  type type of game in witch the asteroid will be spawned
     * @return      Asteroid
     */
    public AsteroidMod(float x, float y, int type) {

        this.x = x;
        this.y = y;
        this.type = type;

        if (type == SMALL) {
            numPoints = 8;
            width = height = (int) Asteroids.HEIGHT/35;
            speed = rand.nextFloat() *
                    ((Asteroids.HEIGHT-80)/6.85f - (Asteroids.HEIGHT-80)/9.6f) + (Asteroids.HEIGHT-80)/6.85f;
            score = 100;
        }
        else if (type == MEDIUM) {
            numPoints = 10;
            width = height = (int) Asteroids.HEIGHT/20;
            speed = rand.nextFloat() *
                    ((Asteroids.HEIGHT-80)/12 - (Asteroids.HEIGHT-80)/16) + (Asteroids.HEIGHT-80)/12;
            score = 50;
        }
        else if (type == LARGE) {
            numPoints = 12;
            width = height = (int) Asteroids.HEIGHT/10;
            speed = rand.nextFloat() *
                    ((Asteroids.HEIGHT-80)/24 - (Asteroids.HEIGHT-80)/48) + (Asteroids.HEIGHT-80)/24;
            score = 20;
        }

        // movement controlers
        rotationSpeed = rand.nextFloat() * 2 - 1;
        radians = rand.nextFloat() * ((Asteroids.HEIGHT-80)/240 * 3.1415f)
                + (Asteroids.HEIGHT-80)/240 * 3.1415f;
        dx = (float)Math.cos(radians) * speed;
        dy = (float)Math.sin(radians) * speed;

        // shape setters
        shapex = new float[numPoints];
        shapey = new float[numPoints];
        dists = new float[numPoints];

        // generate shape
        int radius = width / 2;
        for (int i = 0; i < numPoints; 	++i) {
            dists[i] = rand.nextFloat() * (radius / 2 - radius) + radius;
        }
        setShape();
    }

    /**
     * Constructor number 2
     * @param  x    position on the canvas x edge
     * @param  y    position on the canvas y edge
     * @param  dx   vector of movement on the x edge
     * @param  dy   vector of movement on the y edge
     * @param  type type of game in witch the asteroid will be spawned
     * @return      Asteroid
     */
    public AsteroidMod(float x, float y, float dx, float dy, int type) {

        this.x = x;
        this.y = y;
        this.type = type;

        if (type == SMALL) {
            numPoints = 8;
            width = height = (int) Asteroids.HEIGHT/35;
            speed = rand.nextFloat() *
                    ((Asteroids.HEIGHT-80)/6.85f - (Asteroids.HEIGHT-80)/9.6f) + (Asteroids.HEIGHT-80)/6.85f;
            score = 100;
        }
        else if (type == MEDIUM) {
            numPoints = 10;
            width = height = (int) Asteroids.HEIGHT/20;
            speed = rand.nextFloat() *
                    ((Asteroids.HEIGHT-80)/12 - (Asteroids.HEIGHT-80)/16) + (Asteroids.HEIGHT-80)/12;
            score = 50;
        }
        else if (type == LARGE) {
            numPoints = 12;
            width = height = (int) Asteroids.HEIGHT/10;
            speed = rand.nextFloat() *
                    ((Asteroids.HEIGHT-80)/24 - (Asteroids.HEIGHT-80)/48) + (Asteroids.HEIGHT-80)/24;
            score = 20;
        }

        // movement controlers
        rotationSpeed = rand.nextFloat() * 2 - 1;
        radians = rand.nextFloat() * ((Asteroids.HEIGHT-80)/240 * 3.1415f)
                + (Asteroids.HEIGHT-80)/240 * 3.1415f;
        this.dx = dx;
        this.dy = dy;

        // shape setters
        shapex = new float[numPoints];
        shapey = new float[numPoints];
        dists = new float[numPoints];

        // generate shape
        int radius = width / 2;
        for (int i = 0; i < numPoints; 	++i) {
            dists[i] = rand.nextFloat() * (radius / 2 - radius) + radius;
        }
        setShape();
    }

    /**
     * Getter of the type of game in wich the asteroid will be spawned
     * @return type of game in wich the asteroid will be spawned
     */
    public int getType() { return type; }

    /**
     * Getter of the score related with the asteroid
     * @return the score related with the asteroid
     */
    public int getScore() { return score; }

    /**
     * Says if the asteroid is no longer correct
     * @return if the asteroid is correct or not
     */
    public boolean shouldRemove() { return remove; }

    /**
     * Updates the current state of the asteroid on the game
     * @param dt delta time
     */
    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;

        radians += rotationSpeed * dt;
        setShape();

        wrap();
    }

    /**
     * Draws the asteroid on the canvas
     * @param  c Canvas in wich the asteroid will be written
     * @return   the input canvas with the asteroid drawn on it
     */
    public Canvas draw(Canvas c) {

        // color white
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // draw shape
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            c.drawLine(shapex[i], shapey[i], shapex[j], shapey[j], paint);
        }

        return c;
    }

    private void setShape() {
        float angle = 0;
        for (int i = 0; i < numPoints; ++i) {
            shapex[i] = x + (float)Math.cos(angle + radians) * dists[i];
            shapey[i] = y + (float)Math.sin(angle + radians) * dists[i];
            angle += 2 * 3.1415f / numPoints;
        }
    }
    
}
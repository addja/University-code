package GameObjectsMod;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Random;

import GameObjects.Asteroid;
import oteroavalle.daniel.asteroids.Asteroids;

/**
 * Class that implements a ship that is controled by the player
 */
public class PlayerMod extends GameObjectMod {

    // bullets
    private ArrayList<BulletMod> bullets;

    // rocket boosters
    private float[] flamex;
    private float[] flamey;

    // position control
    private boolean drag;
    private float acx, acy;

    // movement control
    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float acceleratingTimer;

    // state control
    private float hitTimer;
    private float hitTime;
    private boolean hit;
    private boolean dead;
    private float hitvar;

    private long score;
    private int extraLives;
    private long scoreExtraLife;

    private Random rand = new Random();

    /**
     * Constructor
     * @param  bullets list of bullets
     * @return         Player
     */
    public PlayerMod(ArrayList<BulletMod> bullets) {

        // player centred
        x = Asteroids.WIDTH / 2;
        y = (Asteroids.HEIGHT-80) / 2;

        maxSpeed = (Asteroids.HEIGHT-80)/2;
        acceleration = (Asteroids.HEIGHT-80)/3;
        deceleration = (Asteroids.HEIGHT-80)/48;

        shapex = new float[4];
        shapey = new float[4];
        flamex = new float[3];
        flamey = new float[3];

        // to put the figure look up
        radians = 3.1415f / 2;
        rotationSpeed = (Asteroids.HEIGHT-80)/160;

        this.bullets = bullets;

        hit = dead = false;
        hitTime = 1;
        hitTimer = 0;

        score = 0;
        extraLives = 3;
        scoreExtraLife = 10000;
        setShape();
    }

    /**
     * Rotates 180 degrees the ship, used to print the lives on the game hud
     */
    public void inverseShape() {
        shapex[0] = (x - (float)Math.cos(radians) * Asteroids.HEIGHT/50);
        shapey[0] = (y - (float)Math.sin(radians) * Asteroids.HEIGHT/50);

        shapex[1] = x - (float)Math.cos(radians - 4 * 3.1415f / 5) * Asteroids.HEIGHT/50;
        shapey[1] = y - (float)Math.sin(radians - 4 * 3.1445f / 5) * Asteroids.HEIGHT/50;

        shapex[2] = x - (float)Math.cos(radians + 3.1415f) * Asteroids.HEIGHT/70;
        shapey[2] = y - (float)Math.sin(radians + 3.1415f) * Asteroids.HEIGHT/70;

        shapex[3] = x - (float)Math.cos(radians + 4 * 3.1415f / 5) * Asteroids.HEIGHT/50;
        shapey[3] = y - (float)Math.sin(radians + 4 * 3.1415f / 5) * Asteroids.HEIGHT/50;
    }

    /**
     * Resets the the Player to default values, used when the player dies
     */
    public void reset() {
        x = Asteroids.WIDTH / 2;
        y = (Asteroids.HEIGHT-80) / 2;
        setShape();
        hit = dead = false;
        hitvar = 0;
    }

    /**
     * Creates a bullet simulating a shoot
     */
    public void shoot() {
        if (hit) return;
        bullets.add(new BulletMod(x, y, radians));
    }

    /**
     * Rotates the player ship a given amount of angles
     * @param angle angles in radians with wich the player ship will be rotates
     */
    public void rotate(float angle) {
        radians += angle;
    }

    /**
     * Handles the drag event, used to make the ship move forward
     */
    public void drag() {
        drag = true;
    }

    /**
     * Updates the player according to the game
     * @param dt delta time
     */
    public void update(float dt) {

        // check if hit
        if (hit) {
            hitTimer += dt;
            if (hitTimer > hitTime) {
                dead = true;
                hitTimer = 0;
            }
            hitvar += dt * 10;
            return; // we don't want to update more
        }

        // check extra lives
        if (score  >= scoreExtraLife) {
            ++extraLives;
            scoreExtraLife += 10000;
        }

        // accelerating
        if (drag) {
            dx += (float)Math.cos(radians) * acceleration * dt;
            dy += (float)Math.sin(radians) * acceleration * dt;
            acceleratingTimer += dt;
            if (acceleratingTimer > 0.1f) acceleratingTimer = 0;
        }
        else acceleratingTimer = 0;

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // set flame
        if (drag) setFlame();

        // screen wrap
        wrap();

    }

    /**
     * Draws the player ship on the canvas
     * @param  c Canvas in wich the player will be drawn
     * @return   the input canvas with the player ship drawn on it
     */
    public Canvas draw(Canvas c) {

        // color white
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        // draw ship destruction
        if (hit) {
            for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
                float var = rand.nextFloat() * hitvar;
                c.drawLine(shapex[i]-var, shapey[i]-var,
                        shapex[j]+var, shapey[j]+var, paint);
            }
            return c; // we don't want to continue drawing
        }

        // draw ship
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            c.drawLine(shapex[i], shapey[i], shapex[j], shapey[j], paint);
        }

        // draw flame
        if (drag) {
            for (int i = 0, j = flamex.length - 1; i < flamex.length; j = i++) {
                c.drawLine(flamex[i], flamey[i], flamex[j], flamey[j], paint);
            }
            drag = false;
        }

        return c;
    }

    /**
     * Tells the Player state that has been hit
     */
    public void hit() {
        if (hit) return;
        hit = true;
        dx = dy = 0;
    }

    /**
     * Sets the player ship in a given position
     * @param x postion in the canvas x edge where the shape will be set
     * @param y postion in the canvas y edge where the shape will be set
     */
    public void setPosition(float x, float y) {
        super.setPosition(x,y);
        setShape();
    }

    /**
     * Tells the player state that it has lost a life
     */
    public void loseLife() { extraLives--; }
    
    /**
     * Increments to the player current score a given value
     * @param l value that will be added to the player score
     */
    public void incrementScore(long l) { score += l; }
    
    /**
     * Checks if the player is hit
     * @return true if the player is hit, false otherwise
     */
    public boolean isHit() { return hit; }
    
    /**
     * Checks is the player is dead
     * @return true if the player is dead, false otherwise
     */
    public boolean isDead() { return dead; }

    /**
     * Getter of the player score
     * @return the player score
     */
    public long getScore() { return score; }

    /**
     * Getter of the player extra lives
     * @return the player extra live
     */
    public int getExtraLives() { return extraLives; }

    private void setShape() {
        shapex[0] = (x + (float)Math.cos(radians) * Asteroids.HEIGHT/50);
        shapey[0] = (y + (float)Math.sin(radians) * Asteroids.HEIGHT/50);

        shapex[1] = x + (float)Math.cos(radians - 4 * 3.1415f / 5) * Asteroids.HEIGHT/50;
        shapey[1] = y + (float)Math.sin(radians - 4 * 3.1445f / 5) * Asteroids.HEIGHT/50;

        shapex[2] = x + (float)Math.cos(radians + 3.1415f) * Asteroids.HEIGHT/70;
        shapey[2] = y + (float)Math.sin(radians + 3.1415f) * Asteroids.HEIGHT/70;

        shapex[3] = x + (float)Math.cos(radians + 4 * 3.1415f / 5) * Asteroids.HEIGHT/50;
        shapey[3] = y + (float)Math.sin(radians + 4 * 3.1415f / 5) * Asteroids.HEIGHT/50;
    }

    private void setFlame() {
        flamex[0] = x + (float)Math.cos(radians - 5 * 3.1415f / 6) * Asteroids.HEIGHT/70;
        flamey[0] = y + (float)Math.sin(radians - 5 * 3.1415f / 6) * Asteroids.HEIGHT/70;

        flamex[1] = x + (float)Math.cos(radians - 3.1415f) *
                (Asteroids.HEIGHT/50 + acceleratingTimer * 50);
        flamey[1] = y + (float)Math.sin(radians - 3.1415f) *
                (Asteroids.HEIGHT/50 + acceleratingTimer * 50);

        flamex[2] = x + (float)Math.cos(radians + 5 * 3.1415f / 6) * Asteroids.HEIGHT/70;
        flamey[2] = y + (float)Math.sin(radians + 5 * 3.1415f / 6) * Asteroids.HEIGHT/70;
    }
}

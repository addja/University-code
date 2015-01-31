package GameObjectsMod;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import GameObjects.Asteroid;
import oteroavalle.daniel.asteroids.Asteroids;

/**
 * Class that implements a Bullet for the classic game mode
 */
public class BulletMod extends GameObjectMod {

    private float lifeTime;
    private float lifeTimer;

    private boolean remove;

    /**
     * Constructor
     * @param  x       position on the canvas x edge
     * @param  y       position on the canvas y edge
     * @param  radians angle in with the bulled has been shot
     * @return         Bullet
     */
    public BulletMod(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;

        float speed = (Asteroids.HEIGHT-80)*1.2f;
        dx = (float)Math.cos(radians) * speed;
        dy = (float)Math.sin(radians) * speed;

        lifeTimer = 0;
        lifeTime = Asteroids.WIDTH/(Asteroids.HEIGHT-80);
    }

    /**
     * Says if the bullet is no longer correct
     * @return if the bullet is correct or not
     */
    public boolean shouldRemove() { return remove; }

    /**
     * Updates the current state of the bullet on the game
     * @param dt delta time
     */
    public void update(float dt) {

        x += dx * dt;
        y += dy * dt;

        wrap();

        lifeTimer += dt;
        if (lifeTimer > lifeTime) remove = true;
    }

    /**
     * Draws the bullet on the canvas
     * @param  c Canvas in wich the bullet will be written
     * @return   the input canvas with the bullet drawn on it
     */
    public Canvas draw(Canvas c) {

        // color white
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);


        // draw bullet
        c.drawCircle(x, y, (Asteroids.HEIGHT-80) / 400, paint);

        return c;
    }
}

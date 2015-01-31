package oteroavalle.daniel.asteroids;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import GameObjects.Asteroid;
import GameObjectsMod.AsteroidMod;
import GameObjectsMod.BulletMod;
import GameObjectsMod.PlayerMod;

/**
 * Class that implements the game engine for the classic type play
 */
public class GameEngineMod extends SurfaceView implements Runnable{

    private Thread t = null;
    private SurfaceHolder holder;
    private boolean isItOk = false;

    private boolean left, right, up, shoot;

    private PlayerMod player;
    private PlayerMod hudPlayer = new PlayerMod(null);
    private ArrayList<BulletMod> bullets;
    private ArrayList<AsteroidMod> asteroids;
    private int level;
    private Random rand = new Random();
    private boolean gameOver;
    private ArrayList<Integer> highClassic;
    private ArrayList<Integer> highCascade;
    private ArrayList<Integer> highEntropy;
    private SharedPreferences sharedPrefs;

    private Typeface tf;
    private float testTextSize;
    private int mode;

    private Bitmap menuic;
    private Bitmap leftic;
    private Bitmap rightic;
    private Bitmap upic;
    private Bitmap shootic;

    /**
     * Constructor
     * @param  context Android context
     * @param  cl      list of classic highscores
     * @param  ca      list of cascade highscores
     * @param  e       list of entropy highscores
     * @param  mode    type of game
     * @return         GameEngine
     */
    public GameEngineMod(Context context, ArrayList<Integer> cl,
                         ArrayList<Integer> ca, ArrayList<Integer> e, int mode) {
        super(context);
        highClassic = cl;
        highCascade = ca;
        highEntropy = e;
        holder = getHolder();
        bullets = new ArrayList<BulletMod>();
        asteroids = new ArrayList<AsteroidMod>();
        player = new PlayerMod(bullets);
        level = 1;
        spawnAsteroids();
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Hyperspace Bold Italic.ttf");
        gameOver = false;
        menuic = BitmapFactory.decodeResource(getResources(), R.drawable.menubutton);
        leftic = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        rightic = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        upic = BitmapFactory.decodeResource(getResources(), R.drawable.up);
        shootic = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mode = mode;
        left = right = up = shoot = false;
        resume();
    }

    /**
     * Handles slide events
     * @param x coordinate of the x edge on the screen
     * @param y coordinate of the y edge on the screen
     */
    public void slide(float x, float y) {
        float maxX = Asteroids.WIDTH;
        float maxY = Asteroids.HEIGHT;

        if (x < 5 + leftic.getWidth() && x > 5 &&
                y < maxY && y > maxY - 80) {
            if (!left) touchUp();
        }
        else if (x < 10 + rightic.getWidth() + leftic.getWidth() && x > 10 + leftic.getWidth() &&
                y < maxY && y > maxY - 80) {
            if (!right) touchUp();
        }
        else if (x < maxX - 5 && x > maxX - shootic.getWidth() - 10 &&
                y < maxY && y > maxY - 80) {
            if (!shoot) touchUp();
        }
        else if (x < maxX- 10 - upic.getWidth() &&
                x > maxX - 10 - shootic.getWidth() - upic.getWidth() &&
                y < maxY && y > maxY - 80) {
            if (!up) touchUp();
        }
        else touchUp();
    }

    /**
     * Handles touch events on press
     * @param x coordinate of the x edge on the screen
     * @param y coordinate of the y edge on the screen
     */
    public void touchDown(float x, float y) {

        float maxX = Asteroids.WIDTH;
        float maxY = Asteroids.HEIGHT;

        if (x < leftic.getWidth()*1/3 +leftic.getWidth() &&
                x > leftic.getWidth()*1/3 &&
                y > Asteroids.HEIGHT-leftic.getHeight()) left = true;
        else if (x < leftic.getWidth()*2/3 +leftic.getWidth() + rightic.getWidth() &&
                x > leftic.getWidth()*2/3 +leftic.getWidth() &&
                y > Asteroids.HEIGHT-rightic.getHeight()) right = true;
        else if (x < Asteroids.WIDTH - shootic.getWidth()*1/3 &&
                x > Asteroids.WIDTH - shootic.getWidth()*2/3 -shootic.getWidth() &&
                y > Asteroids.HEIGHT-shootic.getHeight()) shoot = true;
        else if (x < Asteroids.WIDTH - upic.getWidth()*1/3 &&
                x > Asteroids.WIDTH - leftic.getWidth()*1/3 - shootic.getWidth() -upic.getWidth() &&
                y > Asteroids.HEIGHT-upic.getHeight()) up = true;
    }

    /**
     * Handles touch events on release
     */
    public void touchUp() {
        left = right = up = shoot = false;
    }

    /**
     * Set up everything when the app is paused
     */
    public void pause() {
        isItOk = false;
        while (true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    /**
     * Set up everything when the app is stopped
     */
    public void stop() {
        isItOk = false;
        while (true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
    }

    /**
     * Set up everything when the app is resumed
     */
    public void resume() {
        isItOk = true;
        t = new Thread(this);
        t.start();
    }

    /**
     * Checks if the menu button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the menu button has been pressed, false otherwise
     */
    public boolean menuPressed(float x, float y) {
        float maxX = Asteroids.WIDTH;
        float maxY = Asteroids.HEIGHT;
        if (x < maxX / 2 + menuic.getWidth()/2 && x > maxX / 2 - menuic.getWidth()/2 &&
                y < 0 + menuic.getHeight()) {
            return true;
        }
        return false;
    }

    /**
     * Getter of the game score
     * @return game score
     */
    public Integer getScore() { return (int) (long) player.getScore(); }

    /**
     * Does the work
     */
    public void run() {
        while (isItOk) {

            float dt = 0.01f; // delta time to 0.01 sec;
            update(dt);
            if (!holder.getSurface().isValid()) continue;
            Canvas c = holder.lockCanvas();
            c.drawColor(Color.BLACK); // clean the screen
            c.drawARGB(0,0,0,0); // paint background
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Rect bounds;
            String s;

            if (!gameOver) {

                c.drawLine(0, c.getHeight() - upic.getHeight(),
                        c.getWidth(), c.getHeight() - upic.getHeight(), paint);

                // draw back to menu icon
                c.drawBitmap(menuic, c.getWidth()/2 - menuic.getWidth()/2, 0, null);

                // draw control icons
                c.drawBitmap(leftic, leftic.getWidth()*1/3,
                        c.getHeight() - leftic.getHeight(), null);
                c.drawBitmap(rightic,leftic.getWidth() + leftic.getWidth()*2/3,
                        c.getHeight() - rightic.getHeight(), null);
                c.drawBitmap(shootic, c.getWidth() - shootic.getWidth() - shootic.getWidth()*1/3,
                        c.getHeight() - shootic.getHeight(), null);
                c.drawBitmap(upic, c.getWidth() - upic.getWidth()
                                - shootic.getWidth()*2/3 - shootic.getWidth(),
                        c.getHeight() - upic.getHeight(), null);


                //draw player
                c = player.draw(c);

                // draw bullets
                for (int i = 0; i < bullets.size(); ++i) bullets.get(i).draw(c);

                // draw asteroids
                for (int i = 0; i < asteroids.size(); ++i) asteroids.get(i).draw(c);

                // draw current lives
                for (int i = 0; i < player.getExtraLives(); i++) {
                    hudPlayer.setPosition(Asteroids.WIDTH - Asteroids.WIDTH *1/20 * i
                            - Asteroids.WIDTH *2/20, Asteroids.HEIGHT* 1/20);
                    hudPlayer.inverseShape();
                    hudPlayer.draw(c);
                }

                // draw score
                testTextSize = Asteroids.HEIGHT/20;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                c.drawText(String.valueOf(player.getScore()),
                        Asteroids.WIDTH *1/20,
                        Asteroids.HEIGHT *1/15, paint);
            }
            else {
                bounds = new Rect();

                // check if highscore
                if (checkScore(player.getScore())) {
                    s = "GAME OVER";
                    testTextSize = Asteroids.HEIGHT/13;
                    paint.setTextSize(testTextSize);
                    paint.setTypeface(tf);
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                            Asteroids.HEIGHT *1/3, paint
                    );
                    s = "Congratulations!";
                    testTextSize = Asteroids.HEIGHT/19;
                    paint.setTextSize(testTextSize);
                    paint.setTypeface(tf);
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                            Asteroids.HEIGHT / 2 - bounds.height(), paint
                    );
                    testTextSize = Asteroids.WIDTH/20;
                    paint.setTextSize(testTextSize);
                    s = " Your score: " + String.valueOf(player.getScore() + " is one");
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                            Asteroids.HEIGHT *6/10 - bounds.height(), paint
                    );
                    testTextSize = Asteroids.WIDTH/20;
                    paint.setTextSize(testTextSize);
                    s = "of the five best scores";
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                            Asteroids.HEIGHT *6/10 + bounds.height(), paint
                    );
                }
                else{  // if no highscore
                    s = "GAME OVER";
                    testTextSize = Asteroids.HEIGHT/16;
                    paint.setTextSize(testTextSize);
                    paint.setTypeface(tf);
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                            Asteroids.HEIGHT *1/3, paint
                    );
                    testTextSize = Asteroids.HEIGHT/24;
                    paint.setTextSize(testTextSize);
                    s = " Your score: " + String.valueOf(player.getScore());
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2 - 10,
                            Asteroids.HEIGHT / 2, paint
                    );

                }
                testTextSize = Asteroids.HEIGHT/36;
                paint.setTypeface(null);
                paint.setTextSize(testTextSize);
                s = "(Touch to go to menu)";
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                        Asteroids.HEIGHT *8/10, paint
                );

            }

            holder.unlockCanvasAndPost(c);
        }
    }

    private void spawnAsteroids() {
        asteroids.clear();
        int numAsteroids = 2 + level;

        // classic mode
        if (mode == 1) {
            for (int i = 0; i < numAsteroids; ++i) {
                float x = rand.nextFloat() * Asteroids.WIDTH;
                float y = rand.nextFloat() * (Asteroids.HEIGHT - 80);

                // to not spawn asteroids on top of the player
                float dx = x - player.getx();
                float dy = y - player.gety();
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                while (dist < 100) {
                    x = rand.nextFloat() * Asteroids.WIDTH;
                    y = rand.nextFloat() * (Asteroids.HEIGHT - 80);
                    dx = x - player.getx();
                    dy = y - player.gety();
                    dist = (float) Math.sqrt(dx * dx + dy * dy);
                }

                asteroids.add(new AsteroidMod(x, y, AsteroidMod.LARGE));
            }
        }

        // cascade mode
        if (mode == 2) {

            float speed = rand.nextFloat() * (20 - 10) + 10;

            for (int i = 0; i < numAsteroids; ++i) {
                float x = 50 + i * Asteroids.WIDTH / numAsteroids;
                float y = 50;

                float dx = (float)Math.cos(3.1415f/2) * speed;
                float dy = (float)Math.sin(3.1415f/2) * speed;

                asteroids.add(new AsteroidMod(x, y, dx, dy, AsteroidMod.LARGE));
            }
        }

        // entropy mode
        if (mode == 3) {

            float speed = rand.nextFloat() * (20 - 10) + 10;

            for (int i = 0; i < numAsteroids; ++i) {

                float x, y;

                if (i%4 == 0) {
                    x = rand.nextFloat() * Asteroids.WIDTH;
                    y = 50;
                }
                else if (i%4 == 1) {
                    x = rand.nextFloat() * Asteroids.WIDTH;
                    y = Asteroids.HEIGHT - 50 - 80;
                }
                else if (i%4 == 2) {
                    x = 50;
                    y = rand.nextFloat() * (Asteroids.HEIGHT - 80);
                }
                else {
                    x = Asteroids.WIDTH - 50;
                    y = rand.nextFloat() * (Asteroids.HEIGHT - 80);
                }

                float m = Asteroids.WIDTH /2;
                float n = (Asteroids.HEIGHT - 80) /2;
                float angle = (float) Math.atan((n-y)/(m-x));
                if (x > m) angle += 3.1415f;
                float dx = (float) Math.cos(angle) * speed;
                float dy = (float) Math.sin(angle) * speed;

                asteroids.add(new AsteroidMod(x, y, dx, dy, AsteroidMod.LARGE));
            }
        }
    }

    private void splitAsteroid(AsteroidMod a) {
        if (a.getType() == AsteroidMod.LARGE) {
            asteroids.add(new AsteroidMod(a.getx(), a.gety(), AsteroidMod.MEDIUM));
            asteroids.add(new AsteroidMod(a.getx(), a.gety(), AsteroidMod.MEDIUM));
            asteroids.add(new AsteroidMod(a.getx(), a.gety(), AsteroidMod.MEDIUM));
            asteroids.add(new AsteroidMod(a.getx(), a.gety(), AsteroidMod.MEDIUM));
        }
        if (a.getType() == AsteroidMod.MEDIUM) {
            asteroids.add(new AsteroidMod(a.getx(), a.gety(), AsteroidMod.SMALL));
            asteroids.add(new AsteroidMod(a.getx(), a.gety(), AsteroidMod.SMALL));
        }
    }

    private void checkCollisions() {

        // bullet asteroid
        for (int i = 0; i < bullets.size(); ++i) {
            BulletMod b = bullets.get(i);
            for (int j = 0; j < asteroids.size(); ++j) {
                AsteroidMod a = asteroids.get(j);
                if (a.contains(b.getx(), b.gety())) {
                    bullets.remove(i);
                    --i;
                    asteroids.remove(j);
                    --j;
                    splitAsteroid(a);
                    player.incrementScore(a.getScore());
                    break;
                }
            }
        }

        // player asteroid
        if (!player.isHit()) {
            for (int i = 0; i < asteroids.size(); ++i) {
                AsteroidMod a = asteroids.get(i);
                if (a.intersects(player)) {
                    player.hit();
                    asteroids.remove(i);
                    i--;
                    splitAsteroid(a);
                    break;
                }
            }
        }
    }


    private boolean checkScore(long sc) {
        if (mode == 1) {
            for (int i = 0; i < 5; i++) {
                if (highClassic.get(i) > sc) continue;
                return true;
            }
            return false;
        }
        // cascade
        else if (mode == 2) {
            for (int i = 0; i < 5; i++) {
                if (highCascade.get(i) > sc) continue;
                return true;
            }
            return false;
        }
        // entropy
        else {
            for (int i = 0; i < 5; i++) {
                if (highEntropy.get(i) > sc) continue;
                return true;
            }
        }
        return false;
    }

    private void update(float dt) {

        // checkCollisions
        checkCollisions();

        // handle touch events
        if (left) {
            player.rotate(-0.04f);
        }
        else if (right) {
            player.rotate(0.04f);
        }
        else if (up) {
            player.drag();
        }
        else if (shoot) {
            player.shoot();
            shoot = false;
        }

        // next level
        if (asteroids.size() == 0) {
            level++;
            spawnAsteroids();
        }

        // update player
        player.update(dt);
        if(player.isDead()) {
            if (player.getExtraLives() == 0) {
                Asteroids.gameOver();
                gameOver = true;
            }
            player.reset();
            player.loseLife();
            return;
        }

        // update bullets
        for (int i = 0; i < bullets.size(); ++i) {
            if (bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
            else bullets.get(i).update(dt);
        }

        // update asteroids
        for (int i = 0; i < asteroids.size(); ++i) {
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                --i;
            }
            else asteroids.get(i).update(dt);
        }
    }
}
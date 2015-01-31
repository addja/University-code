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
import GameObjects.Bullet;
import GameObjects.Player;

/**
 * Class that implements the game engine for the touch type play
 */
public class GameEngine extends SurfaceView implements Runnable{

    private Thread t = null;
    private SurfaceHolder holder;
    private boolean isItOk = false;

    private float touchx, touchy;
    private boolean touch;
    private float dragx, dragy;
    private boolean drag;

    private Player player;
    private Player hudPlayer = new Player(null);
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;
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

    /**
     * Constructor
     * @param  context Android context
     * @param  cl      list of classic highscores
     * @param  ca      list of cascade highscores
     * @param  e       list of entropy highscores
     * @param  mode    type of game
     * @return         GameEngine
     */
    public GameEngine(Context context, ArrayList<Integer> cl,
                      ArrayList<Integer> ca, ArrayList<Integer> e, int mode) {
        super(context);
        highClassic = cl;
        highCascade = ca;
        highEntropy = e;
        holder = getHolder();
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();
        player = new Player(bullets);
        level = 1;
        spawnAsteroids();
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Hyperspace Bold Italic.ttf");
        gameOver = false;
        menuic = BitmapFactory.decodeResource(getResources(), R.drawable.menubutton);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mode = mode;
        resume();
    }

    /**
     * Handles touch events
     * @param x coordinate of the x edge on the screen
     * @param y coordinate of the y edge on the screen
     */
    public void touch(float x, float y) {
        touchx = x;
        touchy = y;
        if (!drag) touch = true;
    }

    /**
     * Handles drag events
     * @param x coordinate of the x edge on the screen
     * @param y coordinate of the y edge on the screen
     */
    public void drag(float x, float y) {
        dragx = x;
        dragy = y;
        drag = true;
    }

    /**
     * Set up everything when the app is paused
     */
    public void pause() {
        isItOk = false;
        touch = false;
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
        touch  = false;
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
    public  void run() {
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

                // draw back to menu icon
                c.drawBitmap(menuic, c.getWidth()/2 - menuic.getWidth()/2, 0, null);

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
                    testTextSize = Asteroids.HEIGHT/24;
                    paint.setTextSize(testTextSize);
                    s = " Your score: " + String.valueOf(player.getScore() + " is one");
                    paint.getTextBounds(s, 0, s.length(), bounds);
                    c.drawText(
                            s, Asteroids.WIDTH / 2 - bounds.width() / 2,
                            Asteroids.HEIGHT *6/10 - bounds.height(), paint
                    );
                    testTextSize = Asteroids.HEIGHT/24;
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
                float y = rand.nextFloat() * Asteroids.HEIGHT;

                // to not spawn asteroids on top of the player
                float dx = x - player.getx();
                float dy = y - player.gety();
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                while (dist < 100) {
                    x = rand.nextFloat() * Asteroids.WIDTH;
                    y = rand.nextFloat() * Asteroids.HEIGHT;
                    dx = x - player.getx();
                    dy = y - player.gety();
                    dist = (float) Math.sqrt(dx * dx + dy * dy);
                }

                asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
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

                asteroids.add(new Asteroid(x, y, dx, dy, Asteroid.LARGE));
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
                    y = Asteroids.HEIGHT - 50;
                }
                else if (i%4 == 2) {
                    x = 50;
                    y = rand.nextFloat() * Asteroids.HEIGHT;
                }
                else {
                    x = Asteroids.WIDTH - 50;
                    y = rand.nextFloat() * Asteroids.HEIGHT;
                }

                float m = Asteroids.WIDTH /2;
                float n = Asteroids.HEIGHT /2;
                float angle = (float) Math.atan((n-y)/(m-x));
                if (x > m) angle += 3.1415f;
                float dx = (float) Math.cos(angle) * speed;
                float dy = (float) Math.sin(angle) * speed;

                asteroids.add(new Asteroid(x, y, dx, dy, Asteroid.LARGE));
            }
        }
    }

    private void splitAsteroid(Asteroid a) {
        if (a.getType() == Asteroid.LARGE) {
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
        }
        if (a.getType() == Asteroid.MEDIUM) {
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
        }
    }

    private void checkCollisions() {

        // bullet asteroid
        for (int i = 0; i < bullets.size(); ++i) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < asteroids.size(); ++j) {
                Asteroid a = asteroids.get(j);
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
                Asteroid a = asteroids.get(i);
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

    private void update(float dt) {

        // checkCollisions
        checkCollisions();

        // handle touch events
        if (touch) {
            player.touch(touchx, touchy);
            touch = false;
        }

        // handle drag events
        if (drag) {
            player.drag(dragx, dragy);
            drag = false;
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

}
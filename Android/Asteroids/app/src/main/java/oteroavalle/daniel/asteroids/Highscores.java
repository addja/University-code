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

/**
 * Class that controles the highscores screen
 */
public class Highscores extends SurfaceView implements Runnable{

    Thread t = null;
    SurfaceHolder holder;
    boolean isItOk = false;

    Typeface tf;
    private float testTextSize;

    private Bitmap menuic;

    private ArrayList<Integer> highClassic;
    private ArrayList<Integer> highCascade;
    private ArrayList<Integer> highEntropy;

    /**
     * Constructor
     * @param  context Android context
     * @param  cl      List of classic mode scores
     * @param  ca      List of cascade mode scores 
     * @param  e       List of entropu mode scores
     * @return         Highscores
     */
    public Highscores(Context context, ArrayList<Integer> cl,
                      ArrayList<Integer> ca, ArrayList<Integer> e) {
        super(context);
        highClassic = cl;
        highCascade = ca;
        highEntropy = e;
        holder = getHolder();
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Hyperspace Bold.ttf");
        resume();
        menuic = BitmapFactory.decodeResource(getResources(), R.drawable.menubutton);

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
     * Does the work
     */
    public void run() {
        while (isItOk) {
            if (!holder.getSurface().isValid()) continue;
            Canvas c = holder.lockCanvas();
            c.drawColor(Color.BLACK); // clean the screen
            c.drawARGB(0,0,0,0); // paint background
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Rect bounds = new Rect();
            String s;

            // draw title
            s = "HIGHSCORES";
            testTextSize = Asteroids.HEIGHT/12;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH / 2 - bounds.width()/2,
                    Asteroids.HEIGHT *1/4, paint
            );

            // draw titles highscores
            s = "Cascade";
            testTextSize = Asteroids.HEIGHT/26;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH *1/6 - bounds.width()/2,
                    Asteroids.HEIGHT *4/10, paint
            );
            s = "Classic";
            testTextSize = Asteroids.HEIGHT/26;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH *3/6 - bounds.width()/2,
                    Asteroids.HEIGHT *4/10, paint
            );

            s = "Entropy";
            testTextSize = Asteroids.HEIGHT/26;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH *5/6 - bounds.width()/2,
                    Asteroids.HEIGHT *4/10, paint
            );

            // draw highscores
            for (int i = 0; i < 5; ++i) {
                s = highClassic.get(i).toString();
                testTextSize = Asteroids.HEIGHT/26;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH *3/6 - bounds.width()/2,
                        Asteroids.HEIGHT *(6 + i)/12, paint
                );
                s = highCascade.get(i).toString();
                testTextSize = Asteroids.HEIGHT/26;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH *1/6 - bounds.width()/2,
                        Asteroids.HEIGHT *(6 + i)/12, paint
                );
                s = highEntropy.get(i).toString();
                testTextSize = Asteroids.HEIGHT/26;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH *5/6 - bounds.width()/2,
                        Asteroids.HEIGHT *(6 + i)/12, paint
                );

            }

            // draw back to menu icon
            c.drawBitmap(menuic, c.getWidth()/2 - menuic.getWidth()/2, 0, null);

            holder.unlockCanvasAndPost(c);
        }
    }
}
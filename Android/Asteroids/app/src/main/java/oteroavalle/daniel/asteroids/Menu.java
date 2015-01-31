package oteroavalle.daniel.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Class that implements the main menu
 */
public class Menu extends SurfaceView implements Runnable{

    private Thread t = null;
    private SurfaceHolder holder;
    private boolean isItOk = false;
    private Typeface tf;
    private float testTextSize;

    /**
     * Constructor
     * @param  context Android context
     * @return         Menu
     */
    public Menu(Context context) {
        super(context);
        holder = getHolder();
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Hyperspace Bold Italic.ttf");
        resume();
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
     * Checks if the play button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the play button has been pressed, false otherwise
     */
    public boolean playPressed(float x, float y) {
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT/2 + Asteroids.HEIGHT/10 &&
                y > Asteroids.HEIGHT/2 - Asteroids.HEIGHT/10) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the highscores button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the highscores button has been pressed, false otherwise
     */
    public boolean highscoresPressed(float x, float y) {
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT*3/4 + Asteroids.HEIGHT/10 &&
                y > Asteroids.HEIGHT*3/4 - Asteroids.HEIGHT/10) {
            return true;
        }
        return false;
    }

    /**
     * Does the workº
     */
    public void run() {
        while (isItOk) {
            if (!holder.getSurface().isValid()) continue;
            Canvas c = holder.lockCanvas();
            c.drawColor(Color.BLACK); // clean the screen
            c.drawARGB(0,0,0,0); // paint background
            String s;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Rect bounds = new Rect();

            // draw title
            s = "ASTEROIDS";
            testTextSize = (float)Asteroids.WIDTH/7;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH / 2 - bounds.width()/2,
                    Asteroids.HEIGHT * 1/4, paint
            );

            // draw play button
            s = "PLAY";
            testTextSize = (float)Asteroids.WIDTH/9;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH / 2 - bounds.width()/2,
                    Asteroids.HEIGHT/2 + bounds.height()/2, paint
            );
            Paint myPaint = new Paint();
            myPaint.setColor(Color.rgb(255, 0, 0));
            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth((float)Asteroids.HEIGHT/50);
            c.drawRect(Asteroids.WIDTH/2 - Asteroids.WIDTH/4,
                    Asteroids.HEIGHT/2 - Asteroids.HEIGHT/10,
                    Asteroids.WIDTH/2 + Asteroids.WIDTH/4,
                    Asteroids.HEIGHT/2 + Asteroids.HEIGHT/10,
                    myPaint
            );

            // draw highscores button
            s = "HIGHSCORES";
            testTextSize = (float)Asteroids.WIDTH/16;
            paint.setTextSize(testTextSize);
            paint.setTypeface(tf);
            paint.getTextBounds(s, 0, s.length(), bounds);
            c.drawText(
                    s, Asteroids.WIDTH / 2 - bounds.width()/2,
                    Asteroids.HEIGHT * 3/4 + bounds.height()/2, paint
            );
            c.drawRect(Asteroids.WIDTH/2 - Asteroids.WIDTH/4,
                    Asteroids.HEIGHT*3/4 - Asteroids.HEIGHT/10,
                    Asteroids.WIDTH/2 + Asteroids.WIDTH/4,
                    Asteroids.HEIGHT*3/4 + Asteroids.HEIGHT/10,
                    myPaint
            );

            holder.unlockCanvasAndPost(c);
        }
    }
}

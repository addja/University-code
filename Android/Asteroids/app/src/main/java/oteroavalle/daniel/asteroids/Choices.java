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
 * Class that controles the choice of game mode and control sistem
 */
public class Choices extends SurfaceView implements Runnable{

    private Thread t = null;
    private SurfaceHolder holder;
    private boolean isItOk = false;
    private Bitmap ic1;
    private Bitmap ic;
    private Bitmap menuic;
    Typeface tf;
    private float testTextSize;

    // to control the choice stage
    private boolean fisrtStage;

    private final int CLASSIC_GAME = 1;
    private final int CASCADE_GAME = 2;
    private final int ENTROPTY_GAME = 3;
    private final int CLASSIC_CONTROL = 4;
    private final int TOUCH_CONTROL = 5;
    private int typeGame;
    private int typeControl;

    /**
     * Constructor
     * @param  context Android context
     * @return         Choices
     */
    public Choices(Context context) {
        super(context);
        holder = getHolder();
        ic = BitmapFactory.decodeResource(getResources(), R.drawable.icongeneratorblue);
        ic1 = BitmapFactory.decodeResource(getResources(), R.drawable.icongeneratorgreen);
        tf = Typeface.createFromAsset(context.getAssets(),"fonts/Hyperspace Bold Italic.ttf");
        resume();
        fisrtStage = true;
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
     * Checks if the classic game button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the classic button has been pressed, false otherwise
     */
    public boolean classicGamePressed(float x, float y) {
        if (fisrtStage) return false;
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT*9/15 + Asteroids.HEIGHT/12 &&
                y > Asteroids.HEIGHT* 9/15 - Asteroids.HEIGHT/12) {
            typeGame = CLASSIC_GAME;
            return true;
        }
        return false;
    }

    /**
     * Checks if the cascade game button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the cascade button has been pressed, false otherwise
     */
    public boolean cascadePressed(float x, float y) {
        if (fisrtStage) return false;
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT*12/15 + Asteroids.HEIGHT/12 &&
                y > Asteroids.HEIGHT* 12/15 - Asteroids.HEIGHT/12) {
            typeGame = CASCADE_GAME;
            return true;
        }
        return false;
    }

    /**
     * Checks if the entropy game button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the entropy button has been pressed, false otherwise
     */
    public boolean entropyPressed(float x, float y) {
        if (fisrtStage) return false;
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT*6/15 + Asteroids.HEIGHT/12 &&
                y > Asteroids.HEIGHT* 6/15 - Asteroids.HEIGHT/12) {
            typeGame = ENTROPTY_GAME;
            return true;
        }
        return false;
    }

    /**
     * Checks if the classic control button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the classic button has been pressed, false otherwise
     */
    public boolean classicControlPressed(float x, float y) {
        if (!fisrtStage) return false;
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT/2 + Asteroids.HEIGHT/10 &&
                y > Asteroids.HEIGHT/2 - Asteroids.HEIGHT/10) {
            typeControl = CLASSIC_CONTROL;
            return true;
        }
        return false;
    }

    /**
     * Checks if the touch control button is pressed
     * @param  x the x canvas coordinate in to test if corresponds to the button
     * @param  y the y canvas coordinate in to test if corresponds to the button
     * @return   true if the trouch button has been pressed, false otherwise
     */
    public boolean touchControlPressed(float x, float y) {
        if (!fisrtStage) return false;
        if (x < Asteroids.WIDTH/2 + Asteroids.WIDTH/4 &&
                x > Asteroids.WIDTH/2 - Asteroids.WIDTH/4 &&
                y < Asteroids.HEIGHT*3/4 + Asteroids.HEIGHT/10 &&
                y > Asteroids.HEIGHT*3/4 - Asteroids.HEIGHT/10) {
            typeControl = TOUCH_CONTROL;
            return true;
        }
        return false;
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
     * Changes the state of the choice
     */
    public void changeStage() { fisrtStage = false; }

    /**
     * Checks if the classic control button has been pressed
     * @return true if the classic control button has been pressed, false otherwise
     */
    public boolean isClassicControl() { return typeControl == CLASSIC_CONTROL; }

    /**
     * Checks if the touch control button has been pressed
     * @return true if the touch control button has been pressed, false otherwise
     */
    public boolean isTouchControl() { return typeControl == TOUCH_CONTROL; }

    /**
     * Checks if the classic game button has been pressed
     * @return true if the classic game button has been pressed, false otherwise
     */
    public boolean isClassicGame() { return typeGame == CLASSIC_GAME; }

    /**
     * Checks if the cascade game button has been pressed
     * @return true if the cascade game button has been pressed, false otherwise
     */
    public boolean isCascadeGame() { return typeGame == CASCADE_GAME; }

    /**
     * Checks if the entropy game button has been pressed
     * @return true if the entropy game button has been pressed, false otherwise
     */
    public boolean isEntropyGame() { return typeGame == ENTROPTY_GAME; }

    /**
     * Does the work
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

            // draw back to menu icon
            c.drawBitmap(menuic, c.getWidth()/2 - menuic.getWidth()/2, 0, null);

            if (!fisrtStage) {

                // explanation
                s = "Game mode";
                testTextSize = (float)Asteroids.HEIGHT/15;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width()/2,
                        Asteroids.HEIGHT* 1/4 * 9/10, paint
                );

                // entropy game
                s = "ENTROPY";
                testTextSize = Asteroids.HEIGHT/22;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width()/2,
                        Asteroids.HEIGHT* 6/15 + bounds.height()/2, paint
                );
                Paint myPaint = new Paint();
                myPaint.setColor(Color.rgb(0, 0, 255));
                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth((float)Asteroids.HEIGHT/50);
                c.drawRect(Asteroids.WIDTH/2 - Asteroids.WIDTH/4,
                        Asteroids.HEIGHT* 6/15 - Asteroids.HEIGHT/12,
                        Asteroids.WIDTH/2 + Asteroids.WIDTH/4,
                        Asteroids.HEIGHT*6/15 + Asteroids.HEIGHT/12,
                        myPaint
                );

                // classic game
                s = "CLASSIC";
                testTextSize = Asteroids.HEIGHT/22;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width()/2,
                        Asteroids.HEIGHT* 9/15 + bounds.height()/2, paint
                );
                c.drawRect(Asteroids.WIDTH/2 - Asteroids.WIDTH/4,
                        Asteroids.HEIGHT* 9/15 - Asteroids.HEIGHT/12,
                        Asteroids.WIDTH/2 + Asteroids.WIDTH/4,
                        Asteroids.HEIGHT*9/15 + Asteroids.HEIGHT/12,
                        myPaint
                );

                // cascade game
                s = "CASCADE";
                testTextSize = Asteroids.HEIGHT/22;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width()/2,
                        Asteroids.HEIGHT* 12/15 + bounds.height()/2, paint
                );
                c.drawRect(Asteroids.WIDTH/2 - Asteroids.WIDTH/4,
                        Asteroids.HEIGHT* 12/15 - Asteroids.HEIGHT/12,
                        Asteroids.WIDTH/2 + Asteroids.WIDTH/4,
                        Asteroids.HEIGHT*12/15 + Asteroids.HEIGHT/12,
                        myPaint
                );
            }
            else {

                // explanation
                s = "Game controls";
                testTextSize = (float)Asteroids.HEIGHT/15;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width()/2,
                        Asteroids.HEIGHT * 1/4, paint
                );
                // classic control
                s = "Classic";
                testTextSize = (float)Asteroids.HEIGHT/18;
                paint.setTextSize(testTextSize);
                paint.setTypeface(tf);
                paint.getTextBounds(s, 0, s.length(), bounds);
                c.drawText(
                        s, Asteroids.WIDTH / 2 - bounds.width()/2,
                        Asteroids.HEIGHT/2 + bounds.height()/2, paint
                );
                Paint myPaint = new Paint();
                myPaint.setColor(Color.rgb(0, 255, 0));
                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth((float)Asteroids.HEIGHT/50);
                c.drawRect(Asteroids.WIDTH/2 - Asteroids.WIDTH/4,
                        Asteroids.HEIGHT/2 - Asteroids.HEIGHT/10,
                        Asteroids.WIDTH/2 + Asteroids.WIDTH/4,
                        Asteroids.HEIGHT/2 + Asteroids.HEIGHT/10,
                        myPaint
                );

                // touch control
                s = "Touch";
                testTextSize = (float)Asteroids.HEIGHT/18;
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

            }
            holder.unlockCanvasAndPost(c);
        }
    }
}
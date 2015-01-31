package oteroavalle.daniel.asteroids;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Main class of the app, handles all input and controles the app flow
 */
public class Asteroids extends Activity implements View.OnTouchListener {

    private Menu m;
    private GameEngine ge;
    private GameEngineMod gem;
    private Highscores hs;
    private Choices ch;
    private static int State;
    private static boolean GameOver;
    private static final int MENU = 1;
    private static final int GAME = 2;
    private static final int HIGHSCORES = 3;
    private static final int CHOICES = 4;
    private static final int GAMEMOD = 5;
    private float x, y;
    private int mode;

    public static float WIDTH;
    public static float HEIGHT;

    private static final int MAX_CLICK_DURATION = 300;
    private long startClickTime;

    private ArrayList<Integer> highClassic = new ArrayList<Integer>();
    private ArrayList<Integer> highCascade = new ArrayList<Integer>();
    private ArrayList<Integer> highEntropy = new ArrayList<Integer>();
    private SharedPreferences sharedPrefs;

    /**
     * Set up everything when the app is lauched
     * @param savedInstanceState Budle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new Menu(this);
        m.setOnTouchListener(this);
        setContentView(m);
        State = MENU;
        x = y = 0;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        WIDTH = displaymetrics.widthPixels;
        HEIGHT = displaymetrics.heightPixels;

        // read HighScores
        for (int i = 0; i < 5; ++i) {
            Integer classic;
            Integer entropy;
            Integer cascade;
            String iterator = Integer.toString(i);
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            classic = sharedPrefs.getInt("Classic " + iterator, 0); // 0 default value
            entropy = sharedPrefs.getInt("Entropy " + iterator, 0); // 0 default value
            cascade = sharedPrefs.getInt("Cascade " + iterator, 0); // 0 default value
            highClassic.add(i,classic);
            highCascade.add(i,cascade);
            highEntropy.add(i,entropy);
        }
    }

    /**
     * Set up everything when the app is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (State == MENU) m.pause();
        if (State == GAME) ge.pause();
        if (State == GAMEMOD) gem.pause();
        if (State == HIGHSCORES) hs.pause();
        if (State == CHOICES) ch.pause();
    }

    /**
     * Set up everything when the app is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (State == MENU) m.resume();
        if (State == GAME) ge.resume();
        if (State == GAMEMOD) gem.resume();
        if (State == HIGHSCORES) hs.resume();
        if (State == CHOICES) ch.resume();
    }

    /**
     * Handles the input events
     * @param  v  view in wich the input will be listened
     * @param  me catched motion event
     * @return    true or false depending on the correct hadling of the touch events
     */
    @Override
    public boolean onTouch(View v, MotionEvent me) {

        if (GameOver) {
            gameToMenu();
            GameOver = false;
            return true;
        }

        switch (me.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x = me.getX();
                y = me.getY();
                if (State == GAMEMOD) {
                    gem.touchDown(x,y);
                }
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;

            case MotionEvent.ACTION_UP:
                x = me.getX();
                y = me.getY();
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {
                    //click event has occurred
                    if (State == MENU) {
                        if (m.playPressed(x, y)) {
                            menuToChoices();
                        }
                        if (m.highscoresPressed(x, y)) {
                            menuToHighscores();
                        }
                        return true;
                    }
                    if (State == GAME) {
                        if (ge.menuPressed(x,y)) {
                            gameToMenu();
                        }
                        else {
                            ge.touch(x,y);
                        }
                        return true;
                    }
                    if (State == HIGHSCORES) {
                        if (hs.menuPressed(x,y)) {
                            highscoresToMenu();
                        }
                        return true;
                    }
                    if (State == CHOICES) {
                        if (ch.classicControlPressed(x, y)) {
                            ch.changeStage();
                            return true;
                        }
                        if (ch.touchControlPressed(x, y)) {
                            ch.changeStage();
                            return true;
                        }
                        if (ch.classicGamePressed(x, y)) {
                            lauchGame();
                            return true;
                        }
                        if (ch.entropyPressed(x, y)) {
                            lauchGame();
                            return true;
                        }
                        if (ch.cascadePressed(x, y)) {
                            lauchGame();
                            return true;
                        }
                        if (ch.menuPressed(x,y)) {
                            choicesToMenu();
                            return true;
                        }
                        return true;
                    }
                }
                if (State == GAMEMOD) {
                    if (gem.menuPressed(x,y)) {
                        gameToMenu();
                        return true;
                    }
                    else {
                        gem.touchUp();
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                x = me.getX();
                y = me.getY();
                if (State == GAME) ge.drag(x,y);
                if (State == GAMEMOD) gem.slide(x,y);
                break;
        }

        return true;
    }

    /**
     * Sets the game as over
     */
    public static void gameOver() {
        GameOver = true;
    }

    private void gameToMenu() {
        if (State == GAME) {
            ge.stop();
            if (GameOver) {
                Integer newscore = ge.getScore();
                updateHighScores(newscore);
            }
        }
        else {
            gem.stop();
            if (GameOver) {
                Integer newscore = gem.getScore();
                updateHighScores(newscore);
            }
        }

        m = new Menu(this);
        m.setOnTouchListener(this);
        setContentView(m);
        State = MENU;

        // write new highscores table
        SharedPreferences.Editor editor = sharedPrefs.edit();
        for (int i = 0; i < 5; ++i) {
            String iterator = Integer.toString(i);
            editor.putInt("Classic " + iterator, highClassic.get(i));
            editor.putInt("Cascade " + iterator, highCascade.get(i));
            editor.putInt("Entropy " + iterator, highEntropy.get(i));
        }
        editor.commit();
    }

    private void highscoresToMenu() {
        hs.stop();
        m = new Menu(this);
        m.setOnTouchListener(this);
        setContentView(m);
        State = MENU;
    }

    private void menuToChoices() {
        m.stop();
        ch = new Choices(this);
        ch.setOnTouchListener(this);
        setContentView(ch);
        State = CHOICES;
    }

    private void lauchGame() {
        int control;
        if (ch.isCascadeGame()) mode = 2;
        else if (ch.isEntropyGame()) mode = 3;
        else mode = 1;
        if (ch.isClassicControl()) control = 1;
        else control = 2;
        ch.stop();
        if (control == 2) {
            ge = new GameEngine(this, highClassic, highCascade, highEntropy, mode);
            ge.setOnTouchListener(this);
            setContentView(ge);
            State = GAME;
        }
        else {
            gem = new GameEngineMod(this, highClassic, highCascade, highEntropy, mode);
            gem.setOnTouchListener(this);
            setContentView(gem);
            State = GAMEMOD;
        }
    }

    private void choicesToMenu() {
        ch.stop();
        m = new Menu(this);
        m.setOnTouchListener(this);
        setContentView(m);
        State = MENU;
    }

    private void menuToHighscores() {
        m.stop();
        hs = new Highscores(this, highClassic, highCascade, highEntropy);
        hs.setOnTouchListener(this);
        setContentView(hs);
        State = HIGHSCORES;
    }

    private void updateHighScores(Integer sc) {

        // classic
        if (mode == 1) {
            for (int i = 0; i < 5; i++) {
                if (highClassic.get(i) > sc) continue;
                Integer tmp = highClassic.get(i);
                highClassic.set(i,sc);
                for (int j = i+1; j < 5; ++j) {
                    Integer tmp1 = tmp;
                    tmp = highClassic.get(j);
                    highClassic.set(j,tmp1);
                }
                break;
            }
        }
        // cascade
        else if (mode == 2) {
            for (int i = 0; i < 5; i++) {
                if (highCascade.get(i) > sc) continue;
                Integer tmp = highCascade.get(i);
                highCascade.set(i, sc);
                for (int j = i + 1; j < 5; ++j) {
                    Integer tmp1 = tmp;
                    tmp = highCascade.get(j);
                    highCascade.set(j, tmp1);
                }
                break;
            }
        }
        // entropy
        else {
            for (int i = 0; i < 5; i++) {
                if (highEntropy.get(i) > sc) continue;
                Integer tmp = highEntropy.get(i);
                highEntropy.set(i, sc);
                for (int j = i + 1; j < 5; ++j) {
                    Integer tmp1 = tmp;
                    tmp = highEntropy.get(j);
                    highEntropy.set(j, tmp1);
                }
                break;
            }
        }
    }

}

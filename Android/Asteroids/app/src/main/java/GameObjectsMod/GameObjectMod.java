package GameObjectsMod;

import oteroavalle.daniel.asteroids.Asteroids;

/**
 * Abstract class that creates the game object template for the classic game mode
 */
public class GameObjectMod {

    // position
    protected float x;
    protected float y;

    // direction
    protected float dx;
    protected float dy;

    // angle
    protected float radians;

    // speed
    protected float speed;
    protected float rotationSpeed;

    // size
    protected int width;
    protected int height;

    // poligon shape
    protected float[] shapex;
    protected float[] shapey;

    /**
     * Checks if a pont is contained in the object
     * @param  x x coordinate of the point in the canvas
     * @param  y y coordinates of the point in the canvas
     * @return   true or false depending on if the point is contained or not
     */
    public boolean contains(float x, float y) {
        boolean b = false;

        // arrange this (ray cast)
        for (int i = 0, j = shapex.length -1; i < shapex.length; j = i++) {
            if ((shapey[i] > y) != (shapey[j] > y) && (x < (shapex[j] - shapex[i]) *
                    (y - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i])) {
                b = !b;
            }
        }

        return b;
    }

    /**
     * Checks if a given poligon intersects with the object
     * @param  other given poligon
     * @return       returns true or false depending on if the poligon intersects or not
     */
    public boolean intersects(GameObjectMod other) {
        float sx[] = other.getShapex();
        float sy[] = other.getShapey();
        for (int i = 0; i < sx.length -1; i++) {
            if (contains(sx[i], sy[i])) return true;
        }
        return false;
    }

    /**
     * Checks if the object is leaving the limit of the canvas and if is so, moves it to the opposite limit
     */
    public void wrap() {
        float maxX = Asteroids.WIDTH;
        float maxY = Asteroids.HEIGHT-105;
        if (x < 0) x = maxX;
        if (x > maxX) x = 0;

        if (y < 0) y = maxY;
        if (y > maxY) y = 0;
    }
    
    /**
     * Getter of the object position on the canvas x edge
     * @return position on the canvas x edge
     */
    public float getx() { return x; }

    /**
     * Getter of the object position on the canvas y edge
     * @return position on the canvas y edge
     */
    public float gety() { return y; }

    /**
     * Getter of the object shape vector of x coordinates
     * @return object shape vector of x coordinates
     */
    public float[] getShapex() { return shapex; }

    /**
     * Getter of the object shape vector of y coordinates
     * @return object shape vector of y coordinates
     */
    public float[] getShapey() { return shapey; }

    /**
     * Sets the position of the poligon in a given coordinates
     * @param x x coordinate of the canvas
     * @param y y coordinathe of the canvas
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

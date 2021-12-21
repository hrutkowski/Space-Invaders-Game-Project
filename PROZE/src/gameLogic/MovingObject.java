package gameLogic;

import java.awt.*;

public class MovingObject {

    private float x, y;
    private final float width, height;
    private Color color;

    public MovingObject(float x, float y, float width, float height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }


    public String toString() {
        return String.format("(%.4f,%.4f,%.4f,%.4f,%s)", x, y, width, height, color);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public void setX(float x) { this.x = x;}

    public void setY(float y) { this.y = y;}

    public void addX(float deltaX) {
        x += deltaX;
    }

    public void addY(float deltaY) { y += deltaY;}
}

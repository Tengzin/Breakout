package a2;

import java.awt.*;

public class Ball {
    double x;
    double y;
    double width;
    double height;

    Ball(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillOval((int)x, (int)y, (int)width, (int)height);
        g2.setColor(Color.white);
        g2.fillOval((int)(x+1), (int)(y+1), (int)(width-2), (int)(height-2));
    }

    public void rescale(double xscale, double yscale) {
        x *= xscale;
        y *= yscale;
        width *= xscale;
        height *= yscale;
    }

    public void move(double speed) {

    }
}

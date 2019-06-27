package a2;

import java.awt.*;

public class Paddle {
    double x;
    double y;
    double width;
    double height;

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }

    Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect((int)x, (int)y, (int)width, (int)height);
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect((int)(x+1), (int)(y+1), (int)(width-2), (int)(height-2));
    }
    public void rescale(double xscale, double yscale) {
        x *= xscale;
        y *= yscale;
        width *= xscale;
        height *= yscale;
    }
    public int hitTest(Ball ball)
    {
        double ballX = ball.getX();
        double ballY = ball.getY();
        double ballSize = ball.getWidth();

        double xDist = Math.abs(ballX - x);
        double yDist = Math.abs(ballY - y);

        if (ballX + ballSize > x && ballX < x + width && (ballY + ballSize) > y && ballY < y + height) {
            // Top
            if (ballY <= (y + height/2) && (ballY+ballSize-y) < 4 && ballX + ballSize > x && ballX < x + width) {
                return 2;
            }
            else if (ballY >= (y + height/2) && (y+height-ballY) < 4  && ballX + ballSize > x && ballX < x + width) {
                return 2;
            }
            return 1;
        }
        return 0;
    }
}

package a2;
import java.awt.*;
import java.util.Observable;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Model extends Observable {
    double frameHeight;
    double frameWidth;
    double blockWidth;
    double blockHeight;
    JFrame frame;
    int FPS;
    int score = 0;

    ArrayList<Brick> brickList = new ArrayList<Brick>();
    Ball ball;
    Paddle paddle;

    int originalSpeed = 80;
    int speedFactor;
    int currSpeed;
    double dx;
    double dy;
    double originaldx;

    double slightMedium = 1.3;
    double mediumMode = 1.5;
    int hardMode = 2;


    int bricksAcross = 20;

    Model(JFrame frame, int FPS, int speed) {
        this.frame = frame;
        this.FPS = FPS;

        currSpeed = originalSpeed * speed;
        speedFactor = speed;

        frameHeight = frame.getHeight();
        frameWidth = frame.getWidth();
        blockWidth = frameWidth/bricksAcross;
        blockHeight = frameHeight/30;
        double ballSize = blockHeight/2;
        setChanged();
        Brick brick;
        for (int i = 0; i < bricksAcross; i++) {
            brick = new Brick(Color.red, 300,
                    (int) (i * blockWidth), (int) (3 * blockHeight), (int) blockWidth, (int) blockHeight);
            brickList.add(brick);
        }
        for (int i = 0; i < bricksAcross; i++) {
            brick = new Brick(Color.orange, 200,
                    (int) (i * blockWidth), (int) (4 * blockHeight), (int) blockWidth, (int) blockHeight);
            brickList.add(brick);
        }
        for (int i = 0; i < bricksAcross; i++) {
            brick = new Brick(Color.yellow, 100,
                    (int) (i * blockWidth), (int) (5 * blockHeight), (int) blockWidth, (int) blockHeight);
            brickList.add(brick);
        }
        for (int i = 0; i < bricksAcross; i++) {
            brick = new Brick(Color.green, 50,
                    (int) (i * blockWidth), (int) (6 * blockHeight), (int) blockWidth, (int) blockHeight);
            brickList.add(brick);
        }
        for (int i = 0; i < bricksAcross; i++) {
            brick = new Brick(Color.blue, 20,
                    (int) (i * blockWidth), (int) (7 * blockHeight), (int) blockWidth, (int) blockHeight);
            brickList.add(brick);
        }
        for (int i = 0; i < bricksAcross; i++) {
            brick = new Brick(Color.magenta, 10,
                    (int) (i * blockWidth), (int) (8 * blockHeight), (int) blockWidth, (int) blockHeight);
            brickList.add(brick);
        }
        ball = new Ball(9*blockWidth, 15*blockHeight, ballSize, ballSize);

        dx = ball.getWidth()/5;
        originaldx = dx;
        dy = dx;

        paddle = new Paddle(9*blockWidth, 28*blockHeight, 1.7*blockWidth, blockHeight*0.8);
    }

    public ArrayList<Brick> getBrickList() {
        return brickList;
    }
    public Ball getBall() {
        return ball;
    }
    public void ballMove() {
        double x = ball.getX();
        double y = ball.getY();
        double ballWidth = ball.getWidth();
        if (x < 0 || x > (frameWidth - ballWidth)) { dx *= -1; }
        // Game over
        if (y > (frameHeight - 3*ballWidth)) {
            dx = 0;
            dy = 0;
            this.notifyObservers();
        }
        if (y < 0) { dy *= -1; }
        // Check if hits bricks
        for (Brick brick : brickList) {
            int test = brick.hitTest(ball);
            if (test == 1 || test == 2) {
                // Make harder if top bricks
                if (brick.color == Color.orange) {
                    if (dx > 0)dx = originaldx * mediumMode;
                    else dx = originaldx * -mediumMode;
                }
                else if (brick.color == Color.red) {
                    if (dx > 0)dx = originaldx * hardMode;
                    else dx = originaldx * -hardMode;
                }
                else if (brick.color == Color.yellow) {
                    if (dx > 0)dx = originaldx * slightMedium;
                    else dx = originaldx * -slightMedium;
                }
                if (test == 1) {
                    dx *= -1;
                }
                else dy *= -1;
                brickList.remove(brick);
                score += brick.getScore();
                break;
            }
        }
        if (paddle.hitTest(ball) == 1) {
            dx *= -1;
        }
        else if (paddle.hitTest(ball) == 2) {
            dy *= -1;
        }

        // move the ball
        ball.setX(x += dx);
        ball.setY(y += dy);
    }
    public void rescale() {
        double xScaleFactor, yScaleFactor;
        xScaleFactor = frame.getWidth()/frameWidth;
        yScaleFactor = frame.getHeight()/frameHeight;

        for (Brick brick : brickList) {
            brick.rescale(xScaleFactor, yScaleFactor);
        }
        ball.rescale(xScaleFactor, yScaleFactor);
        dx *= xScaleFactor;
        dy *= yScaleFactor;
        paddle.rescale(xScaleFactor, yScaleFactor);
        frameWidth = frame.getWidth();
        frameHeight = frame.getHeight();
        blockWidth = frameWidth/20;
        blockHeight = frameHeight/30;
    }
}
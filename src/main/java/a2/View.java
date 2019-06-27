package a2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.*;

class View extends JPanel implements Observer {
    private Model model;
    ArrayList<Brick> brickList;
    Ball ball;
    Paddle paddle;
    int FPS;
    int ballSpeed;
    Boolean title = true;
    Boolean end = false;

    View(Model model) {
        // Blank cursor idea from https://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        this.setCursor(blankCursor);

        // set the model
        this.model = model;
        this.FPS = model.FPS;
        this.ballSpeed = model.currSpeed;

        brickList = model.getBrickList();
        ball = model.getBall();
        paddle = model.paddle;

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    title = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseMoved(MouseEvent e){
                if (e.getX() < model.frameWidth - model.blockWidth)
                paddle.setX(e.getX());
            }});

        // Must resize all components
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                model.rescale();
                repaint();
            }
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask()  {
            @Override
            public void run() {
                repaint();
            }
        };
        timer.schedule(task, 0, (1000/FPS));

        Timer ballTimer = new Timer();
        TimerTask ballTask = new TimerTask()  {
            @Override
            public void run() {
                if (title == false) model.ballMove();
            }
        };
        ballTimer.schedule(ballTask, 0, (1000/ballSpeed));
    }

    public void updateBallSpeed(int speed) {
        this.ballSpeed = speed;
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        end = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                 RenderingHints.VALUE_ANTIALIAS_ON);
        if (title == false) {
            brickList = model.getBrickList();

            for (Brick brick : brickList) {
                brick.draw(g2);
            }
            ball.draw(g2);
            paddle.draw(g2);

            // Score and FPS
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, (int)model.blockHeight));
            g.drawString("FPS: " + Integer.toString(FPS), (int)model.blockWidth/2, (int)model.blockHeight);
            g.drawString("Score: " + Integer.toString(model.score), (int)model.blockWidth/2, (int)model.blockHeight*2);
            if (end == true) {
                g.setColor(Color.RED);
                g.setFont(new Font("Helvetica", Font.BOLD, (int)model.blockHeight*3));
                g.drawString("GAME OVER!", (int)model.blockWidth*5, (int)model.blockHeight*15);
            }

        } else {
            g.setFont(new Font("TimesRoman", Font.PLAIN, (int)model.blockHeight));
            g.drawString("Breakout!", (int)model.blockWidth*9, (int)model.blockHeight*5);
            g.drawString("Author: Teng Da Hu", (int)model.blockWidth*8, (int)model.blockHeight*6);
            g.drawString("20523896", (int)model.blockWidth*9, (int)model.blockHeight*7);
            g.drawString("Use your mouse to control the paddle!", (int)model.blockWidth*6, (int)model.blockHeight*8);
            g.drawString("Press ENTER to begin, ESC to exit", (int)(model.blockWidth*6.5), (int)model.blockHeight*9);
        }
        Toolkit.getDefaultToolkit().sync();
    }
}

import java.awt.*;
import hsa.Console;

/**
 * This class runs the ball in a thread. The drawing/movement aspects must be in 
 * the thread, meaning they are in this class so the ball can move at the same time
 * as score update and platform movement.
 * Created with assistance from: Neeta Garg (Mom)
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class BallThread extends Thread
{
    /**
     * Variable to use Console in BallThread
     */
    protected Console c;
    
    /**
     * Variable to use Ball in BallThread
     */
    protected Ball ball;
    
    /**
     * Variable to use Platform in BallThread
     */
    protected Platform p;
    
    /**
     * Variable to set x-speed of ball
     */
    protected int ballXSpeed = 5;

    /**
     * Variable to set y-speed of ball
     */
    protected int ballYSpeed = 5;
    
    /**
     * Constructor for BallThread
     * @param c Retrieves Console being used so there is only one Console in the program
     * @param ball Retrieves Ball being used so there is only one Ball in the program
     * @param p Retrieves Platform being used so there is only one Platform in the program
     */
    public BallThread(Console c, Ball ball, Platform p)
    {
        this.c = c;
        this.ball = ball;
        this.p = p;
    }
    
    /**
     * Method to draw the ball and the eraser ball
     */
    public void drawBall()
    {       
        ball.ballAlive = true;
        while(ball.ballAlive) //Only runs Thread while ball is on the screen
        {
            c.setColor(Color.red);
            c.fillOval(ball.ballX, ball.ballY, 20, 20);
            p.selectColor();
            c.fillOval(ball.preBallX, ball.preBallY, 20, 20); //Fills previous ball with background color
            ballMove();
        }
    }
    
    /**
     * Method to move the ball randomly, while erasing the previous ball with the background color
     */
    public void ballMove()
    {
        ball.preBallX = ball.ballX-ballXSpeed; //Assigns previous ball location
        ball.preBallY = ball.ballY-ballYSpeed;
        
        ball.ballX += ballXSpeed; //Updates current ball location
        ball.ballY += ballYSpeed;
        
        if (ball.onCollision("Left Wall"))
        {
            ballXSpeed *= -1;
        }
        else if (ball.onCollision("Right Wall"))
        {
            ballXSpeed *= -1;
        }
        else if (ball.onCollision("Top Wall"))
        {
            ballYSpeed *= -1;
        }
        else if(ball.onCollision("Bottom Wall"))
        {
            ballXSpeed = 0;
            ballYSpeed = 0;
            ball.ballAlive = false;
        }
        else if (ball.onCollision("Platform"))
        {
            ballYSpeed *= -1;
        }
        else if(ball.onBrickCollision())
        {
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
        
        try //Delays movement so it is visible, and varies visible speed depending on level being played
        {
            sleep(ball.ballDelay);
        }
        catch(Exception e) {}
    }
    
    /**
     * Method to run current thread and draw the ball
     */
    public void run()
    {
        drawBall();
    }
    
    /**
     * Method to end current Ball thread so new one can be created after
     */
    public void end()
    {
        ball.ballAlive = false;
    }
}

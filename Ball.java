import java.awt.*;
import hsa.Console;

/**
 * This class declares all the variables used for the ball's thread so they do 
 * not reset after every thread is newly created. Has all ball collision detection
 * and resets ball position. 
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class Ball
{
    /**
     * Variable to create Console used in Ball
     */
    protected Console c;

    /**
     * Variable to create Platform used in Ball
     */
    protected Platform p;

    /**
     * Variable to create Background used in Ball
     */
    protected Background b;

    /**
     * Variable to create Brick used in Ball
     */
    protected Brick br;

    /**
     * Variable to set x-coordinate of ball
     */
    protected int ballX = 390;

    /**
     * Variable to set y-coordinate of ball
     */
    protected int ballY = 375;
    
    /**
     * Variable to set previous x-location of ball, setting it to initial location of ball first
     */
    protected int preBallX = ballX;

    /**
     * Variable to set previous y-location of ball, setting it to initial location of ball first
     */
    protected int preBallY = ballY;
    
    /**
     * Variable to set the ball movement delay, which alters speed at which x- and y- coordinates change
     */
    protected int ballDelay;
    
    /**
     * Variable to check if the ball is alive
     */
    protected boolean ballAlive = true;

    /**
     * Variable to check if each brick is alive by assigning a true/false value to each brick at the same index of each brick
     */
    protected boolean[][] brickAlive;
    
    /**
     * Constructor for Ball
     * @param c Retrieves Console being used so there is only one Console in the program
     * @param b Retrieves Background being used so there is only one Background in the program
     * @param p Retrieves Platform being used so there is only one Platform in the program
     * @param br Retrieves Brick being used so there is only one Brick in the program
     */
    public Ball(Console c, Background b, Platform p, Brick br)
    {
	this.c = c;
	this.p = p;
	this.b = b;
	this.br = br;
    }
    
    /**
     * Method to reset position of ball to starting location
     */
    public void resetPos()
    {
	ballX = 390;
	ballY = 375;
    }
    
    /**
     * Method to detect if the ball collides with an object, excluding bricks which have their own method.
     * @param obj Retrieves the object to check collision of ball with it
     * @return true if there is collision, false otherwise
     */
    protected boolean onCollision(String obj)
    {
	if(obj.equals("Left Wall") && ballX<=55)
	{
	    return true;
	}
	else if(obj.equals("Right Wall") && ballX>=725)
	{
	    return true;
	}
	else if(obj.equals("Top Wall") && ballY<=55)
	{
	    return true;
	}
	else if(obj.equals("Bottom Wall") && ballY>=510)
	{
	    p.paused = true; //Ends platform thread
	    return true;
	}
	else if((obj.equals("Platform")) && (ballX >= p.platformX && ballX<=p.platformX+100) && (ballY >=425 && ballY<=435))
	{
	    return true;
	}
	else return false;
    }
    
    /**
     * Method to run through every single brick, checking for ball collision from the center of the ball with each brick.
     * Then, it draws the background color over the brick with confirmed collision.
     * @return true if there is collision, false otherwise
     */
    protected boolean onBrickCollision()
    {
	for (int i = 0; i<6; i++) //Checks each row
	{
	    if(ballY+10 >= br.brickY+(35*i) && ballY+10 <= br.brickY+(35*(i+1))) //Row collision
	    {
		p.selectColor();
		for (int j = 0; j<6; j++) //Checks each brick in the rows, then redraws background over it and sets it as dead
		{
		    if(brickAlive[i][j] && ballX+10 >= br.brickX+(90*j) && ballX+10 <= br.brickX+(90*(j+1))) //Column collision
		    {
			brickAlive[i][j] = false;
			c.fillRoundRect(br.brickX+(90*j)-1,br.brickY+(35*i)-1,92,37,15,15);
			return true;
		    }
		}
	    }
	}
	return false;
    }
}

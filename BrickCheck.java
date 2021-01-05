import java.awt.*;
import hsa.Console;

/**
 * This class runs the score updater in a thread. Since the score will otherwise
 * only update every time a user presses a key to move a platform, this thread 
 * separates the actions to allow the game to update the score instantly upon 
 * collision while allowing all other game actions to continue.
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class BrickCheck extends Thread
{
    /**
     * Variable to create local Console being used
     */
    protected Console c;
    
    /**
     * Variable to create local Ball being used
     */
    protected Ball ball;
    
    /**
     * Variable to create local Brick being used
     */
    protected Brick br;
    
    /**
     * Variable to create local Platform being used
     */
    protected Platform p;
    
    /**
     * Variable to create local Background being used
     */
    protected Background b;
    
    /**
     * Variable for the players score, also used to determine win/loss depending on max score
     */
    protected int scoreCount = 0;
    
    /**
     * Variable to check if the brick in the 2D array is destroyed, and if it is then it 
     * will not add to the score more than once while checking for destroyed bricks
     */
    protected boolean[][] ifBrickDestroyed;
    
    /**
     * Variable to control Thread while loop
     */
    protected boolean checkBricks;
    
    /**
     * Constructor for BrickCheck
     * @param c Retrieves Console being used so there is only one Console in the program
     * @param ball Retrieves Ball being used so there is only one Ball in the program
     * @param br Retrieves Brick being used so there is only one Brick in the program
     * @param p Retrieves Platform being used so there is only one Platform in the program
     * @param b Retrieves Background being used so there is only one Background in the program
     */
    public BrickCheck(Console c, Ball ball, Brick br, Platform p, Background b)
    {
        this.c = c;
        this.ball = ball;
        this.br = br;
        this.p = p;
        this.b = b;
    }
    
    /**
     * Method to initialize 2D array sizes and values
     */
    protected void createBrickChecker()
    {
        ball.brickAlive = new boolean[6][6];
        ifBrickDestroyed = new boolean[6][6];
        
        for (int i = 0; i<6;i++) //Sets each element in the 2D array brickArray to true and in ifBrickDestroyed to false
        {
            for(int j = 0; j<6; j++)
            {
                ball.brickAlive[i][j] = true;
                ifBrickDestroyed[i][j] = false;
            }
        }
    }
    
    /**
     * Method to check if bricks have been destroyed, and add to the score once
     */
    protected void runBrickChecker()
    {
        while(checkBricks) //Runs thread
        {
            for (int i = 0; i<6;i++) //Checks if each brick is alive, and checks only once for each brick if it is
            {
                for(int j = 0; j<6; j++)
                {
                    if(!ifBrickDestroyed[i][j] && !ball.brickAlive[i][j]) //Needs 2 controls, one to prevent ball from bouncing off dead bricks and another for scoring once per brick
                    {
                        scoreCount+=50;
                        ifBrickDestroyed[i][j] = true;
                        displayScore();
                    }
                }
            }
        }
    }
    
    /**
     * Method to display the score on the game border
     */
    protected void displayScore()
    {
        switch (p.level) //Switches backing color being used by level being played
        {
            case 1:
                c.setColor(new Color(234, 189, 252));
                break;
            case 2:
                c.setColor(new Color(230, 197, 179));
                break;
            case 3:
                c.setColor(new Color(247, 198, 198));
                break;
            case 4:
                c.setColor(new Color(140, 220, 245));
                break;
            default:
                break;
        }
        
        c.fillRect(305, 5, 200, 40); //Draws score count' lighter rectangle
        c.setColor(Color.black);
        c.setFont(b.createFont("times-new-roman.ttf", 40));
        c.drawString("Score: " + scoreCount, 310, 40); //Draws score count
    }
    
    /**
     * Method to run the score thread so it constantly updates
     */
    public void run()
    {
        checkBricks = true;
        runBrickChecker();
    }
    
    /**
     * Method to end the score thread when the game ends
     */
    public void end()
    {
        checkBricks = false;
    }
}

import java.awt.*;
import hsa.Console;

/**
 * This class has collision detection method and resets platform position, while 
 * having the actual drawing/moving method, with PlatformThread actually running them.
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class Platform
{
    /**
     * Variable to create Console used in Menu
     */
    protected Console c;

    /**
     * Variable to control platform x-coordinate and allow platform movement
     */
    protected int platformX = 350;

    /**
     * Variable to retrieve previous platform position to draw over previous platform to create animation effect
     */
    protected int prePlatformX;

    /**
     * Variable to control platform y-coordinate to bring platform off the screen when not needed
     */
    protected int platformY = 425;

    /**
     * Variable to control direction the platform is moving along x-axis
     */
    protected char dir;

    /**
     * Variable to check if platform is moving or not
     */
    protected boolean moving = false;

    /**
     * Variable to check which game level is running
     */
    protected int level;
    
    /**
     * Variable to check if platform movement is paused
     */
    protected boolean paused = false;
    
    /**
     * Variable to gather user input to move platform
     */
    protected char input;
        
    /**
     * Constructor for Platform
     * @param c Retrieves Console being used so there is only one Console and assigns it to local Console
     */
    public Platform(Console c)
    {
        this.c = c;
    }
    
    /**
     * Method to select the background color for redrawing behind objects, this method is purely for efficiency
     */
    protected void selectColor()
    {
        switch (level) //Sets redraw color to background color of level being played
        {
            case 1:
                c.setColor(new Color(204,232,255));
                break;
            case 2:
                c.setColor(new Color(204,255,232));
                break;
            case 3:
                c.setColor(new Color(255,204,232));
                break;
            case 4:
                c.setColor(new Color(60, 205, 250));
                break;
            default:
                break;
        }
    }
    
    /**
     * Method to reset platform position to starting position
     */
    public void resetPos()
    {
        input = ' '; //Clears any existing input to prevent errors
        platformX = 350;
        platformY = 425;
    }
        
    /**
     * Method to draw the platform and the eraser for the previous platform 
     */
    public void drawPlatform()
    {
        prePlatformX = platformX;
        if (moving && dir == 'a') //Moves the platform using direction
        {
            platformX -= 50;
            selectColor();
            c.fillRect(prePlatformX, platformY, 100, 10); //Fills previous platform with background color
            moving = false;
        }
        else if(moving && dir == 'd')
        {
            platformX += 50;
            selectColor();
            c.fillRect(prePlatformX, platformY, 100, 10); //Fills previous platform with background color
            moving = false;
        }
        c.setColor(Color.black);
        c.fillRect(platformX,platformY,100,10);
    }
    
    /**
     * Method to check for platform movement using keyboard input
     */
    protected void movePlatform()
    {
        input = c.getChar();
        if (input != 'a' && input != 'd') //If the input is not valid, it rechecks. No error message is displayed as it would interfere with game play
        {
            movePlatform(); 
        }
        else //Only allows movement in each direction if the platform does not collide with the walls
        {
            if(!onCollision('L'))
            {
                if (input == 'a')
                {
                    updateDir('a'); //Updates platform's position
                }
            }
            if(!onCollision('R'))
            {
                if (input == 'd')
                {
                    updateDir('d'); //Updates platform's position
                }
            }
        } 
    }
    
    /**
     * Method to update the direction the platform is moving based on key input
     * @param dir Checks the direction the platform is moving 
     */
    protected void updateDir(char dir)
    {
        if(dir == 'a')
        {
            this.dir = 'a';
            moving = true;
        }
        else if(dir == 'd')
        {
            this.dir = 'd';
            moving = true;
        }
    }

    /**
     * Method to check if there is collision happening between the platform and Left/Right walls. 
     * @param wall Retrieves which wall the collision is happening with
     * @return Returns true if there is collision and false if there is no collision
     */
    protected boolean onCollision(char wall)
    {
        if (wall == 'L' && platformX<=70)
        {
            return true;
        }
        else if(wall == 'R' && platformX>=630)
        { 
            return true;
        }
        else return false;
    }
}

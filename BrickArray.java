import java.awt.*;
import hsa.Console;

/**
 * This subclass creates a 2D array of Brick objects, then draws it as a complete  
 * grid, with each Brick having an index location that is used to check if each 
 * is alive and for collision. Subclass needed since an array of Bricks is needed
 * not int or String etc, so its just more effective.
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class BrickArray extends Brick
{
    /**
     * Variable to create a 2D array of Brick objects
     */
    protected Brick[][] br;
    
    /**
     * Constructor for BrickArray, initializes array to 6x6, the only size of the grid needed
     * @param c Retrieves the Console being used so there is only one Console 
     */
    public BrickArray(Console c)
    {
	this.c = c;
	br = new Brick[6][6];
    }
    
    /**
     * Method to create the grid of bricks from the set starting location
     */
    public void createGrid()
    {
	brickX = 130;
	brickY = 90;
	for(int i = 0; i<6; i++)
	{
	    for (int j = 0; j<6; j++) //Runs through 2D array, drawing each element
	    {
		br[i][j] = new Brick();
		drawBrick();
		brickX += 90; //Next brick
	    }
	    brickX = 130; //Resets row drawing to first slot of grid
	    brickY += 35; //Next row
	}
    }   
}

import java.awt.*;
import hsa.Console;

/**
 * This class creates an individual Brick and assigns it a pair of coordinates.
 * The brick is drawn using a random blend of 3-4 colors to make the graphics
 * more interesting, since refreshing/redrawing the background is not needed.
 * This class is needed so the BrickArray class can use a 2D array of Brick
 * objects, so that each Brick has is a new Brick object. 
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class Brick 
{
    /**
     * Variable to create Console being used in Brick
     */
    protected Console c;

    /**
     * Variable for x-coordinate locations of bricks
     */
    protected int brickX = 130;

    /**
     * Variable for y-coordinate locations of bricks
     */
    protected int brickY = 90;
    
    /**
     * Constructor for Brick class to allow a subclass (empty)
     */
    public Brick()
    {
        
    }
    
    /**
     * Constructor for Brick
     * @param c Retrieves Console being used so there is only one Console in the program
     */
    public Brick(Console c)
    {
        this.c = c;
    }
    
    /**
     * Method to draw a single brick with a cool random color pattern and a black outline at the set x- and y- location
     */
    public void drawBrick()
    {
        for (int i = 20; i>0; i--)
        {
            double randCol = Math.random()*3; //Used to select random colors used while drawing each brick
            if(randCol <= 1)
            {
                c.setColor(Color.white);
                c.fillRoundRect(brickX,brickY,90,i+15,15,15);
            }
            else if(randCol <=2)
            {
                c.setColor(new Color(204, 232-i*3, 255));
                c.fillRoundRect(brickX,brickY,90,i+15,15,15);
            }
            else if(randCol <=3)
            {
                c.setColor(new Color(230-i*3, 255-i, 255-i));
                c.fillRoundRect(brickX,brickY,90,i+15,15,15);
            }
        }
        c.setColor(Color.black); //Brick black borders
	c.drawRoundRect(brickX,brickY,90,35,15,15);
    }
}

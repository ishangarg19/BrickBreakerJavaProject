import hsa.Console;
import java.awt.*;

/**
 * This class runs the platform in a thread. The drawing/movement aspects must be in 
 * the thread, meaning they are in this class so the platform can move at the same time
 * as score update and ball movement. Methods from Platform are used separately as a thread.
 * Created with assistance from: Neeta Garg (Mom)
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class PlatformThread extends Thread
{
    
    /**
     * Variable to create local Console being used
     */
    protected Console c;
    
    /**
     * Variable to create local Platform being used
     */
    protected Platform p;

    /**
     * Constructor for PlatformThread
     * @param c Retrieves Console being used so there is only one Console in the program
     * @param p Retrieves Platform being used so there is only one Platform in the program
     */
    public PlatformThread(Console c, Platform p)
    {
        this.c = c;
        this.p = p;
    }
    
    /**
     * Method to create the platform and constantly draw it in a thread
     */
    public void createPlatform()
    {
        p.input = ' '; //Clears any previously stored input to prevent errors
        p.selectColor();
        c.fillRect(50, 400, 700, 50);

        while(!p.paused) //Runs thread
        {
            p.drawPlatform();
        }
    }
    
    /**
     * Method to run platform thread while drawing platform
     */
    public void run()
    {
        p.paused = false;
        createPlatform();
    }
    
    /**
     * Method to end platform thread so a new one can start
     */
    public void end()
    {
        p.paused = true;
    }
}

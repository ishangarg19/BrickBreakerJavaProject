import java.awt.*;
import hsa.Console;

/**
 * This class contains the main method only, and is used to create an instance 
 * of all non-thread classes and pass them to each other where needed. Also runs the game 
 * by first drawing splash screen then constantly calling askData until exit is true.
 * Threads created with assistance from: Neeta Garg (Mom)
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class Main 
{
    /**
     * Main Method to create instances of all classes and runs game, which starts from askData in GameControlMenu
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
	Console c = new Console(25,100,"Brick Breaker"); //500x800 window
	Background b = new Background(c); //Initializes all class objects and passes them where needed so there is only one of each object at all times
	Scores s = new Scores(c,b);
	Platform p = new Platform(c);
	Brick br = new Brick(c);
	Ball ball = new Ball(c,b,p,br);
	BrickArray a = new BrickArray(c);
	GameControlMenu g = new GameControlMenu(c,b,s,p,br,ball,a);
	
	b.drawSplashScreen();
        do //Runs game until user quits
        {
            g.askData();
        } while(!g.exit);
    }
}

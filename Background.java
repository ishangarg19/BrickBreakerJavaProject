import java.awt.*;
import hsa.Console;
import java.io.*;

/**
 * This class creates all the backdrops for all scenes, including splash screen,
 * main menu, game scenes, win/loss scenes, instructions, scores, and quit. There
 * are no game mechanics in this class, only drawing.
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class Background
{
    /**
     * Variable to create Console used in Background
     */
    protected Console c;

    /**
     * Constructor for Background
     * @param c Retrieves Console used in program so there is only one Console object
     */
    public Background(Console c)
    {
	this.c = c;
    }
    
    /**
     * Method to create a delay in milliseconds using Thread for efficiency
     * @param delay Delay time in milliseconds
     */
    protected void delay(int delay)
    {
	try
	{
	    Thread.sleep(delay);
	}
	catch(Exception e)
	{
	    
	}
    }

    /**
     * Method to create a new font using an external TrueType font file (.ttf). 
     * Created with reference to https://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
     * @param fileName Receives the name of the file to be read to retrieve font
     * @param size Size of the new font
     * @return Newly created font of defined type and size
     */
    protected Font createFont(String fileName, float size)
    {
	Font font = null;
	try
	{
	    font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fileName)); //Loads font 
	    font = font.deriveFont(size); //Derives the font using defined size
	}
	catch(Exception e){}
	
	return font;
    }
    
    /**
     * Method to draw the splash screen
     */
    public void drawSplashScreen()
    {     	
	for(int i = 0;i<255;i++) //Draws gradient background
	{
	    c.setColor(new Color (235, 212+i/10, 245));
	    c.fillOval(-100+i*2, -200+i*2, 1000-i*4, 1000-i*4);
	}
	
	c.setColor(Color.black);
	c.setFont(createFont("Lobster-Regular.ttf", 80));
	c.drawString("Brick", 310, 200);
	c.drawString("Breaker", 275, 285);
	
	for (int i = 50; i>0;i--)
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green Bricks
	    c.fillRoundRect(335,15,155,i+15,30,30);
	    c.fillRoundRect(609,224,155,i+15,30,30);
	    c.fillRoundRect(107,376,155,i+15,30,30);

	    c.setColor(new Color(204, 232-i*3, 255)); //Purple Bricks
	    c.fillRoundRect(45,59,155,i+15,30,30);
	    c.fillRoundRect(554,375,155,i+15,30,30);

	    c.setColor(new Color(255, 204, 232-i*3)); //Orange Bricks
	    c.fillRoundRect(609,69,155,i+15,30,30);
	    c.fillRoundRect(65,220,155,i+15,30,30);
	    delay(25);
	}
	
	for(int j = 0; j<=800;j++) //Loading bar
	{
		c.setColor(Color.red);
		c.fillRect(0,490,j,10);
		delay(3);
	}
	
	delay(500);
	c.clear(); //Clears away splash screen to prevent any possible drawing issues after with Console
    }
    
    /**
     * Method to draw the Menu scene backdrop and buttons
     */
    public void menuScene()
    {
	for(int i = 0;i<255;i++) //Draws gradient background
	{
	    c.setColor(new Color(i, 225+i/10, 255));
	    c.fillOval(-100+i*2, -200+i*2, 1000-i*4, 1000-i*4);
	}
	
	c.setColor(Color.black);
	c.setFont(createFont("Lobster-Regular.ttf", 70)); //Title
	c.drawString("Brick Breaker", 210, 100);
	
	for (int i = 50; i>0;i--)
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green button
	    c.fillRoundRect(275,150,250,i+15,30,30);
	   
	    c.setColor(new Color(204, 232-i*3, 255)); //Purple button
	    c.fillRoundRect(275,230,250,i+15,30,30);
	    
	    c.setColor(new Color(255, 204, 232-i*3)); //Orange button
	    c.fillRoundRect(275,310,250,i+15,30,30);
	    
	    c.setColor(new Color(230-i*3, 255-i, 255-i)); //Blue button
	    c.fillRoundRect(275,390,250,i+15,30,30);
	}
	
	c.setColor(Color.black);
	c.setFont(createFont("times-new-roman.ttf", 30)); //Button labels
	c.drawString("Level Select (1)", 305, 190);
	c.drawString("Instructions (2)", 313, 275);
	c.drawString("High Scores (3)", 303, 355);
	c.drawString("Quit (4)", 340, 432);
    }
    
    /**
     * Method to draw scene upon winning game with button options
     */
    public void winScene()
    {
	c.setColor(new Color(0,0,0,120));
	c.fillRect(0, 0, 800, 500);
	
	c.setColor(Color.white);
	c.setFont(createFont("Lobster-Regular.ttf", 70));
	c.drawString("You Win!", 275, 100);
        
        for (int i = 50; i>0;i--)
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green Brick
	    c.fillRoundRect(275,150,250,i+15,30,30);
	    
	    c.setColor(new Color(255, 204, 232-i*3)); //Orange Brick
	    c.fillRoundRect(275,230,250,i+15,30,30);
	}
        
        c.setColor(Color.black);
	c.setFont(createFont("times-new-roman.ttf", 30)); //Button labels
	c.drawString("Save Result (1)", 305, 190);
	c.drawString("Return to Menu (2)", 285, 275);
    }
    
    /**
     * Method to draw scene upon losing game with button options
     */
    public void loseScene()
    {
	c.setColor(new Color(0,0,0,120));
	c.fillRect(0, 0, 800, 500);
	
	c.setColor(Color.white);
	c.setFont(createFont("Lobster-Regular.ttf", 70));
	c.drawString("Nice Try!", 275, 100);
        
        for (int i = 50; i>0;i--)
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green Brick
	    c.fillRoundRect(275,150,250,i+15,30,30);
	    
	    c.setColor(new Color(255, 204, 232-i*3)); //Orange Brick
	    c.fillRoundRect(275,230,250,i+15,30,30);
	}
        
        c.setColor(Color.black);
	c.setFont(createFont("times-new-roman.ttf", 30)); //Button labels
	c.drawString("Save Result (1)", 305, 190);
	c.drawString("Return to Menu (2)", 285, 275);
    }
    
    /**
     * Method to draw level selection scene with button options
     */
    public void drawLevelSelect()
    {
	for(int i = 0;i<=255;i++) //Draws gradient background
	{
	    c.setColor(new Color(235, 225+i/10, i));
	    c.fillOval(-100+i*2, -200+i*2, 1000-i*4, 1000-i*4);
	}  

	c.setColor(Color.black);
	c.setFont(createFont("Lobster-regular.ttf", 60)); //Button labels
	c.drawString("Level Select", 265, 105);

	for (int i = 50; i>0;i--)
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green Brick
	    c.fillRoundRect(275,160,250,i+15,30,30);

	    c.setColor(new Color(204, 232-i*3, 255)); //Purple Brick
	    c.fillRoundRect(275,240,250,i+15,30,30);

	    c.setColor(new Color(230-i*3, 255-i, 255-i)); //Blue Brick
	    c.fillRoundRect(275,320,250,i+15,30,30);
            
            c.setColor(new Color(255, 204, 232-i*3)); //Orange Brick
	    c.fillRoundRect(275,400,250,i+15,30,30);
	}

	c.setColor(Color.black);
	c.setFont(createFont("times-new-roman.ttf", 30)); //Button labels
	c.drawString("Easy (1)", 351, 200);
	c.drawString("Medium (2)", 332, 285);
	c.drawString("Hard (3)", 351, 365);
        c.drawString("Extreme (4)", 334, 445);
    }
    
    /**
     * Method to draw level backdrop, with each level having a different specified char code
     * @param section Char code for which level backdrop to draw
     */
    public void drawLevel(char section)
    {
        c.clear();
        switch (section)
        {
            case 'E':
                for(int i = 0;i<50;i++) //Walls
                {
                    c.setColor(new Color(204, 82+i*3, 255));
                    c.drawLine(i,0,i,500);
                    c.drawLine(800-i,0,800-i,500);
                    c.drawLine(0, i, 800, i);
                }
                c.setColor(new Color(204,232,255)); //Background
                c.fillRect(50,50,701,500);
                break;
            case 'M':
                for(int i = 0;i<50;i++) //Walls
                {
                    c.setColor(new Color(204+i, 125+i*2, 82+i*3));
                    c.drawLine(i,0,i,500);
                    c.drawLine(800-i,0,800-i,500);
                    c.drawLine(0, i, 800, i);
                }	
                c.setColor(new Color(204,255,232)); //Background
                c.fillRect(50,50,701,500);
                break;
            case 'H':
                for(int i = 0;i<50;i++) //Walls
                {
                    c.setColor(new Color(255, 84+i, 82+i*3));
                    c.drawLine(i,0,i,500);
                    c.drawLine(800-i,0,800-i,500);
                    c.drawLine(0, i, 800, i);
                }	
                c.setColor(new Color(255,204,232)); //Background
                c.fillRect(50,50,701,500);
                break;
            case 'X':
                for(int i = 0;i<50;i++) //Walls
                {
                    c.setColor(new Color(2+i*2, 135+i*2, 176+i));
                    c.drawLine(i,0,i,500);
                    c.drawLine(800-i,0,800-i,500);
                    c.drawLine(0, i, 800, i);
                }	
                c.setColor(new Color(60, 205, 250)); //Background
                c.fillRect(50,50,701,500);
                break;
        } 
    }

    /**
     * Method to draw instructions scene
     */
    public void instructionsScene()
    {
	for(int i = 0;i<=225;i++) //Draws gradient background
	{
	    c.setColor(new Color(i, 225+i/10, i+30));
	    c.fillOval(-100+i*2, -200+i*2, 1000-i*4, 1000-i*4);
	}
	
	c.setColor(Color.black);
	c.setFont(createFont("Lobster-regular.ttf", 60));
	c.drawString("Instructions", 265, 75);
	
	c.setFont(createFont("times-new-roman.ttf",20)); //Instructions
	c.drawString("The goal is to use the ball to eliminate all the bricks! However, the ball will bounce around!        ", 20, 125);
	c.drawString("Use the platform to prevent the ball from falling. Control the platform using A (left) and            ", 20, 155);
	c.drawString("D (right). If the ball falls, you lose the game. The left, right, and top borders will cause the      ", 20, 185);
	c.drawString("ball to bounce too, so don't worry about losing the game from anywhere except the bottom border       ", 20, 215);
	c.drawString("of the game window! Each brick broken means you are one brick closer to winning. Your score is        ", 20, 245);
	c.drawString("displayed at the top of the screen, with each brick being equal to 50 points. In the Blue level,      ", 20, 275);
	c.drawString("the ball is relatively slow, making it easier. In the Green level, the ball is faster. In the Pink    ", 20, 305);
	c.drawString("level, the ball is even faster! The extreme level ball speed is blazing fast, so good luck!           ", 20, 335);
	c.drawString("In the event you win, or lose, you will be asked if you want to save your score. Then, enter your     ", 20, 365);
	c.drawString("name, and check out the high scores from the menu! Or you can skip saving your score and return       ", 20, 395);
        c.drawString("to the menu. Can you get the highest score? Also, a minor tip: the ball can bounce on the bottom      ", 20, 425);
        c.drawString("of the platform and be saved if you collide sideways just in time, though this is tough to do!        ", 20, 455);
	c.drawString("Please press SPACE to confirm continue!", 245, 490);
    }
    
    /**
     * Method to draw high scores scene
     */
    public void scoresScene()
    {
	for(int i = 0;i<=225;i++) //Draws gradient background
	{
	    c.setColor(new Color(255, i+30, 225+i/10));
	    c.fillOval(-100+i*2, -200+i*2, 1000-i*4, 1000-i*4);
	}
	
	c.setColor(Color.black);
	c.setFont(createFont("Lobster-regular.ttf", 60)); //Button labels
	c.drawString("High Scores", 265, 75);
	
	for(int i = 30; i>0;i--) //Draws 10 rectangles
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green blocks
	    c.fillRect(20, 120, 760, i);
	    c.fillRect(20, 225, 760, i);
	    c.fillRect(20, 330, 760, i);
	    c.fillRect(20, 435, 760, i);
	    
	    c.setColor(new Color(204, 232-i*3, 255)); //Purple blocks
	    c.fillRect(20, 155, 760, i);
	    c.fillRect(20, 260, 760, i);
	    c.fillRect(20, 365, 760, i);
	    
	    c.setColor(new Color(230-i*3, 255-i, 255-i)); //Blue blocks
	    c.fillRect(20, 190, 760, i);
	    c.fillRect(20, 295, 760, i);
	    c.fillRect(20, 400, 760, i);
	    
	    c.setColor(new Color(230-i*3, 255-i, 255-i)); //Blue button
	    c.fillRoundRect(650,30,125,i+20,30,30);
	}
	
	c.setColor(Color.black);
	c.setFont(createFont("times-new-roman.ttf",30)); //Button label
	c.drawString("Clear (6)", 660, 65);
	
	c.setFont(createFont("times-new-roman.ttf",20)); //Lists from 1st to 10th palce
	c.drawString("1",35,143);
	c.drawString("2",35,177);
	c.drawString("3",35,211);
	c.drawString("4",35,245);
	c.drawString("5",35,279);
	c.drawString("6",35,316);
	c.drawString("7",35,352);
	c.drawString("8",35,386);
	c.drawString("9",35,422);
	c.drawString("10",35,457);
        
        c.drawString("Please press SPACE to confirm continue!", 250, 490);
    }
    
    /**
     * Method to draw quit scene, which thanks the user and asks them to confirm exit
     */
    public void quitScene()
    {
	for(int i = 0;i<=255;i++) //Draws gradient background
	{
	    c.setColor(new Color(235, 225+i/10, i));
	    c.fillOval(-100+i*2, -200+i*2, 1000-i*4, 1000-i*4);
	}
        
        for (int i = 50; i>0;i--)
	{
	    c.setColor(new Color(232-i*3, 255, 204)); //Green Bricks
	    c.fillRoundRect(335,15,155,i+15,30,30);
	    c.fillRoundRect(609,224,155,i+15,30,30);
	    c.fillRoundRect(87,376,155,i+15,30,30);

	    c.setColor(new Color(204, 232-i*3, 255)); //Purple Bricks
	    c.fillRoundRect(45,59,155,i+15,30,30);
	    c.fillRoundRect(554,375,155,i+15,30,30);

	    c.setColor(new Color(255, 204, 232-i*3)); //Orange Bricks
	    c.fillRoundRect(609,69,155,i+15,30,30);
	    c.fillRoundRect(65,220,155,i+15,30,30);
	}
        
        c.setColor(Color.black);
        c.setFont(createFont("Lobster-Regular.ttf", 80));
        c.drawString("Thanks for", 240, 180);
        c.drawString("Playing!", 270, 255);
        
        c.setColor(Color.black);
        c.setFont(createFont("times-new-roman.ttf", 30)); //Program details
        c.drawString("Programmer: Ishan Garg", 260, 325);
        c.drawString("Version Date: 16-Nov-20", 252, 360);
        c.drawString("Teacher: Mr. Guglielmi", 260, 395);
        c.drawString("Please press SPACE to confirm exit!", 190, 475);
    }
    
}

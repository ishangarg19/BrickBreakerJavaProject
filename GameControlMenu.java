import java.awt.*;
import hsa.Console;
import javax.swing.*;

/**
 * This class does most of the processing to run the game, calling and using all 
 * classes and controlling the game, like asking what to run, running the game by
 * using all other classes in one spot, and all other processing except specific 
 * game mechanics.
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class GameControlMenu
{
    /**
     * Variable to create Console used in GameControlMenu
     */
    protected Console c;

    /**
     * Variable to create Background used in GameControlMenu
     */
    protected Background b;

    /**
     * Variable to create BrickArray used in GameControlMenu
     */
    protected BrickArray a;

    /**
     * Variable to create Scores used in GameControlMenu
     */
    protected Scores s;

    /**
     * Variable to create JFrame used in GameControlMenu
     */
    protected JFrame f;

    /**
     * Variable to create Platform used in GameControlMenu
     */
    protected Platform p;

    /**
     * Variable to create Ball used in GameControlMenu
     */
    protected Ball ball;
    
    /**
     * Variable to create BallThread used in GameControlMenu
     */
    protected BallThread bt;
    
    /**
     * Variable to create PlatformThread used in GameControlMenu
     */
    protected PlatformThread pt;
    
    /**
     * Variable to create BrickCheck used in GameControlMenu
     */
    protected BrickCheck bc;

    /**
     * Variable to create Brick used in GameControlMenu
     */
    protected Brick br;

    /**
     * Variable to gather input to control all menu selections at the start, level selection, and upon game win/loss
     */
    protected char input;

    /**
     * Variable to check if the game is running or not
     */
    protected boolean gameRunning = false;

    /**
     * Variable to check if the user would like to exit
     */
    protected boolean exit = false;
    
    /**
     * Variable to store the name of the level that's running for scoreboard
     */
    protected String levelRunning;
    
    /**
     * Constructor for GameControlMenu
     * @param c Retrieves primary Console object so there is only one Console used
     * @param b Retrieves primary Background object so there is only one Background used
     * @param s Retrieves primary Scores object so there is only one Scores used
     * @param p Retrieves primary Platform object so there is only one Platform used
     * @param br Retrieves primary Brick object so there is only one Brick used
     * @param ball Retrieves primary Ball object so there is only one Ball used
     * @param a Retrieves primary BrickArray object so there is only one BrickArray used
     */
    public GameControlMenu(Console c, Background b, Scores s, Platform p, Brick br, Ball ball, BrickArray a)
    {
        this.c = c;
        this.b = b;
        this.s = s;
        this.p = p;
        this.br = br;
        this.ball = ball;
        this.a = a;
    }
    
    /**
     * Method to control user input on first main menu screen after splash screen
     */
    public void askData()
    {
        b.menuScene(); //Draws menu backdrop
        
        input = c.getChar();
        while(input != '1' && input != '2' && input != '3' && input != '4') //Checks if user input is a valid selection, and displays an error message otherwise
        {
            JOptionPane.showMessageDialog(f,"Please press the number beside each option only!", "Error", JOptionPane.ERROR_MESSAGE);          
            input = c.getChar();
        }
        
        switch (input) //Runs menu selection results
        {
            case '1':
                levelSelect();
                break;
            case '2':
                b.instructionsScene();
                confirmContinue();
                askData();
                break;
            case '3':
                highScores();
                break;
            case '4':
                goodbye();
                break;
            default:
                break;
        }
    }
    
    /**
     * Method to confirm continue by checking if user presses space, displaying an error message otherwise
     */
    protected void confirmContinue()
    {
        input = c.getChar();
        if(input != ' ')
        {
            JOptionPane.showMessageDialog(f,"Please press SPACE to continue!", "Error", JOptionPane.ERROR_MESSAGE);
            confirmContinue();
        }
        else {} //Simply continues otherwise
    }
    
    /**
     * Method to run high scores scene, showing scoreboard and option to clear it
     */
    public void highScores()
    {
        b.scoresScene();
        
        s.readFile(); //Reads existing scores
        s.drawScores(); //Draws scores on scores scene
        
        input = c.getChar();
        while (input != ' ' && input != '6') //Displays error message if user input is invalid
        {
            JOptionPane.showMessageDialog(f,"Please press SPACE to continue or 6 to clear scores!", "Error", JOptionPane.ERROR_MESSAGE);
            input = c.getChar();
        }
        
        if(input == '6') //Clears scores
        {
            int confirm = JOptionPane.showConfirmDialog(f, "Are you sure?", "Alert",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(confirm == JOptionPane.YES_OPTION) //If the user presses YES in the JOptionPane, it will clear the scores
            {
                s.clearScores();
            }
            highScores();
        }
        else if (input == ' ') //Otherwise continues to main menu
        {
            askData();
        } 
    }
    
    /**
     * Method to display level selection screen and select level
     */
    public void levelSelect()
    {
        b.drawLevelSelect(); //Draws level selection scene
        
        input = c.getChar();
        while(input != '1' && input != '2' && input != '3' && input != '4') //Errortraps invalid input and displays error message
        {
            JOptionPane.showMessageDialog(f,"Please press the number beside each option only!", "Error", JOptionPane.ERROR_MESSAGE);
            input = c.getChar();
        }
        switch (input) //Runs appropriate level afterwards using a controlled recursion
        {
            case '1':
                p.level = 1;
                startLevel('E');
                break;
            case '2':
                p.level = 2;
                startLevel('M');
                break;
            case '3':
                p.level = 3;
                startLevel('H');
                break;
            case '4':
                p.level = 4;
                startLevel('X');
                break;
            default:
                break;
        }
    }
    
    /**
     * Method to start the indicated level, resetting the game and running it while checking for win/loss.
     * JOptionPane confirmDialog learned using https://www.javatpoint.com/java-joptionpane, and made by myself
     * @param select Selects which level is being used out of all options, changing backdrop/ball speed accordingly
     */
    public void startLevel(char select)
    {
        int confirm = JOptionPane.showConfirmDialog(f, "The game will start immediately! Do not press A or D right away, \nas the platform is positioned directly underneath the ball right now!", "Alert",JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if(confirm == JOptionPane.CANCEL_OPTION) //If the user presses CANCEL in the JOptionPane, it will return to the level select screen
        {
            levelSelect();
        }
        else if(confirm == JOptionPane.OK_OPTION) //If the user presses OK in the JOptionPane, it will continue
        {
            gameRunning = true;
            
            bt = new BallThread(c,ball,p); //Creates all three threads used
            pt = new PlatformThread(c,p);
            bc = new BrickCheck(c,ball,br,p,b);

            switch (select)
            {
                case 'E':
                    levelRunning = "Easy";
                    ball.ballDelay = 14; //Sets ball speed
                    b.drawLevel('E'); //Draws backdrop
                    break;
                case 'M':
                    levelRunning = "Medium";
                    ball.ballDelay = 11; //Sets ball speed
                    b.drawLevel('M'); //Draws backdrop
                    break;
                case 'H':
                    levelRunning = "Hard";
                    ball.ballDelay = 8; //Sets ball speed
                    b.drawLevel('H'); //Draws backdrop
                    break;
                case 'X':
                    levelRunning = "Extreme";
                    ball.ballDelay = 6; //Sets ball speed
                    b.drawLevel('X'); //Draws backdrop
                    break;
            }

            a.createGrid(); //Creates brick grid and scoring system
            bc.createBrickChecker();
            bc.displayScore();

            bt.start();
            bc.start();
            pt.start(); //Starts all threads
            
            p.resetPos(); //Resets platform and ball starting positions
            ball.resetPos();

            while(gameRunning) //Runs game
            {
                checkWinLoss();
                p.movePlatform(); //Checks for platform control input
            }
        }
    }
    
    /**
     * Method to check if the player wins or loses, can only be used while all 3 threads are running (during game)
     * Simply for organizational purposes.
     */
    protected void checkWinLoss()
    {
        if(bc.scoreCount >= 1800) //Checks for win, then ends game threads
        {
            gameRunning = false;
            p.level = 0;
            bt.end();
            pt.end();
            bc.end();
            JOptionPane.showMessageDialog(f,"You won!", "Alert", JOptionPane.WARNING_MESSAGE);
            s.newScore = bc.scoreCount;
            s.newLevel = levelRunning;
            b.winScene();
            winGame();
        }
        else if(ball.onCollision("Bottom Wall")) //Checks for loss, then ends game threads
        {
            gameRunning = false;
            p.level = 0;
            bt.end();
            pt.end();
            bc.end();
            JOptionPane.showMessageDialog(f,"You lost!", "Alert", JOptionPane.WARNING_MESSAGE);
            s.newScore = bc.scoreCount;
            s.newLevel = levelRunning;
            b.loseScene();
            loseGame();
        }
    }
    
    /**
     * Method to check if the player has won, and runs scene to save score/return to main menu
     */
    public void winGame()
    {
        input = c.getChar();
        while(input != '1' && input != '2') //If the input is invalid, it displays an error
        {
            JOptionPane.showMessageDialog(f,"Please press the number beside each option only!", "Error", JOptionPane.ERROR_MESSAGE);
            input = c.getChar();
        }
        
        if(input == '1') //Sets the new score and goes to high scores scene
        {
            s.setScores();
            highScores();
        }
        else if(input == '2') //Returns to main menu
        {
            int confirm = JOptionPane.showConfirmDialog(f, "Are you sure?", "Alert",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(confirm == JOptionPane.NO_OPTION) //If the user presses NO in the JOptionPane, it will return to the win screen
            {
                winGame();
            }
            else if(confirm == JOptionPane.YES_OPTION) //If the user presses YES in the JOptionPane, it will continue
            {
                askData();
            }
        }
    }
    
    /**
     * Method to check if the player has lost, and runs scene to save score/return to main menu
     */
    public void loseGame()
    {
        input = c.getChar();
        while(input != '1' && input != '2') //If the input is invalid, it displays an error
        {
            JOptionPane.showMessageDialog(f,"Please press the number beside each option only!", "Error", JOptionPane.ERROR_MESSAGE);
            input = c.getChar();
        }
        
        if(input == '1') //Sets the new score and goes to high scores scene
        {
            s.setScores();
            highScores();
        }
        else if(input == '2') //Returns to main menu
        {
            int confirm = JOptionPane.showConfirmDialog(f, "Are you sure?", "Alert",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(confirm == JOptionPane.NO_OPTION) //If the user presses NO in the JOptionPane, it will return to the lose screen
            {
                loseGame();
            }
            else if(confirm == JOptionPane.YES_OPTION) //If the user presses YES in the JOptionPane, it will continue
            {
                askData();
            }
        }
    }
    
    /**
     * Method to run Quit screen, and will exit from game after confirming
     */
    public void goodbye()
    {
        b.quitScene();
        confirmContinue();
        exit = true;
        System.exit(0);
    }
}

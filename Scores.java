import java.awt.*;
import hsa.Console;
import java.io.*;
import javax.swing.*;

/**
 * This class is used to read and write to the game_scores.txt file to write new 
 * scores and read scores to display them. All scores are sorted so they are actually
 * "high" scores, with all scores after the top 10 filled taking their respective
 * place on the scoreboard, if they are high enough to beat the 10th at least.
 * Teacher: Mr. Guglielmi
 * @author Ishan Garg
 * @version 16-Nov-20
 */
public class Scores
{
    /**
     * Variable to create Console used in Scores
     */
    protected Console c;

    /**
     * Variable to create JFrame used in Scores
     */
    protected JFrame f;

    /**
     * Variable to create Background used in Scores
     */
    protected Background b;

    /**
     * Variable to store the scores for each player on the scoreboard
     */
    protected int[] scores;
    
    /**
     * Variable to store the level played for each player on the scoreboard
     */
    protected String[] levels;

    /**
     * Variable to store the names of each player on the scoreboard
     */
    protected String[] names;

    /**
     * Variable to store the number of players currently on the scoreboard
     */
    protected int numPlayers = 0;

    /**
     * Variable to store the name of a new player who has decided to submit their result on the scoreboard
     */
    protected String newName;
    
    /**
     * Variable to store the score of a new player who has decided to submit their score on the scoreboard
     */
    protected int newScore;
    
    /**
     * Variable to store the level played by a new player who has decided to submit their score on the scoreboard
     */
    protected String newLevel;
    
    /**
     * Variable to check if the user would like the file cleared
     */
    protected boolean clearFile = false;
    
    /**
     * Constructor for Scores, initializes names and scores arrays to the maximum size of ten.
     * @param c Retrieves Console being used in the program so there is only one Console
     * @param b Retrieves Background being used in the program so there is only one Background
     */
    public Scores(Console c, Background b)
    {
        this.c = c;
        this.b = b;
        names = new String [10];
        scores = new int [10];
        levels = new String [10];
    }
    
    /**
     * Method to read the names and scores from the file storing the scores and assigns it to the arrays
     */
    public void readFile()
    {
        String line; //Stores data read from file
        BufferedReader in;

        try //Trys to read the file, which is certainly valid considering there is only one file being read
        {
            int count = 0; //Number of players that have been read
            
            in = new BufferedReader (new FileReader ("game_scores.txt")); //Accesses the scores file
            
            if(!clearFile) //Does not read new data if the user is having the file cleared, otherwise reads normally
            {
                line = in.readLine ();
                numPlayers = Integer.parseInt(line);

                line = in.readLine ();
                line = in.readLine ();
                while (count<numPlayers) //Retrieves data constantly for the total size of the array/number of students
                {
                    names [count] = line;
                    line = in.readLine ();

                    scores [count] = Integer.parseInt(line);
                    line = in.readLine ();
                    
                    levels [count] = line;
                    line = in.readLine ();
                    line = in.readLine ();

                    count++;
                }
            }
            else
            {
                in.close(); //Closes file without reading otherwise to prevent errors
            }
        }
        catch (Exception e)  //Catches exception and informs user that there was an error
        {
            JOptionPane.showMessageDialog(f, "Error Occured. Please try again.", "Error", JOptionPane.WARNING_MESSAGE);
        }

        
    }
    
    /**
     * Method to write new data to the file
     */
    public void writeFile()
    {
        PrintWriter output;

        try //Tries to write to the file
        {
            output = new PrintWriter (new FileWriter ("game_scores.txt"));
            
            if(!clearFile) //Only writes all scores to the file when the user is not having the file cleared
            {
                output.println(numPlayers);
                for(int i = 0; i<numPlayers; i++)
                {
                    output.println();
                    output.println (names[i]);
                    output.println (scores[i]);
                    output.println (levels[i]);
                }
            }
            else //Otherwise sets the number of students to zero and assigns *no player* tag to each name position, the score of zero, and a level of N/A as placeholders
            {
                numPlayers = 0;
                output.println(numPlayers);
                for(int i = 0; i<10-numPlayers; i++)
                {
                    output.println();
                    output.println("*no player*");
                    output.println(0);
                    output.println("N/A");
                }   
            }            
            output.close ();
        }
        catch (IOException e) //Informs the user there has been an error
        {
            JOptionPane.showMessageDialog(f,"Something went wrong while uploading score.", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Method to sort the scores from highest to lowest
     * Created with reference to a given example
     * @param scoreArr Retrieves Scores array to sort
     * @param nameArr Retrieves Names array to assign the same index as scores to after sorting
     * @param levelArr Retrieves Levels array to assign the same index as scores to after sorting
     */
    protected void sortScores(int[] scoreArr, String[] nameArr, String[] levelArr) 
    {  
        //One by one move boundary of unsorted subarray 
        for (int i = 0; i < 9; i++) 
        { 
            //Find the maximum element in unsorted array 
            int maxIndex = i; 
            for (int j = i+1; j < 10; j++) 
            {
                if (scoreArr[j] > scoreArr[maxIndex]) 
                {
                    maxIndex = j;
                }
            }
            
            // Swap the found maximum element with the first element
            int temp = scoreArr[maxIndex];
            String tempName = nameArr[maxIndex];
            String tempLevel = levelArr[maxIndex];
            
            scoreArr[maxIndex] = scoreArr[i]; 
            nameArr[maxIndex] = nameArr[i];
            levelArr[maxIndex] = levelArr[i];
            
            scoreArr[i] = temp;
            nameArr[i] = tempName;
            levelArr[i] = tempLevel;
        } 
    }
    
    /**
     * Method to set a new score by asking for the player's name then writing new data to file
     */
    protected void setScores()
    {
        readFile(); //Retrieves existing valid data so it is not overwritten
        newName = JOptionPane.showInputDialog(f, "Please enter your name: ", "Name", JOptionPane.QUESTION_MESSAGE); //Gets the user's name
        if (newName == null) //If the user presses the CANCEL option in the JOptionPane, it sets newName to null automatically, so it will not save the score by setting it to 0
        {
            JOptionPane.showMessageDialog(f, "You will now go to the high scores \nscene without saving your score. ", "Done", JOptionPane.INFORMATION_MESSAGE);
            newScore = 0;
        }
        else if(newName.equals("") || newName.equals("\n") || newName.equals(" "))
        {
            JOptionPane.showMessageDialog(f, "Blank names are not permitted. ", "Alert", JOptionPane.WARNING_MESSAGE); //Gets the user's name
            setScores();
        }
        else
        {
            if(newScore != 0) //Disregards any score that equals 0
            {
                numPlayers++;
            }
            if(numPlayers <=10 && newScore != 0) //Always keeps the number of players <=10
            {
                names[numPlayers-1] = newName; //Assigns latest elements in arrays to the new players name and score
                scores[numPlayers-1] = newScore;
                levels[numPlayers-1] = newLevel;
                JOptionPane.showMessageDialog(f,"Complete. Your result is now on the leaderboard!", "Done", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(newScore == 0) //Informs user that a score of 0 is invalid
            {
                JOptionPane.showMessageDialog(f,"Sorry, your score is not high enough \nto make it to the scoreboard.", "Done", JOptionPane.INFORMATION_MESSAGE);
            } 
            else if(numPlayers > 10) //Replaces smallest score with the new score after 10 scores are already present
            {
                sortScores(scores,names,levels);
                numPlayers = 10; //There can only be 10 scores on the scoreboard, so there can only be 10 players' scores, not 11 or 12 etc.
                
                if(scores[9]>newScore) //If the scores on the scoreboard are all larger than the new score, it will not upload to scoreboard as it is not a high score
                {
                    JOptionPane.showMessageDialog(f,"Sorry, your score is not high enough \nto make it to the scoreboard.", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(scores[9]<=newScore) //Only assigns the new score to the scoreboard if it is larger than or equal to the 10th place score on the leaderboard
                {
                    names[numPlayers-1] = newName; //Assigns latest elements in arrays to the new players name and score
                    scores[numPlayers-1] = newScore;
                    levels[numPlayers-1] = newLevel;
                    JOptionPane.showMessageDialog(f,"Complete. Your result is now on the leaderboard!", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            writeFile(); //Writes scores to file including the new score
        }
    }
    
    /**
     * Method to clear all scores on the scoreboard
     */
    protected void clearScores()
    {
        clearFile = true; //Makes writeFile() write placeholders, not the score
        writeFile(); //Writes placeholders to file
        clearFile = false;
        for (int i = 0; i<10; i++) //Clears array contents
        {
            names[i] = null;
            scores[i] = 0;
            levels[i] = null;
        }
        JOptionPane.showMessageDialog(f,"Scoreboard cleared!", "Done", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Method to draw the scores on the scoreboard after retrieving them from the file
     */
    public void drawScores()
    {
        c.setFont(b.createFont("times-new-roman.ttf",20));
        int yPos = 143;
        
        sortScores(scores,names,levels); //Sorts all scores
        
        for (int i = 0; i<10; i++) //Draws all ten high score names and scores and levels in each slot
        {
            if(scores[i] > 0) //Only draws score if the placeholder per position is not still present with the invalid 
            {                 //score of 0, which is eliminated while setting scores but is present after clearing scoreboard
                c.drawString(names[i],215,yPos);
                c.drawString(""+scores[i],455,yPos);
                c.drawString(levels[i], 645, yPos);
                yPos += 35;
            }
        }
    }
}

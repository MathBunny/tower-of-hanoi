/*
Horatiu Lazu
Tower of Hanoi
December 23, 2013 - Version 1.0.0.1
The main purpose of this application is to allow a user to play a Tower of Hanoi game, which exhibits several features including high scores, saving and opening games, several levels, along with nice graphics.
The splashScreen will always introduce the user once to the program, and then the main menu will control the main flow of the program. The user may choose to play now, which
includes the making of a new game. This means that the user may choose the difficulty, and they will be presented by a level selection screen. The user will select  a level, and
then enter their name. They can then play the game, and save and exit when they wish. When they save, they will be sent back to the main menu (and sent to the save screen).
The open game will present it to the open game window, and then display() where they can play the game, and resave if they desire to do so. High scores will present the user
to the top 10 high scores from within the game. Instructions will present the user to the instructions screen, which will inform the user how to play the game. GoodBye will
simply close the application with a nice image.

Variable Dictionary:
name | type | purpose
---------------------
c | Class Reference | Permits the ability to access the hsa Console library.
d | Class Reference | Permits the ability to access the hsa console library (used for display user input).
image  | static Image | Stores the currentimage being processed.
PICTURE_PATH_PYRAMID | private static final String | Allows the mainMenu to have a nice gradient background - image file name.
PICTURE_PATH_SPLASHSCREEN| private static final String | Allows the splashScreen to have a nice title along with an image as a gradient- image file name.
PICTURE_PATH_HIGHSCORES| private static final String | Allows the highescores to have a nice gradient pyramid with a metallic background- image file name.
PICTURE_PATH_SAVEGAME| private static final String | Allows the save game to have a nice image - image file name.
PICTURE_PATH_OPENGAME| private static final String |Allows the open game to have a nice image - image file name.
PICTURE_PATH_LEVELSELECTION| private static final String |Allows the level selection to have a nice title - image file name.
PICTURE_PATH_GOODBYE| private static final String | Allows the goodbye to have a nice image - image file name.
PICTURE_PATH_INSTRUCTIONS| private static final String | Allows the instructions to have a nice image - image file name.
PICTURE_PATH_WINNER| private static final String | Allows the user to be greeted by a nice image because they won! - image file name.
PICTURE_PATH_DISPLAY| private static final String | Allows the user to have a nice gaming interface - image file name.
board [] [] | array of int | Stores the board position and peg values.
selection | int | Stores the first selection of the user.
selection2 | int | Stores the destination tower for the user.
choice | int | Stores the choice for the user within the mainMenu.
difficulty | int | Stores the difficulty of the game, for 4 being easy, 6 being medium and 8 being hard.
playName | String | Stores the current player's name.
userInput | char | Stores the user's input for display and askData.
turns | int | Stores the number of turns the user went through.
POSITIONS | static final int | Stores the maximum position size of the board variable.
PYRAMIDS | static final int | Stores the maximum number of positions that the board may support.
MAXSCORES | static final int | Stores the maximum number of scores stored by the high scores.
*/


import hsa.*; //Used for message boxes
import java.awt.*; //Used for Java colours
import java.applet.*; //Used for the picture viewing
import javax.swing.JOptionPane; //Used for JOptionPane
import java.io.*; //Used for file IO

public class TowerOfHanoi
{
    Console c;
    Console d;
    static Image picture;
    private static final String PICTURE_PATH_PYRAMID = "images/PYRAMID.png";
    private static final String PICTURE_PATH_SPLASHSCREEN = "images/splashScreen.jpg";
    private static final String PICTURE_PATH_HIGHSCORES = "images/HighscoresPyramid.jpg";
    private static final String PICTURE_PATH_SAVEGAME = "images/SaveGame.jpg";
    private static final String PICTURE_PATH_OPENGAME = "images/openGame.png";
    private static final String PICTURE_PATH_LEVELSELECTION = "images/levelSelectionTitle.png";
    private static final String PICTURE_PATH_GOODBYE = "images/goodBye.png";
    private static final String PICTURE_PATH_INSTRUCTIONS = "images/instructions.png";
    private static final String PICTURE_PATH_WINNER = "images/winner.png";
    private static final String PICTURE_PATH_DISPLAY = "images/display.png";
    private static final String PICTURE_PATH_LOSER = "images/loser.png";

    private static final int POSITIONS = 10;
    private static final int PYRAMIDS = 3;
    private static final int MAXSCORES = 10;

    int[] [] board = new int [POSITIONS] [PYRAMIDS];

    int selection, selection2, choice, difficulty = 4;
    String playerName;
    char userInput = 'd';
    int turns = 0;

    /*
    This method calls BackgroundMusic, to play a song in the background.

    Variable dictionary:
    name | type | purpose
    ---------------------
    b | Class reference variable | Used to run the BackgroundMusic() class.

    */
    public void playMusic ()
    {
	BackgroundMusic b = new BackgroundMusic ();
	b.run ();
    }


    //Displays a winning image, which pauses the screen, and then calles highScores to modify the scores accordingly.

    public void winner ()
    {
	loadImage (PICTURE_PATH_WINNER, true, true);
	setHighScores ();
    }


    /*
    This method erases lines within the given range.

    Variable Dictionary:
    name | type | purpose
    -----------------------
    selection | int | Is used to find the column of the selection, and the line that must be erased.

    Essentially the method works such that it will move the cursor to the specific line, it will then output on that line and then set the cursor back to that position.
    */


    private void eraseLine (int selection)
    {
	d.setCursor (selection, 1);
	d.println ();
	d.setCursor (selection, 1);
    }


    /*
    This method asks the user for the destination and selection towers that they wish to move in the game.

    The first if statement will check to see if the first selection was not made yet, and it will set the cursor accordingly.
    The else if will work such that if the first selection was made and the second was not, it will only erase the second line.
    The else will work such that if there was an invalid range or the program never ran then it'll cause a clear.

    The if (selection == -1) works such that if the first number was never provided a value for, it will ask the user to input the data.
    The large if statement will verify if the character is within a given ASCII value range, while it is not one of the action keys (e, s, etc.)
    The following else and if will return the method if the user chooses to exit the game or to save the game.

    The if (board[0][selection]) is used to determine if there is no block in that current position, such that the user cannot pick any block there, and it will return that it is invalid.

    The large following if statement will verify if the userInput char for the second selection is proper, and it within the given range with the exception of the action keys.
    The else and if works such that if it is one of the action keys being pressed, then the method will return.

    The following if statement with nested ternary operators works such that it will call the highlightIndex method, which will return the top value of the blocks to ensure that the array value of the starting point is less than the end point.
    This is because the user should ONLY be permitted to place smaller blocks on top of bigger blocks.

    Due to the nature of this application the value / size of the blocks is stored within the array, using the formula at (2 * n) - 1. The array index will then count the height at which the block is, using highlightIndex.

    The very last if statement verifies if the user place the same number as the starting / destination, and the method will repeat without propmpting the user.
    */
    private void askData ()
    {
	if (selection == -1 && selection2 != -1)
	    eraseLine (1);
	else if (selection2 == -1 && selection != -1)
	    eraseLine (2);
	else
	    d.clear (); //Not always nessesary but hey (sometimes).
	if (selection == -1)
	{
	    d.print ("Please enter the first tower.");
	    userInput = d.getChar ();
	    if ((userInput - '0' > 3 || userInput - '0' < 1) && userInput != 'e' && userInput != 'E' && userInput != 's' && userInput != 'S') //No NumberFormatException, yay!
	    {
		JOptionPane.showMessageDialog (null, "Error! Invalid range! The range must be between 1 and 3!");
		selection = -1;
		askData ();
		return;
	    }
	    else
		if (userInput == 'e' || userInput == 'E' || userInput == 's' || userInput == 'S')
		    return;
	    d.println ((selection = (userInput - '0') - 1) + 1);
	    if (board [0] [selection] == 0)
	    {
		JOptionPane.showMessageDialog (null, "Error! There is no block there!");
		selection = -1;
		askData ();
		return;
	    }
	}
	d.print ("Please enter the destination tower.");
	userInput = d.getChar ();
	if (userInput - '0' > 3 || userInput - '0' < 1 && userInput != 'e' && userInput != 'E' && userInput != 's' && userInput != 'S')
	{
	    JOptionPane.showMessageDialog (null, "Error! Invalid range! The range must be between 1 and 3!");
	    selection2 = -1; //Here.
	    askData ();
	    return; //Huh?
	}
	else
	    if (userInput == 'e' || userInput == 'E' || userInput == 's' || userInput == 'S')
		return;
	d.println ((selection2 = (userInput - '0') - 1) + 1);
	if ((highlightIndex (selection, false, false) != -1 && highlightIndex (selection2, false, false) != -1 && board [((difficulty - (highlightIndex (selection, false, false)) == -1) ? (0):
	((difficulty - highlightIndex (selection, false, false))))] [selection] > board [(difficulty - (highlightIndex (selection2, false, false)) == -1) ? (0):
		(difficulty - ((highlightIndex (selection2, false, false))))] [selection2]))
	{
	    selection = -1;
	    selection2 = -1;
	    JOptionPane.showMessageDialog (null, "Error! Invalid choices! You cannot place a big block over a smaller block.");
	    askData ();
	    return;
	}
	if (selection == selection2)
	{
	    selection = -1;
	    selection2 = -1;
	    askData ();
	    return;
	}
    }


    /*
    This method acts as the main processing method for the game, by calling helper methods where required.
    The first while loop essentially executes until the user input is not e. The first if statement would automatically close the method if the user chooses to close within askData().
    The next variable assigning would be to change the array variables accordingly in order to ensure that the pieces are moving accordingly.
    The ternary operator works such that it uses the method highlightIndex to retrieve the top block, and while error trapping it to ensure that no crashing occurs, it will calculate the destination and the arrival point.
    This works such that the highlightIndex method will cycle through the for loop and retrieve the location of the top sector.
    The first ternary operator condition will indicate if the result is -1, and then it will move the bottom, else it will move the literal value plus the difficulty minus one.
    The assigned value will be the value of the original block's top point's value, which is accessed from the board[][] array.
    The next variable assigning works such that
    The image for display() is then loaded, and the board is draw.

    The if statement will then return if hasWon() == true, or if the user input is to exit (again), or the user wants to save.
    Similarily, display will leave if the user has played for 1000 turns, which will in turn resolve to the "loser" screen.
    Otherwise, the if statement will send the program back to askData();
    */

    public void display ()
    {
	c.setFont (new Font ("Arial", Font.BOLD, 50));
	userInput = 'H';
	selection = 0;
	selection2 = 0;
	while (userInput != 'e' && userInput != 'E')
	{
	    if (selection == -1 || selection2 == -1)
		return;
	    board [((highlightIndex (selection2, false, false)) == -1) ? (0):
	    ((difficulty + 1) - highlightIndex (selection2, false, false))] [selection2] = board [difficulty - highlightIndex (selection, false, false)] [selection];
	    board [difficulty - highlightIndex (selection, false, false)] [selection] = 0; //Clear variable.
	    loadImage (PICTURE_PATH_DISPLAY, true, false);
	    c.setColor (Color.yellow);
	    drawBoard ();
	    selection = -1;
	    turns++;
	    c.setColor (Color.green);
	    c.drawString ("Moves: " + turns, 390, 310);
	    selection2 = -1;
	    d.clear ();
	    if (userInput == 'e' || userInput == 'E' || hasWon () == true || userInput == 's' || userInput == 'S')
		return;
	    else if (turns == 1000)
		return;
	    else
		askData ();
	}
    }


    /*
    This method would verify is a winner is present. The return is a boolean which indicates if the user has won.

    Variable dictionary:
    name | type | purpose
    ------------------------
    i | int | Used to store the loop progress (array index)

    The for loop is used to go through every height from within the pyramid, and the if statement is used to verify if it equals to 0. If it equals to zero it means that there is no block present, therefore the user did not win.
    The start value for the for loop is 0, and increments by 1 while it is smaller than difficulty.
    If the return never occurs, the user therefore wins.
    */


    private boolean hasWon ()
    {
	for (int i = 0 ; i < difficulty ; i++)
	    if (board [i] [2] == 0)
		return false;
	return true;
    }


    /*
	This method would draw the whole board, specifically designed for the display method in mind.

	Variable dictionary:
	name | type | purpose
	-----------------------
	z | int | Used to store the pyramid number as this algorithem searches through the different array values.
	i | int | Used to store the height number from the array from within this algorithem.

	The two for loops simply cycle between the possible array combinations to output every possible shape, running until the maximum possible index is achieved.
	The start and stop values for the first loop are 0 to 3 incrementing by 1 (to ensure that it goes through every position possible).
	The start and stop conditions for the second loop are starting at 0, and going until it is less than 8 (because the maximum difficulty is 8, and is still preferred to picture some in case the game data is modified past difficulty), incremented by 1.
	The first ternary operator would find the distance required for every difficulty, and simiarily for the second difficulty (to ensure proper fitting on the screen). The numbers were calculated within all ternary operator to permit the pyramid to fit the screen at the best possible scale with space on the sides and in between the pyramids.
    */


    private void drawBoard ()
    {
	for (int z = 0 ; z < 3 ; z++)
	    for (int i = 0 ; i < 8 ; i++)
		drawSector (board [i] [z], (difficulty == 8) ? (22):
		(difficulty == 6) ? (30):
		    (48), (difficulty == 8) ? (10):
			(difficulty == 6) ? (5):
			    (2), z, difficulty, 0, 0, difficulty - i);
    }


    /*
    The highlightIndex method indicates the top location of a specific pyramid location, which is used to drag the top block to another sector. Passes selection, firstSelection and a boolean shouldAdjustDifficulty

    Variable Dictionary:
    name | type | purpose
    ---------------------
    selection | int | States the selected pyramid to analyze (for example 0 for the left, or 2 for the very right pyramid)
    firstSelection | boolean | States if the first selection should be included or not, such that -1 will be returned if there's no blocks, or if 1 should be used if highlighting would be implemented (added feature for future version features)
    shouldAdjustDifficulty | boolean | States if the top sector must be indicated below or higher by 1, used for highlighting top possible elements or not - used for future version feature implementations.
    i | int | Used as a loop variable to keep track of the array index.

    The first for loop functions such that it will process through every index within the pyramid in the board[] [] array.
    The start value for the for loop is the difficulty, and will stop once it's not equal to 0 or greater.
    The following if statement will then verify if the array value is not equal to 0, and if so it will return the height of the particular sector (such that difficulty - i is the return).
    If the if statement never returns anything, the default value returned will be -1 if firstSelection would be false to indicate no blocks are present, however, otherwise the bottom block will be indicated or one below depending on the boolean values as indicated within the ternary operator.

    The ternary operator is simply used for the distribution of values, depending if -1 should be returned, or another number which can be used for highlighting possible block pieces.
    */
    private int highlightIndex (int selection, boolean firstSelection, boolean shouldAdjustDifficulty)  //ADD THE CHANGING STARTING VARIABLE! EVER USE THE SHOULDADJUST AS TRUE?? HUH?
    {
	for (int i = difficulty ; i >= 0 ; i--)
	    if (board [i] [selection] != 0)
		return difficulty - i;
	return (firstSelection == false) ? (-1):
	((shouldAdjustDifficulty == false) ? (difficulty):
		(difficulty != 8) ? (difficulty):
		    (difficulty - 1));
    }


    /*
    drawSector draws rectangles, with according preferences which will change according to how the pyramids will fit on the screen, undergoing several calculations. Passes int i, int size, int shift, int pyramidNumber, int indent, int x, int y, int heightBoost.
    Variable Dictionary:
    variable name | type | purpose
    i | int | Signifies the size of the block required (knowing that the size increases by 2 * n - 1 if the first step is 1 block, second is 3 blocks, third is 5 blocks, etc. within a normal pyramid)
    size | int | Indicates the size increase of the individual block.
    shift | int | Indicates particular shifts that may be required to properly fit on the screen (for the starting point of drawing the graphics).
    pyramidNumber | int | Indicates the starting position which may be multiplied by multiple factors.
    indent | int | Used to shift over the pyramid a specific number of blocks.
    x | int | Used to move the blocks over by a specific X margin, mainly for animation purposes (may be implemented in later versions which include animated piece movement).
    y | int | Used to move the blocks over by a specific Y margin, mainly for animation purposes (may be implemented in later versions which include animated piece movement).
    heightBoost | int | Used to move the blocks over by a specific Y margin, which may be used in particular screens for additional height increase depending on the animation and graphical needs (may be implemented in later versions which include animated piece movement).

    The calculations essentially assure that a pyramid will be constructed to the degree that it will appear visual pleasing. The basic principal behind the calculations is the formula 2 * n - 1, such that the top block is 1, below is 3, below that is 5, etc. Knowing that formula, the blocks will be generated by implementing a simple for loop.
    */
    private void drawSector (int i, int size, int shift, int pyramidNumber, int indent, int x, int y, int heightBoost)
    {
	c.fillRect (5 + (indent - i) * size + shift + (pyramidNumber * size * ((2 * difficulty) - 1) + shift * pyramidNumber) + x, 755 - size * (difficulty + 1 - heightBoost) + y, (2 * (i) - 1) * size, size);
    }


    /*
    This method closes the application, by closing both Console c & Console d.

    Variable Dictionary:
    name | type | purpose
    -----------------------
    e | Exception variable | Used in case an exception occurs during the Thread.sleep(int ms).
    */
    public void goodbye ()
    {
	try
	{
	    Thread.sleep (5000);
	}
	catch (Exception e)
	{
	}
	c.close ();
	d.close ();
    }


    /*
    This method would draw the configuration screen pyramid, so that it will fit the whole screen accordingly. It calls drawSector() to output the particular graphics.

    Variable Dictionary:
    name | type | purpose
    ---------------------
    i | int | Used in the for loop to identify the size of the blocks as it ascends from a difficulty level - essentially assists in the outputting of the pyramid.

    The for loop takes the blocks and changes the passing values to drawSector accordingly, such that it will generate a large full screen image.
    The for loop starts at the value of the difficulty (such that easy = 4, medium = 6 and hard = 8), and it will then decrease until it is no longer greater than or equal to 0.

    */

    private void drawFrameConfigureScreen ()
    {
	for (int i = difficulty ; i >= 0 ; i--)
	    drawSector (i, 68, 0, 0, 8, 0, 0, i);
    }


    /*
    This method allows the user to chose between several levels, by using simple controls in a nice interface.

    Variable Dictionary:
    name | type | purpose
    -----------------------
    i | int | Used within a for loop to adjust array values.

    The while loop functions such that the user is able to shuffle through the options indefinately until he / she has entered a key other than space. The loop breaks once the input is other than ' '.
    The ternary operator from within the setColor is used such that the color will be changed quickly in a little lines as possible, such that easier levels (4) is green, (6) is yellow and (8) hard is red.
    The following ternary operator works such that the output text will be changed and centered accordingly, for difficulty 4 = Easy, 6 = Medium and Hard = 8 / else.
    The following if condition would be used to clear if nessesary, in a efficient manner (when the maximum size was achieved).
    The following if condition would then change the value or stop the method.
    The following ternary operators then change the value of difficulty accordingly, by incrementing it up by two unless it achieved 8, which it will then lower to 4.

    The while loop runs forever until the given conditions are valid by the if statement.
    The if statement will verify if the number is a null, and if so then the user will be returned to the main menu.
    The if statement will verify the length of the String, and String values to verify if the user input is acceptable. If it is, then it will break the while loop, otherwise it will execute indefinately.

    The following for loop will then provide values to plurge the first pyramid, from the highest possible pyramid count.
    The value starts at 8 and runs until it reaches 0, which applies to ALL of the following for loops - incrementation goes down by one. This is such that it will access every single array possible, and to ensure that ALL of the fields are cleared when a new game is made.
    The next for loop will then provide values to the array to fill the first pyramid according to the difficulty.
    The next two for loops will then plurge the values again, to ensure that no traces of previous saved games will interfere with the gameplay in the new game.

    This method returns a boolean which will indicate if the player wants to play the game.
    */


    public boolean configureScreen ()
    {
	c.setTextBackgroundColor (Color.black);
	c.clear ();
	c.drawImage (picture, 0, 0, null); //DRAW THE IMAGE!
	while (true)
	{
	    c.setFont (new Font ("Algerian", Font.BOLD, 40));
	    c.setColor (Color.black);
	    c.fillRect (0, 150, 1050, 300); //MAKE IT MORE ACCURATE!
	    c.setColor ((difficulty == 4) ? (Color.green):
	    (difficulty == 6) ? (Color.yellow):
		Color.red);
	    c.drawString ((difficulty == 4) ? ("Easy: 1x Score Multiplier"):
	    (difficulty == 6) ? ("Medium: 10x Score Multiplier"):
		("Hard: 100x Score Multiplier"), (difficulty == 4) ? (220):
		    (difficulty == 6) ? (210):
			(228), 200);
	    if (difficulty == 4)
	    {
		difficulty = 8;
		c.setColor (Color.black);
		drawFrameConfigureScreen ();
		difficulty = 4;
	    }
	    c.setColor (Color.white);
	    drawFrameConfigureScreen ();
	    if (c.getChar () == ' ')
		difficulty = (difficulty == 8) ? (difficulty = 4):
		(difficulty += 2);
	    else
		break;
	}
	while (true)
	{
	    playerName = JOptionPane.showInputDialog ("Please enter your username.", "Name");
	    if (playerName == null)
		return false;
	    if (playerName.equals ("") || playerName.length () < 3 || playerName.equals ("Name"))
		JOptionPane.showMessageDialog (null, "Error! Invalid name! Please remember the name CANNOT be  less than 3 letters, or be blank - or cannot be name!");
	    else
		break;
	}

	for (int i = 8 ; i >= 0 ; i--) //Why two?
	    board [i] [0] = 0;
	for (int i = difficulty ; i > 0 ; i--)
	    board [difficulty - i] [0] = i;
	for (int i = 8 ; i >= 0 ; i--) //Why two?
	    board [i] [1] = 0;
	for (int i = 8 ; i >= 0 ; i--) //Why two? BECAUSE YOU NEED TO RESET THE FIELDSSSSSSSSSs stupid Horatiu
	    board [i] [2] = 0;
	turns = -1;
	return true;
    }


    /*
    The saveGame method gets the desired file name from the user, and asks if the program should replace the file.
    Variable Dictionary:
    name | type | purpose
    --------------------------
    fileName | String | Used to store the desired file name for the save file.
    isFound | boolean | Used to find out if the file was found.
    n | int | Used to find the selection option from the user.
    options | Array of Objects | Used to show the options from within the JOptionPane.
    e | FileNotFoundException | Used when calling the FileNotFoundException.
    e | IOException | Used to prevent input output related errors / program crashing.

    The first try catch tries to read from the file, in order to look if it exists. If any error occups it will be outputted to the user.
    The second if statement will check to see if the isFound boolean is true, such that if it needs to prompt the user to save.
    If the user has to be prompted, a JOption pane would appear with relevant options. If the input would be to not replace, nothing will be outputted. Else, the file will be overwritten.


    */

    public void saveGame ()
    {
	loadImage (PICTURE_PATH_SAVEGAME, true, false);
	c.drawImage (picture, 0, 0, null);
	c.setColor (Color.white);
	drawBoard ();
	String fileName = JOptionPane.showInputDialog ("Please enter the filename.", "Filename");
	if (fileName == null)
	    return;
	boolean isFound = true;
	try
	{
	    BufferedReader in = new BufferedReader (new FileReader ((fileName)));
	}
	catch (FileNotFoundException e)
	{
	    isFound = false;
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "An IO exception has occured. Please try again later.");
	    return; //Should?
	}
	if (isFound == true)
	{
	    Object[] options = {"Replace",
		"No"};
	    int n = JOptionPane.showOptionDialog (null,
		    "The file has been found."
		    + "Would you like to replace it?",
		    "Error: File is Already Found!",
		    JOptionPane.DEFAULT_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options [1]);

	    if (n == 1)
		return;
	    else
	    {
		saveGame (fileName);
		return;
	    }

	}
	saveGame (fileName);
    }


    /*
    This method outputs the variables stored within the game, and oututs them to a file format in order for the user to play the game later. Passes a String for the fileName.

    Variable Dictionary:
    name | type | purpose
    ---------------------------
    out | PrintWriter reference variable | Used to provide access to the printWriter, and to toutput to the fileName.
    i | int | Used within a for loop to keep count of the index within the board array (the position).
    z | int | Used within a for loop to keep count of the index within the board array (the pyramid number).
    e | IOException e | Used in case there is a File IO related exception within the application.
    fileName | String | Used to store the preferred file name for the output save.

    The try catch is used to catch IO related exception that may occur, for instance not being able to output the file.

    The for loops (nested) are used to cycle between the values of the for loop. The first for loop starts at 0 and stops once it's no longer less than the difficulty, and the other for loop starts at 0 and stops at 2.
    */

    private void saveGame (String fileName)
    {
	try
	{
	    PrintWriter out = new PrintWriter (new FileWriter ((fileName)));
	    out.println ("(c) Horatiu TOWERS OF HANOI VERSION 1.0.0.0 PROTECTED");
	    out.println (difficulty);
	    out.println (turns - 1);
	    out.println (playerName);
	    for (int i = 0 ; i < difficulty ; i++)
		for (int z = 0 ; z < 3 ; z++)
		    out.println (board [i] [z]);
	    out.close ();
	    return;

	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "A IO error has occured. Please try again later.");
	    return;
	}
    }


    /*
    This method opens the game for the user, and loads the appropriate variables for the game to commence. The boolean aspect indicates if the game has loaded successfully or not, and if it is true then the game will commence (display).

    Variable Dictionary:
    name | type | purpose
    ---------------------
    fileName | String | Used to store the fileName where the game information may be retrieved.
    e | FileNotFoundException variable | Used to indicate if the file was not found.
    e | NumberFormatException variable | Used to indicate if read value is not a number.
    e | ArrayOutOfBoundsException variable | Used to indicate if the given values get out of the given range.
    e | IOException variable | Used to indicate if there was a file IO related error.
    i | int | Used to count the index value from within the file inputs.
    z | int | Used to count the pyramid location when receiving the values from the file.
    in | BufferedReader reference | Used to provide access to the file of the variable fileName.
    verification | array of boolean | Used to verify that all of the numbers are applied accordingly.
    counter | int | Used to count the number of given blocks that are found - this is to ensure that all of the values are within bounds.
    **Special note: Variables i and z will be repeated throughout the for loops, with similar purposes.


    The try and catches work such that the program is uncrashable, and that an appropriate error message will appear for every single scenario.
    If the FileNotFoundException will occur, then it occured because the file was not found.
    If the NumberFormatException will occur, then it is because the file was manipulated.
    If the IOException will occur, then a general file input output occured.

    The first if structure verifies if the header is valid, and will return false if it is not - along with outputting an appropriate error message.
    Afterwards the values are inputting from the file accordingly.
    The if structure verifies if the difficulty is valid, such that it cannot be anything other than 4, 6 or 8.
    The following if stucture verifies if the number of moves is possible to exist.

    The following for loop is used to keep and input the index of the variable, and the next for loop is used for the pyramid number.
    The i starts at 0, and progresses until it reaches the value of difficulty, increasing at an incrementation rate of 1.
    The z starts at 0, and would go until it is less than 3 as it increments at a rate of 1.
    The if statement from within is used to verify if the given numbers are possible and valid, such that they are not extremities that will cause the graphics to distort.
    The for loop following will adjust the different values from within the boolean array accordingly, to ensure that they are all false before the process.
    The for loop variable will commence at 0, and continue until it is no longer than one larger than difficulty (incrementing by 1 each time).
    The following for loop will commit to a similar cause, as it will process the board[] [] variables, with the nested carrying the pyramid number, and then it will see if the value was ever repeated, by applying values to the boolean. If it was repeated, then an error message will occur - within the if statement.
    The i starts at 0, and progresses until it reaches the value of difficulty, increasing at an incrementation rate of 1.
    The z starts at 0, and would go until it is less than 3 as it increments at a rate of 1.
    Else, the boolean will be set to true as that value has been provided within the array.
    Next the counter will calculate the total number of booleans which have been set to true, ensuring that all of the required tiles are present.
    The for loops will cycle through the board[][] array (the nested), while the if statement will increment the counter once the loop achieves a point where the boolean is true.
    The i starts at 0, and progresses until it reaches the value of difficulty, increasing at an incrementation rate of 1.
    The z starts at 0, and would go until it is less than 3 as it increments at a rate of 1.
    The following if statement will report that the file is corrupted if the counted entries is NOT equal to the save.
    The next for loops (nested) will cycle through the possible options by going through different possible indexes within the array dimensions.
    The if statement will verify if within the save file one of the numbers makes the pyramid in a corrupted fashion.
    The next for loop go through to verify if the positions are physically valid, which starts off at 0 for i and increments until it is no longer less than difficulty, and similarily z will start at 0 and stop once it reaches the total number of pyramids (which is 3).
    */


    public boolean openGame ()
    {
	String fileName = JOptionPane.showInputDialog ("Please enter the filename.", "Filename");
	if (fileName == null)
	    return false;
	try
	{
	    BufferedReader in = new BufferedReader (new FileReader (fileName));
	    if (!in.readLine ().equals ("(c) Horatiu TOWERS OF HANOI VERSION 1.0.0.0 PROTECTED"))
	    {
		JOptionPane.showMessageDialog (null, "Fatal Error. This save file was not created by this application!");
		return false;
	    }
	    difficulty = Integer.parseInt (in.readLine ()); //ERROR TRAP IF 4, 6 OR 8!
	    if (difficulty != 6 && difficulty != 4 && difficulty != 8)
	    {
		JOptionPane.showMessageDialog (null, "Fatal Error. The file is corrupted! Unsupported by this version!");
		return false;
	    }
	    turns = Integer.parseInt (in.readLine ());
	    if (turns < -1)
	    {
		JOptionPane.showMessageDialog (null, "Fatal Error. The file is corrupted! Unsupported by this version!");
		return false;
	    }
	    playerName = in.readLine ();
	    for (int i = 0 ; i < difficulty ; i++)
		for (int z = 0 ; z < 3 ; z++)
		{
		    board [i] [z] = Integer.parseInt (in.readLine ());
		    if (board [i] [z] > difficulty || board [i] [z] < 0)
		    {
			JOptionPane.showMessageDialog (null, "Fatal Error. The file is corrupted! Unsupported by this version!");
			return false;
		    }
		}
	    boolean[] verification = new boolean [difficulty + 1];
	    for (int i = 0 ; i < difficulty + 1 ; i++)
	    {
		verification [i] = false;
	    }
	    for (int i = 0 ; i < difficulty ; i++)
		for (int z = 0 ; z < 3 ; z++)
		{
		    if (board [i] [z] != 0)
		    {
			if (verification [board [i] [z]] != false)
			{
			    JOptionPane.showMessageDialog (null, "Fatal Error. The file is corrupted! Unsupported by this version!");
			    return false;
			}
			else
			    verification [board [i] [z]] = true;
		    }
		}
	    int counter = 0;
	    for (int i = 0 ; i < difficulty ; i++)
	    {
		for (int z = 0 ; z < 3 ; z++)
		{
		    if (verification [board [i] [z]] == true)
			counter++;
		}
	    }
	    if (counter != difficulty)
	    {
		JOptionPane.showMessageDialog (null, "Fatal Error. The file is corrupted! Unsupported by this version!");
		return false;
	    }
	    for (int i = 0 ; i < difficulty ; i++)
		for (int z = 0 ; z < 3 ; z++)
		{
		    if (board [i] [z] < board [i + 1] [z])
		    {
			JOptionPane.showMessageDialog (null, "Fatal Error. The file is corrupted!");
			return false;
		    }
		}
	    in.close ();
	}
	catch (ArrayIndexOutOfBoundsException e)
	{
	    JOptionPane.showMessageDialog (null, "Error! The given file was not valid!");
	    return false;
	}
	catch (FileNotFoundException e)
	{
	    JOptionPane.showMessageDialog (null, "Error! The given file was not found!");
	    return false;
	}
	catch (NumberFormatException e)
	{
	    JOptionPane.showMessageDialog (null, "Error! The save file is corrupted!");
	    return false;
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "A file input related error has occured.");
	    return false;
	}
	return true;
    }


    /*
    This method outputs a particular image to the Console window, in addition to particular options that may be used throughout the application. Passes String pictureName, boolean shouldOutput, boolean shouldPause.

    Variable dictionary:
    name | type | purpose
    -------------------------
    tracker | MediaTracker | Used to import the image to the list of images that must be tracked.
    e | InterruptedException | Used for the try{} catch{(InterruptedException)} to prevent the program from crashing when loading the image accordingly.
    pictureName | String | States the picture name which will be used when outputting the image (if specific within the shouldOutput boolean).
    shouldOutput | boolean | Used to identify if the picture should output to the Console screen.
    shouldPause | boolean | Used to identify if a pauseProgram should be integrated within the method (used to remove lines of code from within screens).

    The method works such that the picture will be first loaded from the desired picture path, and then it will be added to the tracker.
    The if statement (second one) will output only if shouldOutput is true, and it will draw the image to the Console screen.
    The third if statement will make a pause if the shouldPause is true.
    The last if statement verifies if there were any errors, and if yes then an error message will appear.

    The try catch is used in the scenario that an InterruptedException would occur, and to prevent crashing when loading the image.
    */

    public void loadImage (String pictureName, boolean shouldOutput, boolean shouldPause)
    {
	picture = Toolkit.getDefaultToolkit ().getImage (pictureName);
	MediaTracker tracker = new MediaTracker (new Frame ());
	tracker.addImage (picture, 0);  // Add the picture to the list of images to be tracked
	try
	{
	    tracker.waitForAll ();
	    if (tracker.isErrorAny ())
	    {
		JOptionPane.showMessageDialog (null, "Error: A image loading error has occured.");
		return;
	    }
	}
	catch (InterruptedException e)
	{
	}

	if (shouldOutput == true)
	    c.drawImage (picture, 0, 0, null);
	if (shouldPause == true)
	    c.getChar ();
    }


    /*
    This method essentially calls the SolvePuzzle crash and outputs the Console c variable.

    Variable dictionary:
    name | type | purpose
    ----------------------
    t | Class reference | Allows the access to methods within the SolvePuzzle class.

    This method also outputs an image to the screen, and calls the run() method within SolvePuzzle. This allows for the animation to output to the screen.

    */

    public void splashScreen ()
    {
	//c.drawImage (picture, 0, 0, null); //DRAW THE IMAGE!
	SolvePuzzle t = new SolvePuzzle (c);
	t.run ();
    }


    /*
    This method outputs the high scores into a pyramid like format, with the best score being located at the top of the pyramid.

    Variable Dictionary:
    name | type | purpose
    ------------------------------------
    e | IOException variable | Used to state if an IOException occured <---Repeated several times throughout method.
    e | NullPointerException variable | Used in case due to file manipulation a null pointer were to exist and not caught by the if statement.
    e | NUmberFOrmatException variable | Used in case due to file manipulation the high scores file exhibits letters were letters are supposed to exist.
    out | PrintWriter | Used to output text to a specific file name.
    in | BufferedReader | Used to read the high scores from the file - references the BufferedReader.
    score | int | Used to store the temporary score read from the text file - is not direct simply to make it easier to read (rather than directly outputting Integer.parseInt(in.readLine());
    difficulty | int | Used to store the temporary difficulty read from the user input (from the text file)
    name | String | Used to store the temporary username from the file.
    i | For loop variable (int) | Used to keep a count of the score position.

    The first try catch (which will catch a IOException) - is used in case an error occurs when it (may) output the file.
    The first if statement works on the basis of calling fileFound, and identifying if the file needs to be created or not. If yes, then it outputs a file with an appropriate header.
    The second try catch will be for the main processing, which handles a NullPointerException (in case), NumberFormatException if the file was manually manipulated, in addition to a general IOException.
    The first if statement verifies if the file header is appropriate, and will output an error message if the file has an inappropriate file header.
    The following for loop, which will start at 0 with the ending point when it is no longer less than 10. The purpose is to identify the player position within the pyramid - so for example 1st place or second place, etc.
    The if statement within will automatically end the method, if the name would be null to prevent inaccurate outputting. This is made such that the program will not output null, and it will give the user an oppertunity to view the high scores.
    The large ternary operator at the end is simply outputting specific X and Y coordinates depending on the position number (i) - such that the String will arrange properly within the pyramid formatting (ROUGHLY).
    The ternary operator (within the drawString) verifies if the name is longer than 13 or equal to characters, and if it is then it will only output the specific range.
    */

    public void highScores ()
    {
	try
	{
	    if (fileFound ("highScores.hls") == false)
	    {
		PrintWriter out = new PrintWriter (new FileWriter ("highScores.hls"));
		out.println ("Bunnies Chirp.");
		out.close ();
	    }
	}
	catch (IOException e)
	{
	}
	c.setFont (new Font ("TimesNewRoman", Font.BOLD, 12));
	try
	{
	    BufferedReader in = new BufferedReader (new FileReader ("highScores.hls"));
	    if (!in.readLine ().equals ("Bunnies Chirp."))
	    {
		JOptionPane.showMessageDialog (null, "Error! The highscores file is corrupted."); //THIS!
		return;
	    }
	    for (int i = 0 ; i < 10 ; i++)
	    {
		String name = in.readLine ();
		if (name == null)
		{
		    c.getChar ();
		    return;
		}
		int score = Integer.parseInt (in.readLine ());
		int difficulty = Integer.parseInt (in.readLine ());
		c.drawString (i + 1 + "): " + name.substring (0, (name.length () < 13) ? (name.length ()):
		(13)) + " got a score of: " + score + " on " + ((difficulty == 4) ? ("Easy"):
		    (difficulty == 6) ? ("Medium"):
			("Hard")), (i == 0) ? (400):
			    (i == 1) ? (400):
				(i == 2) ? (260):
				    (i == 3) ? (550):
					(i == 4) ? (250):
					    (i == 5) ? (560):
						(i == 6) ? (230):
						    (i == 7) ? (580):
							(i == 8) ? (210):
							    (i == 9) ? (590):
								(200), (i == 0) ? (280):
								    (i == 1) ? (375):
									(i == 2) ? (450):
									    (i == 3) ? (450):
										(i == 4) ? (530):
										    (i == 5) ? (530):
											(i == 6) ? (600):
											    (i == 7) ? (600):
												(i == 8) ? (700):
												    (i == 9) ? (700):
													(800));
	    }
	    c.getChar ();
	    in.close ();
	}
	catch (NullPointerException e)
	{
	    JOptionPane.showMessageDialog (null, "Error! The highscores file is corrupted.");
	    return;
	}
	catch (NumberFormatException e)
	{
	    JOptionPane.showMessageDialog (null, "Error! The highscores file is corrupted.");
	    return;
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "Error! The highscores file is corrupted.");
	    return;
	}
    }


    /*
    This method sorts the high scores accordingly, and inputs new data to the high scores file after the user has won the game.
    Variable Dictionary:
    name | type | purpose
    -----------------------------
    score | int | Used to store the current user score depending on the moves and difficulty.
    hasAdded | boolean | Used to identify if the score has been added.
    e | NumberFormatException variable | Used to catch if the Exception has been thrown, and used if the input is not of a number value.
    e | IOException variable | Used in case a file input output exception has occured.
    out | PrintWriter reference variable | Used to reference the PrintWriter in order to output the file accordingly.
    in | BufferedReader reference variable |  Used to see user input from the highScores file.
    n | int | Used to find the choice from the JOptionPane.
    i | for loop variable (int) | Used for counting the high score position.
    options | Array of Objects | Used for the JOptionPane to show the options for the buttons.
    scores [] | Array of int | Used to store the previous scores.
    difficultyA[] | Array of int | Used to store the previous difficulty levels.
    names | Array of Strings | Used to store the previous names.

    The first ternary operator will assign the score multiplier depending on the difficulty, for example if the score is 4 then it will multiply by 1 (EASY), 6 is medium (10) and hard is the else.
    The following try catch will catch any NumberFormatExceptions or IOExceptions that may occur from manual file manipulation.
    The first if statement will call fileFound(String fileName), and will create a file if the file is non-existant.
    The next if statement will verify if the header is valid, and if it is not then it will ask the user if they wish to overwrite the invalid file using a JOptionPane.
    The if statement will return if the user chooses to not replace the file, and to not add any high scores.
    The next for loop will run from 0 - 9 in order to verify the position and the placing of the user within the high scores. It will store the high scores within local variables.
    The next if statement will end the method if the name is null, so that no null names will be placed within the file.
    The next for loop will go from 0 - 9 with i, in order to identify the player position in high scores.
    The first if statement within the for loop will output the playerName and information if the name is null and essentially the file was blank from the start.
    Else, if the name is null and the name was never added then it will output the recent non-stored information, otherwise the script will end.
    The next if statement will verify if the score that the player currently played is higher than the score within the stored array, and if so then the next score will be outputted to the file accoridngly.
    The next if statement will verify if the name is null (just in case it was not caught previously), and then output the array values.
    */


    private void setHighScores ()
    {
	int[] scores = new int [MAXSCORES];
	int[] difficultyA = new int [MAXSCORES];
	String[] names = new String [MAXSCORES];

	int score = (((1000 - turns) * ((difficulty == 4) ? (1):
	(difficulty == 6) ? (10):
	    (100))));
	boolean hasAdded = false;
	try
	{
	    if (fileFound ("highScores.hls") == false) //Wat
	    {
		PrintWriter out = new PrintWriter (new FileWriter ("highScores.hls"));
		out.println ("Bunnies Chirp.");
		out.close ();
	    }
	    BufferedReader in = new BufferedReader (new FileReader ("highScores.hls"));
	    if (!in.readLine ().equals ("Bunnies Chirp."))
	    {
		Object[] options = {"Replace",
		    "No"};
		int n = JOptionPane.showOptionDialog (null,
			"The high scores file is not made with this program."
			+ "Would you like to replace it?",
			"Error: File is not valid!",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options [1]);
		if (n == 1)
		    return;
	    }
	    for (int i = 0 ; i < 10 ; i++)
	    {
		names [i] = in.readLine ();
		if (names [i] == null)
		    break;
		scores [i] = Integer.parseInt (in.readLine ());
		difficultyA [i] = Integer.parseInt (in.readLine ());
	    }
	    PrintWriter out = new PrintWriter (new FileWriter ("highScores.hls"));
	    out.println ("Bunnies Chirp.");
	    for (int i = 0 ; i < 10 ; i++)
	    {
		if (names [i] == null && i == 0 && hasAdded == false)
		{
		    out.println (playerName);
		    out.println (score);
		    out.println (difficulty);
		    hasAdded = true;
		}
		else
		    if (names [i] == null)
		    {
			if (i != 0 && score <= scores [i - 1] && hasAdded == false)
			{
			    out.println (playerName);
			    out.println (score);
			    out.println (difficulty);
			    out.close ();
			}
			hasAdded = true;
			break;
		    }
		if (scores [i] <= score && names [i] != null && hasAdded == false)
		{
		    hasAdded = true;
		    out.println (playerName);
		    out.println (score);
		    out.println (difficulty);
		}
		if (names [i] != null)
		{
		    out.println (names [i]);
		    out.println (scores [i]);
		    out.println (difficultyA [i]);
		}
	    }
	    out.close ();
	}
	catch (NumberFormatException e)
	{
	}
	catch (IOException e)
	{
	}
    }



    /*
    This method returns a boolean regarding a particular file's existance.

    Variable Dictionary:
    name | type | purpose
    ----------------------------------
    filePath | String | Keeps the file path which has to be verifies / fileName.
    e | FileNotFoundException variable | Keeps count fit the file is found or not.
    e | IOException variable | Keeps count if the particular variable is found or  not.
    in | BufferedReader | Used for input for the filePath provided through the parameter pass.

    */

    private boolean fileFound (String filePath)
    {
	try
	{
	    BufferedReader in = new BufferedReader (new FileReader (filePath));
	    return true;
	}
	catch (FileNotFoundException e)
	{
	    return false;
	}
	catch (IOException e)
	{
	}
	return false;
    }


    /*
    This menu will output appropriate graphics from within the mainMenu() including appropriate selection using keyboard controls.

    Variable Dictionary:
    name | type | purpose
    ----------------------
    input | char | Used to store the user input.
    highlight | int | Used to store the current process in the mainMenu()
    i | int | Used in the for loop variable to output the initial options.

    The first for loop will be used to output the initial options to integrate it part of the image. The start is at i = 0 and it will progress by one while it is less than 5. This is such that it will affect every single menu possibility.
    The while loop will be used to shift between the options when the keyboard input is a space. This is used such that it will be easy to use.
    The if statement is used such to increase the highlight accordingly, so that the highlighting will be in effect at the proper Y height.
    */

    public void mainMenu ()
    {
	char input = ' ';
	int highlight = 0;
	for (int i = 0 ; i < 5 ; i++)
	    adjustHighlight (i);
	adjustHighlight (0);
	while (input == ' ')
	{
	    input = c.getChar ();
	    highlight = highlight + 1;
	    if (highlight == 5)
		highlight = 0;
	    if (input == ' ') //Do you need this?
		adjustHighlight (highlight);
	}
	choice = highlight;
    }


    /*
    This method will draw the option within a given number of coordinates. Passes int height, boolean erase.

    Variable dictionary:
    name | type | purpose
    -----------------------
    height | int | This one states the height within the menu that has to be adjusted.
    erase | boolean | Indicates if the setColor has to be white ('erase') or red.

    The purpose of the ternary operator is to find the nessesary colour to be used for the setColor, while removing the additional lines of an if statement. If it's erasing then white is chosen, else red..
    The draw command will then output the graphics desired accordingly.
    */


    private void drawOption (int height, boolean erase)
    {
	c.setColor ((erase == false) ? (Color.white):
	(Color.red));
	c.drawRoundRect (179, 348 + 65 * height, 681, 50, 40, 50);
    }


    /*
    This method allows for the highlighting to be adjusted within the mainMenu, as it permits the previous selection to be erased, and the new selection to be drawn with a specific colour. Passes the int for points.

    Variable dictionary:
    name | type | purpose
    --------------------
    points | int | This variable indicates the current point which needs to be applied accordingly

    The ternary operators (for both) essentially work upon the principal that if the selection is 0, then the drawn point should be one previous, or 0 depending if it should be draw or filled [similar logic is used for those ternary operators as they complete the same task].
    There are two method calls, one of which erases the previous input and the other one which draws the new input. (To be more correct, one 'erases' using a white erase and another one draws using a red erase).

    */

    private void adjustHighlight (int point)  //COMBINE THE METHODS!
    {
	drawOption ((point != 0) ? (point - 1):
	(4), true);
	drawOption ((point != 0) ? (point):
	(0), false);
    }



    //The class constructor of the application, which sets the c Console with an appropriate title, in addition to a nice size. Similarily, to the d Console with a small and compact style.


    public TowerOfHanoi ()
    {
	c = new Console (38, 129, "**Tower of Hanoi**");
	d = new Console (5, 40, 12, "Tower of Hanoi: Input Controller");
    }


    /*
    This method is the main program of the application, which controls the flow of the program and loads images accordingly (as some act as screens).

    Variable dictionary:
    name | type | purpose
    -----------------
    t | Class reference variable | Used to access the TowerOfHanoi class.

    The first while loop will execute until the choice equals with 5 (else), and then it will exit and show the goodbye() image and close the Consoles.
    The first if statement will load the game and the images accordingly if the user chooses the first option on the mainMenu.
    The following if statement will then play the game if the user has entered a possible username.
    The second if statement will show the winner screen, and will show the appropriate following screen once it has been completed.
    If the player has won, then winner() is shown, otherwise if the user has not won and the user does not want to save, then the loser image will appear.
    Otherwise, the user must desire to save the game and then the saveGame option will appear.

    If the user chooses the second option within the if structure (inside mainMenu()), then the instructions image will appear.
    If the user chooses the third optoin within the if structure (inside mainMenu()), then the openGame will load, and if openGame will return true (the user has entered a valid open game).
    Then display will run without going to the configuration screen.
    The second if statement will show the winner screen, and will show the appropriate following screen once it has been completed.
    If the player has won, then winner() is shown, otherwise if the user has not won and the user does not want to save, then the loser image will appear.
    Otherwise, the user must desire to save the game and then the saveGame option will appear.

    If the user chooses to go to highscores by selecting the 4th option within the mainMenu, then the highscores option will appear.
    Otherwise, if the user chose nothing then the while loop will break, and then the user will be presented with the goodbye() method which will close the application.

    */

    public static void main (String[] args)
    {
	TowerOfHanoi t = new TowerOfHanoi ();
	t.playMusic ();
	t.loadImage (PICTURE_PATH_SPLASHSCREEN, true, false);
	t.splashScreen ();
	JOptionPane.showMessageDialog (null, "Coordination: To coordinate throughout this application, use the space key to shuffle through \n options and press any other key to select the specific option.", "How to: Coordinate", JOptionPane.INFORMATION_MESSAGE);
	while (true)
	{
	    t.loadImage (PICTURE_PATH_PYRAMID, true, false);
	    t.mainMenu ();
	    if (t.choice == 1)
	    {
		t.loadImage (PICTURE_PATH_LEVELSELECTION, true, false);
		if (t.configureScreen () == true)
		{
		    t.display ();
		    if (t.hasWon () == true)
			t.winner ();
		    else if (t.hasWon () == false && t.userInput != 's' && t.userInput != 'S')
			t.loadImage (PICTURE_PATH_LOSER, true, true);
		    else
			t.saveGame ();
		}
	    }
	    else if (t.choice == 2)
		t.loadImage (PICTURE_PATH_INSTRUCTIONS, true, true);
	    else if (t.choice == 3)
	    {
		t.loadImage (PICTURE_PATH_OPENGAME, true, false);
		if (t.openGame () == true)
		{
		    t.display ();
		    if (t.hasWon () == true)
			t.winner ();
		    else if (t.hasWon () == false && t.userInput != 's' && t.userInput != 'S')
			t.loadImage (PICTURE_PATH_LOSER, true, true);
		    else
			t.saveGame ();
		}
	    }
	    else if (t.choice == 4)
	    {
		t.loadImage (PICTURE_PATH_HIGHSCORES, true, false);
		t.highScores ();
	    }
	    else
		break;
	}
	t.loadImage (PICTURE_PATH_GOODBYE, true, false);
	t.goodbye ();
    }
}

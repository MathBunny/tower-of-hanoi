/*
Horatiu Lazu
Towers of Hanoi
December 23, 2013 - Version 1.0.0.1
The main purpose of this class is to output an animation regarding the solving of a Towers of Hanoi.

Specifically, this class displays the animations within the splashScreen, by outputting a series of pyramid section movements. qw

Variable Dictionary:
name | type | purpose
---------------------
coordinatesXBase | array of ints | Stores the coordinates of the points within the base of the pyramid (X wise).
coordinatesYBase | array of ints | Stores the coordinates of the points within the base of the pyramid (Y wise).
coordinatesXMid | array of ints | Stores the coordinates of the points within the middle of the pyramid (X wise).
coordinatesYMid | array of ints | Stores the coordinates of the points within the middle of the pyramid (Y wise).
coordinatesXTop | array of ints | Stores the coordinates of the points within the upper part of the pyramid (X wise).
coordinatesYTop | array of ints | Stores the coordinates of the points within the upper part of the pyramid (X wise).
con | Class reference  | Provides access to the HSA Console library.
*/
import hsa.Console; //Import for HSA.Console library.
import java.awt.Color; //Import for the Color library.

public class SolvePuzzle extends Thread
{
    private Console con;           // The output console

    private static final int MOVE_SPEED = 1;
    int[] coordinatesXBase = new int [4];
    int[] coordinatesYBase = new int [4];

    int[] coordinatesXMid = new int [4];
    int[] coordinatesYMid = new int [4];

    int[] coordinatesXTop = new int [3];
    int[] coordinatesYTop = new int [3];

    //This method initializes all of the values for the pyramid accordingly.
    private void initialize ()
    {
	coordinatesXBase [0] = 343;
	coordinatesXBase [1] = 286;
	coordinatesXBase [2] = 57;
	coordinatesXBase [3] = 0;
	coordinatesYBase [0] = 755;
	coordinatesYBase [1] = 641;
	coordinatesYBase [2] = 641;
	coordinatesYBase [3] = 755;
	coordinatesXMid [1] = 229;
	coordinatesXMid [0] = 286;
	coordinatesXMid [2] = 114;
	coordinatesXMid [3] = 57;
	coordinatesYMid [0] = 641;
	coordinatesYMid [1] = 527;
	coordinatesYMid [2] = 527;
	coordinatesYMid [3] = 641;
	coordinatesXTop [1] = 171;
	coordinatesXTop [0] = 114;
	coordinatesXTop [2] = 229;
	coordinatesYTop [0] = 527;
	coordinatesYTop [1] = 412;
	coordinatesYTop [2] = 527;
    }
    /*
    This method will draw a solved Towers of Hanoi puzzle featuring a three tile system.
    
    Variable Dictionary:
    name | type | purpose
    ------------------------------
    i | int (for loop) | Used to keep a count in regards to the distance of a pyramid section that has been moved.
    z | int (for loop) | Used to keep the height of the gradient fill patch being used to redraw the polygon movement.
    colora | int | Used to keep the change in red within the for loop to redraw the gradient background accordingly.
    
    The very first section redraws the polygons by calling drawPolygon, in addition to initializing the coordinates of the polygons.
    
    **Important: See below for start / stop variables. It applies to all of the graphically related for loops which adjust the array entries.
    
    The first section will shift over the upper section by a given number of pixels.
    The first for loop will indicate the number of pixels having to be moved.
    The next for loop will calculate the red change used to patch the animation. colora is a variable used to make the reading easier to understand by the user.
    The following lines then adjust the polygon coordinates accordingly, and output the nearby polygon and the immediate polygon being moved to update the frame.
    
    The next section will move the block down, by increasing the Y values.
    The first for loop will indicate the number of pixels having to be moved.
    The next for loop will calculate the red change used to patch the animation. colora is a variable used to make the reading easier to understand by the user.
    The following lines then adjust the polygon coordinates accordingly,and output immediate polygon being moved to update the frame.
    
    The next section will shift over the left block onto the middle (left middle block (2nd biggest block)).
    The first for loop will indicate the number of pixels having to be moved.
    The next for loop will calculate the red change used to patch the animation. colora is a variable used to make the reading easier to understand by the user.
    The following lines then adjust the polygon coordinates accordingly, and output the nearby polygon and the immediate polygon being moved to update the frame.
    
    The next section will move the block down, by increasing the Y values.
    The first for loop will indicate the number of pixels having to be moved.
    The next for loop will calculate the red change used to patch the animation. colora is a variable used to make the reading easier to understand by the user.
    The following lines then adjust the polygon coordinates accordingly,and output immediate polygon being moved to update the frame.
    
    A similar pattern will repeat with the for loops for the remaining lines. 
    
    The start and stop variables are i which starts at 0, and then it continues until the destination has been achieved, at the incrementation of the MOVE_SPEED variable (FOR ALL OF THEM).
    In regards to for loops that commence with the int z for loop variable, these start with the extremeties to a particular dimension (the given coordinate), and then continue to the other coordinate (at the opposite side) such that it will create a gradient for the given area.
    
    */

    public void run ()
    {
	initialize ();
	drawPolygon (0);
	drawPolygon (1);
	drawPolygon (2);
	for (int i = 0 ; i < 686 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [0] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);
		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (0, z, 1030, z);
	    }
	    //con.drawLine (coordinatesXTop [0], coordinatesYTop [0], coordinatesXTop [1], coordinatesYTop [1]);
	    coordinatesXTop [0] += MOVE_SPEED;
	    coordinatesXTop [1] += MOVE_SPEED;
	    coordinatesXTop [2] += MOVE_SPEED;
	    drawPolygon (2);
	    drawPolygon (1);
	}
	//Moving top down!
	for (int i = 0 ; i < 228 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] - 1 ; z < coordinatesYTop [0] ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0] - 1, z, coordinatesXTop [2] + 1, z);
	    }
	    con.drawLine (coordinatesXTop [0], coordinatesYTop [0], coordinatesXTop [1], coordinatesYTop [1]);
	    coordinatesYTop [0] += MOVE_SPEED;
	    coordinatesYTop [1] += MOVE_SPEED;
	    coordinatesYTop [2] += MOVE_SPEED;
	    drawPolygon (2);
	}
	//Moving mid right!
	for (int i = 0 ; i < 343 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYMid [1] ; z < coordinatesYMid [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (0, z, 1030, z);
	    }
	    con.drawLine (coordinatesXTop [0], coordinatesYTop [0], coordinatesXTop [1], coordinatesYTop [1]);
	    coordinatesXMid [0] += MOVE_SPEED;
	    coordinatesXMid [1] += MOVE_SPEED;
	    coordinatesXMid [2] += MOVE_SPEED;
	    coordinatesXMid [3] += MOVE_SPEED;
	    drawPolygon (1);
	    drawPolygon (0);
	}
	//Moving mid right!
	for (int i = 0 ; i < 114 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYMid [1] ; z < coordinatesYMid [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXMid [3], z, coordinatesXMid [0], z);
	    }
	    con.drawLine (coordinatesXMid [0], coordinatesYTop [0], coordinatesXMid [1], coordinatesYTop [1]);
	    coordinatesYMid [0] += MOVE_SPEED;
	    coordinatesYMid [1] += MOVE_SPEED;
	    coordinatesYMid [2] += MOVE_SPEED;
	    coordinatesYMid [3] += MOVE_SPEED;
	    drawPolygon (1);
	}
	//From top block to the left (now going up!.
	for (int i = 0 ; i < 114 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [2] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0], z, coordinatesXTop [2], z);
	    }
	    coordinatesYTop [0] -= MOVE_SPEED;
	    coordinatesYTop [1] -= MOVE_SPEED;
	    coordinatesYTop [2] -= MOVE_SPEED;
	    drawPolygon (2);
	}
	for (int i = 0 ; i < 343 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [2] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0], z, coordinatesXTop [2], z);
	    }
	    coordinatesXTop [0] -= MOVE_SPEED;
	    coordinatesXTop [1] -= MOVE_SPEED;
	    coordinatesXTop [2] -= MOVE_SPEED;
	    drawPolygon (2);
	}
	for (int i = 0 ; i < 228 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYBase [1] ; z < coordinatesYBase [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXBase [0], z, coordinatesXBase [3], z);
	    }
	    coordinatesYBase [0] -= MOVE_SPEED;
	    coordinatesYBase [1] -= MOVE_SPEED;
	    coordinatesYBase [2] -= MOVE_SPEED;
	    coordinatesYBase [3] -= MOVE_SPEED;
	    drawPolygon (0);
	}
	for (int i = 0 ; i < 686 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYBase [1] ; z < coordinatesYBase [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXBase [0], z, coordinatesXBase [3], z);
	    }
	    coordinatesXBase [0] += MOVE_SPEED;
	    coordinatesXBase [1] += MOVE_SPEED;
	    coordinatesXBase [2] += MOVE_SPEED;
	    coordinatesXBase [3] += MOVE_SPEED;
	    drawPolygon (0);
	}
	for (int i = 0 ; i < 228 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYBase [1] ; z < coordinatesYBase [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXBase [0], z, coordinatesXBase [3], z);
	    }
	    coordinatesYBase [0] += MOVE_SPEED;
	    coordinatesYBase [1] += MOVE_SPEED;
	    coordinatesYBase [2] += MOVE_SPEED;
	    coordinatesYBase [3] += MOVE_SPEED;
	    drawPolygon (0);
	}
	for (int i = 0 ; i < 343 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [2] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0], z, coordinatesXTop [2], z);
	    }
	    coordinatesXTop [0] -= MOVE_SPEED;
	    coordinatesXTop [1] -= MOVE_SPEED;
	    coordinatesXTop [2] -= MOVE_SPEED;
	    drawPolygon (1);
	    drawPolygon (2);
	}

	for (int i = 0 ; i < 114 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [2] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0], z, coordinatesXTop [2], z);
	    }
	    coordinatesYTop [0] += MOVE_SPEED;
	    coordinatesYTop [1] += MOVE_SPEED;
	    coordinatesYTop [2] += MOVE_SPEED;

	    drawPolygon (2);
	}
	for (int i = 0 ; i < 114 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYMid [1] ; z < coordinatesYMid [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXMid [3], z, coordinatesXMid [0], z);
	    }
	    coordinatesYMid [0] -= MOVE_SPEED;
	    coordinatesYMid [1] -= MOVE_SPEED;
	    coordinatesYMid [2] -= MOVE_SPEED;
	    coordinatesYMid [3] -= MOVE_SPEED;
	    drawPolygon (1);
	}
	for (int i = 0 ; i < 343 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYMid [1] ; z < coordinatesYMid [3] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXMid [3], z, coordinatesXMid [0], z);
	    }
	    coordinatesXMid [0] += MOVE_SPEED;
	    coordinatesXMid [1] += MOVE_SPEED;
	    coordinatesXMid [2] += MOVE_SPEED;
	    coordinatesXMid [3] += MOVE_SPEED;
	    drawPolygon (1);
	}

	for (int i = 0 ; i < 228 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [2] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0], z, coordinatesXTop [2], z);
	    }
	    coordinatesYTop [0] -= MOVE_SPEED;
	    coordinatesYTop [1] -= MOVE_SPEED;
	    coordinatesYTop [2] -= MOVE_SPEED;
	    drawPolygon (2);
	}
	for (int i = 0 ; i < 686 ; i += MOVE_SPEED)
	{
	    int colora;
	    for (int z = coordinatesYTop [1] ; z < coordinatesYTop [2] + 1 ; z++) //ASK MS. DYKE!
	    {
		colora = (z / 3);

		Color aColour = new Color (colora, 252, 248);
		con.setColor (aColour);
		con.drawLine (coordinatesXTop [0], z, coordinatesXTop [2], z);
	    }
	    coordinatesXTop [0] += MOVE_SPEED;
	    coordinatesXTop [1] += MOVE_SPEED;
	    coordinatesXTop [2] += MOVE_SPEED;
	    drawPolygon (2);
	}
    }

    /*
    This method outputs a specific polygon according to the given part.
    Variable dictionary:
    name | type | purpose
    -------------------
    pyramidPart | int | Stores the part of the pyramid that must be moved.
    
    The if statements will sort the different options and draw a polygon accordingly. The if statements are repeated to not use curly braces in setColors, but could be argued for simplicity either way.
    
    */

    private void drawPolygon (int pyramidPart)
    {
	con.setColor (Color.yellow);
	if (pyramidPart == 0)
	    con.fillPolygon (coordinatesXBase, coordinatesYBase, 4);
	else if (pyramidPart == 1)
	    con.fillPolygon (coordinatesXMid, coordinatesYMid, 4);
	else
	    con.fillPolygon (coordinatesXTop, coordinatesYTop, 3);

	con.setColor (Color.black);
	if (pyramidPart == 0)
	    con.drawPolygon (coordinatesXBase, coordinatesYBase, 4);
	else if (pyramidPart == 1)
	    con.drawPolygon (coordinatesXMid, coordinatesYMid, 4);
	else
	    con.drawPolygon (coordinatesXTop, coordinatesYTop, 3);
    }

    /*
    Class constructor, receives the Console to output the graphics on. Passes Console c.
    Variable Dictionary:
    name | type | purpose
    ------------------
    c | Class reference | Allows access to Console c.
    */
    public SolvePuzzle (Console c)
    {
	con = c;
    }
} // SolvePuzzle class

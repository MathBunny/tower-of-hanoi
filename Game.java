import java.awt.*;
import hsa.*;
public class Game{Console c;           // The output consoleint selection = 0, selection2 = 0, turns = -1,difficulty = 4; //CHANGE DIFFICULTY, 4 = EASY, 6 = MEDIUM AND 8 = HARD!
    int [] [] board = new int [9][3];public static void main (String[] args) throws Exception{Game t = new Game();t.display();} // main method
    public Game(){c = new Console (38, 129);}
    public int difficulty = 4;
    int selection, selection2, turns;
    public void display () throws Exception {
	new Message ("Welcome to Towers of Hanoi compact edition. This is one valuable line. Use 0 - 2 as the blocks. Winner detection will end the game. Good luck.");
	for (int i = difficulty ; i > 0 ; i--)
	    board [difficulty - i] [0] = i;
	while (true){
	    board [((highlightIndex (selection2, false, false)) == -1) ? (0):((difficulty + 1) - highlightIndex (selection2, false, false))] [selection2] = board [difficulty - highlightIndex (selection, false, false)] [selection];
	    board [difficulty - highlightIndex (selection, false, false)] [selection] = 0; //Clear variable.
	    c.clear ();
	    c.setColor(Color.black);
	    for (int z = 0 ; z < 3 ; z++)
		for (int i = 0 ; i < 8 ; i++)
		    drawSector (board [i] [z], (difficulty == 8) ? (22):(difficulty == 6) ? (30):(48), (difficulty == 8) ? (10):(difficulty == 6) ? (5):(2), z, difficulty, 0, 0, difficulty - i);
	    turns++;
	    c.drawString ("Moves: " + turns, 800, 100);
	    if (hasWon () == true)
		return;
	    else{
		selection = c.getChar() - '0';
		c.println(selection);
		selection2 = c.getChar() - '0';
		c.println(selection2);}}}
    private boolean hasWon (){
	for (int i = 0 ; i < difficulty ; i++)
	    if (board [i] [2] == 0)
		return false;
	return true;}
    private int highlightIndex (int selection, boolean firstSelection, boolean shouldAdjustDifficulty) throws Exception{
	for (int i = difficulty ; i >= 0 ; i--)
	    if (board [i] [selection] != 0)
		return difficulty - i;
	return (firstSelection == false) ? (-1): ((shouldAdjustDifficulty == false) ? (difficulty):(difficulty != 8) ? (difficulty):(difficulty - 1));}
    private void drawSector (int i, int size, int shift, int pyramidNumber, int indent, int x, int y, int heightBoost){
	c.fillRect (5 + (indent - i) * size + shift + (pyramidNumber * size * ((2 * difficulty) - 1) + shift * pyramidNumber) + x, 755 - size * (difficulty + 1 - heightBoost) + y, (2 * (i) - 1) * size, size);}
} // Game class

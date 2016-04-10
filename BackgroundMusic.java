/*
Horatiu Lazu
Towers of Hanoi Version 1.0.0.1
The main purpose of this class is to play background music to the user.
*/


import java.io.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.JOptionPane; //Used for JOptionPane

public class BackgroundMusic implements Runnable
{
    /*
    Variable dictionary:
    name | type | purpose
    ----------------------
    in | InputStream reference variable | Allows access to the InputStream to the specified file path.
    audioStream | AudioStream reference variable | Allows access to the AudioStream.
    e | FileNotFoundException variable | Used to find out if a FileNotFoundException has occured, and the audio file could not be found.
    e | IOException variable | Used to find out if a file input output except has occured.
    
    
    */
    public void run ()
    {
	
	try
	{
	    InputStream in = new FileInputStream ("music/backgroundMusic3.au");

	    // Create an AudioStream object from the input stream.
	     AudioStream audioStream = new AudioStream(in);
	     AudioPlayer.player.start(audioStream);
	}
	catch (FileNotFoundException e)
	{
	    JOptionPane.showMessageDialog (null, "Error: The background music audio file was not found!");
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "Error: An IO related error was found!");
	}

    }
} // BackgroundMusic class

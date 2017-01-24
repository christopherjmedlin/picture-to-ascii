package com.medlinchristopher.picturetoascii;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JOptionPane;
import java.io.File;

import com.medlinchristopher.picturetoascii.util.OSUtils;

//TODO: Add support for Mac OS X.
//TODO: Add colored ASCII art possibility.
//TODO: Set look and feel to system default.

/**
* Main.java
*
* Main driver class for picture-to-ascii application
*
* @author Ivan Kenevich
* @author Christopher Medlin
* @version 1.0
* @since 0.0
*/
public class Main 
{

	
	public static void main (String[] args) 
	{
		String dir = "";
		boolean firstRun = false;
		boolean dirSuccess = true;
		String failureMessage = "";	
	
		if (OSUtils.isUnixOrLinux())
			dir = System.getProperty("user.home") + "/.picture-to-ascii";
		else if (OSUtils.isWindows())
			dir = System.getProperty("user.home") + "\\My Documents\\picture-to-ascii";
		else 
		{
			//if unsupported operating system, we can assume the directory would fail to be made if attempted
			dirSuccess = false;
			failureMessage = "Unsupported operating system: " + System.getProperty("os.name");
		}

		if (!new File(dir).isDirectory() && dirSuccess) //if directory does not exist
		{
			if (new File(dir + "/output").mkdirs() && new File(dir + "/temp").mkdirs())
				System.out.println("Directory created.");
			else 
			{
				if (OSUtils.isUnixOrLinux()) 
				{
					failureMessage = "java.io.File.mkdirs() failed to create directories at " + dir + "/output" + ". Are you running as root?";
				}
				else if (OSUtils.isWindows()) 
				{
					failureMessage = "java.io.File.mkdirs() failed to create directories. Are you running as administrator?";
				}
				else
					failureMessage = "java.io.File.mkdirs() failed to create directories.";
				dirSuccess = false;
				System.out.println("Failed to create directory.");
			}
		}
                 
		System.out.println(failureMessage);     	

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (!dirSuccess) //if directories failed to be made
			JOptionPane.showMessageDialog(null, "Failed to create directory: " + failureMessage, "WARNING:", JOptionPane.WARNING_MESSAGE);
		
		//places frame in center of screen
		ASCIIConverterFrame acf = new ASCIIConverterFrame(new Point((int) screenSize.getWidth()/2, (int) screenSize.getHeight()/2));
		
		
	}
}

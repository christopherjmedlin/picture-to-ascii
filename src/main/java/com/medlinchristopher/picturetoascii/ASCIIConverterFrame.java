package com.medlinchristopher.picturetoascii;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.medlinchristopher.picturetoascii.util.OSUtils;

/**
* A JFrame containing various UI elements for the Picture to ASCII program. 
* <p>
* This includes a text field for the path to the image, a button to generate the image, a button to bring
* up the options menu, a button to browse for files, and a label to inform the user what is happening.
* <p>
* ASCIIConverterFrame follows a BoxLayout, in which 2 containers and a JLabel are aranged on a vertical ax
* is in that order. The first container, "upperContainer", holds the text field and the file browser button. 
* The second container, "lowerContainer", holds the generate button and the options button. Finally, the
* JLabel informs the user on the current state of the program (awaiting image, processing image, finished
* processing image, etc...).
*
* @author Christopher Medlin
* @author Ivan Kenevich
* @since 0.1
* @version 1.0
*/
public class ASCIIConverterFrame extends JFrame implements ActionListener
{
	//declare class level variables
	private JTextField pathTextField;
	private JButton generate, options, browse;
	private ASCIIOptionsFrame optionsWindow;
	private Container upperContainer, lowerContainer;
	private JLabel programStatus;
	private JFileChooser fileChooser;

	private int pixelDensity, charSetSize;
	private String path;
	private BufferedImage img;
	private Runtime runtime;
	
	/**
	* Initializes ASCIIConverterFrame object.
	*
	* @param windowLocation Location of the center of the window on the screen.
	*/
	public ASCIIConverterFrame (Point windowLocation) 
	{
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		runtime = Runtime.getRuntime();
		//set specifications for frame
	        setTitle("picture-to-ascii v1.0");
		setSize(new Dimension(400, 150));
		setLocation(windowLocation.x - 315/2, windowLocation.y - 150/2);
		setResizable(false);
		setVisible(true);
		
		//initialize options window
		optionsWindow = new ASCIIOptionsFrame (new Point(windowLocation.x, windowLocation.y + (150/2) + (195/2)), this);
		optionsWindow.setVisible(false);

		pixelDensity = 1;
		charSetSize = 1;
		path = "trump.jpg";
	}
	
	/**
	* Creates GUI components for the object.
	*/
	private void setupGUI()
	{				
		//initialize components
		pathTextField = new JTextField("<path to image>", 13);
		generate = new JButton("Generate");
		options = new JButton("Options");
		browse = new JButton(new ImageIcon(this.getClass().getResource("/img/folder-icon.png")));
		upperContainer = new Container();
		lowerContainer = new Container();
		programStatus = new JLabel("Awaiting command.");
		programStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//setting layout of containers
		upperContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		upperContainer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lowerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		//adding components to their respective containers.
		upperContainer.add(pathTextField);
		upperContainer.add(browse);
		lowerContainer.add(generate);
		lowerContainer.add(options);
		
		//add action listeners
		options.addActionListener(this);
		generate.addActionListener(this);
		pathTextField.addActionListener(this);
		browse.addActionListener(this);
		
		//sets layout of ASCIIConverterFrame and adds containers
		getContentPane().add(upperContainer);
		getContentPane().add(lowerContainer);
		getContentPane().add(programStatus);
		getContentPane().add(Box.createHorizontalStrut(40));
	}
	
	/**
	* Generates ASCII art.
	*/
        private	void generate () 
	{
		
		boolean success = true;
		int fontSize = 0;
		String picOutputPath = "";
		/*
		try {
			picOutputPath = optionsWindow.getOutputPath() + removeFileExtension(pathTextField.getText()) + ".png";
		} catch (StringIndexOutOfBoundsException e) {
			success = false;
		}
		*/
		
		programStatus.setForeground(Color.BLACK);
		programStatus.setText("Generating ASCII Art...");

		try {
			int i = Integer.parseInt(optionsWindow.getFontSize());
			if (i > 0 && i <= 20) {
				fontSize = i;
			}
			else {
				success = false;
				programStatus.setForeground(Color.RED);
				programStatus.setText("ERROR: Font size out of range. (1-20)");
			}
		} catch (NumberFormatException e) {
			success = false;
			programStatus.setForeground(Color.RED);
			programStatus.setText("ERROR: Improper argument for font size.");
		}
		//read image
		try {
			img = ImageIO.read(new File(pathTextField.getText()));
		} catch (IOException e) {
		 	programStatus.setForeground(Color.RED);
			programStatus.setText("ERROR: Image file not found.");
			success = false;
		}

		//if inputed file isn't an image
		if (img == null && success) {
			programStatus.setForeground(Color.RED);
			programStatus.setText("ERROR: Specified file is not an image.");
			success = false;
		}

		try {
			picOutputPath = optionsWindow.getOutputPath() + removeFileExtension(pathTextField.getText()) + ".png";
		} catch (StringIndexOutOfBoundsException e) {}

		

		//generate ASCII
		if (success) {
			if (!ASCIIConversion.writeASCIIToImage(ASCIIConversion.imageToASCII(img, pixelDensity, charSetSize), fontSize, picOutputPath)) {
				success = false;
				programStatus.setText("ERROR: Output path not recognized.");		
			}
		}	
			
				
			
    
		
		if (success)
			programStatus.setText("ASCII art generated: " + picOutputPath);
		
	}
	
	private String removeFileExtension (String str) {
	       String slash = "";
	       if (OSUtils.isWindows()) 
		       slash = "\\";
	       else if (OSUtils.isUnixOrLinux())
		       slash = "/";
	       return slash + str.substring(str.lastIndexOf(slash) + 1, str.indexOf("."));
	}
	
	/**
	* Allows the ASCIIOptionsFrame to change the density of the ASCII art.
	*
	* @param pd The number that the pixel density is to be set to.
	*/
	public void setPixelDensity (int pd) 
	{
		pixelDensity = pd;
	}
	
	/**
	* Sets the size of the charset to be used in ASCII generation.
	*
	* @param size The number indicating the size of the charset. 0=small 2=medium 3=large
	*/
	public void setCharSetSize (int size) {
		if (size == 0 || size == 1 || size == 2)
			charSetSize = size;
		else
			throw new IllegalStateException("Illegal char set size.");
	}
	
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource()==options)
		{
			optionsWindow.setVisible(true);
		}
		else if (e.getSource()==generate)
		{
			generate();
		}
		else if (e.getSource()==browse)
		{
			System.out.println("Browse button selected.");
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "gif", 
										"bmp", "png", "wbmp"));
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				pathTextField.setText(fileChooser.getSelectedFile().getPath());
		}
	}
}

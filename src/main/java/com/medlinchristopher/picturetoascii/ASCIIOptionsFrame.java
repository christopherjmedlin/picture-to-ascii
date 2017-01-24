package com.medlinchristopher.picturetoascii;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

import com.medlinchristopher.picturetoascii.util.OSUtils;

//TODO: Reorganize options GUI.
//TODO: Fix issue with premature options window on Linux.
//TODO: Reorganize functions.

/** 
* ASCIIOptionsFrame.java
*
* A JFrame containing various settings for the Picture to ASCII Program.
* <p>
* This includes radio buttons for choosing the size of the ASCII character set, a JComboBox for choosing density,
* and a text field for choosing the output path.
* <p>
* ASCIIOptionsFrame follows a custom layout.
*
* @author Christopher Medlin and Ivan Kenevich
* @version 1.0
* @since 0.3
*/

public class ASCIIOptionsFrame extends JFrame implements ActionListener
{
	// Explanatory labels
	private JLabel labelNumberOfASCII;
	private JLabel labelNumberOfPixels;
	private JLabel outputPathLabel;
	
	// Radio buttons for the size of ASCII character set
	/*
        private JRadioButton radioSmall;
        private JRadioButton radioMedium;
        private JRadioButton radioLarge;
	private ButtonGroup radioGroup;	
	*/
	private JComboBox<String> comboCharSet;

	// Text field for output folder path.
	private JTextField outputPathTextField;

	// ASCIIConverterFrame object in which this was created in
	private ASCIIConverterFrame acf;

	// A restricted set of values for number of pixels per ASCII character
        private JComboBox<String> comboNumberOfPixels;
	
	// Location of the first label, from which the positions of radio buttons are determined
	private int radioButtonsLabelY;

	// Text field for font size
	private JTextField fontSizeTextField;
	
	// Label for fontSizeTestField
	private JLabel fontSizeLabel;
	
	/**
	* Initializes ASCIIOptionsFrame object.
	*
	* @param windowLocation Location of the center of the options window on the screen.
	*/
	public ASCIIOptionsFrame(Point windowLocation, ASCIIConverterFrame containingFrame)
	{
		radioButtonsLabelY=10;
		getContentPane().setLayout(null);
		setupGUI();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		//sets frame title, size, visibility, and resizability
		setTitle("Options");
		setSize(405,160);
		setVisible(true);
		setResizable(false);
		setLocation(windowLocation.x - 400/2, windowLocation.y - 195/2);
		
		acf = containingFrame;
	}
	
	/**
	* Creates GUI components for the object.
	*/
	private void setupGUI()
	{
		//initializes labelNumberOfASCII and adds to frame 
		labelNumberOfASCII = new JLabel();
		labelNumberOfASCII.setLocation(12, 8);
		labelNumberOfASCII.setSize(191,26);
		labelNumberOfASCII.setText("Number of ASCII characters used");
		getContentPane().add(labelNumberOfASCII);
		
		//initialized comboCharSet and adds to frame
		String comboCharSet_tmp[]={"Small", "Medium", "Large"};
		comboCharSet = new JComboBox<String>(comboCharSet_tmp);
		comboCharSet.setLocation(294, 12);
		comboCharSet.setSize(96, 25);
		comboCharSet.setEditable(true);
		comboCharSet.addActionListener(this);
		getContentPane().add(comboCharSet);

		//initializes labelNumberofPixels and adds to frame
		labelNumberOfPixels = new JLabel();
		labelNumberOfPixels.setLocation(12,28);
		labelNumberOfPixels.setSize(240,51);
		labelNumberOfPixels.setText("Number of pixels per ASCII character: ");
		getContentPane().add(labelNumberOfPixels);
		
		//initializes comboNumberOfPixels and adds to frame
		String comboNumberOfPixels_tmp[]={"1","3","5","7","10"};
		comboNumberOfPixels = new JComboBox<String>(comboNumberOfPixels_tmp);
		comboNumberOfPixels.setLocation(294,40);
		comboNumberOfPixels.setSize(96,25);
		comboNumberOfPixels.setEditable(true);
		comboNumberOfPixels.addActionListener(this);
		getContentPane().add(comboNumberOfPixels);

		//initializes outputPathLabel and adds to frame
		outputPathLabel = new JLabel();
		outputPathLabel.setLocation(12, 55);
		outputPathLabel.setText("Output folder:");
		outputPathLabel.setSize(218, 51);
		outputPathLabel.setVisible(true);
		getContentPane().add(outputPathLabel);

		//initializes outputPathTextField and adds to frame
		outputPathTextField = new JTextField();
		String initPath;
		//sets default output to default directory, created in Main.java. looks according to operating system.
		if (OSUtils.isUnixOrLinux())
			initPath = System.getProperty("user.home") + "/.picture-to-ascii/output";
		else if (OSUtils.isWindows())
			initPath = System.getProperty("user.home") + "\\My Documents\\picture-to-ascii\\output";
		else
			initPath = "(output path)";
		//if the directory failed to create for reasons other than incompatible OS, we want (output path).
		if (!new File(initPath).isDirectory())
			initPath = "(output path)";

		outputPathTextField.setLocation(150, 70);
		outputPathTextField.setSize(240,25);
		outputPathTextField.setText(initPath);
		getContentPane().add(outputPathTextField);

		fontSizeTextField = new JTextField();
		fontSizeTextField.setLocation(150, 100);
		fontSizeTextField.setSize(240,25);
		fontSizeTextField.setText("5");
		getContentPane().add(fontSizeTextField);

		fontSizeLabel = new JLabel();
		fontSizeLabel.setText("Font size:");
		fontSizeLabel.setSize(125, 51);
		fontSizeLabel.setLocation(12, 85);
		fontSizeLabel.setVisible(true);
		getContentPane().add(fontSizeLabel);
	}
	
	/**
	* Made so ASCIIConverterFrame can communicate with ASCIIOptionsFrame and get the required output path during generation.
	*
	* @return The text from outputPathTextField.
	*/
	public String getOutputPath () 
	{
		return outputPathTextField.getText();
	}
	
	/**
	 * Made so ASCIIConverterFrame can communicate with ASCIIOptionsFrame and get the integer specified in the fontSizeTextField.
	 *
	 * @return the text from fontSizeTextField
	 */
	public String getFontSize () {
		return fontSizeTextField.getText();
	}

	@Override
	public void actionPerformed (ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == comboNumberOfPixels) 
		{
			acf.setPixelDensity(Integer.parseInt((String)comboNumberOfPixels.getSelectedItem()));
		}
	
		else if (source == comboCharSet) 
		{
			if (comboNumberOfPixels.getSelectedItem() == "Small")
				acf.setCharSetSize(0);
			else if (comboNumberOfPixels.getSelectedItem() == "Medium")
				acf.setCharSetSize(1);
			else
				acf.setCharSetSize(2);
		}
	
	}	
}

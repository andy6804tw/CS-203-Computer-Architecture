import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.System;
import java.security.AccessControlException;
import java.util.NoSuchElementException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class Montgomery_Multiplier_sim extends JApplet
{
	private JPanel argumentsPanel;
	private JPanel settingsPanel;
	private JPanel outputPanel;

	private JLabel argumentsTitle;
	private JLabel baseLabel;
	private JLabel exponentLabel;
	private JLabel modulusLabel;
	private JLabel settingsTitle;
	private JLabel addShiftLabel;
	private JLabel multSquareLabel;
	private JLabel modLabel;
	private JLabel sliderLabel1;
	private JLabel sliderLabel2;
	private JLabel frb1;
	private JLabel frb2;

	private JTextField baseBox;
	private JTextField exponentBox;
	private JTextField modulusBox;
	private JTextField addShiftBox;
	private JTextField multSquareBox;
	private JTextField modBox;

	private JButton fillRandomButton;
	private JButton startButton;

	private JSlider targetMagnitude;

	private JCheckBox verboseSelect;

	private JTextPane smText;
	private JTextPane mmText;

	private JScrollPane smPane;
	private JScrollPane mmPane;

	StyledDocument smStyledDoc;
	StyledDocument mmStyledDoc;

	private JSplitPane splitPane;

	static final String DEFAULT_BASE = "21967";
	static final String DEFAULT_EXPONENT = "5789";
	static final String DEFAULT_MOD = "59051";
	static final String DEFAULT_ADD_SHIFT = "1";
	static final String DEFAULT_MULT_SQR = "1";
	static final String DEFAULT_MOD_REDUCTION = "9";
	static final int MAG_MIN = 16;
	static final int MAG_MAX = 64;
	static final int MAG_INIT = 16;
	static final int MAG_DEFAULT = 32;

	public void init ()
	{
		initComponents();
	}

	private void initComponents()
	{
		getContentPane().setLayout(null);
		setBackground(new Color(0,0,0));

		//arguments panel
		argumentsPanel = new JPanel();
		argumentsPanel.setLayout(null);
		argumentsPanel.setBackground(Color.lightGray);
		argumentsPanel.setBorder(new EtchedBorder());

		argumentsTitle = new JLabel("Numeric Parameters");
		argumentsTitle.setForeground(Color.red);
		argumentsTitle.setFont(new Font("sansserif",Font.BOLD + Font.ITALIC ,16));
		argumentsPanel.add(argumentsTitle);
		argumentsTitle.setBounds(10, 10, 280, 20);

		baseLabel = new JLabel("base");
		exponentLabel = new JLabel("exponent");
		modulusLabel = new JLabel("modulus");

		baseBox = new JTextField(DEFAULT_BASE);
		exponentBox = new JTextField(DEFAULT_EXPONENT);
		modulusBox = new JTextField(DEFAULT_MOD);

		argumentsPanel.add(baseLabel);
		baseLabel.setBounds(10,40,130,20);
		argumentsPanel.add(exponentLabel);
		exponentLabel.setBounds(10,80,130,20);
		argumentsPanel.add(modulusLabel);
		modulusLabel.setBounds(10,120,130,20);

		argumentsPanel.add(baseBox);
		baseBox.setBounds(10,60,180,20);
		argumentsPanel.add(exponentBox);
		exponentBox.setBounds(10,100,180,20);
		argumentsPanel.add(modulusBox);
		modulusBox.setBounds(10,140,180,20);

		sliderLabel1 = new JLabel("target bit length for");
		sliderLabel2 = new JLabel("randomized parameters");
        argumentsPanel.add(sliderLabel1);
        sliderLabel1.setBounds(10,190,180,20);
        argumentsPanel.add(sliderLabel2);
        sliderLabel2.setBounds(10,205,180,20);

		targetMagnitude = new JSlider(JSlider.HORIZONTAL, MAG_MIN, MAG_MAX, MAG_INIT);
        targetMagnitude.setMajorTickSpacing(16);
        targetMagnitude.setMinorTickSpacing(1);
        targetMagnitude.setPaintTicks(true);
        targetMagnitude.setPaintLabels(true);
        targetMagnitude.setSnapToTicks(true);

		argumentsPanel.add(targetMagnitude);
		targetMagnitude.setBounds(10,225,180,50);

		fillRandomButton = new JButton();
		frb1 = new JLabel("Randomize");
		frb2 = new JLabel("Parameters");
		fillRandomButton.setLayout(new BorderLayout());
		fillRandomButton.add(BorderLayout.NORTH,frb1);
   		fillRandomButton.add(BorderLayout.SOUTH,frb2);

		argumentsPanel.add(fillRandomButton);
		fillRandomButton.setBounds(10,285,110,40);

		fillRandomButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				fillRandomButtonActionPerformed(ae);
			}
		});

		getContentPane().add(argumentsPanel);
		argumentsPanel.setBounds(0,0,200,350);

		//settings panel
		settingsPanel = new JPanel();
		settingsPanel.setLayout(null);
		settingsPanel.setBackground(Color.lightGray);
		settingsPanel.setBorder(new EtchedBorder());

		settingsTitle = new JLabel("Cycles per Operation");
		settingsTitle.setForeground(Color.red);
		settingsTitle.setFont(new Font("sansserif",Font.BOLD + Font.ITALIC ,16));
		settingsPanel.add(settingsTitle);
		settingsTitle.setBounds(10, 10, 280, 20);

		addShiftLabel = new JLabel("add/shift");
		multSquareLabel = new JLabel("multiply/square");
		modLabel = new JLabel("modular reduction");

		addShiftBox = new JTextField(DEFAULT_ADD_SHIFT);
		multSquareBox = new JTextField(DEFAULT_MULT_SQR);
		modBox = new JTextField(DEFAULT_MOD_REDUCTION);

		settingsPanel.add(addShiftLabel);
		addShiftLabel.setBounds(10,40,130,20);
		settingsPanel.add(multSquareLabel);
		multSquareLabel.setBounds(10,80,130,20);
		settingsPanel.add(modLabel);
		modLabel.setBounds(10,120,130,20);

		settingsPanel.add(addShiftBox);
		addShiftBox.setBounds(10,60,70,20);
		settingsPanel.add(multSquareBox);
		multSquareBox.setBounds(10,100,70,20);
		settingsPanel.add(modBox);
		modBox.setBounds(10,140,70,20);

		startButton = new JButton();
		startButton.setText("Launch Test");
		settingsPanel.add(startButton);
		startButton.setBounds(10,200,140,50);

		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				startButtonActionPerformed(ae);
			}
		});

		verboseSelect = new JCheckBox("verbose output", false);
		settingsPanel.add(verboseSelect);
		verboseSelect.setBounds(10,270,120,20);

		getContentPane().add(settingsPanel);
		settingsPanel.setBounds(0,350,200,300);

		//output panel
		outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBackground(Color.darkGray);
		outputPanel.setBorder(new EtchedBorder());

		smText = new JTextPane();
		smText.setPreferredSize(new Dimension(240,650));
		smText.setMargin(new Insets(5,5,5,5));
		smText.setEditable(false);
		smStyledDoc = smText.getStyledDocument();

		smPane = new JScrollPane(smText);
		//smPane.setBounds(0,0,250,650);

		mmText = new JTextPane();
		mmText.setPreferredSize(new Dimension(240,650));
		mmText.setMargin(new Insets(5,5,5,5));
		mmText.setEditable(false);
		mmStyledDoc = mmText.getStyledDocument();

		mmPane = new JScrollPane(mmText);
		//mmPane.setBounds(0,0,250,650);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, smPane, mmPane);
       	splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(3);

        smPane.setMinimumSize(new Dimension(240,650));
        mmPane.setMinimumSize(new Dimension(240,650));
        splitPane.setPreferredSize(new Dimension(500, 650));

		outputPanel.add(splitPane, BorderLayout.CENTER);
		splitPane.setBounds(0, 0, 500, 650);

		getContentPane().add(outputPanel);
		outputPanel.setBounds(200,0,500,650);
	}

	private void fillRandomButtonActionPerformed(ActionEvent ae)
	{
		int bitLength = targetMagnitude.getValue();

		if (bitLength < 2)
		{
			bitLength = MAG_DEFAULT;
		}

		BigInteger modulus, base, exponent;

		// use JAVA built in method to get mod
		modulus = BigInteger.probablePrime(bitLength, new Random());

		// flip every bit with 50% probability to generate base and exponent
		Random coinFlip = new Random();

		// first bit flipped must be a one or the result will be greater than
		// the modulus value
		boolean oneFlipped = false;
		base = modulus;
		for (int i = modulus.bitLength(); i >= 0; i--)
		{
			if(oneFlipped)
			{
				if(coinFlip.nextBoolean() )
				{
					base = base.flipBit(i);
				}
			}
			else
			{
				if(coinFlip.nextBoolean() )
				{
					if(base.testBit(i) )
					{
						base = base.flipBit(i);
						oneFlipped = true;
					}
				}
			}
		}

		oneFlipped = false;
		exponent = modulus;
		for (int i = modulus.bitLength(); i >= 0; i--)
		{
			if(oneFlipped)
			{
				if(coinFlip.nextBoolean() )
				{
					exponent = exponent.flipBit(i);
				}
			}
			else
			{
				if(coinFlip.nextBoolean() )
				{
					if(exponent.testBit(i) )
					{
						exponent = exponent.flipBit(i);
						oneFlipped = true;
					}
				}
			}
		}

		// update text boxes
		baseBox.setText(base.toString() );
		exponentBox.setText(exponent.toString() );
		modulusBox.setText(modulus.toString() );
	}

	private void startButtonActionPerformed(ActionEvent ae)
	{
		BigInteger base, exponent, modulus, R;
		Boolean verbose;
		String[] smOutput, mmOutput;

		base = getBase();
		exponent = getExponent();
		modulus = getModulus();
		verbose = getVerbose();

		// make R 2^b+1 where b is MSB position of modulus
		R = getR(modulus);

		Standard_method sm = new Standard_method(base, exponent, modulus, 10);
		MM_method mm = new MM_method(base, exponent, modulus, R, 10);

		sm.runAlgorithm();
		mm.runAlgorithm();

		// defin1e styles
		// heading
		MutableAttributeSet heading = new SimpleAttributeSet();
		StyleConstants.setAlignment(heading, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontFamily(heading, "SansSerif");
		StyleConstants.setItalic(heading, true);
		StyleConstants.setBold(heading, true);
		StyleConstants.setFontSize(heading, 20);
		StyleConstants.setForeground(heading, Color.black);
		StyleConstants.setLineSpacing(heading, new Float("1.5"));

		// subheading
		MutableAttributeSet subheading = new SimpleAttributeSet();
		StyleConstants.setAlignment(subheading, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(subheading, "SansSerif");
		StyleConstants.setItalic(subheading, false);
		StyleConstants.setBold(subheading, true);
		StyleConstants.setFontSize(subheading, 12);
		StyleConstants.setForeground(subheading, Color.black);
		StyleConstants.setLineSpacing(subheading, new Float(".5"));

		// standard
		MutableAttributeSet standard = new SimpleAttributeSet();
		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(standard, "SansSerif");
		StyleConstants.setItalic(standard, false);
		StyleConstants.setBold(standard, false);
		StyleConstants.setFontSize(standard, 12);
		StyleConstants.setLineSpacing(standard, new Float(".25"));
		StyleConstants.setForeground(standard, Color.black);

		// numeric_red
		MutableAttributeSet numeric_red = new SimpleAttributeSet();
		StyleConstants.setAlignment(numeric_red, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(numeric_red, "SansSerif");
		StyleConstants.setItalic(numeric_red, false);
		StyleConstants.setBold(numeric_red, false);
		StyleConstants.setFontSize(numeric_red, 14);
		StyleConstants.setLineSpacing(numeric_red, new Float(".25"));
		StyleConstants.setForeground(numeric_red, Color.red);

		// numeric_green
		MutableAttributeSet numeric_green = new SimpleAttributeSet();
		StyleConstants.setAlignment(numeric_green, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(numeric_green, "SansSerif");
		StyleConstants.setItalic(numeric_green, false);
		StyleConstants.setBold(numeric_green, false);
		StyleConstants.setFontSize(numeric_green, 14);
		StyleConstants.setLineSpacing(numeric_green, new Float(".25"));
		StyleConstants.setForeground(numeric_green, Color.green);

		// numeric_black
		MutableAttributeSet numeric_black = new SimpleAttributeSet();
		StyleConstants.setAlignment(numeric_black, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(numeric_black, "SansSerif");
		StyleConstants.setItalic(numeric_black, false);
		StyleConstants.setBold(numeric_black, false);
		StyleConstants.setFontSize(numeric_black, 14);
		StyleConstants.setLineSpacing(numeric_black, new Float(".25"));
		StyleConstants.setForeground(numeric_black, Color.black);

		// clear screen and print headings
		try
		{
			smText.setText("");
			mmText.setText("");
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, heading, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, heading, true);
			smStyledDoc.insertString(0, "Standard Method\n", heading);
			mmStyledDoc.insertString(0, "Montgomery Method\n", heading);
			// a hack approach to a problem where an additional line shows up on first write
			smText.setText("");
			mmText.setText("");
			smStyledDoc.insertString(0, "Standard Method\n", heading);
			mmStyledDoc.insertString(0, "Montgomery Method\n", heading);
		}
		catch (BadLocationException ble) {}

		// print algorithm details and verbose output if selected
		if (verbose)
		{
			smOutput = sm.getVerbose();
			mmOutput = mm.getVerbose();
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, subheading, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, subheading, true);
			try
			{
				smStyledDoc.insertString(smStyledDoc.getLength(), smOutput[0] + "\n", subheading);
				mmStyledDoc.insertString(mmStyledDoc.getLength(), mmOutput[0] + "\n", subheading);
			}
			catch (BadLocationException ble) {}
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, standard, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, standard, true);

			for (int i = 1; i < smOutput.length; i++)
			{
				try
				{
					smStyledDoc.insertString(smStyledDoc.getLength(), smOutput[i] + "\n", standard);
				}
				catch (BadLocationException ble) {}
			}

			for (int i = 1; i < mmOutput.length; i++)
			{
				try
				{
					mmStyledDoc.insertString(mmStyledDoc.getLength(), mmOutput[i] + "\n", standard);
				}
				catch (BadLocationException ble) {}
			}
		}
		else
		{
			smOutput = sm.getSimple();
			mmOutput = mm.getSimple();
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, subheading, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, subheading, true);
			try
			{
				smStyledDoc.insertString(smStyledDoc.getLength(), smOutput[0] + "\n", subheading);
				mmStyledDoc.insertString(mmStyledDoc.getLength(), mmOutput[0] + "\n", subheading);
			}
			catch (BadLocationException ble) {}
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, standard, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, standard, true);

			for (int i = 1; i < smOutput.length; i++)
			{
				try
				{
					smStyledDoc.insertString(smStyledDoc.getLength(), smOutput[i] + "\n", standard);
				}
				catch (BadLocationException ble) {}
			}

			for (int i = 1; i < mmOutput.length; i++)
			{
				try
				{
					mmStyledDoc.insertString(mmStyledDoc.getLength(), mmOutput[i] + "\n", standard);
				}
				catch (BadLocationException ble) {}
			}
		}

		// print results
		int smMultCount, smModCount;
		int mmAddShiftCount, mmMultCount, mmModCount;

		smMultCount = sm.getMultCount();
		smModCount = sm.getModCount();
		mmAddShiftCount = mm.getShiftAddCount();
		mmMultCount = mm.getMultCount();
		mmModCount = mm.getModCount();

		smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, subheading, true);
		mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, subheading, true);
		try
		{
			smStyledDoc.insertString(smStyledDoc.getLength(), "Operations by Type\n", subheading);
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Operations by Type\n", subheading);
		}
		catch (BadLocationException ble) {}

		smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, standard, true);
		mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, standard, true);
		try
		{
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Add/Shift = " + mmAddShiftCount + "\n", standard);
			smStyledDoc.insertString(smStyledDoc.getLength(), "Multiply/Square = " + smMultCount + "\n", standard);
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Multiply/Square = " + mmMultCount + "\n", standard);
			smStyledDoc.insertString(smStyledDoc.getLength(), "Mod Reduction = " + smModCount + "\n", standard);
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Mod Reduction = " + mmModCount + "\n", standard);
		}
		catch (BadLocationException ble) {}

		int mmCycles, smCycles;
		int multWeight, addWeight, modWeight;

		multWeight = getMultSquareWeight();
		addWeight = getAddShiftWeight();
		modWeight = getModReductionWeight();

		smCycles = (smMultCount * multWeight) + (smModCount * modWeight);
		mmCycles = (mmMultCount * multWeight) + (mmModCount * modWeight) + (mmAddShiftCount * addWeight);

		smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, subheading, true);
		mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, subheading, true);
		try
		{
			smStyledDoc.insertString(smStyledDoc.getLength(), "\nTotal Cycles\n", subheading);
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "\nTotal Cycles\n", subheading);
		}
		catch (BadLocationException ble) {}

		smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, standard, true);
		mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, standard, true);
		try
		{
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Cycles/Add = " + addWeight + "\n", standard);
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Cycles/Mult = " + multWeight + "\n", standard);
			mmStyledDoc.insertString(mmStyledDoc.getLength(), "Cycles/Mod = " + modWeight + "\n", standard);

			smStyledDoc.insertString(smStyledDoc.getLength(), "Cycles/Mult = " + multWeight + "\n", standard);
			smStyledDoc.insertString(smStyledDoc.getLength(), "Cycles/Mod = " + modWeight + "\n", standard);
		}
		catch (BadLocationException ble) {}

		if (smCycles < mmCycles)
		{
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, numeric_green, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, numeric_red, true);

			try
			{
				smStyledDoc.insertString(smStyledDoc.getLength(), "Total Cycles = " + smCycles + "\n", numeric_green);
				mmStyledDoc.insertString(mmStyledDoc.getLength(), "Total Cycles = " + mmCycles + "\n", numeric_red);
			}
			catch (BadLocationException ble) {}

		}
		else if (smCycles > mmCycles)
		{
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, numeric_red, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, numeric_green, true);

			try
			{
				smStyledDoc.insertString(smStyledDoc.getLength(), "Total Cycles = " + smCycles + "\n", numeric_red);
				mmStyledDoc.insertString(mmStyledDoc.getLength(), "Total Cycles = " + mmCycles + "\n", numeric_green);
			}
			catch (BadLocationException ble) {}

		}
		else
		{
			smStyledDoc.setParagraphAttributes(smStyledDoc.getLength(), 0, numeric_black, true);
			mmStyledDoc.setParagraphAttributes(mmStyledDoc.getLength(), 0, numeric_black, true);

			try
			{
				smStyledDoc.insertString(smStyledDoc.getLength(), "Total Cycles = " + smCycles + "\n", numeric_black);
				mmStyledDoc.insertString(mmStyledDoc.getLength(), "Total Cycles = " + mmCycles + "\n", numeric_black);
			}
			catch (BadLocationException ble) {}

		}

	}

	private BigInteger getBase()
	{
		String userEntry = baseBox.getText();
		BigInteger base, zero;

		zero = new BigInteger("0");
		try
		{
			base = new BigInteger(userEntry);
		}
		catch (NumberFormatException nfe)
		{
			base = new BigInteger(DEFAULT_BASE);
			resetDefaults(1);
		}

		// must be >= 0
		if (base.compareTo(zero) < 0)
		{
			base = new BigInteger(DEFAULT_BASE);
			resetDefaults(1);
		}

		return base;
	}

	private BigInteger getExponent()
	{
		String userEntry = exponentBox.getText();
		BigInteger exponent, one;

		one = new BigInteger("1");
		try
		{
			exponent = new BigInteger(userEntry);
		}
		catch (NumberFormatException nfe)
		{
			exponent = new BigInteger(DEFAULT_EXPONENT);
			resetDefaults(2);
		}

		// must be >= 1
		if (exponent.compareTo(one) < 0)
		{
			exponent = new BigInteger(DEFAULT_EXPONENT);
			resetDefaults(2);
		}

		return exponent;
	}

	private BigInteger getModulus()
	{
		String userEntry = modulusBox.getText();
		BigInteger modulus, one;

		one = new BigInteger("1");
		try
		{
			modulus = new BigInteger(userEntry);
		}
		catch (NumberFormatException nfe)
		{
			modulus = new BigInteger(DEFAULT_MOD);
			resetDefaults(3);
		}

		// must be >= 1
		if (modulus.compareTo(one) < 0)
		{
			modulus = new BigInteger(DEFAULT_MOD);
			resetDefaults(3);
		}

		// must be prime(well must be coprime to R but easier this way)
		if (modulus.isProbablePrime(10) )
		{

		}
		else
		{
			modulus = new BigInteger(DEFAULT_MOD);
			resetDefaults(3);
		}

		return modulus;
	}

	private int getAddShiftWeight ()
	{
		String userEntry = addShiftBox.getText();
		int addShiftWeight;

		try
		{
			addShiftWeight = Integer.parseInt(userEntry);
		}
		catch (NumberFormatException nfe)
		{
			addShiftWeight = Integer.parseInt(DEFAULT_ADD_SHIFT);
			resetDefaults(4);
		}

		// must be >= 1
		if (addShiftWeight < 1)
		{
			addShiftWeight = Integer.parseInt(DEFAULT_ADD_SHIFT);
			resetDefaults(4);
		}

		return addShiftWeight;
	}

	private int getMultSquareWeight ()
	{
		String userEntry = multSquareBox.getText();
		int multSquareWeight;

		try
		{
			multSquareWeight = Integer.parseInt(userEntry);
		}
		catch (NumberFormatException nfe)
		{
			multSquareWeight = Integer.parseInt(DEFAULT_MULT_SQR);
			resetDefaults(5);
		}

		// must be >= 1
		if (multSquareWeight < 1)
		{
			multSquareWeight = Integer.parseInt(DEFAULT_MULT_SQR);
			resetDefaults(5);
		}

		return multSquareWeight;
	}

	private int getModReductionWeight ()
	{
		String userEntry = modBox.getText();
		int modWeight;

		try
		{
			modWeight = Integer.parseInt(userEntry);
		}
		catch (NumberFormatException nfe)
		{
			modWeight = Integer.parseInt(DEFAULT_MOD_REDUCTION);
			resetDefaults(6);
		}

		// must be >= 1
		if (modWeight < 1)
		{
			modWeight = Integer.parseInt(DEFAULT_MOD_REDUCTION);
			resetDefaults(6);
		}

		return modWeight;
	}

	private void resetDefaults(int boxNumber)
	{
		// 1-6 top down as appear in gui, 7 all
		if (boxNumber == 1)
		{
			baseBox.setText(DEFAULT_BASE);
		}
		if (boxNumber == 2)
		{
			exponentBox.setText(DEFAULT_EXPONENT);
		}
		if (boxNumber == 3)
		{
			modulusBox.setText(DEFAULT_MOD);
		}
		if (boxNumber == 4)
		{
			addShiftBox.setText(DEFAULT_ADD_SHIFT);
		}
		if (boxNumber == 5)
		{
			multSquareBox.setText(DEFAULT_MULT_SQR);
		}
		if (boxNumber == 6)
		{
			modBox.setText(DEFAULT_MOD_REDUCTION);
		}
		if (boxNumber == 7)
		{
			baseBox.setText(DEFAULT_BASE);
			exponentBox.setText(DEFAULT_EXPONENT);
			modulusBox.setText(DEFAULT_MOD);
			addShiftBox.setText(DEFAULT_ADD_SHIFT);
			multSquareBox.setText(DEFAULT_MULT_SQR);
			modBox.setText(DEFAULT_MOD_REDUCTION);
		}
	}

	private Boolean getVerbose()
	{
		return verboseSelect.isSelected();
	}

	private BigInteger getR(BigInteger m)
	{
		int MSB = m.bitLength();

		BigInteger R = new BigInteger("0");
		R = R.setBit(MSB);

		return R;
	}
}
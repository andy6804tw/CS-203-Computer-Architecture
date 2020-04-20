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


public class EC_fault_detect_by_point_on_curve extends JApplet
{

	private JPanel curvePanel;
	private JPanel actionPanel;
	private JPanel outputPanel;
	private JPanel pointPanel;


	private JLabel curve_param_A;
	private JLabel curve_param_B;
	private JLabel x_coor;
	private JLabel y_coor;
	private JLabel ECm;
	private JLabel ECreduction_poly;
	private JLabel title;
	private JLabel curveTitle;
	private JLabel pointTitle;
	private JLabel outputTitle;

	private JTextField curve_param_A_box;
	private JTextField curve_param_B_box;
	private JTextField x_coor_box;
	private JTextField y_coor_box;
	private JTextField ECm_box;
	private JTextField ECreduction_poly_box;

	private JTextPane normalText;

	private JButton startButton;
	private JButton findPointButton;
	private JButton oneBitErrorButton;

	private JScrollPane normalPane;

	private  JRadioButton binary;
	private  JRadioButton decimal;
	private  JRadioButton hexadecimal;

	private  JRadioButton binary_output;
	private  JRadioButton decimal_output;
	private  JRadioButton hexadecimal_output;

	private ButtonGroup group;
	private ButtonGroup group_output;

	private String lastBase = new String("dec");
	private boolean started = false;

	private elliptic_curve curve;

	StyledDocument styledDoc;

	static final String DEFAULT_XCOOR = "12";
	static final String DEFAULT_YCOOR = "5";
	static final String DEFAULT_A = "3";
	static final String DEFAULT_B = "1";
	static final String DEFAULT_M = "4";
	static final String DEFAULT_MOD = "19";
	static final String DEFAULT_MOD_STRING = "X^4 + X + 1";

	public void init ()
	{
		initComponents();
	}

	private void initComponents()
	{
		getContentPane().setLayout(null);
		setBackground(new Color(0,0,0));

		//action panel
		actionPanel = new JPanel();
		actionPanel.setLayout(null);
		actionPanel.setBackground(Color.lightGray);
		actionPanel.setBorder(new EtchedBorder());

		outputTitle = new JLabel("Output Base");
		outputTitle.setForeground(Color.red);
		outputTitle.setFont(new Font("sansserif",Font.BOLD + Font.ITALIC ,16));
		actionPanel.add(outputTitle);
		outputTitle.setBounds(10, 10, 280, 20);

		binary_output = new JRadioButton("bin");
		binary_output.setActionCommand("bin");
		binary_output.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				baseChange_output(ae);
			}
		});

		decimal_output = new JRadioButton("dec");
		decimal_output.setActionCommand("dec");
		decimal_output.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				baseChange_output(ae);
			}
		});
		decimal_output.setSelected(true);

		hexadecimal_output = new JRadioButton("hex");
		hexadecimal_output.setActionCommand("hex");
		hexadecimal_output.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				baseChange_output(ae);
			}
		});

		group_output = new ButtonGroup();
		group_output.add(binary_output);
		group_output.add(decimal_output);
		group_output.add(hexadecimal_output);

		actionPanel.add(binary_output);
		binary_output.setBounds(10,50,60, 20);
		actionPanel.add(decimal_output);
		decimal_output.setBounds(115,50,60, 20);
		actionPanel.add(hexadecimal_output);
		hexadecimal_output.setBounds(220,50,60, 20);

		getContentPane().add(actionPanel);
		actionPanel.setBounds(0,450,300,100);

	    //Curve panel
		curvePanel = new JPanel();
		curvePanel.setLayout(null);
		curvePanel.setBackground(Color.lightGray);
		curvePanel.setBorder(new EtchedBorder());

		curveTitle = new JLabel("Elliptic Curve Parameters");
		curveTitle.setForeground(Color.red);
		curveTitle.setFont(new Font("sansserif",Font.BOLD + Font.ITALIC ,16));
		curvePanel.add(curveTitle);
		curveTitle.setBounds(10, 10, 280, 20);

		curve_param_A = new JLabel("curve parameter a:");
		curve_param_B = new JLabel("curve parameter b:");
		ECm = new JLabel("curve parameter m:");
		ECreduction_poly = new JLabel("irreducible polynomial:");

		curve_param_A_box = new JTextField(DEFAULT_A);
		curve_param_B_box = new JTextField(DEFAULT_B);
		ECm_box = new JTextField(DEFAULT_M);
		ECreduction_poly_box = new JTextField(DEFAULT_MOD_STRING);

		curvePanel.add(curve_param_A);
		curve_param_A.setBounds(10,50,130,20);
		curvePanel.add(curve_param_B);
		curve_param_B.setBounds(10,80,130,20);
		curvePanel.add(ECm);
		ECm.setBounds(10,110,130,20);
		curvePanel.add(ECreduction_poly);
		ECreduction_poly.setBounds(10,140,130,20);

		curvePanel.add(curve_param_A_box);
		curve_param_A_box.setBounds(150,50,140,20);
		curvePanel.add(curve_param_B_box);
		curve_param_B_box.setBounds(150,80,140,20);
		curvePanel.add(ECm_box);
		ECm_box.setBounds(150,110,140,20);
		curvePanel.add(ECreduction_poly_box);
		ECreduction_poly_box.setBounds(150,140,140,20);

		getContentPane().add(curvePanel);
		curvePanel.setBounds(0,260,300,200);

		//Point panel
		pointPanel = new JPanel();
		pointPanel.setLayout(null);
		pointPanel.setBackground(Color.lightGray);
		pointPanel.setBorder(new EtchedBorder());

		pointTitle = new JLabel("Point Pair");
		pointTitle.setForeground(Color.red);
		pointTitle.setFont(new Font("sansserif",Font.BOLD + Font.ITALIC ,16));
		pointPanel.add(pointTitle);
		pointTitle.setBounds(10, 10, 280, 20);

		x_coor = new JLabel("X coordinate:");
		y_coor = new JLabel("Y coordinate:");

		x_coor_box = new JTextField(DEFAULT_XCOOR);
		y_coor_box = new JTextField(DEFAULT_YCOOR);

		pointPanel.add(x_coor);
		x_coor.setBounds(10,40,105,20);
		pointPanel.add(y_coor);
		y_coor.setBounds(10,80,105,20);

		pointPanel.add(x_coor_box);
		x_coor_box.setBounds(10,60,280,20);
		pointPanel.add(y_coor_box);
		y_coor_box.setBounds(10,100,280,20);

		binary = new JRadioButton("bin");
		binary.setActionCommand("bin");
		binary.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				baseChange(ae);
			}
		});

		decimal = new JRadioButton("dec");
		decimal.setActionCommand("dec");
		decimal.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				baseChange(ae);
			}
		});
		decimal.setSelected(true);

		hexadecimal = new JRadioButton("hex");
		hexadecimal.setActionCommand("hex");
		hexadecimal.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				baseChange(ae);
			}
		});

		group = new ButtonGroup();
		group.add(binary);
		group.add(decimal);
		group.add(hexadecimal);

		pointPanel.add(binary);
		binary.setBounds(10,130,60, 20);
		pointPanel.add(decimal);
		decimal.setBounds(115,130,60, 20);
		pointPanel.add(hexadecimal);
		hexadecimal.setBounds(220,130,60, 20);


		findPointButton = new JButton();
		findPointButton.setText("Find Point");
		pointPanel.add(findPointButton);
		findPointButton.setBounds(20,170,100,30);

		findPointButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				findPointButtonActionPerformed(ae);
			}
		});

		oneBitErrorButton = new JButton();
		oneBitErrorButton.setText("Flip Bit");
		pointPanel.add(oneBitErrorButton);
		oneBitErrorButton.setBounds(20,210,100,30);

		oneBitErrorButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				oneBitErrorButtonActionPerformed(ae);
			}
		});

		startButton = new JButton();
		startButton.setText("Launch Test");
		pointPanel.add(startButton);
		startButton.setBounds(139,180,140,50);

		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				startButtonActionPerformed(ae);
			}
		});

		getContentPane().add(pointPanel);
		pointPanel.setBounds(0,0,300,260);



		//output panel
		outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setBackground(Color.darkGray);
		outputPanel.setBorder(new EtchedBorder());

		normalText = new JTextPane();
		normalText.setPreferredSize(new Dimension(400,550));
		normalText.setMargin(new Insets(5,5,5,5));
		normalText.setEditable(false);

		styledDoc = normalText.getStyledDocument();

		normalPane = new JScrollPane(normalText);
		normalPane.setBounds(0,0,400,550);
		outputPanel.add(normalPane, BorderLayout.CENTER);

		getContentPane().add(outputPanel);
		outputPanel.setBounds(300,0,400,550);
	}

	private void startButtonActionPerformed(ActionEvent ae)
	{
		started = true;
		drawOutput();
	}
	private void baseChange(ActionEvent ae)
	{
		Update_coors_base(ae.getActionCommand());
	}

	private void baseChange_output(ActionEvent ae)
	{
		if (started)
		{
			drawOutput();
		}
	}

	private void drawOutput ()
	{
		readECC();

		int outputbase;
		if (binary_output.isSelected() )
		{
			outputbase = 2;
		}
		else if (decimal_output.isSelected() )
		{
			outputbase = 10;
		}
		else
		{
			outputbase = 16;
		}

		BigInteger coors[];
		BigInteger y2, xy, x3, ax2, left_side, right_side;
		Float line_spacing = new Float("3.5");

		try
		{
			styledDoc.insertString(0, "\n", null);
		}
		catch (BadLocationException ble) {}


		coors = read_point_coordinates();

		y2 = curve.gf2m_square(coors[1]);
		y2 = curve.gf2m_mod_reduction(curve.get_reduction_poly() , y2);

		xy = curve.gf2m_mult(coors[0] , coors[1]);
		xy = curve.gf2m_mod_reduction(curve.get_reduction_poly() , xy);

		x3 = curve.gf2m_square(coors[0]);
		x3 = curve.gf2m_mod_reduction(curve.get_reduction_poly() , x3);
		x3 = curve.gf2m_mult(coors[0] , x3);
		x3 = curve.gf2m_mod_reduction(curve.get_reduction_poly() , x3);

		ax2 = curve.gf2m_square(coors[0]);
		ax2 = curve.gf2m_mod_reduction(curve.get_reduction_poly() , ax2);
		ax2 = curve.gf2m_mult(ax2 , curve.getA() );
		ax2 = curve.gf2m_mod_reduction(curve.get_reduction_poly() , ax2);

		left_side = curve.gf2m_add(y2, xy);
		right_side = curve.gf2m_add(curve.getB(), curve.gf2m_add(x3 , ax2) );

		// defin1e styles
		// heading
		MutableAttributeSet heading = new SimpleAttributeSet();
		StyleConstants.setAlignment(heading, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontFamily(heading, "SansSerif");
		StyleConstants.setItalic(heading, true);
		StyleConstants.setBold(heading, true);
		StyleConstants.setFontSize(heading, 36);
		StyleConstants.setForeground(heading, Color.black);
		StyleConstants.setLineSpacing(heading, new Float("1.5"));

		// standard
		MutableAttributeSet standard = new SimpleAttributeSet();
		StyleConstants.setAlignment(standard, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(standard, "SansSerif");
		StyleConstants.setItalic(standard, false);
		StyleConstants.setBold(standard, false);
		StyleConstants.setFontSize(standard, 16);
		StyleConstants.setLineSpacing(standard, new Float("1"));
		StyleConstants.setForeground(standard, Color.black);

		// numeric_black
		MutableAttributeSet numeric_black = new SimpleAttributeSet();
		StyleConstants.setAlignment(numeric_black, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontFamily(numeric_black, "SansSerif");
		StyleConstants.setItalic(numeric_black, true);
		StyleConstants.setBold(numeric_black, false);
		StyleConstants.setFontSize(numeric_black, 14);
		StyleConstants.setLineSpacing(numeric_black, new Float("1"));
		StyleConstants.setForeground(numeric_black, Color.black);

		// numeric_red
		MutableAttributeSet numeric_red = new SimpleAttributeSet();
		StyleConstants.setAlignment(numeric_red, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontFamily(numeric_red, "SansSerif");
		StyleConstants.setItalic(numeric_red, true);
		StyleConstants.setBold(numeric_red, false);
		StyleConstants.setFontSize(numeric_red, 14);
		StyleConstants.setLineSpacing(numeric_red, new Float("1"));
		StyleConstants.setForeground(numeric_red, Color.red);

		// numeric_green
		MutableAttributeSet numeric_green = new SimpleAttributeSet();
		StyleConstants.setAlignment(numeric_green, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontFamily(numeric_green, "SansSerif");
		StyleConstants.setItalic(numeric_green, true);
		StyleConstants.setBold(numeric_green, true);
		StyleConstants.setFontSize(numeric_green, 14);
		StyleConstants.setLineSpacing(numeric_green, new Float("1"));
		StyleConstants.setForeground(numeric_green, Color.green);

		if (curve.point_on_curve(coors[0] , coors[1] ) )
		{
			try
			{
			styledDoc.remove(0 , styledDoc.getLength());
			}
			catch (BadLocationException ble) {}


			String initString[] =
			{"Point on Curve Test\n",
			" A fault is detected if equation (1) is not satisfied.\n",
			"y^2 + xy = x^3 + ax^2 + b	(1)\n",
			" Using the parameters provided, this equation yields:\n",
			y2.toString(outputbase).toUpperCase()  + " + " + xy.toString(outputbase).toUpperCase()  + " = " + x3.toString(outputbase).toUpperCase()  + " + " + ax2.toString(outputbase).toUpperCase()  + " + " + curve.getB().toString(outputbase).toUpperCase() + "\n",
			" Performing bit-wise addition shows (1) is satisfied:\n",
			left_side.toString(outputbase).toUpperCase()  + " = " + right_side.toString(outputbase).toUpperCase()};


			try
			{

				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, heading, true);
				styledDoc.insertString(0, initString[0], heading);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, standard, false);
				styledDoc.insertString(styledDoc.getLength(), initString[1], standard);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, numeric_black, false);
				styledDoc.insertString(styledDoc.getLength(), initString[2], numeric_black);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, standard, false);
				styledDoc.insertString(styledDoc.getLength(), initString[3], standard);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, numeric_black, false);
				styledDoc.insertString(styledDoc.getLength(), initString[4], numeric_black);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, standard, false);
				styledDoc.insertString(styledDoc.getLength(), initString[5], standard);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, numeric_green, false);
				styledDoc.insertString(styledDoc.getLength(), initString[6], numeric_green);

			}
			catch(Exception e) {}


		}
		else
		{
			try
			{
				styledDoc.remove(0 , styledDoc.getLength());
			}
			catch (BadLocationException ble) {}

			String initString[] =
			{" Point on Curve Test\n",
			" A fault is detected if equation (1) is not satisfied.\n",
			" y^2 + xy = x^3 + ax^2 + b	(1)\n",
			" Using the parameters provided, this equation yields:\n",
			y2.toString(outputbase).toUpperCase()  + " + " + xy.toString(outputbase).toUpperCase()  + " = " + x3.toString(outputbase).toUpperCase()  + " + " + ax2.toString(outputbase).toUpperCase()  + " + " + curve.getB().toString(outputbase).toUpperCase() + "\n",
			" Performing bit-wise addition shows (1) is not satisfied:\n",
			left_side.toString(outputbase).toUpperCase()  + " = " + right_side.toString(outputbase).toUpperCase()};


			try
			{
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, heading, true);
				styledDoc.insertString(0, initString[0], heading);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, standard, false);
				styledDoc.insertString(styledDoc.getLength(), initString[1], standard);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, numeric_black, false);
				styledDoc.insertString(styledDoc.getLength(), initString[2], numeric_black);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, standard, false);
				styledDoc.insertString(styledDoc.getLength(), initString[3], standard);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, numeric_black, false);
				styledDoc.insertString(styledDoc.getLength(), initString[4], numeric_black);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, standard, false);
				styledDoc.insertString(styledDoc.getLength(), initString[5], standard);
				styledDoc.setParagraphAttributes(styledDoc.getLength(), 0, numeric_red, false);
				styledDoc.insertString(styledDoc.getLength(), initString[6], numeric_red);

			}
			catch(Exception e) {}

		}
	}
	private void findPointButtonActionPerformed(ActionEvent ae)
	{
		BigInteger coors[];
		int radix;

		readECC();

		curve.generatePoint();
		coors = curve.getGeneratedPoint();

		if (lastBase.equals("bin") )
		{
			radix = 2;
		}
		else if (lastBase.equals("dec") )
		{
			radix = 10;
		}
		else
		{
			radix = 16;
		}

		fillPointFields(coors[0], coors[1], radix);
	}

	private void oneBitErrorButtonActionPerformed(ActionEvent ae)
	{
		readECC();

		BigInteger coors[] = new BigInteger[2];
		int radix;

		coors = read_point_coordinates();

		Random prng = new Random();

		//pick x or y at random
		if (prng.nextBoolean() )
		{
			// flip a bit in x
			coors[0] = coors[0].flipBit( prng.nextInt(curve.get_M() ) );
			// update gui
			if (lastBase.equals("bin") )
			{
				radix = 2;
			}
			else if (lastBase.equals("dec") )
			{
				radix = 10;
			}
			else
			{
				radix = 16;
			}

			fillPointFields(coors[0], coors[1], radix);
		}
		else
		{
			// flip a bit in y
			coors[1] = coors[1].flipBit( prng.nextInt(curve.get_M()  ) );
			// update gui
			if (lastBase.equals("bin") )
			{
				radix = 2;
			}
			else if (lastBase.equals("dec") )
			{
				radix = 10;
			}
			else
			{
				radix = 16;
			}

			fillPointFields(coors[0], coors[1], radix);
		}
	}
	private BigInteger[] read_curve_param()
	{
		BigInteger curve_params[] = new BigInteger[4];
		try{
			curve_params[0] = new BigInteger(curve_param_A_box.getText());
			curve_params[1] = new BigInteger(curve_param_B_box.getText());
			curve_params[2] = new BigInteger(ECm_box.getText());
			curve_params[3] = parseModulus(ECreduction_poly_box.getText() );
		}catch (NumberFormatException nfe)
		{
			curve_params[0] = new BigInteger(DEFAULT_A);
			curve_params[1] = new BigInteger(DEFAULT_B);
			curve_params[2] = new BigInteger(DEFAULT_M);
			curve_params[3] = new BigInteger(DEFAULT_MOD);
			updateECParams();
		}

		//check if m is valid with respect to poly
		int m = curve_params[2].intValue() + 1;

		if (curve_params[3].bitLength() != m){
			curve_params[0] = new BigInteger(DEFAULT_A);
			curve_params[1] = new BigInteger(DEFAULT_B);
			curve_params[2] = new BigInteger(DEFAULT_M);
			curve_params[3] = new BigInteger(DEFAULT_MOD);
			updateECParams();
		}

		//check a,b are non negative
		if (curve_params[0].intValue() < 0 | curve_params[1].intValue() < 0)
		{
			curve_params[0] = new BigInteger(DEFAULT_A);
			curve_params[1] = new BigInteger(DEFAULT_B);
			curve_params[2] = new BigInteger(DEFAULT_M);
			curve_params[3] = new BigInteger(DEFAULT_MOD);
			updateECParams();
		}

		return curve_params;

	}

	private BigInteger[] read_point_coordinates()
	{
		BigInteger coors[] = new BigInteger[2];
		int radix;

		if (lastBase.equals("bin") )
				{
					radix = 2;
				}
				else if (lastBase.equals("dec") )
				{
					radix = 10;
				}
				else
				{
					radix = 16;
		}

		try
		{
			coors[0] = new BigInteger(x_coor_box.getText(), radix);
			coors[1] = new BigInteger(y_coor_box.getText(), radix);
		}catch (NumberFormatException nfe)
		{
			coors[0] = new BigInteger(DEFAULT_XCOOR, 10);
			coors[1] = new BigInteger(DEFAULT_YCOOR, 10);
			fillPointFields(coors[0], coors[1], radix);
		}
		return coors;
	}

	private void Update_coors_base(String base)
	{
		String x_box, y_box;
		x_box = x_coor_box.getText();
		y_box = y_coor_box.getText();

		int old_radix, new_radix;

		if (base.equals("bin") )
		{
			new_radix = 2;
		}
		else if (base.equals("dec") )
		{
			new_radix = 10;
		}
		else
		{
			new_radix = 16;
		}

		if (lastBase.equals("bin") )
		{
			old_radix = 2;
		}
		else if (lastBase.equals("dec") )
		{
			old_radix = 10;
		}
		else
		{
			old_radix = 16;
		}

		try
		{
			BigInteger x_coor = new BigInteger(x_box , old_radix);
			BigInteger y_coor = new BigInteger(y_box , old_radix);
			lastBase = base;
			x_coor_box.setText((x_coor.toString(new_radix)).toUpperCase() );
			y_coor_box.setText((y_coor.toString(new_radix)).toUpperCase() );
		}catch (NumberFormatException nfe)
		{
			BigInteger x_coor = new BigInteger(DEFAULT_XCOOR, 10);
			BigInteger y_coor = new BigInteger(DEFAULT_YCOOR, 10);
			fillPointFields(x_coor, y_coor, new_radix);
			lastBase = base;
			x_coor_box.setText((x_coor.toString(new_radix)).toUpperCase() );
			y_coor_box.setText((y_coor.toString(new_radix)).toUpperCase() );
		}




	}

	private void fillPointFields(BigInteger x, BigInteger y, int radix)
	{
		x_coor_box.setText((x.toString(radix)).toUpperCase() );
		y_coor_box.setText((y.toString(radix)).toUpperCase() );

	}

	private void updateECParams()
	{
		curve_param_A_box.setText(DEFAULT_A);
		curve_param_B_box.setText(DEFAULT_B);
		ECm_box.setText(DEFAULT_M);
		ECreduction_poly_box.setText(DEFAULT_MOD_STRING);
	}

	private void readECC()
	{
		BigInteger curve_params[];
		ECFieldF2m ecf;
		curve_params = read_curve_param();

		try{
			ecf = new ECFieldF2m( (curve_params[2]).intValue() , curve_params[3]);
		} catch (IllegalArgumentException iae)
		{
			curve_params[0] = new BigInteger(DEFAULT_A);
			curve_params[1] = new BigInteger(DEFAULT_B);
			curve_params[2] = new BigInteger(DEFAULT_M);
			curve_params[3] = new BigInteger(DEFAULT_MOD);
			updateECParams();
			ecf = new ECFieldF2m( (curve_params[2]).intValue() , curve_params[3]);
		}
		curve = new elliptic_curve( new EllipticCurve(ecf, curve_params[0], curve_params[1]) , curve_params[3] );
	}

	private BigInteger parseModulus(String expression)
	{
		//the expression is assumed to be x^power and ends in 1 for x^0*1
		Pattern modulus = Pattern.compile("([Xx]\\s*\\^*\\s*(\\d*))|\\+\\s*(1)");
		Matcher m = modulus.matcher(expression);
		BigInteger irreducible = new BigInteger("0");

		while (m.find() )
		{
			int bitToSet = new Integer("-1");
			//set lsb if 1 matched
			if (m.group(3) != null)
			{
				bitToSet = new Integer("0");
			}
			else if (m.group(1) != null)
			{
				String exponent = m.group(2);
				try
				{
					bitToSet = new Integer(exponent);
				}catch (NumberFormatException nfe)
				{
					//assuming empty string because of implied power 1
					bitToSet = new Integer("1");
				}
			}

			// build BigInt
			try{
				irreducible = irreducible.setBit(bitToSet);
			}catch (ArithmeticException ae)
			{}

		}

		// test the modulus, return to default if neccessary
		BigInteger testCase = irreducible;
		int counter = 0;

		if (testCase.getLowestSetBit() == 0)
		{

			for (int i = 0 ; i <= testCase.bitLength(); i++)
			{
				if (testCase.testBit(i))
				{
					counter++;
				}
			}
		}
		else
		{
			if (counter == 3 || counter == 5)
			{

			}
			else
			{
				irreducible = new BigInteger(DEFAULT_MOD);
				ECreduction_poly_box.setText(DEFAULT_MOD_STRING);
			}
		}
		return irreducible;
	}

}
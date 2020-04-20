import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class TablePanel extends Canvas
{
    public final int squareLen = 40;

    // The reservation table
    public Table table;
    
    // # of rows and columns
    private int rows;
    private int cols;
    
    // A list of the squares that were marked in 
    // the Reservation Table
    private Vector markList;

    // Name of the reservation table
    private String name;

    //int width, height;

    // Constructor    
    public TablePanel(Table table, String name, int rows, int cols)
    {
	this.table = table;
	this.rows = rows;
	this.cols = cols;
	this.name = name;
	markList = new Vector();
	//width = 1;
	//height = 1;

	// If a mouse button was pressed go process
	// the mouse button
	this.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		mark(e.getX(), e.getY());
	    }
	});
    }
       	
    public Dimension getPreferredSize()
    {
	return new Dimension((cols+2)*squareLen,(rows+1)*squareLen);
    }
    

    // Remove the mark from the square specified by row and col
    public void unmark(int row, int col)
    {
	for(int i=0; i<markList.size(); i++) {
	    Point square = (Point)(markList.elementAt(i));
	    if(square.x == row && square.y == col) {
		markList.removeElementAt(i);
		return;
	    }
	}
    }

    // Given a screen coordinate, find if that point maps
    // to some square in the Reseration Table. If it does
    // check if that square has already been marked or not.
    // If it has not been marked mark it else remove the mark
    // that was already there.
    public void mark(int x, int y)
    {
	for(int i=1; i<=rows; i++) {
	    for(int j=1; j<=cols; j++) {
		// If the x and y map to a certain square
		// process it
		if((x>=26 && x<=squareLen*j+26) &&
		   (y>=15 && y<=squareLen*i+15)) {
		    // If the square is not marked then mark it
		    if(table.getValue(i-1,j-1)==0) {
			markList.addElement(new Point(j-1, i-1));
			table.mark(i-1, j-1);
			table.setModified(true);
		    }
		    // Else remove the mark
		    else {
			unmark(j-1, i-1);
			table.unmark(i-1, j-1);
			table.setModified(true);
		    }
		    repaint();
		    return;
		}
	    }
	}
    }

    // Draw the Reservation Table
    public void paint(Graphics g)
    {
	g.setFont(new Font("Courier", Font.BOLD, 12));

	// Draw the name of the reservation table
	g.setColor(Color.yellow);
	g.drawString(" "+name,0,12);
	
	// Draw Resource labels
	g.setColor(Color.green);
	for(int i=0; i<rows; i++) {
	    g.drawString("S"+i,0,squareLen*i+squareLen);
	}

	// Draw time labels
	g.setColor(Color.blue);
	for(int i=0; i<cols; i++) {
	    g.drawString("T"+i,squareLen*i+squareLen,12);
	}

	// Draw horizontal lines
	g.setColor(Color.white);
	for(int i=0; i<rows; i++) {
	    g.drawLine(0, squareLen*i+15, squareLen*cols+26, squareLen*i+15);
	}
	if(rows!=0)
	    g.drawLine(0, squareLen*rows+15, squareLen*cols+26, squareLen*rows+15);

	// Draw vertical lines
	g.setColor(Color.white);
	for(int i=0; i<cols; i++) {
	    g.drawLine(squareLen*i+26, 0, squareLen*i+26, squareLen*rows+15);
	}
	if(cols!=0)
	    g.drawLine(squareLen*cols+26, 0, squareLen*cols+26, squareLen*rows+15);

	// Draw marks
	for(int i=0; i<markList.size(); i++) {
	    Point square = (Point)markList.elementAt(i);
	    g.setColor(Color.red);
	    g.drawLine(squareLen*square.x+26, squareLen*square.y+15,
		       squareLen*square.x+26+squareLen, squareLen*square.y+15+squareLen);
	    g.drawLine(squareLen*square.x+26, squareLen*square.y+15+squareLen,
		       squareLen*square.x+26+squareLen, squareLen*square.y+15);
	}

	/* Interesting stuff
        Dimension size = new Dimension((cols+2)*squareLen,(rows+1)*squareLen);
	Dimension currSize = getSize();
	if(size.width>=currSize.width || size.height>=currSize.height) {
	    setSize(size);
	    getParent().validate();
	    System.out.println(size.width);
	    System.out.println(currSize.width);
	    System.out.println("Validated");
	}
	*/
    }
}


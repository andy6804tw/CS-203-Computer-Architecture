import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TextPanel extends Panel
{
    Dimension panelSize;
    Point cursor;
    Vector text;
    Font f;
    FontMetrics fm;
    int lineSpacing;
    ScrollPane scrollBar;

    public TextPanel()
    {
	setLayout(new BorderLayout());
	f = new Font("Arial", Font.BOLD, 20);
	fm = getFontMetrics(f);
	lineSpacing = fm.getHeight()-3; 
	cursor = new Point(0,lineSpacing);
	panelSize = new Dimension(1,lineSpacing);
	text = new Vector();
	setFont(f);
	scrollBar = new ScrollPane();
	scrollBar.setSize(175,300);
	scrollBar.add(this);
    }

    public Dimension getPreferredSize()
    {
	return getSize();
    }

    public void append(String string, Color color)
    {
	text.addElement(new TextString(string, cursor, color));
	cursor.x += fm.stringWidth(string+" ");
	panelSize = getSize();
	if(cursor.x >= panelSize.width) {
	    setSize(cursor.x, panelSize.height);
	    scrollBar.add(this);
	}
    }

    public void appendNewLine(String string, Color color)
    {
	cursor.y += lineSpacing;
	cursor.x = 0;
	text.addElement(new TextString(string, cursor, color));
	cursor.x += fm.stringWidth(string+" ");

	panelSize = getSize();
	if(cursor.x >= panelSize.width) {
	    setSize(cursor.x, panelSize.height);
	    scrollBar.add(this);
	}
	
	panelSize = getSize();
	if(cursor.y >= panelSize.height) {
	    setSize(panelSize.width, cursor.y);
	    scrollBar.add(this);
	}
    }

    public void paint(Graphics g)
    {
	for(int i=0; i<text.size(); i++) {
	    TextString t = (TextString)text.elementAt(i);
	    g.setColor(t.color);
	    g.drawString(t.text, t.location.x, t.location.y);
	}
    }
}

class TextString
{
    public String text;
    public Point location;
    public Color color;
    
    public TextString(String text, Point location, Color color)
    {
	this.text = text;
	this.location = new Point(location.x, location.y);
	this.color = color;
    }
}

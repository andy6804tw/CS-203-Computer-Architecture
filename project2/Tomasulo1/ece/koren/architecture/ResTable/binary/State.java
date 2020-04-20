import java.util.*;
import java.applet.Applet;
import java.awt.*;

public class State
{
    // State #
    int id;
    
    // The matrix representation of the state
    Matrix m;
    
    // The possible states that can be reached 
    // from this state by performing tastks A or B 
    Vector edgeA;
    Vector edgeB;
    
    // Constructor
    public State(int id, Matrix m)
    {
	this.id = id;
	this.m = new Matrix(m);
	edgeA = new Vector(10);
	edgeB = new Vector(10);
    }

    // Print out the state information onto the Java Console
    public void print()
    {
	System.out.println("State #"+id);
	m.print();
    }
    
    public void print(TextPanel panel)
    {
	panel.appendNewLine("State #"+id, Color.yellow);
	m.print(panel);
    }

    // Print out the possible states that this state can reach
    // and the edges taken to get to those states onto Java Console
    void printEdges()
    {
	for(int i=0; i<edgeA.size(); i++) {
	    Edge p = (Edge)edgeA.elementAt(i);
	    p.print();
	}
	
	for(int i=0; i<edgeB.size(); i++) {
	    Edge p = (Edge)edgeB.elementAt(i);
	    p.print();
	}
    }

    public void printEdges(TextPanel panel)
    {
	panel.appendNewLine("State #"+id, Color.green);
	
	for(int i=0; i<edgeA.size(); i++) {
	    Edge p = (Edge)edgeA.elementAt(i);
	    p.print(panel);
	}

	for(int i=0; i<edgeB.size(); i++) {
	    Edge p = (Edge)edgeB.elementAt(i);
	    p.print(panel);
	}
    }
}

import java.awt.*;

public class Edge
{
    // The task A or B performed to reach the "nextState"
    char task;
    
    // The number of cycles it took to reach the "nextState"
    int cycles;

    // The state reached after performing a task A or B and
    // a certain amount of cycles
    State nextState;
    
    // Constructor
    public Edge(char task, int cycles, State nextState)
    {
	this.task = task;
	this.cycles = cycles;
	this.nextState = nextState;
    }
    
    // Print the edge information onto the Java Console
    public void print()
    {
	String tmp="";
	tmp += String.valueOf(task) + String.valueOf(cycles) +" -> State #"+nextState.id;
	System.out.println(tmp);
    }

    public void print(TextPanel panel)
    {
	panel.appendNewLine("     Reaches", Color.white);
	panel.append("State #"+nextState.id, Color.green);
	panel.append("after", Color.white);
	panel.append(String.valueOf(task) + String.valueOf(cycles), Color.red);

	if(cycles<nextState.m.getCols())
	    panel.append("cycles", Color.white);
	else
	    panel.append("cycles or more", Color.white);
    }
}

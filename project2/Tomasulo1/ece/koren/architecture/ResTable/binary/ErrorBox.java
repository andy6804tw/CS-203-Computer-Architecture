import java.awt.*;
import java.awt.event.*;

class ErrorBox extends Dialog
{
    // The Error label
    Label errorLabel;
    
    // Ok button
    Button ok;

    ErrorBox(Frame f, String title, String message)
    {
	// Pop up a dialog box showing the error message
	super(f, title);
	setBackground(Color.red);
	setLocation(f.getSize().width/2, f.getSize().height/2);
	errorLabel = new Label(message);
	add(errorLabel,"Center");
	ok = new Button("OK");
	add(ok,"South");
	setResizable(false);
	pack();
	show();
	
	// Kill dialog box
	ok.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		dispose();
	    }
	});

	this.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		dispose();
	    }
	});
    }
}

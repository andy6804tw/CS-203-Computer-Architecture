import java.awt.*;
import java.applet.*;

public class ErrorWindow extends Applet {

  public void init() {
    DialogFrame d= new DialogFrame("Replacement Policy Simulator Error Message");
//    resize(800,100);
//    setBounds(300,200,400,200);
      d.pack();
      d.setLocationRelativeTo(null);


//    validate();
  }


  public class DialogFrame extends Frame {

    DialogFrame(String title) {
      super(title);
      setLayout(new GridLayout(3,1));
      add(new Label("One or more errors have occurred when trying to run simulator, check your inputs and try again."));
      add(new Label("           If you still have problems refer to the help section at the bottom of the page.     "));
      Button b= new Button("OK");
      b.setSize(5,5);
      add(b);

      resize(800, 600);
      pack();
      show();
    }

  public boolean action(Event e, Object arg) {
    String label = (String)arg;

    if (label.equals("OK")) {
      dispose();
    }
    return true;
  }

  }
}

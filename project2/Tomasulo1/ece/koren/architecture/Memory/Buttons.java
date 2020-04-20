import java.awt.*;

public class Buttons extends Panel
{

Button     button1;
Button     button2;
Button     button3;
//Button     button4;
//Button     button5;
public Buttons()
{
  Font f = new Font("TimesRoman",Font.PLAIN,16);
  this.setSize(800,60);
  this.setFont(f);
  setLayout(new GridLayout(1,3,50,10));
   
   button1     = new Button("Random Sequence");
   button2     = new Button("User Specified");
   button3     = new Button("Array Sequence");
//   button4     = new Button("Compute");
//   button5     = new Button("Help");

   button1.setBackground(Color.green);
   button2.setBackground(Color.cyan);
   button3.setBackground(Color.magenta);
//   button4.setBackground(Color.red);
//   button5.setBackground(Color.blue.darker());
//   button5.setForeground(Color.white);

   add(button1);
   add(button2);
   add(button3);
//   add(button4);
//   add(button5);
}
}




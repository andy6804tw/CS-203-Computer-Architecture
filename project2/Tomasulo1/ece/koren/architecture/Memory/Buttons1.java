import java.awt.*;

public class Buttons1 extends Panel
{

Button     button4;
Button     button5;
public Buttons1()
{
  Font f = new Font("TimesRoman",Font.PLAIN,16);
  this.setSize(800,60);
  this.setFont(f);
  setLayout(new GridLayout(1,2,50,10));
   
   button4     = new Button("Compute");
   button5     = new Button("Help");

   button4.setBackground(Color.red);
   button5.setBackground(Color.blue.darker());
   button5.setForeground(Color.white);

   add(button4);
   add(button5);
}
}




import java.awt.*;
public class Out1 extends Panel
{

Label      Entrance1;
Label      Entrance2;

public Out1()
   {
   this.setSize(800,420);
    Font f = new Font("TimesRoman",Font.BOLD,24);
    this.setFont(f);

   setLayout(new GridLayout(2,1,10,10));
   
   Entrance1   = new Label("Welcome to Memory Interleaving Simulator",Label.CENTER);
   add(Entrance1);
   }
}




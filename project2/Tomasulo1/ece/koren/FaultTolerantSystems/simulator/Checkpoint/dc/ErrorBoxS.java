/*********************************************************************************************/
import java.awt.*;
public class ErrorBoxS extends Panel
{
String errDef;

public ErrorBoxS(String Def)
   {
  // this.setSize(900,300);
   errDef = Def;
   }

public void paint(Graphics g)
   {
   g.setColor(Color.red);
   g.setFont( new Font("SansSerif", Font.BOLD+ Font.BOLD, 16));
   g.drawString(" "+errDef,100,81);
   }
}




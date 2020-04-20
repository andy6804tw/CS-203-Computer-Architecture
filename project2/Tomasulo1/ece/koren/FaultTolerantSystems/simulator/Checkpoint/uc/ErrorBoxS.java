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
   g.setColor(Color.black);
   g.setFont( new Font("SansSerif", Font.BOLD+ Font.BOLD, 22));
   g.drawString("Input Error !!! "+errDef,200,81);
   }
}




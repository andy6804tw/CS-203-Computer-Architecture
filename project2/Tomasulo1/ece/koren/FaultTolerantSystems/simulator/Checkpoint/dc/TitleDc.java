import java.awt.*;
import java.awt.Font;

class TitleDc extends Canvas
{
//Color myBlue;
public TitleDc()
   {
            
   }
public void paint(Graphics g)
   {
 //  myBlue     = new Color(150,150,255);
   g.setFont(new Font("TimesRoman",Font.BOLD,20));
   g.setColor(Color.blue);
   g.drawString("DOMINO EFFECT IN A DISTRIBUTED CHECKPOINT SYSTEM",130,20);
   
   }
}

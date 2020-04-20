import java.awt.*;
import java.awt.Font;

class TitleC extends Canvas
{
//Color myBlue;
public TitleC()
   {
            
   }
public void paint(Graphics g)
   {
 //  myBlue     = new Color(150,150,255);
   g.setFont(new Font("TimesRoman",Font.BOLD,30));
   g.setColor(Color.blue);
   g.drawString("UNIPROCESSOR CHECKPOINTING FOR MULTIPLE INSTRUCTIONS",70,60);
   
   }
}

import java.awt.*;
public class ErrorBoxS extends Panel
{
String errdef;

public ErrorBoxS(String Def)
{
   this.setSize(800,210);
   errdef = Def;
}

public void paint(Graphics g)
{
   g.setFont( new Font("SansSerif", Font.BOLD+ Font.ITALIC, 16));
   g.drawString("Input Error: " + errdef,150,61);
}
}

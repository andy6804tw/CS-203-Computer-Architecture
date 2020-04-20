import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;

public class Introduction extends Canvas 
 
{
final int topX     = 15;
final int topY     = 15;
final int columnDist = 16;
private int maintainerX,maintainerY;
private String myStr;
private Graphics  screen;  
   
   
   
   public Introduction() 
      {
       maintainerX = topX;
       maintainerY = topY;
      }

 

    public void paint(Graphics g) 
       {
       screen  = g;
       myStr = "Fibonacci Numbers:" ;
       satirbas();
       
       myStr = "Fibonacci Numbers increase with a function : F[i] = F[i-1] + F[i-2]. Initial Values are Fib[0] = 0 and Fib[1] = 1.";
       satir();
       myStr = "As a result Fib[2] = Fib[1]+Fib[0] = 1 + 0 = 1.  Fib[3] can be found as 1+1=2 in a similar manner.  This animation";
       satir();
       myStr ="finds first  9 Fibonacci values using two processors.  In the animation,  time is also supposed to be equal to ";
       satir();
       myStr = "Fibonacci level";
       satir();
       
       myStr = "Domino Effect Animation:";
       satirbas();
       
       myStr = "In our example, different processors have different checkpointing times.  As we can see from animation, rolling";
       satir();       
       myStr = "back will not be helpful to us.  Reason is rolling back will yield orphaned messages, which has been shown in ";       
       satir();
       myStr = "black color in the animation";
       satir();
       
       myStr = "Running the animation";
       satirbas();
       
       myStr = "User can decide the speed of animation by entering a value between 100 to 5. Each value will be multiplied by 60 ";
       satir();
       myStr = "for deciding the sleep time in Java. Additional delays will be applied after encountering error and & finding Fib[9]";  
       satir();
       myStr = " in each step. Speed between 100 and 70 is recommended.";  
       satir();

       myStr = "IMPORTANT NOTICE ! ! !";  
       maintainerX += 300;
       satirbas();
       
       myStr = "Browsers will not save screen images. Instead of saving it in the memory, they prefer redrawing the image";  
       maintainerX -= 300;
       satir();       
       
       myStr = "If you move screen, it may take time for the browser to redraw the screen ";  
       satir();       
      
       myStr = "If you want to reload the program before it ends running, click browsers 'Back' button first ";  
       satir();   
      
   
       maintainerY = 15;
       }
       
       public void satirbas()
       {
       maintainerY += (columnDist+3);
       screen.setColor(Color.red);
       screen.setFont(new Font("Arial",Font.BOLD,16));
       screen.drawString(myStr,maintainerX,maintainerY);
       maintainerY += 4;
       screen.setColor(Color.black);
       screen.setFont(new Font("Arial",Font.BOLD,13));
       }
       
       public void satir()
       {
       maintainerY += columnDist;
       screen.drawString(myStr,maintainerX,maintainerY);
       }
       
 
    
}

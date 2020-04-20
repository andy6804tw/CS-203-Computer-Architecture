import java.awt.*;
public class MyButtonextends Panel
{
//BorderLayout b;
Button     ResultButton;
Label      L1;
Label      L2;

public MyButton()
   {

//   setLayout(b);   
   setLayout(new GridLayout(1,3,10,10));
   this.setSize(1200,100);
   
   ResultButton  = new Button(" F I N D    R E S U L T S "); 
   L1 =new Label(" ");
   L2 =new Label(" ");
  
   add(L1);
   add(ResultButton);
   add(L2);
   }
}
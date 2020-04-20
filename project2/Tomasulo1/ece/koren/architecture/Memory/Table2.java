import java.awt.*;

class Table2 extends Panel
{
TextField word[];
public Table2( int  NumOfBanks, int Size)
   {
    word = new TextField[Size];
    Font f = new Font("TimesRoman",Font.PLAIN,16);

    this.setSize(800,300);
    this.setFont(f);
       
    for(int i=0;i<NumOfBanks;i++)
    {
       word[i]  =   new TextField ("Bank " +i );
       word[i].setBackground(Color.black); 
       word[i].setForeground(Color.white);
       word[i].setEditable(false);
       add(word[i]);
    }
    for(int i=NumOfBanks;i<Size;i++)
    {
       word[i]  =   new TextField (" ",20);
       word[i].setEditable(false);
       word[i].setBackground(Color.pink);
       word[i].setForeground(Color.black);
       add(word[i]);
    }

    setLayout(new GridLayout((int)(Size/NumOfBanks),NumOfBanks,1,3));  
   }
}

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;

public class DistCheck extends Canvas 
implements Runnable {

    private Thread runner;
     
    private Graphics g;
    final int level1     = 75;
    final int level2     = 275;
    final int level3     = 475;
    final int crackedFib = 5;
    final int newFibo    = 12;
   
    private int lineDist;
    private int procYDist;
     
    private int pW ;
    private int pH ;
    
    private int x;
     
    private int fibo1,fibo2;
    private int lastFib;
    private int counter,crackedcounter,myTime,myFibLevel,myInc;
    
    private int crackWidth, crackedX, crackedY,crackedfibo1,crackedfibo2;
    
    private boolean crackeddownroute;
    private boolean cracked;
    private boolean afterFailure;
    private boolean myFlag;
    
    private boolean oneProcessAfterFail;
     
    private int tempo; 
    
    public DistCheck(int fibLevel,int newInc,int pauseTime) 
       {
       myFibLevel = fibLevel;
       myInc      = newInc;
       myTime     = pauseTime*100;
       initializeNumbers();
       }

    public void start() {
        if (runner == null) 
            {
            runner = new Thread(this);
            runner.start();
            }
    }

    public void stop() {
        runner = null;
    }

    public void run() {
     repaint();
           
    }

    

    void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) { }
    }

    public void paint(Graphics screen) {
     g =screen; 
     
     screen.setFont(new Font("Arial",Font.BOLD,15));
     screen.setColor(Color.magenta);
     screen.drawString("Finding Fibonacci Numbers without fault ",250,level1-(2*pH)+18);
     screen.drawLine(1  ,level1-(2*pH)+10,150 , level1-(2*pH)+10 );  
     screen.drawLine(620,level1-(2*pH)+10,800 , level1-(2*pH)+10 );  
     
     initializeNumbers();
     drawProcessorRun(95,level1);   
     
         
     screen.setFont(new Font("Arial",Font.BOLD,15));
     screen.setColor(Color.magenta);
     screen.drawString("Finding Fibonacci Numbers with failure at time:  "+myFibLevel+" ",210,level2-(2*pH)+8);
     screen.drawLine(1,level2-(2*pH),150, level2-(2*pH) ); 
     screen.drawLine(620,level2-(2*pH),800, level2-(2*pH) );  


    screen.setFont(new Font("Arial",Font.BOLD,24));
 //   screen.drawString("~~~",155,level1-(2*pH)+18);
 //   screen.drawString("~~~",565,level1-(2*pH)+18);
 //   screen.drawString("~~~",155,level2-(2*pH)+8);
 //   screen.drawString("~~~",565,level2-(2*pH)+8);

    initializeNumbers();
   drawProcessorRun(95,level2);    
    myFlag = false;   
    while( (crackedfibo1 != 0) || (crackedfibo2 != 1) )  drawFromRollback( );
    initializeNumbers();
    }
 
 
 
 
 
 public void drawProcessorRun(int xval,int yval)
       {
       x = xval;
       int y = yval;
       // Initial Rectangle Filling 
       
       counter = 0;
       cracked = false;
       
       decideFont(10);
       g.drawString("Processor A",x - 70,y);
       g.drawString("Processor B",x - 70,y+procYDist);
        
       
       g.drawString("Fib["+counter+"]="+fibo1,x,y-(pH/2)-3);     
            
       decideFont(10);
       g.fillRect(x,y-(pH/2),pW,pH);
       counter++;
      
     
       decideFont(10);
       g.drawString("Fib["+counter+"]="+fibo2,x+((lineDist +pW)/2),y+procYDist+(pH/2)+10);
       
       decideFont(10);
       g.fillRect(x+((lineDist +pW)/2),y+procYDist-(pH/2),pW,pH); 
       counter++;
      
     
       x += pW;   
       x -= ((lineDist +pW)/2);
      
      
       // Continous addition
       while(counter <= lastFib)
            {
            if      (counter % 2 == 0) fibo1 = drawProcessor(y,false);
            else if (counter % 2 == 1) fibo2 = drawProcessor(y+procYDist,true);
            
            }//end of while
       
       }// end of method drawProcessorRun
 
  
  
    public void drawFromRollback()
       {
       int top1,top2;
       int halfxDistance = (lineDist+pW)/2; 
       int numberofProcLeft;
       
       int crackWidth = 1200 - crackedX;
       fibo1 = crackedfibo1;
       fibo2 = crackedfibo2;
       counter = crackedcounter;
       afterFailure = true;
       
       boolean isNextUp;
       
       if (crackeddownroute== false)
            {
            
            crackedY = level2;
            top1 =crackedY- (pH/2); 
            fibo2 += fibo1;
            
            
            pause(2*myTime);  
            g.setColor(Color.black);
            g.setFont(new Font("Arial",Font.BOLD,12));
            g.drawString("    Rollback ",crackedX-3*pW,top1-5);
            g.drawLine(crackedX+2*pW  ,top1  ,crackedX-3*pW,top1);
            g.drawLine(crackedX-3*pW+4,top1-3,crackedX-3*pW,top1);
            g.drawLine(crackedX-3*pW+4,top1+3,crackedX-3*pW,top1);
            pause(2*myTime);  
            
            
            g.setColor(Color.magenta);
            g.clearRect(crackedX+1,top1,crackWidth,procYDist+pH);
            g.clearRect(crackedX+pW+3,top1-(pH/2),crackWidth,(pH/2));//Modified
            g.clearRect(crackedX+(3*pW),top1+procYDist+pH,crackWidth,(pH/2));        
            g.clearRect(crackedX-halfxDistance+1,crackedY-1,halfxDistance,2);
            g.setColor(Color.magenta);
            g.clearRect(crackedX+pW+8,crackedY+procYDist+(pH/2),crackWidth,(pH/2));//Modified
            g.clearRect(crackedX-pW,level2-(pH/2),pW,procYDist);
           
            g.clearRect(crackedX+lineDist-halfxDistance,top1-39,lineDist+pW,25);
            g.clearRect(crackedX-pW,top1+pH,lineDist,procYDist-pH);
            
            
            g.setColor(Color.black);
            g.setFont(new Font("Arial",Font.BOLD,12));
            g.drawString("    Rollback ",crackedX-3*pW,top1-5);
            g.drawLine(crackedX+2*pW  ,top1  ,crackedX-3*pW,top1);
            g.drawLine(crackedX-3*pW+4,top1-3,crackedX-3*pW,top1);
            g.drawLine(crackedX-3*pW+4,top1+3,crackedX-3*pW,top1);
            
            
            pause(myTime);
            g.drawLine (crackedX-halfxDistance-(pW/2),crackedY+(pH/2),crackedX-((halfxDistance+pW)/2),top1+procYDist);
            g.drawLine (crackedX-((halfxDistance+pW)/2)-5,top1+procYDist,crackedX-((halfxDistance+pW)/2),top1+procYDist);
            g.drawLine (crackedX-((halfxDistance+pW)/2)+2,top1+procYDist-5,crackedX-((halfxDistance+pW)/2),top1+procYDist);
            g.drawString(" "+fibo1,crackedX-halfxDistance-5,top1+procYDist-10);
            
            pause(myTime);
            g.fillRect(crackedX-pW,top1+procYDist,pW,pH);
            g.drawString("Fib["+(counter-1)+"]=Fib["+(counter-1)+"]+"+fibo1+" = "+fibo2,crackedX-pW,level2+procYDist+(pH/2)+26);
            
                     
           
          //  g.drawString(" counter,crackedX-3*pW+4,top1-23);
            
            x = crackedX-(lineDist+pW);
            isNextUp = false;
            }
       else 
           {
           crackedY = level2+procYDist;
           top1 = crackedY -procYDist -pH/2 ;
           fibo1 += fibo2;

                              
           pause(2*myTime);
           g.setColor(Color.black);
           g.setFont(new Font("Arial",Font.BOLD,12));
           g.drawString("   Rollback ",crackedX-3*pW,crackedY+(pH/2)+12);
           g.drawLine(crackedX+2*pW,crackedY+(pH/2),crackedX-3*pW,crackedY+(pH/2));
           g.drawLine(crackedX-3*pW+4,crackedY+(pH/2)-3,crackedX-3*pW,crackedY+(pH/2));
           g.drawLine(crackedX-3*pW+4,crackedY+(pH/2)+3,crackedX-3*pW,crackedY+(pH/2));
           pause(2*myTime);
           
           
           g.setColor(Color.magenta);
           g.clearRect(crackedX+(3*pW),top1-(pH/2),crackWidth,pH/2);
           g.clearRect(crackedX+1,top1,crackWidth,procYDist+pH);
           g.clearRect(crackedX+pW+3,crackedY+(pH/2),crackWidth,(pH/2)); // Modified
           g.clearRect(crackedX-halfxDistance+1,crackedY-1,halfxDistance,2);
           
           g.setColor(Color.magenta);
           g.clearRect(crackedX-pW,top1+pH,lineDist,procYDist-pH);
           g.clearRect(crackedX+pW+8,top1-(pH/2),crackWidth,(pH/2));//Modified
           g.clearRect(crackedX+lineDist-halfxDistance,level2+procYDist+(pH/2)+12,lineDist+pW,25);
           g.clearRect(crackedX-pW,level2+(pH/2),pW,procYDist-(pH/2));
           
           
          
           g.setColor(Color.black);
           g.setFont(new Font("Arial",Font.BOLD,12));
           g.drawString("   Rollback ",crackedX-3*pW,crackedY+(pH/2)+12);
           g.drawLine(crackedX+2*pW,crackedY+(pH/2),crackedX-3*pW,crackedY+(pH/2));
           g.drawLine(crackedX-3*pW+4,crackedY+(pH/2)-3,crackedX-3*pW,crackedY+(pH/2));
           g.drawLine(crackedX-3*pW+4,crackedY+(pH/2)+3,crackedX-3*pW,crackedY+(pH/2));
           
                     
           pause(myTime);
           g.drawLine(crackedX-halfxDistance-(pW/2),crackedY-(pH/2),crackedX-((halfxDistance+pW)/2),top1+pH);
           g.drawLine(crackedX-((halfxDistance+pW)/2)-5,top1+pH    ,crackedX-((halfxDistance+pW)/2),top1+pH);
           g.drawLine(crackedX-((halfxDistance+pW)/2)+1,top1+pH+4  ,crackedX-((halfxDistance+pW)/2),top1+pH);
           g.drawString(" "+fibo2,crackedX-halfxDistance-10,top1+procYDist-20);
           
           pause(myTime);
           g.fillRect(crackedX-pW,top1,pW,pH);
           g.drawString("Fib["+(counter-1)+"]=Fib["+(counter-1)+"]+"+fibo2+" = "+fibo1,crackedX-pW,top1-19);
          
        
           
           x = crackedX-(lineDist+pW);
           
           isNextUp = true;        
           }
       
       //System.out.print("\n " + counter);
       numberofProcLeft = lastFib - counter+1; 
   //    g.drawString(" "+crackedcounter,crackedX-3*pW+4,top1-23);
       pause(myTime);
       
       while(numberofProcLeft > 0)
          //  g.drawString(" "+crackeddownroute,crackedX-3*pW+4,top1+250-(numberofProcLeft*30));
            {
         // pause(3000);
            if(isNextUp == true)
               {
               fibo2 = drawProcessor(level2+procYDist,true);
               isNextUp =false;
               }
            else
               {
               fibo1 = drawProcessor(level2,false);
               isNextUp =true;
               }  
            numberofProcLeft -= 1; 
            }     
       findConsequentCrackValues();
       }//end of method drawFromRollback
 
 
    public void findConsequentCrackValues()
        {
        if (crackeddownroute == false)
             {
             crackeddownroute = true;
             crackedfibo2 -= crackedfibo1;
             
             }
        else {
             crackeddownroute = false;
             crackedfibo1 -= crackedfibo2;
             }
        cracked = true;
        crackedX         -= ((lineDist +pW)/2); 
        crackedcounter--;   
        }// end of method findConsequentCrackValues
        
    public int drawProcessor(int yval,boolean downroute)
       {
       int y = yval;
       int fibo = fibo1+fibo2; 
      
            
       x += ((lineDist +pW)/2);
       y = yval;

       decideFont(15);
       if (downroute == true)
          {
          drawdown(y);
          g.drawString(" "+fibo2,x+lineDist-67,y-5);
          }
       if (downroute == false) 
          {
          drawup(y);
          g.drawString(" "+fibo1,x+lineDist-67,y-2);
          }
       
       g.drawLine(x,y,x+lineDist-25,y);
       g.drawLine(x+lineDist-35,y-10,x+lineDist-25,y);
       g.drawLine(x+lineDist-35,y+10,x+lineDist-25,y);
             
             
      // System.out.print("\nCounter"+counter+"y value"+y);
       if ( (counter == myFibLevel) &&  (afterFailure == false))
          {
          if((y == level2) || y==(level2+procYDist))
              {
              cracked = true;
              fibo += myInc;
          
              crackedX         = x+((lineDist +pW)/2);
                
              crackeddownroute = downroute;
              crackedfibo1     = fibo1;
              crackedfibo2     = fibo2;
              crackedcounter   = counter;
              oneProcessAfterFail = true;
              }
          }
      
       decideFont(10);
       g.fillRect(x+lineDist,y-(pH/2),pW,pH);
      
       decideFont(10);
       if (downroute == false) 
           g.drawString("Fib["+counter+"]="+fibo,x+lineDist,y-(pH/2)-3);
       if (downroute == true) 
           g.drawString("Fib["+counter+"]="+fibo,x+lineDist,y+(pH/2)+10);
       
          
       g.drawOval(x+lineDist-20,y-10,20,20);
       g.setFont(new Font("Arial",Font.BOLD,20)); 
       g.drawString("+",x+lineDist-15,y+5);
       
       
       pause(150); 
       if(oneProcessAfterFail == true)
           {
           pause(myTime*3);
           oneProcessAfterFail = false;
           }
       if(counter == lastFib && myFlag == false) pause(3*myTime);
       counter++;
       return fibo;
       }



 public void drawup(int yval)
       {
       int y =yval;
      
       g.drawLine(x+(lineDist+pW)/2,y+procYDist,x+lineDist-19,y+13);
       g.drawLine(x+lineDist-15,y+22,x+lineDist-19,y+13);
       g.drawLine(x+lineDist-29,y+13,x+lineDist-19,y+13);
       g.drawString(" "+fibo2,x+lineDist-32,y+40);
       }   
 
 public void drawdown(int yval)
       {
       int y =yval;
       
       g.drawLine(x+(lineDist+pW)/2,y-procYDist,x+lineDist-19,y-13);
       g.drawLine(x+lineDist-16,y-23,x+lineDist-19,y-13);
       g.drawLine(x+lineDist-29,y-13,x+lineDist-19,y-13);
       g.drawString(" "+fibo1,x+lineDist-34,y-38);
       }
       
       

 
  public void decideFont(int decision)
      {
      if(cracked == true && decision == 10)
         {
         g.setColor(Color.black);
         g.setFont(new Font("Arial",Font.BOLD,10));
         }  //end if   
      if(cracked == true && decision == 15)
         {
         g.setColor(Color.red);
         g.setFont(new Font("Arial",Font.BOLD,15));
         
         }  //end if 
      
      if(cracked == false && decision == 10)
         {
         g.setColor(Color.blue);
         g.setFont(new Font("Arial",Font.BOLD,10));
         }  //end if   
      if(cracked == false && decision == 15)
         {
         g.setColor(Color.red);
         g.setFont(new Font("Arial",Font.BOLD,15));
         }  //end if 
    
      } //end of method decideFont
      
  public void initializeNumbers()
      {
      afterFailure = false;
      lineDist = 120;
      procYDist = 80;
      pW = 16;
      pH = 32;
      fibo1 = 0;fibo2 = 1;
      lastFib =10;
      myFlag = true;
      oneProcessAfterFail= false;
      }   
      
      
      
    
}

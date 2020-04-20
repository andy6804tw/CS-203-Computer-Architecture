package rsa;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.security.*;
import java.math.*;

public class RSA extends Applet {
  private boolean isStandalone = false;
  Panel panel1 = new Panel();
  String check = "";
  Vector errorIndex = null;
  boolean error = false;

  Vector channel = null;

  BorderLayout borderLayout1 = new BorderLayout();
  Panel panel2 = new Panel();
//  Label label2 = new Label();
  CheckboxGroup checkboxGroup1 = new CheckboxGroup();
  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public RSA() {
  }
  //Initialize the applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    panel1.setLayout(borderLayout2);

    panel1.setSize(800, 600);

//    label2.setFont(new java.awt.Font("Monospaced", 1, 18));
//    label2.setForeground(Color.black);
//    label2.setLocale(java.util.Locale.getDefault());
//    label2.setText("Encryption Based Error Detection Applet");
//    label2.setVisible(true);
    
    
    panel1.setLocale(java.util.Locale.getDefault());


    panel2.setLayout(gridBagLayout2);
    this.setFont(new java.awt.Font("Dialog", 0, 10));
    textArea1.setColumns(10);
    textArea1.setEnabled(true);
    textArea1.setFont(new java.awt.Font("Dialog", 0, 14));
    textArea1.setEditable(true);
    textArea1.setForeground(Color.red);
    textArea1.setLocale(java.util.Locale.getDefault());
    textArea1.setRows(1);
    label9.setFont(new java.awt.Font("Dialog", 0, 16));
    label9.setText("Decrypted text");
    button3.setForeground(Color.black);
    button3.setLabel("Decrypt");
    button3.setEnabled(false);
    button3.setFont(new java.awt.Font("Dialog", 0, 16));
    button3.setLocale(java.util.Locale.getDefault());
    button3.addActionListener(new RSA_button3_actionAdapter(this));
    button3.addActionListener(new RSA_button3_actionAdapter(this));
    
     label33.setFont(new java.awt.Font("Dialog", 0, 16));
    label33.setText("Status Window");
    
    label7.setEnabled(true);
    label7.setFont(new java.awt.Font("Dialog", 2, 16));
    label7.setLocale(java.util.Locale.getDefault());
    label7.setText("n =");
    label7.setVisible(true);
    
      label28.setEnabled(true);
    label28.setFont(new java.awt.Font("Dialog", 2, 16));
    label28.setLocale(java.util.Locale.getDefault());
    label28.setText("e =");
    label28.setVisible(true);
    
      label29.setEnabled(true);
    label29.setFont(new java.awt.Font("Dialog", 2, 16));
    label29.setLocale(java.util.Locale.getDefault());
    label29.setText("d =");
    label29.setVisible(true);
    
     label30.setEnabled(true);
    label30.setFont(new java.awt.Font("Dialog", 4, 16));
    label30.setLocale(java.util.Locale.getDefault());
    label30.setText("Modulus ");
    label30.setVisible(true);
 
    
    button2.setLabel("Encrypt");
    button2.addActionListener(new RSA_button2_actionAdapter(this));
    button2.setEnabled(false);
    button2.setFont(new java.awt.Font("Dialog", 0, 16));
    button2.addActionListener(new RSA_button2_actionAdapter(this));
    textArea2.setColumns(10);
    textArea2.setEditable(false);
    textArea2.setEnabled(true);
    textArea2.setFont(new java.awt.Font("Dialog", 0, 14));
    textArea2.setRows(3);
    textArea2.setSelectionStart(9);
    textArea3.setColumns(10);
    textArea3.setEditable(false);
    textArea3.setFont(new java.awt.Font("Dialog", 0, 14));
    textArea3.setRows(3);
    
        textArea4.setColumns(80);
    textArea4.setEditable(false);
    textArea4.setFont(new java.awt.Font("Dialog", 0, 14));
    textArea4.setRows(5);
    textArea4.setForeground(Color.red);
    textArea4.setBackground(Color.black);
    
    label5.setFont(new java.awt.Font("Dialog", 0, 16));
    label5.setForeground(Color.black);
    label5.setText("Plain text");
    button1.setFont(new java.awt.Font("Dialog", 0, 14));
    button1.setForeground(Color.black);
     button1.setBackground(Color.red);
    button1.setLabel("Calculate N and e");
    button1.setLocale(java.util.Locale.getDefault());
    button1.addActionListener(new RSA_button1_actionAdapter(this));
    button1.addActionListener(new RSA_button1_actionAdapter(this));
    button1.addActionListener(new RSA_button1_actionAdapter(this));
    
    button4.setFont(new java.awt.Font("Dialog", 0, 14));
    button4.setForeground(Color.black);
    button4.setLabel("Calculate d`s");
    button4.setEnabled(false);
    button4.setLocale(java.util.Locale.getDefault());
    button4.addActionListener(new RSA_button4_actionAdapter(this));
    button4.addActionListener(new RSA_button4_actionAdapter(this));
    button4.addActionListener(new RSA_button4_actionAdapter(this));
    
    
    label1.setFont(new java.awt.Font("Dialog", 0, 16));
    label1.setLocale(java.util.Locale.getDefault());
    label1.setText("2nd Prime (q)");
    panel7.setLayout(gridBagLayout1);
    textField1.setColumns(5);
    textField1.setEnabled(true);
    textField1.setFont(new java.awt.Font("Dialog", 0, 14));
    textField1.setSelectionStart(10);
    textField1.setText("");
    textField1.setBackground(Color.green);
    
    label4.setFont(new java.awt.Font("Dialog", 0, 16));
    label4.setForeground(Color.black);
    label4.setText("1st Prime (p)");
    label13.setLocale(java.util.Locale.getDefault());
    label13.setText("");
    panel3.setLayout(gridBagLayout3);
    label3.setText("    ");
  
    label14.setFont(new java.awt.Font("Dialog", 0, 16));
    label14.setText("Encrypted Text");
 
    
        
    label16.setFont(new java.awt.Font("Dialog", 0, 16));
    label16.setText("Receiver");
    
 
  
      textField5.setBackground(Color.white);  // TEXTFIELDLER e-5 , d-6 , n-3
    textField5.setColumns(6);
    textField5.setEditable(false);
    textField5.setEnabled(true);
    textField5.setFont(new java.awt.Font("Dialog", 0, 16));
    textField5.setText(null);
    
      textField6.setBackground(Color.white);
    textField6.setColumns(6);
    textField6.setEditable(false);
    textField6.setEnabled(true);
    textField6.setFont(new java.awt.Font("Dialog", 0, 16));
    textField6.setText(null);
    
    textField7.setBackground(Color.white);
    textField7.setColumns(2);
    textField7.setEditable(true);
    textField7.setEnabled(true);
    textField7.setFont(new java.awt.Font("Dialog", 0, 16));
    textField7.setText("");
    
    
    
    panel6.setLayout(gridBagLayout4);
    label21.setFont(new java.awt.Font("Dialog", 0, 16));
    label21.setLocale(java.util.Locale.getDefault());
    label21.setText(" ");
    label22.setFont(new java.awt.Font("Dialog", 0, 16));
    label22.setLocale(java.util.Locale.getDefault());
    label22.setText("          ");
    
    textField2.setBackground(Color.green);
    textField2.setColumns(5);
    textField2.setEditable(true);
    textField2.setFont(new java.awt.Font("Dialog", 0, 14));
    textField2.setText("");
    
     textField3.setBackground(Color.lightGray);
    textField3.setColumns(6);
    textField3.setEditable(false);
    textField3.setText("");
    textField3.setVisible(true);
    textField3.setFont(new java.awt.Font("Dialog", 0, 14));
    
    
  //  this.add(panel2, BorderLayout.NORTH);

    this.add(panel1, BorderLayout.WEST);
    
     panel7.add(textField1,        new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0)); // inset Bot-LEF-Rig-Top
    
    panel7.add(textField2,  new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  
    panel7.add(textField3,   new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    
    panel7.add(label7,         new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    
    panel7.add(textField5,  new GridBagConstraints(0,7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    
    panel7.add(label28,         new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        
    panel7.add(label29,         new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    
        panel7.add(textField6,  new GridBagConstraints(0,8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
     panel7.add(textField7,  new GridBagConstraints(0,9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
       panel7.add(label30,         new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,  0, 0, 0), 0, 0));
       
       
    
    
    panel3.add(label3,                                                                                  new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    
  
    
 
   
   
   
   
    
      panel2.add(label33,                 new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      
    
 
       
    panel2.add(textArea1,                           new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(-100, 0, 0, 0), 30,  40));
    panel2.add(label5,    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                   ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(-20, 0, 0, 20), 0, 0));
       panel2.add(button2,               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(-120, 0, 0, 30), 0, 0));
    panel2.add(textArea2,                 new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(-100, -450,0, 0), 30, 0));
    
    panel2.add(label14,           new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(-20, -760, 0, 30), 0, 0));
    
    panel2.add(textArea3,                            new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(-100, -180, 0, 0), 30, 0));
     panel2.add(label9,                 new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(-20, -180, 0, 330), 0, 0));
      panel2.add(button3,                   new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(-120, -180, 0, 40), 0, 0));
   
     
    panel2.add(textArea4,        new GridBagConstraints(0,0, 1, 1, 1.0, 1.0
              ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(30, 0, 0, 70), 0, 0));
     
    
 
    
    panel1.add(panel7, BorderLayout.NORTH);
    panel7.add(label13,                                    new GridBagConstraints(2, 4, 1, 2, 0.0, 0.0
            ,GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
   
   
    panel7.add(button1,        new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 1, 2));
  
       panel7.add(button4,        new GridBagConstraints(0, 2, 1, 4, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(-2, 0, 2, 0), 1, 2));
       
       
    panel7.add(label4,   new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 5, 0));
    panel7.add(label1,   new GridBagConstraints(1, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 14, 0, 0), 3, 0));
    
   

     
    panel7.add(label18,  new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    
     panel1.add(panel3, BorderLayout.SOUTH);

                 panel1.add(panel2, BorderLayout.CENTER);
                
  
    
    
  }
  //Start the applet
  public void start() {
  }
  //Stop the applet
  public void stop() {
  }
  //Destroy the applet
  public void destroy() {
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }
  //Main method
  public static void main(String[] args) {
  
   
    RSA applet = new RSA();
    applet.isStandalone = true;
    Frame frame;
    frame = new Frame();
    frame.setTitle("Applet Frame");
    frame.add(applet, BorderLayout.CENTER);
    applet.init();
    applet.start();
    frame.setSize(400,320);
  
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    frame.setVisible(true);
  }


  long fast_expo(int a, int p, int n)
   {
         long temp;
         overflow=0;
         if (p==0)
                 return 1;
         if((p % 2) == 0)
         {
                 temp = fast_expo(a, p/2, n);
                 if(temp >= 2147483647 ) 
                 overflow=1;
                 return (((temp % n)*temp) % n);
         }
         else
         {
                 temp = fast_expo(a, (p-1)/2, n);
                 if(temp >= 2147483647 )
             overflow=1;
             
                 return  (  a *    (((temp%n)*temp) % n)     ) % n;
         }
   }



   boolean miller_rabin(int n, int s)
   {
           int i=0;
           int flag=0;
           int a=2;

           Random r = new Random();

           for(i = 0 ; i < s ; i++)
           {
             a=r.nextInt(n-3) + 2;

             if(fast_expo(a, n-1, n)!=1)
             {
               return false;  // n is a composite.
             }
           }
           return true;
   }



   int prime_gen(int lower_limit, int upper_limit)
   {

           int trial;

           if( lower_limit < 0 || upper_limit < lower_limit)
           {
                   System.exit(-1);
           }

           trial = lower_limit;

           while( trial <= upper_limit)
           {

                   if(miller_rabin(trial, 1000))
                           return trial;
                   else
                           trial++;
           }
           return 0;
   }


   long gcd(long a, long b)
   {
           if(b==0)
                   return a;
           else
                   return gcd(b, a % b);
   }

   long k = 0;
   long l = 0;

   int n=0;
   int e=0;
   int d=0;
 int resencrypt,resdecrypt,resmoduli;

  String possiblee;
  int[] edizi = new int[6];
long[] ddizi = new long[6];
  // Long ii = null;
  // Long jj = null;
 /*
   long extended_gcd(long a, long b, Long i, Long j)
   {
         long r;
         Long k = i;
         Long l = j;

         long d;

         if(b==0)
         {
           System.out.println("a=" + a);
           i=new Long(1);
           j=new Long(0);
           System.out.println("i=" + i.longValue() + " j=" + j.longValue());
           return a;
         }
         else
         {
                 r = a/b;
                 d = extended_gcd(b, a%b, k, l);
                 i = l;
                 j = new Long( k.longValue() - (l.longValue()*r) );
                 return d;
         }
 }

 */


 long extended_gcd(long a, long b, long [] aa)
   {
         long r;
         long [] bb = new long[2];
         bb[0] = 0;
         bb[1] = 0;

         long d =0;

         if(b==0)
         {
         //  System.out.println("a=" + a);
           aa[0] = 1;
           aa[1] = 0;
       //    System.out.println("i=" + i.longValue() + " j=" + j.longValue());
           return a;
         }
         else
         {
                 r = a/b;
                 d = extended_gcd(b, a%b, bb);
                 aa[0] = bb[1];
                 aa[1] = bb[0] - ( bb[1]*r );
                 return d;
         }
 }



  void button1_actionPerformed(ActionEvent ee) {


    Calendar rightNow = Calendar.getInstance();
    int sec = rightNow.get(Calendar.SECOND);

   // System.out.println(sec);

    //Get other parameters
    
           String isprime = "Both numbers are prime - OK";
           String tempe = "";
           int primeok=1;
            int counte=0;
           int size=8;
           int advp, advq;
           Random random = new Random(sec);

           int start_point = 128;

           int upper_limit = 255;

           int range = 56;

           int random_offset = random.nextInt(56);

           int lower_limit = start_point + random_offset;

    //       System.out.println("The upper limit used in this round is " + upper_limit + " and the lower limit is " + lower_limit);

           int p;

           int q;

//           p = prime_gen(lower_limit, upper_limit);
//
//           if(p==0)
//           {
//             System.out.println("Error: p == 0" );
//             System.exit(-1);
//           }
//
//
//           //get the second prime
//
//           do
//           {
//                   random_offset = random.nextInt( range );
//
//                   lower_limit   = start_point + random_offset;
//
//                   q = prime_gen(lower_limit, upper_limit);
//
//           }while(q==p);


    //       System.out.println("p is " + p + " q is " + q );

    long z;

int chk1,chk2;
chk1=0;
    textField5.setEditable(false);

           String prime1 = textField1.getText().trim();

     p = Integer.valueOf(textField1.getText().trim()).intValue();
       String prime2 = textField2.getText().trim();




     q = Integer.valueOf(textField2.getText().trim()).intValue();
             n = p*q;
if(p > 30000 || q > 30000)
  isprime = "WARNING: Either p or q is \n larger than 30,000 \nbypassing prime number checking";
else
             
             { advp=prime_gen(p,p*2);
               advq=prime_gen(q,q*2);
               if(!miller_rabin(q,10000) && !miller_rabin(p,1000))
               {primeok=0;
                 isprime= "WARNING: Neither p nor q is prime, try " +advp + " and "+ advq+ " instead" ;   }   
  else if(!miller_rabin(p,10000))
  {primeok=0;
    isprime = "WARNING: p is not a prime number, try " + advp + " instead" ; }
  
 else if(!miller_rabin(q,10000))
 {
   isprime = "WARNING: q is not a prime number  try " + advq + " instead"; 
 primeok=0;}
}
  textArea4.setText(isprime);
           textField3.setText(String.valueOf(n));
     
     //       textField1.setText(String.valueOf(p));
       //     textField2.setText(String.valueOf(q));
           //sin
           //printf("n is %ld\n", n);

           z = (p-1) * (q-1); ///n


           int test;
       //  for(test = (int)Math.sqrt(new Double(z).doubleValue()); test < z; test++)

           for(test =(int)Math.sqrt(new Double(z).doubleValue()); test < z; test++)
           {
                   if(gcd(test, z) == 1 )
                   {
                           //printf("e is %u", test);
                           edizi[counte]=test;
                           counte++;
                           if (counte>5)
                           {break;}
                   }
           }

       if (primeok==1)  
       {
 for (int junk=0; junk <5; junk++)
{ 
  tempe = edizi[junk] + " , "  + tempe   ;
} 
          possiblee=  isprime +  ". Totient is: " + z + "\nSome Possible Values for e: "+ String.valueOf(tempe)+"\n" ;
          textArea4.setText(possiblee); //e burada
      
     textField5.setEditable(true);
      
                 
          button4.setEnabled(true);
       
            textField5.setBackground(Color.orange);
           textField6.setBackground(Color.lightGray);
           textField6.setEditable(false);
            textField7.setBackground(Color.lightGray);
            textArea1.setBackground(Color.lightGray);
            button3.setEnabled(false);
            button1.setBackground(Color.lightGray);
             button2.setBackground(Color.lightGray);
              button3.setBackground(Color.lightGray);
             button4.setBackground(Color.red);
   
      
       }  

}
  
    void button4_actionPerformed(ActionEvent ee)
    {  
      int  p = Integer.valueOf(textField1.getText().trim()).intValue();
          int q = Integer.valueOf(textField2.getText().trim()).intValue();
      long  z = (p-1)*(q-1);
         int decided_e = Integer.valueOf(textField5.getText().trim()).intValue();
           e=decided_e;
         
    if( (gcd(e, z) == 1 )  )
            {
      int countd=0;
         long d =0;
         long sum;
           String doverflow= " ";
         String tempd=" ";   
           long [] aa = new long[2];
           aa[0] = 0;
           aa[1] = 0;
    for (int junk=0; junk <5; junk++)
    {ddizi[junk] = 0; }
    
           
                   for(int i=0; i < 2165536; i++)
                   {
 
                          
                     if(((i*e)-1) % z == 0)
                       {
                               ddizi[countd]=i;  
                              countd++; 
                              
                              if(countd>4) 
                                                           {break;}
                       }         
                           
                   //} 
          
           
           
           
}
      d=ddizi[0];
for (int junk=0; junk <5; junk++)
{ 
  if(ddizi[junk]!=0)
  tempd = ddizi[junk] + " , "  + tempd   ;
  else if (ddizi[junk]==0)
  { doverflow = " D overflow" ; }
} 
 
    textArea4.setText(possiblee + "\nSome Possible Values for d: \n"+ tempd + doverflow); 
     button2.setEnabled(true);
           textArea1.setEditable(true);
           textField6.setEditable(true);
          textField5.setBackground(Color.lightGray);
          textField6.setBackground(Color.orange);
            textField7.setBackground(Color.orange);
            textArea1.setBackground(Color.orange);
             button1.setBackground(Color.lightGray);
             button4.setBackground(Color.lightGray);
                button3.setBackground(Color.lightGray);
               button2.setBackground(Color.red);
    }
          
    else 
    { 
      
            textArea4.setText(possiblee + "Enter valid e before proceed");
          }
}
 int overflow;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  Panel panel3 = new Panel();
  TextArea textArea1 = new TextArea();
  Label label9 = new Label();
  Button button3 = new Button();
  Button button4 = new Button();
  Label label7 = new Label();
  Checkbox checkbox4 = new Checkbox();
  Checkbox checkbox3 = new Checkbox();
  Button button2 = new Button();
  TextArea textArea2 = new TextArea();
  TextArea textArea3 = new TextArea();
  TextArea textArea4 = new TextArea();
  TextArea textArea5 = new TextArea();
  Label label5 = new Label();
  Panel panel6 = new Panel();
  Panel panel7 = new Panel();
    Panel panel8 = new Panel();
  Button button1 = new Button();
  Label label1 = new Label();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TextField textField1 = new TextField();
  Label label10 = new Label();
  Label label4 = new Label();
  Label label13 = new Label();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  Label label3 = new Label();
  Label label8 = new Label();
  Label label14 = new Label();
  Label label15 = new Label();
  Label label11 = new Label();
  Label label12 = new Label();
    Label label33 = new Label();
  Label label16 = new Label();
  Label label28 = new Label();
  Label label29 = new Label();
  Label label30 = new Label();
  //TextField textField4 = new TextField();
  TextField textField5 = new TextField();
  TextField textField6 = new TextField();
  TextField textField7 = new TextField();
  Label label17 = new Label();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  Label label21 = new Label();
  Label label22 = new Label();
  TextField textField2 = new TextField();
  TextField textField3 = new TextField();
  Label label18 = new Label();

  void button2_actionPerformed(ActionEvent ee) {
    
  int sigValue=0;
    channel = new Vector();

    if(errorIndex!=null)
      errorIndex.clear();
    error = false;

    String plaintext = textArea1.getText().trim();

   // if(checkbox3.getState())
      e = Integer.valueOf(textField5.getText().trim()).intValue();
   // else
   //   e = Integer.valueOf(textField2.getText().trim()).intValue();

    String encrypttext = "";
long encryptint;
   MessageDigest md = null;
   try
   {
     md = MessageDigest.getInstance("SHA");
//     rd = MessageDigest.getInstance("SHA");
   }catch(NoSuchAlgorithmException e){e.printStackTrace();};



    byte [] buf = new byte[2];
    channel.clear();

    byte [] hashValue = new byte[5];
    byte  [] toBeHashed = null;
    n = Integer.valueOf(textField3.getText().trim()).intValue();
int text=Integer.valueOf(textArea1.getText().trim()).intValue();
//   if(plaintext.length()>=1)
//    {
//
//      for(int i=0; i<plaintext.length(); i++)
//      {
//        char text = plaintext.charAt(i);
 
        long fixed_expo = fast_expo(text, e, n);
        long expo = fixed_expo;
        Long encryptValue = new Long(expo);


//    //hash the plain text, to be encrypted by the private key
//
//      String tmmp = String.valueOf(text);
//        toBeHashed = tmmp.getBytes();
//        md.update(toBeHashed);
//
////get the digest of the plain text
//
//        hashValue = md.digest();
//       byte   shortHash = hashValue[0];
//     sigValue = (new Byte(shortHash)).intValue();
//        System.out.println("~~~~~~The hash before encryption is: " + sigValue);
//        
//       
////encrypt the hash
//
//        int signature = fast_expo(sigValue, e, n);
//        Integer Signature = new Integer(signature);
//
// //       System.out.println("the length of the disgest is: " + hashValue.length);
//        System.out.println("*** ****  message digest is: " + (new String(hashValue)));
//
//
//        Packet p = new Packet();
//        p.eValue = encryptValue;
// //       p.hash = hashValue;
//        p.mm = signature;
//
//
//        channel.add(p);
//
//        buf[0] = new Long((expo << 24)>>24).byteValue();
//        expo = fixed_expo;
//        buf[1] = new Long((expo << 16) >> 24).byteValue();
//
//
//   //     System.out.println(fixed_expo);
//   //     System.out.println("buf[0]=" + buf[0] + " buf[1]=" + buf[1] );
//
//        String charString = Integer.toHexString(encryptValue.intValue());
//
//      if(charString.length()==4)
//        {
//        }
//        else if(charString.length()==3)
//          charString = "0" + charString;
//        else if(charString.length()==2)
//          charString = "00" + charString;
//        else if(charString.length()==1)
//          charString = "000" + charString;
//        else if(charString.length()==0)
//          charString = "0000";
//        else
//        {
//
//          System.out.println("System error.");
//          System.exit(-1);
//        }

        //encrypttext += charString;
        String encstring = Integer.toString(encryptValue.intValue());
  
          //encryptValue.intValue();

 //       encrypttext += new String(buf);
 //System.out.println("encrypttext=" + encrypttext);
      
        
        resmoduli = Integer.valueOf(textField7.getText().trim()).intValue();
      
 resencrypt = text % resmoduli;
 
System.out.println("*** **** Residue 1 : " +  resencrypt);
      textArea2.setText(encstring);

   // }

    button3.setEnabled(true);

    textArea2.setEditable(true);
    button1.setBackground(Color.lightGray);
             button4.setBackground(Color.lightGray);
               button2.setBackground(Color.lightGray);
                   button3.setBackground(Color.red);
  textArea2.setBackground(Color.orange);
/*
     int ii = new Integer("27").intValue();
     System.out.println(ii);

     buf = encrypttext.getBytes();
     System.out.println(buf[0]);
     System.out.println(new String(buf));
*/
    

  }

  void button3_actionPerformed(ActionEvent ee) {
 textField6.setBackground(Color.lightGray);
            textField7.setBackground(Color.lightGray);
            textArea1.setBackground(Color.lightGray);
    int ddsig=0;
    String encryptText = textArea2.getText();
    String decryptText = "";
    Long eInteger = null;
    byte [] sign =  null;
    Packet rp = null;
    MessageDigest dd = null;
    long decryptint ;
    errorIndex = new Vector();
    error = false;
    int tempcalc=0;
    byte [] decryptbyte = new byte[1];
    decryptbyte[0] = 0;


   // if(checkbox3.getState())
      d = Integer.valueOf(textField6.getText().trim()).intValue();
   // else
     // d = Integer.valueOf(textField1.getText().trim()).intValue();


    n = Integer.valueOf(textField3.getText().trim()).intValue();
int encryptint = Integer.valueOf(textArea2.getText().trim()).intValue();

//for(int i=0; i<channel.size(); i++)
//{
//  rp = (Packet)channel.get(i);
//  eInteger = rp.eValue;
//  
//  int ssig = rp.mm;
//  
//  byte [] buf = encryptText.getBytes();
//  int pos = i*4;
//  
//  String encryptCharValue = null;
//  if((pos+4)<=encryptText.length())
//    encryptCharValue = encryptText.substring(pos, pos+4);
//  else
//    encryptCharValue = encryptText.substring(pos);
//  Integer encryptInt = null;
//  try
//  {
//    encryptInt = new Integer(Integer.parseInt(encryptCharValue, 16));
//  }catch(NumberFormatException ne){encryptInt = new Integer(155);};
//
//     int encryptint = encryptInt.intValue();
     System.out.println("from the text before: " + encryptint);

      decryptint = fast_expo(encryptint, d, n);
     System.out.println("from the text: " + decryptint);
   
     Long decryptInt = new Long(decryptint);
     decryptbyte[0] = decryptInt.byteValue();
     String decryptChar = new String(decryptbyte, 0, 1);
     decryptText += decryptChar;

   MessageDigest rd = null;
    try
    {
      rd = MessageDigest.getInstance("SHA");
  }catch(NoSuchAlgorithmException e){e.printStackTrace();};

    byte [] rdCheck= new byte[5];

   // byte toBeHashed = encryptInt.byteValue();

   byte toBeHashed = decryptbyte[0];
    rd.update(toBeHashed);

    rdCheck = rd.digest();
  //  System.out.println("*** ****  passed message digest is: " + (new String(sign)));
    System.out.println("*** ****  calculated message digest is: " + (new String(rdCheck)));

    byte shortCDig = rdCheck[0];

    int calcDig = (new Byte(shortCDig)).intValue();
    tempcalc=calcDig;
    System.out.println("the calcDig is: " + calcDig);
 //   BigInteger sigBig = new BigInteger(rdCheck);
 //  long sigValue = sigBig.longValue();
 //    System.out.println("~~~~~~The hash before encryption is: " + sigValue);



 //   for(int w=0; w<rdCheck.length; w++)
 //       System.out.println(w + ": " + rdCheck[w]);




//here start decryption
//decrypt the hash

    //    int shortssig = ssig;
    //ddsig = fast_expo(ssig, d, n);
     System.out.println("~~~~~ the decrypted value is: " + ddsig);
 
     byte [] decSig = new byte[5];
     decSig = BigInteger.valueOf(ddsig).toByteArray();



  //  if(!MessageDigest.isEqual(rdCheck, decSig))
//  if(calcDig != ddsig)
//    {
//       else
//       {
//         error = true;
//         errorIndex.add(new Integer(i));
//       }
//    }
 // }

     resmoduli = Integer.valueOf(textField7.getText().trim()).intValue();
       resdecrypt = decryptInt.intValue() % resmoduli;
     
      int text = Integer.valueOf(textArea1.getText().trim()).intValue();
      
 resencrypt = text % resmoduli; //ver3.2
 
       
    System.out.println("\n~~~~~ the decrypted RESIDUE " + resdecrypt);
    
  //  byte [] buf = encryptText.getBytes();

/*
    for(int i=0; i<encryptText.length(); i=i+4)
    {

      String encryptCharValue = null;
      if((i+4)<=encryptText.length())
        encryptCharValue = encryptText.substring(i, i+4);
      else
        encryptCharValue = encryptText.substring(i);

      Integer encryptInt = new Integer(Integer.parseInt(encryptCharValue,16));

      int encryptint = encryptInt.intValue();
      int decryptint = fast_expo(encryptint, d, n);
      System.out.println(decryptint);
      Integer decryptInt = new Integer(decryptint);
      decryptbyte[0] = decryptInt.byteValue();
      decryptText += new String(decryptbyte, 0, 1);
    }
*/

 //   System.out.println("The two message digests are: " + MessageDigest.isEqual(mdbuf,rdbuf));





    if(!error)
    {
      //textField4.setText("No error");
      System.out.println("No error");
      label21.setText("");
      label22.setText("");

    }
    else
    {
    //  textField4.setText("Error detected!");
 System.out.println("Error detected!");

        label21.setText("Total number of characters altered: " + errorIndex.size());
      String msg = "The altered characters are: #";
        for(int x=0; x<errorIndex.size(); x++)
        {
           msg += (((Integer)errorIndex.get(x)).toString()+ ", ");
        }
        label22.setText(msg);
    }
    String text4p = textArea4.getText();
    //textArea3.setText(decryptText);
   
 String decryptt = Integer.toString(decryptInt.intValue());
   textArea3.setText(decryptt); 
    
    if (resencrypt < 0 )
    resencrypt = resencrypt + resmoduli;
       if (resdecrypt < 0 )
    resdecrypt = resdecrypt + resmoduli;
    if(resencrypt == resdecrypt && overflow==0 ) 
    textArea4.setText("\n No Error Detected. \n Message Residue: "  + resencrypt+ " \n Decryption residue: " + resdecrypt);
    else if
      (overflow==1)  
      textArea4.setText("\n Overflow Detected. Either enter small prime numbers or small numbers as input ");
      else
       textArea4.setText("\n Error Detected. Residues are different \n Message Residue: "  + resencrypt+ " \n Decryption residue: " + resdecrypt );
  }

  void checkbox4_itemStateChanged(ItemEvent e) {

  }



}

class Packet
{
  Long eValue = null;
//  byte [] hash = null;
  int mm = 0;
}

class RSA_button3_actionAdapter implements java.awt.event.ActionListener {
  RSA adaptee;

  RSA_button3_actionAdapter(RSA adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.button3_actionPerformed(e);
  }
}

class RSA_button2_actionAdapter implements java.awt.event.ActionListener {
  RSA adaptee;

  RSA_button2_actionAdapter(RSA adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.button2_actionPerformed(e);
  }
}

class RSA_button1_actionAdapter implements java.awt.event.ActionListener {
  RSA adaptee;

  RSA_button1_actionAdapter(RSA adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.button1_actionPerformed(e);
  }
}


class RSA_button4_actionAdapter implements java.awt.event.ActionListener {
  RSA adaptee;

  RSA_button4_actionAdapter(RSA adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.button4_actionPerformed(e);
  }
}


class RSA_checkbox4_itemAdapter implements java.awt.event.ItemListener {
  RSA adaptee;

  RSA_checkbox4_itemAdapter(RSA adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.checkbox4_itemStateChanged(e);
  }
}

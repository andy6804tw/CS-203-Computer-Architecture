import java.awt.*;
public class Rates extends Panel
{
Label     EmptyCorner;
Label     Instruction1;
Label     Instruction2;
Label     Instruction3;
Label     Instruction4;
Label     Instruction5;
Label     Instruction6;
Label     Instruction7;
Label     Instruction8;
Label     Instruction9;
Label     Instruction10;

Label     FailureRate; 
TextField FR1;
TextField FR2;
TextField FR3;
TextField FR4;
TextField FR5;
TextField FR6;
TextField FR7;
TextField FR8;
TextField FR9;
TextField FR10;

Label     RecoverRate; 
TextField RR1;
TextField RR2;
TextField RR3;
TextField RR4;
TextField RR5;
TextField RR6;
TextField RR7;
TextField RR8;
TextField RR9;
TextField RR10;

Label     TimeValue; 
TextField TV1;
TextField TV2;
TextField TV3;
TextField TV4;
TextField TV5;
TextField TV6;
TextField TV7;
TextField TV8;
TextField TV9;
TextField TV10;


Label     Frequnce; 
TextField FV1;
TextField FV2;
TextField FV3;
TextField FV4;
TextField FV5;
TextField FV6;
TextField FV7;
TextField FV8;
TextField FV9;
TextField FV10;

public Rates()
   {
   setLayout(new GridLayout(5,11,10,10));
//   this.setSize(900,400);

   EmptyCorner      = new Label("  ",Label.RIGHT);
   Instruction1     = new Label("First Instruction",  Label.RIGHT);
   Instruction2     = new Label("Second Instruction", Label.RIGHT);
   Instruction3     = new Label("Third Instruction",  Label.RIGHT);
   Instruction4     = new Label("Fourth Instruction", Label.RIGHT);
   Instruction5     = new Label("Fifth Instruction",  Label.RIGHT);
   Instruction6     = new Label("Sixth Instruction",  Label.RIGHT);
   Instruction7     = new Label("Seventh Instruction",Label.RIGHT);
   Instruction8     = new Label("Eigth Instruction",  Label.RIGHT);
   Instruction9     = new Label("Ninth Instruction",  Label.RIGHT);
   Instruction10    = new Label("Tenth Instruction",  Label.RIGHT);


   FailureRate         = new Label("Failure Rates",Label.RIGHT);
   FR1                 = new TextField("0.2",10);
   FR2                 = new TextField("0.3",10);
   FR3                 = new TextField("0.5",10);
   FR4                 = new TextField("0.0",10);
   FR5                 = new TextField("0.0",10);
   FR6                 = new TextField("0.0",10);
   FR7                 = new TextField("0.0",10);
   FR8                 = new TextField("0.0",10);
   FR9                 = new TextField("0.0",10);
   FR10                = new TextField("0.0",10);

   RecoverRate         = new Label("Recover Rates",Label.RIGHT);
   RR1                 = new TextField("0.1",10);
   RR2                 = new TextField("0.5",10);
   RR3                 = new TextField("0.4",10);
   RR4                 = new TextField("0.0",10);
   RR5                 = new TextField("0.0",10);
   RR6                 = new TextField("0.0",10);
   RR7                 = new TextField("0.0",10);
   RR8                 = new TextField("0.0",10);
   RR9                 = new TextField("0.0",10);
   RR10                = new TextField("0.0",10);

   TimeValue            = new Label("Time Values",Label.RIGHT);
   TV1                 = new TextField("2.0",10);
   TV2                 = new TextField("2.0",10);
   TV3                 = new TextField("2.0",10);
   TV4                 = new TextField("0.0",10);
   TV5                 = new TextField("0.0",10);
   TV6                 = new TextField("0.0",10);
   TV7                 = new TextField("0.0",10);
   TV8                 = new TextField("0.0",10);
   TV9                 = new TextField("0.0",10);
   TV10                = new TextField("0.0",10);

   Frequnce            = new Label("Frequnce Values",Label.RIGHT);
   FV1                 = new TextField("0.2",10);
   FV2                 = new TextField("0.8",10);
   FV3                 = new TextField("0.0",10);
   FV4                 = new TextField("0.0",10);
   FV5                 = new TextField("0.0",10);
   FV6                 = new TextField("0.0",10);
   FV7                 = new TextField("0.0",10);
   FV8                 = new TextField("0.0",10);
   FV9                 = new TextField("0.0",10);
   FV10                = new TextField("0.0",10);
  
   add(EmptyCorner);
   add(Instruction1);
   add(Instruction2);
   add(Instruction3);
   add(Instruction4);
   add(Instruction5);
   add(Instruction6);
   add(Instruction7);
   add(Instruction8);
   add(Instruction9); 
   add(Instruction10);  

   add(FailureRate);
   add(FR1);
   add(FR2);
   add(FR3);
   add(FR4);
   add(FR5);
   add(FR6);
   add(FR7);
   add(FR8);
   add(FR9);
   add(FR10);
  
   add(RecoverRate);
   add(RR1);
   add(RR2);
   add(RR3);
   add(RR4);
   add(RR5);
   add(RR6);
   add(RR7);
   add(RR8);
   add(RR9);
   add(RR10);


   add(TimeValue);
   add(TV1);
   add(TV2);
   add(TV3);
   add(TV4);
   add(TV5);
   add(TV6);
   add(TV7);
   add(TV8);
   add(TV9);
   add(TV10);

   add(Frequnce);
   add(FV1);
   add(FV2);
   add(FV3);
   add(FV4);
   add(FV5);
   add(FV6);
   add(FV7);
   add(FV8);
   add(FV9);
   add(FV10); 
   }
}

  
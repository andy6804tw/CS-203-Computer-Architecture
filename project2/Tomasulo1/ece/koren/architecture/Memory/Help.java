import java.awt.*;
public class Help extends Panel
{

Label helpstr;

public Help()
{
    Font f = new Font("Courier",Font.BOLD,14);
    this.setFont(f);

   this.setSize(800,420);
   setLayout(new GridLayout(28,1));
    
   helpstr   = new Label("Interleaved Memory Simulator",Label.CENTER);
   add(helpstr);
   helpstr   = new Label("MODES of OPERATION",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Random  : Random memory references are generated and simulated",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   User Specified : Simulation is done on user specified memory reference sequence",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Array : Memory access for an array data type is simulated",Label.LEFT);
   add(helpstr);
   helpstr   = new Label("INPUTS",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Total Memory : Total memory available in system specified in MB",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Number Of Banks : Number of Banks, also referred as interleaving factor",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Frequency : The rate at which CPU generates Memory request",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Access Time : Number of CPU cycles spent in fetching data from memory",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Length of Sequence : Used to specify in random mode the number of sequence to generate",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   User Sequence : Memory address reference sequence specified directly by user",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Number of Rows : Specifies number of rows in an array reference",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Number of Columns : Specifies number of columns in an array reference",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Array stride : Length of array stride at which array is accessed ",Label.LEFT);
   add(helpstr);
   helpstr   = new Label("OUTPUT",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Bandwidth : It's measured in bytes delivered per cpu cycle",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Memory references to each bank is shown in a table form. ",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Each element of the table is a tuple <seq. number, memory addr. , arrival and service time> ",Label.LEFT);
   add(helpstr);
   helpstr   = new Label("STEPS",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   1. Specify mode of operation by pressing mode buttons",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   2. Input data in the form ",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   3. Press compute button ",Label.LEFT);
   add(helpstr);
   helpstr = new Label("NETSCAPE BUS ERROR",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   Sometimes the window crashes. Try setting MOZILLA_HOME",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   setenv MOZILLA_HOME <netscape-install-root>",Label.LEFT);
   add(helpstr);
   helpstr = new Label("   More workarounds at");
   add(helpstr);
   helpstr = new Label("       http://sdb.suse.de/sdb/en/html/netscape472.html",Label.LEFT);
   add(helpstr);


   }

}




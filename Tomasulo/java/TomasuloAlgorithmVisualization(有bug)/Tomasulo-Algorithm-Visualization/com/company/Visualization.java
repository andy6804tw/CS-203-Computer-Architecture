package com.company;

/**
 * Created by ylf951 on 16/3/25.
 */
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class Visualization extends JComponent {

        private static boolean pause = false;
        private static int clock = 1;
        private static List<DataBus> resourcesAvailable = new ArrayList<>();


        //Clocks per second that are used to set slider
        static final int CPS_MIN = 1;
        static final int CPS_MAX = 101;
        static final int CPS_INIT = 1; // initial clocks per second
        private static int cps = CPS_INIT;


        private static List<List<String>> instructions = new ArrayList<>();

        private static InstructionStatus IS;
        private static ReservationStations RS;
        private static LoadBuffers lb;
        private static StoreBuffers sb;
        private static Registers reg;


        //Start Button
        private static JButton button;

        //Reset Button
        private static JButton resetButton;

        //Show Info Label
        private static JLabel showInfo;

        //Show Clock Label
        private static JLabel text;


    public static void initial() {

//            List<String> temp = new ArrayList(Arrays.asList("LD", "F6", "34+", "R2"));
//            instructions.add(temp);
//            temp = new ArrayList(Arrays.asList("LD", "F2", "45+", "R3"));
//            instructions.add(temp);
//            temp = new ArrayList(Arrays.asList("MUL", "F0", "F2", "F4"));
//            instructions.add(temp);
//            temp = new ArrayList(Arrays.asList("SUB", "F8", "F6", "F2"));
//            instructions.add(temp);
//            temp = new ArrayList(Arrays.asList("DIV", "F10", "F0", "F6"));
//            instructions.add(temp);
//            temp = new ArrayList(Arrays.asList("ADD", "F6", "F8", "F2"));
//            instructions.add(temp);



            IS = new InstructionStatus(instructions);
            RS = new ReservationStations();
            lb = new LoadBuffers();
            sb = new StoreBuffers();
            reg = new Registers();


            button = new JButton();
            resetButton = new JButton();
            showInfo = new JLabel();
            text = new JLabel("0");
    }


    private static void runReset(){
        pause = true;
        clock = 1;
        resourcesAvailable = new ArrayList<>();
        text.setText(""+(clock-1));
        showInfo.setText("");
        IS.reset();
        RS.reset();
        lb.reset();
        sb.reset();
        reg.reset();
    }

    private static void runStart(){
        pause = false;
        showInfo.setText("Processing "+instructions.size()+" instructions...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Util.IssueState issueState = Util.IssueState.Available;

                while (!pause && !IS.isAllCompleted()) {

                    text.setText("" + clock);

                    //step 1: At the beginning of each clock, check whether there is any type of resources that becomes available
                    if (resourcesAvailable.size() != 0) {
                        for (int i = 0; i < resourcesAvailable.size(); i++) {
                            DataBus temp = resourcesAvailable.get(i);


                            IS.setWriteResultTime(temp.getInstrIndex(), clock);

                            String register = reg.getRegister(temp.getName());
                            reg.resetFu(register);

                            RS.becomeAvailable(temp.getName(), register);
                            sb.becomeAvailable(temp.getName());

                            if (temp.getResourceType() == DataBus.ResourceType.LoadBuffer) {
                                lb.setAvailable(temp.getName());
                                if (issueState == Util.IssueState.LackLoadBuffer)
                                    issueState = Util.IssueState.Available;
                            } else if (temp.getResourceType() == DataBus.ResourceType.StoreBuffer) {
                                sb.setAvailable(temp.getName());
                                if (issueState == Util.IssueState.LackStoreBuffer)
                                    issueState = Util.IssueState.Available;
                            } else {
                                RS.setAvailable(temp.getName());
                                if (temp.getName().substring(0, 3).equals("Add")) {
                                    if (issueState == Util.IssueState.LackAddUnit)
                                        issueState = Util.IssueState.Available;
                                } else {
                                    if (issueState == Util.IssueState.LackMultUnit)
                                        issueState = Util.IssueState.Available;
                                }
                            }

                        }
                        resourcesAvailable.clear();

                    }

                    //Step 2: If there are still instructions that are not issued and the type of resource this instruction needs is available, issue it!
                    if (!IS.isAllIssued() && issueState == Util.IssueState.Available) {
                        int instr_index = IS.getFirstUnIssued();
                        List<String> instr = instructions.get(instr_index);
                        switch (instr.get(0)) {
                            case "LD":
                                if (lb.hasAvailable()) {
                                    String name = lb.issueLoad(instr_index, instr.get(2) + instr.get(3));
                                    reg.setFu(instr.get(1), name);

                                    IS.setIssueTime(instr_index, clock);

                                } else {
                                    issueState = Util.IssueState.LackLoadBuffer; //lack of LoadBuffer resource.
                                }
                                break;
                            case "SD":
                                if(sb.hasAvailable()){
                                    String FUj = reg.getFu(instr.get(1));

                                    sb.issueStore(instr_index, instr.get(2) + instr.get(3), FUj);

                                    IS.setIssueTime(instr_index, clock);

                                }
                                else{
                                    issueState = Util.IssueState.LackStoreBuffer; //lack of StoreBuffer resource.
                                }
                                break;
                            case "ADD":
                            case "SUB":
                                if (RS.hasAvailableAdd()) {
                                    String FUj = reg.getFu(instr.get(2));
                                    String FUk = reg.getFu(instr.get(3));

                                    int j;
                                    if (FUj == "") {
                                        j = 1;
                                        FUj = "R(" + instr.get(2) + ")";
                                    } else {
                                        j = 2;
                                    }

                                    int k;
                                    if (FUk == "") {
                                        k = 1;
                                        FUk = "R(" + instr.get(3) + ")";
                                    } else {
                                        k = 2;
                                    }

                                    String FU = RS.issueAdd(instr_index, instr.get(0), j, FUj, k, FUk);
                                    reg.setFu(instr.get(1), FU);

                                    IS.setIssueTime(instr_index, clock);
                                } else {
                                    issueState = Util.IssueState.LackAddUnit; //lack of ReservationStations add resource.

                                }
                                break;
                            case "MUL":
                            case "DIV":
                                if (RS.hasAvailableMul()) {
                                    String FUj = reg.getFu(instr.get(2));
                                    String FUk = reg.getFu(instr.get(3));

                                    int j;
                                    if (FUj == "") {
                                        j = 1;
                                        FUj = "R(" + instr.get(2) + ")";
                                    } else {
                                        j = 2;
                                    }

                                    int k;
                                    if (FUk == "") {
                                        k = 1;
                                        FUk = "R(" + instr.get(3) + ")";
                                    } else {
                                        k = 2;
                                    }
                                    String FU = RS.issueMult(instr_index, instr.get(0), j, FUj, k, FUk);
                                    reg.setFu(instr.get(1), FU);

                                    IS.setIssueTime(instr_index, clock);
                                } else {
                                    issueState = Util.IssueState.LackMultUnit; //lack of ReservationStations mult resource.

                                }
                                break;

                        }
                    }//if (!IS.isAllIssued() && issueState == Util.IssueState.Available)



                    //Step 3: check Load Buffer to see whether there is load instruction whose execution is completed.
                    //If so, set the ExecComplTime of this particular instruction in Instruction Status, and add element into resourceAvailable
                    //to show that the Load Buffer resource this instruction occupies will become available at next clock and BROADCAST its result
                    //to Reservation Stations at next clock.
                    List<DataBus> result = lb.moveNextClock();
                    if (result.size() != 0) {
                        for (int i = 0; i < result.size(); i++) {
                            DataBus temp = result.get(i);

                            IS.setExecComplTime(temp.getInstrIndex(), clock);

                            resourcesAvailable.add(new DataBus(DataBus.CompletionType.WriteResult, temp.getInstrIndex(), temp.getName(), temp.getResourceType()));
                        }

                    }

                    //Step4: check Store Buffer, similar to Step3
                    result = sb.moveNextClock();
                    if (result.size() != 0) {
                        for (int i = 0; i < result.size(); i++) {
                            DataBus temp = result.get(i);

                            IS.setExecComplTime(temp.getInstrIndex(), clock);

                            resourcesAvailable.add(new DataBus(DataBus.CompletionType.WriteResult, temp.getInstrIndex(), temp.getName(), temp.getResourceType()));
                        }

                    }

                    //Step5: check Reservation Stations, similar to Step3
                    result = RS.moveNextClock();
                    if (result.size() != 0) {
                        for (int i = 0; i < result.size(); i++) {
                            DataBus temp = result.get(i);

                            IS.setExecComplTime(temp.getInstrIndex(), clock);

                            resourcesAvailable.add(new DataBus(DataBus.CompletionType.WriteResult, temp.getInstrIndex(), temp.getName(), temp.getResourceType()));
                        }

                    }


                    try {
                        Thread.sleep(1000/cps);
                    } catch (InterruptedException excep) {
                        excep.printStackTrace();
                    }
                    clock++;
                }

                if(IS.isAllCompleted()){
                    showInfo.setText(""+instructions.size()+" instructions are completed in "+(clock-1)+" clocks!");
                    try{
                        FileWriter writer = new FileWriter("Tomasulo_output.txt", true);
                        writer.write((clock-1)+"\n");
                        writer.close();

                    }catch (IOException excep){
                        excep.printStackTrace();
                    }

                }
            }
        });

        thread.start();

    }

    public static void openNewFile(File file){
        instructions.clear();
        try{
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            while((strLine = br.readLine()) != null){
                List<String> temp = Arrays.asList(strLine.split(" "));
                instructions.add(temp);
            }

            IS.loadNewInstructions(instructions);

            runReset();

            in.close();

        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Tomasulo Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1105, 875));


        initial();

        JScrollPane scrollPane1 = new JScrollPane(IS.getTable());
        scrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Instruction Status", TitledBorder.CENTER, TitledBorder.TOP));

        JScrollPane scrollPane2 = new JScrollPane(RS.getTable());
        scrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reservation Stations", TitledBorder.CENTER, TitledBorder.TOP));

        JScrollPane scrollPane3 = new JScrollPane(lb.getTable());
        scrollPane3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Load Buffer", TitledBorder.CENTER, TitledBorder.TOP));

        JScrollPane scrollPane4 = new JScrollPane(sb.getTable());
        scrollPane4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Store Buffer", TitledBorder.CENTER, TitledBorder.TOP));

        JScrollPane scrollPane5 = new JScrollPane(reg.getTable());
        scrollPane5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Register Status", TitledBorder.CENTER, TitledBorder.TOP));





        //Layout of each component. For simplicity, absolute position is chosen.
        frame.setLayout(null);

        frame.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 900, 225);

        frame.add(scrollPane2);
        scrollPane2.setBounds(0, 250, 900, 225);

        frame.add(scrollPane3);
        scrollPane3.setBounds(0, 500, 425, 150);

        frame.add(scrollPane4);
        scrollPane4.setBounds(475, 500, 425, 150);

        frame.add(scrollPane5);
        scrollPane5.setBounds(925, 0, 180, 650);

        //Start Button
        button.setText("Start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runStart();
            }
        });
        frame.add(button);
        button.setBounds(10, 675, 120, 60);


        //Pause Button
        JButton pauseButton = new JButton();
        pauseButton.setText("Pause");
        frame.add(pauseButton);
        pauseButton.setBounds(155, 675, 120, 60);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pause)
                    pause = false;
                else
                    pause = true;
            }
        });

        //Reset Button
        resetButton.setText("Reset");
        frame.add(resetButton);
        resetButton.setBounds(300, 675, 120, 60);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runReset();
            }
        });


        //OpenFile Button
        JButton openFileButton = new JButton();
        openFileButton.setText("Open");
        frame.add(openFileButton);
        openFileButton.setBounds(475, 675, 120, 60);
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser("res");
                int returnVal = fc.showOpenDialog(openFileButton);

                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    System.out.println("Opening: " + file.getName());
                    openNewFile(file);


                }


            }
        });



        //Slider, adjust clocks per second
        JSlider clocksPerSecond = new JSlider(JSlider.HORIZONTAL, CPS_MIN, CPS_MAX, CPS_INIT);
        clocksPerSecond.setMajorTickSpacing(10);
        //clocksPerSecond.setMinorTickSpacing(1);
        clocksPerSecond.setPaintLabels(true);
        frame.add(clocksPerSecond);
        clocksPerSecond.setBounds(25, 760, 300, 75);
        clocksPerSecond.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                cps = source.getValue();
            }
        });

        JLabel showClocks = new JLabel("Clocks: ");
        showClocks.setFont(new Font("Serif", Font.PLAIN, 20));
        frame.add(showClocks);
        showClocks.setBounds(350, 755, 75, 75);

        frame.add(text);
        text.setFont(new Font("Serif", Font.PLAIN, 25));
        text.setBounds(450, 755, 75, 75);




        frame.add(showInfo);
        showInfo.setFont(new Font("Serif", Font.PLAIN, 25));
        showInfo.setBounds(550, 755, 525, 75);

        frame.validate();
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

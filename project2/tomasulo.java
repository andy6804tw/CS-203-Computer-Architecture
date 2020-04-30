
import java.io.*;
import java.util.*;

// 指令
class Instruction{
    String opcode="";
    String rd="",rs="",rt="";
    int issue=0,executed=0,execution=0,written=0;

    public Instruction(String opcode,String rd,String rs,String rt){
        this.opcode=opcode;
        this.rd=rd;
        this.rs=rs;
        this.rt=rt;
    }
}
// Reservation Station
class ReservationStation{
    int busy=0;
    String opcode="";
    String FU="";	//記錄使用哪個function unit
    double Vj=0;
    double Vk=0;
    double Qj=0;
    double Qk=0;
    int remain=0;
    public ReservationStation(String opcode,String FU,double Vj,double Vk,double Qj,double Qk){
        this.opcode=opcode;
        this.FU=FU;
        this.Vj=Vj;
        this.Vk=Vk;
        this.Qj=Qj;
        this.Qk=Qk;
    }
}
// class RegisterStatus{
//     String name="";
//     double value=0;
//     public RegisterStatus(String name,double value){
//         this.name=name;
//         this.value=value;
//     }
// }

public class tomasulo {

    public static ArrayList<Instruction> instructionList = new ArrayList<>();
    // Reservation Station
    ReservationStation loadBuffer[]=new ReservationStation[2];
    ReservationStation storeBuffer[]=new ReservationStation[2];
    ReservationStation adder[]=new ReservationStation[3];
    ReservationStation multiplier[]=new ReservationStation[2];
    // Register Status
    // RegisterStatus fRegister[]=new RegisterStatus[16];
    // RegisterStatus iRegister[]=new RegisterStatus[32];
    HashMap fRegister = new HashMap();
    HashMap iRegister = new HashMap();

    public static void main(String[] args) {

        // read file
        readFile("./test/example2.txt");
        for(int i=0;i<instructionList.size();i++){
            Instruction ins=instructionList.get(i);
            System.out.println(ins.opcode+" "+ins.rd+" "+ins.rs+" "+ins.rt);
        }
        
    }
    /**
   * 讀檔將指令分解 opcode, rd, rs, rt
   * @param filename
   */
  public static void readFile(String filename) {
    try {
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      Scanner scn = new Scanner(br);
      while (scn.hasNext()) {
        String instruction = scn.nextLine();
        String opcode=instruction.split(" ")[0];
        String rd="",rs="",rt="";
        if(opcode.equals("L.D")||opcode.equals("S.D")){
            String register=instruction.replace(opcode, "").replaceAll(" ", "");
            String registerArray[]=register.split(",");
            rd=registerArray[0];
            rs=registerArray[1].split("[()]")[0];
            rt=registerArray[1].split("[()]")[1];
        }else{
            String register=instruction.replace(opcode, "").replaceAll(" ", "");
            String registerArray[]=register.split(",");
            rd=registerArray[0];
            rs=registerArray[1];
            rt=registerArray[2];
        }

        instructionList.add(new Instruction(opcode, rd, rs, rt));
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
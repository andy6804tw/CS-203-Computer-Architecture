
import java.io.*;
import java.util.*;

// 指令
class Instruction{
    String opcode="";
    String rd="",rs="",rt="";
    int issue=0,executed=0,written=0;

    public Instruction(String opcode,String rd,String rs,String rt){
        this.opcode=opcode;
        this.rd=rd;
        this.rs=rs;
        this.rt=rt;
    }
}

public class tomasulo {

    public static ArrayList<Instruction> instructionList = new ArrayList<>();
    public static void main(String[] args) {

        // read file
        readFile("./test/example1.txt");
        for(int i=0;i<instructionList.size();i++){
            Instruction ins=instructionList.get(i);
            System.out.println(ins.opcode+" "+ins.rd+" "+ins.rs+" "+ins.rt);
        }
        
    }
    /**
   * 讀檔將指令分解
   * 
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
        if(opcode.equals("L.D")){
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
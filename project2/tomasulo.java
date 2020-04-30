
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
    int id=0; // 表示第幾個指令
    int busy=0;
    String opcode="";
    String Vj="";
    String Vk="";
    String Qj="";
    String Qk="";
    int remain=0;
    public void flush(){
        this.busy=0;
        this.opcode="";
        this.Vj="";
        this.Vk="";
        this.Qj="";
        this.Qk="";
        this.remain=0;
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
    public static int loadMount=2;
    public static int storeMount=2;
    public static int addMount=3;
    public static int mulMount=2;
    public static ReservationStation loadBuffer[]=new ReservationStation[loadMount];
    public static ReservationStation storeBuffer[]=new ReservationStation[storeMount];
    public static ReservationStation adder[]=new ReservationStation[addMount];
    public static ReservationStation multiplier[]=new ReservationStation[mulMount];
    // Register Status
    public static HashMap fRegister = new HashMap();
    public static HashMap iRegister = new HashMap();
    // Op cycle
    public static int loadCycle=2;
    public static int storeCycle=1;
    public static int mutiplyCycle=10;
    public static int addCycle=2;
    public static int divideCycle=40;
    // clock
    public static int clock=1;
    public static int cur_ins_position=0; // 目前指令被執行的行數

    public static void main(String[] args) {

        // read file
        readFile("./test/test1.txt");
        // for(int i=0;i<instructionList.size();i++){
        //     Instruction ins=instructionList.get(i);
        //     System.out.println(ins.opcode+" "+ins.rd+" "+ins.rs+" "+ins.rt);
        // }
        init();
        while(true){
            /** WriteResult */
            // 檢查全部的Reservation Station，是否已經可以Write Result
            // Load
            for(int i=0;i<loadMount;i++){
                if(loadBuffer[i].busy==1){
                    int ins_index=loadBuffer[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // 檢查是否可以Write Result
                    if(ins.executed!=0){
                        ins.written=clock;
                        // 清空Station
                        loadBuffer[i].flush();
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            // Adder
            for(int i=0;i<addMount;i++){
                if(adder[i].busy==1){
                    int ins_index=adder[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // 檢查是否可以Write Result
                    if(ins.executed!=0){
                        ins.written=clock;
                        // 清空Station
                        adder[i].flush();
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            // Multiplier
            for(int i=0;i<mulMount;i++){
                if(multiplier[i].busy==1){
                    int ins_index=multiplier[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // 檢查是否可以Write Result
                    if(ins.executed!=0){
                        ins.written=clock;
                        // 清空Station
                        multiplier[i].flush();
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            /** 執行 */
            /** 執行 */
            // 檢查全部的Reservation Station，能立即執行的就開始執行
            // Load
            for(int i=0;i<loadMount;i++){
                if(loadBuffer[i].busy==1){
                    int ins_index=loadBuffer[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // check start exec
                    if(ins.execution==0){
                        ins.execution=clock;
                    }
                    // 檢查是否可執行結束
                    if(ins.execution+loadCycle-1==clock){
                        ins.executed=clock;
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            // Adder
            for(int i=0;i<addMount;i++){
                if(adder[i].busy==1){
                    int ins_index=adder[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // check start exec
                    if(ins.execution==0){
                        ins.execution=clock;
                    }
                    // 檢查是否可執行結束
                    if(ins.execution+addCycle-1==clock){
                        ins.executed=clock;
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            // Multiplier
            for(int i=0;i<mulMount;i++){
                if(multiplier[i].busy==1){
                    int ins_index=multiplier[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // check start exec
                    if(ins.execution==0){
                        ins.execution=clock;
                    }
                    // 檢查是否可執行結束，首先分辨是 div/mul
                    if(ins.opcode.equals("MUL.D")){
                        if(ins.execution+mutiplyCycle-1==clock){
                            ins.executed=clock;
                        }
                    }else{
                        if(ins.execution+divideCycle-1==clock){
                            ins.executed=clock;
                        }
                    }
                    
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            /** 執行 */
            /** Issue */
            // 確認下一個指令是否可以被Issue
            if(cur_ins_position<instructionList.size()){
                Instruction instruction=instructionList.get(cur_ins_position);
                String opcode=instruction.opcode;
                // 判斷是否還有Reservation Station可用
                if(opcode.equals("L.D")){
                    for(int i=0;i<loadMount;i++){
                        if(loadBuffer[i].busy==0){
                            loadBuffer[i].busy=1;
                            loadBuffer[i].id=cur_ins_position;
                            loadBuffer[i].Vj=iRegister.get(instruction.rt).toString();
                            loadBuffer[i].Vk=iRegister.get(instruction.rt).toString();
                            instruction.issue=clock;
                            break;
                        }
                    }
                }else if(opcode.equals("ADD.D")||opcode.equals("SUB.D")){
                    for(int i=0;i<addMount;i++){
                        if(adder[i].busy==0){
                            adder[i].busy=1;
                            adder[i].id=cur_ins_position;
                            adder[i].Vj=fRegister.get(instruction.rs).toString();
                            adder[i].Vk=fRegister.get(instruction.rt).toString();
                            instruction.issue=clock;
                            break;
                        }
                    }
                }else if(opcode.equals("DIV.D")||opcode.equals("MUL.D")){
                    for(int i=0;i<mulMount;i++){
                        if(multiplier[i].busy==0){
                            multiplier[i].busy=1;
                            multiplier[i].id=cur_ins_position;
                            multiplier[i].Vj=fRegister.get(instruction.rs).toString();
                            multiplier[i].Vk=fRegister.get(instruction.rt).toString();
                            instruction.issue=clock;
                            break;
                        }
                    }
                }
                // 更新指令狀態
                instructionList.set(cur_ins_position,instruction);
            }
            cur_ins_position++;
            /** Issue */
            
            // 檢查是否可以結束
            int flag=0;
            for(;flag<instructionList.size();flag++){
                Instruction ins=instructionList.get(flag);
                if(ins.issue!=0&&ins.executed!=0&&ins.written!=0)
                    continue;
                else
                    break;
            }
            showInfo();
            if(flag==instructionList.size())
                break;
            // if(clock==44)
            //     break;
            clock++;
        }
        System.out.println("End: "+clock);
        
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
  public static void init(){
    // init Register Status
    for(int i=0;i<=30;i+=2){
        fRegister.put("F"+i, 1);
    }
    for(int i=0;i<=31;i++){
        if(i==1)
            iRegister.put("R"+i, 16);
        else
            iRegister.put("R"+i, 0);
    }
    // init Reservation Station
    for(int i=0;i<loadMount;i++){
        loadBuffer[i]=new ReservationStation();
    }
    for(int i=0;i<storeMount;i++){
        storeBuffer[i]=new ReservationStation();
    }
    for(int i=0;i<addMount;i++){
        adder[i]=new ReservationStation();
    }
    for(int i=0;i<mulMount;i++){
        multiplier[i]=new ReservationStation();
    }
        
  }
  public static void showInfo(){
    System.out.println("週期: "+clock);
    System.out.println("Instruction Status");
    System.out.println("指令類型\tIssue\t開始執行\t執行結束\t寫回");
    for(int i=0;i<instructionList.size();i++){
        Instruction ins=instructionList.get(i);
        System.out.println(ins.opcode+"\t\t"+ins.issue+"\t"+ins.execution+"\t\t"+ins.executed+"\t\t\t"+ins.written);
    }
  }
}
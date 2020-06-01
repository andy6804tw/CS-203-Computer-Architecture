import java.io.*;
import java.util.*;

/**
 * 指令格式 opcode rd, rs, rt
 * @param opcode
 * @param rd
 * @param rs
 * @param rt
 */ 
class Instruction{
    String opcode="";
    String rd="",rs="",rt="";
    // issue表示第幾個clock有資源可用
    // excution表示開始執行的clock
    // executed(execution complete)表示執行完畢clock
    // written(write result)表示被寫入clock
    int issue=0,executed=0,execution=0,written=0;
    public Instruction(String opcode,String rd,String rs,String rt){
        this.opcode=opcode;
        this.rd=rd;
        this.rs=rs;
        this.rt=rt;
    }
}
/**
 * Reservation Station 
 * 與StoreBuffer&LoadBuffer共用
 */
class ReservationStation{
    int id=0; // 表示第幾個指令(行數)
    int busy=0; // 0:閒置中 1:資源佔用中
    String opcode=""; // operand指令種類
    String Vj="", Vk=""; // Vj, Vk：可以馬上使用的實際數值
    String Qj="", Qk=""; // Qj, Qk：目標暫存器的值還在處理中等待廣播
    int remain=0; // 儲存還剩餘幾個cycle將結束執行
    // 清空
    public void flush(){
        this.busy=0;
        this.opcode="";
        this.Vj="";this.Vk="";
        this.Qj="";this.Qk="";
        this.remain=0;
    }
}

/**
 * 主程式
 */
public class tomasulo_head_sd {
    // 執行指令串列
    public static ArrayList<Instruction> instructionList = new ArrayList<>();
    // Reservation Station (載入、儲存、加法器、除法器) 數量初始化與建立陣列
    public static int loadMount=2;
    public static int storeMount=2;
    public static int addMount=3;
    public static int mulMount=2;
    public static ReservationStation loadBuffer[]=new ReservationStation[loadMount];
    public static ReservationStation storeBuffer[]=new ReservationStation[storeMount];
    public static ReservationStation adder[]=new ReservationStation[addMount];
    public static ReservationStation multiplier[]=new ReservationStation[mulMount];
    // Register Status (浮點數暫存器、整數暫存器)
    public static HashMap<String,String> fRegister = new HashMap<>();
    public static HashMap<String,String> iRegister = new HashMap<>();
    // 指令執行週期設定 L.D=2、S.D=1、MUL.D=10、ADD.D=2、DIV.D=40
    public static int loadCycle=2;
    public static int storeCycle=1;
    public static int mutiplyCycle=10;
    public static int addCycle=2;
    public static int divideCycle=40;
    // clock時間週期初始化
    public static int clock=1;
    // 儲存目前指令被執行的位置id(行數)
    public static int cur_ins_position=0;
    // 寫檔
    public static String str="";
    // 讀檔檔名
    public static String fileName="";

    public static void main(String[] args) {

        Scanner scn= new Scanner(System.in);
        if(args.length!=0){
			fileName=args[0];
		}else{
            System.out.print("請輸入檔名(xxx.txt): ");
            fileName=scn.nextLine();
        }
        // Read File
        readFile(fileName);
        // 保留站與暫存器初始化
        init();
        while(true){
            /** WriteResult */
            // 檢查全部的Reservation Station，是否已經可以Write Result
            // LoadBuffer
            for(int i=0;i<loadMount;i++){
                if(loadBuffer[i].busy==1){
                    int ins_index=loadBuffer[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // 檢查是否可以Write Result
                    if(ins.executed!=0&&ins.written==0){
                        ins.written=clock;
                        // 廣播Load更新storeBuffer
                        for(int j=0;j<loadMount;j++){
                            if(storeBuffer[j].Qj.equals("Load"+i)){
                                storeBuffer[j].Qj="";
                                storeBuffer[j].Vj=iRegister.get(ins.rt).toString();
                            }
                        }
                        // 廣播Load更新adder
                        for(int j=0;j<addMount;j++){
                            if(adder[j].Qj.equals("Load"+i) && adder[j].remain==0){
                                adder[j].Qj="";
                                adder[j].Vj=iRegister.get(ins.rt).toString();
                            }
                            if(adder[j].Qk.equals("Load"+i) && adder[j].remain==0){
                                adder[j].Qk="";
                                adder[j].Vk=iRegister.get(ins.rt).toString();
                            }
                        }
                        // 廣播Load更新multiplier
                        for(int j=0;j<mulMount;j++){
                            if(multiplier[j].Qj.equals("Load"+i)){
                                multiplier[j].Qj="";
                                multiplier[j].Vj=iRegister.get(ins.rt).toString();
                            }
                            if(multiplier[j].Qk.equals("Load"+i)){
                                multiplier[j].Qk="";
                                multiplier[j].Vk=iRegister.get(ins.rt).toString();
                            }
                        }
                    }
                    // 清空Station
                    if(ins.written+1==clock){
                        loadBuffer[i].flush();
                        
                            
                    }
                    if(ins.written==clock){
                        // 檢查WAW處理Output Dependence
                        int isBroadcast=1;
                        System.out.println("cur_ins_position "+cur_ins_position+" "+ins_index);
                        for(int j=cur_ins_position-1;j>ins_index;j--){
                            if(instructionList.get(j).rd.equals(ins.rd)&&instructionList.get(j).executed==0){
                                isBroadcast=0;
                            }
                        }
                        if(isBroadcast==1){
                            // 廣播更新
                            fRegister.put(ins.rd, iRegister.get(ins.rt).toString());
                        }
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            // StoreBuffer
            for(int i=0;i<storeMount;i++){
                if(storeBuffer[i].busy==1){
                    int ins_index=storeBuffer[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // 檢查是否可以Write Result
                    if(ins.executed!=0&&ins.written==0&&storeBuffer[i].Qj.equals("")){
                        ins.written=clock;
                        // 廣播更新
                        // fRegister.put(ins.rd, iRegister.get(ins.rt).toString());
                        // 廣播Load更新storeBuffer
                        for(int j=0;j<loadMount;j++){
                            if(storeBuffer[j].Qj.equals("Store"+i)){
                                storeBuffer[j].Qj="";
                                storeBuffer[j].Vj=fRegister.get(ins.rd).toString();
                            }
                        }
                        // 廣播Load更新adder
                        for(int j=0;j<addMount;j++){
                            if(adder[j].Qj.equals("Store"+i)){
                                adder[j].Qj="";
                                adder[j].Vj=fRegister.get(ins.rd).toString();
                            }
                            if(adder[j].Qk.equals("Store"+i)){
                                adder[j].Qk="";
                                adder[j].Vk=fRegister.get(ins.rd).toString();
                            }
                        }
                        // 廣播Load更新multiplier
                        for(int j=0;j<mulMount;j++){
                            if(multiplier[j].Qj.equals("Store"+i)){
                                multiplier[j].Qj="";
                                multiplier[j].Vj=fRegister.get(ins.rd).toString();
                            }
                            if(multiplier[j].Qk.equals("Store"+i)){
                                multiplier[j].Qk="";
                                multiplier[j].Vk=fRegister.get(ins.rd).toString();
                            }
                        }
                    }
                    // 清空Station
                    if(ins.written+1==clock){
                        storeBuffer[i].flush();
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
                    if(ins.executed!=0&&ins.written==0){
                        ins.written=clock;

                        // 更新FU
                        double broadcastValue=0;
                        System.out.println(ins.rs+" "+ins.rt);
                        if(ins.opcode.equals("ADD.D"))
                            broadcastValue=Double.parseDouble(adder[i].Vj)+Double.parseDouble(adder[i].Vk);
                        else if(ins.opcode.equals("SUB.D"))
                            broadcastValue=Double.parseDouble(adder[i].Vj)-Double.parseDouble(adder[i].Vk);
                        
                        // 廣播Add更新storeBuffer
                        for(int j=0;j<loadMount;j++){
                            if(storeBuffer[j].Qj.equals("Add"+i)){
                                storeBuffer[j].Qj="";
                                storeBuffer[j].Vj=broadcastValue+"";
                            }
                        }
                        // 廣播Add更新adder
                        for(int j=0;j<addMount;j++){
                            if(adder[j].Qj.equals("Add"+i)){
                                adder[j].Qj="";
                                adder[j].Vj=broadcastValue+"";
                            }
                            if(adder[j].Qk.equals("Add"+i)){
                                adder[j].Qk="";
                                adder[j].Vk=broadcastValue+"";
                            }
                        }
                        // 廣播Add更新multiplier
                        for(int j=0;j<mulMount;j++){
                            if(multiplier[j].Qj.equals("Add"+i)){
                                multiplier[j].Qj="";
                                multiplier[j].Vj=broadcastValue+"";
                            }
                            if(multiplier[j].Qk.equals("Add"+i)){
                                multiplier[j].Qk="";
                                multiplier[j].Vk=broadcastValue+"";
                            }
                        }
                    }
                    // 清空Station
                    if(ins.written+1==clock){
                        adder[i].flush();
                        
                    }
                    if(ins.written==clock){
                        // 檢查WAW處理Output Dependence
                        int isBroadcast=1;
                        for(int j=cur_ins_position-1;j>ins_index;j--){
                            if(instructionList.get(j).rd.equals(ins.rd)&&instructionList.get(j).executed==0){
                                isBroadcast=0;
                            }
                        }
                        if(isBroadcast==1){
                            // 廣播更新
                            double result=0;
                            if(ins.opcode.equals("ADD.D"))
                                result=Double.parseDouble(adder[i].Vj)+Double.parseDouble(adder[i].Vk);
                            else if(ins.opcode.equals("SUB.D"))
                                result=Double.parseDouble(adder[i].Vj)-Double.parseDouble(adder[i].Vk);
                            fRegister.put(ins.rd, result+"");
                        }
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
                    if(ins.executed!=0&&ins.written==0){
                        ins.written=clock;
                        // 更新FU
                        double result=0;
                        if(ins.opcode.equals("MUL.D"))
                            result=Double.parseDouble(multiplier[i].Vj)*Double.parseDouble(multiplier[i].Vk);
                        else if(ins.opcode.equals("DIV.D"))
                            result=Double.parseDouble(multiplier[i].Vj)/Double.parseDouble(multiplier[i].Vk);
                        fRegister.put(ins.rd, result+"");
                        // 廣播Mul更新storeBuffer
                        for(int j=0;j<loadMount;j++){
                            if(storeBuffer[j].Qj.equals("Mul"+i)){
                                storeBuffer[j].Qj="";
                                storeBuffer[j].Vj=fRegister.get(ins.rd).toString();
                            }
                        }
                        // 廣播Mul更新adder
                        for(int j=0;j<addMount;j++){
                            if(adder[j].Qj.equals("Mul"+i)){
                                adder[j].Qj="";
                                adder[j].Vj=fRegister.get(ins.rd).toString();
                            }
                            if(adder[j].Qk.equals("Mul"+i)){
                                adder[j].Qk="";
                                adder[j].Vk=fRegister.get(ins.rd).toString();
                            }
                        }
                        // 廣播Mul更新multiplier
                        for(int j=0;j<mulMount;j++){
                            if(multiplier[j].Qj.equals("Mul"+i)){
                                multiplier[j].Qj="";
                                multiplier[j].Vj=fRegister.get(ins.rd).toString();
                            }
                            if(multiplier[j].Qk.equals("Mul"+i)){
                                multiplier[j].Qk="";
                                multiplier[j].Vk=fRegister.get(ins.rd).toString();
                            }
                        }
                    }
                    // 清空Station
                    if(ins.written+1==clock){
                        multiplier[i].flush();
                        
                    }
                    if(ins.written==clock){
                        // 檢查WAW處理Output Dependence
                        int isBroadcast=1;
                        for(int j=cur_ins_position-1;j>ins_index;j--){
                            if(instructionList.get(j).rd.equals(ins.rd)&&instructionList.get(j).executed==0){
                                isBroadcast=0;
                            }
                        }
                        if(isBroadcast==1){
                            // 廣播更新
                            double result=0;
                            if(ins.opcode.equals("MUL.D"))
                                result=Double.parseDouble(multiplier[i].Vj)*Double.parseDouble(multiplier[i].Vk);
                            else if(ins.opcode.equals("DIV.D"))
                                result=Double.parseDouble(multiplier[i].Vj)/Double.parseDouble(multiplier[i].Vk);
                            fRegister.put(ins.rd, result+"");
                        }
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            /** WriteResult */
            /** 執行 */
            // 檢查全部的Reservation Station，能立即執行的就開始執行
            // Load
            for(int i=0;i<loadMount;i++){
                if(loadBuffer[i].busy==1){
                    int ins_index=loadBuffer[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // check start exec
                    if(loadBuffer[i].Qj.equals("") && ins.execution==0){
                        ins.execution=clock;
                    }
                    // 檢查是否可執行結束
                    if(ins.execution+loadCycle==clock && ins.execution!=0){
                        ins.executed=clock;
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            // Store
            // 判斷是否在load-store queue的最前端才能做execute
            int checkFirst=1;
            for(int i=0;i<storeMount;i++){
                if(storeBuffer[i].busy==1&&checkFirst==1){
                    int ins_index=storeBuffer[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // check start exec
                    if(ins.execution==0){
                        ins.execution=clock-1;
                    }
                    // 檢查是否可執行結束
                    if(ins.execution+storeCycle==clock && ins.execution!=0){
                        ins.executed=clock;
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                    checkFirst=0;
                }
            }
            // Adder
            for(int i=0;i<addMount;i++){
                if(adder[i].busy==1){
                    int ins_index=adder[i].id; // 提取第幾個指令id被執行
                    Instruction ins=instructionList.get(ins_index); // 取得指令
                    // check start exec
                    if(adder[i].Qj.equals("") && adder[i].Qk.equals("") && ins.execution==0){
                        ins.execution=clock;
                    }
                    // 檢查是否可執行結束
                    if(ins.execution+addCycle==clock && ins.execution!=0){
                        ins.executed=clock;
                        adder[i].remain=0; // 剩餘時間歸零
                    }else if(ins.execution!=0  && ins.executed==0){
                        // 計算剩餘執行時間
                        adder[i].remain=ins.execution+addCycle-clock;
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
                    if(multiplier[i].Qj.equals("") && multiplier[i].Qk.equals("") && ins.execution==0){
                        ins.execution=clock;
                    }
                    // 檢查是否可執行結束，首先分辨是 div/mul
                    if(ins.opcode.equals("MUL.D")){
                        if(ins.execution+mutiplyCycle==clock && ins.execution!=0){
                            ins.executed=clock;
                            multiplier[i].remain=0; // 剩餘時間歸零
                        }else if(ins.execution!=0  && ins.executed==0){
                            // 計算剩餘執行時間
                            multiplier[i].remain=ins.execution+mutiplyCycle-clock;
                        }
                    }else{
                        if(ins.execution+divideCycle==clock && ins.execution!=0){
                            ins.executed=clock;
                            multiplier[i].remain=0; // 剩餘時間歸零
                        }else if(ins.execution!=0  && ins.executed==0){
                            // 計算剩餘執行時間
                            multiplier[i].remain=ins.execution+divideCycle-clock;
                        }
                    }
                    // 更新指令狀態
                    instructionList.set(ins_index,ins);
                }
            }
            /** 執行 */
            /** 
             * Issue
             * 當有資源可利用時(busy=0)，檢查下一個指令是否可以被Issue
             * loadBuffer[2]=> L.D
             * stroeBuffer[2]=> S.D
             * adder[3]=> ADD.D、SUB.D
             * multiplier[2]=> MUL.D、DIV.D
             *  */
            if(cur_ins_position<instructionList.size()){
                // 若還有指令未被執行將確認是否可被Issue
                Instruction instruction=instructionList.get(cur_ins_position);
                String opcode=instruction.opcode;
                // 保留站變數初始化，檢查是否有true dependence
                String Vj="",Vk="",Qj="",Qk="";
                String rs=instruction.rs;
                String rt=instruction.rt;
                String rd=instruction.rd;
                // 查詢到有true dependence就不繼續往上找，直到rs和rt都個別找到(isRsDone、isRtDone皆為1)
                int isRsDone=0,isRtDone=0;
                if(!(opcode.equals("L.D")||opcode.equals("S.D"))){
                    for(int i=cur_ins_position-1;i>=0;i--){
                        Instruction preInstruction=instructionList.get(i);
                        if(preInstruction.rd.equals(rs) && isRsDone==0){
                            if(preInstruction.written==0)
                                Qj=fRegister.get(instruction.rs).toString();
                            isRsDone=1;
                        }
                        if(preInstruction.rd.equals(rt) && isRtDone==0){
                            if(preInstruction.written==0)
                                Qk=fRegister.get(instruction.rt).toString();
                            isRtDone=1;
                        }
                    }
                    // 若都無代表無true dependence，即更新Vj、Vk準備執行
                    if(Qj.equals(""))
                        Vj=fRegister.get(instruction.rs).toString();
                    if(Qk.equals(""))
                        Vk=fRegister.get(instruction.rt).toString();
                }else if(opcode.equals("S.D")){
                    // 判斷SD是否可以被儲存
                    for(int i=cur_ins_position-1;i>=0;i--){
                        Instruction preInstruction=instructionList.get(i);
                        if(preInstruction.rd.equals(rd) && preInstruction.written==0){
                            Qj=fRegister.get(rd).toString();
                        }
                    }
                }
                // 判斷是否還有Reservation Station資源可用
                // 若可以利用則將busy=1，以及更新Reservation Station內容
                // id為查表用(存index)，可以從保留站回推是哪一個條被執行指令
                int isIssue=0;
                if(opcode.equals("L.D")){
                    for(int i=0;i<loadMount;i++){
                        if(loadBuffer[i].busy==0){
                            loadBuffer[i].busy=1;
                            loadBuffer[i].id=cur_ins_position;
                            fRegister.put(instruction.rd,"Load"+i);
                            isIssue=1;
                            break;
                        }
                    }
                }else if(opcode.equals("S.D")){
                    for(int i=0;i<storeMount;i++){
                        if(storeBuffer[i].busy==0){
                            storeBuffer[i].busy=1;
                            storeBuffer[i].id=cur_ins_position;
                            storeBuffer[i].Qj=Qj;
                            isIssue=1;
                            break;
                        }
                    }
                }else if(opcode.equals("ADD.D")||opcode.equals("SUB.D")){
                    for(int i=0;i<addMount;i++){
                        if(adder[i].busy==0){
                            adder[i].busy=1;
                            adder[i].id=cur_ins_position;
                            adder[i].Vj=Vj;
                            adder[i].Vk=Vk;
                            adder[i].Qj=Qj;
                            adder[i].Qk=Qk;
                            adder[i].opcode=opcode;
                            fRegister.put(instruction.rd,"Add"+i);
                            isIssue=1;
                            break;
                        }
                    }
                }else if(opcode.equals("DIV.D")||opcode.equals("MUL.D")){
                    for(int i=0;i<mulMount;i++){
                        if(multiplier[i].busy==0){
                            multiplier[i].busy=1;
                            multiplier[i].id=cur_ins_position;
                            multiplier[i].Vj=Vj;
                            multiplier[i].Vk=Vk;
                            multiplier[i].Qj=Qj;
                            multiplier[i].Qk=Qk;
                            multiplier[i].opcode=opcode;
                            fRegister.put(instruction.rd,"Mul"+i);
                            isIssue=1;
                            break;
                        }
                    }
                }
                
                // 檢查目前將被Issue的指令是否可以準備進入下一階段(執行)
                // execution即為該指令被執行的開始時間
                if(Qj.equals("")&&Qk.equals("")||(opcode.equals("S.D")&&Qj.equals("")))
                    instruction.execution=clock;
                // 當有指令被Issue(isIssue=1)，即更新指令狀態並同時紀錄該指令被Issue週期
                if(isIssue==1){
                    instruction.issue=clock;
                    instructionList.set(cur_ins_position,instruction);
                    cur_ins_position++;
                }
            }
            /** Issue */
            
            // 檢查是否可以結束程式
            int flag=0;
            for(;flag<instructionList.size();flag++){
                Instruction ins=instructionList.get(flag);
                if(ins.issue!=0&&ins.executed!=0&&ins.written!=0)
                    continue;
                else
                    break;
            }
            // 印出每個週期狀態與寫檔
            showInfo();
            save();
            // 當flag等於指令行數表示所有指令都已執行與寫入完畢
            if(flag==instructionList.size())
                break;
            // if(clock==49)
            //     break;
            clock++;
        }
        writeOutput();
        System.out.println("End: "+clock);
        
    }
    /**
   * 讀檔將指令分解 opcode, rd, rs, rt
   * @param filename // 檔名 ex: test1.txt
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
        // L.D與S.D另外處理其餘依照字串切割放入指定暫存
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
      scn.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred (Read File).");
      e.printStackTrace();
    }
  }
  /**
   * 初始化Register Status (key: register name, value: function unit)
   */
  public static void init(){
    // 浮點數暫存器有16個，編號為F0、F2、F4、…、F30，初始值為1
    for(int i=0;i<=30;i+=2){
        fRegister.put("F"+i, "1");
    }
    // 整數暫存器有32個，編號為R0、R1、…、R31，除R1的初始值為16外，其餘整數暫存器初始值為0
    for(int i=0;i<=31;i++){
        if(i==1)
            iRegister.put("R"+i, "16");
        else
            iRegister.put("R"+i, "0");
    }
    // 初始化Reservation Station並建立資源
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
  /**
   * 在終端機中列印出每個週期的內容
   */
  public static void showInfo(){
    System.out.println("週期: "+clock);
    System.out.println("Instruction Status");
    System.out.printf("%-10s%-10s%-10s%-10s%-10s\n","指令類型","Issue","開始執行","執行結束","寫回");
    for(int i=0;i<instructionList.size();i++){
        Instruction ins=instructionList.get(i);
        System.out.printf("%-14s%-10s%-14s%-14s%-10s\n",ins.opcode,ins.issue,ins.execution,ins.executed,ins.written);
    }
    System.out.println("\n-----------------------------------------------------------------------");
    System.out.println("Reservation Station");
    System.out.printf("%-5s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n","Time","Name","OP","Vj","Vk","Qj","Qk","Busy");
    for(int i=0;i<addMount;i++){
        System.out.printf("%-5s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n",adder[i].remain,"Add"+(i+1),adder[i].opcode,adder[i].Vj,adder[i].Vk,adder[i].Qj,adder[i].Qk,adder[i].busy);
    }
    for(int i=0;i<mulMount;i++){
        System.out.printf("%-5s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n",multiplier[i].remain,"Mul"+(i+1),multiplier[i].opcode,multiplier[i].Vj,multiplier[i].Vk,multiplier[i].Qj,multiplier[i].Qk,multiplier[i].busy);
    }
    System.out.println("\n-----------------------------------------------------------------------");
    System.out.println("Register result status");
    for(int i=0;i<=30;i+=2)
        System.out.print("F"+i+":" + fRegister.get("F"+i)+" ");
    System.out.println("\n-----------------------------------------------------------------------");
    System.out.println("Load Buffer");
    System.out.printf("%-8s%-8s%-8s%-8s\n","Name","Busy","Vj","Qj");
    for(int i=0;i<loadMount;i++){
        System.out.printf("%-8s%-8s%-8s%-8s\n","Load"+(i+1),loadBuffer[i].busy,loadBuffer[i].Vj,loadBuffer[i].Qj);
    }
    System.out.println("\n-----------------------------------------------------------------------");
    System.out.println("Store Buffer");
    System.out.printf("%-8s%-8s%-8s%-8s\n","Name","Busy","Vj","Qj");
    for(int i=0;i<storeMount;i++){
        System.out.printf("%-8s%-8s%-8s%-8s\n","Store"+(i+1),storeBuffer[i].busy,storeBuffer[i].Vj,storeBuffer[i].Qj);
    }
    System.out.println("=======================================================================");
  }
  /**
   * 將每個週期內容儲存起來(寫檔用)
   */
  public static void save(){
    str+="週期: "+clock+"\n"+"Instruction Status\n"+String.format("%-10s%-10s%-10s%-10s%-10s\n","指令類型","Issue","開始執行","執行結束","寫回");
    for(int i=0;i<instructionList.size();i++){
        Instruction ins=instructionList.get(i);
        str+=String.format("%-13s%-10s%-13s%-12s%-10s\n",ins.opcode,ins.issue,ins.execution,ins.executed,ins.written);
    }
    str+="-----------------------------------------------------------------------\nReservation Station\n";
    str+=String.format("%-5s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n","Time","Name","OP","Vj","Vk","Qj","Qk","Busy");
    for(int i=0;i<addMount;i++){
        str+=String.format("%-5s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n",adder[i].remain,"Add"+(i+1),adder[i].opcode,adder[i].Vj,adder[i].Vk,adder[i].Qj,adder[i].Qk,adder[i].busy);
    }
    for(int i=0;i<mulMount;i++){
        str+=String.format("%-5s%-8s%-8s%-8s%-8s%-8s%-8s%-8s\n",multiplier[i].remain,"Mul"+(i+1),multiplier[i].opcode,multiplier[i].Vj,multiplier[i].Vk,multiplier[i].Qj,multiplier[i].Qk,multiplier[i].busy);
    }
    str+="-----------------------------------------------------------------------\nRegister result status\n";
    for(int i=0;i<=30;i+=2)
        str+="F"+i+":" + fRegister.get("F"+i)+" ";
    str+="\n-----------------------------------------------------------------------\nLoad Buffer\n";
    str+=String.format("%-8s%-8s%-8s%-8s\n","Name","Busy","Vj","Qj");
    for(int i=0;i<loadMount;i++){
        str+=String.format("%-8s%-8s%-8s%-8s\n","Load"+(i+1),loadBuffer[i].busy,loadBuffer[i].Vj,loadBuffer[i].Qj);
    }
    str+="-----------------------------------------------------------------------\nStore Buffer\n";
    str+=String.format("%-8s%-8s%-8s%-8s\n","Name","Busy","Vj","Qj");
    for(int i=0;i<storeMount;i++){
        str+=String.format("%-8s%-8s%-8s%-8s\n","Store"+(i+1),storeBuffer[i].busy,storeBuffer[i].Vj,storeBuffer[i].Qj);
    }
    str+="=======================================================================\n";
  }
  /**
   * 執行結束並寫檔匯出每個週期內容
   */
  public static void writeOutput(){
    try {
        FileWriter fw = new FileWriter("output.txt");
        fw.write(str);
        fw.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred (Write File).");
        e.printStackTrace();
      }
  }
}
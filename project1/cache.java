import java.io.*;
import java.util.*;

class Address {
  String HEX_address = "";
  int DEC_address = 0;
  public Address(String HEX_address, int DEC_address) {
    this.HEX_address = HEX_address;
    this.DEC_address = DEC_address;
  }
}

public class cache {
  public int cache_size=128; // Cache的大小，單位為KByte
  public int block_size = 8; // 每個Cache Block的大小，單位為Byte
  public int set_degree = 1; // 一個set中的cache block個數
  public int hitCount=0;
  public int missCount=0;

  public static ArrayList<Address> addressList = new ArrayList<>();
  public static void main(String[] args){

    readFile("example.txt");
    for(int i=0;i<addressList.size();i++){
      System.out.println(addressList.get(i).HEX_address+" "+addressList.get(i).DEC_address);
    }
    
  }
  /**
   * 讀檔將記憶體存入陣列中，同時轉成十進位
   * @param filename
   */
  public static void readFile(String filename){
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      Scanner scn = new Scanner(br);
      while (scn.hasNext()) {
        String HEX_address = scn.next();
        int DEC_address=Integer.parseInt(HEX_address.substring(2),16);
        addressList.add(new Address(HEX_address, DEC_address));
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
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

class CacheSet {
  Block[] blockArray;

  public CacheSet(int set_degree) {
    blockArray = new Block[set_degree];
    for (int i = 0; i < set_degree; i++) {
      blockArray[i] = new Block(0, 0);
    }
  }
}

class Block {
  int tag = 0;
  int valid = 0;

  public Block(int tag, int valid) {
    this.tag = tag;
    this.valid = valid;
  }
}

public class debug {
  public static int cache_size = 1024; // Cache的大小，單位為KByte
  public static int block_size = 8; // 每個Cache Block的大小，單位為Byte
  public static int set_degree = 1; // 一個set中的cache block個數
  public static int setSize = 0;
  public static int hitCount = 0;
  public static int missCount = 0;
  public static CacheSet[] setArray;

  public static ArrayList<Address> addressList = new ArrayList<>();

  public static void main(String[] args) {

    // 計算set的個數
    setSize = cache_size / (block_size * set_degree);
    // 初始化 setArray
    setArray = new CacheSet[setSize];
    for(int i=0;i<setSize;i++){
    setArray[i]=new CacheSet(set_degree);
    }

    readFile("example3.txt");
    for (int i = 0; i < addressList.size(); i++) {
      int position = addressList.get(i).DEC_address / block_size; // 取得記憶體位置
      int setIndex = position % setSize;
      int tag = position / setSize;
      // n-way nee loop
      for(int j=0;j< set_degree;j++){
        if (setArray[setIndex].blockArray[j].valid != 0 && setArray[setIndex].blockArray[j].tag == tag) {
          hitCount++;
        } else {
          setArray[setIndex].blockArray[j].valid = 1;
          setArray[setIndex].blockArray[j].tag = tag;
          missCount++;
        }
      }
      // System.out.println(addressList.get(i).HEX_address+" "+addressList.get(i).DEC_address);
    }
    System.out.println("Hit Count: " + hitCount);
    System.out.println("Miss Count: " + missCount);

  }

  /**
   * 讀檔將記憶體存入陣列中，同時轉成十進位
   * 
   * @param filename
   */
  public static void readFile(String filename) {
    try {
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      Scanner scn = new Scanner(br);
      while (scn.hasNext()) {
        String HEX_address = scn.next();
        int DEC_address = Integer.parseInt(HEX_address.substring(2), 16);
        addressList.add(new Address(HEX_address, DEC_address));
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
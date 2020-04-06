import java.io.*;
import java.util.*;

/**
 * 記憶體位置儲存 HEX_address(十六進位) DEC_address(十進位)
 */
class Address {
  String HEX_address = "";
  int DEC_address = 0;

  public Address(String HEX_address, int DEC_address) {
    this.HEX_address = HEX_address;
    this.DEC_address = DEC_address;
  }
}
/**
 * Cache Block 是 CPU Cache 中的最小快取單位。Cache Block 由 valid bit、tag 和 data
 * 組成(此專案無data故忽略) n-way說明一個set裡有n個line/block。
 */
class Block {
  int tag = 0;
  int valid = 0;

  public Block(int tag, int valid) {
    this.tag = tag;
    this.valid = valid;
  }
}
/**
 * Cache 由m個 set 组成，一個 set 中有n個 Cache Block。
 */
class CacheSet {
  Block[] blockArray;
  ArrayList<Integer> orderList = new ArrayList<>();

  public CacheSet(int set_degree) {
    blockArray = new Block[set_degree];
    for (int i = 0; i < set_degree; i++) {
      // 初始化每個Block
      blockArray[i] = new Block(0, 0);
      // 寫入順序初始化
      orderList.add(i);
    }
  }
}

public class cacheAssociative {
  public static int cache_size = 128; // Cache的大小，單位為KByte
  public static int block_size = 16; // 每個Cache Block的大小，單位為Byte
  public static int set_degree = 1; // 一個set中的Cache Block個數
  public static String fileName="trace.txt";
  public static int setSize = 0;
  public static int hitCount = 0;
  public static int missCount = 0;
  public static CacheSet[] setArray;

  public static ArrayList<Address> addressList = new ArrayList<>();

  public static void main(String[] args) {

    Scanner scn= new Scanner(System.in);
		if(args.length!=0){
			cache_size= Integer.parseInt(args[0]);
			block_size= Integer.parseInt(args[1]);
			set_degree= Integer.parseInt(args[2]);
			fileName=args[3];
		}else{
			System.out.print("請輸入Cache的大小，單位為KByte: "); 
			cache_size=Integer.parseInt(scn.nextLine());
			System.out.print("請輸入每個Cache Block的大小，單位為Byte: "); 
			block_size=Integer.parseInt(scn.nextLine());
			System.out.print("請輸入一個set中的Cache Block個數: "); 
			set_degree=Integer.parseInt(scn.nextLine());
      System.out.print("請輸入檔名(xxx.txt): ");
      fileName=scn.nextLine();
      System.out.println("---------------------------------------------------------------");
    }
    System.out.println("Cache Size: "+cache_size+" Block Size: "+block_size+" Way: "+set_degree+" "+fileName); 
    // Cach大小轉換
    cache_size=cache_size*1024;
    // 計算set的個數
    setSize = cache_size / (block_size * set_degree);
    // 初始化 setArray
    setArray = new CacheSet[setSize];
    for(int i=0;i<setSize;i++){
    setArray[i]=new CacheSet(set_degree);
    }
    readFile(fileName);
    for (int i = 0; i < addressList.size(); i++) {
      int position = addressList.get(i).DEC_address / block_size; // 取得記憶體位置
      int setIndex = position % setSize;
      int tag = position / setSize;
      // n-way nee loop
      int j=0;
      for(;j< set_degree;j++){
        if (setArray[setIndex].blockArray[j].valid != 0 && setArray[setIndex].blockArray[j].tag == tag) {
          // Set the recent order, and break the search loop
          final int indexInList = setArray[setIndex].orderList.indexOf(j);
          setArray[setIndex].orderList.remove(indexInList);
          setArray[setIndex].orderList.add(j);
          hitCount++;
          break;
        }
      }
      if(j==set_degree){
        int victim = setArray[setIndex].orderList.get(0);
        setArray[setIndex].blockArray[victim].valid = 1;
        setArray[setIndex].blockArray[victim].tag = tag;
        // Set the recent order
        setArray[setIndex].orderList.remove(0);
        setArray[setIndex].orderList.add(victim);
        missCount++;
      }
      // System.out.println(addressList.get(i).HEX_address+" "+addressList.get(i).DEC_address);
    }
    System.out.println("Hit Count: " + hitCount);
    System.out.println("Miss Count: " + missCount);
    System.out.printf("Miss Rate: %f\n", ((double)missCount/addressList.size()));
    scn.nextLine();
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
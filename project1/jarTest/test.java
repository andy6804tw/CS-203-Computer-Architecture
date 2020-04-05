import java.util.Scanner;

public class test{
	public static int cache_size = 128; // Cache的大小，單位為KByte
	public static int block_size = 16; // 每個Cache Block的大小，單位為Byte
	public static int set_degree = 1; // 一個set中的Cache Block個數
	public static String fileName="";
	public static void main(String[] args){
		Scanner scn= new Scanner(System.in);
		if(args.length!=0){
			cache_size= Integer.parseInt(args[0]);
			block_size= Integer.parseInt(args[1]);
			set_degree= Integer.parseInt(args[2]);
			fileName=args[3];
		}else{
			System.out.print("讀入Cache的大小，單位為KByte: "); 
			cache_size=Integer.parseInt(scn.nextLine());
			System.out.print("讀入每個Cache Block的大小，單位為Byte: "); 
			block_size=Integer.parseInt(scn.nextLine());
			System.out.print("讀入一個set中的Cache Block個數: "); 
			set_degree=Integer.parseInt(scn.nextLine());
			System.out.print("讀入檔名: "); 
			fileName=scn.nextLine();
		}
		
        System.out.println(cache_size+" "+block_size+" "+set_degree+" "+fileName); 
	}
}
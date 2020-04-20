import java.util.*;

/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

class pg_tbl{
	  
	public HashMap hm= new HashMap();
	//int entries[];
	//int valid[];
	//int no_entries;
	
/*	public pg_tbl(int i){
	   entries=new int[i];
	   valid=new int[i];
	   for(int j=i;j<i;j++)
	    valid[i]=0;
	   no_entries=i;
	  }
	*/
	
	
	public int check_entry(int va){
	   
		String s=Integer.toString(va);
		boolean b=hm.containsKey(s);
		int val;
		if(b)
			{
			val=((Integer)hm.get(s)).intValue();
			return val;
			}
		return -1;
		//if(valid[va]==1)return entries[va];
	   //return -1;
	   }
	  
	void add_entry(int va,int pa){
		String s=Integer.toString(va);
		hm.put(s,new Integer(pa));
		
	   //entries[va]=pa;
	   //valid[va]=1;
	  }
	
	void rem_entry(int va)
	{
		String s=Integer.toString(va);
		hm.remove(s);
		System.out.println("Removing PT entry :  Pg no. =  "+ va );
	}
	 	 
}
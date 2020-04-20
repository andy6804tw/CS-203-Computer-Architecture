/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

class main_mem
{
	public int no_frames;
	public int lru_ct[];
	public int pg_stored[];
	public int valid[];		// the page stored is valid or not
	int full;			// whether or not the main memory is full
	int top;
	
	main_mem(int i){
		
		pg_stored=new int [i];
		valid=new int[i];
		lru_ct=new int[i];
		for (int j=0;j<i;j++)
		{
			valid[j]=0;lru_ct[j]=0;
		}
		full=0;					//empty frames left in main memory
		top=0;
		no_frames=i;
	}
	
	int isfull()
	{
		if(full==1)return pg_stored[top];
		else return -1;
	}
	
	int isfull_lru()
	{
		if(full==0)return -1;
		int temp=0;
		for ( int i=0;i<no_frames;i++)
		{if(lru_ct[i]<lru_ct[temp])temp=i;}
		return pg_stored[temp];
	}
	
	void inc_count(int fr)
	{
		lru_ct[fr]++;
	}
	
	
	int add_entry(int pg_no, int replace){
		
		if(full==1 && replace==1)					// page replacement using FIFO
		{
			System.out.println("Replacing frame : " + top);
			pg_stored[top]=pg_no;
			valid[top]=1;
			lru_ct[top]++;
			int temp=top;
			top=(top+1)%(no_frames-1);
			return temp;
		}
		
		if(full==1 && replace==2)				// page replacement using LRU
		{
			top=0;
			for(int i=0;i<no_frames;i++)
			{if(lru_ct[i]<lru_ct[top])top=i;}
			System.out.println("Replacing frame : " + top);
			pg_stored[top]=pg_no;
			valid[top]=1;
			lru_ct[top]=1;
			return top;
			
		}
		
		else							// page placement
		{
			pg_stored[top]=pg_no;
			valid[top]=1;
			int temp=top;
			if(top==(no_frames-1))		// if all the frames are filled then set full=1
			{
				full=1;top=0;
				System.out.println("FFULLL");
			}
			else top=top+1;				// else increase the value of top
			
			lru_ct[temp]++;
			return temp;
		}
		
	}
	
	
	
	
	
}
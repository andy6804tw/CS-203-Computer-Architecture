/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

class cache
{
	public int no_entries;
	public int tag[];
	public int valid[];
	int pg_no[];
	boolean setAssoc;
	public int lru_ct[];
	public int top;
	int full;
	
	cache()
	{
		
	}
	
	cache(int i)
	{
		no_entries=i;
		top=0;
		full=0;
		setAssoc=true;
		tag= new int [i];
		valid=new int [i];
		pg_no=new int[i];
		lru_ct=new int[i];
		for(int j=0;j<i;j++)
		{
			valid[j]=0;
			lru_ct[j]=0;
		}
		
	}
	
	int isfull()
	{
		if(full==1)return top;
		else return -1;
	}
	
	int isconflict(int t)
	{
		int pos=t%no_entries;
		if(valid[pos]==1) return pos;
		else return -1;
	}
	
	int isfull_lru()
	{
		if(full==0) return -1;
		int temp=0;
		for(int i=0;i<no_entries;i++)
		{
			if(lru_ct[i]<lru_ct[temp])temp=i;
		}
		return temp;
	}
	
	void add_entry(int t, int a, int pg)
	{
		int blk_placed=0;		// cache location where the block is placed 
		
		if ( a==1 )				// direct mapped cache
		{
			int pos=(t%no_entries);
//			System.out.println("SSS" + pos+ "SS"+ t);
			if(valid[pos]==1){System.out.println("Replacing due to conflict " + pos);}
			tag[pos]=t;
			valid[pos]=1;
			pg_no[pos]=pg;
			blk_placed=pos;
		}
		
		else if ( a==4 )		// 2 way set associative with FIFO replacement
		{
			if(setAssoc)
			{
				int pos=(t%(no_entries/2));
				pos*=2;
				tag[pos]=t;
				valid[pos]=1;
				pg_no[pos]=pg;
				blk_placed=pos;
			}
			if(!setAssoc)
			{
				int pos=(t%(no_entries/2));
				pos*=2;
				pos++;
				tag[pos]=t;
				valid[pos]=1;
				pg_no[pos]=pg;
				blk_placed=pos;
			
			
			}
			setAssoc=!setAssoc;
		}
		
		
		
		else if ( a==5 )		// set associative with LRU replacement
		{
				int pos=(t%(no_entries/2));
				pos*=2;
				if(lru_ct[pos+1]<lru_ct[pos])pos++;
				tag[pos]=t;
				valid[pos]=1;
				pg_no[pos]=pg;
				blk_placed=pos;
				lru_ct[pos]=0;

		}
		
		else if(a==2)			// fully associative cache with FIFO replacement
			{
			if(full==1)
			{
				// replacement usig FIFO
				tag[top]=t;
				valid[top]=1;
				pg_no[top]=pg;
				blk_placed=top;
				top=(top+1)%(no_entries-1);
				
			}
			
			else
			{
			tag[top]=t;
			valid[top]=1;
			pg_no[top]=pg;
			blk_placed=top;
	//		System.out.println("ADDED tag " + t);
			if(top==(no_entries-1)){top=0;full=1;}
			else top++;
			}
			
			}
		
		else if(a==3)		// fully associative cache with LRU replacement
		{
			if(full==1)
			{
				// replacement usig LRU
				top=0;
				for(int i=0;i<no_entries;i++)
				{
					if(lru_ct[i]<lru_ct[top])top=i;
				}
				tag[top]=t;
				valid[top]=1;
				pg_no[top]=pg;
				lru_ct[top]=0;	
				blk_placed=top;
			}
			
			else
			{
			tag[top]=t;
			valid[top]=1;
			pg_no[top]=pg;
			blk_placed=top;
	//		System.out.println("ADDED tag " + t);
			if(top==(no_entries-1)){top=0;full=1;}
			else top++;
			}
			
			
		}
		
		System.out.println("Placed in L2 cache block: " + blk_placed );
	}
	
	void rem_entry(int t)
	{
		valid[t]=0;
	}
	
	int check_entry(int t)
	{
		for(int i=0;i<no_entries;i++)
		{
		//	System.out.println("CCCC" + tag[i] + " CCC " + valid[i]);
			if(tag[i]==t&&valid[i]==1){lru_ct[i]++;return 1;}
		}
		return -1;		
	}
	
	
	
	// if some page is replaced from main memory all the correspondin entries
	// in the cache are marked invalid
	void update(int pg)		
	{
		for(int i=0;i<no_entries;i++)
			if(pg_no[i]==pg){ valid[i]=0;lru_ct[i]=0;}
	}
	

}
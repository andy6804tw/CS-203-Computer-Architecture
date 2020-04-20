/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

class cache_l1_i extends cache
{
	public int l2_blk[];
	
	cache_l1_i(int a)
	{
		super(a);
		l2_blk= new int [a];
		for(int i=0;i<a;i++)l2_blk[i]=-1;
	}
	
	
	void add_entry(int t, int a, int pg, int l2_blk_no)
	{
		int blk_placed=0;
		
		if ( a==1 )				// direct mapped cache
		{
			int pos=(t%no_entries);
//			System.out.println("SSS" + pos+ "SS"+ t);
			tag[pos]=t;
			valid[pos]=1;
			pg_no[pos]=pg;
			l2_blk[pos]=l2_blk_no;
			blk_placed=pos;
		}
		
		else if ( a==4 )		// 2 way set associative
		{
			if(setAssoc)
			{
				int pos=(t%(no_entries/2));
				pos*=2;
				tag[pos]=t;
				valid[pos]=1;
				pg_no[pos]=pg;
				blk_placed=pos;
				l2_blk[pos]=l2_blk_no;
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
				l2_blk[pos]=l2_blk_no;
			
			
			}
			setAssoc=!setAssoc;
		}
		
		
		else if(a==2)			// fully associative cache with FIFO replacement
			{
			tag[top]=t;
			valid[top]=1;
			pg_no[top]=pg;
			l2_blk[top]=l2_blk_no;
			blk_placed=top;
//			System.out.println("ADDED tag " + t);
			//if(top==(no_entries-1)){top=0;full=1;}
			top=(top+1)%(no_entries-1);
	
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
				l2_blk[top]=l2_blk_no;
				blk_placed=top;
			}
			
			else
			{
			tag[top]=t;
			valid[top]=1;
			pg_no[top]=pg;
			l2_blk[top]=l2_blk_no;
			blk_placed=top;
	//		System.out.println("ADDED tag " + t);
			if(top==(no_entries-1)){top=0;full=1;}
			else top++;
			}
			
			
		}
		
		System.out.println("Placed in L1 cache block : " + blk_placed);
		
	}
	
	void update_l1(int l2_blk_no)
	{
//		System.out.println("UPDAATE");
		for(int i=0;i<no_entries;i++)
		{
			if(l2_blk[i]==l2_blk_no){valid[i]=0;lru_ct[i]=0;}
		}
	}
	
	
}
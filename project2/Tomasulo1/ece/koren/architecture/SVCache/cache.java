class cache
{
	public int no_entries;
	public int tag[];
	public int valid[];
	public int lru_ct[];
	int full=0;
	public int hit[];
	public int sticky[];
	
	cache(int i)
	{
		no_entries=i;
		full=0;
		tag=new int[i];
		valid=new int[i];
		lru_ct=new int[i];
		hit=new int[i];
		sticky = new int[i];
		
		for(int j=0;j<i;j++)
		{
			valid[j]=0;
			lru_ct[j]=-1;// A value of -1 indicates that the cache block is not valid
			hit[j]=0;
			sticky[j]=0;
		}
	}
	
	int direct_mapped_conflict(int t)
	{
		int pos=t%no_entries;
		if(valid[pos]==1) return tag[pos];
		else return -1;
	}
	
	int fully_associative_conflict()
	{
		if(full==0) return -1; //indicates no conflict as the cache is not full.
		int temp=0;
		for(int i=1;i<no_entries;i++)
		{
			if(lru_ct[i]>lru_ct[temp])temp=i;
		}
		return tag[temp];
	}
	
	int set_associative_conflict(int t,int a)
	{
		if ((a!=3)&&(a!=4)&&(a!=5)) {
			System.out.println("set_associative_conflict : map invalid");
		}
		
		int m;
		if(a==3)m=2;
		else if(a==4)m=4;
		else m=8;
		int pos=(t%(no_entries/m));
		pos*=m;
		int flag =0;
		for(int i=0;i<m;i++)
		{
			if(lru_ct[pos+i]== -1)
			{
				flag=1;
				break;
			}
		}
		if(flag==0)
		{
			int temp=pos;
			for(int i=1;i<m;i++)
			{
				if(lru_ct[pos+i]>lru_ct[temp]) temp=pos+i;
			}
			return tag[temp];
		}
		else
			return -1;
	}
	
	int check_Blk(int t, int map)
	{		
		// Direct mapped
		if (map == 1)
		{
			int pos = t % no_entries;
			if ((tag[pos]==t)&&(valid[pos]==1)) 
				return 1;
			else 
				return -1;
		}
		
		// Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;
			
		int flag=0;
		for(int i=0;i<m;i++)
		{
			if(tag[pos+i]==t && valid[pos+i]==1) // the block is present and increment its lru count
			{
				lru_ct[pos+i]=0;
				flag=1;
				break;
			}			
		}
		if(flag==1)
		{
			for(int i=0;i<m;i++)
			{
				if(tag[pos+i]!= t && valid[pos+i]==1)
					lru_ct[pos+i]++;
			}
			return 1;
		}
		else 
			return -1;
	}
	
	void add_Blk(int t,int map)
	{
		if(map==1) // directly mapped
		{
			int pos=t%no_entries;
			tag[pos]=t;
			valid[pos]=1;
			return;
		}
		
		// Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;

		// Add the block
		int flag =0, addposn = pos;
		for(int i=0;i<m;i++)
		{
			if(lru_ct[pos+i]== -1)
			{
				addposn = pos+i;
				flag =1;
				break;
			}
		}
		if(flag==0)
		{
			for(int i=1; i<m; i++)
			{
				if (lru_ct[pos+i]>lru_ct[addposn]) addposn = pos+i;
			}
		}
		tag[addposn] = t;
		valid[addposn] = 1;
		lru_ct[addposn] = 0;
		
		// Increment lru for all other valid blocks
		for(int i=0; i<m; i++)
		{
			if ((tag[pos+i]!=t)&&(valid[pos+i]==1))
				lru_ct[pos+i]++;
		}
	}
	
	void rem_Blk(int t, int map)
	{
		// Direct mapped
		if (map == 1)
		{
			int pos = t % no_entries;
			if (tag[pos]==t)
			{
				valid[pos] = 0;
				lru_ct[pos] = -1;
			}
			return;
		}
		
		// Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;
		
		for(int i=0; i<m; i++)
		{
			if ((tag[pos+i]==t)&&(valid[pos+i]==1))
			{
				valid[pos+i] = 0;
				lru_ct[pos+i] = -1;
				break;
			}
		}
	}
	
	void set_Hit(int t,int map,int value)
	{
		if(map==1) // directly mapped
		{
			int pos=t%no_entries;
			hit[pos]=value;
			return;
		}
		
        //Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;
		
		for(int i=0;i<m;i++)
		{
			if(tag[pos+i]==t && valid[pos+i]==1)
			{
				hit[pos+i]=value;
				break;
			}			
		}

	}
	
	void set_Sticky(int t,int map,int value)
	{
		if(map==1) // directly mapped
		{
			int pos=t%no_entries;
			sticky[pos]=value;
			return;
		}
		
        //Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;
		
		for(int i=0;i<m;i++)
		{
			if(tag[pos+i]==t && valid[pos+i]==1)
			{
				sticky[pos+i]=value;
				break;
			}			
		}

	}
	
	int get_Hit(int t,int map)
	{
		int r;
		if(map==1) // directly mapped
		{
			int pos=t%no_entries;
			r=hit[pos];
			return r;
		}
		
        //Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;
		
		for(int i=0;i<m;i++)
		{
			if(tag[pos+i]==t && valid[pos+i]==1)
			{
				r=hit[pos+i];
				return r;
			}
		}
		return 1;
	}
	
	int get_Sticky(int t,int map)
	{
		int r;
		if(map==1) // directly mapped
		{
			int pos=t%no_entries;
			r=sticky[pos];
			return r;
		}
		
        //Associative mapping
		int m;
		if (map == 3) m = 2;      // 2-way associative
		else if (map == 4) m = 4; // 4-way associative
		else if (map == 5) m = 8; // 8-way associative
		else m = no_entries;      // Fully associative		
		int pos = t%(no_entries/m);
		pos *= m;
		
		for(int i=0;i<m;i++)
		{
			if(tag[pos+i]==t && valid[pos+i]==1)
			{
				r=sticky[pos+i];
				return r;
			}
			
		}
		return 1;
	}
}
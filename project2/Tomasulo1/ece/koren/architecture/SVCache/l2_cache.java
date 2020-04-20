class l2_cache extends cache
{
	l2_cache(int a)
	{
		super(a);//this indicates the number of entries in cache
	}
	
	int just_check(int t,int map)
	{
        //Direct mapped
		if (map == 1)
		{
			int pos = t % no_entries;
			if ((tag[pos]==t)&&(valid[pos]==1)) 
				return 1;
			else 
				return -1;
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
				return 1;
			}			
		}
		return -1;
	}
}
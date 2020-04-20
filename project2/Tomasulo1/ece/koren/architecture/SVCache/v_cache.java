class v_cache extends cache
{
	v_cache(int a)
	{
		super(a); //this indicates the # of entries in cache
	}
		
	int check_Blk(int t)
	{
		return super.check_Blk(t, 2);//Since victim cache is fully associative
	}								 //a value of 2 is hardcoded as cache mapping	
	
	void add_Blk(int t)
	{
		super.add_Blk(t, 2);
	}	

	void rem_Blk(int t)
	{
		super.rem_Blk(t, 2);
	}	
}
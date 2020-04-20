/**
 * @author Rakesh Kothari
 * @author Siddhartha Bunga
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

 class tlb{
 	  public int pg_no[];
 	  public int frame_no[];
 	  int valid[];
 	  public int no_entries;
 	  public int top;
 	  
 	 tlb(int i){

 	  pg_no=new int[i];
 	  frame_no=new int[i];
 	  valid=new int[i];
 	  for(int j=0;j<i;j++)
 	   valid[j]=0;
 	  top=0;
 	  no_entries=i;
 	 }
 	 
 	 int check_entry(int pg){
 	  for(int i=0;i<no_entries;i++)
 	   if(pg_no[i]==pg && valid[i]==1)
 	    return frame_no[i];
 	   
 	   return -1;
 	  }
 	 
 	 void add_entry(int pg,int fr){
 	  pg_no[top]=pg;
 	  frame_no[top]=fr;
 	  valid[top]=1;
 	  top=(top+1)%(no_entries);
 	 }
 	 
 	 void rem_entry(int pg)
 	 {
 	 	for(int i=0;i<no_entries;i++)
 	 	   if(pg_no[i]==pg && valid[i]==1)valid[i]=0;
 	 	  	
 	 }
 	 
 	 
 	 }
 	 
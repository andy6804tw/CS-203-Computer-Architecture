
public class CARfile extends DList
{

public DList T1= new DList(0);
public DList B1=new DList(1);
public DList B2= new DList(3);
public DList T2= new DList(2);
public int p;  //T1 target size
public int c;  //number of pages
protected int hits;  //number of hits
protected int csm;  //number of cold start misses
private boolean nmiss; //true if no misses occured(not including csm's)

    public CARfile(int pgs)
    {
      hits=0;
      csm=0;
      nmiss=true;
      p=0;
      c=pgs;
    }
private DNode locate1(String q)  
{

    if (T1.search(q)!=null)
      return T1.search(q);
    else if (T2.search(q)!=null)
      return T2.search(q);
    else
       return null;
}
private DNode locate2(String q)  
{

    if (B1.search(q)!=null)
      return B1.search(q);  
    else if (B2.search(q)!=null)
      return B2.search(q); 
    else
       return null;
}

private int max(int x, int y)
{
    if (x >= y)
	return x;
    return y;
}
private int min(int x, int y)
{
    if (x <= y)
	return x;
    return y;
}

public String[] CAR(String x)
{//INPUT: The requested page x.
  DNode temp,temp2;

  temp= locate1(x);   //checks T1and T2 for item
  temp2= locate2(x);  //checks B1 and B2 for item 
  String[] col= new String[c+1];
  if(temp!=null)  /* cache hit */
     {  temp.rbit=1;          //2: Set the page reference bit for x to one.
	col[c]="Hit";
	hits++;

     }
  else /* cache miss */
  {
     if (T1.count + T2.count == c) 
      {  /* cache full, replace a page from cache */
	 replace();
	/* cache directory replacement */

	nmiss=false; //dont with cold start misses
	col[c]="Miss";
	 
	if((temp2==null) && (T1.count + B1.count == c))
	    { if(B1.count>1) 
		 B1.removeLast();    //Discard the LRU page in B1.
	      else if(B1.count>0)
		  B1.Remove(null);
	      else
		 T1.removeLast();
	    }
	else if ((T1.count + T2.count + B1.count + B2.count == 2*c) && (temp2==null))
	    B2.removeLast();    //Discard the LRU page in B2.
      }
	/* cache directory miss */
     if (temp2==null)
	{ if(T1.count >0 )
	     T1.insertTail(x,0);//Insert x at the tail of T1. Set the page reference bit of x to 0.
	  else
	     T1.Insert(x);
	  if(nmiss==true)
	    { col[c]="Cold Start Miss";
	      csm++;}
	  else
	    col[c]="Miss";
	}
     else if (temp2.ARC_wher == 1)     //x is in B1)/* cache directory hit */
     {   col[c]="Phantom Hit";
	 p = min (p + max(1, B2.count/B1.count), c);//Adapt: Increase the target size for the list T1 as: p = min {p + max{1, |B2|/|B1|}, c}
	 T2.insertTail(x,0);   //Move x at the tail of T2. Set the page reference bit of x to 0.
     }
    /* cache directory hit */
     else /* x must be in B2 */
	{  col[c]="Phantom Hit";
	   p = max(p - max(1, B1.count/B2.count), 0);// Adapt: Decrease the target size for the list T1 as: p = max {p . max{1, |B1|/|B2|}, 0}
	   T2.insertTail(x,0); //Move x at the tail of T2. Set the page reference bit of x to 0.
	}
  }
   String[] tone= T1.printString();
    String[] ttwo= T2.printString();
    int tnum=0;
    for(int i=0;i<c;i++)
     { if(i<T1.count)
	 { if(i<tone.length)
	    col[i]=tone[i];
	   else
	      col[i]="";
	  }
	else
	{ if(tnum<ttwo.length)
	     col[i]=ttwo[tnum]; 
	  else
	    col[i]="";
	  tnum++;
	}
      }  
     return col;  
}


public void replace()
{
    int found = 0;
    do{
     if(T1.count >= p && T1.count > 0) 
      {  if(T1.head.rbit ==0)//if the page reference bit of head page in T1 is 0) then
	    { found = 1;
	      Object t=T1.Remove(null);      //Demote the head page in T1 and make it the MRU page in B1.
	      B1.Insert(t);
	    }
	 else
	    { Object t=T1.Remove(null);   // Set the page reference bit of head page in T1 to 0, and make it the tail page in T2.
	      if(T2.count >0)
		T2.insertTail(t,0);
	      else
		T2.Insert(t);
	    }
      }
     else
	{ if (T2.head.rbit==0)    //the page reference bit of head page in T2 is 0), then
	   { found = 1;
	     Object t= T2.Remove(null);   //Demote the head page in T2 and make it the MRU page in B2.
	     B2.Insert(t);
	   }
	else
	   { Object t=T2.Remove(null);    //Set the page reference bit of head page in T2 to 0, and make it the tail page in T2.
	     if(T2.count >0)
		T2.insertTail(t,0);
	     else
		T2.Insert(t);
	   }
	}
    }while(found==0);
}
public static void main(String[] arg)
    {}
}

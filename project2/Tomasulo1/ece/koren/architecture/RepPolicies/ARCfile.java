
public class ARCfile extends DList
{
//*****************ARC**********************
public DList T1=new DList(0);
public DList T2=new DList(2);
public DList B1=new DList(1);
public DList B2=new DList(3);
public int pgs;
public int T1Length, T2Length,B1Length,B2Length;
protected int hits; //number of hits
protected int csm; //number of cold start misses
private boolean nmiss; //true if no hits have occured (not includeing csms)

public ARCfile (int n)
   {pgs=n;
    T2Length=pgs/2;
    T1Length=pgs-T2Length;
    B2Length=pgs/2;
    B1Length=pgs-B2Length;
    hits=0;
    csm=0;
    nmiss=true;
   }
private DNode locate(String p)
{

    if (T1.search(p)!=null)
      return T1.search(p);
    else if (T2.search(p)!=null)
      return T2.search(p);
    else if (B1.search(p)!=null)
      return B1.search(p);
    else if (B2.search(p)!=null)
      return B2.search(p);
    else
       return null;
}
public String[] ARC (String page)
{
    String[] col= new String[pgs+1];
    DNode temp;
    Object temp2;
    temp = locate (page); /* find the requested page */

    if (temp != null)
    { /* found in cache directory? */
	switch (temp.ARC_wher)
	{ /* yes, which list? */
	    case 0://T1:
	       T2.Insert(temp.value);
	       T2Length++;
	       T1Length--;
		if (T2.count > T2Length)           //>=
		   { temp2=T2.removeLast();
		     B2.Insert(temp2);
		     if(B2.count > B2Length)       //>=
			B2.removeLast();
		    }
		T1.Remove(temp);
		if (B1.count>0)
		   T1.insertTail(B1.Remove(null));
		col[pgs]="Hit";
		hits++;

		break;
		/* fall through */
	    case 2: //_T2_:
		temp2=T2.Remove(temp);
		T2.Insert(temp2);
		col[pgs]="Hit";
		hits++;

		break;
	    case 1:  //_B1_:
		       /* B1 hit: favor recency */
		    //target_T1 = min (target_T1 + max (B2Length / B1Length, 1), c);

		    T1Length++;
		    T2Length--;
		    B1.Remove(temp);
		    col[pgs]="Phantom Hit";
		    /* adapt the target size */
		    T2.Insert(temp.value);
		    if (T2.count > T2Length)   //adds node found to T2 and makes necessary changes
		    {  temp2=T2.removeLast();   // due to space limitation in lists
		       B2.Insert(temp2);
		       if(B2.count > B2Length)
			     B2.removeLast();
			   T2Length--;
		    }
		    if(B1.count>0)
		     { temp2=B1.Remove(null);
		       T1.insertTail(temp2);  //increments length of T1 to include head of B1 if available
		     }
		     break;
	    case 3:  //_B2_:
		    /* B2 hit: favor frequency */
		    T1Length--;
		    T2Length++;
		    col[pgs]="Phantom Hit";

		    /* adapt the target size */
		    T2.Insert(temp.value);
		    B2.Remove(temp);
		    if (T2.count != T2Length && B2.count > 0)   //checks if T2 is full now
		    {                           // if not and B2 has values brigns one up
		       temp2=B2.Remove(null);
		       T2.insertTail(temp2);
		    }
		    if(T1.count > T1Length && T1.count != 0)  //checks if T1  now contains too many elements
		     { temp2=T1.removeLast(); //if so removes last one and places it in B1
		       B1.Insert(temp2);
		       if (B1.count > B1Length)  //now check if B1 exceeds its n umber of elements
			  B1.removeLast();      //if so removes last item so size is correct
		     }
		    else if (T1.count == 0 && T1Length < 0)
		        T1Length=0;
		    break;
	}
    }


    else
    { /* page is not in cache directory */

	if(B1.count==0 && B2.count==0 && T2.count==0 && nmiss==true)
	   {col[pgs]="Cold Start Miss";
	    csm++;
	   }
	else
	  {
	   col[pgs]="Miss";
	   nmiss=false;
	   }
	T1.Insert(page);
	if(T1.count > T1Length)  //checks if T1  now contains too many elements
	   { temp2=T1.removeLast(); //if so removes last one and places it in B1
	     B1.Insert(temp2);
	     if (B1.count > B1Length)  //now check if B1 exceeds its n umber of elements
		B1.removeLast();      //if so removes last item so size is correct
	   }
    }
    String[] tone= T1.printString();
    String[] ttwo= T2.printString();
    int tnum=0;
    for(int i=0;i<pgs;i++)
     { if(i<T1Length)
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

public static void main (String[] arg)
{}
}

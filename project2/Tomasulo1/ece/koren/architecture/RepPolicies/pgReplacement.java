//Nicholas Merrill
//12-28-2005
import java.util.Random;
import java.util.Vector;
public class pgReplacement extends GUI
{

    private int pgs; //nu number of pages
    private Vector ref; //references made in order
    private int nseq;
    private int ccount; //number of check boxes checked
    protected int[] fifos= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] ilrus= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] ilfus= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] randoms= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] opts= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] lruks= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] arcs= new int[2];  //contains [0] number of hits and [1]=cold start misses
    protected int[] cars= new int[2];  //contains [0] number of hits and [1]=cold start misses

    public pgReplacement(int p, int n, Vector r)
    {

     pgs= p;
     nseq= n;
     ccount= super.pcount;
     ref=r;
    }
    private int Search(String sid, Vector v)
    {

	for(int i=0;i<v.size();i++)
	  if( ((String)v.elementAt(i)).equals(sid) )
		return i;
	return -1;
    }
    public String[][] fifo()
    {
	int hits=0;
	int csm=0;

	String hit="Hit";
	String miss="Miss";
	String[][] fifov= new String[pgs+1][nseq];
	int position=0;
	int temp=0;
	for(int c=0;c<nseq;c++)
	{
	  int blank=-1;
	  int h=hits;
	  temp=c;
	  if(c!=0)
	    temp--;
	  for(int i=0;i<pgs;i++)
	    {
	     if((fifov[i][temp]==null) && blank==-1)
		blank=i;
	     else if(fifov[i][temp]!=null &&  (fifov[i][temp]).equals(String.valueOf(ref.elementAt(c))))
	      {  hits++;
		 fifov[pgs][c]=hit;
		 fifov[i][c]=fifov[i][temp];
	      }

	      else if(c!=0)
		   fifov[i][c]=fifov[i][temp];
	      if (i==pgs-1&& h==hits)
	      {
		  if(blank==-1)
		  {
		    fifov[position][c]=(String)ref.elementAt(c);
		    if(position == pgs-1)
		      position=0;
		    else
		      position++;
		    fifov[pgs][c]=miss;
		    break;
		  }
		  else
		  {
		   fifov[blank][c]=(String)ref.elementAt(c);
		   if(position == pgs-1)
		      position=0;
		    else
		      position++;
		    fifov[pgs][c]="Cold Start Miss";
		    csm++;
		    break;
		  }
	      }
	    }
	}

	fifos[0]=hits;
	fifos[1]=csm;
	return fifov;
    }
//********************RANDOM*******************************
    public String[][] random()
    {
	int hits=0;
	int csm=0;

	String hit="Hit";
	String miss="Miss";
	String[][] random= new String[pgs+1][nseq];
	int temp=0;
	Random r= new Random();
	for(int c=0;c<nseq;c++)
	{
	  int blank=-1;
	  int h=hits;
	  temp=c;
	  if(c!=0)
	    temp--;
	  for(int i=0;i<pgs;i++)
	    {
	     if((random[i][temp]==null) && blank==-1)
		blank=i;
	     else if(random[i][temp]!=null &&  (random[i][temp]).equals(String.valueOf(ref.elementAt(c))))
	      {  hits++;
		 random[pgs][c]=hit;
		 random[i][c]=random[i][temp];
	      }
	     else if(c!=0)
		   random[i][c]=random[i][temp];

	      if (i==pgs-1&& h==hits)
	      {
		  if(blank==-1)
		  {
		    random[r.nextInt(pgs)][c]=(String)ref.elementAt(c);
		    random[pgs][c]=miss;
		    break;
		  }
		  else
		  {
		   random[blank][c]=(String)ref.elementAt(c);
		     random[pgs][c]="Cold Start Miss";
		     csm++;
		    break;
		  }
	      }
	    }
	}
	randoms[0]=hits;
	randoms[1]=csm;
	return random;
    }

//***********************iLRU*******************************************
    public String[][] iLRU()
    {
     int hits=0;
     int csm=0;
	String hit="Hit";
	String miss="Miss";
	String[][] lru= new String[pgs+1][nseq];
	int[] used= new int[pgs];
	for (int i=0; i<pgs;i++)
	  used[i]=-1;  // initialize array to all 0's
	int temp=0;
	for(int c=0;c<nseq;c++)
	{
	  int blank=-1;
	  int h=hits;
	  temp=c;
	  if(c!=0)
	    temp--;
	  for(int i=0;i<pgs;i++)
	    {
	     if((lru[i][temp]==null) && blank==-1)
		blank=i;
	     else if(lru[i][temp]!=null &&  (lru[i][temp]).equals(String.valueOf(ref.elementAt(c))))
	      {  hits++;
		 lru[pgs][c]=hit;
		 lru[i][c]=lru[i][temp];
		 used[i]=0;
		 for(int j=0;j<i;j++)
		       used[j]++;  //increments pages prior to the foudn hit
	      }
	     else if(c!=0)
	       {
		 lru[i][c]=lru[i][temp];
		  if(h!=hits || (blank!=-1 && lru[i][temp]!=null) )
		     used[i]++;  //increments pages after hit
	       }

	      if (i==pgs-1&& h==hits)
	      {
		  if(blank==-1)
		  {
		    int t=0;
		    for(int j=0;j<pgs;j++)
		    { if(used[j]>=used[t])
		      { if(j!=0)
			  used[t]++; //incremenet when not least recently used
			 t=j;     //finds least recentlty used page
		      }
		      else
			 used[j]++;  //incremenets if not recently used
		    }

		    lru[t][c]=(String)ref.elementAt(c);
		    lru[pgs][c]=miss;
		    used[t]=0;
		    break;
		  }
		  else
		  {
		   lru[blank][c]=(String)ref.elementAt(c);
		     lru[pgs][c]="Cold Start Miss";
		     csm++;
		     for(int j=0;j<=blank;j++)
			  used[j]++;
		    break;
		  }
	      }
	    }
	}

	ilrus[0]=hits;
	ilrus[1]=csm;
	return lru;
    }

//*******************iLFU************************************************************
    public String[][] iLFU()
    {
     int hits=0;
     int csm=0;
	String hit="Hit";
	String miss="Miss";
	String[][] lfu= new String[pgs+1][nseq];
	Vector freq= new Vector(); //vector contaiing the number of times each component was accessed
	Vector name= new Vector(); //vector contained the names for the frequency vector

	int temp=0;

	for(int c=0;c<nseq;c++)
	{
	  int blank=-1;
	  int h=hits;
	  temp=c;
	  if(c!=0)
	    temp--;
	  for(int i=0;i<pgs;i++)
	    {
	     if((lfu[i][temp]==null) && blank==-1)
		    blank=i;
	     else if(lfu[i][temp]!=null &&  (lfu[i][temp]).equals(String.valueOf(ref.elementAt(c))))
	      {  hits++;
	    	 lfu[pgs][c]=hit;
		     lfu[i][c]=lfu[i][temp];
		     int s=Search((String)ref.elementAt(c),name);
	    	   if(s==-1)
		         {freq.add(new Integer(0));     //adds frequency if not in vector
		       name.add(ref.elementAt(c)); // add name for freq
		        }
	    	   else
	    	   {
		      freq.set(s,new Integer(((Integer)freq.elementAt(s)).intValue()+1)); //increments item if in vector for frequency
		   }
	      }
	     else if(c!=0)
	       lfu[i][c]=lfu[i][temp];

	      if (i==pgs-1&& h==hits)
	      {
		  if(blank==-1)
		  {
		   int t=0;
		   int temp3=0;
		    for(int j=0;j<pgs;j++)
		    {
		     int f = Search(lfu[j][temp],name);
		     if(((((Integer)freq.elementAt(t)).compareTo(((Integer)freq.elementAt(f)))))>0)
			   { t=f;      //finds least frequentlt used out of ones in caches at present moment
			     temp3=j;
		   	   }
		    }
		   int temp2=Search((String)ref.elementAt(c),name);
		   if(temp2==-1)
		     {name.add(ref.elementAt(c));
		      freq.add(new Integer(0));
		     }
		    else
			   freq.set(temp2,new Integer(((Integer)freq.elementAt(temp2)).intValue()+1)); //increments item if in vector for frequency

		   lfu[temp3][c]=(String)ref.elementAt(c);
		   lfu[pgs][c]=miss;
		    break;
		  }
		  else
		  {
		   lfu[blank][c]=(String)ref.elementAt(c);
		   lfu[pgs][c]="Cold Start Miss";
		   csm++;
		   int f= Search((String)ref.elementAt(c),name);
		   if(f==-1)
		     {name.add(ref.elementAt(c));
		      freq.add(new Integer(0));
		     }
		   else
		      freq.set(f,new Integer(((Integer)freq.elementAt(f)).intValue()+1)); //increments item if in vector for frequency
		    break;
		  }
	      }
	    }
	}
	ilfus[0]=hits;
	ilfus[1]=csm;
	return lfu;
    }

//***********OPTIMAL****************************************
    public String[][] OPT()
    {
     int hits=0;
     int csm=0;
	String hit="Hit";
	String miss="Miss";
	String[][] opt= new String[pgs+1][nseq];
	int temp=0;

	for(int c=0;c<nseq;c++)
	{
	  int blank=-1;
	  int h=hits;
	  temp=c;
	  if(c!=0)
	    temp--;
	  for(int i=0;i<pgs;i++)
	    {
	     if((opt[i][temp]==null) && blank==-1)
		blank=i;
	     else if(opt[i][temp]!=null &&  (opt[i][temp]).equals(String.valueOf(ref.elementAt(c))))
	      {  hits++;
		 opt[pgs][c]=hit;
		 opt[i][c]=opt[i][temp];
	      }
	     else if(c!=0)
	       opt[i][c]=opt[i][temp];

	      if (i==pgs-1&& h==hits)
	      {
		  if(blank==-1)
		  {
		   int t=-1;
		   int val=0;
		   for(int j=0;j<pgs;j++)
		    {
		      int r=ref.indexOf(opt[j][temp],c);
		      if(r==-1 && val!=-1)
		      {   t=j;  //if none of current pages are anywhere then put in position 0
			  val=r;
		      }
		      else if(val!=-1 && r >=val)
			 {  val=ref.indexOf(opt[j][temp],c);
			    t=j;  //else find farthest spot to replace
			 }
		      }
		   opt[t][c]=(String)ref.elementAt(c);
		   opt[pgs][c]=miss;
		    break;
		  }
		  else
		  {
		   opt[blank][c]=(String)ref.elementAt(c);
		   opt[pgs][c]="Cold Start Miss";
		   csm++;
		    break;
		  }
	      }
	    }
	}
	opts[0]=hits;
	opts[1]=csm;
	return opt;
    }
//********************************ARC*************************************
    public String[][] ARC()
    {
      String[][] result= new String[pgs+1][nseq];
      String temp[];
      ARCfile ar= new ARCfile(pgs);

      for(int i=0;i<nseq;i++)
      {  temp=ar.ARC((String)ref.elementAt(i));
	for(int r=0;r<pgs+1;r++)
	   result[r][i]=temp[r];
      }

      arcs[0]=ar.hits;
      arcs[1]=ar.csm;
      return result;


    }
//*****************CAR**********************************
    public String[][] CAR()
    {
      String[][] result= new String[pgs+1][nseq];
      String temp[];
      CARfile cr= new CARfile(pgs);

      for(int i=0;i<nseq;i++)
      {  temp=cr.CAR((String)ref.elementAt(i));
	for(int r=0;r<pgs+1;r++)
	   result[r][i]=temp[r];
      }
      cars[0]=cr.hits;
      cars[1]=cr.csm;
      return result;
    }
//******************************LRU-K**********************************************
    public String[][] LRUk(int k)
    {
    int hits=0;
    int csm=0;
	int cnt=0;
	String hit="Hit";
	String miss="Miss";
	String[][] lruk= new String[pgs+1][nseq];
	int[] used= new int[pgs];
	for (int i=0; i<pgs;i++)
	  used[i]=-1;  // initialize array to all 0's
	int temp=0;
	for(int c=0;c<nseq;c++)  //columns(i.e references)
	{
	  int blank=-1;
	  int h=hits;
	  temp=c;
	  if(c!=0)
	    temp--;
	  for(int i=0;i<pgs;i++)  //checks through row (i.e. one reference)
	    {
	     if((lruk[i][temp]==null) && blank==-1)
		blank=i;
	     else if(lruk[i][temp]!=null &&  (lruk[i][temp]).equals(String.valueOf(ref.elementAt(c))))
	      {  hits++;
		 lruk[pgs][c]=hit;
		 lruk[i][c]=lruk[i][temp];
		 used[i]=0;
		 for(int j=0;j<i;j++)
		   if(used[j]!=k)
		    used[j]++;  //increments pages prior to the foudn hit up to K
	      }
	     else if(c!=0)
	       {
		 lruk[i][c]=lruk[i][temp];
		  if(h!=hits || (blank!=-1 && lruk[i][temp]!=null) )
		     if(used[i]!=k)
			used[i]++;  //increments pages after hit if not at K
	       }

	      if (i==pgs-1&& h==hits)
	      {
		  if(blank==-1)
		  {
		    int t=0;
		    int tcnt=0; // used to tell how many values = least recently used
		    for(int j=0;j<pgs;j++)
		    {
		      if(used[j]>=used[t])
		      { if(j!=0)
			  if(used[t]!=k)
			     used[t]++; //incremenet when not least recently used if < K

			 if(used[j]==used[t] && cnt==tcnt)
			   { t=j;     //sets t= next least recently used if multiple with same value
			     tcnt++;
			   }
			 else if(used[j]==used[t] && cnt!=tcnt)
			     tcnt++;   // used to tell how many values = least recently used
			 else
			     t=j;     //finds least recentlty used page
		      }
		      else
			if(used[j]!=k)
			    used[j]++;  //incremenets if not recently used
		    }
		    if(cnt < pgs)  //increments cnt so that if multiple values are equal recently used
			 cnt++;    // it steps through these positions so same page isnt always replaced
		     else
			cnt=0;
		    lruk[t][c]=(String)ref.elementAt(c);
		    lruk[pgs][c]=miss;
		    used[t]=0;
		    break;
		  }
		  else
		  {
		   lruk[blank][c]=(String)ref.elementAt(c);
		     lruk[pgs][c]="Cold Start Miss";
		     csm++;
		     for(int j=0;j<=blank;j++)
			if(used[j]!=k)
			   used[j]++;
		    break;
		  }
	      }
	    }

	}
	lruks[0]=hits;
	lruks[1]=csm;
	return lruk;
    }
    public static void main (String[] arg)
    {}










}

import java.awt.List;
public class DList extends List
{
    public int ARC_where;  //0=T1,1=B1,2=T2,3=B2
    public class DNode{     //acts as block in cache
	private DNode next, prev;
	protected Object value;
	public int ARC_wher;
	public int rbit;  //used only for CAR

	public DNode(DNode p, DNode n, Object v){
	    prev = p;
	    next = n;
	    value = v;
	    ARC_wher= ARC_where;
	    rbit=0; //used for CAR
	}
    }

    protected DNode head, tail;
    protected int count;
 //   public int ARC_where;  //0=T1,1=B1,2=T2,3=B2
    DList(){
	count = 0;
	DNode hNode = new DNode (null, null, null);
	DNode tNode = new DNode (hNode, null, null);
	hNode.next = tNode;
	head = hNode;
	tail = tNode;
      }
     DList(int n){
	count = 0;
	DNode hNode = new DNode (null, null, null);
	DNode tNode = new DNode (hNode, null, null);
	hNode.next = tNode;
	head = hNode;
	tail = tNode;
	ARC_where=n;
    }

    public void Insert(DNode p, Object item)
    {
	DNode newNode = new DNode(p, p.next, item);
	p.next = newNode;
	newNode.next.prev = newNode;
	count ++;
    }
    public void Insert(Object item) //inserts new node at head
    {
	DNode newNode = new DNode(null, head, item);
	head.prev = newNode;
	head = newNode;
	count ++;


    }

    public void insertTail(Object item)
    {
      DNode n = head;
      for(int i=0; i < count-1;i++)
	      n=n.next;
      DNode newNode = new DNode(n, null, item);
      n.next = newNode;
      count++;
     }

    public void insertTail(Object item, int refbit)
    {
      DNode n = head;
      for(int i=0; i < count-1;i++)
	      n=n.next;
      DNode newNode = new DNode(n, null, item);
      newNode.rbit=refbit;
      n.next = newNode;
      count++;
     }


    public Object Remove(DNode p)
    {
	if (p==null)
	{
	  Object item = head.value;
	  if(head.next==null)
	     head= new DNode(null,null,null);
	  else
	  {
	    head.next.prev=null;
	    head=head.next;
	  }
	  count--;
	  return item;
	}
	else{
	  Object item = p.value;
	  if(p.next!=null && p.prev !=null)
	    {p.next.prev = p.prev;
	     p.prev.next = p.next;
	    }
	  else if(p.next!=null && p.prev ==null)
	  {   p.next.prev=null;
	      head=p.next;
	  }
	  else if(p.next ==null && p.prev !=null)
	      p.prev.next=null;
	  else
	    head = new DNode (null, null, null);
	  count--;
	  return item;
	}
    }


    public Object removeLast()
    {
	 DNode n=head;
     if(count <1)
      {
		head=new DNode (null, null, null);
		count--;
		return null;
      }
     else if(count==1)
     {
       head=new DNode (null, null, null);
	   count--;
	   return n.value;
 	 }

      for(int i=0; i < count;i++)
	{  if(i==count-1)
	       n.prev.next=null;
	   else
	      n=n.next;
	}
       count--;
       return n.value;
     }


    public void printAll()
    {
       String[] s= new String[count];
       DNode p= head;
       int n=0;
       while(n<count)
	{

	    System.out.print(n+"= "+(String)p.value+"  ");
	    n++;
	    p = p.next;
	}


    }
    public String[] printString()
    {
       String[] s= new String[count];
       DNode p= head;
       int n=0;
       while(n<count)
	{

	    s[n]=(String)p.value;
	    n++;
	    p = p.next;
	}
       return s;
    }
    public DNode search(Object name)
    {

    if(head.value==null)
	{
         //   System.out.println("This link is empty.");
	    return null;
	}

	DNode p = head;
	int n=0;
	while(n<count && p.value !=null)
	{   if(p.value.equals(name))
	    {

		return p;
	    }

	    p = p.next;
	    n++;
	}

//        System.out.println(name + " is not in the double list");
	return null;

    }

}

public class Table
{
    // # of rows and columns in the table
    private int nRows;
    private int nCols;

    // A 2-dimensional array of integers
    private int table[][];

    // PCV
    int pcv[];

    // true if the table has been modified
    private boolean modified;

    // Constructor
    public Table(int nRows, int nCols)
    {
	this.nRows = nRows;
	this.nCols = nCols;
	table = new int[nRows][nCols];
	
	// Initialize all elements in the table to be 0
	for(int i=0; i<nRows; i++)
	    for(int j=0; j<nCols; j++)
		table[i][j] = 0;
	
	modified = false;
    }

    public void mark(int i, int j)
    {
	table[i][j] = 1;
    }

    public void unmark(int i, int j)
    {
	table[i][j] = 0;
    }

    public int getValue(int i, int j)
    {
	return table[i][j];
    }

    public void setModified(boolean value)
    {
	modified = value;
    }

    public boolean isModified()
    {
	return modified;
    }

    // Check if there is collision between table 'm' and 
    // the current table
    public boolean collision(int x, int y, int[][] m)
    {
	for(int i=0; i<nRows; i++) {
	    if(table[i][x] == 1 && m[i][y] == 1)
		return true;
	}
	
	return false;
    }

    // Test for collision with the shifted matrix
    public boolean collision(int shift, int[][] m)
    {
	for(int i=shift; i<nCols; i++) {
	    if(collision(i-shift, i, m))
	       return true;
	}

	return false;
    }

    // Returns the PCV for this table
    public int[] getPCV()
    {
	pcv = new int[nCols];

	pcv = after(table);

	return pcv;
    }

    // Returns the PCV as a string for this table
    public String getPCVstring()
    {
	String result = "";
	pcv = new int[nCols];
	pcv = after(table);
	for(int i=0; i<pcv.length; i++)
	    result = result + String.valueOf(pcv[i]);
	return result;
    }

    // Return the forbidden latency for this table
    public String getForbiddenLatencies()
    {
	String result = "";
	
	getPCV();

	for(int k=0; k<pcv.length; k++) {
	    if(pcv[k]==1) {
		result += String.valueOf(k) + " ";
	    }
	}

	System.out.println(result);
	return result;
    }

    // Returns the PCV given a reservation table
    // For example:
    // If the current reservation table is A
    // and the paramter passed in represents 
    // reservation table B. Then this function
    // will return the PCV for A after B
    public int[] after(int[][] m)
    {
	int result[] = new int[nCols];
 
	// Initiliaze result to all 0s
	for(int i=0; i<nCols; i++)
	    result[i] = 0;

	// Find all collisions by sliding table m
	for(int j=0; j<nCols; j++) {
	    if(collision(j,m))
		result[j] = 1;
	}
	
	return result;
    }

    public int getCols()
    {
	return nCols;
    }

    public int getRows()
    {
	return nRows;
    }
    
    public int[][] getTable()
    {
	return table;
    }

    public boolean isEqual(Table t)
    {
	int table[][] = t.getTable();

	for(int i=0; i<nRows; i++)
	    for(int j=0; j<nCols; j++)
		if(this.table[i][j] != table[i][j])
		    return false;
	return true;
    }
}

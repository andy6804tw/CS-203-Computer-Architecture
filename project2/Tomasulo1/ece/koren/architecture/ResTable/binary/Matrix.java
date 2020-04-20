import java.awt.*;

public class Matrix
{
    // # of rows and columns in the matrix
    private int nRows;
    private int nCols;

    // An integer matrix
    public int matrix[][];

    // Constructor #1
    public Matrix(int nRows, int nCols)
    {
	this.nRows = nRows;
	this.nCols = nCols;
	matrix = new int[nRows][nCols];
    }

    // Constructor #2: Clones an existing matrix
    public Matrix(Matrix m)
    {
	this(m.nRows, m.nCols);
	for(int i=0; i<nRows; i++)
	    for(int j=0; j<nCols; j++)
		matrix[i][j] = m.matrix[i][j];
    }

    // Test if Matrix m is equal to the current matrix
    public boolean isEqual(Matrix m)
    {
	for(int i=0; i<nRows; i++)
	    for(int j=0; j<nCols; j++)
		if(matrix[i][j] != m.matrix[i][j])
		    return false;
	return true;
    }

    // Returns (Matrix m1) OR (Matrix m2)
    public static Matrix orMatrix(Matrix m1, Matrix m2)
    {
	Matrix result;

	if(m1.nRows!=m2.nRows || m1.nCols!=m2.nCols) {
	    System.out.println("Bad Matrices");
	    return (Matrix)null;
	}
	else {
	    result = new Matrix(m1.nRows, m1.nCols);
	    
	    for(int i=0; i<m1.nRows; i++)
		for(int j=0; j<m1.nCols; j++)
		    result.matrix[i][j] = Matrix.or(m1.matrix[i][j],m2.matrix[i][j]);
	    return result;
	}
    }

    // Peform the OR operation on two binary values
    public static int or(int v1, int v2)
    {
	if(v1==0 && v2==0)
	    return 0;
	else
	    return 1;
    }

    // Returns a new Matrix shifted by the specified amount
    Matrix shiftLeft(int shift)
    {
	Matrix result = new Matrix(this);

	for(int i=0; i<nRows; i++)
	    for(int j=0; j<nCols-shift; j++)
		result.matrix[i][j] = result.matrix[i][j+shift];

	for(int i=0; i<nRows; i++)
	    for(int j=nCols-shift; j<nCols; j++)
		result.matrix[i][j] = 0;
	return result;
    }    

    // Print the matrix contents onto the Java Console
    void print()
    {
	for(int i=0; i<nRows; i++) {
	    String temp = "";
	    for(int j=0; j<nCols; j++)
		temp += matrix[i][j];
	    System.out.println(temp);
	}
    }

    public void print(TextPanel panel)
    {
	for(int i=0; i<nRows; i++) {
	    String temp = "";
	    for(int j=0; j<nCols; j++)
		temp += matrix[i][j];
	    panel.appendNewLine(temp, Color.white);
	}
    }

    public int getCols()
    {
	return nCols;
    }

    public int getRows()
    {
	return nRows;
    }
}

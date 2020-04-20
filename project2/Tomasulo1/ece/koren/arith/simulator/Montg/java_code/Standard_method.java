/**************
Square and multiply method from Handbook of Applied Crypto
**************/

import java.math.BigInteger;
import java.awt.List;

class Standard_method
{

	private int multCount, modCount;
	BigInteger base, exponent, modulus;
	List simple, verbose;
	int outputRadix;

	Standard_method(BigInteger base, BigInteger exponent, BigInteger modulus, int outputRadix)
	{
		multCount = 0;
		modCount = 0;

		this.base = base;
		this.exponent = exponent;
		this.modulus = modulus;
		this.outputRadix = outputRadix;

		// check for 2, 8, 10, or 16 else default to decimal
		switch (outputRadix)
		{
			case 2 :
				break;
			case 8 :
				break;
			case 10 :
				break;
			case 16 :
				break;
			default :
				outputRadix = 10;
		}

		verbose = new List();
		verbose.add("Square and Multiply Verbose Output.");
		verbose.add("Base = " + base.toString(outputRadix) );
		verbose.add("Exponent = " + exponent.toString(outputRadix) );
		verbose.add("Modulus = " + modulus.toString(outputRadix) );
		verbose.add("");

		simple = new List();
		simple.add("Square and Multiply Simple Output.");
		simple.add("Base = " + base.toString(outputRadix) );
		simple.add("Exponent = " + exponent.toString(outputRadix) );
		simple.add("Modulus = " + modulus.toString(outputRadix) );



	}


	public void runAlgorithm()
	{
		// b <- result
		// a <-	base
		// A <- intermediate operand
		// k <- exponent
		// n <- modulus

		BigInteger b,a,A,k,n;

		b = new BigInteger("1");
		a = base;
		A = base;
		k = exponent;
		n = modulus;
		verbose.add("Running algorithm.");
		verbose.add("");
		verbose.add("RESULT <- 1");
		verbose.add("A <- " + base.toString(outputRadix) + " (base)");
		verbose.add("");

		if (k.testBit(0) )
		{
			verbose.add("Is LSB of exponent set?");
			verbose.add("Yes, update RESULT.");
			b = a;
			verbose.add("RESULT <- " + base.toString(outputRadix) + " (base)");
		}
		else
		{
			verbose.add("Is LSB of exponent set?");
			verbose.add("No, do nothing.");
		}
		verbose.add("");

		for(int i = 1; i < k.bitLength(); i++)
		{
			BigInteger oldA = A;
			A = A.multiply(A);
			multCount++;
			A = A.mod(n);
			modCount++;

			if (k.testBit(i) )
			{
				verbose.add("Is bit " + i + " of exponent set?");
				verbose.add("Yes, update A and RESULT.");
				verbose.add("A <- " + A.toString(outputRadix) + " (A^2) mod n");
				b = A.multiply(b);
				multCount++;
				b = b.mod(n);
				modCount++;
				verbose.add("RESULT <- " + b.toString(outputRadix) + " (A * RESULT mod n)");
			}
			else
			{
				verbose.add("Is bit " + i + " of exponent set?");
				verbose.add("No, only update A.");
				verbose.add("A <- " + A.toString(outputRadix) + " (A^2 mod n)");
			}
			verbose.add("");
		}


		verbose.add("Return RESULT = " + b.toString(outputRadix));

		simple.add("Result = " + b.toString(outputRadix));
		simple.add("");
	}

	public int getMultCount ()
	{
		return multCount;
	}

	public int getModCount ()
	{
			return modCount;
	}

	public String[] getVerbose()
	{
		return verbose.getItems();
	}

	public String[] getSimple()
	{
		return simple.getItems();
	}


}
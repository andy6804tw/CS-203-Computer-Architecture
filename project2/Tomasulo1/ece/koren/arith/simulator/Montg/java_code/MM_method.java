/**************
Montgomery exponentiation method from Handbook of Applied Crypto
**************/

import java.math.BigInteger;
import java.awt.List;

class MM_method
{

	private int multCount, modCount, shiftAddCount;
	BigInteger base, exponent, modulus, R;
	List simple, verbose;
	int outputRadix;

	MM_method(BigInteger base, BigInteger exponent, BigInteger modulus, BigInteger R, int outputRadix)
	{
		multCount = 0;
		//squareCount = 0;
		modCount = 0;
		shiftAddCount = 0;

		this.base = base;
		this.exponent = exponent;
		this.modulus = modulus;
		this.outputRadix = outputRadix;
		this.R = R;

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
		verbose.add("Montgomery Method Verbose Output.");
		verbose.add("Base = " + base.toString(outputRadix) );
		verbose.add("Exponent = " + exponent.toString(outputRadix) );
		verbose.add("Modulus = " + modulus.toString(outputRadix) );
		verbose.add("Montgomery Parameter R = " + R.toString(outputRadix) );
		verbose.add("");

		simple = new List();
		simple.add("Montgomery Method Simple Output.");
		simple.add("Base = " + base.toString(outputRadix) );
		simple.add("Exponent = " + exponent.toString(outputRadix) );
		simple.add("Modulus = " + modulus.toString(outputRadix) );
		simple.add("Montgomery Parameter R = " + R.toString(outputRadix) );

	}

	public void runAlgorithm()
	{
		// x <-	base
		// A <- intermediate operand
		// e <- exponent
		// m <- modulus
		// mprime <- m residue
		// R <- montgomery modulus
		// Rmodm <- R mod m
		// R2modm <- R^2 mod m
		// xprime <- x residue

		BigInteger x,A,e,m,mprime,xprime;

		x = base;
		e = exponent;
		m = modulus;


		// set up for MM
		verbose.add("Precomputing Montgomery inputs.");
		verbose.add("");
		mprime = (m.negate() ).modInverse(R);
		verbose.add("mprime = " + mprime.toString(outputRadix) + " ( -m^-1 mod R)");
		shiftAddCount++;
		// even tho mod R is simple shift type op, computing inverse is much more complex
		modCount++;

		xprime = (x.multiply(R)).mod(m);
		verbose.add("xprime = " + xprime.toString(outputRadix) + " (x * R mod n)");
		multCount++;
		modCount++;
		verbose.add("Mont(u,v) = (u*v + (u*v*nprime mod R)*n)/R");
		verbose.add("");

		verbose.add("Running algorithm.");
		verbose.add("");
		A = R.mod(m);
		verbose.add("A <- " + A.toString(outputRadix) + " (R mod n)");
		modCount++;
		verbose.add("");

		for(int i = e.bitLength(); i >= 0; i--)
		{
			A = montgomery(A , A, R, m, mprime);
			if (A.compareTo(m) == 1)
			{
				A = A.subtract(m);
				shiftAddCount++;
			}

			if (e.testBit(i) )
			{
				A = montgomery(A , xprime, R, m, mprime);
				if (A.compareTo(m) == 1)
				{
					A = A.subtract(m);
					shiftAddCount++;
				}
				verbose.add("Is bit " + i + " of exponent set?");
				verbose.add("Yes.");
				verbose.add("A <- " + A.toString(outputRadix) + " (Mont(A, xprime) )");
			}
			else
			{
				verbose.add("Is bit " + i + " of exponent set?");
				verbose.add("No.");
				verbose.add("A <- " + A.toString(outputRadix) + " (Mont(A, A) )");

			}
			verbose.add("");
		}

		// return to non-residue
		A = montgomery(A , new BigInteger("1"), R, m, mprime);
		if (A.compareTo(m) == 1)
		{
			A = A.subtract(m);
			shiftAddCount++;
		}
		verbose.add("");
		verbose.add("Return to non-residue value.");
		verbose.add("RESULT <- " + A.toString(outputRadix) + " (Mont(A, 1) )");

		verbose.add("");
		verbose.add("Return RESULT = " + A.toString(outputRadix));

		simple.add("Result = " + A.toString(outputRadix));
		simple.add("");
	}

	private BigInteger montgomery (BigInteger u, BigInteger v, BigInteger r, BigInteger m, BigInteger mprime)
	{
		BigInteger result;

		result = u.multiply(v);
		result = result.multiply(mprime);
		result = result.mod(r);
		result = result.multiply(m);
		result = result.add(u.multiply(v));
		result = result.divide(r);

		multCount = multCount + 3;
		shiftAddCount = shiftAddCount + 3;

		return result;
	}

	public int getMultCount ()
	{
		return multCount;
	}

	public int getShiftAddCount ()
	{
			return shiftAddCount;
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
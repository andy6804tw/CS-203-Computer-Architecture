import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Random;

class elliptic_curve
{

	// for binary finite fields, E is the curve with relationship:
	// y^2 + xy = x^3 + ax^2 + b
	// an elliptic curve has the relationship 4a3 + 27b2 != 0 for prime fields
	// for binary fields, b != 0
	EllipticCurve curve;
	BigInteger reduction_poly, generated_x_point, generated_y_point;

	elliptic_curve( EllipticCurve c, BigInteger m)
	{
	curve = c;
	reduction_poly = m;
	generated_x_point = new BigInteger("0");
	generated_y_point = new BigInteger("0");
	}

	public boolean point_on_curve( BigInteger x, BigInteger y)
	{

	BigInteger left_side, right_side;

	// reusing left_side
	left_side = this.gf2m_square(x);
	left_side = this.gf2m_mod_reduction(reduction_poly , left_side);

	right_side = this.gf2m_mult(left_side, x);
	right_side = this.gf2m_mod_reduction(reduction_poly , right_side);
	right_side = this.gf2m_add(right_side, this.gf2m_mod_reduction(reduction_poly , this.gf2m_mult( left_side, curve.getA() ) ) );
	right_side = this.gf2m_add(right_side, curve.getB() );

	left_side = this.gf2m_add( this.gf2m_mod_reduction(reduction_poly , this.gf2m_square(y) ) , this.gf2m_mod_reduction(reduction_poly , this.gf2m_mult(x , y)) );

	return ( left_side.equals(right_side) ) ? true : false;
	}

	public BigInteger gf2m_mod_reduction (BigInteger m, BigInteger c)
	{
		// compute c mod m in GF(2m)
		BigInteger cmodn = new BigInteger("0");
		cmodn = c;

		for(int i = (2*(m.bitLength()) - 2); i >= m.bitLength(); i--)
		{
			if (cmodn.testBit(i - 1) )
			{
				cmodn = cmodn.xor(m.shiftLeft( i - m.bitLength()) );
			}
		}

		return cmodn;
	}

	public BigInteger gf2m_square (BigInteger c)
	{
		//squaring is essentially expansion of c to 2c with interleved zeros
		BigInteger csquared = new BigInteger("0");

		for(int i = 0; i < c.bitLength(); i++)
		{
			if (c.testBit(i))
			{
				csquared = csquared.setBit(2*i);
			}
		}

		return csquared;
	}

	public BigInteger gf2m_mult (BigInteger a, BigInteger b)
	{
		BigInteger axb = new BigInteger("0");

		for(int i = 0; i < b.bitLength(); i++)
		{
			if ( b.testBit(i) )
			{
				axb = axb.xor( a.shiftLeft(i));
			}
		}

		return axb;
	}

	public BigInteger gf2m_add (BigInteger a, BigInteger b)
	{
		return a.xor(b);
	}

	public void generatePoint()
	{
		Random prng = new Random();
		int bitlength = ( (ECFieldF2m)(curve.getField()) ).getM();

		//try up to 99 times
		for(int i = 100 ; i > 0; i--)
		{

			BigInteger x_coor = new BigInteger(bitlength, prng);
			BigInteger y_coor = new BigInteger(x_coor.toString());

			BigInteger one = new BigInteger("1");

			boolean fail = false;

			while ( !(this.point_on_curve( x_coor , y_coor)) )
			{

				y_coor = y_coor.add(one);

				//have we looped arround?
				if(x_coor.equals(y_coor) )
				{
					fail = true;
					break;
				}
				//have we hit the 2^m bit?
				if( y_coor.testBit(bitlength) )
				{
					y_coor = new BigInteger("0");
				}

			}

			// did we find something valid?
			if ( !(fail) )
			{
				this.setGenPoint(x_coor, y_coor);
				break;
			}
		}
	}

	private void setGenPoint(BigInteger x, BigInteger y)
	{
		this.generated_x_point = x;
		this.generated_y_point = y;
	}

	public BigInteger[] getGeneratedPoint()
	{
		BigInteger[] coors = {generated_x_point, generated_y_point};
		return coors;
	}

	public int get_M()
	{
		return ( (ECFieldF2m)(curve.getField()) ).getM();
	}

	public BigInteger get_reduction_poly ()
	{
		return 	reduction_poly;
	}

	public BigInteger getA()
	{
		return curve.getA();
	}

	public BigInteger getB()
	{
		return curve.getB();
	}
}
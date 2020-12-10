import java.math.BigInteger;

import java.util.Random;
import java.util.*;

public class KaratsubaMultiplication
{
	
	public static BigInteger karatsuba(final BigInteger factor0, final BigInteger factor1)
	{
		//base cases
		int n = Math.max(factor0.bitLength(),factor1.bitLength());

		 if(n <= 1)
		{
			return factor0.multiply(factor1);
		}
		
		//we want to divide the number of digits in half (based on the base representation)
		n = (n/2) + (n % 2);

		BigInteger a = factor0.shiftRight(n);
		BigInteger b = factor0.subtract(a.shiftLeft(n));
		BigInteger c = factor1.shiftRight(n);
		BigInteger d = factor1.subtract(c.shiftLeft(n));
		
		//algorithm
		
		BigInteger u = karatsuba(a.add(b), c.add(d));
		BigInteger v = karatsuba(a, c);
		BigInteger w = karatsuba(b, d);
		
		BigInteger part1 = v.shiftLeft(2*n);
		BigInteger part2a = u.subtract(v).subtract(w);
		BigInteger part2b = part2a.shiftLeft(n);
		
		return part1.add(part2b).add(w);
	}

	public static void main(String[] args)
	{
		//test cases
		if(args.length < 2)
		{
			System.out.println("Need two factors as input");
			return;
		}
		BigInteger factor0 = null;
		BigInteger factor1 = null;
		final Random r = new Random();
		if(args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("rand") || args[0].equalsIgnoreCase("random"))
		{
			factor0 = new BigInteger(r.nextInt(100000), r);
			System.out.println("First factor : " + factor0.toString());
		}
		else
		{
			factor0 = new BigInteger(args[0]);
		}
		if(args[1].equalsIgnoreCase("r") || args[1].equalsIgnoreCase("rand") || args[1].equalsIgnoreCase("random"))
		{
			factor1 = new BigInteger(r.nextInt(100000), r);
			System.out.println("Second factor : " + factor1.toString());
		}
		else
		{
			factor1 = new BigInteger(args[1]);
		}
		final BigInteger result = karatsuba(factor0, factor1);
		System.out.println(result);
		System.out.println(result.equals(factor0.multiply(factor1)));
	}
}
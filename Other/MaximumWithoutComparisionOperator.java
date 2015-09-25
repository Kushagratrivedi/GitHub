package practice;

public class MaximumWithoutComparisionOperator {

	public static void main(String[] args) 
	{
		System.out.println(getMax(Integer.MAX_VALUE - 2, -15));
		System.out.println(_getMax(Integer.MAX_VALUE - 2, -15));
	}
	
	public static int _getMax(int a, int b)
	{
		int k = sign(a-b);
		int q = flip(k);
		return k * a + q * b;
	}
	public static int flip(int a)
	{
		return 1 ^ a; //returns 0 if 1, and 1 if 0
	}
	
	//Return 1 if positive, 0 if negative
	public static int sign(int a)
	{
		return flip((a >> 31) & 1);
	}
	
	public static int getMax(int a, int b)
	{
		//get the sign of a, b and (a - b)
		int sa = sign(a);
		int sb = sign(b);
		int sc = sign(a - b);
		
		//if 1, that means both have different sign
		//if 0, that means both have same sign
		int use_sign_a = sa ^ sb;
		
		//if a and b have the different sign, no problem
		//if a and b have the same sign, we need to use the sign of a
		int use_sign_c = flip(use_sign_a);
		
		int k = sa * use_sign_a + sc * use_sign_c;
		int q = flip(k);
		
		return a * k + q * b;
	}

}

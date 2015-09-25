import java.util.ArrayList;
import java.util.Set;


public class RemoveSpace 
{

	public static void main(String[] args) 
	{
		System.out.println(removeSpace1("Hello, World, I am Kushagra	"));
		System.out.println(removeSpace2("Hello, World, I am Kushagra	")+"PWL");

	}

	public static String removeSpace1(String s)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < s.length() ; i++)
		{
			if(!Character.isSpaceChar(s.charAt(i)))
				sb.append(s.charAt(i));
		}
		s = sb.toString();
		return s;
	}
	
	
	public static String removeSpace2(String s)
	{
		char [] c = s.toCharArray();
		int count = 0;
		int n = s.length();
		for(int i = 0 ; i < n; i++)
		{
			if(c[i] == '\n' || c[i] == '\t' || c[i] == ' ')
			{
				shift(c, i, count); 
				count++; //keeps track of number of white spaces
				n--; // if white space is there, move it to end of the string, and decrese length by 1
			}
		}
		//System.out.println(count);
		s = new String(c, 0, c.length - count);
		
		return s;
	}
	
	public static void shift(char[] c, int i, int count)
	{
		for(int j = i ;  j < c.length - 1; j++)
		{
			c[j] = c[j + 1];
		}
		c[c.length - 1] = ' ';
	}

}

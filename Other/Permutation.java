package practice;

import java.util.ArrayList;
import java.util.Stack;

public class Permutation {

	public static void main(String[] args) 
	{
		permte("123".toCharArray(), 0);
		permute1("123");
		Combi("123");
	}
	
	public static void permte(char [] c, int index)
	{
		if(c.length - 1 == index)
			System.out.println(new String(c));
		for(int i = index ; i < c.length ; i++)
		{
			swap(c, i, index);
			permte(c, index+1);
			swap(c, i , index);
		}
	}
	
	
	public static void permute1(String s)
	{
		//char [] c = s.toCharArray();
		int cur = 0, pre = 0;
		Stack<String> st = new Stack<>();
		st.push(s);
		for(int i = 0 ; i < fact(s.length()) - 1; i++)
		{
			String s1 = st.peek();
			pre = ((cur + 1) % s.length());
			String s2 = swap(s1.toCharArray(), cur, pre);
			st.push(s2);
			cur = ((cur + 1) % s.length());
		}
		
		System.out.println(st);
	}
	public static void Combi(String s)
	{
		ArrayList<String> list = new ArrayList<>();
		char [] c = s.toCharArray();
		for(int i = 1 ; i < 1 << s.length() ; i++)
		{
			int index = i;
			//System.out.println("1");
			StringBuilder sb = new StringBuilder();
			for(int j = 0 ; j < s.length(); j++)
			{
				if((index & 1) == 1)
					sb.append(c[j]);
				index >>=1;
			}
			list.add(sb.toString());
		}
		System.out.println(list);
	}
	public static String swap(char [] c, int i, int j)
	{
		char temp = c[i];
		c[i] = c[j];
		c[j] = temp;
		return new String(c);
	}
	
	public static int fact(int n)
	{
		if(n == 1)
			return 1;
		else
			return n * fact(n - 1);
	}
	

}

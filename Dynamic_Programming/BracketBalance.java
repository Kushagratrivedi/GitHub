import java.util.HashSet;


public class BracketBalance {

	public static void main(String[] args) 
	{
		HashSet<String> results = new HashSet<>();
		results = bracketBalance(3);
		System.out.println(results);
		brackets(2, "", 0, 0);

	}
	
	public static void brackets(int num, String output, int open, int close)
	{
		//System.out.println(output);
		if(open == num && close == num)
			System.out.println(output);
		if(open < num)
			brackets(num, output+"(", open + 1, close);
		if(close < open)
			brackets(num, output+")", open , close + 1);
	}
	
	
	public static HashSet<String> bracketBalance(int num)
	{
		HashSet<String> results = new HashSet<>();
		if(num == 0)
		{
			results.add("");
			//return results;
		}
		else
		{
		HashSet<String> more = bracketBalance(num -1);
		//results.addAll(more);
		for(String s: more)
		{
			for(int i = 0 ; i < s.length() ; i++)
			{
				if(s.charAt(i) == '(')
				{
					String str = insertAt(i, s);
					//if(!results.contains(str))
						results.add(str);
				}
			}
			results.add( "()" + s);
			
		}
		}
		return results; 
	}
	public static String insertAt(int index, String str)
	{
		String before = str.substring(0, index + 1);
		String after = str.substring(index + 1);
		return before +"()"+ after;
	}
}

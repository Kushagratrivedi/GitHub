import java.util.LinkedHashSet;
import java.util.Set;


public class ParenthesesPermutation 
{
	public static Set<String> parenthesesPermute(int remaining)
	{
		Set<String> permutation = new LinkedHashSet<>();
		if(remaining == 0)
			permutation.add("");
		else
		{
			Set<String> getPermute = parenthesesPermute(remaining - 1);
			for(String str : getPermute)
			{
				for(int i = 0 ; i < str.length() ; i++)
				{
					if(str.charAt(i) == '(')
					{
						String s = insertInside(str, i);
						permutation.add(s);
					}
				}
				if(!permutation.contains(str + "()"))
					permutation.add(str + "()");
			}
		}
		return permutation; 
	}

	private static String insertInside(String str, int i)
	{
		String start = str.substring(0, i+1);
		String end = str.substring(i+1, str.length());
		return start + "()" + end;
	}

	public static void main(String[] args) {
		System.out.println(parenthesesPermute(3));

	}

}

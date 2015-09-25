import java.util.ArrayList;
import java.util.Collections;


public class StringPermutation 
{
	
	//this is based on swapping of characters in the array
	// saurabh school
	public static ArrayList<String> getStringPermutataion(String str)
	{
		char [] string = str.toCharArray();
		int len = 0;
		ArrayList<String> permutations = new ArrayList<>();
		permutationHelper(string, len, permutations);
		System.out.println(permutations.toString());
		return permutations;
	}
	
	private static void permutationHelper(char [] string, int len, ArrayList<String> perms)
	{
		if(string.length == len)
		{
			//System.out.println(new String(string));
			perms.add(new String(string));
			return;
		}
		for(int i = 0 ; i <= len ; i++)
		{
			swap(string, i, len);
			permutationHelper(string, len+1, perms);
			swap(string, i, len); // BackTracking
		}
	}
	private static void swap(char [] c, int i, int j)
	{
		char ctemp = c[i];
		c[i] = c[j];
		c[j] = ctemp;
		return;
	}
	
	
	//based on getting first elemenet and making permutation of other and append
	//cracking code interview
	public static ArrayList<String> getStringPermutation(String str)
	{
		if(str == null)
			return null;
		ArrayList<String> permutations = new ArrayList<>();
		if(str.length() == 0)
		{
			permutations.add("");
			return permutations;
		}
		char first = str.charAt(0);
		String remainder = str.substring(1);
		ArrayList<String> permutation = getStringPermutation(remainder);
		for(String word : permutation)
		{
			System.out.println("Word: "+word);
			// Here adding one more character to the string that is why we would iterate one more time
			for(int i = 0 ; i <= word.length() ; i++)
			{
				String s = insertCharAt(word, first, i);
				System.out.println("Permute: "+s);
				permutations.add(s);
			}
		}
		return permutations;
	}
	
	

	private static String insertCharAt(String word, char first, int i) {
		String start = word.substring(0, i);
		String end = word.substring(i);
		
		return start + first + end;
	}

	public static void main(String[] args) 
	{
		StringPermutation.getStringPermutataion("1234");
		System.out.println(getStringPermutation("123"));

	}

}

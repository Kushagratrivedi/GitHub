
public class Interleaved {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		WrapInt count = new WrapInt();
		System.out.println(isInterleaved("xxym",3,"xzxt",3,"xxxzxytm",7, count));
		System.out.println(count.count);
		count.count = 0;
		WrapInt count2 = new WrapInt();
		Boolean [][] cache = new Boolean[4][4];
		System.out.println(isInterleaved("xxym",3,"xxzt",3,"xxxzxytm",7,cache,count2));
		System.out.println(count2.count);
	}
	
	private static class WrapInt
	{
		public int count;
	}
	public static boolean isInterleaved(String str1, int len1, 
										String str2, int len2, 
										String str, int len, WrapInt count)
	{
		count.count++;
		if(len1 +  len2 + 2 != len +1)
			return false;
		if(str1 == null || str2 == null || str ==null)
			return false;
		if(len1 == -1)
			return isSame(str2, len2, str, len);
		if(len2 == -1)
			return isSame(str1, len1, str, len);
		if(str1.charAt(len1) == str.charAt(len))
			return isInterleaved(str1,  len1 - 1, str2, len2, str, len-1,count);
		if(str2.charAt(len2)== str.charAt(len))
			return isInterleaved(str1,  len1, str2, len2-1, str, len-1,count);
		return false;
	}
	
	public static boolean isInterleaved(String str1, int len1, 
			String str2, int len2, 
			String str, int len, Boolean [][] cache, WrapInt count)
	{
		count.count++;
		//printCache(cache);
		if(len1 +  len2 + 2 != len +1)
			return false;
		if(str1 == null || str2 == null || str ==null)
			return false;
		if(len1 == -1)
			return isSame(str2, len2, str, len);
		if(len2 == -1)
			return isSame(str1, len1, str, len);
		if(cache[len1][len2] != null)
			return cache[len1][len2];
		if(str1.charAt(len1) == str.charAt(len))
			cache[len1][len2] =  isInterleaved(str1,  len1 - 1, str2, len2, str, len-1, cache, count);
		if(str2.charAt(len2)== str.charAt(len))
			cache[len1][len2] = isInterleaved(str1,  len1, str2, len2-1, str, len-1, cache, count);
		return cache[len1][len2];
	}

	private static void printCache(Boolean[][] cache)
	{
		System.out.println();
		for(Boolean[] b:cache)
		{
			for(Boolean v : b)
			{
				System.out.print(v +" | " );
			}
			System.out.println();
		}
		// TODO Auto-generated method stub
		
	}

	private static boolean isSame(String str2, int len2, String str, int len) 
	{
		// TODO Auto-generated method stub
		if(len != len2)
			return false;
		while(len != -1)
		{
			if(str.charAt(len) != str2.charAt(len))
				return false;
			len-=1;
		}
		return true;
	}

}

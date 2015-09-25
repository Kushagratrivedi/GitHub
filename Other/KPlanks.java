package practice;

import java.util.HashSet;

public class KPlanks {

	public static void main(String[] args) 
	{
		System.out.println(getAllLengths(3 , 1 , 2));
		System.out.println(_getAllLengths(3 , 1 , 2));
		System.out.println(__getAllLengths(3 , 1 , 2));

	}
	
	public static HashSet<Integer> getAllLengths(int k, 
			int shorter, int longer)
	{
		
		HashSet<Integer> lengths = new HashSet<>();
		getAllLengths(k, 0, shorter, longer, lengths);
		return lengths;
		
	}

	private static void getAllLengths(int k, int total, int shorter, int longer,
			HashSet<Integer> lengths) 
	{
		//if k is 0 we cannot use any more planks
		if(k == 0)
		{
			lengths.add(total);
			return;
		}
		//get all permutation by adding shorted/longer planks
		getAllLengths(k - 1, total + shorter , shorter, longer, lengths);
		getAllLengths(k - 1, total + longer, shorter, longer, lengths);
		
	}
	
	public static HashSet<Integer> _getAllLengths(int k
			, int shorter, int longer)
	{
		HashSet<Integer> lengths = new HashSet<>();
		HashSet<String> totals = new HashSet<>();
		_getAllLengths(k, 0, shorter, longer, lengths, totals);
		return lengths;
	}

	private static void _getAllLengths(int k, int i, int shorter, int longer,
			HashSet<Integer> lengths, HashSet<String> totals)
	{
		if(k == 0)
		{
			lengths.add(i);
			return;
		}
		String key = i +" " + k;
		if(totals.contains(key))
			return;
		_getAllLengths(k - 1, i + shorter, shorter, longer, lengths, totals);
		_getAllLengths(k - 1, i + longer, shorter, longer, lengths, totals);
		totals.add(key);
	}
	
	public static HashSet<Integer> __getAllLengths(int k, int shorter, int longer)
	{
		HashSet<Integer> lengths = new HashSet<>();
		for(int nShorter = 0 ; nShorter <= k; nShorter ++)
		{
			int nLonger = k - nShorter;
			int length = nShorter * shorter + nLonger * longer;
			lengths.add(length);
			
			
		}
		return lengths;
	}

}

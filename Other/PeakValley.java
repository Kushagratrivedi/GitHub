package practice;

//import java.util.ArrayList;
import java.util.Arrays;



public class PeakValley {

	public static void main(String[] args) 
	{
		int [] arr = {234,32,43,24,23,4,4,546,5,7,65,434246,76,75,43,4,2343,567,7};
		peakValley_sorted_approach(arr);
		peakValley_unsorted_approach(arr);
	}
	
	public static void peakValley_sorted_approach(int [] arr)
	{
		Arrays.sort(arr);
		for(int i = 1 ; i < arr.length; i+=2)
		{
			swap(arr, i, i+1);
		}
		Arrays.stream(arr).mapToObj(n -> Integer.toUnsignedString(n, 64)).forEach(System.out::print);
		System.out.println();
	}
	
	public static void peakValley_unsorted_approach(int [] arr)
	{
		for(int i = 0 ; i < arr.length - 1; i++)
		{
			int maxIndex = getMaxIndex(arr, i-1, i, i+1);
			if(i != maxIndex)
				swap(arr, i, maxIndex);
		}
		Arrays.stream(arr).mapToObj(n -> Integer.toUnsignedString(n)).forEach(System.out::print);
	}
	
	public static int getMaxIndex(int [] arr, int a, int b, int c)
	{
		int aValue = a >=0 && a < arr.length ? arr[a] : Integer.MIN_VALUE;
		int bValue = b >=0 && b < arr.length ? arr[b] : Integer.MIN_VALUE;
		int cValue = c >=0 && c < arr.length ? arr[c] : Integer.MIN_VALUE;
		int max = Math.max(aValue, Math.max(bValue, cValue));
		if(aValue == max)
			return a;
		else if(bValue == max)
			return b;
		else 
			return c;
		
	}
	
	private static  void swap(int [] arr, int i, int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	

}

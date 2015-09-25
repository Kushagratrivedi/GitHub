
public class MaxIncreasingSumSubsequence {

	public static void main(String[] args) 
	{
		int max = maxSum(new int[]{4,6,1,3,8,4,6});
		System.out.println("Max Sum: "+ max);

	}
	
	public static int maxSum(int [] arr)
	{
		if(arr == null)
			throw new NullPointerException("Array is Null");
		if(arr.length == 1)
			return arr[0];
		int [] maxSum = new int[arr.length];
		int [] subSeq = new int[arr.length];
		
		System.arraycopy(arr, 0, maxSum, 0, arr.length);
		for(int i = 0 ; i < arr.length ; i++)
		{
			subSeq[i] = i;
		}
		
		for(int i = 1 ; i < arr.length; i++)
		{
			for(int j = 0 ; j < i ; j++)
			{
				if(arr[j] < arr[i] && arr[i] + maxSum[j] > maxSum[i])
				{
					maxSum[i] = arr[i] + maxSum[j];
					subSeq[i] = j;
				}
					
			}
		}
		
		int []  max = maxIndex(maxSum);
		int maxIndex = max[1];
		int maxValue = max[0];
		System.out.print("Max Index Sequence: ");
		while(maxIndex != subSeq[maxIndex])
		{
			System.out.print(maxIndex+" --> ");
			maxIndex = subSeq[maxIndex];
		}
		System.out.println(maxIndex);
		return maxValue;
	}
	
	public static int []  maxIndex(int [] maxSum)
	{
		int  [] max = new int[2];
		for(int i =1 ; i < maxSum.length ; i++)
		{
			if(maxSum[i] > max[0])
			{
				max[0] = maxSum[i];
				max[1] = i;
			}
		}
		return max;
	}

}

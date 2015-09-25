
public class IncreamentInArray {

	public static void main(String[] args) {
		int [] arr = new int[]{9,9,9};
		printArray(arr);
		arrayIncreament(arr);
		//printArray(arr);
		

	}
	public static void printArray(int [] arr)
	{
		for(int a: arr)
		{
			System.out.print(a +" | ");
		}
		System.out.println();
	}
	
	public static void arrayIncreament(int [] arr)
	{
		if(arr == null)
			throw new NullPointerException("Array Is Null");
		int index = arr.length - 1;
		int carry = 1;
		int [] temp =  arrayIncreament(arr, index, carry);
		System.out.println(temp.length);
		printArray(temp);
	}

	private static int [] arrayIncreament(int[] arr, int index, int carry) 
	{
		if(index == -1)
			return null;
		int element = arr[index];
		int _sum = element + carry;
		int sum = _sum % 10;
		int _carry = _sum / 10; 
		if(index == 0 && _carry == 1)
		{
			//System.out.println("check");
			int [] another = new int[arr.length + 1];
			//System.arraycopy(arr, 0, another, 1, arr.length);
			for(int i = 0 ; i < arr.length ; i++)
			{
				another[i+1] = arr[i];
			}
			//arr = null;
			//arr = new int[another.length];
			//System.arraycopy(another, 0, arr, 0, another.length);
			//arr[1] = sum;
			//arr[0] = _carry;
			another[1] = sum;
			another[0] = _carry;
			printArray(another);
		
			return another;
		}
		arr[index] = sum;
		if(_carry == 1)
		{
			arrayIncreament(arr, index - 1, _carry);
		}
		return arr;
	}

}

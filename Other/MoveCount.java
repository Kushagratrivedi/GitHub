package practice;

public class MoveCount {

	public static void main(String[] args) 
	{

		int [][] arr = { { 0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
		System.out.println(count(arr));
	}
	
	public static int count(int [][] arr)
	{
		int i1, j1, i, j;
		i1 = j1 = i = j = 0;
		return _count(arr.length-1, arr[0].length-1, i1, j1, arr);
	}

	private static int _count(int i, int j, int i1, int j1, int [][] arr) 
	{
		int count = 0;
		System.out.println(i1+ "--" +j1);
		if(i == j1 && j == j1)
			return 1;
		if(arr[i1][j1] == 0 && (i1+1) != (i + 1))
			count += _count(i , j , i1 + 1, j1, arr);
		if(arr[i1][j1] == 0 && (j1+1) != (j + 1))
			count += _count(i , j , i1, j1 + 1, arr);
		return count;
	}

}

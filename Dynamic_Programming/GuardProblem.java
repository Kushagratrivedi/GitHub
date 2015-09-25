
public class GuardProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static int guardMatrix(char [][] floors, int [][] results, int i, int j)
	{
		if(floors[i][j] == 'E')
		{
			int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE;
			if(i-1 != -1)
			{
				a = guardMatrix(floors, results, i - 1, j);
			}
			if( j - 1 != -1)
			{
				b = guardMatrix(floors, results, i , j-1);
			}
			results[i][j] = Math.min(a, b) + 1;
		}
		if(floors[i][j] == 'G')
		{
			results[i][j] = 99;
			
		}
		return 0;
	}

}
class Floor
{
	int [][] floor;
	public Floor(int i, int j)
	{
		floor =new int[i][j];
	}
	public boolean hasUp(int i, int j)
	{
		return i-1 != -1;
	}
	public boolean hasDown(int i, int j)
	{
		return i+1 != floor.length;
	}
	public boolean hasLeft(int i, int j)
	{
		return j-1 !=-1;
	}
	public boolean hasRight(int i, int j)
	{
		return j + 1 != floor[0].length;
	}
	
	public boolean isEmpty(int i, int j)
	{
		return floor[i][j] == -100;
	}
	
	public boolean isGuard(int i, int j)
	{
		return floor[i][j] == 100;
	}
	
	public boolean isObstacle(int i, int j)
	{
		return floor[i][j] == Integer.MIN_VALUE;
	}
}

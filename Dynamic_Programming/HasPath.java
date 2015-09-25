import java.util.ArrayList;


public class HasPath 
{
	
	Point [][] plane;
	public HasPath()
	{
		plane = new Point[10][10];
		for(int i = 0 ; i < 10 ; i++)
		{
			for(int j = 0 ; j < 10; j++)
			{
				plane[i][j] = new Point(i, j);
				if((int)(Math.random() * 20) < 3)
					plane[i][j].isFree = false;
			}
		}
		
	}
	public boolean hasPath(int x, int y, ArrayList<Point> points)
	{
		Point p = new Point(x, y);
		points.add(p);
		if(x == 0 && y == 0)
			return true; //Success
		// add the current point to the path
		boolean success = false;
		if(x >= 1 && this.plane[x-1][y].isFree())
		{
			success = this.hasPath(x - 1, y, points);
		}
		if(!success && y >= 1 && this.plane[x][y-1].isFree())
			success = this.hasPath(x, y-1, points);
			
		if(success)
			points.add(p);
		
		
		return success;
	}
	
	public void printPlane(ArrayList<Point> plane)
	{
		//int p = 0;
		for(int i = 0 ; i < 10 ; i++)
		{
			for(int j = 0 ; j < 10; j++)
			{
				Point po = new Point(i , j);
				if(this.contain(plane, po))
				{
					System.out.print(1);
					
				}
				else
					System.out.print(" ");
					
				
			}
			System.out.println();
		}
	}
	
	public void printPlane()
	{
		for(int i = 0 ; i < 10 ;i++)
		{
			for(int j = 0 ; j < 10 ;j++)
			{
				if(plane[i][j].isFree())
					System.out.print(1);
				else
					System.out.print("_");
			}
			System.out.println();
		}
			
	}
	
	private boolean contain(ArrayList<Point> list, Point q)
	{
		for(int i = 0 ; i < list.size() ; i++)
		{
			Point p = list.get(i);
			if(p.x == q.x && p.y == q.y)
				return true;
		}
		return false;
	}
	
	

	public static void main(String[] args) 
	{
		HasPath hs = new HasPath();
		ArrayList<Point> plane = new ArrayList<>();
		hs.printPlane();
		System.out.println(hs.hasPath(9, 9, plane));
		
		hs.printPlane(plane);

	}

}

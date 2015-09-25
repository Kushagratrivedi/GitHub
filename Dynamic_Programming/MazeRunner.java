import java.util.ArrayList;


public class MazeRunner {

	public static void main(String[] args) 
	{
		int [][] maze = new int [][]{{0,0,0},{0,0,0},{0,0,0}};
		ArrayList<Point> list = new ArrayList<Point>(); 
 		if(getPath(maze, 0, 0, list))
 			System.out.println(list);
 		

	}
	
	public static boolean getPath(int [][] maze, int row, int col, ArrayList<Point> list)
	{
		System.out.println(list);
		if(row == maze.length)
			return false;
		if(col == maze[0].length)
			return false;
		
		if(row == maze.length - 1 && col == maze[0].length - 1)
		{
			list.add(new Point(row, col));
			return true;
		}
		else if(row >= 0 && row < maze.length && maze[row][col] != -1)
		{
			Point p = new Point(row, col);
			list.add(p);
			boolean b = getPath(maze, row + 1, col, list);
			if(b)
				return true;
			else
			{
				list.remove(p);
			}
		}
		else if(col >= 0 && col < maze.length && maze[row][col] != -1)
		{
			Point p = new Point(row, col);
			list.add(p);
			boolean b = getPath(maze, row, col + 1, list);
			if(b)
				return true;
			else
			{
				list.remove(p);
			}
		}
		return false;
	}
	
	

}


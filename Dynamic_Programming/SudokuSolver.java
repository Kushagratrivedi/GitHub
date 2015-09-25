
public class SudokuSolver {

	public static void main(String[] args) 
	{
		SudokuSolver ss = new SudokuSolver();
		int [][] sudoku = {{0,0,0,0,0,0,0,4,9},
				{0,0,1,3,0,5,2,0,0},
				{0,0,0,9,0,0,8,0,3},
				{0,4,7,0,0,2,0,0,0},
				{5,0,0,0,0,0,0,0,4},
				{0,0,0,8,0,0,5,1,0},
				{2,0,3,0,0,9,0,0,0},
				{0,0,8,1,0,3,7,0,0},
				{6,7,0,0,0,0,0,0,0}};
		ss.printSudoku(sudoku);
		System.out.println(ss.sudokuSolver(sudoku));
		System.out.println();	
		System.out.println("Solved: ");
		System.out.println();
		ss.printSudoku(sudoku);

	}
	public void printSudoku(int [][] s)
	{
		for(int [] row:s)
		{
			for(int cell: row)
			{
				System.out.print(cell+" | ");
			}
			System.out.println();
		}
	}
	public Point findUnassigned(int [][] sudoku)
	{
		for(int i = 0 ; i < sudoku.length ; i ++)
		{
			for(int j = 0 ; j < sudoku[0].length ; j++)
			{
				if(sudoku[i][j] == 0)
					return new Point(i, j);
			}
		}
		return null;
	}
	
	public boolean isRowSafe(int [][] maze, int row, int element)
	{
		for(int i = 0 ; i < maze[row].length ; i++)
		{
			if(i == row)
				continue;
			if(maze[row][i] ==  element)
				return false;
		}//System.out.println("Check 1");
		return true;
	}
	
	public boolean isColSafe(int [][] maze, int col, int element)
	{
		for(int i = 0 ; i < maze.length ; i++)
		{
			if(i == col)
				continue;
			if(maze[i][col] ==  element)
				return false;
		}
		//System.out.println("Check 2");
		return true;
	}
	public boolean isGridSafe(int [][] maze, int row ,int col, int element)
	{
		for(int i = (row - (row % 3)) ; i < 3 ; i++)
		{
			for(int j = (col - (col % 3)) ; j < 3 ; j++)
			{
				//System.out.println(new Point(i,j));
				if(i == row && j ==col)
					continue;
				if(maze[i][j] ==  element)
					return false;
			}
		}
		//System.out.println("Check 3");
		return true;
	}
	
	public boolean isSafe(int [][] maze, int row, int col, int element)
	{
		return this.isRowSafe(maze, row, element) 
				&& this.isColSafe(maze, col, element) 
				&& this.isGridSafe(maze, row, col, element);
	}
	
	public boolean sudokuSolver(int [][] maze)
	{
		//printSudoku(maze);
		Point p = null;
		if((p = this.findUnassigned(maze)) == null)
			return true;
		for(int i = 1 ; i <= 9 ; i++)
		{
			//System.out.println(p);
			if(this.isSafe(maze, p.x, p.y, i))
			{
				maze[p.x][p.y] = i;
				if(this.sudokuSolver(maze))
					return true;
				maze[p.x][p.y] = 0;
				
			}
				
			//p=null;
		}
		return false;
	}

}

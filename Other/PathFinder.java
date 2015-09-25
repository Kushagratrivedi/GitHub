/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class PathFinder 
{
    public static void main(String arg[])
    {
        int a[][] = new int[][]{{1, 1, 1, 0, 0},
                                {0, 0, 1, 1, 1},
                                {1, 0, 0, 1, 0},
                                {0, 1, 0, 1, 1},
                                {0, 0, 0, 0, 1}};
        mazeSolve(a, 0, 0);
    }
    public static boolean mazeSolve(int [][] a, int m, int n)
    {
        if(m == a.length - 1 && n == a[0].length - 1)
        {
            System.out.print(m+""+n);
            return true;
        }
        if(isSafe(a, m, n))
        {
            System.out.print(m+""+n+" --> ");
            if(mazeSolve(a, m + 1, n))
                return true;
            if(mazeSolve(a, m, n + 1))
                return true;
            if(mazeSolve(a, m - 1, n))
                return true;
            if(mazeSolve(a, m, n - 1))
                return true;
            return false;
        }
        return false;
    }
    public static boolean isSafe(int [][] a, int i , int j)
    {
        return i>=0 && i < a.length && j >= 0 && j < a[0].length  && a[i][j] == 1;
    }
    
}

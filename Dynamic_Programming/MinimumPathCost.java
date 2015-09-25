/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class MinimumPathCost 
{
    public int minimumPathCost(int [][] cost)
    {
        if(cost == null)
            throw new NullPointerException("Array is Null");
        int [][] cache = new int [cost.length][cost[0].length];
        for(int i = 0 ; i < cost.length ; i++)
        {
            for(int j = 0 ; j < cost[0].length ; j++)
            {
                if(i > 0 && j> 0)
                {
                    cache[i][j] = Math.min(cache[i-1][j] + cost[i][j],
                            cache[i][j-1] + cost[i][j]);
                }
                else if( i > 0)
                {
                    cache[i][j] = cache[i-1][j] + cost[i][j];
                }
                else if(j > 0)
                {
                    cache[i][j] = cache[i][j-1] + cost[i][j];
                }
                else
                    cache[i][j] = cost[i][j];
            }
        }
        for(int [] row: cache)
        {
            for(int cell: row)
            {
                System.out.print(cell +" | ");
            }
            System.out.println();
        }
        return cache[cost.length-1][cost[0].length -1];
        
    }
}

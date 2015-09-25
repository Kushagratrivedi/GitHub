/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_8;

/**
 *
 * @author Kushagra
 */
public class CoinChange 
{
    public int coinChange(int [] denominations, int change)
    {
        int index = 0;
        return this.coinChange(denominations, change, index);
    }
    
    public int coinChange(int []  denominations, int change, int index)
    {
        if(index >= denominations.length - 1)
            return 1;
        int ways = 0;
        int denomAmount = denominations[index];
        for(int i = 0 ; i * denomAmount <= change ; i++ )
        {
            int remaining = change - i * denomAmount;
            ways += this.coinChange(denominations, remaining, index + 1);
        }
        return ways;
        
    }
    
    public int coinChange_DP(int [] denominations, int change, int index , int [][] map)
    {
        if(index >= denominations.length -1)
            return 1;
        if(map[index][change] > 0)
            return map[index][change];
        int ways = 0;
        int denomAmount = denominations[index];
        for(int i = 0 ; i  * denomAmount <= change ; i++)
        {
            int remaining = change - i * denomAmount;
            ways+= this.coinChange_DP(denominations, remaining, index+1, map);
        }
        map[index][change] = ways;
        return ways;
        
    }
    
    public int coinChange_DP_iterative(int [] denominations, int change)
    {
        int [][] cache = new int[denominations.length][change + 1];
        for(int i = 0 ; i < denominations.length ; i++)
        {
            cache[i][0] = 1;
        }
        
        for(int i = 0 ; i < cache.length ; i++)
        {
            for(int j = 1 ; j <cache[0].length ; j++)
            {
                if(j < i)
                {
                    System.out.println(i+ " --> " +j);
                    cache[i][j] = cache[i-1][j];
                }
                    
                else if(j >= i && i > 0 && j > 0)
                {
                    System.out.println(i+" "+j+ "  " +denominations[i]);
                    cache[i][j] = cache[i-1][j] + cache[i][j - denominations[i]];
                }
                else
                {
                    System.out.println(i+" "+j+ "  " +denominations[i]);
                    cache[i][j] = cache[i][j - denominations[i]];
                }
                    
                
            }
            
        }
        return cache[cache.length - 1][cache[0].length -1];
    }
    
    
}

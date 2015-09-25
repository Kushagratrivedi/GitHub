/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class LongestIncreasingSubsequence 
{
    //Finds longest Increasing Subsequence
    //For array a [] = {1, 0, 3, 4} answer is 3
    // for i = 1, position = 0,count = 1 // (1, 0)
    //for i = 2, position = 1,count = 2 // (1, 0, 3)
    //for i = 3, position = 2,count = 3 // (1, 0, 3, 4)
    
    // Recursive Solution
    public int LCS(int [] a, int length, Integer max)
    {
        if(a.length == 0)
            return 0;
        if(a.length == 1)
            return 1;
        int count = 1, position;
        for(int  i = 1 ; i < length ; i ++)
        {
            position = this.LCS(a, i, max);
            if(a[i - 1] < a[length - 1] && position + 1 > count)
                count = position + 1;
        }
        if(max < count)
            max = count;
        return max;
    }
    
    //Dynamic Programming Solution
    
    public int LCS (int [] a, int length)
    {
        int [] results = new int[length];
        for(int  i = 0 ; i < length ; i ++)
        {
            results[i] = 1;
        }
        return this.LCS_(a, length, results);
    }
    
    private int LCS_(int [] a, int length, int [] results)
    {
        if(length == 1)
            return a[length-1];
        if(results[length - 1] > 1)
            return results[length - 1];
        int position, count = 1;
        
        for(int i = 1 ; i < length ; i ++)
        {
            position = this.LCS_(a, i, results);
            if(a[i - 1] < a[length - 1] && results[i] < position + 1)
            {
                count = position + 1; 
                
            }        
        }
        results[length - 1] = count;
        
        /*for(int i = 1 ; i < length ; i ++)
        {
            for(int j = 0 ; j < i ; j++)
            {
                if(a[j] < a[i] && results[j] + 1 > results[i])
                    results[i] = results[j] + 1;
            }
            
        }*/
        int max = Integer.MIN_VALUE;
        for(int i = 0 ; i < results.length ; i ++)
        {
            if(results[i] > max)
                max = results[i];
        }
        return max;
    }
    
    
    
}

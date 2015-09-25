/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_17;

/**
 *
 * @author Kushagra
 */
public class MaxSumSubMatrix 
{
    private class MaxSumSubSequence
    {
        int sum;
        int start;
        int end;
    }
    public MaxSumSubSequence maxSumSubSeq(int [] col)
    {
        int [] cache = new int[col.length];
        int [] position_cache = new int [col.length];
        cache[0] = col[0];
        for(int i = 1 ; i < col.length ; i++ )
        {
            if(cache[i - 1] + col[i] > col[i])
            {
                cache[i] = cache[i - 1] + col[i];
                position_cache[i] = i-1;
            }
            else
            {
                cache[i] = col[i];
                position_cache[i] = i;
            }
               
            //cache[i] = Math.max(cache[ i - 1] + col[i], col[i]);
        }
        int [] max = this.max(cache);
        int index = max[1];
        while(index != 0 && position_cache[index] != index)
            index = position_cache[index];
        MaxSumSubSequence msss = new MaxSumSubSequence();
        msss.end = max[1];
        msss.start = index;
        msss.sum = max[0];
        
        return msss;
    }

    private int[] max(int[] cache)
    {
        int [] max = new int [2];
        max[0] = Integer.MIN_VALUE;
        for(int i = 0 ; i < cache.length ; i++)
        {
            if(cache[i] > max[0])
            {
                max[0] = cache[i];
                max[1] = i;
            }
                
        }
        return max;
        
    }
    
    public int maxSumSubMatrix(int [][] arr)
    {
        
        int l, r, max_sum = Integer.MIN_VALUE, current_sum, max_left = 0, max_right = 0, max_up = 0, max_down = 0;
        for(l = 0 ; l < arr[0].length ; l++)
        {
            int [] cache = new int[arr.length];
            for(r = l ; r < arr[0].length ; r ++)
            {
                this.columnSum(arr, cache, r);
                MaxSumSubSequence msss = this.maxSumSubSeq(cache);
                current_sum = msss.sum;
                if(current_sum > max_sum)
                {
                    max_sum = current_sum;
                    max_left = l;
                    max_right = r;
                    max_up = msss.start;
                    max_down = msss.end;
                }
                    
            }
        }
        System.out.println("Maximum Sum " + max_sum);
        System.out.println("Max Sum Sub-matrix: "+ "Max_Left: " +max_left+" Max Right: "+max_right +" Max Up: "+ max_up +" Max Down: "+ max_down);
        return max_sum;
    }
    
    private void columnSum(int [][] arr, int [] cache, int col)
    {
        for(int i = 0 ; i < cache.length ; i++)
        {
            cache[i] += arr[i][col];
        }
        
    }
}

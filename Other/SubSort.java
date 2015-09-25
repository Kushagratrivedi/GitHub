/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class SubSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println(new SubSort().getSubSortIndexes(new int[]{1,2,4,7,10,11,7,12,6,7,16,18,19}));
    }
    
    // int arr = {1 2   4   7   10  11  7   12  6   7   16  18  19}
    public Result getSubSortIndexes(int [] arr)
    {
        int end_left = getEndLeft(arr);
        
        
        if(end_left >= arr.length-1)
            return new Result(0, arr.length - 1);
        
        int start_right = getStartRight(arr);
        
        int min_index = start_right;
        int max_index = end_left;
        
        for(int i = end_left + 1 ; i < start_right ; i++)
        {
            min_index = arr[i] < arr[min_index] ? i : min_index;
            max_index = arr[i] > arr[max_index] ? i : max_index;
        }
        
        int left = shrinkLeft(arr, min_index, end_left);
        int right = shrinkRight(arr, max_index, start_right);
        
        return new Result(left, right);
    }

    private int getEndLeft(int[] arr) 
    {
        for(int i = 1 ; i < arr.length ; i++)
        {
            if(arr[i-1] > arr[i])
                return i-1;
        }
        return arr.length - 1;
    }

    private int getStartRight(int[] arr) 
    {
        for(int i = arr.length - 2 ; i >=0 ;i--)
        {
            if(arr[i] > arr[i + 1])
                return i + 1;
        }
        return 0;
    }

    private int shrinkLeft(int[] arr, int min_index, int end_left) 
    {
        int temp = arr[min_index];
        int index = 0;
        for(int i =  end_left - 1 ; i >= 0; i--)
        {
            if(arr[i] <= temp) return i + 1;
        }
        return 0;
    }

    private int shrinkRight(int[] arr, int max_index, int start_right) 
    {
        int temp = arr[max_index];
        for(int  i = start_right ; i < arr.length ; i++)
        {
            if(arr[i] >= temp) return i - 1;
        }
        return arr.length - 1;
    }
    
    private class Result
    {
        int start_index;
        int end_index;

        public Result(int start_index, int end_index) {
            this.start_index = start_index;
            this.end_index = end_index;
        }
        
        @Override
        public String toString()
        {
            return "start index: "+this.start_index +" & end index: "+this.end_index;
        }

        
    }
            
            
    
}

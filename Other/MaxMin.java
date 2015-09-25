/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice2;

import java.util.Comparator;

/**
 *
 * @author Kushagra
 */
public class MaxMin {

    
    public static void main(String[] args) 
    {
        MaxMin mm = new MaxMin();
        mm.maxMin(new int []{4534,5,35345,345,34});
    }
    
    public Pair maxMin(int [] arr, int start, int end)
    {
        if(start >= 0 && start == end)
        {
            return new Pair(arr[start], arr[start]);
        }
        else if(start == end - 1)
        {
            Pair p = new Pair();
            p.max = Math.max(arr[start], arr[end]);
            p.min = Math.min(arr[start], arr[end]);
            return p;
        }
        int mid = (start + end)/2;
        Pair part1 = this.maxMin(arr, start, mid);
        Pair part2 = this.maxMin(arr, mid + 1, end);
        return new Pair(Math.max(part1.max, part2.max), Math.min(part1.min, part2.min));
    }
    
    public void maxMin(int [] arr)
    {
        int start = 0;
        int end = arr.length - 1;
        Pair p = this.maxMin(arr, start, end);
        System.out.println("Max is: "+p.max+", Min is: "+p.min);
                 
    }
    private class Pair
    {
        int max;
        int min;
        Pair(int max, int min)
        {
            this.max = max;
            this.min = min;
        }
        Pair(){}

    }
    
}

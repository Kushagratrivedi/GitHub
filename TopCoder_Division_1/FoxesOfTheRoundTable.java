

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra -  Greedy algorithm
 * 
 * 	There are n foxes, numbered 0 through n-1. You are given a int[] h with n elements. The elements of h are the heights of those foxes. Your task is to arrange all foxes around a round table. 



Given an arrangement of foxes, let D be the largest height difference between adjacent foxes. For example, suppose that four foxes with heights { 10, 30, 20, 40 } sit around the table in this order. The height differences are |10-30|=20, |30-20|=10, |20-40|=20, and |40-10|=30. (Note that the last fox is also adjacent to the first one, as this is a round table.) Then, the value D is max(20,10,20,30) = 30. 



Find an arrangement of the given foxes for which the value D is as small as possible. Return a permutation of foxes that describes your arrangement. I.e., return a int[] with n elements: the numbers of foxes in order around the table. If there are multiple optimal solutions, you may return any of them.
 
Definition
    	
Class:	FoxesOfTheRoundTable
Method:	minimalDifference
Parameters:	int[]
Returns:	int[]
Method signature:	int[] minimalDifference(int[] h)
(be sure your method is public)
    
 
Constraints
-	h will contain between 3 and 50 elements, inclusive.
-	Each element in h will be between 1 and 1,000, inclusive.
 
Examples
0)	
    	
{1,99,50,50}
Returns: {0, 3, 1, 2 }
In the optimal solution the foxes with heights 1 and 99 mustn't be adjacent. Hence, the heights of foxes have to be 1, 50, 99, 50, in this cyclic order, and the optimal value of D is 49. One permutation that produces this order of foxes is 0, 3, 1, 2.
1)	
    	
{123,456,789}
Returns: {0, 1, 2 }
Whatever we do, the result will always be 789-123.
2)	
    	
{10,30,40,50,60}
Returns: {0, 1, 4, 3, 2 }
The permutation {0, 1, 4, 3, 2 } specifies that the heights of foxes are in the following order: 10, 30, 60, 50, 40.
3)	
    	
{1,2,3,4,8,12,13,14 }
Returns: {0, 1, 2, 3, 5, 6, 7, 4 }
4)	
    	
{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 }
Returns: {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 }

 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
public class FoxesOfTheRoundTable 
{
    public static void main(String[] args) 
    {
        int [] arr1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 };
        int [] arr2 = {1,2,3,4,8,12,13,14 };
        int [] arr3 = {10,30,40,50,60};
        int [] arr4 = {1,99,50,50};
        
        print(minimalDifference(arr1));
        print(minimalDifference(arr2));
        print(minimalDifference(arr3));
        print(minimalDifference(arr4));
    }
    
    
    public static void print(int [] a)
    {
        System.out.println();
        for (int b : a) {
            System.out.print(b + " | ");
        }
    }
    public static int [] minimalDifference(int [] h) 
    {
        HashMap<Integer, Stack<Integer>> map = new HashMap<>();
        for(int i = 0 ; i < h.length ; i++)
        {
            Stack<Integer> stack;
            if(!map.containsKey(h[i]))
            {
                stack = new Stack<>();
                stack.push(i);
                map.put(h[i], stack);
            }
            else
            {
                stack = map.get(h[i]);
                stack.push(i);
                map.put(h[i], stack);
            }
        }
        int [] clone = h.clone();
        //print(clone);
        Arrays.sort(clone);
        int [] result = new int[h.length];
        int j = 0, k = clone.length - 1;
        result[j] = clone[0];
        result[k] = clone[1];
        for(int i = 2 ; i < clone.length ; i++)
        {
            int diff1 = Math.abs(clone[i] - result[j]);
            int diff2 = Math.abs(clone[i] - result[k]);
            if(diff1 >= diff2)
                result[++j] = clone[i];
            else
                result[--k] = clone[i];
        }
        
        int [] final_result = new int[result.length];
        for(int i = 0 ; i <final_result.length ; i++)
        {
            final_result[i] = map.get(result[i]).pop();
        }
        return final_result;
    }
}

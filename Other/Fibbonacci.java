
import java.lang.reflect.Array;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Fibbonacci 
{
    public static void main(String arfs[])
    {
        for(int i = 0 ; i < 7 ; i ++)
        {
            System.out.println(fib(i));
        }
        int [] arr = new int [50];
        for(int i = 0 ; i < 50 ; i ++)
        {
            Array.setInt(arr, i, -1);
        }
        for(int i = 0 ; i < 7 ; i ++)
        {
            System.out.println(fibbonacci(i, arr));
        }
    }
    
    // recursive algorithm, it takes exponetial time
    public static int fib(int n)
    { 
        if(n==2 || n==1)
        {
            return 1;
        }
        else if(n == 0)
            return 0;
        else
            return fib(n-1) + fib(n-2);
    }
    
    //dynamic programming
    public static int fibbonacci(int n, int [] arr)
    {
        if(arr[n] != -1)
            return arr[n];
        else
        {
            if(n == 1 || n == 2)
                arr[n] = 1;
            else if( n == 0)
                arr[n] = 0;
            else
                arr[n] = fibbonacci(n - 1, arr) + fibbonacci(n - 2, arr);
        }
        return arr[n];
    }
    
}

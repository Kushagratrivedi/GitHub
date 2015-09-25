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
public class RecursiveMultiply 
{
    public int multiply(int a, int b)
    {
        if(a > b)
        {
            int big = Math.max(a, b);
            int small = Math.min(a, b);
            this.multiply(small, big);
        }
        if(a == 0)
            return 0;
        if(a == 1)
            return b;
        int s = a >> 1;
        int side1 = this.multiply(s, b);
        int side2 = side1;
        if(a % 2 ==1)
            side2 = this.multiply(a - s, b);
        return side1 + side2;
    }
    
    public int multiply_2(int a, int b)
    {
        if(a > b)
        {
            int big = Math.max(a, b);
            int small = Math.min(a, b);
            this.multiply(small, big);
        }
        if(a == 0)
            return 0;
        if(a == 1)
            return b;
        int s = a >> 1;
        int half = this.multiply_2(s, b);
        if(a % 2 == 0)
            return half + half;
        else
            return b + half + half;
        
    }
}

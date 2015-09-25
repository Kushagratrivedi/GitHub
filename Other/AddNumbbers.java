/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class AddNumbbers 
{
    public static void main(String ags[])
    {
        int [] a = {8, 4, 0, 2};
        int [] b = {4, 2, 0, 1};
        int [] sumValue = sum(a, b);
        display(sumValue);
    }
    public static void display(int a[])
    {
        for(int i : a)
        {
            System.out.print(i+" ,");
        }
    }
    // a = {8, 4, 0, 2}, b = {4, 2, 0, 1}
    public static int [] sum(int a[], int b[])
    {
        int [] value = new int[a.length + 1];
        int temp = 0;
        for(int i = 0 ; i < a.length ; i ++)
        {
            int digit = a[i] + b[i];
            if(i == a.length - 1)
            {
                if(digit > 9)
                    value[i+1] = 1;
            }
            digit = digit +  temp;
            if(digit > 9)
            {
                value[i] = digit % 10;
                System.out.println(value[i]);
                temp = 1;
            }
            else
            {
                value[i] = digit;
                temp =0;
            }
                
        }
        
        return value;
    }
    
}

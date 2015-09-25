
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class StringAllCombination 
{
    public void combination(int [] a, char [] c, int len, int k)
    {
        if(k == len)
        {
            a[k-1] = 0; print(a, c, len);
            a[k-1] = 1; print(a, c, len);
            return;
        }
        a[k] = 0;
        combination(a, c, len, k+1);
        a[k] = 1;
        combination(a, c, len, k+1);
        
    }
    private void print(int [] a, char [] c, int len)
    {
        for(int i = 0 ; i < len; i++)
        {
            if(a[i] == 1)
                System.out.print(c[i]);
        }
        System.out.println();
    }
}

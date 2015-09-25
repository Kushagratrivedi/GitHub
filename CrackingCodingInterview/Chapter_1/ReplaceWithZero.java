/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_1;

/**
 *
 * @author Kushagra
 */
public class ReplaceWithZero 
{
    public char[][] replaceWithZero(char [][] c)
    {
        int a = 0, b = 0;
        for(int i = 0 ;i < c.length ; i++)
        {
            for(int j = 0 ; j < c[0].length ; j++)
            {
                if(c[i][j] == '0')
                {
                    a = i;
                    b = j;
                    break;
                }
                    
            }
        }
        for(int p = 0 ; p < c.length ; p++)
        {
            for(int q = 0; q < c[0].length ; q++)
            {
                if(p == a || q == b)
                    c[p][q] = '0';
            }
        }
        return c;
    }
    
    
}

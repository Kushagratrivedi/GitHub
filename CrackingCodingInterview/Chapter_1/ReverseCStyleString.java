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
public class ReverseCStyleString 
{
    public String reverse(char [] c)
    {
        for(int i = 0 ; i < c.length/2 ; i++)
        {
            c = this.swap(c, i, c.length-i-1);
        }
        return new String(c);
    }
    private char[] swap(char [] c, int i, int j)
    {
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
        return c;
    }
    
}

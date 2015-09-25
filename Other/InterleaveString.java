/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class InterleaveString 
{
    public static void main(String args[])
    {
        
    }
    public static void interleave(char [] c1, char [] c2, int len, char [] c)
    {
        if(c1[0] == '/' && c2[0] == '/')
        {
            System.out.println(new String(c));
        }
        if(c1[0] != '/')
        {
            c[0] = c1[0];
            
        }
    }
    
}

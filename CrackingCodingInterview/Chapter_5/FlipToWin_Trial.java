/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_5;

/**
 *
 * @author Kushagra
 */
public class FlipToWin_Trial
{
    public int flipToWin(String binary)
    {
        char[] charArray = binary.toCharArray();
        int [] cache = new int[charArray.length];
        int last_flip_index = 0;
        boolean flip;
        int max = Integer.MIN_VALUE;
        flip = charArray[0] != '1';
        cache[0] = 1; 
        for(int i = 0 ; i < charArray.length ; i++)
        {
            if(charArray[i] == '1')
                cache[i]  += cache[i-1];
            if(!flip) 
            {   
                last_flip_index = i;
                max = i + 1 ;
            }
        }
        return 0;
    }
}

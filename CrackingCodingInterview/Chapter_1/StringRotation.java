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
public class StringRotation 
{
    public boolean isRotation(String str1, String str2)
    {
        int i = 0,j = 0;
        // String lenghs are not equal, return false
        if(str1.length() != str2.length())
            return false;
        //iterate while first letter matcher of both strings, if matches starts over
        for(;;)
        {
            if(i == str1.length() - 1)
                    i = 0;
            if(str1.charAt(j) == str2.charAt(i))
            {
                System.out.println(str1.charAt(j) +" --> "+ str2.charAt(i));
                if(str1.charAt(j) != str2.charAt(i))
                    return false;
                j+=1;
                if( j == str2.length())
                    return true;
            }
            i+=1;
        }
    }
}

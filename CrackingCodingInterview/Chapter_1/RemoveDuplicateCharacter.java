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
public class RemoveDuplicateCharacter 
{
    public String removeDuplicates(String s)
    {
        for(int i = 0 ; i < s.length() ; i ++)
        {
            for(int j = i + 1 ; j < s.length(); j ++)
            {
                if(s.charAt(i) == s.charAt(j))
                {
                    String s1 = s.substring(0,j);
                    String s2 = s.substring(j+1);
                    s = s1 + s2;
                }
            }
        }
        return s;
    }
}

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
public class UniqueStringCharacter 
{
    public boolean hasUniqueCharacters(String str)
    {
        for(int i = 0; i < str.length()-1; i++)
        {
            char c1 = (char)str.charAt(i);
            for(int j = i+1 ; j < str.length() ; j ++)
            {
                char c2 = (char)str.charAt(j);
                //System.out.println(c1+" ---> "+c2);
                if(c1 == c2)
                    return false;
            }
        }
        return true;
    }
}

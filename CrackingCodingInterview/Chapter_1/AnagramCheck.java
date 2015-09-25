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
public class AnagramCheck
{
    public boolean isAnagram(String s1, String s2)
    {
        if(s1.length() != s2.length())
            return false;
        char [] c1 = new char[s1.length()];
        c1 = s1.toCharArray();
        char [] c2 = new char[s2.length()];
        c2 = s2.toCharArray();
        for(int i = 0 ; i < c1.length; i++)
        {
            for(int j = 0 ; j < c2.length; j++)
            {
                if(c1[i] == c2[j])
                {
                    c1[i] = '1';
                    c2[j] = '1';
                }
            }
        }
        //System.out.println(new String(c1) +" -> "+new String(c2));
        return new String(c1).equals(new String(c2));
    }
}

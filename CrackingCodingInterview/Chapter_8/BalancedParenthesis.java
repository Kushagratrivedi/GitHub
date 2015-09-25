/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_8;

import java.util.HashSet;

/**
 *
 * @author Kushagra
 */
public class BalancedParenthesis 
{
    public HashSet<String> catalan(int index)
    {
        HashSet<String> set = new HashSet<>();
        if(index == 0)
        {
            set.add("");
            return set;    
        }
        HashSet<String> previous = this.catalan(index - 1);
        for(String s :  previous)
        {
            for(int i = 0 ; i < s.length() ; i++)
            {
                if(s.charAt(i) == '(')
                {
                    String str = this.insertAt(i, s);
                    set.add(str);
                } 
            }
            set.add("()" + s);
        }
        return set;
        
    }
    
    public void catalan_optimized(int count, int left, int right, char []  str, HashSet<String> set)
    {
        if(left < 0 || right < left)
            throw new IllegalArgumentException("Invalid expression");
        if(left ==0 && right == 0)
            set.add(new String(str));
        else
        {
            if(left > 0)
            {
                str[count] = '(';
                this.catalan_optimized(count+1, left - 1, right, str, set);
            }
            if(right > left)
            {
                str[count] = ')';
                this.catalan_optimized(count+1, left, right - 1, str, set);
            }
            
        }
    }
    
    
    public String insertAt(int index, String str)
    {
        String before = str.substring(0, index + 1);
        String after = str.substring(index + 1);
        return before + "()" + after;
    }
}

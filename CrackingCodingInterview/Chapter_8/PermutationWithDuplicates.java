/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_8;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Kushagra
 */
public class PermutationWithDuplicates 
{
    public HashSet<String> permutation(String str)
    {
       int remaining = str.length();
       int start = 0;
       HashSet<String> set = new HashSet<>();
       doPermute(set, "", remaining, this.getFrequency(str));
       return set;
    }
    
    private HashMap<Character, Integer> getFrequency(String str)
    {
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i = 0 ; i < str.length() ; i++)
        {
            if(!map.containsKey(str.charAt(i)))
                map.put(str.charAt(i), 1);
            else 
                map.put(str.charAt(i), map.get(str.charAt(i)) + 1);
        }
        return map;
        
    }

    private void doPermute(HashSet<String> set, String prefix, int remaining, HashMap<Character, Integer> map) 
    {
        if(remaining == 0)
        {
            set.add(prefix);
            return;
        }
        for(char c : map.keySet())
        {
            int count =  map.get(c);
            if(count > 0)
            {
                map.put(c, count-1);
                this.doPermute(set, prefix + c, remaining - 1, map);
                map.put(c, count); // BACKTRACKING
            }
            
        }
        
    }
    
}

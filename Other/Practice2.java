/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Kushagra
 */
public class Practice2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int [] a = new int [] {0,0,1,1,1,1,1,1,1,1};
        System.out.println(count0s(a, 0, 6));
        // TODO code application logic here
        
        String [] s = new String []{"Men", "People", "Word", "MenPeople", "MenPeopleWord"};
        Practice2.printLongestWord(s);
        
        Practice2.priorityQueueExample();
        Set<String> dictionary = new TreeSet<>();
        dictionary.add("Hello");
        dictionary.add("Hal0o");
        dictionary.add("efghi");
        dictionary.add("jklmn");
        dictionary.add("opqrs");
        
        Iterator<String> itr = Practice2.transform("Hello", "Pello", dictionary).listIterator();
        while(itr.hasNext())
            System.out.println(itr.next());
        
    }
    
    public static int count0s(int [] a, int start, int end)
    {
        if(start == end)
        {
            if(a[start] == 0)
                return start + 1;
            else
                return start;
        }
        else
        {
            int mid = (start + end) / 2;
            if(a[mid] == 0)
                return Practice2.count0s(a, mid+1, end);
            else
                return Practice2.count0s(a, start, mid);
        }
        //return 0;
        
    }
    
    public static void printLongestWord(String [] s)
    {
        Map<String, Boolean> map = new HashMap<>();
        for(String s1: s)
        {
            map.put(s1, true);
            
        }
        Arrays.sort(s, new LengthComparator());
        
        for(String s2 : s)
        {
            if(canWeBuild(s2, true, map))
                System.out.println(s2);
        }
    }
    private static boolean canWeBuild(String str, boolean isOriginal, Map<String, Boolean> map)
    {
        if(map.containsKey(str) && !isOriginal)
            return map.get(str);
        for(int i = 1 ; i < str.length() ; i++)
        {
            String left = str.substring(0, i);
            String right = str.substring(i);
            if(map.containsKey(left) && map.get(left) == true && canWeBuild(right, false, map))
                return true;
        }
        map.put(str, false);
        return false;
    }
    
    public static void priorityQueueExample()
    {
        Queue<Integer> queue = new PriorityQueue<>();
        for(int i = 0 ; i < 10 ; i++)
        {
            int newInt = new java.util.Random().nextInt(100); 
            System.out.println(newInt);
            queue.offer(newInt);
        }
        System.out.println();
        for(int i = 0 ; i < 10 ; i++)
        {
            System.out.println(queue.poll());
        }
        
    }
    
    public static LinkedList<String> transform(String startWord, String stopWord, Set<String> dictionary)
    {
        startWord = startWord.toUpperCase();
        stopWord = stopWord.toUpperCase();
        Queue<String> actionQueue = new LinkedList<>();
        Set<String> visitedSet = new HashSet<>();
        Map<String, String> backtrackMap = new TreeMap<>();
        actionQueue.add(startWord);
        visitedSet.add(startWord);
        while (!actionQueue.isEmpty()) 
        {
            String w = actionQueue.poll();
           for (String v : getOneEditWords(w)) 
           {
                if (v.equals(stopWord)) 
                {
                    LinkedList<String> list = new LinkedList<String>();
                    list.add(v);
                    while (w != null) 
                    {
                        list.add(0, w);
                        w = backtrackMap.get(w);
                    }
                    return list;
                }
                if (dictionary.contains(v)) 
                {
                    if (!visitedSet.contains(v)) 
                    {
                        actionQueue.add(v);
                        visitedSet.add(v);
                        backtrackMap.put(v, w);
                    }
                }
           }
        }
        return null;
    }
    static Set<String> getOneEditWords(String word) 
    {
        Set<String> words = new TreeSet<>();
        for (int i = 0; i < word.length(); i++) 
        {
            char[] wordArray = word.toCharArray();
            for (char c = 'A'; c <= 'Z'; c++) 
            {
                if (c != word.charAt(i)) 
                {
                    wordArray[i] = c;
                    words.add(new String(wordArray));
                }
            }
        }
        return words;
    }
}
  


class LengthComparator implements Comparator<String>
{

    @Override
    public int compare(String o1, String o2) 
    {
        return Integer.compare(o1.length(), o2.length());
    }
    
}

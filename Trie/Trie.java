
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Trie 
{
    private final TrieNode root;
    public Trie(ArrayList<String> strings)
    {
         root = new TrieNode();
         strings.stream().forEach(root::addWord);
    }
    
    public Trie(String[] strings)
    {
         root = new TrieNode();
         for(String s: strings)
         {
             root.addWord(s);
         }
    }
    
    public TrieNode getRoot()
    {
        return this.root;
    }
    
    @Override
    public String toString()
    {
       return this.root.children.toString();
    }
    public boolean contains(String prefix)
    {
        return this.contains(prefix, false);
    }

    private boolean contains(String prefix, boolean exact) 
    {
        TrieNode lastNode = root;
        for(int i = 0 ; i < prefix.length() ; i++)
        {
            lastNode = lastNode.getChild(prefix.charAt(i));
            if(lastNode == null) return false;
        }
        return !exact || lastNode.getTerminate();
    }
    
    
}

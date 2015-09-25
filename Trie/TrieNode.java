
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class TrieNode 
{
    //CONTAIN DATA OF THIS NODE
    private char character;
    //CHILDREN OF THIS NODE, CHARACTER IS USEFUL FOR EASY LOOKUP IN HASHTABLE
    HashMap<Character, TrieNode> children;
    //CHECK THIS TRIENODE HAS TERMINATED OR NOT
    private boolean terminates = false;
    
    //DESIGNED FOR ROOT, ROOT ITSELF DOES NOT CONTAIN ANY VALUE, IT HAS JUST POINTERS TO ALL CHILDREN
    public TrieNode()
    {
        
        children = new HashMap<>();
    }
    
    @Override
    public String toString()
    {
        return Character.toString(this.character);
    }
    
    //ALL OTHER NODES, EXCEPT PARENT/ROOT
    public TrieNode(char c)
    {
        this();
        this.character = c;
    }
    
    public void setTerminate(boolean value)
    {
        this.terminates = value;
       
    }
    
    public boolean getTerminate()
    {
        return this.terminates;
    }
    
    public TrieNode getChild(char c)
    {
        return this.children.get(c);
    }
    
    public void addWord(String value)
    {
        if(value == null || value.length() == 0)
            return;
        char firstChar = value.charAt(0);
        TrieNode child = this.getChild(firstChar);
        if(child == null)
        {
            child = new TrieNode(firstChar);
            this.children.put(firstChar, child);
        }
        if(value.length() > 1)
            child.addWord(value.substring(1));
        else
            child.setTerminate(true);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class TrieDemo 
{
    public static void main(String args[])
    {
        String [] arr = {"Hello", "Hi", "How are you", "doing", "duck", "dove", "dog"};
        Trie t = new Trie(arr);
        //System.out.println(ts);
        System.out.println(t.contains("Hllo"));
    }
}

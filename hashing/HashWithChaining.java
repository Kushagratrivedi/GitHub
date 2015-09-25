/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

/**
 *
 * @author Kushagra
 */
public class HashWithChaining 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        HashTable hashTable = new HashTable(10);
        hashTable.hashFunction1(15);
        hashTable.hashFunction1(16);
        hashTable.hashFunction1(120);
        hashTable.hashFunction1(60);
        hashTable.hashFunction1(70);
        System.out.println(hashTable.toString());
        System.out.println(hashTable.isExist(60));
    }
}
class HashTable
{
    int maxSize;
    LinkedList<Integer> [] hashTable;
    HashTable(int maxSize)
    {
        this.maxSize = maxSize;
        hashTable = new LinkedList [this.maxSize];
    }
    public void hashFunction1(Integer data)
    {
        int mod = data % this.maxSize;
        if(this.hashTable[mod] == null)
        {
            this.hashTable[mod] = new LinkedList<>();
            this.hashTable[mod].insert(data);
        }
        else
        {
            this.hashTable[mod].insert(data);
        }
    }
    public boolean isExist(Integer data)
    {
        int mod = data % this.maxSize;
        if(this.hashTable[mod] == null)
            return false;
        return this.hashTable[mod].isExist(data);
    }
    @Override
    public String toString()
    {
        String s = "";
        int i = 0;
        for(LinkedList<Integer> temp : this.hashTable)
        {
            if(temp != null)
                s += (i++)+"  =====>> "+ temp.toString() + "\n";
            else
                s+=(i++) +"  =====>> "+ "\n";
           // System.out.println("Check 1");
        }
        return s;
    }
}
class LinkedList<AnyType>
{
    Node<AnyType> root;
    int size;
    
    LinkedList()
    {
        root  = null;
        size = 0;
    }
    
    public void insert(AnyType data)
    {
        size +=1;
        root = new Node<>(data, root);
    }
    
    public boolean isExist(AnyType data)
    {
        Node<AnyType> nextNode = this.root;
        if(root.data == data)
            return true;
        while(nextNode.next != null)
        {
            //System.out.println("Check 2");
            if(nextNode.data.equals(data))
                return true;
            nextNode = nextNode.next;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        Node<AnyType> nextNode = this.root;
        String s = "";
        while(nextNode.next != null)
        {
            //System.out.println("Check 3");
            s+=nextNode.data +" ---> ";
            nextNode = nextNode.next;
        }
        s += nextNode.data;
        return s;
    }
    
    private class Node<AnyType>
    {
        AnyType data;
        Node<AnyType> next;

        public Node(AnyType data, Node<AnyType> next) 
        {
            this.data = data;
            this.next = next;
        }
        
    }
}

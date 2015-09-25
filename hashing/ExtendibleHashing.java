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
public class ExtendibleHashing 
{
    
}

class ExtendedHashTable
{
    int maxSize;
    LinkedList<Integer> [] hashTable;
    ExtendedHashTable()
    {
        this.maxSize = 2;
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


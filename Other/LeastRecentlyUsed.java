
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
public class LeastRecentlyUsed 
{

    HashMap<Integer, LinkedNode> map; 
    LinkedNode head;
    LinkedNode tail;
    int maxSize = 0;
    public LeastRecentlyUsed(int size)
    {
        maxSize = size;
        map = new HashMap<>();
        head = null;
        tail = null;
    }
    
    //removes node from the linkedList
    public boolean remove(LinkedNode node)
    {
        if(node == null) return false;
        if(node.previous != null) node.previous.next = node.next;
        if(node.next != null) node.next.previous = node.previous;
        node.next = null;
        node.previous = null;
        node = null;
        return true;    
    }
    
    //adds node to from of the linkedList
    //for moving already used entry to front
    public boolean addFirst(LinkedNode node)
    {
        if(node == null) return false;
        if(head == null) 
        {
            head = node;
            tail = node;
        }
        else
        {
            head.previous = node;
            node.next = head;
            head = node;
        }
        return true;
    }
    
    //removes the node from the linkedList and removes element from the hash map
    //removes the complete entry from LRU
    public boolean removeKey(int key)
    {
        LinkedNode node = map.get(key);
        this.remove(node);
        map.remove(key);
        return true;
    }
    
    //adds node to least recently useed
    //adds complete new value to map
    public boolean setValue(int key, String value)
    {
        this.removeKey(key);
        
        if(maxSize <= map.size() && tail != null)
            this.removeKey(tail.key);
        LinkedNode node = new LinkedNode(key, value);
        this.addFirst(node);
        this.map.put(key, node);
        return true;
    }
    
    //prints linkedList
    public void printLinkedList()
    {
        LinkedNode tempNode = head;
        while(tempNode != null)
        {
            System.out.print(tempNode +" --> ");
            tempNode = tempNode.next;
        }
            
    }
    
    //gets least recently used to user
    //moves that node to front
    public String getValue(int key)
    {
        if(!this.map.containsKey(key))
            return null;
        LinkedNode item = this.map.get(key);
        if(item != head)
        {
            //System.out.println("Check");
            this.remove(item);
            this.addFirst(item);
        }
        return item.value;
    }
    
    
    @Override
    public String toString()
    {
        return this.map.toString();
    }
    public static void main(String[] args) 
    {
        LeastRecentlyUsed lru = new LeastRecentlyUsed(10);
        int [] keys = new int[]{1,2,3,4,5,6,7,8,9,0};
        String [] values = new String[]{"Hello", " World,", " This ", "is ", "Kushagra", " Trivedi. ", "I ", "am ", "a", " Googler"};
        for(int i = 0 ; i < keys.length ; i++)
        {
            lru.setValue(keys[i], values[i]);
        }
        System.out.println(lru);
        lru.printLinkedList();
        System.out.println(lru.getValue(5));
        System.out.println(lru);
        lru.printLinkedList();
    }
    
    private class LinkedNode
    {
        int key;
        String value;
        LinkedNode previous;
        LinkedNode next;
        public LinkedNode(int key, String value)
        {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public String toString()
        {
            return "("+this.key+" , "+value+")";
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_2;

/**
 *
 * @author Kushagra
 */
public class LinkedList 
{
    Node root;
    public LinkedList()
    {
        root = null;
    }
    
    public void insert(int data)
    {
        root = new Node(root, data);
    }
    
    public void remove(int data)
    {
        Node tempNode = root;
        if(root.data == data)
            root = root.next;
        else
        {
            while(tempNode.next!= null && tempNode.next.data != data)
            {
                tempNode= tempNode.next;
            }
            if(tempNode.next == null)
            {
                throw new java.util.NoSuchElementException("cannot delete");
            }
            if(tempNode.next.data == data)
            {
                tempNode.next = tempNode.next.next;
            } 
        }
    }
    
    public LinkedList corruptInserted()
    {
        Node node1 = new Node(null, 1);
        Node node2 = new Node(null, 2);
        Node node3 = new Node(null, 3);
        Node node4 = new Node(null, 4);
        Node node5 = new Node(null, 5);
        this.root = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node3;
        return this;
    }
    
    public LinkedList reverse()
    {
        LinkedList list = new LinkedList();
        Node tempNode = this.root;
        while(tempNode.next != null)
        {
            list.insert(tempNode.data);
            tempNode = tempNode.next;
        }
        list.insert(tempNode.data);
        return list;
    }
    
    public void clearList()
    {
        this.root = null;
    }
    
    public void display()
    {
        Node tempNode = root;
        while(tempNode.next != null)
        {
            System.out.print(tempNode.data+" ====>> ");
            tempNode = tempNode.next;
        }
        System.out.print(tempNode.data);
        System.out.println();
    }
    public LinkedList makeTempList()
    {
        this.insert(1);
        this.insert(2);
        this.insert(3);
        this.insert(4);
        this.insert(3);
        this.insert(4);
        this.insert(4);
        this.insert(5);
        this.insert(6);
        this.insert(7);
        return this;
    }
    class Node
    {
        Node next;
        int data;

        public Node(Node next, int data) 
        {
            this.next = next;
            this.data = data;
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doublylinkedlist;

import java.util.Iterator;

/**
 *
 * @author Kushagra
 * @param <T>
 */
public class DoublyLinkedList<T>  implements Iterable<T>
{
    Node<T> head;
    
    
    DoublyLinkedList()
    {
        head = null;
    }
    
    private Node<T> getLast()
    {
        Node<T> nextNode = head;
        while(nextNode.next != null)
        {
            nextNode = nextNode.next;
        }
        return nextNode;
    }
    
    private T remove(int index)
    {
        int count = 1;
        Node<T> nextNode = head;
        while(count > index)
        {
            nextNode = nextNode.next;
            count+=1;
        }
        nextNode.next = nextNode.next.next;
        return null;
    }
    
    public void displayFromLast()
    {
        Node<T> lastNode = this.getLast();
        while(lastNode.previous != null)
        {
            T data = lastNode.data;
            lastNode = lastNode.previous;
            System.out.print(data+" <===> ");
        }
        System.out.print(lastNode.data+"\n");
    }
    
    public void displayList()
    {
        Node<T> nextNode = head;
        Iterator<T> itr = this.iterator();
        while(itr.hasNext())
        {
            T data = itr.next();
            System.out.print(data+" <===> ");
        }
        System.out.print("null\n");
    }
    
    public void insert(T data)
    {
        if(head == null)
        {
            head = new Node<>(data, head, head);
            return;
        }
        
        head = new Node<>(data, head, null);
        head.next.previous = head;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        list.insert(5);
        list.displayList();
        list.displayFromLast();
    }

    @Override
    public Iterator<T> iterator() 
    {
        return new ListIterator<>();
    }
    
    private class Node<T>
    {
        private T data;
        private Node next;
        private Node previous;
        public Node(T data, Node next, Node previous) 
        {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }
    
    private class ListIterator<T> implements Iterator<T>
    {
        Node<T> nextNode = (Node<T>) head;
        @Override
        public boolean hasNext() 
        {
            return nextNode != null;
        }

        @Override
        public T next() 
        {
            if(!hasNext())
            {
                throw new java.util.NoSuchElementException("Element does not exist");
            }
            T data = nextNode.data;
            nextNode = nextNode.next;
            return data;
        }
    }
    
}


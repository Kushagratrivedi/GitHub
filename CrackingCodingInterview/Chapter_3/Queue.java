/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_3;

/**
 *
 * @author Kushagra
 */
public class Queue 
{
    Node first, last;
    Queue()
    {
        first = null;
        last = null;
    }
    public void enqueue(int data)
    {
        if(first == null)
        {
            first = new Node(first, data);
            last = first;
        }
        else
        {
            last.next = new Node(null, data);
            last = last.next;
        }
    }
    public int dequeue()
    {
        if(first != null)
        {
            int data = first.data;
            first = first.next;
            return data;
        }
        throw new java.lang.NullPointerException("Queue is empty");
        
    }
    
}

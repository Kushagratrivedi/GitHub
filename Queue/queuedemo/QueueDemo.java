/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queuedemo;

/**
 *
 * @author Kushagra
 */
public class QueueDemo 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Queue queue = new Queue(10);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        queue.enQueue(4);
        queue.enQueue(5);
        
        System.out.println(queue);
        
        while(queue.hasElement())
        {
            queue.deQueue();
            System.out.println(queue);
        }
    }
    
}
class Queue
{
    private int top;
    private int index;
    private int maxSize;
    private int [] queue;
    public Queue(int maxSize)
    {
        this.maxSize = maxSize;
        queue = new int[maxSize];
        top=0;
        index = 0;
    }
    
    public boolean hasElement()
    {
        return index >= 0;
    }
    
    public void enQueue(int element)
    {
        queue[top++] = element;
    }
    
    public int deQueue()
    {
        int indexValue = index++;
        int value = queue[indexValue];
        queue[indexValue] = 0;
        return value;
    }
    @Override
    public String toString()
    {
        String display = "";
        for(int element : queue)
        {
            display+="------\n";
            display+="|  "+element+"  |\n";
        }
        display+="------\n";
        return display;
    }
    
    
}

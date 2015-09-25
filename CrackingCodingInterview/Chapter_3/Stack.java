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
public class Stack 
{
    Node top;
    public Stack()
    {
        top = null;
    }
    public void push(int data)
    {
        top = new Node(top, data);
    }
    public boolean isEmpty()
    {
        return top == null;
    }
    public int pop()
    {
        if(top != null)
        {
            int data = top.data;
            top = top.next;
            return data;
        }
        throw new java.lang.NullPointerException("Top of the stack is null");
    }
    public Stack sort()
    {
        Stack temp = new Stack();
        while(!this.isEmpty())
        {
            int value = this.pop();
            while(!temp.isEmpty() && temp.peek() > value)
            {
                this.push(temp.pop());
            }
            temp.push(value);
        }
        return temp;
    }
    
    public int peek()
    {    if(top != null)
        {
            int data = top.data;
            return data;
        }
        throw new java.lang.NullPointerException("Top of the stack is null");
    }
    public void printStack()
    {
        Stack tempStack = new Stack();
        tempStack.top = this.top;
        System.out.println("---");
        while(!tempStack.isEmpty())
        {
            int value = tempStack.pop();
            System.out.println("|"+value+"|");
            System.out.println("---");
        }

    }
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

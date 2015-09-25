/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stack;

/**
 *
 * @author Kushagra
 */
public class StackDemo
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        Stack stack = new Stack(10);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        
        System.out.println(stack.toString());
        
        while(!stack.isEmpty())
        {
            int value = stack.pop();
            System.out.println(stack.toString());
            //System.out.println(value);
        }
    }
    
    
    
  
}

class Stack
{
    private final int maxSize;
    public int [] stack;
    public int top;
    public Stack(int size)
    {
        maxSize = size;
        stack = new int[maxSize];
        top = -1;
    }
    public boolean isEmpty()
    {
        return top == -1;
    }
    public boolean isOverFlow()
    {
        return top == maxSize -1;
    }
    public void push(int element)
    {
        stack[++top] = element;
    }
    public int pop()
    {
        int index = top--;
        int value = stack[index];
        stack[index] = 0;
        return value;
    }
    @Override
    public String toString()
    {
        String display = "";
        for(int element : stack)
        {
            display+="------\n";
            display+="|  "+element+"  |\n";
        }
        display+="------\n";
        return display;
    }
    
}

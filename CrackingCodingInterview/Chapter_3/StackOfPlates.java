/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



//INCOMPLETE



package Chapter_3;

import java.util.ArrayList;

/**
 *
 * @author Kushagra
 */
public class StackOfPlates 
{
    ArrayList<_Stack> stacks = new ArrayList<>();
    int size = 0;
    int capacity = 0;
    StackOfPlates(int capacity)
    {
        this.capacity = capacity;
    }
    public void push(int data)
    {
        
    }
}

class _Stack
{
    StackNode top;
    StackNode bottom;
    public _Stack()
    {
        this.top = null;
        this.bottom = null;
    }
    
    private void join(StackNode top, StackNode bottom)
    {
          if(top != null && bottom != null)
          {
              top.below = bottom;
              bottom.above = top;
          }
    }
    public void push(int data)
    {
        StackNode newNode = new StackNode(data);
        if(top == null)
        {
            top = newNode;
            bottom = top;    
        }
        this.join(newNode, top);
        top = newNode;
    }
    
    public int pop()
    {
        StackNode dataNode = top;
        top = top.below;
        top.above.below = null;
        top.above = null;
        return dataNode.data;
        
    }
    
    public int peek()
    {
        return top.data;
    }
    
    public boolean isEmpty()
    {
        return top == null;
        
    }
    
    public int removeBottom()
    {
        StackNode b  = bottom;
        if(bottom != null)
        {
            
            bottom = bottom.above;
        }
        if(bottom != null)
        {
            
            bottom.below = null;
        }
        return b.data;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private class StackNode
    {
        int data;
        StackNode above;
        StackNode below;
        public StackNode(int data)
        {
            this.data = data;
        }
    }
}

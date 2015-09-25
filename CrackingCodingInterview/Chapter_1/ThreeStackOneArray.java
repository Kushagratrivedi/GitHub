/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_1;

/**
 *
 * @author Kushagra
 */
public class ThreeStackOneArray 
{
    int [] stack;
    int [] stackPointers;
    int size;
    ThreeStackOneArray(int size)
    {
        this.size = size;
        stack = new int[size * 3];
        stackPointers = new int[]{0, 0, 0};
    }
    public void push(int stackIndex, int data)
    {
        int index = stackIndex * size + 1 + stackPointers[stackIndex];
        stackPointers[stackIndex]+=1;
        stack[index] = data;
    }
    public int pop(int stackIndex)
    {
        int index = stackIndex * size + stackPointers[stackIndex];
        stackPointers[stackIndex]-=1;
        int value = stack[index];
        stack[index] = 0;
        return value;
    }
    public void printStacks()
    {
        
        for(int i = 0 ; i < size * 3 ; i ++)
        {
            if(i == size || i == (2 * size))
            {
                System.out.println();
            }
            System.out.print(stack[i] +" | ");
        }
    }
    
}

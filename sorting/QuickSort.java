/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

/**
 *
 * @author Kushagra
 */
public class QuickSort 
{
    int [] arr;
    QuickSort(int [] arr)
    {
        this.arr = arr;
    }
    
    public void sort()
    {
        sort(this.arr, 0, arr.length - 1);
    }
    
    private void sort(int [] arr, int left, int right)
    {
        if(left < right) // REQUIRED TO AVOID INFINITY
        {
            int partition = partition(arr, left, right); // SET THE ELEMENT TO ITS SORTED POSITION
            sort(arr, 0, partition - 1); //RECURSIVELY SET ALL ELEMENTS ON THE LEFT
            sort(arr, partition + 1, right);
        }
    }
    
    
    public void printArray()
    {
        for(int i: this.arr)
        {
            System.out.print(i+" | ");
        }
        System.out.println();
    }
            
    
    private int partition(int [] arr, int left, int right)
    {
        int pivot = arr[left]; // SET THE FIRST ELEMENT AS A PIVOT
        int l = left + 1; // LEFT INDEX
        int r = right;// RIGHT INDEX
        
        while(l <= r) //WHILE LEFT INDEX IS LESS OR EQUAL TO RIGHT
        {
            while(l <= right && pivot > arr[l]) // IF PIVOT IS BIGGER THAN ELEMENT INCREASE LEFT
                l++;
            while(r >= left && arr[r] >  pivot) // IF PIVOT IS LESS THAN ELEMENT DECREASE RIGHT
                r--;
            if(l < r) // IF LEFT INDEX IS LESS THAN RIGHT SWAP
                swap(arr, l, r);
        
        }
        if(l > r)
        {
           // System.out.println(left+ " -"+ r);
            swap(arr, left, r);
        }
        //this.printArray();
        return r; // RETURN POSITON OF PIVOT
        
    }
    
    private void swap(int [] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

 }


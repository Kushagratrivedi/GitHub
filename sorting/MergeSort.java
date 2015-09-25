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
public class MergeSort 
{
    int [] arr;
    int size;
    public MergeSort(int [] arr)
    {
        this.arr = arr;
        size = this.arr.length;
    }
        public void printArray()
    {
        for(int i: this.arr)
        {
            System.out.print(i+" | ");
        }
        System.out.println();
    }
    public void sort()
    {
        sort(this.arr, 0, size - 1);
    }
    
    private void sort(int [] arr, int start, int end)
    {
        if(start < end)
        {
            int mid = (start + end) / 2;
            sort(arr, start, mid);
            sort(arr, mid + 1, end);
            merge(arr, start, mid, end);
        }
    }

    private void merge(int[] arr, int start, int mid, int end) 
    {
        while(mid >= start && mid < end)
        {
            if(arr[mid] > arr[end])
            {
                swap(arr, mid, end);
                mid--;
            }
            else
            {
                end--;
            }
        }
    }
    
     
    private void swap(int [] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    
}

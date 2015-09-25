/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;

/**
 *
 * @author Kushagra
 */
public class Heap 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int [] array = {4,1, 3, 2, 16, 9, 10 , 14, 8 , 7};
        Heap heap = new Heap();
        heap.displayArray(array);
        heap.displayArray(heap.heapSort(array));
        // TODO code application logic here
    }
    private void displayArray(int [] array)
    {
        for(int i:array)
        {
           System.out.print(i + " ---->> ");
        }
        System.out.println();
    }
    private int left(int index)
    {
        return (index * 2) + 1;
    }
    
    private int right(int index)
    {
        return (index * 2) + 2;
    }
    
    private void  maxHeapify(int [] array, int index)
    {
        
        int left = this.left(index);
        int right = this.right(index);
        int largest;
        if(left < array.length && array[left] > array[index])
        {
            largest = left;
        }
        else
        {
            largest = index;
        }
        if(right < array.length && array[right] > array[largest])
        {
            largest = right;
        }
        if(largest != index)
        {
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            this.maxHeapify(array, largest);
        }
        
    }
    private int [] buildMaxHeap(int [] array)
    {
        for(int i = array.length - 1  ; i  >= 0 ; i --)
        {
           // this.displayArray(array);
            this.maxHeapify(array, i);
        }
        return array;
    }
    private int [] heapSort(int [] array)
    {
         int heapSize = array.length-1;
         this.buildMaxHeap(array);
         for(int i = heapSize ; i > 1 ; i --)
         {
             int temp = array[i];
             array[1] = array[i];
             array[i] = temp;
             heapSize = heapSize - 1;
             this.maxHeapify(array, 1);
         }
         return array;
    }
    
}

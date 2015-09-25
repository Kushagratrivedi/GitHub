/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice2;

/**
 *
 * @author Kushagra
 */
public class Partition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int [] arr = {45,54,67,56,87,89,65,35,64,4};
        Partition p = new Partition();
        p.printArray(arr);
        p.partition(arr, arr.length - 1, 6);
        p.printArray(arr);
    }
    
    public static void partition(int [] arr, int len, int pivot)
    {
        int pivot_element = arr[pivot];
        int left = 0;
        int right = len;
        while(left < right)
        {
            while(arr[left] < pivot_element)
                left++;
            while(arr[right] > pivot_element)
                right--;
            if(left < right)
            {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }
    }
    
    public void printArray(int [] arr)
    {
        System.out.println();
        for(int i : arr)
        {
            System.out.print(i +" | ");
        }
    }
    
    
}

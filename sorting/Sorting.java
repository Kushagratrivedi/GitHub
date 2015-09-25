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
public class Sorting 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //QuickSort
        QuickSort qs = new QuickSort(new int[]{20,15,9,7,24,6,3});
        qs.printArray();
        qs.sort();
        qs.printArray();
        
        MergeSort ms = new MergeSort(new int[]{20,15,9,7,24,6,3});
        ms.printArray();
        ms.sort();
        ms.printArray();
        
        //Bubble Sort
        System.out.println("Bubble Sort");
        int [] a = giveRandomIntArray(15);
        printArray(a);
        printArray(bubbleSort(a));
        
        //Selection Sort
        System.out.println("\n\nSelection Sort");
        a = giveRandomIntArray(20);
        printArray(a);
        printArray(selectionSort(a));
        
        //Quick Sort
        System.out.println("\n\nQuick Sort");
        a = giveRandomIntArray(10);
        printArray(a);
        printArray(quickSort(a, 0, a.length-1));
        
        //Insertion Sort
        System.out.println("\n\nInsertion Sort");
        a = giveRandomIntArray(10);
        printArray(a);
        printArray(insertionSort(a));
        
        //Merge Sort
        System.out.println("\n\nMerge Sort");
        a = giveRandomIntArray(10);
        printArray(a);
        printArray(mergeSort(a));
        
        //Heap Sort - NOT WORKING YET
        System.out.println("\n\nHeap Sort  - NOT WORKING YET");
        a = giveRandomIntArray(10);
        printArray(a);
        printArray(heapSort(a));
        
        System.out.println("\n\nRadix Sort");
        a = giveRandomIntArray(10);
        printArray(a);
        printArray(radixSort(a));
        
        
    }
    
    public static int [] giveRandomIntArray(int size)
    {
        int [] a = new int[size]; 
        java.util.Random random = new java.util.Random();
        for(int i= 0 ;i < size ; i ++)
        {
            a[i] = random.nextInt(100);
        }
        return a;
    }
    
    public static int[] swap(int [] a , int i, int j)
    {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return a;
    }
    public static void printArray(int [] a)
    {
        System.out.println();
        for(int i: a)
        {
            System.out.print(i + " --> ");
        }
    }
    
    // Bubble Sort
    // O(N ^ 2) runtime
    // Stable algorithm
    // O(n) swaps
    public static int [] bubbleSort(int [] a)
    {
        for(int i = 0 ; i < a.length ;i++)
        {
            for(int j = i + 1 ; j < a.length ; j++)
            {
                if(a[i] > a[j])
                    swap(a, i, j);
            }
        }
        return a;
    }
    
    public static int [] selectionSort(int [] a)
    {
        int i, j , k;
        for(i = 0 ; i < a.length ; i ++)
        {
            k = i;
            for(j = i + 1 ; j < a.length ; j++)
            {
                if(a[k] > a[j])
                    k = j;
            }
            if(k != i)
            {
                swap(a, i , k);
            }
        }
        return a;
    }
    
    public static int [] quickSort(int [] a, int left, int right)
    {
        if(left < right)
        {
            int i = partition(a, left, right);
            quickSort(a, left, i - 1);
            quickSort(a, i+1, right);
        }
        return a;
    }
    
    private static int partition(int [] a, int left, int right)
    {
        int pivot = a[left];
        int l = left + 1;
        int r = right;
        
        while(l < r)
        {
            while(l <= right && pivot > a[l])
                l++;
            while(r >=left && pivot < a[r])
                r--;
            if(l < r)
                swap(a, l, r);
            
        }
        swap(a, left, r);
        return r;
    }
    
    public static int[] insertionSort(int [] a)
    {
        int i, j, newValue;
        for(i =  1; i < a.length ; i ++)
        {
            newValue = a[i];
            j = i;
            while(j > 0 && a[j-1] > newValue)
            {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = newValue;
        }
        return a;
    }
    
    public static int[] mergeSort(int [] a)
    {
        int n = a.length;
        
        if(n < 2)
            return a;
        int mid = n/2;
        int [] left = new int[mid];
        int [] right = new int[n - mid];
        System.arraycopy(a, 0, left, 0, left.length);
        
        for(int i = 0 ; i < right.length; i++)
        {
            int temp = mid++;
            right[i] = a[temp]; 
        }
        mergeSort(left);
        mergeSort(right);
        merge(a, left, right);
        return a;
    }
    
    private static void merge(int [] a, int [] left, int [] right)
    {
        int left_length = left.length;
        int right_length = right.length;
        int i = 0, j = 0, k = 0;
        while(i < left_length && j < right_length)
        {
            if(left[i] <= right[j])
            {
                a[k] = left[i];
                i+=1;
            }
            else
            {
                a[k] = right[j];
                j+=1;
            }
            k+=1;
        }
        while(i < left_length)
        {
            a[k] = left[i];
            k+=1;
            i+=1;
        }
        while(j < right_length)
        {
            a[k] = right[j];
            k+=1;
            j+=1;
        }
    }
    
    private static void max_heapify(int [] a, int i)
    {
        //System.out.println("check 1");
        int largest;
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        if(left < a.length && a[i] < a[left])
            largest = left;
        else
            largest  = i;
        if(right < a.length && a[right] > a[largest])
            largest = right;
        else
            largest = i;
        if(largest!= i)
        {
            //System.out.println(i + " -- " + largest);
            swap(a, i, largest);
            max_heapify(a, largest);
        }
    }
    
    private static void build_max_heap(int [] a)
    {
        for(int i = a.length; i>=0; i--)
        {
            max_heapify(a, i);
        }
            
    }
    
    public static int [] heapSort(int [] a)
    {
        build_max_heap(a);
        int length = a.length;
        for(int i = length - 1; i >= 0 ; i--)
        {
            swap(a, 0, i);
            length-=1;
            max_heapify(a, 0);
        }
        return a;
    }
    private static int max(int [] a)
    {
        int max = Integer.MIN_VALUE;
        for(int i = 0 ; i < a.length ; i++)
        {
            if(a[i] > max)
                max = a[i];
        }
        //System.out.println("Max: "+max);
        return max;
    }
    private static int digits(int a[])
    {
        int max = max(a);
        int digits = 0;
        while(max != 0)
        {
            max/=10;
            digits+=1;
        }
        
        //System.out.println("Digits: "+ digits);
        return digits;
    }
    public static int [] radixSort(int [] a)
    {
        int digits = digits(a);
        
        for(int i = 0 ; i < digits ; i++)
        {   
            int [][] nos = new int [10][a.length];
            int [] count = new int [10];
            for(int j = 0 ; j < a.length ; j ++)
            {
                
                int temp = a[j];
                int digit = temp / ((int)Math.pow(10, i)) % 10;
                
                nos[digit][count[digit]] = temp;
                count[digit]+=1;
                
            }
            int k = 0;
            for(int p = 0 ; p < 10 ; p ++)
            {
                for(int q = 0 ; q < count[p] ; q++)
                {
                    a[k++] = nos[p][q];
                }
            }
        }
        return a;
    }
}


import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class PairWithSum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int [] values = {4,9,8,2,3,7,5,8,2,6,4,7,8,70,2,39,7,5,8,9,2,3,4,7,9,8,7,2,3,4,3};
        System.out.println(new PairWithSum()._getPairSum(values, 13));
        System.out.println(new PairWithSum().getPairSum(values, 13));
    }
    
    public HashSet<Pair> getPairSum(int [] arr, int sum)
    {
        HashSet<Pair> pairs = new HashSet<>();
        for(int i = 0 ; i < arr.length ; i++)
        {
            for(int j = i + 1 ; j < arr.length ; j++)
            {
                if(arr[i] + arr[j] == sum)
                    pairs.add(new Pair(arr[i], arr[j]));
            }
            
        }
        return pairs;
    }
    
    public HashSet<Pair> _getPairSum(int [] arr, int sum)
    {
        HashSet<Integer> pairs = new HashSet<>();
        HashSet<Pair> results = new HashSet<>();
        for(int i = 0 ; i < arr.length ; i++)
        {
            int difference = sum - arr[i];
            if(pairs.contains(difference) && !pairs.contains(arr[i]))
                results.add(new Pair(arr[i], difference));
            pairs.add(arr[i]);
                
        }
        return results;
    }
     
    private class Pair
    {
        int value1;
        int value2;
        public Pair(int v1, int v2)
        {
            value1 = v1;
            value2 = v2;
        }
        
        @Override
        public String toString()
        {
            return "("+this.value1+", "+this.value2+")";
        }
        
        @Override
        public boolean equals(Object o)
        {
            Pair p;
            if(o instanceof Pair)
                p = (Pair) o;
            else
                return false;
            return (this.value1 == p.value1 && this.value2 == p.value2)
                    ||(this.value1 == p.value2 && this.value2 == p.value1);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 23 * hash + this.value1;
            hash = 23 * hash + this.value2;
            return hash;
        }

    }
            
    
}

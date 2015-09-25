
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class MissingInteger 
{

    public static void main(String[] args) 
    {
        int [] values = new int[]{1,2,4,5,6,5,7,8,9};
        ArrayList<BitInteger> bitIntegers = getBitInteger(values);
        System.out.println(getMissingNumber(bitIntegers, 28));
    }
    
    public static int getMissingNumber(ArrayList<BitInteger> values,  int column)
    {
        if(column > BitInteger.INTEGER_SIZE) return 0;
        
        ArrayList<BitInteger> zeroBits = new ArrayList<>(values.size()/2);
        ArrayList<BitInteger> oneBits = new ArrayList<>(values.size()/2);
        
        for(BitInteger value : values)
        {
            if(value.get(column) == 1)
                oneBits.add(value);
            else
                zeroBits.add(value);
        }
        
        if(zeroBits.size() <= oneBits.size())
        {
            int v = getMissingNumber(zeroBits, column + 1);
            return v << 1;
        }
        else
        {
            int v = getMissingNumber(oneBits, column + 1);
            return (v << 1) | 1;
        }
        
    }

    private static ArrayList<BitInteger> getBitInteger(int[] values) 
    {
        ArrayList<BitInteger> bits = new ArrayList<>();
        for(int value : values)
        {
            bits.add(new BitInteger(value));
        }
        return bits;
    }
    
}

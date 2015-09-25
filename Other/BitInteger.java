/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class BitInteger
{
    public static int INTEGER_SIZE = 32;
    public boolean [] value;
   
    public BitInteger()
    {
        value = new boolean[INTEGER_SIZE];
    }
    public BitInteger(int _value)
    {
        this();
        int count = 0;
        while(_value > 0)
        {
            if((_value & 1) == 1) value[INTEGER_SIZE - 1 - count] = true;
            count++;
            _value >>=1;
        }
    }
    
    public int get(int index)
    {
        if(value[index]) return 1;
        return 0;
    }
    
    public void set(int _value, int index)
    {
        value[index] = _value == 1;
    }
    
    public void set(int index, boolean _value)
    {
        value[index] = _value;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb= new StringBuilder();
        for(boolean v : this.value)
        {
            sb.append(v ? 1 : 0);
        }
        return sb.toString();
    }
}

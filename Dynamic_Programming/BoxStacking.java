/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class BoxStacking 
{
    private Box[] sort(Box b1, Box b2)
    {
        return null;
    }
    
    private Box[] generateCombinations(Box b1, Box b2)
    {
        Box [] b = new Box[6];
        for(int i = 0 ; i < b.length ; i++)
        {
            b[i] = new Box();
        }
        return null;
    }
    
}

class Box implements Comparable<Box>
{
    int length;
    int height;
    int width;
    public Box(int l, int h, int w)
    {
        this.length = l;
        this.height = h;
        this.width = w;
    }
    public Box()
    {}

    @Override
    public int compareTo(Box o) 
    {
        if(this.area() > o.area())
            return 1;
        else if(this.area() < o.area())
            return -1;
        else
            return 0;
    }
    
    public int area()
    {
        return this.width * this.length;
    }
    
    public Box[] permutations()
    {
        int l = this.length, h = this.height, w = this.width;
        Box[] b = new Box[3];
        
        for(int i = 0 ; i < 6 ; i++)
        {
            int temp = l;
            l = 0;
        }
        
        return null;
    }
    
    
    @Override
    public String toString()
    {
        return "{(Height: "+this.height+"),(Length: "+length+"),(Width: "+width+")}";
    }
    
}

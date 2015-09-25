/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Kushagra
 */
public class PoisonTest 
{   
    public List<Bottle> generateBottles(int total)
    {
        List<Bottle> bottles = new ArrayList<>();
        for(int i = 0 ; i <= total ;i++)
        {
            bottles.add(new Bottle(i));
        }
        int random_poison = new java.util.Random().nextInt(1000);
        bottles.get(random_poison).setPoison(true);
        return bottles;
    }
    
    private List<TestStrip> generateStrips(int total)
    {
        List<TestStrip> strips = new ArrayList<>();
        for(int i = 0 ; i < total ; i++)
        {
            strips.add(new TestStrip(i));
        }
        return strips;
    }
    
    public int testBottles(List<Bottle> bottles)
    {
        ArrayList<ArrayList<Bottle>> strips = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++)
        {
            strips.add(new ArrayList<> ());
        }
        Iterator<Bottle> itr = bottles.iterator();
        while(itr.hasNext())
        {
            Bottle bottle = itr.next();
            int id = bottle.getId();
            for(int i = 0 ; i < Integer.toString(id).length() ; i++)  
            {
                if((id & 1) == 1)
                {
                    strips.get(i).add(bottle);
                }
                id>>=1;
            }
        }
        return bottle_test(strips);
        
    }
    
    

    private int bottle_test(ArrayList<ArrayList<Bottle>> strips) 
    {
        int index = 0;
        for(int i = 9 ; i >= 0 ; i--)
        {
            ArrayList<Bottle> strip = strips.get(i);
            if(check(strip))
            {
                index |= 1;
            }
            index<<=1;
        }
        return index;
    }

    private boolean check(ArrayList<Bottle> strip) 
    {
        boolean result = false;
        Iterator<Bottle> itr = strip.iterator();
        while(itr.hasNext())
        {
            result|=itr.next().isPoison();
        }
        return result;
    }
    
}


class TestStrip
{
    private final int id;
    public TestStrip(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}

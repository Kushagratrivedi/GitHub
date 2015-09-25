/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Kushagra
 */
public class BoxStacking 
{
    public int createStack(int [][] boxes)
    {
        ArrayList<Box> boxList = Box.createBoxes(boxes);
        Collections.sort(boxList, new BoxComparator());
        int maxHeight = 0;
        
        for(int i = 0 ; i < boxList.size() ; i++)
        {
            int height = createStack(boxList, i);
            maxHeight = maxHeight < height ? height : maxHeight;
        }
        
        return maxHeight;
    }

    private int createStack(ArrayList<Box> boxList, int i) 
    {
        Box bottom = boxList.get(i);
        int maxHeight = 0;
        for(int j = i + 1 ; j < boxList.size() ; j++)
        {
            if(boxList.get(j).canAbove(bottom))
            {
                int height = this.createStack(boxList, j);
                maxHeight = Math.max(maxHeight, height);
            }
        }
        maxHeight+=bottom.h;
        return maxHeight;
    }
    
    
    
    
}

class BoxComparator implements Comparator<Box>
{
    @Override
    public int compare(Box o1, Box o2) 
    {
        return o1.h - o2.h;
    }
    
}

class Box
{
    int l;
    int w;
    int h;
    
    Box(int l, int w, int h)
    {
        this.l = l;
        this.w = w;
        this.h = h;
    }

    public boolean canAbove(Box above)
    {
        return above.l < this.l && above.w < this.w && above.h < this.h;
    }
    
    public static ArrayList<Box> createBoxes(int [][] boxes)
    {
        ArrayList<Box> boxList = new ArrayList<>();
        for(int [] box : boxes)
        {
            boxList.add(new Box(box[0], box[1], box[2]));
        }
        return boxList;
    }
    
}

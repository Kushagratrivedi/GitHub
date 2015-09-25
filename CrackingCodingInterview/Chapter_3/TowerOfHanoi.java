/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_3;

/**
 *
 * @author Kushagra
 */
public class TowerOfHanoi 
{
    public void towerOfHanoi(int n)
    {
        Tower [] towers = new Tower[3];
        for(int i = 0 ; i < towers.length ; i++)
        {
            towers[i] = new Tower(i);
        }
        for(int i = n; i > 0 ; i--)
        {
            towers[0].add(i);
        }
        towers[0].moveDisks(n, towers[2], towers[1]);
    }
}
class Tower
{
    Stack disks;
    int index;
    Tower(int index)
    {
        this.index = index;
        disks = new Stack();
    }
     
    int getIndex()
    {
        return index;
    }
    
    void add(int value)
    {
        if(!disks.isEmpty() && disks.peek() <= value)
        {
            throw new RuntimeException("Cannot add");
        }
        else
            disks.push(value);
    }
    
    void moveTopTo(Tower t)
    {
        int a = this.disks.pop();
        t.add(a);
        System.out.println("Disk "+a+" moved from tower "+this.index+" to tower "+t.index);
    }
    
    void moveDisks(int n, Tower destination, Tower buffer)
    {
        if(n > 0)
        {
            this.moveDisks(n-1, buffer, destination);
            this.moveTopTo(destination);
            buffer.moveDisks(n-1, destination, this);
        }
        
    }
}

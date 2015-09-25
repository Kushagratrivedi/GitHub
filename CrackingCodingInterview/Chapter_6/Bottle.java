/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_6;

/**
 *
 * @author Kushagra
 */
public class Bottle
{
    private final int id;
    private boolean poison = false;
    public Bottle(int id)
    {
        this.id = id;
    }
    
    public void setPoison(boolean value)
    {
        poison = value;
        System.out.println(id);
    }

    public int getId() 
    {
        return id;
    }

    public boolean isPoison() 
    {
        return poison;
    }  
}
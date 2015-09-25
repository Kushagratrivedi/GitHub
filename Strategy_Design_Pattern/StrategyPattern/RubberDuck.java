/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StrategyPattern;

/**
 *
 * @author Kushagra
 */
public class RubberDuck extends Duck
{
    public RubberDuck()
    {
        this.fb = new NoWayFly();
        this.qb = new Silent();
    }
    @Override
    public void display() 
    {
        System.out.println("I am rubber duck.");
    }
    
}

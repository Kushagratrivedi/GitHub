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
 public class MallardDuck extends Duck
{
    public MallardDuck()
    {
        super();
        this.fb = new FlyWithWings();
        this.qb = new Quack();
    }
    @Override
    public void display() 
    {
        System.out.println("I am Mallard Duck.");
    }
    
}

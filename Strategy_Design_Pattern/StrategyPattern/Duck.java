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
public abstract class Duck 
{
    FlyingBehaviour fb;
    QuackBehaviour qb;
    Duck()
    {
         
    }
    public abstract void display();
    
    public void swim()
    {
        System.out.println("I am duck, I can swim.");
    }
    public void setFb(FlyingBehaviour fb)
    {
        this.fb = fb;
    }

    public void setQb(QuackBehaviour qb) 
    {
        this.qb = qb;
    }
    
    public void doFly()
    {
        this.fb.fly();
    }
    
    public void doQuack()
    {
        this.qb.quack();
    }
}

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
public class DuckDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //Super class reference - with child class object
        Duck duck = new MallardDuck();
        duck.display();
        duck.doFly();
        duck.doQuack();
        duck.swim();
        System.out.println();
        //same reference - other child class object
        duck = new RubberDuck();
        duck.display();
        duck.doQuack();
        duck.swim();
        duck.doFly();
        //changing the behaviour of object at runtime
        duck.setFb(new FlyWithRockets());
        duck.doFly();
    }
    
}

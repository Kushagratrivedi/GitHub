/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice2;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Kushagra
 */
public class PrintNumber 
{
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your bid: ");
        
        float value = input.nextFloat();
        System.out.println("Your bid is: "+value);
    }
    
}

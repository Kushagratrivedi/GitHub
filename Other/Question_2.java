
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Question_2 
{   
    public static void main(String args[])
    {
        int [] array1 = new int[5];
        int [] array2 = new int[5];
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the values of first array: ");
        for(int i = 0;i < 5 ; i++)
        {
            System.out.println("Enter the number "+(i+1)+": ");
            array1[i] = sc.nextInt();
        }
        System.out.println("Enter the values of second array: ");
        for(int i = 0;i < 5 ; i++)
        {
            System.out.println("Enter the number "+(i+1)+": ");
            array2[i] = sc.nextInt();
        }
        System.out.print("Common Elements: {");
        for(int i = 0 ; i < 5 ; i++)
        {
            for(int j = 0 ; j < 5 ; j++)
            {
                if(array1[i] == array2[j])
                {
                    
                    System.out.print(array1[i]+", ");
                }
            }
        }
        
        System.out.println("}");
        System.out.println("Done!");
    }
}

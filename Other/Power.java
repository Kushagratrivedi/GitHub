/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Power {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println(new Power().power(2, 20));
    }
    
    public int power(int num, int exp)
    {
        if(exp == 1)
            return num;
        else if(exp % 2 == 1)
            return num * this.power(num, (exp - 1) / 2 ) * this.power(num, (exp - 1) / 2 );
        else 
            return this.power(num, exp / 2) * this.power(num, exp / 2);
    }
    
}

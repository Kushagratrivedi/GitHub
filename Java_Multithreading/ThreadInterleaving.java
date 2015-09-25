
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 * 
 * Use of synchronized keyword: 
 * when we use synchronized keyword, thread will gain lock on intrinsic
 * lock that associated with every object. 
 */
public class ThreadInterleaving {

    private static int count = 0;
    
    public static void main(String[] args) 
    {
        
        Thread t3 = new Thread(() -> {
            for(int i = 0 ; i < 1000 ; i++) 
            {
                increment();
            }
        });
        
        Thread t4 = new Thread(() -> {
            for(int i = 0 ; i < 1000 ; i++) 
            {
                increment();
            }
        });
        
        t3.start();
        t4.start();
        try 
        {
            t3.join();
            t4.join();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(ThreadInterleaving.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Count: "+count);
    }
    
    public static synchronized void increment()
    {
        count++;
    }
    
}

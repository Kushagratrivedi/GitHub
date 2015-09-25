
import java.util.Scanner;
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
 * Volatile keyword is used to remove the effect of caching. Here t1 is 
 * running as a different thread and in order to optimize it checks that
 * it is never changing the value of running variable, so it will cache
 * the value of running variable, and even if other thread changes the 
 * value of running variable it wont reflect to t1, and continue to execute
 * In order to remove the effect of caching we use the volatile keyword,
 * it tell the thread that variable can change its value.
 */
public class ThreadCachingProblem
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        Processor p1 = new Processor();
        //thread created
        Thread t1 = new Thread(p1);
        //thread started
        t1.start();
        Scanner sc = new Scanner(System.in);
        //Ends thread execution
        System.out.println("Press return key to exit");
        sc.nextLine();
        p1.shutDown();
    }
    
}

class Processor implements Runnable
{
    
    private volatile boolean running = true;
    @Override
    public void run() 
    {
        while(running)
        {
            try 
            {
                Thread.sleep(100);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Hello");
        }
    }
    
    public void shutDown()
    {
        running = false;
    }
    
}

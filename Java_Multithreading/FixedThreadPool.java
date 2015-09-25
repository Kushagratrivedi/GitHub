
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
 * Executors.newFixedThreadPool(2) --> newFixedThreadPool is used to 
 * reuse the thread for the task, here we want to accomplish 5 task
 * using 2 threads, so 2 threads will start its execution and other 
 * 3 thread will wait in queue, and when running thread will complete
 * its execution, thread from the queue, will get chance to execute.
 * 
 * 
 */
public class FixedThreadPool 
{
    
    
    public static void main(String[] args) 
    {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for(int i = 0 ; i < 5 ; i++)
        {
            executor.submit(new Processor1(i));
        }
        executor.shutdown();
        
        System.out.println("All Task submitted");
        
        try 
        {
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(FixedThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("All Task completed.");
        
    }
    
}

class Processor1 implements Runnable
{
    private final int id;
    public Processor1(int id)
    {
        this.id = id;
    }
    @Override
    public void run() 
    {
        System.out.println("Started. Thread : " + this.id);
        try 
        {
            Thread.sleep(3000);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Processor1.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Completed. Thread : " + this.id);
    }
    
}

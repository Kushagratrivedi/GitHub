
import java.util.concurrent.CountDownLatch;
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
 */
public class CountDownLatchDemo 
{
    public static void main(String[] args) 
    {
        CountDownLatch latch = new CountDownLatch(3);
        
        ExecutorService executors = Executors.newFixedThreadPool(3);
        
        for(int i = 0 ; i < 3 ; i++)
        {
            executors.submit(new Processor2(latch));
        }
        
        executors.shutdown();
        
        //System.out.println("All Task Submitted.");
        
        try 
        {
            latch.await(1, TimeUnit.DAYS);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(CountDownLatchDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("All Task Completed.");
        
        
        
    }
    
}

class Processor2 implements Runnable
{
    CountDownLatch latch;
    
    public Processor2(CountDownLatch latch)
    {
        this.latch = latch;
    }
    
    @Override
    public void run() 
    {
        System.out.println("Thread Started.");
        try 
        {
            Thread.sleep(3000);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Processor2.class.getName()).log(Level.SEVERE, null, ex);
        }
        latch.countDown();
        
        //System.out.println("Thread Completed.");
        
    }
    
}

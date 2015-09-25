
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
public class BlockingQueueProdCons 
{
    
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    
    public static void main(String[] args) 
    {
        Thread t1 = new Thread(new Runnable(){

            @Override
            public void run() 
            {
                try 
                {
                    producer();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(BlockingQueueProdCons.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
        
        Thread t2 = new Thread(new Runnable()
        {

            @Override
            public void run() 
            {
                try
                {
                    consumer();
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            
        });
        
        t1.start();
        t2.start();
        
        try 
        {
            t1.join();
            t2.join();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(BlockingQueueProdCons.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void producer() throws InterruptedException
    {
        Random random = new Random();
        
        while(true)
        {
            Thread.sleep(100);
            queue.put(random.nextInt(100));
            System.out.println("Size of Queue: " + queue.size());
        }
        
    }
    
    public static void consumer() throws InterruptedException
    {
        Random random = new Random();
        while(true)
        {
            if(random.nextInt(1000000) == 0)
            {
                Thread.sleep(100);
                System.out.println("Element: " + queue.take() +" Size of Queue: " + queue.size());
            }
        }
        
    }
    
}


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
public class WaitNotifyProdCons 
{

    public static void main(String[] args) 
    {
        WaitNotifyProdCons wnpc = new WaitNotifyProdCons();
        Thread t1 = new Thread(new Runnable(){

            @Override
            public void run() 
            {
                try 
                {
                    wnpc.producer();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(WaitNotifyProdCons.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        Thread t2 = new Thread(new Runnable(){

            @Override
            public void run() 
            {
                try 
                {
                    wnpc.consumer();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(WaitNotifyProdCons.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitNotifyProdCons.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void producer() throws InterruptedException
    {
        synchronized(this)
        {
            System.out.println("Producer started.");
            wait();
            System.out.println("Resumed.");
        }
        
    }
    
    public void consumer() throws InterruptedException
    {
        synchronized(this)
        {
            Thread.sleep(2000);
            System.out.println("Consumer started.");
            Thread.sleep(3000);
            notify();
            Thread.sleep(5000);
        }
        
    }
    
}

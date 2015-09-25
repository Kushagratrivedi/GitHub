
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
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
public class LowLevelProdCons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Processor3 process = new Processor3();
        
        Thread t1 = new Thread(new Runnable()
        {

            @Override
            public void run() 
            {
                try 
                {
                    process.produce();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(LowLevelProdCons.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        Thread t2 = new Thread(new Runnable(){

            @Override
            public void run() 
            {
                try 
                {
                    process.consume();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(LowLevelProdCons.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LowLevelProdCons.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
}


class Processor3
{
    private Queue<Integer> list = new ArrayDeque<>(10);
    private Object lock = new Object();
    
    public void produce() throws InterruptedException
    {
        Random random = new Random();
        while(true)
        {
            synchronized(lock)
            {
                while(list.size() == 10)
                    lock.wait();
                list.add(random.nextInt(100));
                Thread.sleep(500);
                lock.notify();

            }
        }
        
    }
    
    public void consume() throws InterruptedException
    {
        Random random = new Random();
        while(true)
        {
        synchronized(lock)
            {
                while(list.size() == 0)
                    lock.wait();
                int value = list.poll();
                Thread.sleep(1000);
                lock.notify();
                System.out.println("Size of List: "+ list.size()+"; Element value: "+value);
            }    
        }
        
    }
    
    
}

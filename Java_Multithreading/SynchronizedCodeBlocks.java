
import java.util.ArrayList;
import java.util.List;
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
 * Synchronized Code Block: when we make the method synchronized it will,
 * gain intrinsic lock on the object, so other thread will not be able to 
 * and other thread will not be able to access other method, so will gain
 * lock for the body of the method.
 * 
 * Therefore, we will use multiple lock object in order to gain lock on the
 * two different method bodies.
 */
public class SynchronizedCodeBlocks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Worker work = new Worker();
        
        work.main();
    }
    
}

class Worker
{
    private Object lock1= new Object();
    private Object lock2 = new Object();
    
    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();
    
    public void main()
    {
        System.out.println("Application started.");
        long start = System.currentTimeMillis();
        
        Thread t1 = new Thread(this::process);
        Thread t2 = new Thread(this::process);
        
        t1.start();
        t2.start();
        
        try 
        {
            t1.join();
            t2.join();
        }
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        long end = System.currentTimeMillis();
        System.out.println("Time taken: "+ (end - start) +" List1 "
                + "size: "+ list1.size()+" List2 size: "+ list2.size());
    }
    
    public void process()
    {
        for(int i = 0 ; i < 1000 ; i++)
        {
            stageOne();
            stageTwo();
        }
    }
    
    private void stageOne()
    {
        Random random = new Random();
        synchronized(lock1)
        {
            try 
            {
                Thread.sleep(1);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            list1.add(random.nextInt(100));
        }
        
    }
    
    private synchronized void stageTwo()
    {
        Random random = new Random();
        synchronized(lock2)
        {
            try 
            {
                Thread.sleep(1);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            list2.add(random.nextInt(100));
        }
    }
} 

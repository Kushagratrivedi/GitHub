
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class ReentrantLockDemo 
{
    public static void main(String[] args) throws InterruptedException 
    {
        Processor4 process = new Processor4();
        
        Thread t1 = new Thread(() -> {
            process.firstThread();
        });
        
        Thread t2 = new Thread(process::secondThread);
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        process.displayCount();
    }
}
class Processor4
{
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();
    
    private void increment()
    {
        for(int i = 0 ; i < 1000 ; i++)
        {
            count++;
        }
        
    }
    
    public void firstThread()
    {
        
        lock.lock();
        try
        {
            increment();
            cond.await();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
        
    }
    
    public void secondThread()
    {
        lock.lock();
        try
        {
            increment();
            cond.signal();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
    
    public void displayCount()
    {
        System.out.println("Count is: " + count);
    }

}
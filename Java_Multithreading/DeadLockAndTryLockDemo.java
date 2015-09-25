
import java.util.Random;
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
public class DeadLockAndTryLockDemo {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException
    {
        Processor5 process = new Processor5();
        
        Thread t1 = new Thread(process :: firstThread);
        
        Thread t2 = new Thread(() -> {
            process.secondThread();
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        process.displayAccounts();
    }
    
}

class Processor5
{
    private final Account acc1= new Account(10000);
    private final Account acc2= new Account(10000);
    
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();
    
    public void firstThread()
    {
        Random random = new Random();
        //lock1.lock();
        //lock2.lock();
        acquireLocks(lock1, lock2);
        try
        {
            Account.transfer(acc1, acc2, random.nextInt(10000));
        }
        finally
        {
            lock1.unlock();
            lock2.unlock();
        }
    }
    
    public void secondThread()
    {
        Random random = new Random();
        //lock2.lock();
        //lock1.lock();
        acquireLocks(lock2, lock1);
        try
        {
            Account.transfer(acc2, acc1, random.nextInt(10000));
        }
        finally
        {
            lock1.unlock();
            lock2.unlock();
        }
    }
    
    private void acquireLocks(Lock lock1, Lock lock2)
    {
        //Do not forget to put this code in loop, as
        //if you will not put, then after returning
        //you will try to unlock that thread
        //which was never lockd.
        while(true)
        {
            boolean gotLock1 = lock1.tryLock();
            boolean gotLock2 = lock2.tryLock();
            if(gotLock1 && gotLock2)
                return;
            if(gotLock1)
                lock1.unlock();
            if(gotLock2)
                lock2.unlock();

        }
        
    }
    
    public void displayAccounts()
    {
        System.out.println("Account 1: "+acc1+"; Account 2: "+acc2);
    }
}

class Account
{
    private int amount = 0;
    public Account(int amount)
    {
        this.amount = amount;
    }
    
    private void deposite(int amount)
    {
        this.amount = this.amount + amount;
    }
    
    private void withdraw(int amount)
    {
        this.amount = this.amount -  amount;
    }
    
    public static void transfer(Account acc1, Account acc2, int amount)
    {
        acc1.withdraw(amount);
        acc2.deposite(amount);
    }
    
    @Override
    public String toString()
    {
        return Integer.toString(amount);
    }
}


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
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
 * If you do not use, newCacheThreadPool with semaphore and use
 * newFixedThreadPool alone that will work also in this case.
 */
public class Semaphores {

    private static Semaphore sem = new Semaphore(10);
    
    public static void main(String[] args) throws InterruptedException 
    {
        Connection connect = Connection.getInstance();
        
        ExecutorService executors = Executors.newCachedThreadPool();
        
        for(int i = 0 ; i < 200 ; i++)
        {
            executors.submit(() -> {
                try 
                {
                    doConnect(connect);
                }
                catch (InterruptedException ex)                
                {
                    Logger.getLogger(Semaphores.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
        executors.shutdown();
        
        executors.awaitTermination(1, TimeUnit.DAYS);
    }
    
    private static void doConnect(Connection connect) throws InterruptedException
    {
        sem.acquire();
        try
        {
            connect.connect();
        }
        finally
        {
            sem.release();
        }
        
    }
    
}

class Connection
{
    private static Connection connection;
    
    private int connections = 0;
    
    private Connection() {}
    
    public static Connection getInstance()
    {
        if(connection == null)
            connection = new Connection();
        return connection;
    }
    
    public void connect() throws InterruptedException
    {
        synchronized(this)
        {
            connections++;
        }
        
        Thread.sleep(2000);
        
        System.out.print("Before: " + this.connections);
        synchronized(this)
        {
            connections--;
            System.out.println("After: " + this.connections);
        }
        
        
    }
        
            
}

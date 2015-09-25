
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class CallableFuturesDemo {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException 
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        
        Future<Integer> future = executor.submit(() -> {
            int value = (int) (Math.random() * 4000);
            Thread.sleep(value);
            return value;
        });
        
        executor.shutdown();
        
        executor.awaitTermination(1, TimeUnit.DAYS);
        
        System.out.println("Returned Value: " + future.get());
        
    }
    
}

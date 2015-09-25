

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class CoinChange 
{
    public int minCoinRequired(int [] noc, int change)
    {
        int [] results = new int [change + 1];
        for(int i = 0 ; i < change ; i ++)
        {
            results[i] = Integer.MAX_VALUE;
        }
        return this.coinChanges(noc, change, results);
    }
    private int coinChanges(int [] noc, int change, int [] results)
    {
        int q = 0;
        if(change > 0)
        {
            results[0] = 0;
            for(int i = 1 ; i < results.length ; i++)
            {
                results[i] = this.min(noc, i, results) + 1;
                
                q = results[i];
            }
        }
        return q;
    }
    private int min(int [] noc, int change, int [] results)
    {
        int min = Integer.MAX_VALUE;
        for(int i = 0 ; i < noc.length ; i ++)
        {
            if(change - noc[i] >= 0)
            { 
                if(results[change - noc[i]] < min)
                {
                    
                    min = results[change - noc[i]];
                }
                    
            }
            
        }
        
        return min;
    }
   
    
}

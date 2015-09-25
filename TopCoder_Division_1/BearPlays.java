
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 * 
 *     	
 * Limak is a little bear who loves to play. Today he is playing by moving some stones between two piles of stones. Initially, one of the piles has A and the other has B stones in it.



 * Limak has decided to perform a sequence of K operations. In each operation he will double the size of the currently smaller pile. Formally, if the current pile sizes are labeled X and Y in such a way that X <= Y, he will move X stones from the second pile to the first one. After this move the new pile sizes will be X+X and Y-X.



 * You are given the ints A, B, and K. Determine the pile sizes after Limak finishes all his operations. Return the size of the smaller of those piles.



 * Formally, suppose that the final pile sizes are labeled P and Q in such a way that P <= Q. Return P.

 
 * Definition
    	
 * Class:	BearPlays
 * Method:	pileSize
 * Parameters:	int, int, int
 * Returns:	int
 * Method signature:	int pileSize(int A, int B, int K)
 * (be sure your method is public)
    
 
 * Notes
-	Pay attention to the unusual time limit.
 
 * Constraints
-	A and B will be between 1 and 1,000,000,000, inclusive. 

-	K will be between 1 and 2,000,000,000, inclusive.
 
 * Examples
0)	
    	
4
7
2
Returns: 5
The process will look as follows:
Initially, the pile sizes are 4 and 7.
First operation: Limak doubles the pile of size 4 by moving 4 stones from the other pile to this pile. The new pile sizes are 8 and 3.
Second operation: Limak doubles the pile of size 3. The final pile sizes are 5 and 6.
As 5 <= 6, the correct return value is 5.
1)	
    	
5
5
3
Returns: 0
The initial pile sizes are 5 and 5. In the first operation Limak will double one of them, so after the operation the new pile sizes will be 10 and 0. The second and third operation do nothing: in each of them Limak doubles the size of an empty pile.



As 0 ≤ 10, the correct return value is 0.

2)	
    	
2
6
1
Returns: 4
After the only operation the pile sizes will be 4 and 4, hence the correct return value is 4.
3)	
    	
2
8
2000000000
Returns: 2
4)	
    	
900000000
350000000
3
Returns: 300000000

 */
public class BearPlays {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println(bearPlays(2 , 8 , 2000000000));
        System.out.println(_bearPlays(2 , 8 , 2000000000));
    }
    // Brute-Force
    public static int bearPlays(int a, int b, int k)
    {
        System.out.println(System.currentTimeMillis());
        int _a  = Math.max(a, b); //7
        int _b = Math.min(a, b); //4
        
        for(int i = 0 ; i < k ; i ++) 
        {
            _a = _a - _b;//8 5
            _b = _b * 2;//3 6
            //System.err.println(_a + " --> "+_b);
            int temp = Math.max(_a, _b);//8
            _b = Math.min(_a, _b);//3
            _a = temp;
            //System.err.println(_a + " --> "+_b);
        }
        System.out.println(System.currentTimeMillis());
        return Math.min(_a, _b);
    }
    // Careful Bruteforce
    public static int _bearPlays(int a, int b, int k)
    {
        System.out.println(System.currentTimeMillis());
        if(k == 0)
            return Math.min(a, b);
        if(a == b)
            return 0;
        int temp = Math.max(a, b);
        b = Math.min(a, b);
        a = temp;
        
        
        int count = 1;
        ArrayList<Pair> list = new ArrayList<>();
        int temp1 = a;
        int temp2 = b;
        list.add(new Pair(a, b));
        
        while(true)
        {
            
            count+=1;
            temp1 = temp1 - temp2;
            temp2<<=1;
            if(temp2 != a && temp1 != b)
                break;
            
            list.add(new Pair(temp1, temp2));
            temp = Math.max(temp1, temp2);
            temp2 = Math.min(temp1, temp2);
            temp1 = temp;
            
        }
        
        int _temp = k % count;
        System.out.println(System.currentTimeMillis());
        if(_temp == 0)
            return Math.min(a, b);
        else
        {
            Pair p = list.get(_temp);
            return p.min();
        }
        
    }
    
    public static class Pair
    {
        int a;
        int b;
        public Pair(int a, int b)
        {
            this.a = a;
            this.b = b;
        }
        
        public int min()
        {
            return a < b ? a : b;
        }
    }
    
}

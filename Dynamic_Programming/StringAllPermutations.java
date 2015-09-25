
import java.util.Stack;

public class StringAllPermutations
{
    public void permutations(char [] c, int len, int k)
    {
        
        if(k == len)
          System.out.println(new String(c));
        else
        {
            for(int i = k ; i < len ; i++)
            {
                this.swap(c , i , k  ); System.out.println("permutation("+len+" ,"+k+") -- > " + new String(c));
                this.permutations(c, len, k + 1);
                this.swap(c , i , k  );
            }
        }
        
    }
    // NOT WORKING
    public void permutations_Iterative(char [] c)
    {
        System.out.println(new String(c));
        Stack<String> stack = new Stack<>();
        int k = 0;
        int i;
        while(true)
        {
            //System.out.println("check 1");
            
            for(i = k ; i < c.length ; i++)
            {
                this.swap(c, i, k);
                stack.push(new String(c));
                this.swap(c, i, k);
            }
            k+=1;
            if(k == c.length-1)
                break;
        }
        while(!stack.empty())
            System.out.println(stack.pop());
        
    }
    private void swap(char [] c, int i , int j)
    {
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
    }
    
}


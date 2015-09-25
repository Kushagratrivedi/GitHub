/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class EditDistance 
{
    /*******************************************************
    * Finding Edit Distance: Minimum number of operations required to change string 1 to string 2
    * E(i, j) = min{ E(i - 1, j - 1), if str1(i) == str2(j)
    *                E(i - 1, j - 1) + 1, if str(i) != str2(j)       
    *                E(i - 1, j) + 1
    *                E(i, j - 1) + 1
    *              }
     * @param c1 String 1
     * @param c2 String 2
     * @param len1 length of string 1
     * @param len2 length of string 2
     * @return minimum operations required to convert string 1 to string 2
    ********************************************************/
    
    // Recursive - Solution
    
    public int editDistance_Recursive(char [] c1, char [] c2, int len1, int len2)
    {
        if(len1 == 0 && len2 == 0)
            return 0;
        if(len1 == 0)
            return len2;
        if(len2 == 0)
            return len1;
        return this.min(this.editDistance_Recursive(c1, c2, len1, len2 - 1) + 1, 
                        this.editDistance_Recursive(c1, c2, len1 - 1, len2) + 1,  
                        this.editDistance_Recursive(c1, c2, len1 - 1, len2 - 1) + this.difference(c1[len1 - 1], c2[len2 - 1]));
    }
    
    //Dynamic Programming  - Solution
    
    public int editDistance_DP(char [] c1, char [] c2, int len1, int len2, int [][] results)
    {
        if(len1 == 0 && len2 == 0)
            return 0;
        if(results[len1 - 1][len2 - 2] >  -1)
            return results[len1 - 1][len2 - 1];
        for(int i = 1 ; i < len1 ; i++)
        {
            for(int j = 1 ; j < len2; j++)
            {
                
            }
        }
        return 0;
    }
    
    public int editDistance_(char [] c1, char [] c2, int len1, int len2)
    {
        int [][] results = new int[len1][len2];
        for(int i = 0 ; i  < len1 ; i ++)
        {
            for(int j = 0 ; j < len2 ; j++)
            {
                if(i == 0)
                    results[i][j] = i;
                else if(j == 0)
                    results[i][j] = j;
                else
                    results[i][j] = -1;
            }
        }
        return this.editDistance_DP(c1, c2, len1, len2, results);
    }
    
    private int difference(char c1, char c2)
    {
        return c1 == c2 ? 0 : 1;
    }
    
    private int min(int a, int b, int c)
    {
        int min = (b < c ? b : c);
        return a < (min) ? a : (min);
    }
    
}

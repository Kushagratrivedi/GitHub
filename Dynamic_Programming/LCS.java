/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class LCS 
{
    public char [][] L1;
    /* Let the input sequences be X[0..m-1] and Y[0..n-1] of lengths m and n respectively. 
       And let L(X[0..m-1], Y[0..n-1]) be the length of LCS of the two sequences X and Y. 
       Following is the recursive definition of L(X[0..m-1], Y[0..n-1]).
       If last characters of both sequences match (or X[m-1] == Y[n-1]) then
       L(X[0..m-1], Y[0..n-1]) = 1 + L(X[0..m-2], Y[0..n-2])
       If last characters of both sequences do not match (or X[m-1] != Y[n-1]) then
       L(X[0..m-1], Y[0..n-1]) = MAX ( L(X[0..m-2], Y[0..n-1]), L(X[0..m-1], Y[0..n-2])

        Examples:
        1) Consider the input strings “AGGTAB” and “GXTXAYB”. Last characters match for the strings. 
           So length of LCS can be written as: L(“AGGTAB”, “GXTXAYB”) = 1 + L(“AGGTA”, “GXTXAY”)
        2) Consider the input strings “ABCDGH” and “AEDFHR. Last characters do not match for the strings. 
           So length of LCS can be written as: L(“ABCDGH”, “AEDFHR”) = MAX ( L(“ABCDG”, “AEDFHR”), L(“ABCDGH”, “AEDFH”) )
    */
    // Recursion Method
    public int findLcs(char [] c1, int len1, char [] c2, int len2)
    {
        if(len1 == 0 || len2 == 0)
            return 0;
        else if(c1[len1 - 1] == c2[len2 - 1])
            return 1 + this.findLcs(c1, len1-1, c2, len2-1);
        else
            return this.max(this.findLcs(c1,len1 - 1, c2, len2), this.findLcs(c1,len1, c2, len2-1));
    }
    
    
    //Dynamic Programming Method
    public int findLcs_DP(char [] X, int m, char [] Y, int n)
    {
         int [][] L = new int[m+1][n+1];
         L1 = new char[m + 1][n + 1];
   int i, j;
  
   /* Following steps build L[m+1][n+1] in bottom up fashion. Note 
      that L[i][j] contains length of LCS of X[0..i-1] and Y[0..j-1] */
   for(i = 1 ; i <= m ; i++)
       L[i][0] = 0;
   for(j = 1 ; j <= n; j++)
       L[0][j] = 0;
   for (i=1; i<=m; i++)
   {
     for (j=1; j<=n; j++)
     {
       if (i == 0 || j == 0)
         L[i][j] = 0;
  
       else if (X[i-1] == Y[j-1])
       {
           L[i][j] = L[i-1][j-1] + 1;
           L1[i][j] = '\\';
       }
       else if(L[i-1][j] >= L[i][j-1])
       {
           L[i][j]= L[i-1][j];
           L1[i][j] = '|';
       }
       else if(L[i-1][j] <= L[i][j-1])
       {
           L[i][j]= L[i][j-1];
           L1[i][j] = '-';
       }
     }
   }
    
   /* L[m][n] contains length of LCS for X[0..n-1] and Y[0..m-1] */
   return L[m][n];
    }
     
    public void print_Lcs(char [] c1, int i, int j)
    {
        if(i == 0 || j == 0)
            return;
        if(L1[i][j] == '\\')
        {
            this.print_Lcs(c1, i-1, j-1);
            System.out.print(c1[i]);
        }
        else if(L1[i][j] == '|')
            this.print_Lcs(c1, i - 1, j);
        else        
            this.print_Lcs(c1, i, j-1);
           /*
        for(char[] c: L1)
        {
            System.out.println();
            for(char c5 : c)
                System.out.print(c5);
        */
        
    }
    
    
    private int max(int a, int b)
    {
        return b ^ ((a ^ b) & (a > b ? 1 : 0));
    }
    
}

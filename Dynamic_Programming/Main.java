/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Main 
{
    public static void main(String args[])
    {
        CoinChange cc = new CoinChange();
        int [] noc = new int[]{1, 2, 3};
        int change = 10;
        System.out.println(cc.minCoinRequired(noc, change));
        
        //Cutting - Rod Problem
        
        CuttingRod cr = new CuttingRod();
        System.out.println("Maximum Amount: "+cr.cuttingRod(4));
        System.out.println("Maximum Amount - Bottom Up : "+cr.cuttingRod_BottomUp(4));
        System.out.println("Maximum Amount - Recursion : "+cr.cuttingRod_Recursion(4));
        
        //LIS
        
        int[]  a = new int[]{1 , 5 , 2 , 6 , 7 , 1 , 9};
        int [] b = new int[]{1 , 12 , 7 , 25 , 6 , 31 , 9};
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
        System.out.println("Recursion: "+lis.LCS(a, a.length, 1));
        System.out.println("DP: "+lis.LCS(a, a.length));
        
        
        //LCS
        String c1 = "AGGTAB";
        String c2 = "GXTXAYB";
        LCS lcs = new LCS();
        System.out.println(lcs.findLcs(c1.toCharArray(), c1.length(), c2.toCharArray(), c2.length()));
        System.out.println(lcs.findLcs_DP(c1.toCharArray(), c1.length(), c2.toCharArray(), c2.length()));
        //lcs.print_Lcs(c1.toCharArray(), c1.length(), c2.length());
        
        //Edit - Distance
        char [] c3 = "ZEIL".toCharArray();
        char [] c4 = "TRIALS".toCharArray();
        EditDistance ed = new EditDistance();
        System.out.println("Edit Required: "+ed.editDistance_Recursive(c3, c4, c3.length, c4.length));
        
        //String ALL PERMUTATIONS   
        char [] c = "123".toCharArray();
        new StringAllPermutations().permutations(c, c.length, 0);
        //new StringAllPermutations().permutations_Iterative(c); <-- NOT WORKING
        
        //STRING COMBINATION
        System.out.println("COMBINATIONS");
        int [] arr = new int[]{0, 0, 0};
        new StringAllCombination().combination( arr,"123".toCharArray(), "123".length() , 0);
        
        //MINIMIM COST PATH
        System.out.println("MINIMUM COST PATH");
        int [][] cost = new int[][]{{1,3,5,8},{4,2,1,7},{4,3,2,3}};
        int __cost = new MinimumPathCost().minimumPathCost(cost);
        System.out.println("COST: "+__cost);
        
        
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_1;

import Chapter_17.MaxSumSubMatrix;
import Chapter_2.CircularCorrupt;
import Chapter_2.LinkedList;
import Chapter_2.LinkedListSum;
import Chapter_2.RemoveDuplicatesLinkedList;
import Chapter_2.RemoveMiddle;
import Chapter_2.SearchFromLast;
import Chapter_3.BracketBalance;
import Chapter_3.InfixToPostfix;
import Chapter_3.Stack;
import Chapter_3.TowerOfHanoi;
import Chapter_6.Bottle;
import Chapter_6.PoisonTest;
import Chapter_8.BalancedParenthesis;
import Chapter_8.BooleanEvaluation;
import Chapter_8.BoxStacking;
import Chapter_8.CoinChange;
import Chapter_8.NQueen_Backtracking;
import Chapter_8.PermutationWithDuplicates;
import Chapter_8.RecursiveMultiply;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Kushagra
 */
public class MainClass 
{
    public static void main(String args[])
    {
        /*****************************************************************************/
        /**************                                      *************************/
        /**************                CHAPTER 1             *************************/
        /**************                                      *************************/
        /*****************************************************************************/
        
        // QUESTION 1
        UniqueStringCharacter usc = new UniqueStringCharacter();
        System.out.println(usc.hasUniqueCharacters("Helo,Iam Kushgr"));
        
        // QUESTION 2
        ReverseCStyleString rcss = new ReverseCStyleString();
        System.out.println(rcss.reverse("I am Kushagra Trivedi".toCharArray()));
        
        // QUESTION 3
        RemoveDuplicateCharacter rdc = new RemoveDuplicateCharacter();
        System.out.println(rdc.removeDuplicates("Hello, I am Kushagra"));
        
        // QUESTION 4
        AnagramCheck ac = new AnagramCheck();
        System.out.println(ac.isAnagram("How are you doing sir?", "oHwuoy  rea ingdo? ris"));
        
        // QUESTION 5
        PutCharacters pc = new PutCharacters();
        System.out.println(pc.replaceCharacters("Hello, I am Kushagra Trivedi."));
        
        
        // QUESTION 6
        RotateMatrix rm1 = new RotateMatrix();
        char[][] c = {{'1','2','3','4','p'},{'5','6','7','8','q'},{'9','0','+','-','r'},{'/','*','&','%','s'},{'a','b','c','d','e'}};
        rm1.display(c);
        System.out.println();
        rm1.display(rm1.matrixRotation(c));
        
        // QUESTION 7
        System.out.println();
        ReplaceWithZero rwz= new ReplaceWithZero();
        rm1.display(c);
        System.out.println();
        rm1.display(rwz.replaceWithZero(c));
        
        // QUESTION 8
        StringRotation sr = new StringRotation();
        System.out.println(sr.isRotation("waterbottle", "erbotlewat"));
        
        /*****************************************************************************/
        /**************                                      *************************/
        /**************                CHAPTER 2             *************************/
        /**************                                      *************************/
        /*****************************************************************************/
        
        // QUESTION 1
        LinkedList list = new LinkedList();
        list.makeTempList();
        list.display();
        RemoveDuplicatesLinkedList rdll = new RemoveDuplicatesLinkedList();
        rdll.removeDuplicates(list).display();
        
        // QUESTION 2
        list.clearList();
        list.makeTempList();
        list.display();
        SearchFromLast sfl = new SearchFromLast();
        System.out.println(sfl.searchFromLast(list, 4));
        
        // QUESTION 3
        list.clearList();
        list.makeTempList();
        list.display();
        rdll.removeDuplicates(list);
        RemoveMiddle rm = new RemoveMiddle();
        rm.removeMiddleElement(list);
        list.display();
        
        // QUESTION 4
        LinkedList list1 = new LinkedList();
        list1.insert(5);
        list1.insert(1);
        list1.insert(3);
        list1.display();
        LinkedList list2 = new LinkedList();
        list2.insert(2);
        list2.insert(9);
        list2.insert(5);
        list2.display();
        LinkedListSum lls = new LinkedListSum();
        lls.sum(list1, list2).display();
        
        // QUESTION 5
        LinkedList list3 = new LinkedList();
        list3.corruptInserted();
        CircularCorrupt cc = new CircularCorrupt();
        System.out.println(cc.circularCorruptPoint(list3));
        
        /*****************************************************************************/
        /**************                                      *************************/
        /**************                CHAPTER 3             *************************/
        /**************                                      *************************/
        /*****************************************************************************/
        
        //Question 1
        ThreeStackOneArray tsoa = new ThreeStackOneArray(10);
        tsoa.push(0, 1);
        tsoa.push(0, 2);
        tsoa.push(0, 3);
        tsoa.push(0, 4);
        tsoa.push(0, 5);
        
        tsoa.push(1, 11);
        tsoa.push(1, 12);
        tsoa.push(1, 13);
        tsoa.push(1, 14);
        tsoa.push(1, 15);
        
        tsoa.push(2, 21);
        tsoa.push(2, 22);
        tsoa.push(2, 23);
        tsoa.push(2, 24);
        tsoa.push(2, 25);
        
        tsoa.printStacks();
        System.out.println();
        
        //Question 2
        TowerOfHanoi toh = new TowerOfHanoi();
        toh.towerOfHanoi(2);
        
        //Question 3
        Stack stack = new Stack();
        stack.push(7);
        stack.push(12);
        stack.push(4);
        stack.push(73);
        stack.push(5);
        stack.push(8);
        stack.push(0);
        stack.push(77);
        stack.printStack();
        //System.out.println();
        stack.sort().printStack();
        System.out.println();
        
        BracketBalance bb = new BracketBalance();
        String s = "{([][][]{{}})}";
        System.out.println(bb.bracketBalance(s));
        
        InfixToPostfix itp = new InfixToPostfix();
        String infix = "(a+b)*c/(e+f)";
        String postfix = itp.infixToPostfix(infix);
        System.out.println(postfix);
        
        
        /*****************************************************************************/
        /**************                                      *************************/
        /**************                CHAPTER 8             *************************/
        /**************                                      *************************/
        /*****************************************************************************/
        
        //QUESTION 14
        
        String expression = "1^0|0|1";
        boolean result = false;
        BooleanEvaluation be = new BooleanEvaluation();
        System.out.println("Count boolean recursive way: "+be.countEval(expression, result));
        System.out.println("Count boolean DP way: "+be.countEval(expression, result, new HashMap<>() ));
        
        
        //QUESTION 13
        
        System.out.println("Maximum Height of Boxes: "+ new BoxStacking().createStack(new int [][]{{2,1,5},{9,8,2},{5,3,7}}));
        
        //QUESTION 12
        
        ArrayList<Integer[]> colums = new ArrayList<>();
        Integer [] column = new Integer[8];
        new NQueen_Backtracking().nQueen(0, column, colums);
        int i = 0;
        for(Integer [] col : colums)
        {
            if(i == 8)
            {
                System.out.println();
                i=0;
            }
            for(Integer cell : col)
            {
                System.out.print(cell +" | ");
            }
            i+=1;
            System.out.println();
        }
        //System.out.println(colums);
        
        
        //QUESTION 11
        
        int [] denominations = {1, 2, 3};
        int change = 5;
        int [][] map = new int[denominations.length][change + 1];
        
        System.out.println("COIN CHANGE - RECURSIVE: "+ new CoinChange().coinChange(denominations, change));
        System.out.println("COIN CHANGE - DP: "+ new CoinChange().coinChange_DP(denominations, change, 0, map));
        //System.out.println("COIN CHANGE - DP - Iterative: "+ new CoinChange().coinChange_DP_iterative(denominations, change));
        
        
        //QUESTION 9
        System.out.println(new BalancedParenthesis().catalan(5));
        HashSet<String> set = new HashSet<>();
        new BalancedParenthesis().catalan_optimized(0, 5, 5, new char[10], set);
        System.out.println(set);
        
        //QUESITON 8
        System.out.println(new PermutationWithDuplicates().permutation("aab"));
        
        //QUESTION7
        System.out.println(new RecursiveMultiply().multiply(5, 7));
        System.out.println(new RecursiveMultiply().multiply_2(5, 7));
        
        
        
        /*****************************************************************************/
        /**************                                      *************************/
        /**************                CHAPTER 6             *************************/
        /**************                                      *************************/
        /*****************************************************************************/
        
        
        PoisonTest pt = new PoisonTest();
        List<Bottle> _list = pt.generateBottles(1000);
        System.out.println("Poisoned Strip: "+ pt.testBottles(_list));
        
        
        //MAX SUM SUBMATRIX

        MaxSumSubMatrix mssm = new MaxSumSubMatrix();
        mssm.maxSumSubMatrix(new int[][]{{2,1,-3,-4,5},{0,6,3,4,1},{2,-2,-1,4,-5},{-3,3,1,0,3}});
        System.out.println();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_8;

import java.util.ArrayList;

/**
 *
 * @author Kushagra
 */
public class NQueen_Backtracking 
{
    private static final int GRID_SIZE = 8;
    
    /*  ALGORITHM:
    * STARTS WITH 0TH ROW , AND 0TH COLUMN
    * TRY TO PLACE THE QUEEN AT CERTAIN ROW(STARTS WITH 0) IF YOU CAN PLACE IT THEN YOU ARE GOOD AND MOVE IT TO NEXT ROW
    * CHECK IF IT IS NOT VALID MOVE, THEN MOVE TO NEXT COLUMN
    * DO THIS TILL ROW  ==  GRID_SIZE AND ADD THE COLUMN TO LIST
    */
    
    
    
    //Colums  = Initially contains all zeros, it represents column 1, 2, .., GRID_SIZE
    //cols = it holds the colums with queens placed in it
    //row = starts with zero
    
    public void nQueen(int row, Integer [] colums, ArrayList<Integer []> cols)
    {
        if(row == GRID_SIZE)
            cols.add(colums.clone());
        for(int i = 0 ; i < GRID_SIZE; i++)
        {
            if(isValid(colums, row, i))
            {
                colums[row] = i; // IF FIRST QUEEN THEN COLUMN[ROW] = 0; , IF SECOND THEN COLUMN[ROW] = 1; , AND SO ON...
                this.nQueen(row + 1, colums, cols);
            }
        }
    }
    
    private boolean isValid(Integer[] colums, int row, int i) 
    {
        for(int row2 = 0 ; row2 < row ; row2++)
        {
            int column2 = colums[row2];
            
            if(column2 == i)
                return false;
            
            int columnDistance = Math.abs(column2 - i);
            int rowDistance = Math.abs(row - row2);
            
            if(rowDistance == columnDistance)
                return false;
            
            
        }
        return true;
    }
}

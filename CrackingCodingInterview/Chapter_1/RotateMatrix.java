/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_1;

/**
 * MATRIX ROTATION IN PLACE
 * @author Kushagra
 */


public class RotateMatrix 
{
    public char[][] matrixRotation(char[][] c)
    {
        // iterate number of layers in matrix
        for(int i = 0 ; i < c[0].length / 2 ; i++) // if 4 X 4 matrix, there two sub-matrix
        {
            //iterate 1 less then the number of columns in inner matrix
            
            for(int j = i ; j < c[0].length -i - 1 ; j++) // if 4 X 4 matrix, first 4 colums for outer  matrix, then 2 colunms for inner matrix
            {
                //temp <- top - left
                char temp = c[i][j];
                //top-left <- bottom - left
                c[i][j]= c[c.length - 1 - j][i];
                //bottom - left <- bottom - right
                c[c.length - 1-j][i] = c[c.length - 1-i][c.length - 1-j];
                // bottom - right <- top - right
                c[c.length - 1 - i][c.length - 1 - j] = c[j][c.length - 1- i ];
                // top - right <- temp
                c[j][c.length - 1- i] = temp;
            }
        }
        return c;
    }
    public void display(char[][] c)
    {
        for(char a[] : c)
        {
            for(char p: a)
            {
                System.out.print(p+"\t");
            }
            System.out.println();
        }
    }
}

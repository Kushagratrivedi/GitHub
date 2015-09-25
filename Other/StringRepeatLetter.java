/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class StringRepeatLetter 
{
    public static void main(String argd[])
    {
        String str1 = "Hello";
        System.out.println(hasRepeatLetter(str1));
        System.out.println(reverse(str1));
        String str2 = "elolH";
        System.out.println(isPermutation(str1, str2));
        System.out.println(compressString(str1));
        int [][] array = {{1,2,3,10},{4,0,6,11},{7,8,9,12}};
        arrayDisplay(array);
        arrayDisplay(rotate(array));
        arrayDisplay(makeRowColumnZero(array));
        int [] array1 = {4,3,34,3,5,34,534,43,5345};
        arrayDisplay(array1);
        arrayDisplay(insertionSort(array1));
        
    }
    private static void arrayDisplay(int [] array)
    {
        for(int value: array)
        {
            System.out.print(value+" ---> ");
        }
        System.out.println();
    }
    private static void arrayDisplay(int [][] array)
    {
        for(int [] row: array)
        {
            for (int cell: row)
            {
                System.out.print(cell+" ");
            }
            System.out.println();
        }
    }
    
    private static boolean hasRepeatLetter(String s)
    {
        for(int i = 0 ; i < s.length() ; i++)
        {
            for (int j = i+1; j < s.length(); j++)
            {
                if(s.charAt(i) == s.charAt(j))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static String reverse(String s)
    {
        StringBuilder b = new StringBuilder();
        for(int i = s.length()-1 ; i >= 0 ; i--)
        {
            b.append((char)s.charAt(i));
        }
        return new String(b);
        
    }
    
    private static boolean isPermutation(String s1, String s2)
    {
        boolean flag = false;
        if(s1.length() ==  s2.length())
        {
            for(int i = 0; i < s1.length() ; i++)
            {
                for (int j = 0 ; j<s2.length();j++)
                {
                    if(s1.charAt(i) == s2.charAt(j))
                    {
                        flag = true;
                        break;
                    }
                }
                
                if(flag == false)
                {
                    
                    return false;
                    
                }
                flag = false;
            }
            return true;
            
        }
        return false;
    }
    
    private static String compressString(String s)
    {
        s+=" ";
        int count = 1;
        StringBuilder str = new StringBuilder();
        char previousChar = s.charAt(0);
        for(int i = 1;i< s.length();i++)
        {
            if(previousChar == s.charAt(i))
            {
                count++;
                previousChar = s.charAt(i);
            }
            else
            {
                str.append(previousChar);
                str.append(count);
                count=1;
                previousChar = s.charAt(i);
            }
        }
        return new String(str);
    }
    
    private static int[][] rotate(int [][] array)
    {
        int [][] rotated = new int [array[0].length][array.length];
        int i = 0,j = 0;
        for(int [] rows: array)
        {
            for(int cell: rows)
            {
                rotated[i][j] = cell;
                i+=1;
            }
            i=0;
            j++;
        }
        
        return rotated;
    }
    
    private static int[][] makeRowColumnZero(int [][] array)
    {
        int [][] changed = new int [array.length][array[0].length];
        int row = 0, column = 0;
        for(int i = 0 ; i < array.length ; i++)
        {
            for(int j = 0 ; j < array[0].length ; j++)
            {
                if(array[i][j] == 0)
                {
                    row = i;
                    column = j;
                    //break;
                }
            }
        }
        for(int i = 0 ; i < array.length ; i++)
        {
            for(int j = 0 ; j < array[0].length ; j++)
            {
                if(i == row || j == column)
                {
                    changed[i][j] = 0;
                }
                else
                {
                    changed[i][j] = array[i][j];
                }
            }
        }
        
        return changed;
    }
    
    private static int[] insertionSort(int [] array1)
    {
        int [] array = array1;
        int i,j,key;
        for(j = 1 ; j < array.length ; j++)
        {
            key = array[j];
            i = j - 1;
            while(i >= 0 && array[i] >= key)
            {
                array[i + 1] = array[i];
                arrayDisplay(array);
                i = i - 1;
            }
            array[i+1] = key;
            arrayDisplay(array);
          
        }
        return array;
    }
    
}

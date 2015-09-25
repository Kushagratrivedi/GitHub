
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class DancingSequence 
{
    boolean flag = true;
    @SuppressWarnings("empty-statement")
    public static void main(String args[])
    {
        DancingSequence ds = new DancingSequence();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        try 
        {
            
            while(true)
            {
                System.out.println("Enter String: ");
                input = br.readLine();
                System.out.println("Dancing Sequence: "+ds.makeDancing(input));
                System.out.println("Do you want to continue? Y or N");
                if(!br.readLine().equalsIgnoreCase("Y"))
                    break;
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DancingSequence.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    public String makeDancing(String str)
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++)
        {
            int c = str.charAt(i);
            if(c >= 65 && c <= 90)
            {
                if(flag)
                {
                    sb.append((char)c);
                    flag = false;
                }
                else
                {
                    c+=32;
                    sb.append((char)c);
                    flag= true;
                }
            }
            else if(c >=97 && c <= 122)
            {
                if(flag)
                {
                    c-=32;
                    sb.append((char)c);
                    flag = false;
                }
                else
                {
                    sb.append((char)c);
                    flag = true;
                }
            }
            else
            {
                sb.append((char)c);
            }
        }
        return sb.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_1;

/**
 *
 * @author Kushagra
 */
public class PutCharacters 
{
    public String replaceCharacters(String str)
    {
        String replaceString = "%20";
        
        for(int i = 0 ; i < str.length() ; i++)
        {
            if(str.charAt(i)==' ')
            {
                str = this.replaceAt(str, i);
            }
        }
        return str;
    }
    public String replaceAt(String str, int index)
    {
        return str.substring(0, index)+"%20"+str.substring(index+1);
    }
    
}

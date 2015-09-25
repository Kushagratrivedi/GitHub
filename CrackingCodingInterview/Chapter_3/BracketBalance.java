/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_3;

import java.util.Stack;
/**
 *
 * @author Kushagra
 */
public class BracketBalance 
{
    public boolean bracketBalance(String s)
    {
        Stack<Character> stack = new Stack<>();
        char [] stringChar = s.toCharArray();
        for(int i = 0 ; i < stringChar.length ; i++)
        {
            if(stringChar[i] == '{' || stringChar[i] == '[' || stringChar[i] == '(')
                stack.push(stringChar[i]);
            if(stringChar[i] == '}' || stringChar[i] == ']' || stringChar[i] == ')')
            {
                char c = stack.pop();
                //System.out.println(c +" -- "+ stringChar[i]);
                if(!this.isBalance(c, stringChar[i]))
                   return false;
            }
        }
        return stack.empty();
    }
    
    private boolean isBalance(char c1, char c2)
    {
        switch(c1)
        {
            case '{' : if(c2 == '}') return true; break;
            case '[' : if(c2 == ']') return true; break;
            case '(' : if(c2 == ')') return true; break;
        }
        return false;
    }
    
    
}

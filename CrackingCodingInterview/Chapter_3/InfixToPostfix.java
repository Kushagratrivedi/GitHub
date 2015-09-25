/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_3;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Kushagra
 */
public class InfixToPostfix 
{
    private final HashMap<Character, Integer> precedence;
    public InfixToPostfix()
    {
        precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('^', 3);
    }
    private boolean isHigher(char c1, char c2)
    {
        return precedence.get(c1) >= precedence.get(c2);
    }
    private boolean isOpearator(char c)
    {
        return precedence.containsKey(c);
    }
    private boolean isOperand(char c)
    {
        return Character.isLetterOrDigit(c);
    }
    private boolean isParentheses(char x)
    {
        return x == '(' || x == ')';
    }
    private int getPrecedence(char c)
    {
        return precedence.get(c);
    }
    public String infixToPostfix(String s)
    {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        char [] expression = s.toCharArray();
        for(int i = 0 ; i < expression.length ; i ++)
        {
            char c = expression[i];
            if(this.isOperand(c))
                output.append(c);
            else if(this.isOpearator(c))
            {
                while(!stack.empty() && this.isHigher(stack.peek(), c))
                    output.append(stack.pop());
                stack.push(c);
            }
            else if(c == '(')
            {
                StringBuilder sb = new StringBuilder();
                i++;
                while(expression[i] != ')')
                {
                    sb.append(expression[i]);
                    i++;
                }
                output.append(this.infixToPostfix(sb.toString()));
            }
            else
            {
                return "Invalid Infix String";
            }
        }
        while(!stack.empty())
        {
            char poppedChar = stack.pop();
            if(!this.isParentheses(poppedChar))
                output.append(poppedChar);
        }
        return output.toString();
    }
    
}

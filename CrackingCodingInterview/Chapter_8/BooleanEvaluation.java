/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_8;

import java.util.HashMap;

/**
 *
 * @author Kushagra
 */
public class BooleanEvaluation 
{
    private boolean StringToBoolean(String s)
    {
        return "1".equals(s);
    }
    
    public int countEval(String expression , boolean result)
    {
        if(expression == null)
            return 0;
        if(expression.length() == 1)
            return result == this.StringToBoolean(expression) ? 1 : 0;
        int ways = 0;
        
        for(int i = 1 ; i < expression.length() ; i+=2)
        {
            String left = expression.substring(0, i);
            String right = expression.substring(i+1, expression.length());
            char op = expression.charAt(i);
        
        
        int left_true = this.countEval(left, true);
        int left_false = this.countEval(left, false);
        int right_true = this.countEval(right, true);
        int right_false = this.countEval(right, false);
        int total_true = 0;
        int total = (left_true + left_false)*(right_true + right_false);
        
        if(op == '^')
        {
            total_true = left_true * right_false + right_true * left_false;
        }
        else if(op == '&')
        {
            total_true = left_true * right_true;
        }
        else
        {
            total_true = left_true * right_true + left_true * right_false + right_true * left_false;
        }
            
        int subways = result ? total_true : total - total_true;
        
        ways+=subways;
        }
        return ways;
    }
    
    
    public int countEval(String expression , boolean result, HashMap<String, Integer> cache)
    {
        if(expression == null)
            return 0;
        if(expression.length() == 1)
            return result == this.StringToBoolean(expression) ? 1 : 0;
        if(cache.containsKey(result+expression)) return cache.get(result + expression);
        int ways = 0;
        
        for(int i = 1 ; i < expression.length() ; i+=2)
        {
            String left = expression.substring(0, i);
            String right = expression.substring(i+1, expression.length());
            char op = expression.charAt(i);
        
        
            int left_true = this.countEval(left, true);
            int left_false = this.countEval(left, false);
            int right_true = this.countEval(right, true);
            int right_false = this.countEval(right, false);
            int total_true = 0;
            int total = (left_true + left_false)*(right_true + right_false);

            if(op == '^')
            {
                total_true = left_true * right_false + right_true * left_false;
            }
            else if(op == '&')
            {
                total_true = left_true * right_true;
            }
            else
            {
                total_true = left_true * right_true + left_true * right_false + right_true * left_false;
            }

            int subways = result ? total_true : total - total_true;

            ways+=subways;
            
            cache.put(result+expression, ways);
        }
        
        return ways;
    }
    
}

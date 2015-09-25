
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class ABBADiv1 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {   
        System.out.println(canObtain("A", "BABA"));
        System.out.println(canObtain("BAAAAABAA", "BAABAAAAAB"));
        System.out.println(canObtain("A", "ABBA"));
        System.out.println(canObtain("AAABBAABB", "BAABAAABAABAABBBAAAAAABBAABBBBBBBABB"));
        System.out.println(canObtain("AAABAAABB", "BAABAAABAABAABBBAAAAAABBAABBBBBBBABB"));
        
        // TODO code application logic here
    }
    
   
    public static String canObtain(String s, String t)
    {
        if(_canObtain(s, t))
            return "Possible";
        return "Impossible";
    }
    
    private static boolean _canObtain(String s, String t)
    {
        if(s.equals(t))
            return true;
        if(s.length() == t.length())
            return false;
        else
        {
            if(_canObtain(addA(s), t)) return true;
            if(_canObtain(addB(s), t)) return true;
        }
        return false;
        
    }
    
    
    private static String addA(String s)
    {
        StringBuilder sb = new StringBuilder(s);
        sb.append("A");
        return sb.toString();
    }
    
    private static String addB(String s)
    {
        StringBuilder sb = new StringBuilder(s);
        sb.append("B");
        return sb.reverse().toString();
    }
    
}

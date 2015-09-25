
import java.util.Random;


public class FortuneCookies 
{
    public String getCookies(int num)
    {
        // GENERATED RANDOM NUMBER OF COOKIES AND APPEND IT TO STRING BUILDER
        StringBuilder sb = new StringBuilder();
        String s = "";
        for(int i = 0 ; i < num ; i++)
        {
            sb.append(new Random().nextInt(50)).append(", ");/*;*/
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }
    
    
    
    
}

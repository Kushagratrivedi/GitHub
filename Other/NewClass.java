
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
interface Sum
{
    public int sum(int a, int b);
}
public class NewClass
{
    public static void display()
    {
        System.out.println("Hello");
    }
    
    public static int updateBits(int n, int m, int i, int j)
    {
        //put all 1's
        int a = ~0;
        
        //put all 0's from right to the left, till j position
        System.out.println("Line 1: "+Integer.toBinaryString(a));
        int left = a << (j + 1);
        
        //put all 1's from the left to the right, till i position
        System.out.println("Line 2: "+Integer.toBinaryString(left));
        int right = (i << 1) - 1;
        System.out.println("Line 3: "+Integer.toBinaryString(right));
        int mask = left | right;
        System.out.println("Line 4: "+Integer.toBinaryString(mask));
        int mask_n = mask & n;
        System.out.println("Line 5: "+Integer.toBinaryString(mask_n));
        int m_shifted = m << i;
        System.out.println("Line 6: "+Integer.toBinaryString(m_shifted));
        System.out.println("Line 7: "+Integer.toBinaryString(mask_n| m_shifted));
        return mask_n| m_shifted;
    }
    public static String toBinary(int a)
    {
        if(a == 0)
            return "00000000";
        StringBuilder sb;
        if(a > 0)
        {
            sb = new StringBuilder();
            while(a != 0)
            {
                if(a % 2 == 0)
                    sb.append(0);
                else
                    sb.append(1);
                a/=2;
            }
            return sb.reverse().toString();
        }
        return "Negative Number";
        
    }
    public static String printBinary(double d)
    {
        if(d<=0 || d>=1)
            return null;
        StringBuilder binary = new StringBuilder();
        binary.append(".");
        while(d > 0)
        {
            /*if(binary.length() >= 32)
                return "ERROR";*/
            double number = d * 2;
            if(number >= 1)
            {
                binary.append(1);
                d = number - 1;
            }
            else
            {
                binary.append(0);
                d = number;
            }
        }
        return binary.toString();
    }
    public static void DrawLine(byte [] screen, int width, int x1, int x2, int y)
    {
           int start_offset = x1 % 8;
           int first_full_bytes = x1 / 8;
           if(start_offset != 0)
           {
               first_full_bytes ++;
           }
           
           int end_offset = x2 % 8;
           int end_full_bytes = x2 / 8;
           if(end_offset != 7)
           {
               end_full_bytes--;
           }
           
           for(int b = first_full_bytes; b <= end_full_bytes; b++)
           {
               screen[(width / 8) * y + b] = (byte)0xFF;
           }
           
           byte start_mask = (byte)(0xFF >> start_offset);
           byte end_mask = (byte)(0xFF >> ~(end_offset+1));
           if((x1/8) == (x2/8))
           {
               byte mask = (byte) (start_mask & end_mask);
               screen[(width / 8) * y + (x1/8)]|=mask;
           }
           else
           {
               if(start_offset != 0)
               {
                   int byte_number = (width / 8) * y + first_full_bytes - 1;
                   screen[byte_number]|=start_mask;
               }
               if(end_offset != 0)
               {
                   int byte_number = (width / 8) * y + end_full_bytes - 1;
                   screen[byte_number]|=end_mask;
               }
           }
    }
    public static void main(String args[])
    {
        updateBits(Integer.parseInt("10000000000", 2), Integer.parseInt("10011", 2), 2, 6);
        System.out.println(printBinary(0.07));
        System.out.println(7&8);
        System.out.println(toBinary(10));
        System.out.println(Integer.toBinaryString(~(-1)));
        System.out.println(Integer.toBinaryString(1 << 3));
        System.out.println(Integer.toBinaryString(~((1 << 3)-1)));
        
        byte[] b = new byte[8];
        for(int i = 0 ; i < b.length ; i++)
        {
            b[i] = 0;
        }
        DrawLine(b, 4, 1, 3 , 4);
        for(byte b1:b)
        System.out.println(b1);
                /*
        System.out.println("NewClass");
        
        int i , j=2 , k=0 , h;
        k = j = i = 1;
        System.out.println(k);
       /* Base rabbit = new Base();
        Base rabbit1 = new Derived();
        Derived rabbit2 = new Derived();
        System.out.println(rabbit.value);
        System.out.println(rabbit1.value);
        System.out.println(rabbit2.value);
        rabbit.jump();
        rabbit1.jump();/
        rabbit2.jump();/
        /*int count = 0;
        System.out.println("Explained: ");
        System.out.println(count == 0?"The value is zero":"The values is not 0");
        
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        int sum1 = list.stream().map( x -> x).reduce((x, y) -> x+y).get();
        
        list.forEach(System.out::println);

        System.out.println("End");
        int b1= 1;
        int b2 = 2;
        int b3 = 3;
        A a1 = new A(b1);
        A a2 = new A(b2);
        A a3 = new A(b3);
        
        a1.a = 5;
        
        System.out.println(a1.a);
        System.out.println(a2.a);
        System.out.println(a3.a);
        
        
        a2.a = 6;
        
        System.out.println(a1.a);
        System.out.println(a2.a);
        System.out.println(a3.a);
        
        
        a3.a = 7;
        
        
        System.out.println(a1.b);
        System.out.println(a2.b);
        System.out.println(a3.b);
        System.out.println();
        System.out.println(a1.a);
        System.out.println(a2.a);
        System.out.println(a3.a);
        
        NewClass newClass = new NewClass();
        
        newClass.display();*/
        /*
        StringBuilder sb = new StringBuilder(10 + 2 + "SUN" + 4 + 5);
        System.out.println("Actual String: "+sb);
        String s = sb.delete(3, 6).toString();
        System.out.println("Deleted String: "+s);
        sb.append(s);
        System.out.println("Resulting String: "+sb);
        */
        /*
        int i = 0;
        boolean t = true, f = false, b;
        b = (t & ((i++) == 0));
        System.out.println(b +" "+i);
        b = (f & ((i+=2) > 0));
        System.out.println(b +" "+i);
                */
    }

}

class Base
{
    String value = "Kushu";
    void jump()
    {
        System.out.println(value);
    }
}
class Derived extends Base
{
    String value = "Kratika";
    void jump()
    {
        System.out.println(value);
    }
}
/*class Rabbit extends Animal
{
    void jump()
    {
        System.out.println("Rabbit");
    }
}*/
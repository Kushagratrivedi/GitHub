
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        BestLine bst = new BestLine();
        Point [] points = new Point[10];
        Random random = new Random();
        for(int i = 0 ; i < points.length ; i++)
        {
            
            points[i] = new Point(random.nextInt(10) + Math.random() , random.nextInt(10) + Math.random());
        }
        System.out.println(bst.getBestLine(points));
    }
    
}

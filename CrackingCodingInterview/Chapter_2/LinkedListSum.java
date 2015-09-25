/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_2;

import Chapter_2.LinkedList.Node;

/**
 *
 * @author Kushagra
 */
public class LinkedListSum 
{
    public LinkedList sum(LinkedList list1, LinkedList list2)
    {
        int number1 = 0, number2 = 0, temp = 0;
        Node tempNode1= list1.root, tempNode2 = list2.root;
        temp = 1;
        while(tempNode1.next != null)
        {
            
            number1 += temp * tempNode1.data;
            temp *=10;
            tempNode1 = tempNode1.next;
            //System.out.println("Check 1");
        }
        number1+= (temp) * tempNode1.data;
        temp = 1;
        while(tempNode2.next != null)
        {
            
            number2 += temp * tempNode2.data;
            temp *=10;
            tempNode2 = tempNode2.next;
            //System.out.println("Check 2");
        }
        number2+= (temp) * tempNode2.data;
        
        int number = number1 + number2;
       // System.out.println(number +" <- "+number1+" + "+number2);
        LinkedList newList = new LinkedList();
        String str = ""+number;
        
        for(int i = 0 ; i < str.length() ; i++)
        {
            newList.insert(Integer.valueOf(""+str.charAt(i)));
            //System.out.println("Check 1");
        }
                
        
        
        return newList;
    }
    
}

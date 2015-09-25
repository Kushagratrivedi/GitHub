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
public class RemoveMiddle 
{
    public void removeMiddleElement(LinkedList list)
    {
        Node tempNode1 = list.root;
        Node tempNode2 = list.root;
        Node tempNode = null;
        while(tempNode2!= null && tempNode2.next != null)
        {
            System.out.println(tempNode1.data+" ==> "+tempNode2.data);
            tempNode = tempNode1;
            tempNode1 = tempNode1.next;
            tempNode2 = tempNode2.next.next;
        }
        tempNode.next = tempNode.next.next;
        
    }
    
}

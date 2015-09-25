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
public class RemoveDuplicatesLinkedList 
{
    public LinkedList removeDuplicates(LinkedList list)
    {
        // two pointers are needed to delete duplicates, one pointer holds current node, second pointer iterate throgh the list in order to match with another element
        Node tempNode = list.root, tempNode2;
        
        while(tempNode != null && tempNode.next != null)
        {
            tempNode2 = tempNode;
            while(tempNode2.next != null)
            {
                // checks that matches the data
                if(tempNode.data == tempNode2.next.data)
                {
                   //remove duplicates
                    tempNode2.next = tempNode2.next.next;
                }
                else
                {
                    //if does not match, increment second pointer till it reaches null
                    tempNode2 = tempNode2.next;
                }
            }
            // first pointer moves to next Element
            tempNode = tempNode.next;
        }
        return list;
    }
    
}

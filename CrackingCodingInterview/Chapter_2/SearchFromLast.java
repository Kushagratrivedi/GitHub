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
public class SearchFromLast 
{
    public boolean searchFromLast(LinkedList list, int data)
    {
        Node tempNode = list.root;
        LinkedList list2 = new LinkedList();
        while(tempNode.next != null)
        {
            list2.insert(tempNode.data);
            tempNode = tempNode.next;
        }
        list2.insert(tempNode.data);
        tempNode = list2.root;
        
        if(tempNode.data == data)
            return true;
        
        while(tempNode.next != null)
        {
            if(tempNode.data == data)
            {
                return true;
            }
            else
            {
                tempNode = tempNode.next;
            }
        }
        return false;
    }
    
}

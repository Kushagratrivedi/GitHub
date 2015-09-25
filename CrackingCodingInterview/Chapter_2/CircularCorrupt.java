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
public class CircularCorrupt 
{
    public int circularCorruptPoint(LinkedList list)
    {
        Node node1 = list.root;
        Node node2 = node1;
        while(true)
        {
            Node tempNode = node1;
            node1 = node1.next;
            node2 = node2.next.next;
            if(node1 == node2)
                return tempNode.data;
        }
    }
    
}

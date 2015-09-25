import java.util.Iterator;

/*
 * Singly LinkedList Implementation
 * 
 * 
 */

/**
 *
 * @author Kushagra
 * @param <AnyType>
 */
public class LinkedList<AnyType> implements Iterable<AnyType>
{
    private static int count;
    
    // head of the linked list
    private Node<AnyType> head;
    
    //start with the no elements in list
    public LinkedList()
    {
        head = null;
    }
    
    //prepond the new node to the list
    private void addFirst(AnyType data)
    {
        head = new Node<>(data, head);
    }
    
    private void addLast(AnyType data)
    {
        Node<AnyType> nextNode = head;
        
        if(head == null)
        {
            this.addFirst(data);
        }
        
        while(nextNode.next != null)
        {
            nextNode = nextNode.next;
        }
        
        nextNode.next = new Node<>(data, null);
    }
    
    private AnyType searchFromLast(int index)
    {
        Node<AnyType> nextNode = head;
        int count1 = 0;
        while(nextNode != null)
        {
            count1+=1;
            nextNode = nextNode.next;
        }
        nextNode = head;
        int index2 = count1 - index - 1;
        for(int i = 0 ; i< index2;i++)
        {
            nextNode = nextNode.next;
        }
        return nextNode.data;
    }
    private void deleteMiddle()
    {
        Node<AnyType> nextNode = head;
        Node<AnyType> tempNode = head;
        
        while(nextNode.next != null)
        {
            nextNode = nextNode.next;
            nextNode = nextNode.next;
            System.out.println(tempNode.data.toString());
            tempNode = tempNode.next;
        }
        
        tempNode.next = tempNode.next.next;
        //this.displayList();
        
    }
    @SuppressWarnings("empty-statement")
    private LinkedList<AnyType> [] partition(AnyType data)
    {
        LinkedList<AnyType> [] part;
        part = new LinkedList[2];
        part[0] = new LinkedList<>();
        part[1] = new LinkedList<>();
        Node<AnyType> nextNode = head;
        while(nextNode.data != data)
        {
            part[0].addFirst(nextNode.data);
            nextNode = nextNode.next;
        }
        //part[1].addFirst(nextNode.data);
        while(nextNode.next != null)
        {
            part[1].addFirst(nextNode.data);
            nextNode = nextNode.next;
        }
        part[1].addFirst(nextNode.data);
        return part;
    }
    private LinkedList<AnyType> sumAsList(LinkedList<AnyType> part1, LinkedList<AnyType> part2)
    {
        String s1= "";
        String s2= "";
        
        Node<AnyType> nextNode1 = part1.head;
        Node<AnyType> nextNode2 = part2.head;
        
        while(nextNode1.next != null)
        {
            s1 = nextNode1.data + s1;
            nextNode1 = nextNode1.next;
           // System.out.println(s1);
        }
       s1 = nextNode1.data + s1;
        while(nextNode2.next != null)
        {
           // System.out.println(s2);
            s2 = nextNode2.data + s2;
            nextNode2 = nextNode2.next;
        }
        s2 = nextNode2.data + s2;
        System.out.println(s1 +" -- > "+s2);
        Integer sum = Integer.parseInt(s1) + Integer.parseInt(s2);
        System.out.println(sum);
        String temp = sum.toString();
        
        LinkedList<AnyType> newList = new LinkedList<>();
        
        for(int i = 0;i < temp.length() ; i++)
        {
            Character c = (char)temp.charAt(i);
            newList.addFirst((AnyType) c);
        }
        
        return newList;
    }
    private AnyType searchLast(int index)
    {
        int count1 = 0;
        boolean flag = false;
        boolean firstTime = true;
        Node<AnyType> tempNode = null;
        Node<AnyType> nextNode  = head;
        while(nextNode.next != null)
        {
            nextNode = nextNode.next;
            count1+=1;
            
            if(count1 == index)
            {
                flag = true;
                //tempNode = head;
            }
            if(flag)
            {
                if(firstTime)
                {
                    firstTime = false;
                    tempNode = head;
                }
                else
                {
                //System.out.println(tempNode.data.toString());
                tempNode = tempNode.next;
                }
            }
        }
        //System.out.println("Exit");
        return tempNode.data;
    }
   
    public LinkedList<AnyType> insertAfter(AnyType data, AnyType newElement)
    {
        LinkedList<AnyType> tempList = this.reverse();
        Node<AnyType> nextNode = tempList.head;
        if(nextNode.data.equals(data))
        {
            Node<AnyType> temp = nextNode.next;
            nextNode.next = new Node<>(newElement, nextNode.next);
            nextNode.next.next = temp;
            
        }
        else
        {
            while(nextNode != null && nextNode.data.equals(data))
            {
                nextNode = nextNode.next;
            }
            if(nextNode == null)
            {
                throw new java.util.NoSuchElementException("cannot insert");
            }
            else
            {
                Node<AnyType> temp = nextNode.next;
                nextNode.next = new Node<>(newElement, nextNode.next);
                nextNode.next.next = temp;
                
            }
        }
        return tempList.reverse();
        //tempList.displayList();
    }
    

    
    private LinkedList<AnyType> reverse()
    {
        Node<AnyType> tempNode = head;
        LinkedList<AnyType> reverseList = new LinkedList<>();
        while(tempNode != null)
        {
            reverseList.addFirst(tempNode.data);
            tempNode = tempNode.next;
        }
        return reverseList;
        
    }
    
    //displays the list 
    private void displayList()
    {
        //get the iterator of AnyType
        Iterator<AnyType> itr = this.iterator();
        //contains more elemetns, then will go into the loop
        while(itr.hasNext())
        {
            // get the next element data
            AnyType item = itr.next();
            // print the data
            System.out.print(item.toString()+" ---> ");
        }
        System.out.println();
    }
    
    
    
    private boolean isPalindrome()
    {
        Node<AnyType> nextNode = head;
        LinkedList<AnyType> temp = new LinkedList<>();
        int count = 0;
        while(nextNode.next != null)
        {
            count++;
            temp.addFirst(nextNode.data);
            nextNode = nextNode.next;
        }
        nextNode = head;
        Node<AnyType> tempNode = temp.head.next;
        while(count>0)
        {
            System.out.println(tempNode.data +" --------- "+nextNode.data);
            if(tempNode.data != nextNode.data)
                return false;
            tempNode = tempNode.next;
            nextNode = nextNode.next;
            count--;
        }
        return true;
    }
    // remove element from the list
    private void remove(AnyType item)
    {
        // head is null, cannot delete
        if(head == null)
        {
            throw new RuntimeException("Cannot delete");
        }
        // delete the head, if matches
        if(head.data.equals(item))
        {
            head = head.next;
        }
        // hold the current node value
        Node<AnyType> currentNode = head;
        // hold the previous nod evalue
        Node<AnyType> previousNode = null;
        
        //current node is not null and current node matches then exit the loop
        while(currentNode != null && !currentNode.data.equals(item))
        {
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        
        // if current node is null then error
        if(currentNode == null)
        {
            throw new RuntimeException("Cannot Delete");
        }
        // delete the node, assign the next node value to current node
        previousNode.next = currentNode.next;
    }
    // remove the duplicate element from the list
    private void removeDuplicate()
    {
        Node<AnyType> node1, node2;
        node1 = head;
        while(node1.next != null)
        {
            node2 = node1;
            while(node2.next != null)
            {
                if(node1.data.equals(node2.next.data))
                {
                    this.remove(node2.next.data);
                    node2.next = node2.next.next;
                }
                else
                {
                    node2 = node2.next;
                }
            }
            node1 = node1.next;
        }
        
    }

    @Override
    public Iterator<AnyType> iterator() 
    {
        return (Iterator<AnyType>) new LinkedListIterator<>();
    }
    
    /*******************************************************************
    *
    *    The Node class
    *
    ********************************************************************/
    private class Node<AnyType>
    {
        private AnyType data;
        private Node<AnyType> next;
        
        public Node(AnyType data, Node<AnyType> next)
        {
            this.data = data;
            this.next = next;
            count++;
        }
        
        
    }

    /*******************************************************************
    *
    *    The Iterator class
    *
    ********************************************************************/
    private class LinkedListIterator<AnyType> implements Iterator<AnyType>
    {
        private Node<AnyType> nextNode; 
        
        LinkedListIterator()
        {
            nextNode = (Node<AnyType>) head;
        }
        
        @Override
        public boolean hasNext() 
        {
            return nextNode != null;
        }

        @Override
        public AnyType next() 
        {
            if(!hasNext())
            {
                throw new java.util.NoSuchElementException();
            }
            AnyType item = nextNode.data;
            nextNode= nextNode.next;
            return item;
        }
        
        
    }
    public static void main(String args[])
    {
        LinkedList<Integer> linkedList = new LinkedList<>();
        
        System.out.println("Adding Elements");
        linkedList.addFirst(1);
        linkedList.addFirst(8);
        linkedList.addFirst(8);
        linkedList.addFirst(10);
        linkedList.addFirst(11);
        
        linkedList.displayList();
        
        linkedList.removeDuplicate();
        
        System.out.println("Removing Duplicates");
        
        linkedList.displayList();
        
        
        System.out.println("Insert 10");
        linkedList.addFirst(10);
        linkedList.displayList();
        
        System.out.println("Reverse");
        linkedList.reverse().displayList();
        
        
        System.out.println("Inserting elements after 1");
        linkedList = linkedList.insertAfter(1, 12);
        linkedList.displayList();
        
        System.out.println("Add at last");
        linkedList.addLast(15);
        linkedList.displayList();
        
        System.out.println("Search from last");
        
        System.out.println(linkedList.searchFromLast(2));
        System.out.println("Search from last");
        
        System.out.println(linkedList.searchLast(3));
        
        System.out.println("Delete Middle");
        linkedList.deleteMiddle();
        linkedList.displayList();
        
        System.out.println("partition");
        LinkedList temp[];
        temp = linkedList.partition(8);
        System.out.println("list 1");
        temp[0].displayList();
        System.out.println("list 2");
        temp[1].displayList();
        
        LinkedList<Integer> num1 = new LinkedList<>();
        num1.addFirst(6);
        num1.addFirst(1);
        num1.addFirst(7);
        
        num1.displayList();
        
        LinkedList<Integer> num2 = new LinkedList<>();
        num2.addFirst(2);
        num2.addFirst(9);
        num2.addFirst(5);
        
        num2.displayList();
        
        linkedList.sumAsList(num1, num2).displayList();
        
        
        
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.addFirst(1);
        list2.addFirst(5);
        list2.addFirst(2);
        list2.addFirst(5);
        list2.addFirst(1);
        
        
        System.out.println(list2.isPalindrome());
        
    }
}


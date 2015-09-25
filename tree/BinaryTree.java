/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

/**
 *
 * @author Kushagra
 */
public class BinaryTree 
{

    /**
     * @param args the command line arguments
     */
    
    Node<Integer> root;
    
    public BinaryTree()
    {
        root = null;
    }
    private void insert()
    {
        Node node3 = new Node(25, null, null);
        Node node4 = new Node(15, null, null);
        Node node5 = new Node(5, null, null);
        Node node6 = new Node(25, null, null);
        Node node1 = new Node(40, node3, node4);
        Node node2 = new Node(30, node5, node6);
        this.root = new Node(70, node1, node2);

    }
    
    
    
    private boolean hasSumProperty(Node<Integer> node)
    {
        if(node == null)
            return true;
        else
        {
            if(hasSumProperty(node.left) && hasSumProperty(node.right))
            {
                System.out.println(node.data +" -- " +node.sum());
                
                return node.data == node.sum();
            }
                
            else 
                return false;
        }
    }
    public static void main(String[] args) 
    {
        BinaryTree bt = new BinaryTree();
        bt.insert();;
        System.out.println(bt.hasSumProperty(bt.root));
        
        
        
        
    }
    
    private class Node<Integer> implements java.lang.Comparable<Node<Integer>>
    {
        int data;
        Node<Integer> left;
        Node<Integer> right;

        public Node(int data, Node<Integer> left, Node<Integer> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node<Integer> o) 
        {
            if(this.data > o.data)
                return 1;
            else if(this.data < o.data)
                return -1;
            else
                return 0;
        }
        
        public int sum()
        {
            if(this.left == null && this.right == null)
                return this.data;
            if(this.left == null)
                return this.right.data;
            if(this.right == null)
                return this.left.data;
            return this.left.data + this.right.data;
        }
        
        public boolean hasSameValue()
        {
            return this.data == this.sum();
        }
    }
    
}

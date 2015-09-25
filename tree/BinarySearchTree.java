/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Kushagra
 * 
 */
public class BinarySearchTree
{
    Node root;
    BinarySearchTree()
    {
        root = null;
    }
    
    
    private void printSum(Node node, int sum, String s)
    {
        if(node!=null)
        {
            s += " + "+ node.data;
            sum+=node.data;
        }    
        else
            return;
        if(node.left == null && node.right == null)
        {
            
            System.out.println(s+" = "+sum);
            return;
        }
        this.printSum(node.left, sum, s);
        this.printSum(node.right, sum, s);
    }
    public Node getRandomNode()
    {
        if(this.root == null)
            return null;
        Random rand = new Random();
        int random_index = rand.nextInt(this.root.size);
        return this.root.getIthNode(random_index);
    }
    public Node toOptimalTree(int [] arr)
    {
        if(arr == null)
            throw new NullPointerException("Array is null");
        if(arr.length == 1)
        {
            Node head = new Node(arr[0], null,null,  1,  null);
            return head;
        }
        Node head = null;
        head =  toOptimalTree(arr, 0, arr.length-1);
        return head;
    }
    
    
    
    
    private Node toOptimalTree(int [] arr, int start, int end)
    {
        if(start < end)
        {
            int mid = (start + end) / 2;
            Node newNode = new Node(arr[mid], null, null, 1, null);
            newNode.left =  this.toOptimalTree(arr, start, mid-1);
            newNode.right =  this.toOptimalTree(arr, mid+1, end);
            return newNode;
        }
        return null;
        
    }
        private Node createTree(int [] a, int left, int right)
    {
        if(right < left)
            return null;
        int mid = (left + right) / 2;
        Node n = new Node(a[mid], null, null, 1, null);
        n.left = this.createTree(a, left, mid - 1);
        n.right = this.createTree(a, mid+1, right);
        return n;
    }
    private void createMinimalBST(int [] a)
    {
        this.root = this.createTree(a, 0, a.length-1);
    }
    private void toDoublyLinkedList(Node node)
    {
        if(node != null)
        {
            this.toDoublyLinkedList(node.left);
            if(node.left != null)
                node.left.right = node;
            if(node.right != null)
                node.right.left = node;
            this.toDoublyLinkedList(node.right);
        }
    }

    private void BFT(Node node)
    {
        if(node == null)
            return;
        if(node.left == null && node.right == null)
        {
            System.out.println(node.data);
            return;
        }
        Queue<Node> queue = new PriorityQueue<>();
        queue.add(node);
        while(!queue.isEmpty())
        {
            //System.out.println("check 1");
            Node node1 = queue.poll();
            System.out.print(node1.data+" --> ");
            if(node1.left != null)
                queue.add(node1.left);
            if(node1.right != null)
                queue.add(node1.right);
        }
    }
    private void DFS(Node node)
    {
        if(node == null)
            return;
        System.out.print(node.data +" -- ");
        this.DFS(node.left);
        this.DFS(node.right);
    }
    private void inOrderTraverse(Node node)
    {   
        if(node != null)
        {
            this.inOrderTraverse(node.left);// System.out.println("Element: "+node.data);
            System.out.println(node.data);
            this.inOrderTraverse(node.right);// System.out.println("Element2: "+node.data);
        }
    }
    private void preOrderTraversalIterative(Node node)
    {
        if(node != null)
        {
            Stack<Node> st1 = new Stack<>();
            Stack<Node> st2 = new Stack<>();
            st1.push(node);
            while(!st1.empty())
            {
                Node node1 = st1.pop();
                st2.push(node1);
                if(node1.left != null)
                {
                    st1.push(node1.left);
                }
                if(node1.right != null)
                {
                    st1.push(node1.right);
                }
            }
            while(!st2.empty())
                System.out.print(st2.pop().data+" --> ");
        }
    }
    private int min() // complexity of O(height)  
    {
        Node temp = root;
        while(temp.left != null)
        {
            temp = temp.left;
        }
        return temp.data;

    }
    
    private int sumPath(Node root, int sum)
    {
        if(root == null)
            return 0;
        HashMap<Integer, Integer> pathCount = new HashMap<>();
        int currentSum = 0;
        int targetSum = sum;
        incrementHashTable(pathCount, 0, 1);
        return sumPath(root, pathCount, targetSum, currentSum);
    }
    
    private int sumPath(Node root, HashMap<Integer, Integer> pathCount, int targetSum, int currentSum)
    {
        if(root == null)
            return 0;
        int totalPath = 0;
        int runningSum = currentSum + root.data;
        this.incrementHashTable(pathCount, runningSum, 1);
        int sum = runningSum - targetSum;
        totalPath = pathCount.containsKey(sum) ? pathCount.get(sum) : 0;
        
        totalPath+= this.sumPath(root.left, pathCount, targetSum, runningSum);
        totalPath+= this.sumPath(root.right, pathCount, targetSum, runningSum);
        
        this.incrementHashTable(pathCount, runningSum, -1); // IF YOU GOING TO RIGHT PATH, WE SHOULD SUBTRACT THE RUNNING SUM OF LEFT
        
        return totalPath;
        
    }
    private int max()// complexity of O(height) 
    {
        Node temp = root;
        while(temp.right != null)
            temp = temp.right;
        return temp.data;
    }
    private int getSize(int data)// complexity of O(height) 
    {
        Node temp = root;
        while(true)
        {
            if(temp.data == data)
            {
                return temp.size;
            }
            else
            {
                if(temp.data > data)
                {
                    temp = temp.left;
                }
                else if(temp.data < data)
                {
                    temp = temp.right;
                }
            }
        }
    }
    private void insert(int data) // complexity of O(height) for finding and checking and constant time for insertion
    {
        Node temp = root;
        if(root == null)
        {
            root = new Node(data, null, null, 1, null);
            System.out.println("Root: "+this.root.data);
        }
        else
        {
            System.out.print("Root --> ");
            while(true)
            {
                //System.out.println(temp.data);
                temp.size+=1;
                if(temp.data > data)
                {
                    System.out.print("Left --> ");
                    if(temp.left == null)
                    {
                        temp.left = new Node(data, null, null,1,temp);
                        System.out.print(temp.left.data);
                        break;
                    }
                    else
                        temp = temp.left;
                }
                else if(temp.data < data)
                {
                    System.out.print("Right --> ");
                    if(temp.right == null)
                    {
                        temp.right = new Node(data, null, null, 1, temp);
                        System.out.print(temp.right.data);
                        break;
                    }
                    else
                        temp = temp.right;
                }
            }
            System.out.println();
        }
    }
    private int checkHeight(Node root)
    {
        if(root == null)
            return 0;
        int left_height = this.checkHeight(root.left);
        if(left_height == -1)
            return -1;
        int right_height = this.checkHeight(root.right);
        if(right_height == -1)
            return -1;
        int difference = left_height - right_height;
        if(Math.abs(difference) > 1)
            return -1;
        else
            return Math.max(left_height, right_height)+1;
        
    }
    private boolean isBalanced_(Node root)
    {
        return this.checkHeight(root) != -1;
    }
    private int getHeight(Node root)
    {
        if(root == null)
            return 0;
        else
            return Math.max(this.getHeight(root.left), this.getHeight(root.right))+1;
    }
    private boolean isBalanced(Node root)
    {
        if(root == null)
            return false;
        else if(Math.abs(this.getHeight(root.left)- this.getHeight(root.right))> 1)
            return false;
        else
            return this.isBalanced(root.left) && this.isBalanced(root.right);
    }
    

    public static void main(String[] args)
    {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(5);
        bst.insert(15);
        bst.insert(96);
        bst.insert(1);
        bst.insert(76);
        bst.insert(2);
        bst.insert(3);
        System.out.println("Size of 5: "+bst.getSize(5));
        System.out.println("Size of 15: "+bst.getSize(15));
        System.out.println("Size of 96: "+bst.getSize(96));
        System.out.println("Size of 1: "+bst.getSize(1));
        System.out.println("Size of 76: "+bst.getSize(76));
        System.out.println("Size of 2: "+bst.getSize(2));
        System.out.println("Size of 3: "+bst.getSize(3));
        bst.inOrderTraverse(bst.root);
        
        bst.preOrderTraversalIterative(bst.root);
        
        System.out.println(bst.isBalanced(bst.root));
        
        int [] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        BinarySearchTree bst1 = new BinarySearchTree();
        bst1.createMinimalBST(a);
        bst1.preOrderTraversalIterative(bst1.root);
        
        System.out.println();
        
        bst.BFT(bst.root);
        
        System.out.println();
        
        bst.DFS(bst.root);
        System.out.println();
        //bst.toDoublyLinkedList(bst.root);
           
        
        System.out.println("Sum Path: "+bst1.sumPath(bst1.root, 9));
        
        
        //bst.inOrderTraverse(bst.root);
         bst.printSum(bst.root, 0 , "0");
        
         
         
         BinarySearchTree bs2 = new BinarySearchTree();
         Node head = bs2.toOptimalTree(a);
         bs2.preOrderTraversalIterative(head);
         
         
         System.out.println("Random Node: "+bst.getRandomNode().data);
         
        // System.out.println(bst.hasSumProperty(bst.root));
    }

    private void incrementHashTable(HashMap<Integer, Integer> pathCount, int key, int delta) 
    {
        if(!pathCount.containsKey(key))
            pathCount.put(key, 0);
        pathCount.put(key, pathCount.get(key) + delta);
    }
    
    private class Node implements java.lang.Comparable
    {
        int data;
        Node left;
        Node right;
        Node parent;
        int size;
        
        public Node(int data, Node left, Node right, int size, Node parent) 
        {
            this.data = data;
            this.left = left;
            this.right = right;
            this.size = size;
            this.parent = parent;
        } 
        
        public Node getIthNode(int i)
        {
            int leftSize = this.left == null ? 0 : this.left.size;
            if(i < leftSize)
                return this.left.getIthNode(i);
            else if( i == leftSize)
                return this;
            else
                return this.right.getIthNode(i - (leftSize + 1));
        }

        @Override
        public int compareTo(Object o) 
        {
            return 0;
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Kushagra
 */
public class BinarySearchTree_2 
{
    TreeNode root;
    private final TreeNode sentinel = new TreeNode(Integer.MAX_VALUE);
    
    BinarySearchTree_2()
    {
        root = sentinel;
    }
    public TreeNode toMinimalTree(int [] arr, int start,int end)
    {
        if(start > end)
            return null;
        int mid = (start + end) / 2;
        TreeNode newNode = new TreeNode(arr[mid]);
        newNode.left = this.toMinimalTree(arr, start, mid - 1);
        newNode.right = this.toMinimalTree(arr, mid + 1, end);
        return newNode;
    }
   public NodePair toDoubly(TreeNode root)
    {
        if(root.isEmpty())
            return null;
        NodePair part1 = this.toDoubly(root.left);
        NodePair part2 = this.toDoubly(root.right);
        
        if(part1 != null)
        {
            this.concate(part1.tail, root);
        }
        if(part2 != null)
        {
            this.concate(root, part2.head);
        }
        
        return new NodePair(part1 == null ? root : part1.head,
                            part2 == null ? root : part2.tail);
        
        
        
        
        
    }
   
    public void concate(TreeNode left, TreeNode right)
    {
        left.right = right;
        right.left = left;
    }
    private class NodePair
    {
        TreeNode head;
        TreeNode tail;
        public NodePair(TreeNode head, TreeNode tail)
        {
            this.head = head;
            this.tail = tail;
        }
    }
    
    public TreeNode commonAncestor(int key1, int key2)
    {
        TreeNode node1 = new  TreeNode(sentinel, sentinel, key1);
        TreeNode node2 = new  TreeNode(sentinel, sentinel, key2);
        TreeNode _root = this.root;
        if(key1 <= _root.data && key2 > _root.data)
            return _root;
        return commonAncestor(node1, node2, _root);
        
    }

    private TreeNode commonAncestor(TreeNode node1, TreeNode node2, TreeNode root) 
    {
        if(node1.isEmpty())
            return sentinel;
        if(node2.isEmpty())
            return sentinel;
        if(node1.data < root.data && node2.data > root.data)
            return root;
        if(node1.data <= root.data)
        {
            return this.commonAncestor(node1, node2, root.left);
        }
        else
        {
            return this.commonAncestor(node1, node2, root.right);
        }
    }
    
    
    public int height(TreeNode root)
    {
        if(root.isEmpty())
            return 0;
        return Math.max(this.height(root.left), this.height(root.right)) + 1;
    }
    
    public int size(TreeNode root)
    {
        
        if(root.isEmpty())
            return 0;
        return 1 + this.size(root.right) + this.size(root.left);
        
    }
    
    public boolean isBalanced(TreeNode root)
    {
        if(root.isEmpty())
            return true;
        int leftsize = this.height(root.left);
        int rightsize = this.height(root.right);
        if(Math.abs(leftsize - rightsize) > 1)
            return false;
        return this.isBalanced(root.left) && this.isBalanced(root.right);
    }
    public boolean _hasPathSum(int sum, TreeNode root)
    {
        if(root == null && sum != 0) return false;
        if(sum == 0) return true;
        int subsum = sum - root.data;
        return this._hasPathSum(subsum, root.left) || this._hasPathSum(subsum, root.right);
            
    }
    /*private boolean isBst(TreeNode root)
    {
        if(root.isEmpty())
            return true;
        if(root.left.data <= root.data)
            return this.isBst(root.left);
        if(root.right.data > root.data)
            return this.isBst(root.right);
        return false;
        
    }*/

    //BST - SEQUENCE IS USED TO FIND ALL POSSIBLE WAYS HOW THE BINARY TREE CAN BE CREATED
    public ArrayList<LinkedList<TreeNode>> bstSequence(TreeNode root)
    {
        
        ArrayList<LinkedList<TreeNode>> result = new ArrayList<>();
        if(root.isEmpty())
        {
            //IF NULL NODE THAN ADD NULL LIST AND RETURN
            result.add(new LinkedList<>());
            return result;
        }
        
       //ADD THE ROOT OF TREE/SUBTREE TO LINKEDLIST
       LinkedList<TreeNode> prefix = new LinkedList<>();
       prefix.add(root);
       
       //GET ALL WEAVED LIST FOR THE LEFT SUBTREE
       ArrayList<LinkedList<TreeNode>> leftSeq = this.bstSequence(root.left);
       //GET ALL WEAVED LIST FOR THE RIGHT SUBTREE
       ArrayList<LinkedList<TreeNode>> rightSeq = this.bstSequence(root.right);
       
       //WEAVE EACH LEFT AND RIGHT SEQUENCE IN ALL THE POSSIBLE WAY
       for(LinkedList<TreeNode> left:leftSeq)
       {
           for(LinkedList<TreeNode> right: rightSeq)
           {
               ArrayList<LinkedList<TreeNode>> weaved = new ArrayList<>();
               getArrangements(left, right, weaved, prefix);
               //ADD EACH ARRANGEMENT TO RESULTSET
               result.addAll(weaved);
           }
       }
       return result;
        
    }

    private void getArrangements(LinkedList<TreeNode> left, 
            LinkedList<TreeNode> right, 
            ArrayList<LinkedList<TreeNode>> weaved, 
            LinkedList<TreeNode> prefix) 
    {
        if(left.size() == 0 || right.size() == 0)
        {
            //GET THE CLONE OF PREFIX SO ACTUAL PREFIX DOES NOT CHANGE
            LinkedList<TreeNode> result = (LinkedList<TreeNode>) prefix.clone();
            
            result.addAll(left);
            result.addAll(right);
            weaved.add(result);
            return;
        }
        
        //REMOVE THE FIRST ELEMENT
        TreeNode headFirst = left.removeFirst();
        //ADD IT TO LAST OF PREFIX
        prefix.addLast(headFirst);
        //WEAVE AGAIN - RECURSION TILL FIRST OR SECOND LIST DOES NOT BECOME OF SIZE 0
        this.getArrangements(left, right, weaved, prefix);
        //REMOVE THE LAST ADDED ELEMENT
        prefix.removeLast();
        //ADD IT BACK TO FIRST LIST
        left.addFirst(headFirst);
        
        //FOLLOW SAME STEPS FOR THE SECOND LIST
        TreeNode headSecond = right.removeFirst();
        prefix.addLast(headSecond);
        this.getArrangements(left, right, weaved, prefix);
        prefix.removeLast();
        right.addFirst(headSecond);
    }
    
    
    public void insert(int data)
    {
        if(!this.root.isEmpty())
        {
            TreeNode tempNode = this.root;
            TreeNode newNode = new TreeNode(data);
            newNode.left = sentinel;
            newNode.right = sentinel;
            TreeNode parent = sentinel;
            while(!tempNode.isEmpty())
            {
                parent = tempNode;
                if(data <= tempNode.data)
                    tempNode = tempNode.left;
                else
                    tempNode = tempNode.right;
            }
            if(data <= parent.data)
                parent.left = newNode;
            else
                parent.right = newNode;
        } 
        else 
        {
            this.root = new TreeNode(data);
            this.root.left = sentinel;
            this.root.right = sentinel;
        }  
    }
    
    public boolean isSubTree(BinarySearchTree_2 tree)
    {
        if(this.root.isEmpty())
            return false;
        if(tree.root.isEmpty())
            return true;
        return isSubTree(this.root, tree.root);
    }
    
    private boolean isSubTree(TreeNode node1, TreeNode node2)
    {
        if(node1.isEmpty())
            return false;
        else if(node1.data == node2.data && isMatch(node1, node2))
            return true;
        return isSubTree(node1.left, node2) || isSubTree(node1.right, node2);
    }

    
    private boolean isMatch(TreeNode node1, TreeNode node2) 
    {
        if(node2.isEmpty() &&  node1.isEmpty())
            return true;
        else if(node1.isEmpty())
            return false;
        else if(node1.data != node2.data)
            return false;
        else
            return isMatch(node1.left, node2.left) 
                    && isMatch(node1.right, node2.right);
    }
   
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        this._inorder(this.root, sb);
        String s = sb.substring(0, sb.length()-2);
        s+="]";
        return s;
    }
    
    private void _inorder(TreeNode root, StringBuilder sb)
    {
        if(root != sentinel)
        {
            this._inorder(root.left, sb);
            sb.append(root.data).append(", ");
            this._inorder(root.right, sb);
            
        }
    }
    
    
    public TreeNode min(TreeNode node)
    {
        if(node.left.isEmpty())
            return node;
        return this.min(node.left);
    }
    
    public TreeNode max(TreeNode node)
    {
        if(node.right.isEmpty())
            return node;
        return this.max(node.right);
    }
     
    public TreeNode successor(int key)
    {
        TreeNode node = new TreeNode(sentinel, sentinel, key);
        return this.successor(node);
    }
    public TreeNode successor(TreeNode node)
    {
        if(!node.right.isEmpty())
            return this.min(node);
        TreeNode s = sentinel , a = this.root;
        while(!a.isEmpty() && a != node)
        {
            if(node.data < a.data)
            {
                s = a;
                a = a.left;
            }
            else
            {
                a = a.right;
            }
        }
        return s;
    }
    
    public TreeNode predecessor(int key)
    {
        return this.predecessor(new TreeNode(sentinel, sentinel, key));
    }
    
    public TreeNode predecessor(TreeNode node)
    {
        if(!node.left.isEmpty())
            return this.max(node.left);
        TreeNode p = sentinel, a = this.root;
        while(!a.isEmpty() && a != node)
        {
            if(node.data > a.data)
            {
                p = a;
                a = a.right;
            }
            else
            {
                a = a.left;
            }
            
        
        }
        return p;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        BinarySearchTree_2 bst = new BinarySearchTree_2();
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(3);
        bst.insert(7);
        bst.insert(13);
        bst.insert(17);
        System.out.println(bst);
        
        
        BinarySearchTree_2 bst1 = new BinarySearchTree_2();
        bst1.insert(15);
        bst1.insert(13);
        bst1.insert(17);
        System.out.println(bst1);
        System.out.println(bst.isSubTree(bst1));
        
        
        ArrayList<LinkedList<TreeNode>> lists = bst.bstSequence(bst.root);
        /*for(LinkedList<TreeNode> list:lists)
            System.out.println(list);
        */
        System.out.println(lists);
        
        System.out.println(bst.commonAncestor(13, 17));
        
        //System.out.println(bst.isBst(bst.root));
        
        System.out.println("Size of 10: "+bst.size(bst.root));
        
        System.out.println("Height of 10: "+bst.height(bst.root));
        
        System.out.println("This tree is Balanced Binary Tree: "+bst.isBalanced(bst.root));
        
        System.out.println("Minimum Node: "+bst.min(bst.root));
        
        System.out.println("Maximum Node: "+bst.max(bst.root));
        
        System.out.println("Successor of 10: "+ bst.successor(10));
        
        System.out.println("Predessor of 10: "+bst.predecessor(10));
        
        NodePair np = bst.toDoubly(bst.root);
        System.out.println(np.head +" ~~~~> "+ np.tail);
        TreeNode n = np.head;
        System.out.println(n.right);
        while(!n.right.isEmpty())
        {
            System.out.print(n +" ~~> ");
            n = n.right;
        }
        
        System.out.println(bst._hasPathSum(15, bst.root));
        
    }

    

    private class TreeNode
    {
        int data;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int data)
        {
            this.data = data;
        }
        
        public boolean isEmpty()
        {
            return this == sentinel;
        }
    
        
        TreeNode(TreeNode left, TreeNode right, int data)
        {
            this(data);
            this.left = left;
            this.right = right;
        }
        
        @Override
        public String toString()
        {
            return Integer.toString(data);
        }
    }
    
}

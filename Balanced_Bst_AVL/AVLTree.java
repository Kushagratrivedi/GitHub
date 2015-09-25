import java.util.Stack;


public class AVLTree 
{
	
	Node root;
	public AVLTree()
	{
		root = null;
	}
		public void spiralOrder(Node root)
	{
		if(root == null)
			return;
		Stack<Node> st1 = new Stack<>();
		st1.push(root);
		Stack<Node> st2 = new Stack<>();
		while(!st1.empty() | !st2.empty())
		{
			Node current = null;
			while(!st1.empty())
			{
				current  = st1.pop();
				if(current.left != null)
					st2.push(current.left);
				if(current.right != null)
					st2.push(current.right);
				
				System.out.print(current.data+" -> ");
				
			}
			System.out.println();
			while(!st2.empty())
			{
				current = st2.pop();
				if(current.right != null)
					st1.push(current.right);
				if(current.left != null)
					st1.push(current.left);
				System.out.print(current.data +" <- ");
			}
			System.out.println();
		}
		
	}
	public boolean isBst1(Node root)
	{
		if(root == null)
			return true;
		if(root.left != null)
		{
			if(root.left.data > root.data)
			return false;
		}
		if(root.right != null)
		{
			if(root.right.data <= root.data)
				return false;
		}
		return isBst1(root.left) && isBst1(root.right);
	}
	
	public boolean isBst2(Node root)
	{
		if(root == null)
			return true;
		Stack<Node> stack  =new Stack<>();
		stack.push(root);
		while(!stack.empty())
		{
			Node node = stack.pop();
			if(node.left != null)
			{
				stack.push(node.left);
				if(node.left.data > node.data)
					return false;
				
			}
			if(node.right != null)
			{
				stack.push(node.right);
				if(node.right.data <= node.data)
					return false;
				
			}
		}
		return true;
	}
	public void inorder(Node root)
	{
		if(root != null)
		{
			this.inorder(root.left);
			System.out.print(root.data +" -- ");
			this.inorder(root.right);
		}
	}
	
	public Node get(int data, Node root)
	{
		if(root == null)
			return null;
		if(data < root.data)
			return this.get(data, root.left);
		else if(data > root.data)
			return this.get(data, root.right);
		else
			return root;
	}
	
	public Node commonAncestor(Node root, Node p, Node q)
	{
		if(!this.search(root, p) || !this.search(root, q))
			return null;
		return this.ancestor_helper(root, p, q);
	}
	private Node ancestor_helper(Node root, Node p, Node q)
	{
		if(p.data <= root.data && q.data > root.data)
			return root;
		else if(p.data < root.data)
			return this.ancestor_helper(root.left, p, q);
		else 
			return this.ancestor_helper(root.right, p, q);
	}
	
	public boolean search(Node root, Node p)
	{
		if(root == null || p == null)
			return false;
		if(p.data < root.data)
			return this.search(root.left, p);
		else if(p.data > root.data)
			return this.search(root.right, p);
		else return true;
	}
	public Node inOrderSuccessor(Node root)
	{
		if(root == null)
			return null;
		if(root.right != null)
			return this.minNode(root.right);
		Node ancestor = this.root, successor = null;
		while(ancestor != root)
		{
			if(ancestor.data > root.data)
			{
				successor = ancestor;
				ancestor = ancestor.left;
			}
			else
			{
				ancestor = ancestor.right;
			}
		}
		return successor;
	}
	public Node getMinimalTree(int [] arr)
	{
		int start = 0;
		int end = arr.length -1;
		Node root = null;
		root =  this._minimalHelper(arr, start, end);
		return root;
	}
	private Node _minimalHelper(int [] arr, int start, int end)
	{
		if(start > end)
			return null;
		int mid = (start + end) / 2;
		Node newNode = new Node(arr[mid]);
		newNode.left = this._minimalHelper(arr, 0, mid - 1);
		newNode.right = this._minimalHelper(arr, mid+ 1 , end);
		//System.out.println();
		//this.inorder(newNode);
		return newNode;
		
	}
	public void preOrder(Node root, String path)
	{
		if(root == null)
			return;
		else
		{
			System.out.println(path+" : "+root.data);
			this.preOrder(root.left, path+ " --> left");
			this.preOrder(root.right,  path+ " --> rigth");
		}
	}
	
	public void track(int number)
	{
		this.insert(number, this.root);
	}
	
	
	public int getRank(int number, Node root)
	{
		if(number > root.data)
		{
			return this.getRank(number, root.right) + root.left.size() + 1;
		}
		else if(number < root.data)
		{
			return this.getRank(number, root.left);
		}
		else
		{
			return root.left.size() + 1;
		}
	}
	
	public Node delete(int data, Node root)
	{
		if(root == null)
			return null;
		else 
		{
			if(data < root.data)
				root.left = this.delete(data, root.left);
			else if(data > root.data)
				root.right = this.delete(data, root.right);
			else
			{
				if(root.left == null && root.right == null)
				{
					root.parent = null;
					root = null;
					return root;
				}
				else if(root.left == null)
				{
					Node tempNode = root.right;
					tempNode.parent = root.parent;
					
					root.parent = null;
					root.right = null;
					root = null;
					return tempNode;
				}
				else if(root.right == null)
				{
					Node tempNode = root.left;
					tempNode.parent = root.parent;
					root.parent = null;
					root.left = null;
					root = null;
					return tempNode;
				}
				else
				{
					Node minNode = this.minNode(root.right);
					root.data = minNode.data;
					System.out.println(minNode.data);
					root.right = this.delete(minNode.data, root.right);
					return root;
				}
			}
			//this.preOrder(this.root, "Root");
			if(Math.abs(root.getBalance()) > 1)
			{
				if(root.getBalance() < -1)
				{
					if(root.right.getBalance() == -1)
					{
						System.out.println("check");
						root = this.leftRotation(root);
					}	
					else if(root.right.getBalance() == 1)
					{
						root.right = this.rightRotation(root.right);
						root = this.leftRotation(root);
					}
					else
					{
						
					}
				}
				if(root.getBalance() > 1)
				{
					if(root.left.getBalance() == 1)
						root  = this.rightRotation(root);
					else if(root.left.getBalance() == -1)
					{
						root.left = this.leftRotation(root.left);
						root = this.rightRotation(root);
						
					}
				}
				else
				{
					
				}
			}
		}
		return root;
			
		
	}
	private Node minNode(Node root)
	{
		if(root.left == null)
			return root;
		return this.minNode(root.left);
		
	}
	public Node insert(int data, Node root)
	{
		if(root == null)
		{
			root = new Node(data);
			//System.out.println(root.data);
			return root;
		}	
		else 
		{
			if(data <= root.data)
			{
				//root.size +=1;
				root.left = this.insert(data, root.left);
				root.left.parent = root;
				
			}
				
			else 
			{
				root.right = this.insert(data, root.right);
				root.right.parent = root;
				//this.preOrder(this.root);
				
				//System.out.println(root.right.data);
			}
				
		}
		if(Math.abs(root.getBalance()) > 1)
		{
			if(root.getBalance() < -1)
			{
				if(root.right.getBalance() == -1)
				{
					//System.out.println("check");
					root = this.leftRotation(root);
				}
					
				else if(root.right.getBalance() == 1)
				{
					root.right = this.rightRotation(root.right);
					root = this.leftRotation(root);
				}
			}
			if(root.getBalance() > 1)
			{
				if(root.left.getBalance() == 1)
					root  = this.rightRotation(root);
				else if(root.left.getBalance() == -1)
				{
					root.left = this.leftRotation(root.left);
					root = this.rightRotation(root);
					
				}
			}
		}
		return root;	
	}

	public Node leftRotation(Node node)
	{
		Node tempNode = node.right;
		node.right = tempNode.left;
		if(tempNode.left != null)
			 tempNode.left.parent = node;
		tempNode.parent = node.parent;
		if(node.parent == null)
			this.root = tempNode;
		else if(node == node.parent.left)
			node.parent.left = tempNode;
		else
			node.parent.right = tempNode;
		tempNode.left = node;
		node.parent = tempNode;
		//this.preOrder(this.root, "root");
		return tempNode;
	}
	
	public Node rightRotation(Node node)
	{
		Node tempNode = node.left;
		node.left = tempNode.right;
		if(tempNode.right != null)
			tempNode.right.parent = node;
		tempNode.parent = node.parent;
		if(node.parent == null)
			this.root = tempNode;
		else if(node == node.parent.right)
			node.parent.right = tempNode;
		else
			node.parent.left = tempNode;
		tempNode.right = node;
		node.parent = tempNode;
		return tempNode;
		 
	}
}

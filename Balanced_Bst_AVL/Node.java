
public class Node 
{
	//int size = 0;
	int data;
	Node left;
	Node right;
	Node parent;
	private int height;
	private int balance;
	public Node(int data)
	{
		this.data = data;
		
	}
	public int getHeight()
	{
		if(this.left == null && this.right == null)
			this.height =  1;
		else if(this.left == null)
			this.height=  this.right.getHeight() + 1;
		else if(this.right == null)
			this.height = this.left.getHeight() + 1;
		else if(this.left != null && this.right !=  null)
			this.height = this.max(this.left.getHeight(), this.right.getHeight()) + 1;
		return this.height;
	}
	public int getBalance()
	{
		if(this.left == null && this.right == null)
			this.balance = 0;
		else if(this.left == null)
			this.balance = 0 - this.right.getHeight();
		else if(this.right == null)
			this.balance = this.left.getBalance() - 0;
		else if(this.left != null && this.right != null)
			this.balance = this.left.getHeight() - this.right.getHeight();
		return this.balance;
	}
	
	public int size()
	{
		if(this.left != null && this.right != null)
		{
			return 1 + this.left.size() + this.right.size();
		}
		else if(this.left != null)
		{
			return 1 + this.right.size();
		}
		else if(this.right != null)
		{
			return 1 + this.left.size();
		}
		else
		{
			return 1;
		}
			
	}
	private int max(int a , int b)
	{
		return b ^ ((a ^ b) & (a > b ? 1 : 0));
	}
	
	public String toString()
	{
		return this.data+"";
	}
	
}

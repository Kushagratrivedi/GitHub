
public class StackMin {

	public static void main(String[] args) 
	{
		Stack stack = new Stack();
		stack.push(6);
		stack.push(4);
		stack.push(7);
		stack.push(12);
		stack.push(2);
		stack.push(-1);
		stack.push(13);
		
		System.out.println("Minimum: "+stack.min());
		System.out.println("Pop 1: "+stack.pop());
		System.out.println("Pop 2: "+stack.pop());
		System.out.println("Minimum: "+stack.min());
		
		stack.sort();
		
		System.out.println(stack);

	}
//7405876594
}
class Stack
{
	private int [] values;
	private int [] mins;
	private int minTop = -1;
	int top = -1;
	private int size = 10;
	//int min = Integer.MAX_VALUE;
	Stack(int size)
	{
		this.size = size;
		mins = new int[size];
		values = new int[size];
	}
	
	Stack()
	{
		values = new int[size];
		mins = new int[size];
	}
	
	public void sort()
	{
		Stack stack = new Stack(this.size);
		if(stack.isEmpty())
			stack.push(this.pop());
		
		while(!this.isEmpty())
		{
			int data = this.pop();
			while(data<=stack.peek())
				this.push(stack.pop());
			stack.push(data);
		}
		
		while(!stack.isEmpty())
			this.push(stack.pop());
		
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0 ; i < this.top  ; i++)
		{
			sb.append(values[i] + ", ");
		}
		sb.append(values[top] +"]");
		return sb.toString();
	}
	
	public void push(int data)
	{
		if(top + 1 == size)
			throw new StackOverflowError();
		top++;
		values[top] = data;
		push_update_min(data);
	}
	
	public int pop()
	{
		if(top == -1)
			return -1;
		int data = values[top];
		values[top] = 0;
		top --;
		pop_update_min(data);
		return data;
	}
	
	public int peek()
	{
		if(top == -1)
			return -1;
		return values[top];
	}
	
	public boolean isEmpty()
	{
		return top == -1;
	}
	
	public int min()
	{
		return mins[minTop];
	}
	
	private void push_update_min(int data)
	{
		if(minTop == -1)
		{
			minTop++;
			mins[minTop] = data;
		}
		if(data < mins[minTop])
		{
			minTop++;
			mins[minTop] = data;
		}
	}
	
	private void pop_update_min(int data)
	{
		if(data == mins[minTop])
		{
			mins[minTop] = 0;
			minTop--;
		}
	}
	
}

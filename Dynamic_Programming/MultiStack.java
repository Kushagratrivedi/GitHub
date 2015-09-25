
public class MultiStack 
{
	private int size = 10;
	private int k = 3;
	private int [] stacks;
	private int [] pointer;
	
	public MultiStack()
	{
		super();
		stacks = new int[size * k];
		pointer = new int[k];
		for(int i = 0 ; i < k ; i ++)
		{
			pointer[i] = -1;
		}
	}
	public MultiStack(int stackSize, int totalStacks)
	{
		size = stackSize;
		k = totalStacks;
		stacks = new int[size * k];
		pointer = new int[k];
		for(int i = 0 ; i < k ; i ++)
		{
			pointer[i] = -1;
		}
	}
	public MultiStack(int totalStack)
	{
		k = totalStack;
		stacks = new int[size * k];
		pointer = new int[k];
		for(int i = 0 ; i < k ; i ++)
		{
			pointer[i] = -1;
		}
	}
	
	public void push(int data, int index)
	{
		if(index > k)
			return;
		pointer[index]++;
		stacks[size * index + pointer[index]] = data;
	}
	
	public int pop(int index)
	{
		if(index > k)
			return 0;
		int data = stacks[size * index + pointer[index]];
		stacks[size * index + pointer[index]] = 0;
		pointer[index]--;
		return data;
	}
	
	public int peek(int index)
	{
		return stacks[size * index + pointer[index]];
	}
	
	public boolean isEmpty(int index)
	{
		System.out.println(stacks.length);
		return pointer[index] == -1;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < stacks.length ; i++)
		{
			if(i % size == 0)
				sb.append("\n");
			sb.append(stacks[i] +" | ");
			
		}
		return sb.toString();
	}
}

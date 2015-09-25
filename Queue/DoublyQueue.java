import java.util.Arrays;


public class DoublyQueue
{
	private int [] items;
	int size;
	private int capacity;
	private int front;
	private int last;
	public DoublyQueue(int size)
	{
		this.size = size;
		items = new int[size];
		front = -1;
		last = -1;
		capacity = this.size;
	}
	
	public int insertAtEnd(int key)
	{
		if(capacity == 0)
			return -1;
		if(front == -1 && last == -1)
		{
			items[0] = key;
			front++;
			last++;
			capacity--;
			return 1;
		}
		else
		{
			last +=1;
			last%=size;
			//System.out.println(last);
			capacity--;
			items[last] = key;
			return 1;
		}
	}
	
	public int insertAtFront(int key)
	{
		if(capacity == 0)
			return -1;
		if(front == -1 && last == -1)
		{
			items[0] = key;
			front++;
			last++;
			capacity--;
			return 1;
		}
		else
		{
			front--;
			if(front < 0)
				front = this.size - 1;
			//System.out.println(front);
			capacity--;
			items[front] = key;
			return 1;
		}
		
	}
	
	public int removeFromLast()
	{
		if(capacity == size)
			return Integer.MIN_VALUE;
		else 
		{
			int element = items[last];
			items[last] = 0;
			last--;
			if(last < 0)
				last = this.size - 1;
			capacity++;
			return element;
		}
	}
	
	public int removeFront()
	{
		if(capacity == size)
			return Integer.MIN_VALUE;
		else
		{
			int element = items[front];
			items[front] = 0;
			front ++;
			front%=this.size;
			capacity++;
			return element;
		}
	}
	
	public String toString()
	{
		return Arrays.toString(this.items);
	}

	public static void main(String[] args) 
	{
		DoublyQueue q = new DoublyQueue(8);
		q.insertAtEnd(0);
		q.insertAtEnd(1);
		q.insertAtFront(2);
		q.insertAtFront(3);
		q.insertAtFront(4);
		
		System.out.println("Remove at Front: "+q.removeFront());
		System.out.println("Remove at Front: "+q.removeFront());
		System.out.println("Remove at Front: "+q.removeFront());
		System.out.println("Remove at Front: "+q.removeFront());
		System.out.println("Remove at Last: "+q.removeFromLast());
		System.out.println("Remove at Last: "+q.removeFromLast());
		
		
		System.out.println(q);
		

	}

}

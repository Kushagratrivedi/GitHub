import java.util.ArrayList;


public class Heap<T extends Comparable<T>> 
{
	ArrayList<T> items;
	
	public Heap()
	{
		items = new ArrayList<>();
	}
	public Heap(int size)
	{
		super();
		items = new ArrayList<>(size);
	}
	
	private int parent(int k)
	{
		return (k-1)/2;
		
	}
	private int left(int p)
	{
		return 2 * p + 1;
	}
	private int right(int p)
	{
		return 2 * p + 2;
	}
	
	private void siftUp()
	{
		// get the last inserted element
		int k = items.size() - 1;
		
		// go till 0th index
		while(k > 0)
		{
			//get the parent index
			int p = this.parent(k);
			
			T child = items.get(k);
			T parent = items.get(p);
			//compares parent and child index element, if parent is small then swap with child
			if(parent.compareTo(child) < 0)
			{
				//Swapping
				items.set(p, child);
				items.set(k, parent);
				// after swapping, moves index to parent
				k = p;
			}
			else 
				break;
		}
	}
	
	private void siftDown()
	{
		//starting with 1st index, as after deleting of first element we replaced last element with first one
		int k = 0;
		
		//getting left
		int left = this.left(k);
		
		while(left < items.size())
		{
			//getting left
			int right = this.right(k);
			int max = left;
			//if right exist
			if(right < items.size())
			{
				//which one is maximum in left and right
				if((items.get(right).compareTo(items.get(max)))>0)
					max+=1;
			}
			
			T parent = items.get(k);
			T child = items.get(max);
			
			//if parent is less, then swap
			if(parent.compareTo(child) < 0)
			{
				//swapping
				items.set(k, child);
				items.set(max, parent);
				//sift down, moving parent index downwards
				k = max;
				//getting the left index
				left = this.left(k);
			}
			else
				break;
		}
		
	}
	
	public void insert(T key)
	{
		if(items.size() == 0)
		{
			items.add(key);
			return;
		}
		else
		{
			items.add(key);
			this.siftUp();
		}
	}
	
	public T extract()
	{
		if(items.size() < 1)
			throw new java.util.NoSuchElementException();
		else if(items.size() == 1)
			return items.remove(0);
		else
		{
			T element  = items.get(0);
			items.set(0, items.remove(items.size() - 1));
			this.siftDown();
			return element;
		}
	}
	
	public void sort()
	{
		ArrayList<T> sortedList = new ArrayList<>();
		while(items.size() != 0)
		{
			sortedList.add(this.extract());
		}
		items.addAll(sortedList);
	}
	public String toString()
	{
		return items.toString();
	}
}

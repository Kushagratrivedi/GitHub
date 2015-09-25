
public class LinkedListLoop {

	public static void main(String[] args) 
	{
		LinkedListLoop lln = new LinkedListLoop();
		lln.createLinkedListLoop();
		System.out.println(lln.findLoop());

	}
	
	
	private LinkedListNode findLoop()
	{
		if(head == null)
			return null;
		LinkedListNode slow = this.head, fast = this.head;
		while(fast != null && fast.next != null)
		{
			slow = slow.next;
			fast = fast.next.next;
			if(slow == fast)
				break;
		}
		if(fast == null || fast.next == null)
			return null;
		slow = head;
		while(slow != fast)
		{
			slow = slow.next;
			fast = fast.next;
			System.out.println(slow+ " -- "+ fast);
		}
		return fast;
		
		
	}
	private LinkedListNode head;
	
	LinkedListLoop()
	{
		head = null;
	}
	
	private class LinkedListNode
	{
		int data;
		LinkedListNode next;
		LinkedListNode(int data, LinkedListNode next)
		{
			this.data = data;
			this.next = next;
		}
		
		public String toString()
		{
			return this.data +"";
		}
	}
	
	LinkedListNode createLinkedListLoop()
	{
		head = new LinkedListNode(1, null);
		head.next = new LinkedListNode(2, null);
		head.next.next = new LinkedListNode(3, null);
		head.next.next.next = new LinkedListNode(4, null);
		head.next.next.next.next = new LinkedListNode(5, null);
		head.next.next.next.next.next = new LinkedListNode(6, null);
		head.next.next.next.next.next.next = new LinkedListNode(7, null);
		head.next.next.next.next.next.next.next = new LinkedListNode(8, null);
		head.next.next.next.next.next.next.next.next = head.next.next.next;
		return head;
	}

}

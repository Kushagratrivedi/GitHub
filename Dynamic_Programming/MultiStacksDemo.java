
public class MultiStacksDemo 
{

	public static void main(String[] args) 
	{
		MultiStack ms = new MultiStack(11, 4);
		ms.push(1, 0);
		ms.push(2, 0);
		ms.push(3, 0);
		
		ms.push(4, 1);
		ms.push(5, 1);
		ms.push(6, 1);
		
		ms.push(7, 2);
		ms.push(8, 2);
		ms.push(9, 2);
		
		System.out.println(ms);
		
		System.out.println(ms.isEmpty(3));
		
		System.out.println(ms.pop(0));
		System.out.println(ms.pop(1));
		System.out.println(ms.pop(2));
		
		System.out.println(ms.peek(1));
		
		System.out.println(ms);
	}

}


public class HeapDemo {

	public static void main(String[] args) 
	{
		Heap<Integer> heap = new Heap<>();
		
		java.util.Random random = new java.util.Random();
		for(int i = 0 ; i < 6 ; i++)
		{
			heap.insert(random.nextInt(50));
			System.out.println(heap);
		}
		/*
		for(int i = 0 ; i < 5 ; i++)
		{
			System.out.println(heap.extract());
			System.out.println(heap);
		}
		*/
		heap.sort();
		System.out.println();
		System.out.println("Heap Sort: "+heap);
	}

}

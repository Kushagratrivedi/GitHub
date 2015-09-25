import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Kushagra
 *
 */
public class LinkedListRemove 
{

	private static Scanner sc;

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
			List<Integer> data  = null;
			
			data  = new LinkedList<Integer>();
			
			System.out.println("Please enter the data: ");
			sc = new Scanner(System.in);
			
			while(true)
			{
				Integer input  = sc.nextInt();
				if(input.equals(0))
				{
					break;
				}
				data.add(input);
				
			}
			String s = "";
			
			for(int i = 0; i< data.size() ; i++)
			{
				s+=data.get(i);
				
				for(int j = i + 1; j < data.size() ; j++)
				{
					if(s.contains(data.get(j).toString()))
					{
						data.remove(j);
					}
				}
				
			}
			
			for(Integer i : data)
			{
				System.out.println(i);
			}
			
			
	}

}

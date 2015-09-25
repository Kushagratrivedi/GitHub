import java.util.Arrays;

/**
 * 
 */

/**
 * @author Kushagra
 *
 */
public class HashTable 
{
	public String [] theArray;
	public int arraySize;
	public int itemsToStored;
	
	public static void main(String args[])
	{
		HashTable ht = new HashTable(30);
		
		String [] arrayWithData = {"1", "5", "9", "11", "25"};
		
		ht.hashFunction1(arrayWithData, ht.theArray);
		ht.displayTheStack();
		
		System.out.println("\n \n");
		
		HashTable ht2= new HashTable(30);
		
		String [] arrayWithData1 = {"1", "5", "9", "11", "25","64","78","112","164","235",
									"456", "420", "535", "355", "888","999","666","555","333","222",
									"753", "357", "159", "951", "459","956","235","876","451","237"};
		
		ht2.hashFunction2(arrayWithData1, ht2.theArray);
		ht2.displayTheStack();
		
		
		System.out.println("\n \n");
		
		HashTable ht3= new HashTable(250);
		
		String [] arrayWithData2 = {"ku", "pq","gh" ,"xy" ,"hj","yu"};
		
		ht3.hashFunction3(arrayWithData2, ht3.theArray);
		ht3.displayTheStack();
		
	}
	
	HashTable(int size)
	{
		this.arraySize = size;
		this.theArray = new String[this.arraySize];
		Arrays.fill(this.theArray, "-1");
	}
	
	public void hashFunction1(String [] arrayWithData, String [] theArray )
	{
		for(int i = 0 ; i < arrayWithData.length ; i++)
		{
			String element = arrayWithData[i];
			this.theArray[Integer.parseInt(element)] = element;
		}
	}
	
	public void hashFunction2(String [] arrayWithData, String [] theArray)
	{
		for(int i = 0; i< arrayWithData.length ; i++)
		{
			String elementValue = arrayWithData[i];
			int arrayIndex = Integer.parseInt(elementValue) % 29;
			System.out.println("Modulus Index: "+arrayIndex+" value: "+elementValue);
			while(theArray[arrayIndex] != "-1")
			{
				arrayIndex+=1;
				System.out.println("Try index "+ arrayIndex);
				arrayIndex%=arraySize;
			}
			this.theArray[arrayIndex] = elementValue;
			
		}
		
	}
	
	public void hashFunction3(String [] arrayWithData, String [] theArray)
	{
		for(int i = 0 ; i < arrayWithData.length ; i++)
		{
			String elementValue = arrayWithData[i];
			int newSum = 0;
			for(int j = 0 ; j < elementValue.length() ; j++)
			{
				int c = elementValue.charAt(j);
				newSum+=c;
			}
			this.theArray[newSum] = elementValue;
		}
	}
	public void displayTheStack() 
	{
		int increment = 0;
		for (int m = 0; m < ((int)this.theArray.length)/10; m++) 
		{
			increment += 10;
			for (int n = 0; n < 71; n++)
				System.out.print("-");
			System.out.println();
			for (int n = increment - 10; n < increment; n++) 
			{
				System.out.format("| %3s " + " ", n);
			}
			System.out.println("|");
			for (int n = 0; n < 71; n++)
				System.out.print("-");
			System.out.println();
			for (int n = increment - 10; n < increment; n++) 
			{
				if (theArray[n].equals("-1"))
					System.out.print("|      ");
				else
					System.out.print(String.format("| %3s " + " ", theArray[n]));
			}
			System.out.println("|");
			for (int n = 0; n < 71; n++)
				System.out.print("-");
			System.out.println();
		}
	}

}

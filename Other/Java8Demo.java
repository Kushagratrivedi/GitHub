package practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Java8Demo {

	public static void main(String[] args) 
	{
		List<String> newList  = new ArrayList<>();
		newList.add("Jignasu");
		newList.add("Devanshi");
		newList.add("Kushagra");
		newList.add("Rahil");
		newList.add("Himani");
		newList.add("Kedar");
		newList.add("Ankita");
		
		
		Stream<String> stream = newList.stream();
		Collections.min(newList);
		Collections.max(newList);
		
		
		

	}

}

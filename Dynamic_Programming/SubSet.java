import java.util.ArrayList;

public class SubSet
{
	public ArrayList<ArrayList<Integer>> getSubSet(ArrayList<Integer> set, int index)
	{
		ArrayList<ArrayList<Integer>> allSubset;
		if(index == set.size())
		{
			allSubset = new ArrayList<>();
			allSubset.add(new ArrayList<Integer>()); // if no elements in set, one subset is empty set
		}
		else
		{
			allSubset = this.getSubSet(set, index + 1);
			int item = set.get(index);
			ArrayList<ArrayList<Integer>> moreSubset = new ArrayList<>();
			for(ArrayList<Integer> subset : allSubset)
			{
				ArrayList<Integer> newsubset = new ArrayList<>();
				newsubset.addAll(subset);
				newsubset.add(item);
				
				moreSubset.add(newsubset);
			}
			allSubset.addAll(moreSubset);
		}
		System.out.println("Iteration: "+allSubset);
		return allSubset;
	}
	
	
	public ArrayList<ArrayList<Integer>> getSubset2(ArrayList<Integer> st)
	{
		ArrayList<ArrayList<Integer>> allSubset = new ArrayList<>();
		int max = 1 << st.size();
		for(int i = 0 ; i < max ;i++)
		{
			ArrayList<Integer> list = convertToIntSet(st, i);
			allSubset.add(list);
		}
		return allSubset;
	}
	private ArrayList<Integer> convertToIntSet(ArrayList<Integer> st, int i) 
	{
		ArrayList<Integer> list = new ArrayList<>();
		int index = 0;
		for(int j = i ; j > 0 ; j = j >> 1)
		{
			if(( j & 1) == 1)
				list.add(st.get(index));
			index++;
		}
		return list;
	}


	public void printSubset(ArrayList<ArrayList<Integer>> subset)
	{
		System.out.println(subset.toString());
		
	}
	
	public static void main(String args[])
	{
		ArrayList<Integer> set = new ArrayList<>();
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);
		
		SubSet s = new SubSet();
		
		s.printSubset(s.getSubSet(set, 0));
		s.printSubset(s.getSubset2(set));
	}
}
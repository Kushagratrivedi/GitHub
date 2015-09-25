import java.util.Arrays;


public class JobScheduling {

	public static void main(String[] args) 
	{
		Job [] jobs = new Job[6];
		jobs[0] = new Job(1,3,5);
		jobs[1] = new Job(2,5,6);
		jobs[2] = new Job(4,6,5);
		jobs[3] = new Job(5,8,11);
		jobs[4] = new Job(6,7,4);
		jobs[5] = new Job(7,9,2);
		//printArray(jobs);
		Arrays.sort(jobs);
		printArray(jobs);
		System.out.println(jobScheduling(jobs));

	}
	public static void printArray(Object [] jobs)
	{
		for(Object job : jobs)
		{
			System.out.println((Job)job);
		}
		System.out.println();
	}
	
	public static int max(int a, int b)
	{
		return a > b ? a : b;
	}
	public static int jobScheduling(Job[] jobs)
	{
		if(jobs == null)
			throw new NullPointerException();
		Arrays.sort(jobs);
		int [] profit = new int[jobs.length];
		int i, j = 0;
		for(i = 1 ; i < jobs.length; i++)
		{
			for(j= 0 ; j < i ; j++)
			{
				System.out.println(i +" --> "+ j +" = "+ jobs[i].isOverlap(jobs[j]));
				if(!jobs[j].isOverlap(jobs[i]))
				{
					profit[i] = max(profit[i], profit[j] + jobs[i].profit);
				}
			}
		}
		
		int max = -1;
		for(int k = 0 ; k < jobs.length ; k++)
		{
			//System.out.println(profit[k]);
			if(max < profit[k])
				max = profit[k];
		}
		return max;
	}

}

class Job implements Comparable<Job>
{
	int start;
	int end;
	int profit;
	public Job(int start, int end, int profit)
	{
		this.start = start;
		this.end = end;
		this.profit = profit;
	}
	@Override
	public int compareTo(Job other) 
	{
		if(this.end < other.end)
			return -1;
		else if(this.end > other.end)
			return 1;
		else 
			return 0;
		
	}
	
	public boolean isOverlap(Job other)
	{
		if(this.start < other.start && this.end > other.start)
		{
			if(this.end < other.end || this.end > other.end)
				return true;
		}
		if(this.start > other.start && this.end > other.start)
		{
			if(this.end > other.end || this.end < other.end)
				return true;
		}
		if(this.start == other.start && this.end == other.end)
			return true;
		return false;
	}
	
	
	public String toString()
	{
		return "{(Start: "+this.start+", End: "+this.end+"), Profit: ("+this.profit+")}";
	}
	
}

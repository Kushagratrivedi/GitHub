
public class MinimumJumpsToEnd {

	public static void main(String[] args)
	{
		minimumJumpsToEnd(new int[]{2,3,1,1,2,4,2,0,1,1});

	}
	
	public static int minimumJumpsToEnd(int [] steps)
	{
		if(steps == null)
			throw new NullPointerException("Array Is Null");
		if(steps.length == 1)
			return 0;
		int [] maxJumps = new int[steps.length];
		int [] subSeq = new int[steps.length];
		for(int i = 1 ; i < maxJumps.length ; i++)
		{
			maxJumps[i] = Integer.MAX_VALUE;
		}
		for(int i = 1; i<steps.length ; i++)
		{
			for(int j = 0; j<i;j++)
			{
				if(steps[j] + j >= i && maxJumps[j] + 1 < maxJumps[i])
				{
					maxJumps[i] = maxJumps[j] + 1; // lend on i from j, using maxJumps[i] steps
					subSeq[i] = j; // lend on i from j
				}
			}
		}
		
		System.out.println("Maximum Jumps needed: "+maxJumps[steps.length - 1]);
		System.out.print("Sequence: ");
		int i;
		for(i = steps.length - 1 ; i != subSeq[i];)
		{
			System.out.print(i +" <- ");
			i = subSeq[i];
		}
		System.out.print(i);
		return maxJumps[steps.length-1];
	}

}


public class Point 
{
	int x;
	int y;
	boolean isFree;
	public Point()
	{
		super();
		this.x = 0;
		this.y = 0;
		isFree = true;
	}
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
		isFree = true;
	}
	public double getDistance(Point other)
	{
		int x = this.x - other.x;
		int y = this.y - other.y;
		int x2 = x * x;
		int y2 = y * y;
		return Math.sqrt(x2 + y2);
	}
	
	public boolean isFree()
	{
		return this.isFree;
	}
	
	public String toString()
	{
		return "("+this.x+", "+this.y+")";
	}

}

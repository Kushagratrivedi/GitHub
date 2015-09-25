
public class Edge implements Comparable<Edge>
{
	Vertex from, to;
	private int weight;
	public Edge(Vertex from, Vertex to, int weight)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public int getWeight()
	{
		return this.weight;
	}
	
	public String toString()
	{
		return "("+this.from+", "+this.to+", "+weight+")";
	}

	@Override
	public int compareTo(Edge arg0) 
	{
		return arg0.from.equals(this.from) && arg0.to.equals(this.to) && arg0.weight == this.weight ? 0 : 1;

	}
	
}

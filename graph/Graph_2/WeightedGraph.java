import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;


public class WeightedGraph 
{
	private HashMap<String, Vertex> myVertices;
	private HashMap<Vertex, TreeSet<Edge>> myEdges;
	
	public WeightedGraph()
	{
		this.myVertices = new HashMap<>();
		this.myEdges = new HashMap<>();
		
	}
	
	public Vertex getVertex(String name)
	{
		return this.myVertices.get(name);
	}
	
	public Vertex addVertex(String name)
	{
		Vertex vertex;
		vertex = this.getVertex(name);
		if(vertex == null)
		{
			vertex = new Vertex(name);
			this.myVertices.put(name, vertex);
			this.myEdges.put(vertex, new TreeSet<Edge>());
		}
		return vertex;
	}
	
	public boolean hasVertex(String name)
	{
		return this.myVertices.containsKey(name);
	}
	
	public boolean hasEdge(String from, String to, int weight)
	{
		if(!this.hasVertex(from) || !this.hasVertex(to))
			return false;
		return this.myEdges.get(this.myVertices.get(from)).contains(new Edge(this.myVertices.get(from), this.myVertices.get(to), weight));
		
	}
	
	public void addEdge(String from, String to, int weight)
	{
		//System.out.println(from +" -- "+to);
		Vertex u, v;
		if(this.hasEdge(from, to, weight))
		{
			//System.out.println(from+ " -- " +to);
			return;
		}
			
		if(( u = this.getVertex(from)) == null)
			u = this.addVertex(from);
		if((v = this.getVertex(to)) == null)
			v = this.addVertex(to);
		Edge e = new Edge(this.myVertices.get(from), this.myVertices.get(to), weight);
		Edge f = new Edge(this.myVertices.get(to), this.myVertices.get(from), weight);
		this.myEdges.get(u).add(e);
		this.myEdges.get(v).add(f);
		
		
		
	}
	
	public String toString()
	{
		return this.myEdges.toString();
	}

}

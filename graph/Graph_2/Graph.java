import java.util.HashMap;
import java.util.HashSet;


public class Graph 
{
	private HashMap<String, Vertex> vertices;
	private HashMap<Vertex, HashSet<Vertex>> adjList= new HashMap<>();
	
	public Graph()
	{
		this.vertices  = new HashMap<>();
		this.adjList = new HashMap<>();
	}
	
	public Vertex addVertex(String name)
	{
		Vertex v;
		v = this.getVertex(name);
		if(v == null)
		{
			v = new Vertex(name);
			this.vertices.put(name, v);
			this.adjList.put(v, new HashSet<Vertex>());
		}
		return v;
	}
	
	public Vertex getVertex(String name)
	{
		return this.vertices.get(name);
	}
	
	public boolean hasVertex(String name)
	{
		return this.vertices.containsKey(name);
	}
	
	public boolean hasEdge(String from, String to)
	{
		if(!this.hasVertex(from) || !this.hasVertex(to))
			return false;
		return this.adjList.get(this.vertices.get(from)).contains(this.vertices.get(to));
	}
	
	public void addEdge(String from, String to)
	{
		Vertex u, v;
		if(this.hasEdge(from, to))
			return;
		if((u = this.getVertex(from)) == null)
			u = this.addVertex(from);
		if((v = this.getVertex(to)) == null)
			v= this.addVertex(to);
		this.adjList.get(u).add(v);
		this.adjList.get(v).add(u);
	}
	
	public String toString()
	{
		return this.adjList.toString();
	}
}


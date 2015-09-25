/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 *
 * @author Kushagra
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {
    private HashMap<Vertex, TreeSet<Vertex>> myAdjList;
	private HashMap<String, Vertex> myVertices;
	private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<>();
	private int myNumVertices;
	private int myNumEdges;

	/**
	 * Construct empty Graph
	 */
	public Graph() {
		myAdjList = new HashMap<>();
		myVertices = new HashMap<>();
		myNumVertices = myNumEdges = 0;

	}

	/**
	 * Add a new vertex name with no neighbors (if vertex does not yet exist)
	 * 
	 * @param name
	 *            vertex to be added
	 */
        public enum Status
        {
            unvisited, visiting, visited;
        }
        
        public boolean hasPath(Vertex u, Vertex v)
        {
            
            if(!this.hasVertex(u.name) || !this.hasVertex(v.name))
                throw new java.util.NoSuchElementException("Vertex does not exist");
            if(this.hasEdge(u.name, v.name))
                return true;
            Deque<Vertex> queue = new LinkedList<>();
            HashMap<Vertex, Status> status = new HashMap<>();
            Iterator<Vertex> itr = this.getVertices().iterator();
            while(itr.hasNext())
            {
                status.put(itr.next(), Status.unvisited);
            }
            queue.addLast(u);
            //System.out.println(queue.isEmpty());
            while(!queue.isEmpty())
            {
                Vertex w = queue.pollFirst();
                if(status.get(w).equals(Status.visited))
                    continue;
                System.out.println(w+ " -- "+ v);
                if(w.name.equals(v.name))
                    return true;
                TreeSet<Vertex> set = this.myAdjList.get(w);
                System.out.println(set);
                queue.addAll(set);
                status.replace(w, Status.visited);
            }
            return false;
        }

    /**
     *
     */
        public void depthFirstTraversal()
        {
            if(myVertices.isEmpty())
                return;
            Stack<Vertex> stack;
            stack = new Stack<>();
            HashMap<Vertex, Status> statusMap = new HashMap<>();
            Iterator<Vertex> itr = this.getVertices().iterator();
            while(itr.hasNext())
            {
                statusMap.put(itr.next(), Status.unvisited);
            }
            itr = this.getVertices().iterator();
            stack.add(itr.next());
            while(!stack.empty())
            {
                Vertex v = stack.pop();
                if(statusMap.get(v) == Status.visited)
                    continue;
                System.out.print(v.name+" --> ");
                statusMap.replace(v, Status.visited);
                TreeSet<Vertex> ts = this.myAdjList.get(v);
                while(!ts.isEmpty())
                    stack.push(ts.pollFirst());
            }
        }
        
        public void depthFirstSearch()
        {
            HashMap<Vertex, Status> status = new HashMap<>();
            Iterator<Vertex> itr = this.getVertices().iterator();
            while(itr.hasNext())
            {
                status.put(itr.next(), Status.unvisited);
            }
            Vertex v = this.myVertices.values().iterator().next();
            this.depthFirstSearchVisit(v, status);
        }
        
        private void depthFirstSearchVisit(Vertex v, HashMap<Vertex, Status> status)
        {
            if(status.get(v) == Status.unvisited)
            {
                System.out.print(v.name+" --> ");
                status.replace(v, Status.visited);
            }
                
            Iterator<Vertex> itr = this.adjacentTo(v).iterator();
            while(itr.hasNext())
            {
               // System.out.println("chcekc 1");
                Vertex u = itr.next();
                if(status.get(u)== Status.unvisited)
                    this.depthFirstSearchVisit(u, status);
            }
        }
        public void breathFirstTraversal()
        {
            
            if(myVertices.isEmpty())
                return;
            HashMap<Vertex, Status> traverseStatus = new HashMap<>();
            Queue<Vertex> queue;
            queue = new PriorityQueue<>();
            Iterator<Vertex> itr = myVertices.values().iterator();
            while(itr.hasNext())
            {
                traverseStatus.put(itr.next(), Status.unvisited);
            }
            itr = myVertices.values().iterator();
            queue.add(itr.next());
            while(!queue.isEmpty())
            {
                Vertex v= queue.poll();
                if(traverseStatus.get(v) == Status.visited)
                    continue;
                System.out.print(v.name +" --> ");
                TreeSet<Vertex> t1 = myAdjList.get(v);
                queue.addAll(t1);
                traverseStatus.replace(v, Status.visited);
            }
        }
        
	public Vertex addVertex(String name) {
		Vertex v;
		v = myVertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			myVertices.put(name, v);
			myAdjList.put(v, new TreeSet<>());
			myNumVertices += 1;
		}
		return v;
	}

	/**
	 * Returns the Vertex matching v
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return the Vertex with a name that matches v or null
	 * if no such Vertex exists in this Graph
	 */
	public Vertex getVertex(String name) {
		return myVertices.get(name);
	}

	/**
	 * Returns true iff v is in this Graph, false otherwise
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return true iff v is in this Graph
	 */
	public boolean hasVertex(String name) {
		return myVertices.containsKey(name);
	}

	/**
	 * Is from-to, an edge in this Graph. The graph is 
	 * undirected so the order of from and to does not
	 * matter.
	 * @param from the name of the first Vertex
	 * @param to the name of the second Vertex
	 * @return true iff from-to exists in this Graph
	 */
	public boolean hasEdge(String from, String to) {

		if (!hasVertex(from) || !hasVertex(to))
			return false;
		return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
	}
	
	/**
	 * Add to to from's set of neighbors, and add from to to's
	 * set of neighbors. Does not add an edge if another edge
	 * already exists
	 * @param from the name of the first Vertex
	 * @param to the name of the second Vertex
	 */
	public void addEdge(String from, String to) {
		Vertex v, w;
		if (hasEdge(from, to))
			return;
		myNumEdges += 1;
		if ((v = getVertex(from)) == null)
			v = addVertex(from);
		if ((w = getVertex(to)) == null)
			w = addVertex(to);
		myAdjList.get(v).add(w);
		myAdjList.get(w).add(v);
	}

	/**
	 * Return an iterator over the neighbors of Vertex named v
	 * @param name the String name of a Vertex
	 * @return an Iterator over Vertices that are adjacent
	 * to the Vertex named v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(String name) {
		if (!hasVertex(name))
			return EMPTY_SET;
		return myAdjList.get(getVertex(name));
	}

	/**
	 * Return an iterator over the neighbors of Vertex v
	 * @param v the Vertex
	 * @return an Iterator over Vertices that are adjacent
	 * to the Vertex v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(Vertex v) {
		if (!myAdjList.containsKey(v))
			return EMPTY_SET;
		return myAdjList.get(v);
	}

	/**
	 * Returns an Iterator over all Vertices in this Graph
	 * @return an Iterator over all Vertices in this Graph
	 */
	public Iterable<Vertex> getVertices() {
		return myVertices.values();
	}

	public int numVertices()
	{
		return myNumVertices;
	}
	
	public int numEdges()
	{
		return myNumEdges;
	}
	
	/**
	 * Sets each Vertices' centrality field to
	 * the degree of each Vertex (i.e. the number of
	 * adjacent Vertices)
	 */
	public void degreeCentrality()
	{
		// TODO: complete degreeCentrality
	}
	
	/**
	 * Sets each Vertices' centrality field to
	 * the average distance to every Vertex
	 */
	public void closenessCentrality()
	{
		// TODO: complete closenessCentrality
	}
	/**
	 * Sets each Vertices' centrality field to
	 * the proportion of geodesics (shortest paths) that
	 * this Vertex is on
	 */
	public void betweennessCentrality()
	{
		// TODO: complete betweennessCentrality
	}
	/*
	 * Returns adjacency-list representation of graph
	 */
    @Override
	public String toString() {
		String s = "";
		for (Vertex v : myVertices.values()) {
			s += v + ": ";
                        s = myAdjList.get(v).stream().map((w) -> w + " ").reduce(s, String::concat);
			s += "\n";
		}
		return s;
	}

	private String escapedVersion(String s) {
		return "\'"+s+"\'";

	}

	public void outputGDF(String fileName)
    {
    	HashMap<Vertex, String> idToName = new HashMap<>();
    	 try {
                try (FileWriter out = new FileWriter(fileName)) {
                    int count = 0;
                    out.write("nodedef> name,label,style,distance INTEGER\n");
                    // write vertices
                    for (Vertex v: myVertices.values())
                    {
                        String id = "v"+ count++;
                        idToName.put(v, id);
                        out.write(id + "," + escapedVersion(v.name));
                        out.write(",6,"+v.distance+"\n");
                    }
                    out.write("edgedef> node1,node2,color\n");
                    // write edges
                    for (Vertex v : myVertices.values())
                        for (Vertex w : myAdjList.get(v))
                            if (v.compareTo(w) < 0)
                            {
                                out.write(idToName.get(v)+","+
                                        idToName.get(w) + ",");
                                if (v.predecessor == w ||
                                        w.predecessor == v)
                                    out.write("blue");
                                else
                                    out.write("gray");
                                out.write("\n");
                            }
                }
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 
    }

	public static void main(String[] args) {
		Graph G = new Graph();
		G.addEdge("A", "B");
		G.addEdge("A", "C");
		G.addEdge("C", "D");
		G.addEdge("D", "E");
		G.addEdge("D", "G");
		G.addEdge("E", "G");
		G.addVertex("H");
		System.out.println(G.escapedVersion("Bacon, Kevin"));
		// print out graph
		System.out.println(G);

		// print out graph again by iterating over vertices and edges
		for (Vertex v : G.getVertices()) {
			System.out.print(v + ": ");
			for (Vertex w : G.adjacentTo(v.name)) {
				System.out.print(w + " ");
			}
			System.out.println();
		
                }
                
                // System.out.println(G.adjacentTo("A").iterator().hasNext());
                //G.depthFirstSearch();
                
                System.out.println();
                
                //G.breathFirstTraversal();
                
                System.out.println();
                
                //G.depthFirstTraversal();
                
                System.out.println();
                
                //G.depthFirstSearch();
                boolean s =G.hasPath(G.getVertex("A"), G.getVertex("G"));
                System.out.println(s);
                
		G.outputGDF("graph.gdf");
	}
}
class Vertex implements Comparable<Vertex> {
	/**
	 * label for Vertex
	 */
	public String name;  
	/**
	 * length of shortest path from source
	 */
	public int distance; 
	/**
	 * previous vertex on path from sourxe
	 */
	public Vertex predecessor; // previous vertex
	
	/**
	 * a measure of the structural importance of a vertex.
	 * The value should initially be set to zero. A higher
	 * centrality score should mean a Vertex is more central.
	 */
	public double centrality;
	/**
	 * Infinite distance indicates that there is no path
	 * from the source to this vertex
	 */
	public static final int INFINITY = Integer.MAX_VALUE;

	public Vertex(String v)
	{
		name = v;
		distance = INFINITY; // start as infinity away
		predecessor = null;
		centrality = 0.0;
	}

	/**
	 * The name of the Vertex is assumed to be unique, so it
	 * is used as a HashCode
	 * 
	 * @see java.lang.Object#hashCode()
	 */
        @Override
	public int hashCode()
	{
		return name.hashCode();
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.distance != other.distance) {
            return false;
        }
        if (!Objects.equals(this.predecessor, other.predecessor)) {
            return false;
        }
        return Double.doubleToLongBits(this.centrality) == Double.doubleToLongBits(other.centrality);
    }
	
        @Override
	public String toString()
	{ 
		return name;
	}
	/**
	 * Compare on the basis of distance from source first and 
	 * then lexicographically
	 */
        @Override
	public int compareTo(Vertex other)
	{
		int diff = distance - other.distance;
		if (diff != 0)
			return diff;
		else
			return name.compareTo(other.name);
	}
}

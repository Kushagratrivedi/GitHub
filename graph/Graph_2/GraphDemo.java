
public class GraphDemo {

	public static void main(String[] args)
	{
		Graph g= new Graph();
		g.addEdge("A", "B");
		g.addEdge("A", "C");
		g.addEdge("A", "D");
		g.addEdge("B", "C");
		g.addEdge("C", "D");
		g.addEdge("D", "E");
		g.addEdge("E", "F");
		g.addEdge("B", "E");
		g.addEdge("C", "F");
		
		g.addVertex("H");
		
		
		System.out.println(g);
		
		
		WeightedGraph g1= new WeightedGraph();
		g1.addEdge("A", "B", 350);
		g1.addEdge("A", "C", 340);
		g1.addEdge("A", "D", 110);
		g1.addEdge("B", "C", 332);
		g1.addEdge("C", "D", 30);
		g1.addEdge("D", "E", 50);
		g1.addEdge("E", "F", 330);
		g1.addEdge("B", "E", 440);
		g1.addEdge("C", "F", 50);
		g1.addEdge("C", "F", 50);
		
		g1.addVertex("H");
		
		
		System.out.println(g1);
	}

}

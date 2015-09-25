/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter_4;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Kushagra
 */
public class Graph 
{
    private HashMap<String, Vertex> vertices;
    private final TreeSet<Vertex> EMPTY_SET = new TreeSet<>();
    private HashMap<Vertex, TreeSet<Vertex>> edges;
    private int numberOfEdges;
    private int numberOfVertices;
    public Graph()
    {
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }
    public Vertex addVertex(String name)
    {
        Vertex v;
        if(vertices.containsKey(name))
            return vertices.get(name);
        numberOfVertices+=1;
        v = new Vertex(name);
        vertices.put(name, v);
        edges.put(v, new TreeSet<>());
        
        return v;
    }
    public Vertex getVertex(String name)
    {
        return this.vertices.get(name);
    }
    public void addEdge(String from, String to)
    {
        if(this.hasEdge(from, to))
            return;
        Vertex v, u;
        if((v = this.getVertex(from)) == null)
            v = this.addVertex(from);
        if((u = this.getVertex(to)) == null)
            u = this.addVertex(to);
        this.edges.get(v).add(u);
        
    }
    public boolean hasVertex(String name)
    {
        return vertices.containsKey(name);
    }
    public boolean hasEdge(String from, String to)
    {
        if(this.hasVertex(to) && this.hasVertex(from))
            return this.edges.get(vertices.get(from)).contains(vertices.get(to));
        return false;
    }
    public int edgesCount()
    {
        return this.numberOfEdges;
    }
    public int vertexCount()
    {
        return this.numberOfVertices;
    }
    
}
class Vertex
{
    String name;
    Vertex(String name)
    {
        this.name = name;
    }
}

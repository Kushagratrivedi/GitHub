/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.ProjectDependency;

/**
 *
 * @author Kushagra
 */
public class ProjectDependency {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    
    public Graph createGraph(String[][] dependencies)
    {
       Graph g = new Graph();
       for(String [] dependency : dependencies)
       {
           g.addEdge(dependency[1], dependency[0]);
       }
       return g;
    }
    
    
}

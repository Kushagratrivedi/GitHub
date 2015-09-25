/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.ProjectDependency;

import java.util.HashMap;

/**
 *
 * @author Kushagra
 */
public class Graph 
{
    HashMap<String, Project> projects;
    public Graph()
    {
        projects = new HashMap<>();
    }
    public Project addProject(String name)
    {
        Project project;
        if((project = projects.get(name)) == null)
        {
            project = new Project(name);
            projects.put(name, project);
        }
        return project;
    }
    
    public void addEdge(String before, String after)
    {
        Project u = this.addProject(after);
        Project v = this.addProject(before);
        v.addNeighbour(u);
    }
    
}

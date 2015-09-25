/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.ProjectDependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Kushagra
 */
public class Project 
{
    String name;
    int depedencies;
    HashSet<Project> children = new HashSet<>();
    //HashMap<String, Project> childrenMap = new HashMap<>();
    public Project(String name)
    {
        this.name = name;
    }
    
    public Project addNeighbour(Project p)
    {
        if(!children.contains(p))
        {
            children.add(p);
            this.incrementDependency();
        }
        return p;
    }
    
    public void incrementDependency()
    {
        this.depedencies++;
    }
    
    public void decrementDependency()
    {
        this.depedencies--;
    }
    
    public int getDependency()
    {
        return this.depedencies;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
}

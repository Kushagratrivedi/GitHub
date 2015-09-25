
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 * @param <K> Key
 * @param <V> Value
 */
public class HashMapList<K, V> 
{
    private HashMap<K, ArrayList<V>> map;
    public HashMapList()
    {
        this.map = new HashMap<>();
        
    }
    
    public void put(K key, V value)
    {
        ArrayList<V> valueList;
        if(key == null) return;
        if(this.map.containsKey(key))
            valueList = this.map.get(key);
        else
        {
            valueList = new ArrayList<>();
            this.map.put(key, valueList);
        }
        valueList.add(value);
            
    }
    
    public void putAll(K key, ArrayList<V> values)
    {
        ArrayList<V> _values;
        if(key == null) return;
        if(this.map.containsKey(key))
        {
            _values = this.map.get(key);
            _values.addAll(values);
        }
        else
        {
            this.map.put(key, values);
        }
    }
    
    public ArrayList<V> get(K key)
    {
        
        return this.map.get(key);
        
    }
    
    public int size()
    {
        return this.map.size();
    }
    
    public Set<K> keySet()
    {
        return this.map.keySet();
    }
    
    public Set<ArrayList<V>> valueSet()
    {
        return (Set<ArrayList<V>>) this.map.values();
    }
    
}

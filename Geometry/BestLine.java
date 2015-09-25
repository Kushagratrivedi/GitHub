
import java.util.ArrayList;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class BestLine 
{
    public Line getBestLine(Point [] points)
    {
        HashMapList<Double, Line> allLines = getAllLinesFromPoints(points);
        Line line = getBestLine(allLines);
        return line;
    }

    private HashMapList<Double, Line> getAllLinesFromPoints(Point[] points) 
    {
        HashMapList<Double, Line> allLines = new HashMapList<>();
        for(int i = 0 ; i < points.length ; i++)
        {
            for(int j = i + 1 ; j < points.length ; j++)
            {
                Point p1 = points[i];
                Point p2 = points[j];
                Line line = new Line(p1, p2);
                Double nearestSlope = Line.getNearestSlope(line.slope);
                allLines.put(nearestSlope, line);
            }
        }
        return allLines;
    }

    private Line getBestLine(HashMapList<Double, Line> allLines) 
    {
        Line bestLine = null;
        int bestLineCount = 0;
        
        Set<Double> slopes = allLines.keySet();
        for(double slope : slopes)
        {
            ArrayList<Line> lines = allLines.get(slope);
            for(Line line:lines)
            {
                int count = getSameLineCount(allLines, line);
            
                if(count > bestLineCount)
                {
                    count = bestLineCount;
                    bestLine = line;
                    
                }
            }
                
        }
        return bestLine;
    }

    private int getSameLineCount(HashMapList<Double, Line> allLines, Line line) 
    {
        int count = 0;
        double key = Line.getNearestSlope(line.slope);
        count+= getCount(allLines.get(key), line);
        count+= getCount(allLines.get(key - Line.epsilon), line);
        count+= getCount(allLines.get(key + Line.epsilon), line);
        return count;
    }
    
    private int getCount(ArrayList<Line> lines, Line line)
    {
        if(lines == null) return 0;
        int count = 0;
        for(Line sameLine : lines)
        {
            if(sameLine.isEquivalent(line))
                count+=1;
        }
        return count;
    }
    
    
}

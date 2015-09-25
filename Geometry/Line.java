/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 
 */
public class Line
{
    
    public static final double epsilon = 0.00001;
    double slope, y_intercept;
    boolean infinite_slope = false;
    private Point p1, p2;
    
    
    public Line(Point p1, Point p2)
    {
        this.p1 = p1;
        this.p2 = p2;
        if(Math.abs(p1.getX() - p2.getX()) > Line.epsilon)
        {
            this.slope = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
            this.y_intercept = p2.getY() - this.slope * p2.getX();
        }
        else
        {
            this.infinite_slope = true;
            this.y_intercept = p2.getX();
        }
    }
    
    public  static double getNearestSlope(double slope)
    {
        int r = (int)(slope / Line.epsilon);
        return ((double) r) * Line.epsilon;
    }
    
    public boolean isEquivalent(Line other)
    {
        return this.isEquivalent(this.slope, other.slope) 
                && this.isEquivalent(this.y_intercept, other.y_intercept)
                && this.infinite_slope == other.infinite_slope;
    }

    private boolean isEquivalent(double a, double b) 
    {
        return Math.abs(a - b) < Line.epsilon;
        
    }
    
    @Override
    public String toString()
    {
        return "["+this.p1+", "+this.p2+"]";
    }
    
}

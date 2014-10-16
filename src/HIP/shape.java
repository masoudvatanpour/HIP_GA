package HIP;

import java.awt.Polygon;
import java.awt.geom.Point2D;

public class shape {
	public int xcenter;
	public int ycenter;
	public String name;
	public Polygon poly;
	public int angleStep = 90;

 
	public shape(){  
	     poly = new Polygon();
	     setCenterToRandomLocation();
	} 
	
	public double getArea(){
		return PolygonUtilities.area(getVertices2D());		
	}
	
	public Point2D[] getVertices2D(){
		Point2D [] points = new Point2D[poly.npoints];
		for (int i = 0; i < poly.npoints; i++) {
			points[i] = new Point2D.Double();
			points[i].setLocation(poly.xpoints[i], poly.ypoints[i]);					
		}
		if(points.length == 0) 
			System.err.print("Size of point array is Zero: getVertices2D/shape.java");
		return points;
	}
	
	public void setCenterToRandomLocation(){
		ycenter = (int)(Math.random()*global.hight);            
		xcenter = (int)(Math.random()*global.width);
		
//		//for test
//		xcenter = 200;
//		ycenter = 200; 
		
		Polygon translatedPoly = new Polygon();
		for (int i = 0; i < poly.npoints; i++) {
	    	 translatedPoly.addPoint(poly.xpoints[i]+xcenter,poly.ypoints[i]+ycenter);
	    }
		poly = translatedPoly;
	}
	
	public void setCenterToLocation(int x, int y){
		ycenter = y;            
		xcenter = x;
		
		Polygon translatedPoly = new Polygon();
		for (int i = 0; i < poly.npoints; i++) {
	    	 translatedPoly.addPoint(poly.xpoints[i]+xcenter,poly.ypoints[i]+ycenter);
	    }
		poly = translatedPoly;
	}
		
	
	public void printSpecs(){
		if(name == "c"){
			circle c = (circle)this;
			c.printYourSelf();
		}else if(name == "r"){
			rectangle c = (rectangle)this;
			c.printYourSelf();
		}else if(name == "cw"){
			circleWedge c = (circleWedge)this;
			c.printYourSelf();
		}else if(name == "t"){
			triangle c = (triangle)this;
			c.printYourSelf();
		}			
	}
	
	public void applyRotation(double angleInDegree){
		double angle = Math.toRadians(angleInDegree);		
		Polygon result = new Polygon();
		
		for (int i = 0; i < poly.npoints; i++) {
			
			//TRANSLATE TO ORIGIN
			double x1 = poly.xpoints[i] - xcenter;
			double y1 = poly.ypoints[i] - ycenter;			

			double newX;
			double newY;
			
			//APPLY ROTATION
			newX = x1 * Math.cos(angle) - y1 * Math.sin(angle);
			newY = x1 * Math.sin(angle) + y1 * Math.cos(angle);

			//TRANSLATE BACK
			result.addPoint((int)(newX + xcenter), (int)(newY + ycenter));
		}
		poly = result;
	}	
}
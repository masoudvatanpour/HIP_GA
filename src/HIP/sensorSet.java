package HIP;

import java.util.ArrayList;

public class sensorSet {	
	public ArrayList<shape> set;
	public int angleStep = 90;
	
    public sensorSet(){
    	set = new ArrayList<shape>();
    	for (int i = 0; i < global.numberOfCircles; i++) {
    		set.add(new circle(100, 20));
		}    	
    	for (int i = 0; i < global.numberOfCircleWedges; i++) {
        	set.add(new circleWedge(150,90,360-(int)(Math.random()*(360/angleStep))*angleStep,360));
		}        
    	for (int i = 0; i < global.numberOfRectangles; i++) {
        	set.add(new rectangle(200,150));
		}
    	for (int i = 0; i < global.numberOfSquares; i++) {
        	set.add(new rectangle(175,175));
		}
    	for (int i = 0; i < global.numberOfTriangles; i++) {
        	set.add(new triangle(100,90,-(int)(Math.random()*(360/angleStep))*angleStep));
		}
    }
    
    public shape pickSensor(){
    	if(set.size() == 0){
    		System.err.println("Ran Out of Sensors: pickSensor/sensorSet");
    		System.exit(0);
    	}
    	int whichtype = (int)(Math.random()*(set.size()));
    	shape result = set.get(whichtype);
    	set.remove(whichtype);
    	return result;
    }
    
    public int getNum(){
    	return set.size();
    }
    
    public void printSet(){
    	if(set.isEmpty()){
    		System.out.println("Set is Empty");
    		return;
    	}
		System.out.println("Printing Set ...");
    	for (int i = 0; i < set.size(); i++)
    		set.get(i).printSpecs();    		
    	System.out.println();			 	
    }       
}

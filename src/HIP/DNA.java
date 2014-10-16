package HIP;

import java.util.ArrayList;

public class DNA {
    int[][] room = new int[global.hight][global.width];
    public ArrayList<shape> krom;
    public int kromSize;
    boolean isChoosed=false;
    sensorSet sensorSet;
    int angleStep = 90; 
    
    public DNA(int len){
    	kromSize = len;
    	krom = new ArrayList<shape>();
    	sensorSet = new sensorSet();	
    	init();
    }
 
    public DNA(DNA dna){
    	kromSize = dna.kromSize;
    	krom = new ArrayList<shape>();
    	for (int i = 0; i < dna.krom.size(); i++) {
    		krom.add(dna.krom.get(i));
    	}	
    	sensorSet = dna.sensorSet;    	
    }

    public void init(){
    	if(sensorSet.set.size() <= 0) 
    		System.err.println("No more Sensors: init/DNA");
    	for (int i = 0; i < kromSize; i++) {
    		int whichShape = (int)(Math.random()*sensorSet.getNum());
			krom.add(sensorSet.set.get(whichShape));
			sensorSet.set.remove(whichShape);						
		}
    }

    public void print(){
    	for (int i = 0; i < krom.size(); i++) {
    		krom.get(i).printSpecs();
		}    	
    }
    
    public double areaCost(){
    	double costArea = 0;
    	for (int i = 0; i < krom.size(); i++) {
			costArea += krom.get(i).getArea();
		}
    	return costArea;    	
    }
    
    public void mutateToAnotherShape(int place){
    	if(sensorSet.set.isEmpty()){
        	krom.get(place).applyRotation((int)(Math.random()*(360/angleStep))*angleStep);      
    	}else{
    		shape previous = krom.get(place);	    	
	    	krom.set(place,sensorSet.pickSensor());    	
	    	sensorSet.set.add(previous);	    	
    	}
    }

    public void setChoosed(boolean newVal){
        isChoosed=newVal;
    }

    public boolean getChoosed(){
        return isChoosed;
    }

    public void setSensorType(int kromNum, String name){
    	krom.get(kromNum).name = name;
    }
    
    public String getSensorType(int kromNum){
    	return krom.get(kromNum).name;
    }
    
    public int length(){
        return kromSize;
    }
    
    public void changeCenterX(int place, int val){
    	krom.get(place).xcenter = val;
    }
    
    public void changeCenterY(int place, int val){
    	krom.get(place).ycenter = val;
    }
}

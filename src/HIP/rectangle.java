package HIP;

public class rectangle extends shape{
	 int width;
	 int hight;
	 public int orientation;
	 
	 public rectangle(int hight, int width){
		 this.name = "r";
		 this.hight = hight;		
		 this.width = width;
		 
		 poly.addPoint(-width/2, -hight/2);
		 poly.addPoint(-width/2, hight/2);
		 poly.addPoint(width/2, hight/2);
		 poly.addPoint(width/2, -hight/2);				 		 
		 orientation = (int)(Math.random()*2)*90;
		 
		 setCenterToRandomLocation();
//		 applyRotation(orientation);		 
	 }	 	
	 
	 public int getHight(){
		 return hight;		
	 }
	 
	 public int getWidth(){
		 return width;		
	 }
	 
	 public void printYourSelf(){
		 System.out.println("r:: width:" + width + " hight:" + hight + " x:" + xcenter + " y:" + ycenter + " orientation:" + orientation);
	 }
}

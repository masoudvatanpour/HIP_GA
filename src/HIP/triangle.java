package HIP;

public class triangle extends shape{
	int radius;
	int tetha;
	int tetha0;
	
	public triangle(int radius, int tetha, int tetha0){
		 this.name = "t";
		 this.radius = radius;
		 this.tetha = tetha;
		 this.tetha0 = 360-tetha0;
		 
		 int xs1, ys1, xs2, ys2;		 
		 xs1 = (int)(radius * (Math.cos(((2*Math.PI)/360) * (-tetha/2))));
	     ys1 = (int)(radius * (Math.sin(((2*Math.PI)/360) * (-tetha/2))));	     
	     xs2 = (int)(radius * (Math.cos(((2*Math.PI)/360) * (tetha/2))));
	     ys2 = (int)(radius * (Math.sin(((2*Math.PI)/360) * (tetha/2))));
	     	   
    	 poly.addPoint(xs1, ys1);
    	 poly.addPoint(xs2, ys2);
		 poly.addPoint(0, 0);
		 
		 setCenterToRandomLocation();
		 applyRotation(tetha0);		 

	 }
	 
	 public void printYourSelf(){
		 System.out.println("t:: radius:"+ radius + " x:" + xcenter + " y:" + ycenter + " tetha0:" + tetha0);
	 }
}

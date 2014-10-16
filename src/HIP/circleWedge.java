package HIP;

public class circleWedge extends shape{	
	 int radius;
	 int tetha;
	 int tetha0;
	 
	 public circleWedge(int radius, int tetha, int tetha0, int resolution){		 
		 this.name = "cw";
		 this.radius = radius;
		 this.tetha = tetha;
		 this.tetha0 = tetha0;
		 
		 int xs, ys;
//		 tetha0 = tetha0 + (int)(Math.random()*(360/angleStep))*angleStep;				
		 for(int i = (int)((-tetha/2)*resolution/360.0); i <= (+tetha/2)*resolution/360.0; i++){
			 xs = (int)(radius * (Math.cos(((2*Math.PI)/360) * (i*(360.0/resolution)))));
		     ys = -(int)(radius * (Math.sin(((2*Math.PI)/360) * (i*(360.0/resolution)))));
//		     System.out.println(i * 360.0/resolution);
		     poly.addPoint(xs, ys);
		 }		     
		 poly.addPoint(0, 0);
		 
		 setCenterToRandomLocation();
		 applyRotation(tetha0);		
	 }
	 
	 public int getRadius(){
		 return radius;
	 }
	 
	 public void printYourSelf(){
		 System.out.println("cw:: radius:"+ radius + " x:" + xcenter + " y:" + ycenter + " tetha:" + tetha + " tetha0:" + tetha0);
	 }
}
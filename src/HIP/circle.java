package HIP;

public class circle extends shape{
	int radius;
	public circle(int radius, int resolution){		 
		this.name = "c";
		this.radius = radius;
		
		int xs, ys;      
		for(int i = 0; i < resolution; i++){
			xs = (int)(radius * (Math.cos(((2*Math.PI)/360) * (i*(360.0/resolution)))));
		    ys = (int)(radius * (Math.sin(((2*Math.PI)/360) * (i*(360.0/resolution)))));
		    poly.addPoint(xs, ys);
		}
		setCenterToRandomLocation();		
	 }
	 
	 public int getRadius(){
		 return radius;
	 }
	 
	 public void printYourSelf(){
		 System.out.println("c:: radius:"+ radius + " x:" + xcenter + " y:" + ycenter);
	 }
}
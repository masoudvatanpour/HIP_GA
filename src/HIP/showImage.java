package HIP;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class showImage {

    private JFrame mainMap;
    private ArrayList<shape> shapes;
    public BufferedImage image;

    public showImage() {
		  mainMap = new JFrame();
//		  mainMap.setResizable(false);
		  mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  shapes = new ArrayList<shape>();
		  
		  try {
				image = ImageIO.read(new File(global.pictureName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		 
    }
    
    public void initComponentsFromSensorSet(sensorSet s) {       
		for (int i = 0; i < s.set.size(); i++) {
			shapes.add(s.set.get(i));			
		}
		
        JPanel p = new JPanel() {        	 
        	protected void paintComponent(Graphics g) {
        	   super.paintComponent(g);
        	   g.drawImage(image, 0, 0, null); 
               g.setColor(Color.BLUE);
               for (int i = 0; i < shapes.size(); i++) {            	   
            	   g.drawPolygon(shapes.get(i).poly);
               }               
           }
        	
    	   public Dimension getPreferredSize() {
               return new Dimension(image.getWidth(), image.getHeight());
           }
        };
        mainMap.add(p);
        mainMap.pack();
        mainMap.setVisible(true);
    }         

    public void initComponentsFromDNA(DNA dna) {       
		for (int i = 0; i < dna.krom.size(); i++) {
			shapes.add(dna.krom.get(i));			
		}
		
        JPanel p = new JPanel() {        	 
        	protected void paintComponent(Graphics g) {
        	   super.paintComponent(g);
        	   g.drawImage(image, 0, 0, null); 
               g.setColor(Color.BLUE);
               for (int i = 0; i < shapes.size(); i++) {            	   
            	   g.drawPolygon(shapes.get(i).poly);
               }               
           }
        	
    	   public Dimension getPreferredSize() {
               return new Dimension(image.getWidth(), image.getHeight());
           }
        };
        mainMap.add(p);
        mainMap.pack();
        mainMap.setVisible(true);
    }         
}
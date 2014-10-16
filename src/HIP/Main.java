package HIP;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main implements Runnable {
	
	int[][] room = new int[global.hight][global.width];
	boolean [][]roomCovered = new boolean[global.hight][global.width];
	    
	//Parameters to set ---------------------------------------------------------
	int m/*Andaje jameiat*/, n/*tedade sensor ha*/, u/*tedade childha*/;	
	int iterationCount;    
    double crossOverRate;
    double mutationRate;
    
    DNA bestDNA;    
    double posTot;
    
    long startTime;
    long after1Iter;	
    long endTime;
    
    int mainNumber;
    int expNumber;
    
    public Main(int m, int n, int u, int itr, double p_c, double p_m, String FileName, int mainNumber, int expNumber){
		this.m = m;
		this.n = n;
		this.u = u/2;
		this.iterationCount = itr;
		this.crossOverRate = p_c;
		this.mutationRate = p_m;
		this.mainNumber = mainNumber;
		this.expNumber = expNumber;
	    readImage();

	}
    
    public void writeResults(double accuracyW, double durationW, int iterationW, int finalLenW, int mainNumberW){
    	FileWriter fstream;
		try {
			fstream = new FileWriter("Results.txt",true);
			BufferedWriter fbw = new BufferedWriter(fstream);
	        if(true/*finalLenW == 6 /*any condition*/){
	        	System.out.println(accuracyW);
	        	fbw.write("" + mainNumberW + ", " + ", "+accuracyW + ", " + durationW + ", " + iterationW + ", " + finalLenW + "\n");
	        }else{
	        	fbw.write("\n"); 
	        }
	        fbw.close();    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    public void readImage()
    {    	
    	try {
            BufferedReader in = new BufferedReader(new FileReader(global.filename));
            String str;
            int row = 0;
            str = in.readLine();
            while ((str = in.readLine()) != null) {
                String[] strArray = str.split(",");
                int[] intArray = new int[strArray.length];
                for(int i = 0; i < strArray.length; i++) {
                    intArray[i] = Integer.parseInt(strArray[i]);
                }           
                for (int i = 0; i < intArray.length; i++) {
					room[row][i] = intArray[i];
//					room[row][i] *= 1; 
				}
                row ++;               
            }
            in.close();
        } catch (IOException e) {
            System.err.println("File Read Error");
            System.exit(0);
        }
    	    	
		
		sensorSet s = new sensorSet();
//
//		s.set.get(0).setCenterToLocation(357,249);
//		s.set.get(1).setCenterToLocation(243,217);
//		s.set.get(2).setCenterToLocation(369,321);
//		s.set.get(3).setCenterToLocation(199,443);
//		s.set.get(4).setCenterToLocation(131,175);
//		s.set.get(5).setCenterToLocation(411,459);
//		s.set.get(6).setCenterToLocation(443,101);
//		s.set.get(7).setCenterToLocation(219,151);//(279,171); original 8     new: (219,151)
//		s.set.get(8).setCenterToLocation(301,375);
//		s.set.get(9).setCenterToLocation(447,291);
//		s.set.get(10).setCenterToLocation(85,433);
//		s.set.get(11).setCenterToLocation(193,270);		
//		s.set.get(12).setCenterToLocation(309,191);	// new	
//		s.set.get(13).setCenterToLocation(291,295); // new
//		
//		s.set.get(0).applyRotation(-80); // living room nearest to kitchen *  DONE
//		s.set.get(1).applyRotation(-8.44); // centre most *DONE
//		s.set.get(2).applyRotation(1.09); // above table top *DONE
//		s.set.get(3).applyRotation(-68.5); // bedroom near door *DONE
//		s.set.get(4).applyRotation(-86.44); // living room near window *DONE 
//		s.set.get(5).applyRotation(5.1); // bathroom *DONE
//		s.set.get(6).applyRotation(-32.25); // enterance *DONE
//		s.set.get(7).applyRotation(-7.44); // living room above 2 tables *DONE
//		s.set.get(8).applyRotation(-82.55); // Hallway between bed and bathroom *DONE
//		s.set.get(9).applyRotation(-44.39); // kitchen center *DONE
//		s.set.get(10).applyRotation(104); // bedroom near window *DONE
//		s.set.get(11).applyRotation(9); //living room above sofa *DONE		
//		s.set.get(12).applyRotation(-1.105); // living room nearest to enterance *DONE
//		s.set.get(13).applyRotation(95.55); // near panel *DONE
//
//		
		//old - before measuring orientation 
//		s.set.get(0).applyRotation(90);
//		s.set.get(1).applyRotation(0);
//		s.set.get(2).applyRotation(0);
//		s.set.get(3).applyRotation(90);
//		s.set.get(4).applyRotation(90);
//		s.set.get(5).applyRotation(0);
//		s.set.get(6).applyRotation(90);
//		s.set.get(7).applyRotation(0);
//		s.set.get(8).applyRotation(90);
//		s.set.get(9).applyRotation(0);
//		s.set.get(10).applyRotation(90);
//		s.set.get(11).applyRotation(0);		
//		s.set.get(12).applyRotation(0);
//		s.set.get(13).applyRotation(90);
//		
		
		posTot = 0;
		int metric1=0;
		int metric2=0;
		int p1=0, p2=0;
    	for (int i = 0; i < global.hight; i++) {
			for (int j = 0; j < global.width; j++) {
				
				//metric 1
				if(room[i][j]>=0){
					int sensorTriggers = 0;
					for (int k = 0; k < s.set.size(); k++) {														
						if(s.set.get(k).poly.contains(i,j)){
							sensorTriggers ++;
						}						
					}
					if(room[i][j]>sensorTriggers){
						metric1 += (sensorTriggers - room[i][j]);
						p1++;
					}
				}
				
				//metric 2
				if(room[i][j]>0){
					boolean isCovered = false;
					for (int k = 0; k < s.set.size(); k++) {														
						if(s.set.get(k).poly.contains(i,j)){
							isCovered = true; 
						}						
					}
					if(!isCovered){
						metric2 -= room[i][j];
						p2++;
					}
				}							
				
				//posTot
				if(room[i][j]>0){
					posTot += room[i][j];
				}
			}
    	}
//    	System.out.println("Coverage difference: " + metric1 + " from " + p1 + " points");
//    	System.out.println("Uncovered weight: " + metric2 + " from " + p2 + " points");    	
    	System.out.println("Heat to cover: " + posTot);
//    	
//    	showImage sI = new showImage();
//		sI.initComponentsFromSensorSet(s);			
    }

    public void run(){
        //start the clock!
        startTime = System.nanoTime();
        DNA pop[]=new DNA[m];//jamiat avalie
        for (int i=0;i<m;i++){
            pop[i]=new DNA(n);
            pop[i].setChoosed(false);
        }
        int iteration=0;
        int countP=0;
    	int convergedCount = 0;
    	int averageCostOfPop = 0;
    	double bestAcc = 0;
    	int bestLen = 0;
    	
        while(iteration < iterationCount){	
        	// a termination check:
        	if(convergedCount > iterationCount/3){
//        		System.out.println("One third of total iterations reached");
//        		break;
        	}
        	// a termination check:
        	if(bestAcc >= 0.7626 && bestLen <= 9){
//        		System.err.println("Desired cost of eqquipment and accuracy reached");
        		//break;
        	}
        	
            for(int i=0;i<m;i++){//entekhab valedein baraye baztarkibi
            	double randNum = Math.random();     
            	if(randNum < crossOverRate){ 
                    pop[i].setChoosed(true);
                    countP++;                
                }
            }
            DNA child1[]=new DNA[u];
            DNA child2[]=new DNA[u];
            for(int i=0;i<u;i++){//tolide farzandan
                if(countP >= 2){
	                int p1=(int)(Math.random()*countP),p2;//pedare aval
	                DNA parent1,parent2;
	                do{
	                    p2=(int)(Math.random()*countP);//pedare dovom
	                }while(p1==p2);
	                int temp=0;
	                while(p1!=0){//entekhab p1omin pedare shayeste
	                    if(pop[temp].isChoosed==true) p1--;
	                    temp++;
	                }
	                parent1=new DNA(pop[temp]);
	                temp=0;
	                while(p2!=0){//entekhab p2omin pedare shayeste
	                    if(pop[temp].isChoosed==true) p2--;
	                    temp++;
	                }
	                parent2= new DNA(pop[temp]);               
	                
	                int pointer1, pointer2, newSize1, newSize2;	                
	                do{
//	                	if(parent1.length() == global.maxNumberOfSensors && parent2.length() == global.maxNumberOfSensors){
//	                		newSize1 = global.maxNumberOfSensors;
//	 	 	                newSize2 = global.maxNumberOfSensors;
//	                	}
	                	pointer1=(int)(Math.random()*parent1.length());//pedare aval
	 	                pointer2=(int)(Math.random()*parent2.length());//pedare dovom
	 	                newSize1 = pointer1 + parent2.length() - pointer2; 
	 	                newSize2 = pointer2 + parent1.length() - pointer1;	 	
//	 	                System.out.println("Newsizes: " + newSize1 + " " + newSize2);
	                }while(newSize1>global.maxNumberOfSensors || newSize2>global.maxNumberOfSensors);
// 	                System.out.println("Newsizes made");

	                int genChoose1[]=new int[newSize1];
	                int genChoose2[]=new int[newSize2];
	                child1[i]=new DNA(newSize1);
	                child2[i]=new DNA(newSize2);
	                
	                for(int j=0;j<newSize1;j++)
	                    if(j < pointer1 )
	                        genChoose1[j]=0;//get from parent1
	                    else
	                        genChoose1[j]=1;//get from parent2	                
	
	                for(int j=0;j<newSize2;j++)
	                    if(j < pointer2 )
	                        genChoose2[j]=1;//get from parent2
	                    else
	                        genChoose2[j]=0;//get from parent1	                	               	                	            
	                
	                int parentIterator1 = 0;
	                int parentIterator2 = 0;
	                for(int j=0;j<newSize1;j++)
	                    if(genChoose1[j]==0){
	                    	child1[i].krom.add(parent1.krom.get(j));
	                    	parentIterator1 ++;
	                    }else{
	                    	break;
	                    }	                    	                	 
	                
	                for(int j=0;j<newSize2;j++)
	                    if(genChoose2[j]==1){
	                    	child2[i].krom.add(parent2.krom.get(j));
	                    	parentIterator2 ++;
	                    }else{
	                    	break;
	                    }	                    	                
	                
	                for(int j=parentIterator2;j<parent2.kromSize;j++)	                    
                    	child1[i].krom.add(parent2.krom.get(j));
	                	 
	                for(int j=parentIterator1;j<parent1.kromSize;j++)	                    
                    	child2[i].krom.add(parent1.krom.get(j));
	                	                	                        
                }else{
                	 child1[i]=new DNA(n);
 	                 child2[i]=new DNA(n);
                }
                
                //Mutation         	
                double isMutate1=Math.random();
                if(isMutate1<mutationRate){   
                	int whichChange = (int)(Math.random()*2);
                	int place=(int)(Math.random()*child1[i].krom.size());
                	if(whichChange == 0){ // change LOCATION of one of the sensors in the dna, only   
                		child1[i].krom.get(place).setCenterToRandomLocation();                		
                	}else{// change SHAPE of one of the sensors in the dna, only
                		child1[i].mutateToAnotherShape(place);
                	}
                }
                double isMutate2=Math.random();
                if(isMutate2<mutationRate){   
                	int whichChange = (int)(Math.random()*2);
                	int place=(int)(Math.random()*child2[i].krom.size());
                	if(whichChange == 0){ // change LOCATION of one of the sensors in the dna, only                 	                   
                		child2[i].krom.get(place).setCenterToRandomLocation();                		
                	}else{// change SHAPE of one of the sensors in the dna, only                		
                		child2[i].mutateToAnotherShape(place);                	
                	}
                }        
            }
            
            //entekhab bazmandegan
            double allCost[]=new double[m+2*u];
            DNA allPop[]=new DNA[m+2*u];
            for(int i=0;i<m;i++){
                allCost[i]=cost(pop[i]);                
                allPop[i]=pop[i];
            }
            for(int i=m;i<m+u;i++){
                allCost[i]=cost(child1[i-m]);
                allPop[i]=child1[i-m];
            }
            for(int i=m+u;i<m+2*u;i++){
                allCost[i]=cost(child2[i-m-u]);
                allPop[i]=child2[i-m-u];
            }
            
            double allCostSorted [] = new double[m+2*u];
            DNA allPopSorted [] = new DNA[m+2*u];
            
            for (int i = 0; i < allCostSorted.length; i++) {
				double max = Integer.MIN_VALUE;
				int index = 0;
				for (int j = 0; j < allCost.length; j++) {
					if(allCost[j] > max){
						max = allCost[j];
						index = j;
					}										
				}
				allCostSorted[i] = max;
				allPopSorted[i] = allPop[index];
				allCost[index] = Integer.MIN_VALUE;
			}    
//            for (int i = 0; i < allPopSorted.length; i++) {
//				allPopSorted[i].print();
//	            System.out.println("----------");
//
//			}
//            System.err.println("----------");
            bestDNA = allPopSorted[0];
            bestLen = bestDNA.krom.size();            
            for(int i=0;i<m;i++){
                pop[i]=allPopSorted[i];
                pop[i].setChoosed(false);
            }
            
            countP=0;                             
            long now = System.nanoTime();
    	    long fromStart = now - startTime;
    	    fromStart /= 1000000;
    	     
    	    if(iteration % 10 == 0){
    	    	bestAcc = acc(bestDNA);
    	    	bestAcc = bestAcc/posTot;    	    	
    	    	System.out.println(iteration + "- Acc: " + (float)bestAcc + " best Cost: " + allCostSorted[0] + " at time: " + fromStart + " and length: " + bestLen );
    	    }    	        	 
    	    if(bestAcc >= 1){
            	break;
            }
            
            iteration++;            
        }// End of while
        
        //stop the clock!
		endTime = System.nanoTime();
	    long duration = endTime - startTime;
	    long sec = duration/(int)(Math.pow(10, 9));
	    long mili = duration/(int)(Math.pow(10, 6))%1000;
	    double dur = duration / Math.pow(10, 9);
		writeResults(bestAcc*100, dur, iteration, bestLen, mainNumber);
		System.out.println("\nBest DNA: "); bestDNA.print();
		saveImage();			
    }
    
    public double acc(DNA dna){    	
    	int roomCopy[][] = new int[global.hight][global.width];
    	for (int j = 0; j < global.hight; j++) {
			for (int j2 = 0; j2 < global.width; j2++) {
				roomCopy[j][j2] = room[j][j2];
				roomCovered[j][j2] = false;
			}
		}    	
    	for (int i = 0; i < dna.length(); i++) {
    		Rectangle r = dna.krom.get(i).poly.getBounds();    		
    		for (int j = (int)r.getMinY(); j < r.getMaxY(); j++) {
    			for (int k = (int)r.getMinX(); k < r.getMaxX(); k++) { 
    	    		if(j >=0 && k >=0 && j < global.hight && k < global.width){
    					if(dna.krom.get(i).poly.contains(k,j)){
    						if(roomCopy[j][k] > 0){
    							roomCopy[j][k] -= 1;
    							roomCovered[j][k] = true;
    						}
    					}
    	    		}
				}	
    		}						
    	}
    	double acc = 0;
    	for (int j = 0; j < global.hight; j++) {
			for (int j2 = 0; j2 < global.width; j2++) {	
				if(roomCovered[j][j2] && room[j][j2]>0){	
					acc += room[j][j2];
				}
			}
		}    	
    	return acc;
    }

    public double cost(DNA dna){
    	double cost=0;
       	int roomCopy[][] = new int [global.hight][global.width];
    	for (int j = 0; j < global.hight; j++) {
			for (int j2 = 0; j2 < global.width; j2++) {
				roomCopy[j][j2] = room[j][j2];
			}
		}
    	for (int i = 0; i < dna.length(); i++) {
    		Rectangle r = dna.krom.get(i).poly.getBounds();    		    		
    		for (int j = (int)r.getMinY(); j < r.getMaxY(); j++) {
    			for (int k = (int)r.getMinX(); k < r.getMaxX(); k++) { 
    	    		if(j >=0 && k >=0 && j < global.hight && k < global.width){
    					if(dna.krom.get(i).poly.contains(k,j)){
    						cost += roomCopy[j][k];								
    						if(roomCopy[j][k]>0){
    							roomCopy[j][k] -= 1;	        	    			        			       							
    						}    						
    					}
    	    		}
    			}
    		}						
    	}
    	// cost # 1:
//        cost -= Math.pow(dna.length(),3)*lenghtCoeff;
    	
    	//cost # 2:
//    	cost = Math.pow(cost, 1/10);
//    	int areaCost = 50//percent
        cost = cost - 0.5*dna.areaCost() ;//- Math.pow(dna.length(),1)/global.maxNumberOfSensors;
        
        //cost # 3
//    	cost -= 0.5*dna.areaCost();
        return cost;
    }
    
    public void saveImage(){    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	showImage sI = new showImage();
            	sI.initComponentsFromDNA(bestDNA);
            }
        });
    }
}


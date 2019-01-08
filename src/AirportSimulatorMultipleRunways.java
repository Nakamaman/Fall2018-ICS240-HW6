/**
 * @author Ethan Xiong
 * Class: ICS 240
 * Professor Thanaa Ghanem
 * Date: 11/16/18
 *
 *AirportSimulatorMultipleRunways.java
 *This java file tests multiple run ways. Used to see how multiple run ways can affect the end results
 *of planes landed, planes that took off, and the average wait times for landing/takeoff. 
 */


import java.util.LinkedList;
import java.util.Queue;

public class AirportSimulatorMultipleRunways {
	
	
	public static void main(String[] args){
		

		
		int landingTime = 2;
		int takeoffTime = 3;
		//randomize landingProb between 0 and 50%
		//double landingProb = (Math.random() * 49 + 1) / 100;
		double landingProb = 0.9;
		//randomize takeoffProb between 0 and 50%
		//double takeoffProb = (Math.random() * 49 + 1) / 100;
		double takeoffProb = 0.8;
		int totalTime = 600;
		
		System.out.println("Wait time for landing "+ landingTime);
		System.out.println("Wait time for takeoff "+ takeoffTime);
		System.out.println("Arrival probability "+landingProb);
		System.out.println("Takeoff probability "+takeoffProb);
		System.out.println("Total time "+ totalTime);
		System.out.println("==============");
		
		Simulate(landingTime, takeoffTime, landingProb, takeoffProb, totalTime);
	}
	
	
	public static void Simulate(int landingTime, int takeoffTime, double landingProb, 
										double takeoffProb, int totalTime){
		
		Queue<Integer> landingTimes = new LinkedList<Integer>();
		Queue<Integer> takeoffTimes = new LinkedList<Integer>();
		BooleanSource landing = new BooleanSource(landingProb);
		BooleanSource takeoff = new BooleanSource(takeoffProb);
	//	Runway runway = new Runway(landingTime, takeoffTime);
	//	int numRunWays = (int) (Math.random() * 5 + 1);
	//	Runway[] runwayArr = new Runway[numRunWays]; 
		Runway[] runwayArr = new Runway[5]; 
	//	System.out.println("Number of Runways: "+ (numRunWays)); 
	//	System.out.println("==============");
		for (int i=0 ; i < runwayArr.length; i++)  
			runwayArr[i] = new Runway(landingTime,takeoffTime); 
		Averager landingWaitTimes = new Averager();
		Averager takeoffWaitTimes = new Averager();
		int currentMin;
		int next;
		
		for (currentMin = 0; currentMin < totalTime; currentMin++){
			
			//Check if Plane is landing
			if (landing.query())
				landingTimes.add(currentMin);
			
			//Check if Plane is taking off
			if (takeoff.query())
				takeoffTimes.add(currentMin);
			
			//2.subtract one minute from the remaining time in the current cycle
			//runway.reduceRemainingTime();
			
			for (int i=0; i < runwayArr.length; i++)   
				runwayArr[i].reduceRemainingTime(); 
			
			//3. Check if we can have a plane land or takeoff
			for (int i=0; i < runwayArr.length; i++)   {
				
			if ((!runwayArr[i].isBusy()) && (!landingTimes.isEmpty())){
				next = landingTimes.remove();
				landingWaitTimes.addNumber(currentMin - next);
				runwayArr[i].startService(1);
			}
			
			if ((!runwayArr[i].isBusy()) && (!takeoffTimes.isEmpty())){
				next = takeoffTimes.remove();
				takeoffWaitTimes.addNumber(currentMin - next);
				runwayArr[i].startService(2);
			}
			
		}
	}
		
		//Write summary information about the simulation
		System.out.println("Planes landed "+landingWaitTimes.howManyNumbers());
		System.out.println("Average wait "+landingWaitTimes.average());
		System.out.println("Landing planes not serviced "+landingTimes.size());
		System.out.println("==============");
		System.out.println("Planes that tookoff "+takeoffWaitTimes.howManyNumbers());
		System.out.println("Average wait "+takeoffWaitTimes.average());
		System.out.println("Takeoff planes not serviced "+takeoffTimes.size());
	}

}

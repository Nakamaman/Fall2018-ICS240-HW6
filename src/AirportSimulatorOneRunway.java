/**
 * @author Ethan Xiong
 * Class: ICS 240
 * Professor Thanaa Ghanem
 * Date: 11/16/18
 *
 *AirportSimulatorOneRunways.java
 *This java file tests one run way. There is code in this program that utilizes a random number generator
 *to differentiate the probability of landing planes and take off planes and in turn let us see how that effects
 *the ending numbers for planes that landed, planes that took off, and plane landing/take off waiting times changed.
 */

import java.util.LinkedList;
import java.util.Queue;

public class AirportSimulatorOneRunway {
	
	
	public static void main(String[] args){
		

		
		int landingTime = 2;
		int takeoffTime = 3;
		//randomize landingProb between 0 and 50%
		//double landingProb = (Math.random() * 49 + 1) / 100;
		double landingProb = 0.3;
		//randomize takeoffProb between 0 and 50%
		double takeoffProb = (Math.random() * 49 + 1) / 100;
		//double takeoffProb = 0.2;
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
		Runway runway = new Runway(landingTime, takeoffTime);
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
			runway.reduceRemainingTime();
			
			//3. Check if we can have a plane land or takeoff
			if ((!runway.isBusy()) && (!landingTimes.isEmpty())){
				next = landingTimes.remove();
				landingWaitTimes.addNumber(currentMin - next);
				runway.startService(1);
			}
			
			if ((!runway.isBusy()) && (!takeoffTimes.isEmpty())){
				next = takeoffTimes.remove();
				takeoffWaitTimes.addNumber(currentMin - next);
				runway.startService(2);
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

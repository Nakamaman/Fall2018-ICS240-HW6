/**
 * @author Ethan Xiong
 * Class: ICS 240
 * Professor Thanaa Ghanem
 * Date: 11/16/18
 *
 *Runway.java
 *This java file initializes and creates methods for plane runways. This class can be used for landings and takeoffs. 
 *The biggest draw to this program is the startService, where landings and takeoffs are separated.
 */

public class Runway {
	
	private int minutesForLanding; //minutes until landing time
	private int minutesForTakeoff; //minutes until take off time
	private int runwayTimeLeft; //seconds until the runway is no longer busy
	
	/**
	 * Initializes a runway for airplanes
	 * @param s number of seconds for one wash cycle
	 */
	public Runway(int l, int t){
		this.minutesForLanding = l;
		this.minutesForTakeoff = t;
		this.runwayTimeLeft = 0;
	}
	/**
	 * determines whether the runway is currently busy
	 * @return true if the runway is busy otherwise return false
	 */
	public boolean isBusy(){
		return (this.runwayTimeLeft > 0);
	}
	/**
	 * reduce the remaining time in the runway by 1 minute
	 */
	public void reduceRemainingTime(){
		if (this.runwayTimeLeft > 0)
			this.runwayTimeLeft--;
	}
	/**
	 * start Service for landing or takeoff planes
	 * 1 = landing
	 * 2 = takeoff
	 */
	
	public void startService(int input){
		if (this.runwayTimeLeft > 0)
			throw new IllegalStateException("Runway is already busy");
		if(input == 1)
			this.runwayTimeLeft = this.minutesForLanding;
		if(input == 2)
			this.runwayTimeLeft = this.minutesForTakeoff;
	}

}

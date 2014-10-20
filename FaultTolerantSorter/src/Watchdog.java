import java.util.TimerTask;

/*
 * Created on Aug 6, 2004
 * Watchdog timer
 * From sample code on Watchdog Timer by Dr. Scott Dick
 * Retrieved Oct. 15, 2014
 */

public class Watchdog extends TimerTask {

Thread watched;

	public Watchdog (Thread target){
		// Constructor sets the class variable 'watched'
		watched = target;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("deprecation")
	public void run() {
		watched.stop();
	}

}

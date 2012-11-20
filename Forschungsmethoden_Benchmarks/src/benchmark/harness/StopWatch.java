package benchmark.harness;

/**
 * Basic interface for a stop watch.
 * 
 * @author fleckb
 *
 */
public abstract class StopWatch {
	
	private static float ONE_MILLION = 1000000;
	
	public abstract void start();
	
	/**
	 * @return Time since start() in nanoseconds.
	 */
	public abstract long split();
	
	/**
	 * Resets the stop watch.
	 * @return Time since start() in nanoseconds.
	 */
	public abstract long stop();
	
	public abstract StopWatch copy();
	
	
	public static float nanoToMilliseconds(long nanoseconds) {
		return (float)nanoseconds / ONE_MILLION;
	}
}

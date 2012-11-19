package benchmark.harness;

/**
 * Basic interface for a stop watch.
 * 
 * @author fleckb
 *
 */
public interface StopWatch {
	
	public void start();
	
	/**
	 * 
	 * @return Time since start() in nanoseconds.
	 */
	public long split();
	
	/**
	 * Resets the stop watch.
	 * @return Time since start() in nanoseconds.
	 */
	public long stop();

}

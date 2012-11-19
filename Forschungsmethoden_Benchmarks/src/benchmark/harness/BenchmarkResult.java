package benchmark.harness;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the results from one benchmark run. 
 * 
 * @author fleckb
 *
 */
public class BenchmarkResult {
	
	/**
	 * Elapsed time in milliseconds.
	 */
	public float elapsedTime = -1;
	
	/**
	 * True if the search string was found.
	 */
	public boolean found = false;
	
	/**
	 * Time to first hit in milliseconds.
	 */
	public float timeToFirstHit = -1;
	
	/**
	 * Number of search hits.
	 */
	public int numberOfHits = -1;
	
	/**
	 * The elapsed time for each search hit.
	 */
	public List<Float> hitTimes = new ArrayList<Float>();

	/**
	 * The CPU and memory usage.
	 */
	public ResourceUsage resourceUsage;
	
	@Override
	public String toString() {		
		String returnVal = "elapsed time: " + String.valueOf(elapsedTime) + 
				"ms, " + (found ? "search string found" : "search string not found");
		
		if(found) {
			returnVal += ", time to first hit: " + String.valueOf(timeToFirstHit) + "ms";
			returnVal += ", hit times for " + numberOfHits + " hit(s): ";
			for(Float time : hitTimes) {
				returnVal += time.toString() + "ms, ";
			}
		}
		
		return returnVal;
	}
}

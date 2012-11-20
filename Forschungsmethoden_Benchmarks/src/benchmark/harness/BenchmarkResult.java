package benchmark.harness;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
	
	/**
	 * The used algorithm
	 */
	public String algorithm;
	
	@Override
    public String toString() {    
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
		
        String result = "Benchmark run with algorithm: " + algorithm + "\n" + 
                (found ? "The search string was found " + numberOfHits + " times." : 
                    "The search string was not found.");
        
        result += "\nOverview\nelapsed time (ms);average cpu load (%);peak memory used (MB);time to first hit (ms)\n";
        result += nf.format(elapsedTime) + ";" + nf.format(resourceUsage.averageCpuUsage) + ";" + nf.format(resourceUsage.peakMemoryUsed) + 
                ";" + (found ? nf.format(timeToFirstHit) : "-");
        
        result += "\nHit times (ms)" + (hitTimes.size()==0 ? "\nno hits" : "");
        for(Float time : hitTimes) {
            result += "\n" + nf.format(time);
        }
        
        result += "\nResource Usage during execution at given times\ntime (ms);cpu load (%);memory usage (MB)\n" + 
                (resourceUsage.measurements.size()==0 ? "no measurement data" : "");
        for(MeasureResult measure : resourceUsage.measurements) {
            result += nf.format(measure.time) + ";" + nf.format(measure.cpuLoad) + ";" + nf.format(measure.memoryUsage);
        }
        
        return result;
	}
}

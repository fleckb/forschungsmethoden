package benchmark.harness;

import java.util.List;

public class ResourceUsage {

	/**
	 * Average CPU usage in %
	 */
	public float averageCpuUsage = 0;
	
	/**
	 * Peak memory used in MB
	 */
	public float peakMemoryUsed = 0;
	
	/**
	 * List of all the measurement samples during
	 * resource monitoring:
	 *  - time
	 *  - cpu
	 *  - memory
	 */
	public List<MeasureResult> measurements;
	
	 @Override
	 public String toString() {
		 String result = "average CPU usage: " + averageCpuUsage + 
				 ", peak memory usage: " + peakMemoryUsed;
		 
		 for(MeasureResult measurement : measurements) {
			 result += "\n" + measurement.toString();
		 }
		 
		 return result;
	 }

}

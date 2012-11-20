package benchmark.harness;

public class MeasureResult {
	
	public final float time;
	public final float cpuLoad;
	public final float memoryUsage;
	
	public MeasureResult(float time, float cpuLoad, float memoryUsage) {
		this.time = time;
		this.cpuLoad = cpuLoad;
		this.memoryUsage = memoryUsage;
	}
	
	@Override
	public String toString() {
		return "Time: " + time + ", Load: " + cpuLoad + ", Memory: " + memoryUsage;
	}
	
}

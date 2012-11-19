package benchmark.harness;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadMXBean;

/**
 * Monitors the resource usage during a benchmark run.
 * 
 * @author fleckb
 */
public class ResourceMonitor {
	
	private final StopWatch watch = new DefaultSystemStopWatch();
	
	//Bean for CPU and memory benchmarks
	private final ThreadMXBean threadBean;
	//private final MemoryMXBean memoryBean;
		
	private long startCpuTime;
	private long stopCpuTime;		
	//private volatile boolean stop;
		
	/**
	 * TODO:
	 * @param sampleInterval Sample interval in milliseconds.
	 * Zero means no sampling: Just take the average in the end.
	 */
	public ResourceMonitor(){
		threadBean = ManagementFactory.getThreadMXBean();
	}
	
	public void start() {
		for(MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
			memoryPool.resetPeakUsage();
		}		
		startCpuTime = threadBean.getCurrentThreadCpuTime();
		watch.start();
	}
	
	public ResourceUsage stop() {
		stopCpuTime = threadBean.getCurrentThreadCpuTime();
		long duration = watch.stop();
		long cpuTime = stopCpuTime - startCpuTime;
		
		float peakMemoryUsed = 0;
		for(MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
			peakMemoryUsed += byteToMb(memoryPool.getPeakUsage().getUsed());
		}
		
		ResourceUsage usage = new ResourceUsage();
		usage.averageCpuUsage = (float)cpuTime / (float)duration;
		usage.peakMemoryUsed = peakMemoryUsed;
		return usage;
	}
	
	private float byteToMb(long bytes) {
		return (float)bytes / (float)(1024 * 1024);
	}
}

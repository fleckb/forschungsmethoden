package benchmark.harness;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Monitors the resource usage during a benchmark run.
 * 
 * @author fleckb
 */
public class ResourceMonitor {
	
	private final StopWatch watch = new DefaultSystemStopWatch();
	
	//Bean for CPU benchmark
	private final ThreadMXBean threadBean;
	
	private final ScheduledExecutorService executor;
	private MonitorThread monitor;
	private ScheduledFuture<?> monitorHandle;
		
	private long startCpuTime;
	private long stopCpuTime;		
	private long sampleInterval;
		
	/**
	 * @param interval Sample interval in milliseconds.
	 * Zero means no sampling: Just take the average in the end.
	 */
	public ResourceMonitor(long interval) {
		threadBean = ManagementFactory.getThreadMXBean();
		executor = Executors.newScheduledThreadPool(1);
		sampleInterval = interval;
	}
	
	public void start() {
		for(MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
			memoryPool.resetPeakUsage();
		}		
		startCpuTime = threadBean.getCurrentThreadCpuTime();
		watch.start();
		
		monitor = new MonitorThread(Thread.currentThread().getId());
		monitorHandle = executor.scheduleAtFixedRate(
				monitor,
				sampleInterval, 
				sampleInterval,
				TimeUnit.MILLISECONDS);
	}
	
	public ResourceUsage stop() {
		monitorHandle.cancel(true);
		
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
		usage.measurements = monitor.getMeasurements();
		return usage;
	}
	
	private float byteToMb(long bytes) {
		return (float)bytes / (float)(1024 * 1024);
	}
	
	private class MonitorThread implements Runnable {
		
		private List<MeasureResult> measurements = new CopyOnWriteArrayList<MeasureResult>();
		private long lastCpuTime = startCpuTime;
		private long lastRunTime = watch.split();
		private final long threadId;
		
		public MonitorThread(long threadId) {
			this.threadId = threadId;
		}
				
		@Override
		public void run() {
			
			float memoryUsage = 0;
			for(MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
				memoryUsage += byteToMb(memoryPool.getUsage().getUsed());
			}
			
			long cpuTime = threadBean.getThreadCpuTime(threadId);
			long now = watch.split();
			long iterationCpuTime = cpuTime - lastCpuTime;
			long iterationDuration = now - lastRunTime;
			
			lastRunTime = now;
			lastCpuTime = cpuTime;
			
			float cpuLoad =  (float)iterationCpuTime / (float)iterationDuration;
			if(cpuLoad > 1) {
				cpuLoad = 1;
			}
			
			MeasureResult result = new MeasureResult(
					StopWatch.nanoToMilliseconds(now), 
					cpuLoad, 
					memoryUsage);
			measurements.add(result);	
		}
		
		protected List<MeasureResult> getMeasurements() {
			return measurements;
		}
		
	}
}

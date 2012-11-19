package benchmark.harness;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;

import benchmark.reporter.CsvReporter;
import benchmark.reporter.Reporter;


/**
 * Monitors the resource usage during a benchmark run.
 * 
 * @author fleckb
 *
 */
public class ResourceMonitor implements Runnable{
	
	//Bean for CPU Benchmarks
	ThreadMXBean threadBean = null;
	MemoryMXBean memoryBean = null;
	
	Reporter reporter = null;
	
	private volatile boolean stop;
	private int intervall = 0;
	
	public ResourceMonitor(){}
	
	public ResourceMonitor(String string, int intervall){

		memoryBean =  ManagementFactory.getMemoryMXBean();
		threadBean = ManagementFactory.getThreadMXBean();
		
		reporter = new CsvReporter(string+"CpuMem");
		this.intervall = intervall;
		
	}

	public void stop(){
		this.stop = true;
	}
		
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(!stop){
			logValues(System.currentTimeMillis(),
					threadBean.getCurrentThreadCpuTime(),
					memoryBean.getHeapMemoryUsage().getUsed());
			try {
				Thread.currentThread().sleep(intervall);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
			
	}
		
	public void logValues(long time, long cpu, long mem){
		/*
		 * TODO
		reporter.report(new String(String.valueOf(time)+";"+
						String.valueOf(cpu)+";"+
						String.valueOf(mem)+";"));
						*/
	}
}

package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;

import benchmark.CsvReporter;
import benchmark.interfaces.Reporter;


//Class that does all the logging of cpu time and used memory for a benchmark.algorithms.Finder
public class LogThread implements Runnable{
	
	//Bean for CPU Benchmarks
	ThreadMXBean threadBean = null;
	MemoryMXBean memoryBean = null;
	
	Reporter reporter = null;
	
	private volatile boolean stop;
	private int intervall = 0;
	
	public LogThread(){}
	
	public LogThread(String string, int intervall){

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
		reporter.report(new String(String.valueOf(time)+";"+
						String.valueOf(cpu)+";"+
						String.valueOf(mem)+";"));
	}
}

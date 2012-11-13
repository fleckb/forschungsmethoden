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


//Class that does all the logging for a search algorithm class
public class LogThread implements Runnable{
	
	String algo = "";
	SearchAlgorithm algoImpl = null;
	
	//Bean for CPU Benchmarks
	ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
	MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
	
	File file;
	OutputStream out;

	private volatile boolean stop;
	private int intervall = 0;
	
	public LogThread(){
		super();
		init();
	}
	
	public LogThread(String string, int intervall, SearchAlgorithm algoImpl){
		super();
		algo = string;
		file = new File(algo+"_bench.csv");
		this.intervall = intervall;
		this.algoImpl = algoImpl;
		
		init();
	}

	public void stop(){
		this.stop = true;
	}
	
	private void init(){
		try {	
			if(!file.exists())
				file.createNewFile();
			
			out = new FileOutputStream(file);
			out.write(("CPUTime Benchmarks " +  algo).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		//do some logging
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
		
		logFinal();	
	}
	
	public void logFinal(){
		HashMap<String,String> map = this.algoImpl.getFinal();
		
		//log final values, close outputstream
	}
	
	public void logValues(long time, long cpu, long mem){
		try {
			out.write(
					new String(String.valueOf(time)+";"+
							String.valueOf(cpu)+";"+
							String.valueOf(mem)+";").getBytes());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

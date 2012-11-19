package test.benchmark;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import benchmark.harness.ResourceMonitor;
import benchmark.harness.ResourceUsage;

public class ResourceMonitorTest {
	
	private static int MEMORY_USAGE_IN_MB = 50;
	private static int MEMORY_USAGE_IN_BYTE = MEMORY_USAGE_IN_MB * 1024 * 1024;
	
	private final ResourceMonitor monitor = new ResourceMonitor();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAverageCpuUsage() {
		
		monitor.start();
		generateLoad(100);
		ResourceUsage result = monitor.stop(); 
		assertTrue("Average CPU usage greater than 10% ", result.averageCpuUsage > 0.9);
	}
	
	@Test
	public void testPeakMemoryUsage() {
		monitor.start();
		//generateMemoryUsage(MEMORY_USAGE_10_MB_IN_BYTES);
		generateMemoryUsage(MEMORY_USAGE_IN_BYTE);
		ResourceUsage result = monitor.stop();
		System.out.println(result.peakMemoryUsed);
		assertTrue("Peak memory used greater than 9MB ", result.peakMemoryUsed > MEMORY_USAGE_IN_MB);
	}
	
	private void generateMemoryUsage(int bytes) {
		// Allocate some memory
		byte[] buffer = new byte[bytes];
		for(int i=0; i<bytes; i++) {
			buffer[i] = 0x00;
		}
	}

	/**
	 * Generates CPU load.
	 * @param duration Time span in milliseconds to generate load.
	 */
	private static void generateLoad(int duration) {
	    long sleepTime = duration*1000000L;
	    long startTime = System.nanoTime();
	    while ((System.nanoTime() - startTime) < sleepTime) {}
	}

}

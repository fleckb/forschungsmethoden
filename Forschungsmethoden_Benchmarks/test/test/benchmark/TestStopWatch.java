package test.benchmark;

import static org.junit.Assert.*;
import org.junit.Test;

import benchmark.DefaultSystemStopWatch;
import benchmark.StopWatch;

public class TestStopWatch {

	@Test
	public void testBasicStopWatchUsage() throws Exception {
		StopWatch watch = new DefaultSystemStopWatch();
		
		watch.start();
		Thread.sleep(10);
		long split = watch.split();
		Thread.sleep(10);
		long stop = watch.stop();
		
		assertTrue("Split time greater zero", split > 0);
		assertTrue("Stop time greater split time", stop > split);
	}

}

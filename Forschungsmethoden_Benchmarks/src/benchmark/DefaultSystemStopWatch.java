package benchmark;

import benchmark.interfaces.StopWatch;

public class DefaultSystemStopWatch implements StopWatch {
	
	private long startTime;
	
	public DefaultSystemStopWatch() {
		startTime = -1;
	}

	@Override
	public void start() {
		startTime = System.nanoTime();
	}

	@Override
	public long split() {
		return System.nanoTime() - startTime;
	}

	@Override
	public long stop() {
		startTime = -1;
		return System.nanoTime() - startTime;
	}

}

package benchmark.harness;


public class DefaultSystemStopWatch extends StopWatch {
	
	private long startTime;
	
	public DefaultSystemStopWatch() {
		startTime = -1;
	}
	
	protected DefaultSystemStopWatch(long startTime) {
		this.startTime = startTime;
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
		long oldStartTime = startTime;
		startTime = -1;
		return System.nanoTime() - oldStartTime;
	}

	@Override
	public StopWatch copy() {
		return new DefaultSystemStopWatch(startTime);
	}

}

package benchmark.harness;

import java.io.InputStream;

import benchmark.algorithms.Finder;
import benchmark.algorithms.FinderResult;
import benchmark.algorithms.FinderStatusListener;
import benchmark.algorithms.Position;
import benchmark.reporter.Reporter;

/**
 * Central class to run benchmarks.
 * 
 * @author fleckb
 *
 */
public class Benchmarker implements FinderStatusListener {
	
	private final Finder algorithm;
	private final StopWatch watch;
	private final ResourceMonitor monitor;

	private InputStream inputText;
	private String searchString;
	private BenchmarkResult benchmarkResult;
	
	public Benchmarker(Finder algorithm) {
		this.algorithm = algorithm;
		this.algorithm.setStatusListener(this);
		this.watch = new DefaultSystemStopWatch();
		this.monitor = new ResourceMonitor(100);
	}

	public void run() {
		
		watch.start();
		monitor.start();
		
		FinderResult result = algorithm.find(inputText, searchString);

		// Stop resource monitoring
		benchmarkResult.resourceUsage = monitor.stop();
		// Stop the watch
		// time of the whole search
		benchmarkResult.elapsedTime = StopWatch.nanoToMilliseconds(watch.stop());
		// found true/false
		benchmarkResult.found = result.isFound();
		// number of hits
		benchmarkResult.numberOfHits = result.numberOfHits;
	}

	public void prepare(InputStream inputText, String searchString) {
		this.inputText = inputText;
		this.searchString = searchString;
		this.benchmarkResult = new BenchmarkResult();
	}

	public void report(Reporter reporter) {
		reporter.report(benchmarkResult);
	}

	@Override
	public void progressUpdate(long bytesRead) {
		// TODO Auto-generated method stub
	}

	@Override
	public void searchStringFound(Position position) {
		if(!benchmarkResult.found) {
			benchmarkResult.found = true;
			benchmarkResult.timeToFirstHit = StopWatch.nanoToMilliseconds(watch.split());
			benchmarkResult.hitTimes.add(benchmarkResult.timeToFirstHit);
		} else {
			benchmarkResult.hitTimes.add(StopWatch.nanoToMilliseconds(watch.split()));
		}
	}

}

package benchmark.harness;

import java.io.InputStream;

import benchmark.algorithms.Finder;
import benchmark.algorithms.FinderResult;
import benchmark.algorithms.FinderStatusListener;
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

	private InputStream inputText;
	private String searchString;
	private int iterations;
	private BenchmarkResult[] benchmarkResult;
	
	public Benchmarker(Finder algorithm) {
		this.algorithm = algorithm;
		this.algorithm.setStatusListener(this);
		this.watch = new DefaultSystemStopWatch();
	}

	public void run() {
		// TODO 
		//
		// time to first hit
		// hits with time of each hit
		// number of hits
		
		for(int i=0; i<iterations; i++) {
			benchmarkResult[i] = new BenchmarkResult();
			
			watch.start();
			FinderResult result = algorithm.find(inputText, searchString);
			
			// time of the whole search
			benchmarkResult[i].elapsedTime = watch.stop();
			// found true/false
			benchmarkResult[i].found = result.isFound();
		}
	}

	public void prepare(InputStream inputText, String searchString,
			int iterations) {
		this.inputText = inputText;
		this.searchString = searchString;
		this.iterations = iterations;
		this.benchmarkResult = new BenchmarkResult[iterations];
	}

	public void report(Reporter reporter) {
		BenchmarkResult result = ResultAccumulator.accumulate(benchmarkResult);
		reporter.report(result);
	}

	@Override
	public void searchStringFound(int line, int column) {
	}
	@Override
	public void searchStringFound(int position) {
		
	}

	@Override
	public void progressUpdate(float percentage) {
		// TODO Auto-generated method stub
		
	}

}

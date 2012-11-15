package benchmark;

import java.io.InputStream;
import java.io.OutputStream;

import main.LogThread;

import benchmark.algorithms.FinderResult;
import benchmark.algorithms.interfaces.Finder;
import benchmark.algorithms.interfaces.FinderStatusListener;
import benchmark.interfaces.Reporter;
import benchmark.interfaces.StopWatch;

public class Benchmarker implements FinderStatusListener {
	
	private final Finder algorithm;
	private final Reporter reporter;
	private final StopWatch watch;

	private InputStream inputText;
	private String searchString;
	private int iterations;
	private BenchmarkResult[] benchmarkResult;
	
	public Benchmarker(Finder algorithm, Reporter reporter) {
		this.algorithm = algorithm;
		this.reporter = reporter;
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
			benchmarkResult[i].found = result.found;
		}

		reporter.shutdown();
	}

	public void prepare(InputStream inputText, String searchString,
			int iterations) {
		this.inputText = inputText;
		this.searchString = searchString;
		this.iterations = iterations;
		this.benchmarkResult = new BenchmarkResult[iterations];
	}

	public void report(OutputStream output) {
		BenchmarkResult result = ResultAccumulator.accumulate(benchmarkResult);
		reporter.report(result, output);
	}

	@Override
	public void searchStringFound(int line, int column) {
	}

	@Override
	public void progressUpdate(float percentage) {
		// TODO Auto-generated method stub
		
	}

}

package test.benchmark;

import java.io.OutputStream;

import benchmark.BenchmarkResult;
import benchmark.Reporter;

/**
 * A reporter for testing purposes only.
 * 
 * @author fleckb
 *
 */
public class TestReporter implements Reporter {
	
	public BenchmarkResult result;

	@Override
	public void report(BenchmarkResult result, OutputStream outputStream) {
		// Don't write to any output stream, just get the results.
		this.result = result;
	}

}

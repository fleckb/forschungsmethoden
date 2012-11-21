package test.benchmark.reporter;

import benchmark.harness.BenchmarkResult;
import benchmark.reporter.Reporter;

/**
 * A reporter for testing purposes only.
 * 
 * @author fleckb
 *
 */
public class TestReporter implements Reporter {
	
	public BenchmarkResult result;

	@Override
	public void report(BenchmarkResult... results) {
		// Don't write to any output stream, just get the results.
		this.result = results[0];
	}
}

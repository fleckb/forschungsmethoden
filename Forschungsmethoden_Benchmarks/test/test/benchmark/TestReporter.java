package test.benchmark;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import benchmark.BenchmarkResult;
import benchmark.interfaces.Reporter;

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

	@Override
	public void report(BenchmarkResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void report(BenchmarkResult result, OutputStreamWriter out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void report(String values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}

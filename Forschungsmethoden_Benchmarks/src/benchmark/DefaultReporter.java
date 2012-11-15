package benchmark;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import benchmark.interfaces.Reporter;

/**
 * Default Reporter, outputs to System.out
 * */
public class DefaultReporter implements Reporter {

	private OutputStreamWriter out = null;

	public DefaultReporter(){
		out = new OutputStreamWriter(System.out);
	}
		
	@Override
	public void report(BenchmarkResult result) {
		try {
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void report(BenchmarkResult result, OutputStream outputStream) {}

	@Override
	public void report(BenchmarkResult result, OutputStreamWriter out) {}

	@Override
	public void report(String values) {
		try {
			out.write(values);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
	}

}

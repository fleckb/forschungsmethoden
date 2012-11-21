package benchmark.reporter;

import java.io.IOException;
import java.io.OutputStreamWriter;

import benchmark.harness.BenchmarkResult;

/**
 * Default Reporter, outputs to System.out
 * */
public class DefaultReporter implements Reporter {

	private OutputStreamWriter out = null;

	public DefaultReporter(){
		out = new OutputStreamWriter(System.out);
	}
		
	@Override
	public void report(BenchmarkResult... results) {
		try {
			out.write(BenchmarkResult.accumulate(results));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

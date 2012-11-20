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
	public void report(BenchmarkResult result) {
		try {
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

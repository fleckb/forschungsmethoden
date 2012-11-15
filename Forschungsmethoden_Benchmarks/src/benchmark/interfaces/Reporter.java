package benchmark.interfaces;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import benchmark.BenchmarkResult;

public interface Reporter {
	
	public void report(BenchmarkResult result, OutputStream outputStream);

	public void report(BenchmarkResult result, OutputStreamWriter out);
	
	void report(BenchmarkResult result);

	void report(String values);

	void shutdown();

}

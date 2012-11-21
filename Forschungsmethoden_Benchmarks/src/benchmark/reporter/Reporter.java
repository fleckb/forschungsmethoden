package benchmark.reporter;

import benchmark.harness.BenchmarkResult;

public interface Reporter {
	
	public void report(BenchmarkResult... result);

}

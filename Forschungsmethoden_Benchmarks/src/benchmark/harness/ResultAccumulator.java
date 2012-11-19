package benchmark.harness;

public class ResultAccumulator {
	
	public static BenchmarkResult accumulate(BenchmarkResult... results) {
		
		BenchmarkResult accumulatedResult = new BenchmarkResult();
		
		for(BenchmarkResult result : results) {
			accumulatedResult.elapsedTime += result.elapsedTime;
			
			// TODO: better idea?
			accumulatedResult.found |= result.found;
		}
		accumulatedResult.elapsedTime /= results.length;
		
		return accumulatedResult;
	}

}

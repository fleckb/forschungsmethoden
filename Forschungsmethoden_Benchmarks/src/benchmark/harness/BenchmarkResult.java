package benchmark.harness;

public class BenchmarkResult {
	
	public long elapsedTime = 0;
	public boolean found = false;
	
	@Override
	public String toString() {
		return String.valueOf(elapsedTime) + ";" + String.valueOf(found) + ";";
	}
	

}

package benchmark;

import java.io.OutputStream;

public interface Reporter {
	
	public void report(BenchmarkResult result, OutputStream outputStream);

}

package benchmark.reporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import benchmark.harness.BenchmarkResult;

public class CsvReporter implements Reporter {

	private OutputStreamWriter out = null;
	
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd_HHmmss");
	Date date = new Date();
		
	public CsvReporter(String filename) {
			
		try {
			out = new FileWriter(filename+"_results_" + sdf.format(date) + ".csv");
		} catch (IOException e) {
			e.printStackTrace();
		}	
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

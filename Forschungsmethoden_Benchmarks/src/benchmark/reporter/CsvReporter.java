package benchmark.reporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

import benchmark.harness.BenchmarkResult;

public class CsvReporter implements Reporter {

	private OutputStreamWriter out = null;
	
	DateFormat df = DateFormat.getTimeInstance();
	Date date = new Date();
	
	public CsvReporter(){}
	
	public CsvReporter(OutputStreamWriter out, String filename){
		this.out = out;
	}
	
	public CsvReporter(String filename){
			
		try {
			out = new FileWriter(filename+"_results_" + df.format(date).toString() + ".csv");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void report(BenchmarkResult result) {	
		try {
			out.write(df.format(System.currentTimeMillis()) + "#" + result.toString() + "#");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

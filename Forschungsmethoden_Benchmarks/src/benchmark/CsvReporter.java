package benchmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

import benchmark.interfaces.Reporter;

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
	public void report(BenchmarkResult result, OutputStreamWriter outputStream) {}

	@Override
	public void report(BenchmarkResult result) {	
		try {
			out.write(df.format(System.currentTimeMillis()) + "#" + result.toString() + "#");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void report(String values) {	
		try {
			out.write(df.format(System.currentTimeMillis()) + "#" + values + "#");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown(){
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void report(BenchmarkResult result, OutputStream outputStream) {}

}

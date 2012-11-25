package benchmark;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import benchmark.algorithms.Finder;
import benchmark.algorithms.KnuthMorrisPrattFinder;
import benchmark.algorithms.NaiveFinder;
import benchmark.algorithms.RabinKarpFinder;
import benchmark.harness.BenchmarkResult;
import benchmark.harness.Benchmarker;
import benchmark.reporter.CsvReporter;
import benchmark.reporter.DefaultReporter;
import benchmark.reporter.Reporter;

/***
 * Testerklasse die zum Benchmarken aufgerufen wird
 * @param String Path
 * @param String Search string
 */
public class Tester {
	
	private Tester() {}
	
	/**
	 * benchmark.Tester algorithm path-to-input-file search-string iterations [output-filename]
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length < 4) {
			printUsage();
		}
		
		int iterations = 0;
		try {
			iterations = Integer.parseInt(args[3]);
		} catch(Exception ex) {
			System.err.print(ex.getMessage());
			System.exit(1);		
		}
		
		Finder algorithm = selectAlgorithm(args[0]);
		
		if(algorithm==null) {
			printUsage();
		}
		
		Reporter reporter;
		File inputFile = new File(args[1]);
		String searchString = args[2];
				
		Benchmarker benchmarker = new Benchmarker(algorithm);
		
		List<BenchmarkResult> results = new ArrayList<BenchmarkResult>();
		
		// TODO
		if(args.length == 5) {
			reporter = new CsvReporter(args[4]);
		} else {
			reporter = new DefaultReporter();
		}
		
		try {
			for(int i=0; i<iterations; i++) {
				InputStream inputStream = new FileInputStream(inputFile);
				benchmarker.prepare(inputStream, searchString);
				results.add(benchmarker.run());
				inputStream.close();
			}
			reporter.report(results.toArray(new BenchmarkResult[0]));
		} catch(Exception ex) {
			System.err.print(ex.getMessage());
		}
		System.exit(0);
	}
	
	private static void printUsage() {
		System.err.println("Usage: benchmark.Tester algorithm path-to-input-file search-string iterations [output-basename]");
		System.err.println("algorithm - one of:" + 
				"\n   naive (Naive Search)" +
				"\n   kmp (Knuth-Morris-Pratt" +
				"\n   rk (Rabin-Karp" +
				"\n   bm (Boyer-Moore\n");
		System.exit(1);		
	}

	private static Finder selectAlgorithm(String finderString) {		
		if(finderString.equalsIgnoreCase("naive")){
			return new NaiveFinder();
		} else if(finderString.equalsIgnoreCase("kmp")){
			return new KnuthMorrisPrattFinder();
		} else if(finderString.equalsIgnoreCase("rk")){
			return new RabinKarpFinder();
		} else if(finderString.equalsIgnoreCase("bm")){
			return new RabinKarpFinder();
		} 
		return null;
	}
}

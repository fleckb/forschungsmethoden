package test.benchmark;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import benchmark.algorithms.BoyerMooreFinder;
import benchmark.algorithms.Finder;
import benchmark.algorithms.KnuthMorrisPrattFinder;
import benchmark.algorithms.NaiveFinder;
import benchmark.algorithms.RabinKarpFinder;
import benchmark.harness.BenchmarkResult;
import benchmark.harness.Benchmarker;
import benchmark.reporter.CsvReporter;
import benchmark.reporter.Reporter;

public class Tester {
		
	public final String[] fileNames = {"small.txt", "medium.txt", "large.txt", "huge.txt"};
	public final String[] searchStrings = {"Registerkarte", "Registerkarte", "Registerkarte", "Registerkarte"};
	public final int[] iterations = {100, 100, 10, 10};
	public final String[] type = {"small", "medium", "large", "huge"};
	public final Finder[] finders = {new NaiveFinder(),
			                         new BoyerMooreFinder(),
			                         new KnuthMorrisPrattFinder(),
			                         new RabinKarpFinder()};
	
	@Test
	public void executeAllBenchmarks() {
		
		for(Finder finder : finders) {
			for(int i=0; i<fileNames.length; i++) {
				benchmarkAndReport(aBenchmarkerWithA(finder),
						iterations[i],
						fileNames[i],
						searchStrings[i],
						finder.getAlgorithmName() + "_" + type[i]);
			}
		}
	}
	
	@Test
	public void executeNaiveBenchmarks() {
		Finder finder = new NaiveFinder();
		for(int i=0; i<fileNames.length; i++) {
			benchmarkAndReport(aBenchmarkerWithA(finder),
					iterations[i],
					fileNames[i],
					searchStrings[i],
					finder.getAlgorithmName() + "_" + type[i]);
		}
	}
	
	@Test
	public void executeBoyerMooreBenchmarks() {
		Finder finder = new BoyerMooreFinder();
		for(int i=0; i<fileNames.length; i++) {
			benchmarkAndReport(aBenchmarkerWithA(finder),
					iterations[i],
					fileNames[i],
					searchStrings[i],
					finder.getAlgorithmName() + "_" + type[i]);
		}
	}
	
	@Test
	public void executeKnuthMorrisPrattBenchmarks() {
		Finder finder = new KnuthMorrisPrattFinder();
		for(int i=0; i<fileNames.length; i++) {
			benchmarkAndReport(aBenchmarkerWithA(finder),
					iterations[i],
					fileNames[i],
					searchStrings[i],
					finder.getAlgorithmName() + "_" + type[i]);
		}
	}
	
	@Test
	public void executeRabinKarpBenchmarks() {
		Finder finder = new RabinKarpFinder();
		for(int i=0; i<fileNames.length; i++) {
			benchmarkAndReport(aBenchmarkerWithA(finder),
					iterations[i],
					fileNames[i],
					searchStrings[i],
					finder.getAlgorithmName() + "_" + type[i]);
		}
	}
	
	@SuppressWarnings("static-access")
	private void benchmarkAndReport(Benchmarker benchmark, int iterations, String file, String search, String report) {
		List<BenchmarkResult> results = new ArrayList<BenchmarkResult>();
		Reporter reporter = new CsvReporter(report);
		
		for(int i=0; i<iterations; i++) {
			InputStream textFile = this.getClass().getClassLoader().getSystemResourceAsStream(file);
			
			benchmark.prepare(textFile, search);
			results.add(benchmark.run());
		}
		
		reporter.report(results.toArray(new BenchmarkResult[0]));
	}
	
	private Benchmarker aBenchmarkerWithA(Finder finder) {
		return new Benchmarker(finder);
	}
}

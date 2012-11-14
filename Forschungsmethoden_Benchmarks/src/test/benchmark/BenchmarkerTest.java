package test.benchmark;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import benchmark.Benchmarker;
import benchmark.algorithms.Finder;
import benchmark.algorithms.NaiveFinder;


public class BenchmarkerTest {
	
	private InputStream inputText = null;
	private OutputStream output = null;
	private String searchString = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNaiveSearchBenchmarker() {
		int iterations = 1;
		
		Finder naiveFinder = new NaiveFinder();
		Benchmarker benchmark = new Benchmarker(naiveFinder);
		
		// Setup the benchmark
		benchmark.prepare(inputText, searchString, iterations);
		
		// Run the benchmark
		benchmark.run();
		
		// Get the results
		benchmark.report(output);
		
		fail("Not yet implemented");
	}

}

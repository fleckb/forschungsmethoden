package test.benchmark;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import benchmark.Benchmarker;
import benchmark.algorithms.Finder;
import benchmark.algorithms.NaiveFinder;


public class BenchmarkerTest {
	
	private final String text = "This is a test for a naive search!\nForm follows function.";
	private final String searchString = "for";
	
	private OutputStream output = null;


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
	public void testNaiveSearchBenchmarker() throws Exception {
		int iterations = 1;
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		TestReporter reporter = new TestReporter();
		
		Finder naiveFinder = new NaiveFinder();
		Benchmarker benchmark = new Benchmarker(naiveFinder, reporter);
		
		// Setup the benchmark
		benchmark.prepare(inputText, searchString, iterations);
		
		// Run the benchmark
		benchmark.run();
		
		// Get the results
		benchmark.report(output);
		
		assertEquals("Search string found", true, reporter.result.found);
		assertTrue("Elapsed time greater than zero", reporter.result.elapsedTime>0);
	}

}

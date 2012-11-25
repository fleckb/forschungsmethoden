package test.benchmark;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.benchmark.reporter.TestReporter;

import benchmark.algorithms.Finder;
import benchmark.algorithms.NaiveFinder;
import benchmark.harness.Benchmarker;
import benchmark.reporter.CsvReporter;
import benchmark.reporter.DefaultReporter;

public class BenchmarkerTest {
	
	private final String text = "This is a test for a naive search!\nForm follows function.";
	private final String searchString = "for";
	
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
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		TestReporter reporter = new TestReporter();
		
		Finder naiveFinder = new NaiveFinder();
		Benchmarker benchmark = new Benchmarker(naiveFinder);
		
		// Setup the benchmark
		benchmark.prepare(inputText, searchString);
		
		// Run the benchmark
		benchmark.run();
		
		// Get the results
		benchmark.report(reporter);
		
		assertEquals("Search string found", true, reporter.result.found);
		assertTrue("Elapsed time greater than zero", reporter.result.elapsedTime > 0);
	}
	
	@Test
	public void testNaiveSearchWithTimeToFirstHit() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		TestReporter reporter = new TestReporter();
		
		Finder naiveFinder = new NaiveFinder();
		Benchmarker benchmark = new Benchmarker(naiveFinder);
		
		// Setup the benchmark
		benchmark.prepare(inputText, searchString);
		
		// Run the benchmark
		benchmark.run();
		
		// Get the results
		benchmark.report(reporter);
		
		assertTrue("Time to first hit greater than zero", reporter.result.timeToFirstHit > 0);
		
	}
	
	@Test
	public void testBenchmarkerWithDefaultReporter() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		Finder finder= new NaiveFinder();
		Benchmarker benchmark = new Benchmarker(finder);
		
		benchmark.prepare(inputText, searchString);
		benchmark.run();
		benchmark.report(new DefaultReporter());
	}
	
	@Test
	public void testBenchmarkerWithCsvReporter() throws Exception {
		
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		Finder finder= new NaiveFinder();
		Benchmarker benchmark = new Benchmarker(finder);
		
		benchmark.prepare(inputText, searchString);
		benchmark.run();
		benchmark.report(new CsvReporter("testrun"));
	}
}

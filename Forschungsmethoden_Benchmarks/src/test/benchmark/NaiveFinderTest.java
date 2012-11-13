package test.benchmark;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import benchmark.algorithms.Finder;
import benchmark.algorithms.FinderResult;
import benchmark.algorithms.NaiveFinder;

public class NaiveFinderTest {
	
	private Finder finder;
	
	private final String text = "This is a test for a naive search!\nForm follows function.";
	private final String searchString = "for";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		finder = new NaiveFinder();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimpleSearch() throws Exception {
		
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		FinderResult result = finder.find(inputText, searchString);
		
		assertEquals("Search string found", true, result.found);
		
	}

}

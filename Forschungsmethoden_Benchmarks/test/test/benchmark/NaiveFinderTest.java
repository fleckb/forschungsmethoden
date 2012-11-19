package test.benchmark;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import benchmark.algorithms.Finder;
import benchmark.algorithms.FinderResult;
import benchmark.algorithms.FinderStatusListener;
import benchmark.algorithms.NaiveFinder;
import benchmark.algorithms.Position;

@RunWith(JMock.class) 
public class NaiveFinderTest {
	
	private Finder finder;
	
	private final String text = "This is a test for a naive search!\nForm follows function.";
	private final String searchString = "for";
	
	private final Mockery context = new Mockery();

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
	
	@Test
	public void testSearchStringFoundListener() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		final FinderStatusListener listener = context.mock(FinderStatusListener.class);
		finder.setStatusListener(listener);
		
		context.checking(new Expectations() {{
			exactly(1).of(listener).searchStringFound(with(any(Position.class)));
			ignoring(listener).progressUpdate(with(any(Long.class)));
		}});
		
		finder.find(inputText, searchString);
	}
	
	@Test public void testCorrectElapsedTime() throws Exception {
		fail("TODO");
	}
	
	@Test public void testFindCorrectNumberOfHits() throws Exception {
		fail("TODO");
	}
	
	@Test public void testCorrectHitTimesAmount() throws Exception {
		fail("TODO");
	}

}

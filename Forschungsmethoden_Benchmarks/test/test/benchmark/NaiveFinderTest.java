package test.benchmark;

import java.io.ByteArrayInputStream;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import benchmark.algorithms.Finder;
import benchmark.algorithms.FinderResult;
import benchmark.algorithms.FinderStatusListener;
import benchmark.algorithms.NaiveFinder;
import benchmark.algorithms.Position;

@RunWith(JMock.class) 
public class NaiveFinderTest {
	
	private Finder finder;
	
	private final String text = "This is a test for a simple search!\nForm follows simple function.";
	private final String search1 = "for";
	private final String search2 = "simple";
	
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
		
		FinderResult result = finder.find(inputText, search1);
		
		assertEquals("Search string found", true, result.found);
	}
	
	@Test
	public void testSearchStringFoundListener() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		final FinderStatusListener listener = context.mock(FinderStatusListener.class);
		finder.setStatusListener(listener);
		
		context.checking(new Expectations() {{
			exactly(2).of(listener).searchStringFound(with(any(Position.class)));
			ignoring(listener).progressUpdate(with(any(Long.class)));
		}});
		
		finder.find(inputText, search2);
	}
	
	@Test
	public void testCorrectHitPositions() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		FinderResult result = finder.find(inputText, search2);
		
		assertThat(result.hits.get(0), equalTo(new Position(1, 22)));
		assertThat(result.hits.get(1), equalTo(new Position(2, 14)));
	}
	
	@Test
	public void testFindCorrectNumberOfHits() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		FinderResult result = finder.find(inputText, search2);
		
		assertEquals("Number of hits", 2, result.numberOfHits);
	}
	
	@Test
	public void testCorrectSizeOfHitsList() throws Exception {
		ByteArrayInputStream inputText = new ByteArrayInputStream(text.getBytes("UTF-8"));
		
		FinderResult result = finder.find(inputText, search2);
		
		assertEquals("Size of hits list", 2, result.hits.size());
	}

}

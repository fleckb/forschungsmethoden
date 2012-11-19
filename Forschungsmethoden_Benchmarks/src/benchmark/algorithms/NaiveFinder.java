package benchmark.algorithms;

import java.io.InputStream;

import benchmark.utility.StreamsHelper;

/**
 * Naive search implementation.
 * 
 * Just uses the built in search of the String object.
 * 
 * @author fleckb
 */
public class NaiveFinder implements Finder {
	
	FinderStatusListener statusListener;

	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		FinderResult result = new FinderResult();
		
		String text = StreamsHelper.inputStreamToString(inputText);
		
		if(text.contains(searchString)) {
			result.found = true;
		}
		return result;		
	}
		
	@Override
	public void setStatusListener(FinderStatusListener listener) {
		this.statusListener = listener;		
	}
}

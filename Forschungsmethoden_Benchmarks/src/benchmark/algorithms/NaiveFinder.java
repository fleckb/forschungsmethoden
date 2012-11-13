package benchmark.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Naive search implementation.
 * 
 * Just uses the built in search of the String object.
 * 
 * @author fleckb
 */
public class NaiveFinder implements Finder {

	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		FinderResult result = new FinderResult();
		
		String text = inputStreamToString(inputText);
		
		if(text.contains(searchString)) {
			result.found = true;
		}
		return result;		
	}
	
	/**
	 * Helper method to convert an InputStream to a String.
	 * There are other (and faster) ways to do this...
	 * 
	 * @param stream InputStream to be converted.
	 * @return The InputStream converted to a String.
	 */
	private String inputStreamToString(InputStream stream) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder text = new StringBuilder();
		
		try {
			String line = reader.readLine();
			while(line != null) {
				text.append(line);
				line = reader.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return text.toString();
	}

}

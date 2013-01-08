package benchmark.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Naive search implementation.
 * 
 * Just uses the built in search of the String object.
 * 
 * @author fleckb
 */
public class NaiveFinder implements Finder {
	
	private FinderStatusListener statusListener;
	private final String charset = "UTF-8";
	
	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		FinderResult result = new FinderResult();
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(inputText, charset));
		} catch (UnsupportedEncodingException ex) {
			reader = new BufferedReader(new InputStreamReader(inputText));
			ex.printStackTrace();
		}
		
		try {
			int currentLinePosition = 1;
			long bytesRead = 0;
			int column = 0;
			int fromIndex;
			String line = reader.readLine();
			while(line != null) {
				fromIndex = 0;
				column = line.indexOf(searchString, fromIndex);
				while(column != -1) {
					Position hitPosition = new Position(currentLinePosition, column+1);
					result.found = true;
					result.hits.add(hitPosition);
					result.numberOfHits++;
					
					if(statusListener != null) {
						statusListener.searchStringFound(hitPosition);
					}
					
					fromIndex = column+1;
					column = line.indexOf(searchString, fromIndex);
				}
				if(statusListener != null) {
					bytesRead += line.getBytes(charset).length;
					statusListener.progressUpdate(bytesRead);
				}
												
				line = reader.readLine();
				currentLinePosition++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;		
	}
		
	@Override
	public void setStatusListener(FinderStatusListener listener) {
		this.statusListener = listener;		
	}

	@Override
	public String getAlgorithmName() {
		return "Java String Implementation";
	}
}

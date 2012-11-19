package benchmark.algorithms;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import benchmark.utility.StreamsHelper;


/**
 * Source: http://www.fmi.uni-sofia.bg/fmi/logic/vboutchkova/sources/KMPMatch_java.html
 * */
public class KnuthMorrisPrattFinder implements Finder {

	private String pattern = null;
	private int percentage = 0;
	private FinderStatusListener listener = null;
//	private FinderResult result = null;
	private int[] failure;
	private BufferedReader reader = null;
	private int lineNum = 0;

	public KnuthMorrisPrattFinder(){}

	public KnuthMorrisPrattFinder(String needle){
		this.pattern = needle;

		computeFailure();
	}

	public KnuthMorrisPrattFinder(InputStream stream, String needle){
		this.pattern = needle;		
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	@Override
	public FinderResult find(InputStream inputText, String searchString) {

		reader = new BufferedReader(new InputStreamReader(inputText));
		FinderResult result = new FinderResult();
		String line = "";

		try {
			while((line = reader.readLine()) != null){
				lineNum++;

				int i = this.match(line);

				if(i > 0){
					listener.searchStringFound(lineNum,i);

					result.incNumberOfHits();
					result.found |= true;
					result.addHit(new Position(lineNum,i));
				}
				else if(i == 0){
					//Input text of length 0
				}
				else if(i == -1){
					//Pattern not found
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public void setStatusListener(FinderStatusListener listener) {
		this.listener = listener;
	}


	/**************************
	 * Specific Algorithm	 * 
	 **************************/

	/**
	 * Finds the first occurrence of the pattern in the text.
	 */
	public int match(String line) {	  
		//computeFailure
		int j = 0;
		int x = 0;

		if (line.length() == 0) 
			return -1;

		for (int i = 0; i < line.length(); i++) {
			while (j > 0 && pattern.charAt(j) != line.charAt(i)) {
				j = failure[j - 1];
			}
			if (pattern.charAt(j) == line.charAt(i)) { j++; }
			if (j == pattern.length()) {
				x = i - pattern.length() + 1;
				return x;
			}
		}
		return -1;
	}

	/******************
	 * PREPROCESSING  * 
	 * ****************/

	/** 
	 * Computes the failure function using a boot-strapping process,
	 * where the pattern is matched against itself.
	 */
	private void computeFailure() {
		int j = 0;
		for (int i = 1; i < pattern.length(); i++) {
			while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) { 
				j = failure[j - 1];
			}
			if (pattern.charAt(j) == pattern.charAt(i)) { 
				j++; 
			}
			failure[i] = j;
		}
	}
}

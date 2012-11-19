package benchmark.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Source: http://www.fmi.uni-sofia.bg/fmi/logic/vboutchkova/sources/KMPMatch_java.html
 * */
public class KnuthMorrisPrattFinder implements Finder {

	private String pattern = null;
	private FinderStatusListener listener = null;
//	private FinderResult result = null;
	private int[] failure;
	private BufferedReader reader = null;
//	private int lineNum = 0;
	private final String charset = "UTF-8";

	public KnuthMorrisPrattFinder(){}

	public KnuthMorrisPrattFinder(String needle){
		this.pattern = needle;

		
	}

	public KnuthMorrisPrattFinder(InputStream stream, String needle){
		this.pattern = needle;		
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	@Override
	public FinderResult find(InputStream inputText, String searchString) {

		computeFailure();
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(inputText,charset));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		FinderResult result = new FinderResult();
		String line = "";
		int lineNum = 0;
		long progress = 0;

		try {
			while((line = reader.readLine()) != null){
				lineNum++;

				int i = this.match(line);

				if(i > 0){
					listener.searchStringFound(new Position(lineNum,i));

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
				
				if(listener != null) {
					listener.searchStringFound(new Position(lineNum,i));
					progress += line.getBytes(charset).length;
					listener.progressUpdate(progress);
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

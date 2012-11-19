package benchmark.algorithms;
import java.io.InputStream;

import benchmark.utility.StreamsHelper;


/**
 * Source: http://www.fmi.uni-sofia.bg/fmi/logic/vboutchkova/sources/KMPMatch_java.html
 * */

public class KnuthMorrisPrattFinder implements Finder {
	
	private int oldText = -1;
	private String text = null;
	private String pattern = null;
	private int iterations = 0;
	private int charTable[] = null;
	private int offsetTable[] = null;
	private int percentage = 0;
	private FinderStatusListener listener = null;
	private FinderResult result = new FinderResult();
	private int latestIndexFound = -1;
	
	private int[] failure;
	
	public KnuthMorrisPrattFinder(){}
	
	public KnuthMorrisPrattFinder(String needle, int it){
		this.iterations = it;
		this.pattern = needle;
		
		computeFailure();
	}
	
	public KnuthMorrisPrattFinder(InputStream stream, String needle, int it){
		this.iterations = it;
		this.pattern = needle;
		this.text = StreamsHelper.inputStreamToString(stream);
		this.oldText = text.length();
		
	}
	
	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		
		int i = this.match();
		
		if(i > 0){
			this.latestIndexFound = i;
			listener.searchStringFound(i);
			//if found, cut haystack, remove portion before found index
			text = text.substring(i);

			percentage = 100 - (text.length() / (oldText / 100));
			result.incNumberOfHits();
			result.found |= true;
			result.addHit(new Position(i));
		}
		else if(i == 0){
			//Input text of length 0
		}
		else if(i == -1){
			//Pattern not found
		}
		return result;
	}

	@Override
	public void setStatusListener(FinderStatusListener listener) {
		this.listener = listener;
	}
	
	private void update(){
		listener.progressUpdate(percentage);
		listener.searchStringFound(this.latestIndexFound);
	}
	
	/**************************
	 * Specific Algorithm	 * 
	 **************************/
	  
	  /**
	   * Finds the first occurrence of the pattern in the text.
	   */
	  public int match() {	  
		//computeFailure
	    int j = 0;
	    int x = 0;
	    
	    if (text.length() == 0) 
	    	return -1;
	    
	    for (int i = 0; i < text.length(); i++) {
	      while (j > 0 && pattern.charAt(j) != text.charAt(i)) {
	        j = failure[j - 1];
	      }
	      if (pattern.charAt(j) == text.charAt(i)) { j++; }
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

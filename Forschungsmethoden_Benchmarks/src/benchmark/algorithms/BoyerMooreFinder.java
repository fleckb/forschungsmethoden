package benchmark.algorithms;

import java.io.InputStream;


import benchmark.harness.ResourceMonitor;
import benchmark.utility.StreamsHelper;

/**
 * Source http://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm
 * */
public class BoyerMooreFinder implements Finder{
	
	private int oldHayStack = -1;
	private String haystack = null;
	private String needle = null;
	private int iterations = 0;
	private int charTable[] = null;
	private int offsetTable[] = null;
	private int percentage = 0;
	private FinderStatusListener listener = null;
	private FinderResult result = new FinderResult();
	private int latestIndexFound = -1;
	
	public BoyerMooreFinder(){}
	
	public BoyerMooreFinder(String needle, int it){
		this.iterations = it;
		this.needle = needle;
	}
	
	public BoyerMooreFinder(InputStream stream, String needle, int it){
		this.iterations = it;
		this.needle = needle;
		this.haystack = StreamsHelper.inputStreamToString(stream);
		this.oldHayStack = haystack.length();
		
		preprocess();
	}
			
	private void preprocess(){
	    charTable = makeCharTable();
	    offsetTable = makeOffsetTable();
	}

	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		
		//do search run
		int i = indexOf();

		if(i > 0){
			this.latestIndexFound = i;
			listener.searchStringFound(i);
			//if found, cut haystack, remove portion before found index
			haystack = haystack.substring(i);

			percentage = 100 - (haystack.length() / (oldHayStack / 100));
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
	   * Returns the index within this string of the first occurrence of the
	   * specified substring. If it is not a substring, return -1.
	   * 
	   * @param haystack The string to be scanned
	   * @param needle The target string to search
	   * @return The start index of the substring
	   */
	  public int indexOf() {
		  
		//preprocess();
	    if (needle.length() == 0) {
	      return 0;
	    }

	    for (int i = needle.length() - 1, j; i < haystack.length();) {
	      for (j = needle.length() - 1; needle.charAt(j) == haystack.charAt(i); --i, --j) {
	        if (j == 0) {
	          return i;
	        }
	      }
	      // i += needle.length - j; // For naive method
	      i += Math.max(offsetTable[needle.length() - 1 - j], charTable[haystack.charAt(i)]);
	    }
	    return -1;
	  }
	 
	  /****************************************************
	   * PREPROCESSING Methods
	   * *************************************************/
	  
	  /**
	   * Makes the jump table based on the mismatched character information.
	   */
	  private int[] makeCharTable() {
	    final int ALPHABET_SIZE = 256;
	    int[] table = new int[ALPHABET_SIZE];
	    for (int i = 0; i < table.length; ++i) {
	      table[i] = needle.length();
	    }
	    for (int i = 0; i < needle.length() - 1; ++i) {
	      table[needle.charAt(i)] = needle.length() - 1 - i;
	    }
	    return table;
	  }
	 
	  /**
	   * Makes the jump table based on the scan offset which mismatch occurs.
	   */
	  private int[] makeOffsetTable() {
	    int[] table = new int[needle.length()];
	    int lastPrefixPosition = needle.length();
	    for (int i = needle.length() - 1; i >= 0; --i) {
	      if (isPrefix(needle, i + 1)) {
	        lastPrefixPosition = i + 1;
	      }
	      table[needle.length() - 1 - i] = lastPrefixPosition - i + needle.length() - 1;
	    }
	    for (int i = 0; i < needle.length() - 1; ++i) {
	      int slen = suffixLength(needle, i);
	      table[slen] = needle.length() - 1 - i + slen;
	    }
	    return table;
	  }
	 
	  /**
	   * Is needle[p:end] a prefix of needle?
	   */
	  private boolean isPrefix(String needle, int p) {
	    for (int i = p, j = 0; i < needle.length(); ++i, ++j) {
	      if (needle.charAt(i) != needle.charAt(j)) {
	        return false;
	      }
	    }
	    return true;
	  }
	 
	  /**
	   * Returns the maximum length of the substring ends at p and is a suffix.
	   */
	  private int suffixLength(String needle, int p) {
	    int len = 0;
	    for (int i = p, j = needle.length() - 1;
	         i >= 0 && needle.charAt(i) == needle.charAt(j); --i, --j) {
	      len += 1;
	    }
	    return len;
	  }
}
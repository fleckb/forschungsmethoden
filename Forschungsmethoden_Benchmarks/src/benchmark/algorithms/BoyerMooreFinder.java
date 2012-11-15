package benchmark.algorithms;

import java.io.InputStream;

import main.LogThread;

import benchmark.algorithms.interfaces.Finder;
import benchmark.algorithms.interfaces.FinderStatusListener;

/**
 * From http://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm
 * */
public class BoyerMooreFinder implements Finder{

	private LogThread log = null;
	
	public BoyerMooreFinder(){
		this.log = new LogThread("cpuMem_BoyerMoore", 100);
	}
		
	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusListener(FinderStatusListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	  /**
	   * Returns the index within this string of the first occurrence of the
	   * specified substring. If it is not a substring, return -1.
	   * 
	   * @param haystack The string to be scanned
	   * @param needle The target string to search
	   * @return The start index of the substring
	   */
	  public static int indexOf(char[] haystack, char[] needle) {
	    if (needle.length == 0) {
	      return 0;
	    }
	    int charTable[] = makeCharTable(needle);
	    int offsetTable[] = makeOffsetTable(needle);
	    for (int i = needle.length - 1, j; i < haystack.length;) {
	      for (j = needle.length - 1; needle[j] == haystack[i]; --i, --j) {
	        if (j == 0) {
	          return i;
	        }
	      }
	      // i += needle.length - j; // For naive method
	      i += Math.max(offsetTable[needle.length - 1 - j], charTable[haystack[i]]);
	    }
	    return -1;
	  }
	 
	  /**
	   * Makes the jump table based on the mismatched character information.
	   */
	  private static int[] makeCharTable(char[] needle) {
	    final int ALPHABET_SIZE = 256;
	    int[] table = new int[ALPHABET_SIZE];
	    for (int i = 0; i < table.length; ++i) {
	      table[i] = needle.length;
	    }
	    for (int i = 0; i < needle.length - 1; ++i) {
	      table[needle[i]] = needle.length - 1 - i;
	    }
	    return table;
	  }
	 
	  /**
	   * Makes the jump table based on the scan offset which mismatch occurs.
	   */
	  private static int[] makeOffsetTable(char[] needle) {
	    int[] table = new int[needle.length];
	    int lastPrefixPosition = needle.length;
	    for (int i = needle.length - 1; i >= 0; --i) {
	      if (isPrefix(needle, i + 1)) {
	        lastPrefixPosition = i + 1;
	      }
	      table[needle.length - 1 - i] = lastPrefixPosition - i + needle.length - 1;
	    }
	    for (int i = 0; i < needle.length - 1; ++i) {
	      int slen = suffixLength(needle, i);
	      table[slen] = needle.length - 1 - i + slen;
	    }
	    return table;
	  }
	 
	  /**
	   * Is needle[p:end] a prefix of needle?
	   */
	  private static boolean isPrefix(char[] needle, int p) {
	    for (int i = p, j = 0; i < needle.length; ++i, ++j) {
	      if (needle[i] != needle[j]) {
	        return false;
	      }
	    }
	    return true;
	  }
	 
	  /**
	   * Returns the maximum length of the substring ends at p and is a suffix.
	   */
	  private static int suffixLength(char[] needle, int p) {
	    int len = 0;
	    for (int i = p, j = needle.length - 1;
	         i >= 0 && needle[i] == needle[j]; --i, --j) {
	      len += 1;
	    }
	    return len;
	  }
}

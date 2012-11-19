package benchmark.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Source http://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string_search_algorithm
 * */
public class BoyerMooreFinder implements Finder{

	private String needle = null;
	private int charTable[] = null;
	private int offsetTable[] = null;
	private FinderStatusListener listener = null;
//	private FinderResult result = null;
//	private int lineNum = 0;
	BufferedReader reader = null;
	private final String charset = "UTF-8";

	public BoyerMooreFinder(){}

	public BoyerMooreFinder(String needle){
		this.needle = needle;
	}

	public BoyerMooreFinder(InputStream stream, String needle, int it){
		this.needle = needle;
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	private void preprocess(){
		charTable = makeCharTable();
		offsetTable = makeOffsetTable();
	}

	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		
		int lineNum = 0;
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputText,charset));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String line = "";

		//Part of the algorithm, so has to be repeated for every find()-Call
		preprocess();

		FinderResult result = new FinderResult();

		long progress = 0;
		
		try {
			while((line = reader.readLine()) != null){
				lineNum++;

				//do search run
				int i = indexOf(line);

				if(i > 0){
					listener.searchStringFound(new Position(lineNum,i));

					result.incNumberOfHits();
					result.found |= true;
					result.addHit(new Position(lineNum,i));

				}
				else if(i == 0){
					//Input text of length 0
					return result;
				}
				else if(i == -1){
					//Pattern not found in this line
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
	 * Returns the index within this string of the first occurrence of the
	 * specified substring. If it is not a substring, return -1.
	 * 
	 * @param haystack The string to be scanned
	 * @param needle The target string to search
	 * @return The start index of the substring
	 */
	public int indexOf(String input) {

		//preprocess();
		if (needle.length() == 0) {
			return 0;
		}

		for (int i = needle.length() - 1, j; i < input.length();) {
			for (j = needle.length() - 1; needle.charAt(j) == input.charAt(i); --i, --j) {
				if (j == 0) {
					return i;
				}
			}
			// i += needle.length - j; // For naive method
			i += Math.max(offsetTable[needle.length() - 1 - j], charTable[input.charAt(i)]);
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

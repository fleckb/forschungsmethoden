package benchmark.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Source: http://algs4.cs.princeton.edu/53substring/KMP.java.html
 * */
public class KnuthMorrisPrattFinder implements Finder {

	private final int R = 256;   // the radix

	private FinderStatusListener listener = null;
	private final String charset = "UTF-8";

	public KnuthMorrisPrattFinder(){	
	}

	@Override
	public FinderResult find(InputStream inputText, String pat) {

		int[][] dfa;       // the KMP automoton

		BufferedReader reader = null;

		dfa = buildDFA(pat);

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

				int i = this.search(line, pat, dfa);

				if(i >= 0){
					Position hitPosition = new Position(lineNum,i+1);

					result.incNumberOfHits();
					result.found |= true;
					result.addHit(hitPosition);
					
					if(listener != null) {
						listener.searchStringFound(hitPosition);
					}
				}
				else if(i == -1){
					//Pattern not found
				}

				if(listener != null) {
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

	// return offset of first match; N if no match
	public int search(String txt, String pat, int[][] dfa) {

		// simulate operation of DFA on text
		int M = pat.length();
		int N = txt.length();
		int i, j;
		for (i = 0, j = 0; i < N && j < M; i++) {
			j = dfa[txt.charAt(i)][j];
		}
		if (j == M) 
			return i - M;    // found
		return -1;                    // not found
	}

	private int[][] buildDFA(String pat) {

		int[][] dfa = null;
		
		// build DFA from pattern
		int M = pat.length();
		dfa = new int[R][M]; 
		dfa[pat.charAt(0)][0] = 1; 
		for (int X = 0, j = 1; j < M; j++) {
			for (int c = 0; c < R; c++) 
				dfa[c][j] = dfa[c][X];     // Copy mismatch cases. 
			dfa[pat.charAt(j)][j] = j+1;   // Set match case. 
			X = dfa[pat.charAt(j)][X];     // Update restart state. 
		}
		return dfa;
	}

	@Override
	public String getAlgorithmName() {
		return "Knuth-Morris-Pratt";
	}

}
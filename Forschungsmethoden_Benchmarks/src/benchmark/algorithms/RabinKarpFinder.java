package benchmark.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;

/**
 * //Source: http://algs4.cs.princeton.edu/53substring/RabinKarp.java.html
 * */
public class RabinKarpFinder implements Finder {

	private long patHash;    // pattern hash value
	private int M;           // pattern length
	private long Q;          // a large prime, small enough to avoid long overflow
	private int R;           // radix
	private long RM;         // R^(M-1) % Q

	private String pattern = null;
	private FinderStatusListener listener = null;
//	private FinderResult result = null;
//	private int lineNum = 0;
	//private BufferedReader reader = null;
	private final String charset = "UTF-8";

	public RabinKarpFinder(){}

	private void preprocess(String pattern) {
		this.pattern = pattern;
		
		R = 256;
		M = pattern.length();
		Q = longRandomPrime();
		
		// precompute R^(M-1) % Q for use in removing leading digit
		RM = 1;
		for (int i = 1; i <= M-1; i++)
			RM = (R * RM) % Q;
		patHash = hash(pattern,M);
	}

	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		
		int lineNum = 0;
		long progress = 0;
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputText,charset));
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}
		String line = "";

		FinderResult result = new FinderResult();

		preprocess(searchString);	

		try {
			while((line = reader.readLine()) != null){
				lineNum++;
				int i = this.search(line);

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

	// check for exact match
	public int search(String line) {
		int N = line.length(); 
		if (N < M) 
			return -1;
		//  return N;

		long txtHash = hash(line, M); 

		// check for match at offset 0
		if ((patHash == txtHash) && check(line, 0))
			return 0;

		// check for hash match; if hash match, check for exact match
		for (int i = M; i < N; i++) {
			// Remove leading digit, add trailing digit, check for match. 
			txtHash = (txtHash + Q - RM*line.charAt(i-M) % Q) % Q; 
			txtHash = (txtHash*R + line.charAt(i)) % Q; 

			// match
			int offset = i - M + 1;
			if ((patHash == txtHash) && check(line, offset))
				return offset;
		}

		// no match
		return -1;
		//        return N;
	}

	// Compute hash for key[0..M-1]. 
	private long hash(String key, int M) { 
		long h = 0; 
		for (int j = 0; j < M; j++) 
			h = (R * h + key.charAt(j)) % Q; 
		return h; 
	} 

	// Las Vegas version: does pat[] match txt[i..i-M+1] ?
	private boolean check(String txt, int i) {
		for (int j = 0; j < M; j++) 
			if (pattern.charAt(j) != txt.charAt(i + j)) 
				return false; 
		return true;
	}

	// a random 31-bit prime
	private static long longRandomPrime() {
		BigInteger prime = new BigInteger(31, new Random());
		return prime.longValue();
	}

	@Override
	public String getAlgorithmName() {
		return "Rabin-Karp";
	}

}

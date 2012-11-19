package benchmark.algorithms;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Random;

import benchmark.utility.StreamsHelper;


/**
 * //Source: http://algs4.cs.princeton.edu/53substring/RabinKarp.java.html
 * 
 * */
public class RabinKarpFinder implements Finder {
	
    private long patHash;    // pattern hash value
    private int M;           // pattern length
    private long Q;          // a large prime, small enough to avoid long overflow
    private int R;           // radix
    private long RM;         // R^(M-1) % Q
    
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
	
	public RabinKarpFinder(){}
	
	public RabinKarpFinder(String needle, int it){
		this.iterations = it;
		this.pattern = needle;
	}
	
	public RabinKarpFinder(InputStream stream, String pat, int it){
		this.iterations = it;
		this.pattern = pat;
		this.text = StreamsHelper.inputStreamToString(stream);
		this.oldText = text.length();
		
        R = 256;
        M = pat.length();
        Q = longRandomPrime();
        
		preprocess();
	}
	
	private void preprocess(){	
	    // precompute R^(M-1) % Q for use in removing leading digit
        RM = 1;
        for (int i = 1; i <= M-1; i++)
           RM = (R * RM) % Q;
		patHash = hash(pattern,M);
	}

	@Override
	public FinderResult find(InputStream inputText, String searchString) {
		
		//do search run
		int i = 0;

		if(i >= 0){
			this.latestIndexFound = i;
			listener.searchStringFound(i);
			//if found, cut haystack, remove portion before found index
			text = text.substring(i);

			percentage = 100 - (text.length() / (oldText / 100));
			result.incNumberOfHits();
			result.found |= true;
			result.addHit(new Position(i));
			
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
 
	// check for exact match
    public int search() {
        int N = text.length(); 
        if (N < M) 
        	return -1;
//        	return N;
        
        long txtHash = hash(text, M); 

        // check for match at offset 0
        if ((patHash == txtHash) && check(text, 0))
            return 0;

        // check for hash match; if hash match, check for exact match
        for (int i = M; i < N; i++) {
            // Remove leading digit, add trailing digit, check for match. 
            txtHash = (txtHash + Q - RM*text.charAt(i-M) % Q) % Q; 
            txtHash = (txtHash*R + text.charAt(i)) % Q; 

            // match
            int offset = i - M + 1;
            if ((patHash == txtHash) && check(text, offset))
                return offset;
        }

        // no match
        return -1;
//        return N;
    }
	
//	    // check for exact match
//	    public int search(String txt) {
//	        int N = txt.length(); 
//	        if (N < M) 
//	        	return -1;
////	        	return N;
//	        
//	        long txtHash = hash(txt, M); 
//	
//	        // check for match at offset 0
//	        if ((patHash == txtHash) && check(txt, 0))
//	            return 0;
//	
//	        // check for hash match; if hash match, check for exact match
//	        for (int i = M; i < N; i++) {
//	            // Remove leading digit, add trailing digit, check for match. 
//	            txtHash = (txtHash + Q - RM*txt.charAt(i-M) % Q) % Q; 
//	            txtHash = (txtHash*R + txt.charAt(i)) % Q; 
//	
//	            // match
//	            int offset = i - M + 1;
//	            if ((patHash == txtHash) && check(txt, offset))
//	                return offset;
//	        }
//	
//	        // no match
//	        return -1;
////	        return N;
//	    }

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

}

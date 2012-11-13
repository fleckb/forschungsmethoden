package benchmark;

import java.io.InputStream;
import java.io.OutputStream;

import benchmark.algorithms.Finder;

public class Benchmarker {
	
	private final Finder algorithm; 

	private InputStream inputText;
	private String searchString;
	private int iterations;

	public Benchmarker(Finder algorithm) {
		this.algorithm = algorithm;
	}

	public void run() {
		
		for(int i=0; i<iterations; i++) {
			algorithm.find(inputText, searchString);
		}
	}

	public void prepare(InputStream inputText, String searchString,
			int iterations) {
		this.inputText = inputText;
		this.searchString = searchString;
		this.iterations = iterations;
	}

	public void report(OutputStream output) {
		// TODO Auto-generated method stub
		
	}

}

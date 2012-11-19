package benchmark;

import java.util.List;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.lang.management.*;

import javax.swing.JPanel;

import benchmark.algorithms.Finder;
import benchmark.algorithms.KnuthMorrisPrattFinder;
import benchmark.algorithms.NaiveFinder;
import benchmark.algorithms.RabinKarpFinder;
import benchmark.harness.Benchmarker;
import benchmark.reporter.CsvReporter;
import benchmark.reporter.Reporter;

/***
 * Testerklasse die zum Benchmarken aufgerufen wird
 * @param String Path
 * @param String Search string
 */
public class Tester {
	
	Logger log;
	Finder algo = null;
	String algoName = "";
	Benchmarker bench = null;
	Reporter reporter = null;
	
	public Tester(){}
	
	public Tester(String al, InputStream in, String word, int num){
		algo = selectAlgorithm(al);
		reporter = new CsvReporter(algoName);
		bench = new Benchmarker(algo);
		bench.prepare(in, word, num);
	}

	private void reInit(String al, InputStream in, String word, int num){
		algo = selectAlgorithm(al);
		reporter = new CsvReporter(algoName);
		bench = new Benchmarker(algo);
		bench.prepare(in, word, num);
	}
	
	private Finder selectAlgorithm(String s){
		Finder f = null;
		
		if(s.equalsIgnoreCase("naive")){
			f = new NaiveFinder();
			algoName = "Naive String Search";
			}
		else if(s.equalsIgnoreCase("kmp")){
			f = new KnuthMorrisPrattFinder();
			algoName = "Knut-Morris-Pratt String Search";
			}
		else if(s.equalsIgnoreCase("rk")){
			f = new RabinKarpFinder();
			algoName = "Rabin-Karp String Search";
			}
		else if(s.equalsIgnoreCase("bm")){
			f = new RabinKarpFinder();
			algoName = "Boyer-Moore String Search";
			}
		else {
			System.err.println("Algorithm can only be \"naive\", \"kmp\", \"rk\" or \"bm\".");
			System.exit(1);
		}
		
		return f;
	}
	
	private void start() {
		bench.run();
	}

	public static void main(String[] args) {
		
		File file = null;
		InputStream in = null;
		OutputStream out = null;
		
		if((args.length != 4) ||
				!(Integer.getInteger(args[3]) == null)){
			System.err.println("Usage: Tester <Algorithm> <Path-to-input-file> <Search string> <#>");
			System.exit(1);
		}
		
		try {
			file = new File(args[1]);
			
			if(!file.exists()){
				System.err.println("Inputfile not found.");
				System.exit(1);
				}
			
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Tester tester = new Tester(args[0], in, args[2], Integer.parseInt(args[3]));
		tester.start();
		
		//TODO Implement Checks etc. for several runs, with new input
	}
}

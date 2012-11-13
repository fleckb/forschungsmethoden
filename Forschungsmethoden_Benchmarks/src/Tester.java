import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import java.lang.management.*;

import javax.swing.JPanel;

/***
 * Testerklasse die zum Benchmarken aufgerufen wird
 * @param String Path
 * @param String Search string
 */
public class Tester {
	
	Logger log;
	SearchAlgorithm algo = null;	
	
	public Tester(){
		super();
	}
	
	public Tester(String al, InputStream in, String word, int num){
		super();
		doWork(al, in, word, num);
	}

	private void doWork(String al, InputStream in, String word, int num) {
		//Run selected algorithm
		if(al.equals("KMP")){
			this.algo = new KMPSearch();
			algo.doSearch(in, word, num);
		}
		else {
			
		}
	}

	public static void main(String[] args) {
		
		File file = null;
		InputStream input = null;
		
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
			
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Tester tester = new Tester(args[0], input, args[2], Integer.parseInt(args[3]));

	}
}

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
	
	public Tester(InputStream in, String word, int num){
		super();
		doWork(in, word, num);
	}

	private void doWork(InputStream in, String word, int num) {
		this.algo = new KMPSearch();
		algo.doSearch(in, word, num);
	}

	public static void main(String[] args) {
		
		File file = null;
		InputStream input;
		
		if((args.length != 3) ||
				!(Integer.getInteger(args[2]) == null)){
			System.err.println("Usage: Tester <Path-to-input-file> <Search string> <#>");
			System.exit(1);
		}
		
		try {
			file = new File(args[0]);
			
			if(!file.exists())
				System.exit(1);
			
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Tester tester = new Tester();

	}
}

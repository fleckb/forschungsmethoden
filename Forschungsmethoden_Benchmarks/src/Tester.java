import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.lang.management.*;

import javax.swing.JPanel;

/***
 * Testerklasse die zum Benchmarken aufgerufen wird
 * @param String Path
 * @param String Search string
 */
public class Tester {
	
	public static void main(String[] args) {
		
		List<String> input = null;
//		
//		if(args.length != 3){
//			System.err.println("Usage: Tester <Path-to-input-file> <Search string>");
//			return;
//			
//		}
//		
//		Path path = Paths.get(args[1]);
//		try {
//			input = Files.readAllLines(path, StandardCharsets.UTF_8);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		TesterGui gui = new TesterGui();
	}
}

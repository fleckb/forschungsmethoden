package benchmark.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamsHelper {
	
	/**
	 * Helper method to convert an InputStream to a String.
	 * There are other (and faster) ways to do this...
	 * 
	 * @param stream InputStream to be converted.
	 * @return The InputStream converted to a String.
	 */
	public static String inputStreamToString(InputStream stream) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder text = new StringBuilder();
		
		try {
			String line = reader.readLine();
			while(line != null) {
				text.append(line);
				line = reader.readLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return text.toString();
	}
}

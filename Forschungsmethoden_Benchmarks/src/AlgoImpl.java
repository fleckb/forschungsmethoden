import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;


public class AlgoImpl implements SearchAlgorithm{

	LogThread log;
	int found = 0;
	long start = 0;
	long finished = 0;
	int checked = 0;
		
	public AlgoImpl(){
		super();
	}

	@Override
	public Object doSearch(InputStream input, String searchstring, int num) {
		return null;
	}
	
	//return final values, words found, checked etc. for logging
	public HashMap<String,String> getFinal(){
		
		return null;
	}
	
}

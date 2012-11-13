import java.io.InputStream;
import java.util.HashMap;

public interface SearchAlgorithm {
	
	public Object doSearch(InputStream input, String searchstring, int num);
	
	public HashMap<String,String> getFinal();
}

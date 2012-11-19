package benchmark.algorithms;

import java.io.InputStream;


public interface Finder {
	
	public FinderResult find(InputStream inputText, String searchString);
	
	public void setStatusListener(FinderStatusListener listener);

}

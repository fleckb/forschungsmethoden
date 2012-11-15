package benchmark.algorithms.interfaces;

import java.io.InputStream;

import benchmark.algorithms.FinderResult;

public interface Finder {
	
	public FinderResult find(InputStream inputText, String searchString);
	
	public void setStatusListener(FinderStatusListener listener);

}

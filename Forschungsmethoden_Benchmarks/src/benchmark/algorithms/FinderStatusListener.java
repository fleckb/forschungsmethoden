package benchmark.algorithms;

public interface FinderStatusListener {
		
	public void searchStringFound(Position position);
	
	public void progressUpdate(long bytesRead);

}

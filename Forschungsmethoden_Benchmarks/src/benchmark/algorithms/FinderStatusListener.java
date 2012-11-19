package benchmark.algorithms;

public interface FinderStatusListener {
	
	public void searchStringFound(int line, int column);
	
	public void progressUpdate(float percentage);

	void searchStringFound(int position);

}

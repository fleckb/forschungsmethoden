package benchmark.algorithms.interfaces;

public interface FinderStatusListener {
	
	public void searchStringFound(int line, int column);
	
	public void progressUpdate(float percentage);

}

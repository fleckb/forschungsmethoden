package benchmark.algorithms;

import java.util.ArrayList;
import java.util.List;

public class FinderResult {

	public boolean found = false;
	public int numberOfHits = 0; // necessary? just hits.size() ?
	public List<Position> hits = new ArrayList<Position>();
	
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}
	public int getNumberOfHits() {
		return numberOfHits;
	}
	public void setNumberOfHits(int numberOfHits) {
		this.numberOfHits = numberOfHits;
	}
	public List<Position> getHits() {
		return hits;
	}
	public void setHits(List<Position> hits) {
		this.hits = hits;
	}
	public void incNumberOfHits() {
		this.numberOfHits++;
	}
	public void addHit(Position position) {
		this.hits.add(position);
	}
	
	
	
}

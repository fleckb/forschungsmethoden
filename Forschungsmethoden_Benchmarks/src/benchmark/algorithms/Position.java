package benchmark.algorithms;

public class Position {
	
	private int line = -1;
	private int column = -1;
	
	public Position(){}
	
	
	public Position(int line, int col){
		this.line = line;
		this.column = col;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString(){
		String ret = "";
		
		ret += (this.line != -1 && column != -1) ? "Line " + line + ", Column " + column + " ": "";
		
		return ret;
		
	}
}

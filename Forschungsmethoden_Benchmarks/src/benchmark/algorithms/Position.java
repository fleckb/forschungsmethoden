package benchmark.algorithms;

public class Position {
	
	private int line;
	private int column;
	
	public Position(){
		this.line = -1;
		this.column = -1;
	}
	
	public Position(int line, int column){
		this.line = line;
		this.column = column;
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
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Position) {
			Position position = (Position)object;
			if(position.getLine()==getLine() && position.getColumn()==getColumn()) {
				return true;
			} 
		} 
		return false;
	}
}

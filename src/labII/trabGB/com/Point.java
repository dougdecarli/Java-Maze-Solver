package labII.trabGB.com;

public class Point {
	private int row;
	private int col;
	private char symbol;
	private String status;
	
	private boolean isVisited;
	private boolean isAbandoned;
	
	public Point(int row, int col, char symbol) {
		super();
		this.row = row;
		this.col = col;
		this.symbol = symbol;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public boolean isVisited() {
		return isVisited;
	}
	
	public boolean isAbandoned() {
		return isAbandoned;
	}
	
	public void setVisited() {
		this.isVisited = true;
		status = "VISITED";
	}
	
	public void setAbandoned() {
		this.isAbandoned = true;
		status = "ABANDONED";
	}
	
	public void setValid() {
		this.symbol = '*';
	}
	
	public void setExitPoint() {
		this.status = "EXIT";
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")" + " " + getStatus();
	}
}

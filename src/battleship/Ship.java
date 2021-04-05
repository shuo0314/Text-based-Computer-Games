package battleship;

public abstract class Ship {
	
	private int bowRow;
	
	private int bowColumn;
	
	private int length;
	
	private boolean horizontal;
	
	private boolean[] hit;
	
	public Ship(int length) {
		//To do
	}
	
	public int getLength() {
		//Todo
	}
	
	public int getBowRow() {
		//Todo
	}
	
	public int getBowColumn() {
		//Todo
	}
	public boolean[] getHit() {
		//Todo
	}
	
	public boolean isHorizontal() {
		//Todo
	}
	
	public void setBowRow(int row) {
		
	}
	
	public void setBowColumn(int column) {
		
	}
	
	public void setHorizontal(boolean horizontal) {
		
	}
	
	public abstract String getShipType() {
		
	}
	
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
	}
	
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
	}
	
	boolean shootAt(int row, int column) {
		
	}
	
	boolean isSunk() {
		
	}
	
	@Override
	public String toString() {
	}

package battleship;

public class EmptySea extends Ship {
	
	final static int emptySeaLength = 1;//emptySea's length is 1
	
	final static String emptySeaType = "empty";//emptySea's type is "empty"
	
	/**
	 * constructor. Calling parent class's constructor to set up
	 */
	public EmptySea() {
		super(emptySeaLength);
	}
	
	/**
	 * override the shootAt method in parent class, always return false
	 */
	@Override
	boolean shootAt(int row, int column) {
		
		return false;
	}
	
	/**
	 * override the isSunk method in parent class, always return false
	 */
	@Override
	boolean isSunk() {
		return false;
	}
	
	/*
	 * display "-" if a shot has been fired but nothing has been hit
	 */
	@Override
	public String toString() {
		return "-";
	}
	
	/**
	 * override the abstract getShipType method in parent class
	 */
	@Override
	public String getShipType() {
		return emptySeaType;
	}
	
}

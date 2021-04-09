package battleship;

public class EmptySea extends Ship {
	
	final static int emptySeaLength = 1;
	
	final static String emptySeaType = "empty";
	
	public EmptySea() {
		super(emptySeaLength);
	}
	
	@Override
	boolean shootAt(int row, int column) {
		
		return false;
	}
	
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
	
	@Override
	public String getShipType() {
		return emptySeaType;
	}
	
}

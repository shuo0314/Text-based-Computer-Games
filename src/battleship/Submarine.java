package battleship;

public class Submarine extends Ship {
	
	final static int submarineLength = 1;//submarine's length is 1
	
	final static String submarineType = "submarine";//submarine's type is "submarine"
	
	/**
	 * constructor. Calling parent class's constructor to set up
	 */
	public Submarine() {
		super(submarineLength);
	}

	/**
	 * override the abstract getShipType method in parent class
	 */
	@Override
	public String getShipType() {
		return submarineType;
	}

}

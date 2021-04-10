package battleship;

public class Cruiser extends Ship {
	
	final static int cruiserLength = 3;//cruiser's length is 3
	
	final static String cruiserType = "cruiser";//cruiser's type is "cruiser"
	
	/**
	 * constructor. Calling parent class's constructor to set up
	 */
	public Cruiser() {
		super(cruiserLength);
	}

	/**
	 * override the abstract getShipType method in parent class
	 */
	@Override
	public String getShipType() {
		return cruiserType;
	}
}

package battleship;

public class Destroyer extends Ship {
	
	final static int destroyerLength = 2;//destroyer's length is 2
	
	final static String destroyerType = "destroyer";//destroyer's type is "destroyer"
	
	/**
	 * constructor. Calling parent class's constructor to set up
	 */
	public Destroyer() {
		super(destroyerLength);
	}

	/**
	 * override the abstract getShipType method in parent class
	 */
	@Override
	public String getShipType() {
		return destroyerType;
	}

}

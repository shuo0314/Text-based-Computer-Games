package battleship;

public class Battleship extends Ship {
	
	final static int battleshipLength = 4;//battleship's length is 4
	
	final static String battleshipType = "battleship";//hattleship's type is "battleship"
	
	/**
	 * constructor. Calling parent class's constructor to set up
	 */
	public Battleship() {
		super(battleshipLength);
	}

	/**
	 * override the abstract getShipType method in parent class
	 */
	@Override
	public String getShipType() {
		return battleshipType;
	}
}

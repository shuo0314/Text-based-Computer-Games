package battleship;

public class Battleship extends Ship {
	
	final static int battleshipLength = 4;
	
	final static String battleshipType = "battleship";
	
	public Battleship() {
		super(battleshipLength);
	}

	@Override
	public String getShipType() {
		return battleshipType;
	}
}

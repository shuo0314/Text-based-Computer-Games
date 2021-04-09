package battleship;

public class Submarine extends Ship {
	
	final static int submarineLength = 1;
	
	final static String submarineType = "submarine";
	
	public Submarine() {
		super(submarineLength);
	}

	@Override
	public String getShipType() {
		return submarineType;
	}

}

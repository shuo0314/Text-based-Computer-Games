package battleship;

public class Cruiser extends Ship {
	
	final static int cruiserLength = 3;
	
	final static String cruiserType = "cruiser";
	
	public Cruiser() {
		super(cruiserLength);
	}

	@Override
	public String getShipType() {
		return cruiserType;
	}
}

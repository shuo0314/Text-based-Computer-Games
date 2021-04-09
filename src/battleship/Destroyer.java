package battleship;

public class Destroyer extends Ship {
	
	final static int destroyerLength = 2;
	
	final static String destroyerType = "destroyer";
	
	public Destroyer() {
		super(destroyerLength);
	}

	@Override
	public String getShipType() {
		return destroyerType;
	}

}

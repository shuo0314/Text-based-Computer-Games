package battleship;

import java.util.Random;

public class Ocean {
	
	int numberOfShips = 10;
	
	int oceanSize = 10;
	
	private Ship[][] ships = new Ship[oceanSize][oceanSize];// Used to quickly determine which ship is in any given location
	private boolean[][] fireMap = new boolean[oceanSize][oceanSize]; //used to quickly determine whether a given location has been hit or not
	
	static final int numOfBattleships = 1;
	static final int numOfCruisers = 2;
	static final int numOfDestroyers = 3;
	static final int numOfSubmarines = 4;
	
	private int shotsFired;// The total number of shots fired by the user
	
	private int hitCount;// The number of times a shot hit a ship.
	
	private int shipsSunk; // The number of ships sunk (10 ships in all)
	
	/**
	 * Constructor method to initialize variables shotsFired, hitCount, shipsSunk to 0
	 * create an ocean (10*10 ship array) with empty sea placed on each place
	 * create a fire map (10*10 boolean array) with each place initialize to false
	 */
	public Ocean() {
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		
		this.createEmptyOcean();
		
		this.createFireMap();
	}
	
	/**
	 * create empty ocean by placing a EmptySea instance into every place in the ocean.
	 */
	private void createEmptyOcean() {
		for(int i=0; i<oceanSize; i++) {
			for (int j=0; j<oceanSize; j++) {
				EmptySea emptysea= new EmptySea();
				emptysea.placeShipAt(i, j, true, this);
			}
		}
	}
	
	/**
	 * create fire map, a 10*10 2D array and initialize each place to false since
	 * no place has been hit yet at the starting of the game 
	 */
	private void createFireMap() {
		for(int i=0; i<oceanSize; i++) {
			for (int j=0; j<oceanSize; j++) {
				this.fireMap[i][j] = false;
			}
		}
	}
	
	/**
	 * create different ships according to the requirement of ship numbers:one battleship, two cruisers, three destroyers and four submarines.
	 * Order is from the longest ship to the shortest ship,
	 * that is in the order of battleship, cruiser, destroyer and submarine,  
	 * calling helper method placeOneShipRandomly() to place one ship after creating it.
	 */
	void placeAllShipsRandomly() {
		for(int i=0;i<numOfBattleships; i++) {
			Ship battleShip = new Battleship();
			this.placeOneShipRandomly(battleShip);
		}
		for(int i=0;i<numOfCruisers; i++) {
			Ship cruiser = new Cruiser();
			this.placeOneShipRandomly(cruiser);
		}
		for(int i=0;i<numOfDestroyers; i++) {
			Ship destroyer = new Destroyer();
			this.placeOneShipRandomly(destroyer);
		}
		for(int i=0;i<numOfSubmarines; i++) {
			Ship submarine = new Submarine();
			this.placeOneShipRandomly(submarine);
		}
	}
	
	/**
	 *Helper method to place one ship on the ocean randomly
	 *@param shipName
	 * (1) Randomly generate three numbers, the first two are the location (row, column), the third is the direction.
     * (2) Check if the ship can be placed at the location and direction, if yes, place it; Otherwise, repeat (1) and (2) until place it.
	 */
	private void placeOneShipRandomly(Ship shipName) {
		Random rand = new Random();
		
		while(true) {
			int randRow = rand.nextInt(oceanSize);
			int randCol = rand.nextInt(oceanSize);
			boolean isHorizontal = rand.nextBoolean();
			
			if(shipName.okToPlaceShipAt(randRow, randCol, isHorizontal, this)) {
				shipName.placeShipAt(randRow, randCol, isHorizontal, this);
				break;
			}
		}
	}
	
	/**
     * Returns true if the given location contains a real ship, false if it does not
     * @param row row index of a given location
     * @param column column index of a given location
     * @return true if occupied, false if not
     */
	boolean isOccupied(int row, int column) {
		//get ship type on position (row,column)
		String shipTypeHere = this.getShipArray()[row][column].getShipType();
		if(shipTypeHere != "empty") {
			return true;//if type is not type of empty sea, return true
		}
		return false;//if type is type of empty sea, return false
	}
	
	/**
     * Returns true if the given location contains a ”real” ship, still afloat, (not an EmptySea), false if it does not.
     * In addition, this method updates the number of shots that have been fired, and the number of hits.
     * Note: If a location contains a “real” ship, shootAt should return true every time the user shoots at that same location.
     * Once a ship has been ”sunk”, additional shots at its location should return false.
     *
     * 1) Add 1 to shotsFired no matter hit or miss
     * 2) Shoot at the ocean (row, column), return false if miss;
     *    Otherwise add 1 to HitCount, then check if the ship sunk and add 1 if the whole ship sunk.
     * @param row
     * @param column
     * @return true if hit a real ship, false if not
     */
	boolean shootAt(int row, int column) {
		//update number of shots fired
		this.setShotsFired(this.getShotsFired()+1);
		//set the position (row,column) on fire map to true since this place is already shot
		this.getFireMap()[row][column] = true;
		Ship shipHere = this.getShipArray()[row][column];
		//if the ship at this place is empty sea, return false
		if(shipHere.getShipType()=="empty") {
			return false;
		//or if the ship at this place is already sunk, player shoots repeatly at this place, return false
		}else if(shipHere.isSunk()) {
			return false;
		}else {//if player shoots this place for the first time and the ship at this place is not empty sea, return true
			this.setHitCount(this.getHitCount()+1);
			//calling shootAt() method in Ship class to update the hitarray of this ship
			shipHere.shootAt(row, column);
			//if now this ship is sunk, add one to the number of ships sunk
			if(shipHere.isSunk()) {
				this.setShipsSunk(this.getShipsSunk()+1);
			}
			return true;
		}
	}
	
	/**
	 * set number of shots fired to shots
	 * @param shots
	 */
	public void setShotsFired(int shots) {
		this.shotsFired = shots;
	}
	
	/**
     * get the number of shots fired (in the game)
     * @return number of shots
     */
	int getShotsFired() {
		return this.shotsFired;
	}
	
	/**
	 * set the number of hit to hitcount
	 * @param hitcount
	 */
	public void setHitCount(int hitcount) {
		this.hitCount = hitcount;
	}
	
	/**
	 * set the number of ships sunk to shipSunk
	 * @param shipSunk
	 */
	public void setShipsSunk(int shipSunk) {
		this.shipsSunk = shipSunk;
	}
	
	/**
	 * get number of hit in the game
	 * @return number of hit
	 */
	int getHitCount() {
		return this.hitCount;
	}
	
	/**
	 * get number of ships already sunk in the game
	 * @return number of ship sunk
	 */
	int getShipsSunk() {
		return this.shipsSunk;
	}
	
	/**
	 * get the fire map (10*10 boolean array)
	 * @return boolean array
	 */
	boolean[][] getFireMap(){
		return this.fireMap;
	}
	
	/**
	 * if all ships are sunk, game is over, return true
	 * else, game is not over, return false
	 * @return true if game is over, false if not
	 */
	boolean isGameOver() {
		if (this.shipsSunk == numberOfShips) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * Gets the 10x10 array of Ocean, which is represented by ShipArray. 
     * @return 10x10 array of Ships
     */
	Ship[][] getShipArray(){
		return this.ships;
	}
	
	/**
     * Prints the Ocean. Row numbers should be displayed along the left edge of the array, and column numbers should be displayed along the top.
     * Numbers should be 0 to 9, not 1 to 10.
     * 1) The top left corner square should be 0, 0.
     * 2) 'x': Use 'x' to indicate a location that you have fired upon and hit a (real) ship.
     * (reference the description of toString in the Ship class)
     * 3) '-': Use '-' to indicate a location that you have fired upon and found nothing there.
     * (reference the description of toString in the EmptySea class)
     * 4) 's': Use 's' to indicate a location containing a sunken ship. (reference the description of toString in the Ship class)
     * 5) '.': and use '.' (a period) to indicate a location that you have never fired upon
     * 6) This is the only method in the Ocean class that does any input/output, and it is never called from within the Ocean class,
     * only from the BattleshipGame class.
     */
	void print() {
		//print an empty line
		System.out.println();
		//print the top left corner
		System.out.print(" ");
		
		//print column number on the top line
		for(int i=0; i<oceanSize; i++) {
			System.out.print(" "+i);
		}
		//print row number on the next line at the start
		for(int j=0; j<oceanSize; j++) {
			System.out.print("\n"+j);
			// for each column on this row j
			for(int k=0; k<oceanSize; k++) {
				//if this place is already hit, print this ship
				if(this.getFireMap()[j][k]) {
					Ship ship = this.getShipArray()[j][k];
					System.out.print(" "+ ship);
				}else {//is this place is not hit yet, place the sign '.'
					System.out.print(" "+'.');
				}
				
			}
		}
		System.out.println();
	}
	
	
	/**
     * Prints the Ocean with the location of the ships
     * mark their location by the first letter of the shiptype
     * Helper function to help debug
     */
	void printWithShips() {
		System.out.println();
		//print the top left corner
		System.out.print(" ");
		
		//print column number on the top line
		for(int i=0; i<oceanSize; i++) {
			System.out.print(" "+i);
		}
		//print row number on the next line
		for(int j=0; j<oceanSize; j++) {
			System.out.print("\n"+j);
			//print ship array on this row
			for(int k=0; k<oceanSize; k++) {
				//get ship at this location
				Ship ship = this.getShipArray()[j][k];
				//print their signs according to the ship type
				if(ship.getShipType()=="empty") {
					System.out.print(" "+" ");
				}else if(ship.getShipType()=="battleship") {
					System.out.print(" "+'b');
				}else if(ship.getShipType()=="cruiser") {
					System.out.print(" "+'c');
				}else if(ship.getShipType()=="destroyer") {
					System.out.print(" "+'d');
				}else if(ship.getShipType()=="submarine") {
					System.out.print(" "+'s');
				}
			}
	
		}
		System.out.println();
	}
}


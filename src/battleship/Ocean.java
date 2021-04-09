package battleship;

import java.util.Random;

public class Ocean {
	
	int numberOfShips = 10;
	
	int oceanSize = 10;
	
	private Ship[][] ships = new Ship[oceanSize][oceanSize];
	private boolean[][] fireMap = new boolean[oceanSize][oceanSize];
	
	static final int NUM_OF_BATTLESHIPS = 1;
	static final int NUM_OF_CRUISERS = 2;
	static final int NUM_OF_DESTROYERS = 3;
	static final int NUM_OF_SUBMARINES = 4;
	
	private int shotsFired;
	
	private int hitCount;
	
	private int shipsSunk;
	
	public Ocean() {
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		
		this.createEmptyOcean();
		
		this.createFireMap();
	}
	
	private void createEmptyOcean() {
		for(int i=0; i<oceanSize; i++) {
			for (int j=0; j<oceanSize; j++) {
				EmptySea emptysea= new EmptySea();
				emptysea.placeShipAt(i, j, true, this);
			}
		}
	}
	
	private void createFireMap() {
		for(int i=0; i<oceanSize; i++) {
			for (int j=0; j<oceanSize; j++) {
				this.fireMap[i][j] = false;
			}
		}
	}
	
	void placeAllShipsRandomly() {
		for(int i=0;i<NUM_OF_BATTLESHIPS; i++) {
			Ship battleShip = new Battleship();
			this.placeOneShipRandomly(battleShip);
		}
		for(int i=0;i<NUM_OF_CRUISERS; i++) {
			Ship cruiser = new Cruiser();
			this.placeOneShipRandomly(cruiser);
		}
		for(int i=0;i<NUM_OF_DESTROYERS; i++) {
			Ship destroyer = new Destroyer();
			this.placeOneShipRandomly(destroyer);
		}
		for(int i=0;i<NUM_OF_SUBMARINES; i++) {
			Ship submarine = new Submarine();
			this.placeOneShipRandomly(submarine);
		}
	}
	
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
	
	boolean isOccupied(int row, int column) {
		String shipTypeHere = this.getShipArray()[row][column].getShipType();
		if(shipTypeHere != "empty") {
			return true;
		}
		return false;
	}
	
	boolean shootAt(int row, int column) {
		this.setShotsFired(this.getShotsFired()+1);
		this.getFireMap()[row][column] = true;
		Ship shipHere = this.getShipArray()[row][column];
		if(shipHere.getShipType()=="empty") {
			return false;
		}else if(shipHere.isSunk()) {
			return false;
		}else {
			this.setHitCount(this.getHitCount()+1);
			shipHere.shootAt(row, column);
			if(shipHere.isSunk()) {
				this.setShipsSunk(this.getShipsSunk()+1);
			}
			return true;
		}
	}
	
	public void setShotsFired(int shots) {
		this.shotsFired = shots;
	}
	
	
	int getShotsFired() {
		return this.shotsFired;
	}
	
	public void setHitCount(int hitcount) {
		this.hitCount = hitcount;
	}
	
	public void setShipsSunk(int shipSunk) {
		this.shipsSunk = shipSunk;
	}
	
	int getHitCount() {
		return this.hitCount;
	}
	
	int getShipsSunk() {
		return this.shipsSunk;
	}
	
	boolean[][] getFireMap(){
		return this.fireMap;
	}
	
	boolean isGameOver() {
		if (this.shipsSunk == numberOfShips) {
			return true;
		} else {
			return false;
		}
	}
	
	Ship[][] getShipArray(){
		return this.ships;
	}
	
	void print() {
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
				if(this.getFireMap()[j][k]) {
					Ship ship = this.getShipArray()[j][k];
					System.out.print(" "+ ship);
				}else {
					System.out.print(" "+'.');
				}
				
			}
		}
		System.out.println();
	}
	
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


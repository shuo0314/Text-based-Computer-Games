package battleship;

import java.util.Random;

import java.lang.Math.*;

public abstract class Ship {
	
	private int bowRow;// The row that contains the bow (front part of the ship)
	
	private int bowColumn;// The column that contains the bow (front part of the ship)
	
	private int length;// The length of the ship
	
	private boolean horizontal;// A boolean that represents whether the ship is going to be placed horizontally or vertically
	
	private boolean[] hit;// An array of booleans that indicate whether that part of the ship has been hit or not
	
	/**
     * Constructor: sets the length property of the particular ship and initializes the hit array based on that length;
     * @param length the length of a ship
     */
	public Ship(int length) {
		this.length = length;
		this.hit = new boolean[length];
	}
	
	/*
	 * get length of the ship
	 */
	public int getLength() {
		return this.length;
	}
	
	/*
	 * get the row corresponding to the position of the bow 
	 */
	public int getBowRow() {
		return this.bowRow;
	}
	
	/*
	 * get the column corresponding to the position of the bow.
	 */
	public int getBowColumn() {
		return this.bowColumn;
	}
	
	/*
	 * get the hit array
	 */
	public boolean[] getHit() {
		return this.hit;
	}
	
	/*
	 * get whether the ship is horizontal or not
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}
	
	/*
	 * set the row of the front part of the bow
	 */
	public void setBowRow(int row) {
		this.bowRow = row;
	}
	
	/*
	 * set the column of the front part of the bow
	 */
	public void setBowColumn(int column) {
		this.bowColumn = column;
	}
	
	/*
	 * set whether ship is horizontal
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	/*
	 * abstract method without implement
	 */
	public abstract String getShipType();
	
	/**
	 * Method to check whether ok to place a ship at a given position (row,column)
	 * no ships are immediately adjacent to each other, either horizontally, vertically, or diagonally
	 * The ship must not ”stick out” beyond the array.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if ok to place ship at (row,column), false if not ok to place
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		int shipLength = this.getLength(); //get length of the ship
		int leftCol = column - shipLength +1; //if ship is placed at this place, calculate the left most column the ship occupies
		int rightCol = column;//if ship is placed at this place, calculate the right most column the ship occupies
		int upRow = row - shipLength +1;//if ship is placed at this place, calculate the up most row the ship occupies
		int downRow = row;//if ship is placed at this place, calculate the down most row the ship occupies
		int oceanSizelimit = ocean.oceanSize-1; //calculate the boarder of the ocean
		int leftTestCol =  Math.max(0,leftCol-1);//left Test column has to be 0 or bigger to be valid
		int rightTestCol = Math.min(rightCol+1,oceanSizelimit);//right test column has to be 9 or smaller to be valid
		int upTestRow = Math.max(0, upRow-1);//up Test row has to be 0 or bigger to be valid
		int downTestRow = Math.min(downRow+1, oceanSizelimit);//down test row has to be 9 or smaller to be valid
		
		//if the ship is placed horizontally
		if (horizontal) {
			  if(leftCol<0 || rightCol>oceanSizelimit) {//assume ship is placed, check whether it sits on place out of boarder
				  return false;
			  }else {//if all parts of ship sit within the boarder, check each place in the frame that immediately adjacent this ship
				  //if any place is occupied, not ok to place ship here,return false
				  for(int i= rightTestCol;i>=leftTestCol;i--) {
					  for(int j = downTestRow; j>=Math.max(0, row-1);j--) {
						  if (ocean.isOccupied(j, i)){
							  return false;
						  }
					  }
				  }
			  }
		}else {//if the ship is not placed horizontally
			if(upRow<0 || downRow>oceanSizelimit) {//assume ship is placed, check whether it sits on place out of boarder
				return false;
			}else {//if all parts of ship sit within the boarder, check each place in the frame that tightly surrounding this ship
				  //if any place is occupied, not ok to place ship here,return false
				for(int i= rightTestCol;i>=Math.max(0,column-1);i--) {
					  for(int j = downTestRow; j>=upTestRow;j--) {
						  if (ocean.isOccupied(j, i)){
							  return false;
						  }
					  }
				}	  
			}
		}
		return true;//if the surrounding frame is all clear and ship will not sit out of boarder, ok to place this ship, return true
	}
	
	/**
	 * “Puts” the ship in the ocean. 
	 * Giving values to the bowRow, bowColumn, and horizontal instance variables in the ship
	 * Putting a reference to the ship in each location in the ships array in the Ocean object.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		this.setBowColumn(column);//set column of bow to the given column
		this.setBowRow(row);//set row of the bow to the given row
		this.setHorizontal(horizontal);//set whether bow is placed horizontal
		
		if(horizontal) {//if ship is placed horizontally
			int leftCol = column - this.getLength() +1;//find the left most column the ship occupied
			int rightCol = column;//find the right most column the ship occupied
			for (int i=leftCol;i<=rightCol;i++) {
				ocean.getShipArray()[row][i] = this;//put a reference to the ship in each location it occupies in the ocean
			}
		}else {//if ship is not placed horizontally
			int upRow = row - this.getLength() +1;//find the up most row the ship occupied
			int downRow = row;//find the down most row the ship occupied
			for (int j=upRow; j<=downRow; j++) {
				ocean.getShipArray()[j][column] = this;//put a reference to the ship in each location it occupies in the ocean
			}
		}
	}
	
	/**
     * If a part of the ship occupies the given row and column, and the ship hasn’t been sunk, mark that part of the ship as “hit”
     * (in the hit array, index 0 indicates the bow) and return true; otherwise return false.
     * @param row
     * @param column
     * @return true if hit a real ship, false if hit empty sea
     */
	boolean shootAt(int row, int column) {
		int shipBowRow = this.getBowRow();
		int shipBowCol = this.getBowColumn();
		int shipLength = this.getLength();
		int hitArrayIndex;
		
		//if this ship is empty sea or already sunk, return false
		if(this.getShipType()=="empty" || this.isSunk()) {
			return false;
		}else {//otherwise this ship is a real shit and not sunk yet
			//if ship is placed horizontally
			if(this.isHorizontal()) {
				int leftCol = shipBowCol - shipLength +1;//find the left most column the ship occupied
				int rightCol = shipBowCol;//find the right most column the ship occupied
				//check whether the given row and column is located within the area that this ship covers
				if(leftCol <= column && column <= rightCol && row == shipBowRow) {
					hitArrayIndex = shipBowCol-column;
					this.getHit()[hitArrayIndex] = true;//if yes, this ship is hit, update its hitArray accordingly and return true
					return true;
				}
				
			}else {//if ship is not placed horizontally
				int upRow = shipBowRow - shipLength +1;//find the up most row the ship occupied
				int downRow = shipBowRow;//find the down most row the ship occupied
				//check whether the given row and column is located within the area that this ship covers
				if(upRow <= row && row <= downRow && column == shipBowCol) {
					hitArrayIndex = shipBowRow - row;
					this.getHit()[hitArrayIndex] = true;//if yes, this ship is hit, update its hitArray accordingly and return true
					return true;
				}
			}
		}
		return false;//return false if the given row and column is not located within the area that this ship covers, this ship is not hit
	}
	
	 /**
     * Return true if every part of the ship has been hit, false otherwise
     * @return boolean value
     */
	boolean isSunk() {
		for (boolean i: this.getHit()) {
			if (!i) {
				return false;
			}
		}
		return true;
	}
	
	 /**
     * Returns a single-character String to use in the Ocean’s print method. 
     * return ”s” if the ship has been sunk and ”x” if it has not been sunk. 
     * not to be used to print locations that have not been shot at. 
     * behaves exactly the same for all ship types in subclass
     *
     * @return "s" if ship sunk, "x" if shit is hit
     */
	@Override
	public String toString() {
		if (this.isSunk()){
			return "s";//return 's' if ship is already sunk
		}else {
			return "x"; //otherwise ship is not sunk, but hit, return 'x'
		}
		
	}
}
	

package battleship;

import java.util.Random;

import java.lang.Math.*;

public abstract class Ship {
	
	private int bowRow;
	
	private int bowColumn;
	
	private int length;
	
	private boolean horizontal;
	
	private boolean[] hit;
	
	/*
	 * constructor to set length and hit array
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
	
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		int shipLength = this.getLength();
		int leftCol = column - shipLength +1;
		int rightCol = column;
		int upRow = row - shipLength +1;
		int downRow = row;
		int oceanSizelimit = ocean.oceanSize-1;
		int leftTestCol =  Math.max(0,leftCol-1);
		int rightTestCol = Math.min(rightCol+1,oceanSizelimit);
		int upTestRow = Math.max(0, upRow-1);
		int downTestRow = Math.min(downRow+1, oceanSizelimit);
		
		if (horizontal) {
			  if(leftCol<0 || rightCol>oceanSizelimit) {
				  return false;
			  }else {
				  for(int i= rightTestCol;i>=leftTestCol;i--) {
					  for(int j = downTestRow; j>=Math.max(0, row-1);j--) {
						  if (ocean.isOccupied(j, i)){
							  return false;
						  }
					  }
				  }
			  }
		}else {
			if(upRow<0 || downRow>oceanSizelimit) {
				return false;
			}else {
				for(int i= rightTestCol;i>=Math.max(0,column-1);i--) {
					  for(int j = downTestRow; j>=upTestRow;j--) {
						  if (ocean.isOccupied(j, i)){
							  return false;
						  }
					  }
				}	  
			}
		}
		return true;
	}
	
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		this.setBowColumn(column);
		this.setBowRow(row);
		this.setHorizontal(horizontal);
		
		if(horizontal) {
			int leftCol = column - this.getLength() +1;
			int rightCol = column;
			for (int i=leftCol;i<=rightCol;i++) {
				ocean.getShipArray()[row][i] = this;
			}
		}else {
			int upRow = row - this.getLength() +1;
			int downRow = row;
			for (int j=upRow; j<=downRow; j++) {
				ocean.getShipArray()[j][column] = this;
			}
		}
	}
	
	boolean shootAt(int row, int column) {
		int shipBowRow = this.getBowRow();
		int shipBowCol = this.getBowColumn();
		int shipLength = this.getLength();
		int hitArrayIndex;
		
		
		if(this.getShipType()=="empty" || this.isSunk()) {
			return false;
		}else {
			if(this.isHorizontal()) {
				int leftCol = shipBowCol - shipLength +1;
				int rightCol = shipBowCol;
				if(leftCol <= column && column <= rightCol && row == shipBowRow) {
					hitArrayIndex = shipBowCol-column;
					this.getHit()[hitArrayIndex] = true;
					return true;
				}
				
			}else {
				int upRow = shipBowRow - shipLength +1;
				int downRow = shipBowRow;
				if(upRow <= row && row <= downRow && column == shipBowCol) {
					hitArrayIndex = shipBowRow - row;
					this.getHit()[hitArrayIndex] = true;
					return true;
				}
			}
		}
		return false;
	}
	
	boolean isSunk() {
		for (boolean i: this.getHit()) {
			if (!i) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * abstract method toString
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
	

package battleship;

import java.util.Scanner;

public class BattleshipGame {

	public static void main(String[] args) {
		
		boolean play = true;
		
		//While loop to play game for one round
		while(play) {
		//nextShot is true if we are going to make next shot
		boolean nextShot = true;
		//welcome message
		System.out.println("-----BattleshipGame Starts!-----");
		//create a new ocean, which is a 10*10 array with EmptySea on each position
		Ocean ocean = new Ocean();
		
		//place all ships based on requirement
		ocean.placeAllShipsRandomly();
		
		//initialize a new scanner to take fire position input from player
		Scanner scanner = new Scanner(System.in);
		//ocean.printWithShips(); //Helper function to debug
		
		while(nextShot) {
			
			//print ocean map for player to view
			ocean.print();
			//print message to ask for player input
			System.out.println("---Enter Row, column:");
			//take input and store the first input in row, the second input in column
			int row = scanner.nextInt();
			int column = scanner.nextInt();
			
			//if player choose a position (row, column) that has a real ship there, shootAt()method return true
			if(ocean.shootAt(row, column)) {
				//if true, player hit a ship, print message "hit"
				System.out.println("hit");
				//get the ship on the position(row,column)
				Ship shipHit = ocean.getShipArray()[row][column];
				
				//if this ship is sunk
				if(shipHit.isSunk()) {
					//print message to tell player the ship type 
					System.out.println("---You just sank a ship - "+shipHit.getShipType());
					//ocean.setShipsSunk(ocean.getShipsSunk()+1);
				}
			} else {//if player choose a position without a real ship there
				//print message "miss"
				System.out.println("miss");
			}
			//After a shot, check if player already hit all ships, if yes, isGameOVer() return true
			if (ocean.isGameOver()) {
				//print message to tell player the player result, with number of shots fired and hit count
				System.out.println("-----You sank all ships by "+ocean.getShotsFired()+" shots with "+ocean.getHitCount()+" hits, game over!-----");
				//this round finish, set nextShot to false if we are not going to make the next shot
				nextShot = false;				
			}
		}
		//ask player whether play another round
		System.out.println("Play again? (yes/no):");
		//take player's response
		String playAgain = scanner.next();
		//if replying "yes", Player wants to play another round
		if(playAgain.equals("yes")) {
			System.out.println("Play again!");
			//set play to true to play another round
			play = true;
		}else {
			//if not playing, print message "Goodbye" and set play to false not play another round
			System.out.println("Goodbye!");
			play = false;
		}
		}

	}

}

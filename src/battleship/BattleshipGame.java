package battleship;

import java.util.Scanner;

public class BattleshipGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Create Ocean(Class Ocean: set ocean, 10*10 grid, put dot inside each grid)
		
		//Place ships(Class Ship: put ships accordingly)
		
		//Start game(Player input shot position, display the result)
		
		//End game(Print final scores, ask the user if wants to play again)
		boolean play = true;
		
		
		while(play) {
		boolean nextShot = true;
		System.out.println("-----BattleshipGame Starts!-----");
		Ocean ocean = new Ocean();
		
		ocean.placeAllShipsRandomly();
		Scanner scanner = new Scanner(System.in);
		ocean.printWithShips();
		
		while(nextShot) {
			//System.out.println("miss");
			ocean.print();
			System.out.println("---Enter Row, column:");
			int row = scanner.nextInt();
			int column = scanner.nextInt();
			
			if(ocean.shootAt(row, column)) {
				System.out.println("hit");
				Ship shipHit = ocean.getShipArray()[row][column];
				System.out.println("##############ship type and hit array is "+shipHit.getShipType());
				for(boolean value:shipHit.getHit()) {
				System.out.println("##############shit hitarray is "+ value);
				}if(shipHit.isSunk()) {
					System.out.println("---You just sank a ship - "+shipHit.getShipType());
					//ocean.setShipsSunk(ocean.getShipsSunk()+1);
				}
			} else {
				System.out.println("miss");
			}
			System.out.println("##############ship sunk #"+ocean.getShipsSunk());
			if (ocean.isGameOver()) {
				System.out.println("-----You sank all ships by "+ocean.getShotsFired()+" shots with "+ocean.getHitCount()+" hits, game over!-----");
				nextShot = false;				
			}
		}
		System.out.println("Play again? (yes/no):");
		String playAgain = scanner.next();
		if(playAgain.equals("yes")) {
			System.out.println("Play again!");
			play = true;
		}else {
			System.out.println("Goodbye!");
			play = false;
		}
		}

	}

}

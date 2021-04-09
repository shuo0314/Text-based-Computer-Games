package battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

	Ocean ocean;
	
	static int NUM_BATTLESHIPS = 1;
	static int NUM_CRUISERS = 2;
	static int NUM_DESTROYERS = 3;
	static int NUM_SUBMARINES = 4;
	static int OCEAN_SIZE = 10;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}
	
	@Test
	void testEmptyOcean() {
		
		//tests that all locations in the ocean are "empty"
		
		Ship[][] ships = ocean.getShipArray();
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				
				assertEquals("empty", ship.getShipType());
			}
		}
		
		assertEquals(0, ships[0][0].getBowRow());
		assertEquals(0, ships[0][0].getBowColumn());
		
		assertEquals(5, ships[5][5].getBowRow());
		assertEquals(5, ships[5][5].getBowColumn());
		
		assertEquals(9, ships[9][0].getBowRow());
		assertEquals(0, ships[9][0].getBowColumn());
	}
	
	@Test
	void testPlaceAllShipsRandomly() {
		
		//tests that the correct number of each ship type is placed in the ocean
		
		ocean.placeAllShipsRandomly();

		Ship[][] ships = ocean.getShipArray();
		ArrayList<Ship> shipsFound = new ArrayList<Ship>();
		
		int numBattlehips = 0;
		int numCruisers = 0;
		int numDestroyers = 0;
		int numSubmarines = 0;
		int numEmptySeas = 0;
		
		//######Logic test again
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				if (!shipsFound.contains(ship)) {
					shipsFound.add(ship);
				}
			}
		}
		
		for (Ship ship : shipsFound) {
			if ("battleship".equals(ship.getShipType())) {		
				numBattlehips++;
			} else if ("cruiser".equals(ship.getShipType())) {
				numCruisers++;
			} else if ("destroyer".equals(ship.getShipType())) {
				numDestroyers++;
			} else if ("submarine".equals(ship.getShipType())) {
				numSubmarines++;
			} else if ("empty".equals(ship.getShipType())) {
				numEmptySeas++;
			}
		}
		
		assertEquals(NUM_BATTLESHIPS, numBattlehips);
		assertEquals(NUM_CRUISERS, numCruisers);
		assertEquals(NUM_DESTROYERS, numDestroyers);
		assertEquals(NUM_SUBMARINES, numSubmarines);
		
		//calculate total number of available spaces and occupied spaces
		int totalSpaces = OCEAN_SIZE * OCEAN_SIZE; 
		int occupiedSpaces = (NUM_BATTLESHIPS * 4)
				+ (NUM_CRUISERS * 3)
				+ (NUM_DESTROYERS * 2)
				+ (NUM_SUBMARINES * 1);
		
		//test number of empty seas, each with length of 1
		assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
	}

	@Test
	void testIsOccupied() {

		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(1, 5));
		
		
		//More tests
		assertTrue(ocean.isOccupied(0, 0));
		assertFalse(ocean.isOccupied(9, 9));
		
		Ship ship = new Cruiser();
		row = 7;
		column = 7;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(7, 5));
		assertFalse(ocean.isOccupied(7, 8));
		
		ship = new Battleship();
		row = 3;
		column = 9;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(3, 6));
		assertTrue(ocean.isOccupied(3, 7));
		assertTrue(ocean.isOccupied(3, 8));
		assertTrue(ocean.isOccupied(3, 9));
		assertFalse(ocean.isOccupied(3, 5));
	}

	@Test
	void testShootAt() {
	
		assertFalse(ocean.shootAt(0, 1));
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		
		
		//More tests
		assertFalse(ocean.shootAt(0, 9));
		
		Ship ship = new Submarine();
		row = 0;
		column = 9;
		horizontal = false;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(0, 9));
		assertTrue(ship.isSunk());
		
		
		
		assertFalse(ocean.shootAt(6, 3));
		
		ship = new Cruiser();
		row = 6;
		column = 3;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(6, 3));
		assertFalse(ship.isSunk());
		assertTrue(ocean.shootAt(6, 2));
		assertTrue(ocean.shootAt(6, 1));
		assertFalse(ocean.shootAt(6, 0));
		assertTrue(ship.isSunk());
		assertFalse(ocean.shootAt(6, 2));
	}

	@Test
	void testGetShotsFired() {
		
		//should be all false - no ships added yet
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(4, ocean.getShotsFired());
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(6, ocean.getShotsFired());
		
		
		//More tests
		Ship cruiser = new Cruiser();
		row = 7;
		column = 7;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(7, 7));
		assertFalse(cruiser.isSunk());
		assertTrue(ocean.shootAt(7, 6));
		assertFalse(cruiser.isSunk());
		assertTrue(ocean.shootAt(7, 5));
		assertTrue(cruiser.isSunk());
		assertFalse(ocean.shootAt(7, 5));
		assertEquals(10, ocean.getShotsFired());
		
		
		assertTrue(ocean.shootAt(0, 0));
		assertFalse(ocean.shootAt(0, 0));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(13, ocean.getShotsFired());
	}

	@Test
	void testGetHitCount() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		
		
		//More tests
		assertFalse(ocean.shootAt(2, 5));
		assertTrue(ocean.shootAt(0, 5));
		assertFalse(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(2, ocean.getHitCount());
		
		Ship cruiser = new Cruiser();
		row = 7;
		column = 7;
		horizontal = true;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(ocean.shootAt(7, 8));
		assertFalse(ocean.shootAt(7, 9));
		assertEquals(2, ocean.getHitCount());
	}
	
	@Test
	void testGetShipsSunk() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());
		

		//More tests
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(2, ocean.getHitCount());
		assertEquals(1, ocean.getShipsSunk());
		
		Ship ship = new Submarine();
		row = 9;
		column = 9;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(ocean.shootAt(8, 9));
		assertFalse(ship.isSunk());
		assertTrue(ocean.shootAt(9, 9));
		assertTrue(ship.isSunk());
		assertEquals(3, ocean.getHitCount());
		assertEquals(2, ocean.getShipsSunk());
		
		
		Ship cruiser = new Cruiser();
		row = 7;
		column = 7;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(ocean.shootAt(7, 9));
		assertTrue(ocean.shootAt(7, 7));
		assertFalse(cruiser.isSunk());
		assertEquals(4, ocean.getHitCount());
		assertEquals(2, ocean.getShipsSunk());
	}

	@Test
	void testGetShipArray() {
		
		Ship[][] shipArray = ocean.getShipArray();
		assertEquals(OCEAN_SIZE, shipArray.length);
		assertEquals(OCEAN_SIZE, shipArray[0].length);
		
		assertEquals("empty", shipArray[0][0].getShipType());
		
		
		//More tests
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		shipArray = ocean.getShipArray();
		assertEquals("destroyer", shipArray[0][5].getShipType());
		assertEquals("empty", shipArray[2][5].getShipType());
		assertEquals(destroyer, shipArray[1][5]);
		
		Ship ship = new Submarine();
		row = 9;
		column = 9;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		shipArray = ocean.getShipArray();
		assertEquals(1, shipArray[9][9].getLength());
		assertEquals("empty", shipArray[8][9].getShipType());
		
		
	}
	
	@Test
	void testsetShotsFired() {
		int shots = ocean.getShotsFired();
		assertEquals(0, shots);
		shots++;
		ocean.setShotsFired(shots);
		assertEquals(1, ocean.getShotsFired());
		
		shots--;
		ocean.setShotsFired(shots);
		assertEquals(0, ocean.getShotsFired());
		
		shots = shots + 5;
		ocean.setShotsFired(shots);
		assertEquals(5, ocean.getShotsFired());
		
	}
	
	@Test
	void testsetHitCount() {
		int hitCount = ocean.getHitCount();
		assertEquals(0, hitCount);
		hitCount++;
		ocean.setHitCount(hitCount);
		assertEquals(1, ocean.getHitCount());
		
		hitCount--;
		ocean.setHitCount(hitCount);
		assertEquals(0, ocean.getHitCount());
		
		hitCount = hitCount + 5;
		ocean.setHitCount(hitCount);
		assertEquals(5, ocean.getHitCount());
	}
	
	@Test
	void testsetShipsSunk() {
		int shipsSunk = ocean.getShipsSunk();
		assertEquals(0, shipsSunk);
		shipsSunk++;
		ocean.setShipsSunk(shipsSunk);
		assertEquals(1,ocean.getShipsSunk());
		
		shipsSunk--;
		ocean.setShipsSunk(shipsSunk);
		assertEquals(0,ocean.getShipsSunk());
		
		shipsSunk = shipsSunk +5;
		ocean.setShipsSunk(shipsSunk);
		assertEquals(5,ocean.getShipsSunk());
		
	}
	
	@Test
	void testgetFireMap() {
		assertFalse(ocean.getFireMap()[0][0]);
		
		ocean.shootAt(1, 1);
		assertTrue(ocean.getFireMap()[1][1]);
		
		assertFalse(ocean.getFireMap()[9][9]);
		ocean.shootAt(9, 9);
		assertTrue(ocean.getFireMap()[9][9]);
	}
	
	@Test
	void testisGameOver() {
		int shipsSunk = ocean.getShipsSunk();
		assertEquals(0, shipsSunk);
		assertFalse(ocean.isGameOver());
		
		ocean.setShipsSunk(ocean.getShipsSunk()+5);
		assertEquals(5, ocean.getShipsSunk());
		assertFalse(ocean.isGameOver());
		
		ocean.setShipsSunk(ocean.getShipsSunk()+5);
		assertEquals(10, ocean.getShipsSunk());
		assertEquals(10, ocean.numberOfShips);
		assertTrue(ocean.isGameOver());
	}
	
}

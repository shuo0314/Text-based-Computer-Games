package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

	Ocean ocean;
	Ship ship;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}

	@Test
	void testGetLength() {
		ship = new Battleship();
		assertEquals(4, ship.getLength());
		
		ship = new Cruiser();
		assertEquals(3, ship.getLength());
		
		ship = new EmptySea();
		assertEquals(1, ship.getLength());
	}

	@Test
	void testGetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		
		Ship submarine = new Submarine();
		row = 1;
		column = 0;
		horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, submarine.getBowRow());
		
		Ship destroyer = new Destroyer();
		row = 2;
		column = 3;
		horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, destroyer.getBowRow());
	}

	@Test
	void testGetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		//battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());	
		
		Ship cruiser = new Cruiser();
		row = 8;
		column = 9;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		//battleship.setBowColumn(column);
		assertEquals(column, cruiser.getBowColumn());
		
		Ship emptysea = new EmptySea();
		row = 4;
		column = 4;
		horizontal = true;
		emptysea.placeShipAt(row, column, horizontal, ocean);
		//battleship.setBowColumn(column);
		assertEquals(column, emptysea.getBowColumn());
	}

	@Test
	void testGetHit() {
		ship = new Battleship();
		boolean[] hits = new boolean[4];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		assertFalse(ship.getHit()[1]);
		
		ship = new Cruiser();
		hits = new boolean[3];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		assertFalse(ship.getHit()[1]);
		assertFalse(ship.getHit()[2]);
		
		ship = new Submarine();
		hits = new boolean[1];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		
	}
	
	@Test
	void testGetShipType() {
		ship = new Battleship();
		assertEquals("battleship", ship.getShipType());
		
		ship = new Submarine();
		assertEquals("submarine", ship.getShipType());
		
		ship = new EmptySea();
		assertEquals("empty", ship.getShipType());
	}
	
	@Test
	void testIsHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(battleship.isHorizontal());
		
		Ship cruiser = new Cruiser();
		row = 8;
		column = 8;
		horizontal = false;
		cruiser.placeShipAt(row, column, horizontal, ocean);
		assertFalse(cruiser.isHorizontal());
		
		Ship destroyer = new Destroyer();
		row = 2;
		column = 3;
		horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		assertFalse(destroyer.isHorizontal());
		
	}
	
	@Test
	void testSetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowRow(row);
		assertEquals(row, battleship.getBowRow());
		
		Ship cruiser = new Cruiser();
		row = 8;
		column = 8;
		cruiser.setBowRow(row);
		assertEquals(row, cruiser.getBowRow());
		
		Ship submarine = new Submarine();
		row = 2;
		column = 3;
		submarine.setBowRow(row);
		assertEquals(row, submarine.getBowRow());
	}

	@Test
	void testSetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());
		
		Ship cruiser = new Cruiser();
		row = 8;
		column = 8;
		cruiser.setBowColumn(column);
		assertEquals(column, cruiser.getBowColumn());
		
		Ship submarine = new Submarine();
		row = 2;
		column = 3;
		submarine.setBowColumn(column);
		assertEquals(column, submarine.getBowColumn());
	}

	@Test
	void testSetHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setHorizontal(horizontal);
		assertTrue(battleship.isHorizontal());
		
		Ship cruiser = new Cruiser();
		row = 8;
		column = 8;
		horizontal = true;
		cruiser.setHorizontal(horizontal);
		assertTrue(cruiser.isHorizontal());
		
		Ship submarine = new Submarine();
		row = 2;
		column = 3;
		horizontal = false;
		submarine.setHorizontal(horizontal);
		assertFalse(submarine.isHorizontal());
	}

	@Test
	void testOkToPlaceShipAt() {
		
		//test when other ships are not in the ocean
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
		
		Ship cruiser = new Cruiser();
		row = 8;
		column = 8;
		horizontal = false;
		ok = cruiser.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
		
		Ship submarine = new Submarine();
		row = 2;
		column = 3;
		horizontal = true;
		ok = submarine.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
	}
	
	@Test
	void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {
		
		//test when other ships are in the ocean
		
		//place first ship
		Battleship battleship1 = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		battleship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		Battleship battleship2 = new Battleship();
		row = 1;
		column = 4;
		horizontal = true;
		boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
		
		Ship ship1 = new Cruiser();
		row = 2;
		column = 3;
		horizontal = true;
		ok1 = ship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		ship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		Ship ship2 = new Cruiser();
		row = 1;
		column = 5;
		horizontal = true;
		ok2 = ship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
		
		ship1 = new Destroyer();
		row = 7;
		column = 6;
		horizontal = false;
		ok1 = ship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		ship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		ship2 = new Destroyer();
		row = 6;
		column = 6;
		horizontal = false;
		ok2 = ship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
	}

	@Test
	void testPlaceShipAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		assertEquals(column, battleship.getBowColumn());
		assertTrue(battleship.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals(battleship, ocean.getShipArray()[0][1]);
		

		Ship ship = new Submarine();
		row = 9;
		column = 9;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, ship.getBowRow());
		assertEquals(column, ship.getBowColumn());
		assertTrue(ship.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[8][9].getShipType());
		assertEquals(ship, ocean.getShipArray()[9][9]);
		
		Ship ship2 = new Cruiser();
		row = 7;
		column = 9;
		horizontal = false;
		ship2.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, ship2.getBowRow());
		assertEquals(column, ship2.getBowColumn());
		assertFalse(ship2.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[4][9].getShipType());
		assertEquals(ship2, ocean.getShipArray()[6][9]);
	}

	@Test
	void testShootAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 9;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(battleship.shootAt(1, 9));
		boolean[] hitArray0 = {false, false, false, false};
		assertArrayEquals(hitArray0, battleship.getHit());
		
		Ship ship = new Cruiser();
		row = 4;
		column = 9;
		horizontal = true;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ship.shootAt(4, 9));
		boolean[] hitArray1 = {true, false, false};
		assertArrayEquals(hitArray1, ship.getHit());
		
		Ship ship2 = new Destroyer();
		row = 7;
		column = 2;
		horizontal = false;
		ship2.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ship2.shootAt(6, 2));
		boolean[] hitArray2 = {false, true};
		assertArrayEquals(hitArray2, ship2.getHit());
	}
	
	@Test
	void testIsSunk() {
		
		Ship submarine = new Submarine();
		int row = 3;
		int column = 3;
		boolean horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(submarine.isSunk());
		assertFalse(submarine.shootAt(5, 2));
		assertFalse(submarine.isSunk());
		
		Ship ship = new Submarine();
		row = 5;
		column = 5;
		horizontal = false;
		ship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(ship.isSunk());
		assertTrue(ship.shootAt(5, 5));
		assertTrue(ship.isSunk());
		
		Ship ship2 = new Cruiser();
		row = 9;
		column = 9;
		horizontal = false;
		ship2.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(ship2.isSunk());
		assertTrue(ship2.shootAt(7, 9));
		assertFalse(ship2.isSunk());
		
	}

	@Test
	void testToString() {
		
		Ship battleship = new Battleship();
		assertEquals("x", battleship.toString());
		
		int row = 9;
		int column = 1;
		boolean horizontal = false;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.shootAt(9, 1);
		assertEquals("x", battleship.toString());
		
		Ship ship = new Destroyer();
		assertEquals("x", ship.toString());
		
		row = 9;
		column = 9;
		horizontal = false;
		ship.placeShipAt(row, column, horizontal, ocean);
		ship.shootAt(9, 9);
		assertEquals("x", ship.toString());
		ship.shootAt(8, 9);
		assertEquals("s", ship.toString());
		
		Ship ship2 = new EmptySea();
		assertEquals("-", ship2.toString());
		
		row = 5;
		column = 4;
		horizontal = false;
		ship2.placeShipAt(row, column, horizontal, ocean);
		ship2.shootAt(5, 4);
		assertEquals("-", ship2.toString());
	}

}

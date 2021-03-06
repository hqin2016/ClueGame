package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class SSAL_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(6, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(12, 20);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(2, 18);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(15, 9);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(14, 14);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(11, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(8, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 18)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(3, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 7)));
		//TEST DOORWAY UP
		testList = board.getAdjList(17, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 1)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(4, 5);
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(6, 14);
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		assertTrue(testList.contains(board.getCellAt(7, 14)));
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(8, 18);
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		assertTrue(testList.contains(board.getCellAt(7, 18)));
		assertTrue(testList.contains(board.getCellAt(8, 17)));
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(19, 16);
		assertTrue(testList.contains(board.getCellAt(18, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 16)));
		assertTrue(testList.contains(board.getCellAt(19, 15)));
		assertTrue(testList.contains(board.getCellAt(19, 17)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(0, 5);
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertEquals(1, testList.size());
		
		// Test on left edge of board, 2 walkway pieces
		testList = board.getAdjList(15, 0);
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(15, 1)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(19, 21);
		assertTrue(testList.contains(board.getCellAt(19, 20)));
		assertTrue(testList.contains(board.getCellAt(19, 22)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(13,11);
		assertTrue(testList.contains(board.getCellAt(13, 12)));
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		assertTrue(testList.contains(board.getCellAt(12, 11)));
		assertTrue(testList.contains(board.getCellAt(14, 11)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(23, 11);
		assertTrue(testList.contains(board.getCellAt(23, 12)));
		assertTrue(testList.contains(board.getCellAt(22, 11)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 2 room pieces
		testList = board.getAdjList(11, 23);
		assertTrue(testList.contains(board.getCellAt(11, 22)));		
		assertEquals(1, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(5, 13);
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 12)));
		assertTrue(testList.contains(board.getCellAt(4, 13)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are MAGENTA on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(23, 5, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 5)));
		assertTrue(targets.contains(board.getCellAt(23, 4)));	
		
		board.calcTargets(7, 0, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 1)));					
	}
	
	// Tests of just walkways, 2 steps
	// These are MAGENTA on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(23, 12, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 11)));
		assertTrue(targets.contains(board.getCellAt(21, 12)));
		
		board.calcTargets(22, 5, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 4)));
		assertTrue(targets.contains(board.getCellAt(21, 4)));	
		assertTrue(targets.contains(board.getCellAt(20, 5)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(23, 12, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 12)));
		assertTrue(targets.contains(board.getCellAt(21, 12)));
		assertTrue(targets.contains(board.getCellAt(20, 11)));
		assertTrue(targets.contains(board.getCellAt(22, 11)));

		
		// Includes a path that doesn't have enough length
		board.calcTargets(0, 12, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 12)));
		assertTrue(targets.contains(board.getCellAt(4, 12)));	
		assertTrue(targets.contains(board.getCellAt(3, 13)));
		assertTrue(targets.contains(board.getCellAt(1, 13)));			

	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are MAGENTA on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(23, 5, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 4)));
		assertTrue(targets.contains(board.getCellAt(22, 4)));	
		assertTrue(targets.contains(board.getCellAt(18, 4)));	
		assertTrue(targets.contains(board.getCellAt(19, 5)));	
		assertTrue(targets.contains(board.getCellAt(21, 5)));	
		assertTrue(targets.contains(board.getCellAt(17, 5)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 3 away
		board.calcTargets(15, 0, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		//is door
		assertTrue(targets.contains(board.getCellAt(17, 1)));
		//other spaces
		assertTrue(targets.contains(board.getCellAt(16, 0)));
		assertTrue(targets.contains(board.getCellAt(15, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));
		assertTrue(targets.contains(board.getCellAt(16, 2)));		
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(5, 13, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 13)));
		assertTrue(targets.contains(board.getCellAt(6, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 12)));
		assertTrue(targets.contains(board.getCellAt(4, 13)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));
		assertTrue(targets.contains(board.getCellAt(2, 13)));
		assertTrue(targets.contains(board.getCellAt(3, 12)));
		assertTrue(targets.contains(board.getCellAt(4, 11)));
		assertTrue(targets.contains(board.getCellAt(5, 10)));
		assertTrue(targets.contains(board.getCellAt(6, 11)));
		assertTrue(targets.contains(board.getCellAt(7, 12)));
		assertTrue(targets.contains(board.getCellAt(7, 14)));				
		
	}

	// Test getting out of a room
	// These are BROWN on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(15, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		// Take two steps
		board.calcTargets(15, 8, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 9)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(13, 8)));
	}

}

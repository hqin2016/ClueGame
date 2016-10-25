package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class SSALGameSetupTests {

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
				board = Board.getInstance();
				board.setConfigFiles("SSAL_ClueLayout.csv", "SSAL_ClueLegend.txt", "SSAL_Weapons.txt", "SSAL_Players.txt");		
				board.initialize();
	}
	
	@Test
	public void testPeopleCards()
	{
		ArrayList<Card> deck = board.getDeck(); 
		assertTrue(deck.contains(new Card('P', "Thorin")));
		assertTrue(deck.contains(new Card('P', "Fili")));
		assertTrue(deck.contains(new Card('P', "Kili")));
		assertTrue(deck.contains(new Card('P', "Nori")));
		assertEquals(21, deck.size());
	}
	
	@Test
	public void testWeaponCards()
	{
		ArrayList<Card> deck = board.getDeck(); 
		assertTrue(deck.contains(new Card('W', "Cat")));
		assertTrue(deck.contains(new Card('W', "Bunny")));
		assertTrue(deck.contains(new Card('W', "AK47")));
		assertTrue(deck.contains(new Card('W', "Acid")));
		assertEquals(21, deck.size());
	}
	
	@Test
	public void testRoomCards()
	{
		ArrayList<Card> deck = board.getDeck(); 
		assertTrue(deck.contains(new Card('R', "Kitchen")));
		assertTrue(deck.contains(new Card('R', "Great Hall")));
		assertTrue(deck.contains(new Card('R', "Laboratory")));
		assertTrue(deck.contains(new Card('R', "Study")));
		assertEquals(21, deck.size());
	}
	
	@Test
	public void testPeople()
	{
		ArrayList<Player> players = board.getPlayers();  
		System.out.println(players.toString());
		assertTrue(players.contains(new Player("Thorin", 15, 1, Color.blue)));
		assertTrue(players.contains(new Player("Fili", 22, 5, Color.red)));
		assertTrue(players.contains(new Player("Dori", 11, 21, Color.orange)));
		assertTrue(players.contains(new Player("Nori", 1, 21, Color.gray)));
		assertEquals(6, players.size());
	}
	
	@Test
	public void testWeapons()
	{
		ArrayList<String> weapons = board.getWeapons(); 
		assertTrue(weapons.contains("Cat"));
		assertTrue(weapons.contains("Bunny"));
		assertTrue(weapons.contains("Bird"));
		assertTrue(weapons.contains("AK47"));
		assertEquals(6, weapons.size());
		
	}
	
	
}

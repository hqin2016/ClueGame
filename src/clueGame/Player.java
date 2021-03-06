package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Set;

public class Player {

	private String playerName;
	private Color color; 
	protected int row; 
	protected int column; 
	protected ArrayList<Card> hand; 
	
	public Player(String playerName,int row, int column, Color color)
	{
		hand = new ArrayList<>(); 
		this.playerName = playerName; 
		this.row = row;
		this.column = column; 
		this.color = color;
	}

	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(column*ClueGame.CELL_PIXEL_SIZE, row*ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE);
		g.setColor(Color.BLACK);
		g.drawOval(column*ClueGame.CELL_PIXEL_SIZE, row*ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE, ClueGame.CELL_PIXEL_SIZE);
	}
	
	public Solution moveToSpot(BoardCell spot, Board board, boolean onlyMove)
	{
		this.row = spot.getRow();
		this.column = spot.getColumn();
		return null;
	}
	
	public Solution makeAccusation(Board board)
	{
		return null;
	}
	
	public Solution makeAccusation(Board board, Solution accusation)
	{
		board.checkAccusation(accusation);
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		Player other = (Player) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (column != other.column)
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	public Solution makeSuggestion(Board board) {
		return null; 
	}
	
	public Card disproveSuggestion(Board board, Solution suggestion) { 
		return null;
	}
	
	public ArrayList<Card> getHand() {
		return hand; 
	}
	
	public String getName() {
		return playerName;
	}
	
	public BoardCell getMove(Set<BoardCell>targets) {
		return null;
	}
	
	public int getRow(){
		return row; 
	}
	
	public int getCol(){
		return column; 
	}
	
	public void setHand(ArrayList<Card> newHand) {
		hand = new ArrayList<Card>(newHand);
	}
	
	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", color=" + color + ", row=" + row + ", column=" + column
				+ ", hand=" + hand + "]";
	}

}

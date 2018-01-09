package piece;

import java.util.ArrayList;
import java.util.Scanner;

import chess.*;

public class Pawn extends Piece 
{	
	private short dir;
	private final Cell orig;	//The original cell of the pawn.
	public Pawn(String col, Cell cell)  throws Exception
	{	super(col, cell);
		if(col == "White")		//White pawns can move only forward.
			dir = 1;
		else					//Black pawns can move only backwards.
			dir = -1;
		this.orig = cell;
	}
	
	public String toString()
	{
		return colour.charAt(0)+"P";
	}
	
	/* A pawn can move only to a direction 'dir' fixed by its color, 
	 * it can go to immediate next column (in that direction), and 
	 * move straight or one step diagonally to kill a piece.
	 * 
	 * It can also move 2 steps in an initial move.
	 * */
	
	protected ArrayList<Cell> getAllMoves(Board board) 
	{
		this.moves = new ArrayList<Cell>();
		//Case 1: Normal move 
		if(board.colourAt((char)(currentPos.row + this.dir), 
			currentPos.col)	== null)
		{	
			moves.add(board.getCellAt((char)(currentPos.row + this.dir), 
				currentPos.col));
		}
		
		//Case 2: killing move.
		if(board.colourAt((char)(currentPos.row + this.dir), 
			(char)(currentPos.col-1))	!= this.colour)
		{	
			moves.add(board.getCellAt((char)(currentPos.row + this.dir), 
				(char)(currentPos.col-1)));
		}
		if(board.colourAt((char)(currentPos.row + this.dir), 
			(char)(currentPos.col+1))	!= this.colour)
		{	
			moves.add(board.getCellAt((char)(currentPos.row + this.dir), 
				(char)(currentPos.col+1)));
		}
		
		//Case 3: initial move.
		//Both, destination and middle cell should be empty.
		if( currentPos.equals(orig) && 
			board.colourAt((char)(currentPos.row + 2*this.dir), 
				currentPos.col)	== null &&
			board.colourAt((char)(currentPos.row + this.dir), 
				currentPos.col)	== null)
		{	
			moves.add(board.getCellAt((char)(currentPos.row + 2*this.dir), 
				currentPos.col));
		}
	
		return this.moves;
	}
	
	/* A pawn can get upgraded to a higher piece (Queen or Knight 
	 * or Rook or Bishop) if it reaches the terminal cell of any column.
	 * */
	@Override
	public boolean moveTo(Cell dest, Board board)
	{
		if(dest.row == Board.rowMax && dest.getPiece()==null)
		{
			currentPos.setPiece(null);
			
			System.out.println("You can update your piece.");
			System.out.println("Enter Q for Queen, R for rook, K for knight and B for bishop.");
			Scanner sc = new Scanner(System.in);
			char newPiece = sc.nextLine().charAt(0);
			while(newPiece!='Q' && newPiece!='R' && newPiece!='K' && newPiece!='B')
			{
				newPiece = sc.nextLine().charAt(0);
			}
			sc.close();
			try 
			{
				if(newPiece == 'Q')
				{	
					dest.setPiece(new Queen(this.colour, dest));
				}
				else if(newPiece == 'K')
				{	
					dest.setPiece(new Knight(this.colour, dest));
				}
				else if(newPiece == 'R')
				{	
					dest.setPiece(new Rook(this.colour, dest));
				}
				else if(newPiece == 'B')
				{	
					dest.setPiece(new Bishop(this.colour, dest));
				}
			}
			catch(Exception e)
			{
				System.out.println("Something went wrong while "
					+ "upgrading the pawn to a higher piece.");
			}
			currentPos = null;
			//i.e. this pawn is to be destructed.
			return true;
		}
		else
			return super.moveTo(dest, board);
	}
	
}

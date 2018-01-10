package piece;

import java.util.ArrayList;
import java.util.Scanner;

import chess.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Pawn extends Piece 
{	
	private short dir;
	private final Cell orig;	//The original cell of the pawn.
	public Pawn(String col, Cell cell)  throws Exception
	{	super(col, cell);
		if(col == Board.White)		//White pawns can move only forward.
			dir = 1;
		else					//Black pawns can move only backwards.
			dir = -1;
		this.orig = cell;
	}
	
	/**	
	 * Returns the original cell of the pawn.
	 * */
	public Cell getOrig()
	{	return this.orig;
	}
	
	/**	
	 * Returns the direction of the pawn.
	 * A pawn can move only in the direction 'dir' fixed by its colour, 
	 * I.e. 1 for white pawns, -1 for black pawns.
	 * */
	public short getDir()
	{	return this.dir;
	}
	
	/**
	 * Returns the pawn as a string.
	 * @return "WP" for a white pawn. Returns "BP" otherwise.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"P";
	}
	
	/** 
	 * A pawn can move only in the direction 'dir' fixed by its colour, 
	 * it can go to immediate next column (in that direction), and 
	 * move straight or one step diagonally to kill a piece.
	 * 
	 * It can also move 2 steps in an initial move.
	 * */
	@Override
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
		//The diagonally opposite cells must be occupied by a piece
		//of the opposite colour, for the pawn to attack it.
		if(	board.colourAt((char)(currentPos.row + this.dir), 
				(char)(currentPos.col-1))	!= null		
		&&	board.colourAt((char)(currentPos.row + this.dir), 
			(char)(currentPos.col-1))	!= this.colour)
		{	
			moves.add(board.getCellAt((char)(currentPos.row + this.dir), 
				(char)(currentPos.col-1)));
		}
		if(	board.colourAt((char)(currentPos.row + this.dir), 
			(char)(currentPos.col+1))	!= null
		&&	board.colourAt((char)(currentPos.row + this.dir), 
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
	
	/**
	 * A pawn can get upgraded to a higher piece (Queen or Knight 
	 * or Rook or Bishop) if it reaches the terminal cell of any column.
	 * Returns false if either of the arguments are null.
	 * */
	@Override
	public boolean moveTo(Cell dest, Board board)
	{
		if(board == null || dest == null)
			return false;
		
		// If the destination cell is one of the terminal cells of a row,
		// and, it is empty, and pawn can move to it,
		// prompt the user for the piece it can upgrade to.
		if((dest.row == Board.rowMax || dest.row == Board.rowMin)
			&& dest.getPiece() == null
			&& this.canMoveTo(dest, board))
		{
			currentPos.setPiece(null);
			
			/*System.out.println("You can update your piece.");
			System.out.println("Enter Q for Queen, R for rook, K for "
					+ "knight and B for bishop.");
			Scanner sc = new Scanner(System.in);
			char newPiece = sc.nextLine().charAt(0);
			while(newPiece!='Q' && newPiece!='R' && newPiece!='K' 
					&& newPiece!='B')
			{
				newPiece = sc.nextLine().charAt(0);
			}
			sc.close();*/
			char newPiece = 'Q';
			try 
			{
				if(newPiece == 'Q')
				{	
					new Queen(this.colour, dest);
				}
				/*else if(newPiece == 'K')
				{	
					new Knight(this.colour, dest);
				}
				else if(newPiece == 'R')
				{	
					new Rook(this.colour, dest);
				}
				else if(newPiece == 'B')
				{	
					new Bishop(this.colour, dest);
				}*/
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

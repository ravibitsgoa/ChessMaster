package piece;

import java.util.Scanner;

import chess.*;

public class Pawn extends Piece {
	
	private short dir;
	private final Cell orig;	//The original cell of the pawn.
	public Pawn(String col, Cell cell)
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
	public boolean canMoveTo(Cell dest, Board board)
	{	
		//System.out.println(dest.col+" "+dest.row);
		//Case 1: Normal move or killing move.
		if(  (dest.row == (char)(currentPos.row + this.dir)) && 
			((dest.col == currentPos.col && dest.getPiece()==null)
			|| ((dest.col == currentPos.col -1 || dest.col == currentPos.col +1)
			 && dest.getPiece()!=null && dest.getPiece().getColour()!=this.colour))
		  )
		{	
			return true;
		}
		//Case 2: initial move.
		//Both, destination and middle cell should be empty.
		else if(dest.col == currentPos.col && currentPos == orig 
				&& dest.row == (char)(orig.row + 2*this.dir)
				&& dest.getPiece() == null 
				&& board.colourAt((char)(orig.row + this.dir), orig.col)==null)
		{	
			return true;
		}
		else
			return false;
	}
	
	/* A pawn can get upgraded to a higher piece (Queen or Knight 
	 * or Rook or Bishop) if it reaches the terminal cell of any column.
	 * */
	@Override
	protected boolean moveTo(Cell dest, Board board)
	{
		if(dest.row == orig.row+6)
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
			currentPos = null;
			return true;
		}
		else
			return super.moveTo(dest, board);
	}
	
}

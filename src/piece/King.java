package piece;

import chess.*;
import java.util.ArrayList;

/**
 * @author Ravishankar P. Joshi
 * */
public class King extends Piece 
{	
	private boolean inInitialState;
	//This flag is true if king has not moved yet from the start.
	private boolean kingSideCastling, queenSideCastling;
	//These flags denote whether castling is possible in the next move.
	
	public King(String col, Cell cell) throws Exception
	{	
		super(col, cell);
		inInitialState = true;
		kingSideCastling = false;
		queenSideCastling = false;
	}
	
	/**
	 * Returns the string representation of a king.
	 * @return WK for white king. BK for black king.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"K";
	}
	
	/**
	 * @return the cell containing this king.
	 * */
	public Cell getCell()
	{
		return this.currentPos;
	}
	
	/** 
	 * A King can move only to immediately adjacent (at most 8) cells.
	 * Also, the destination cell must not be under attack, right now.
	 * (King doesn't want himself to be killed.) :P
	 * King needs to know whole board so that it can call 
	 * isUnderAttack() method of the board while moving around.
	 * */
	@Override
	public ArrayList<Cell> getAllMoves(Board board)
	{
		this.moves = new ArrayList<Cell>();	//Make the list of moves empty.
		
		if(inInitialState)
		{
			{	
				//If the king and either of the rooks are in initial state,
				//and nothing is between them, they can castle.
				int dir=-1;
				boolean canCastle = true;
				char cc= this.getCell().col, row =this.getCell().row, col;
				System.out.println(cc+" "+row);
				for(col = (char)(cc+dir); 
						col>Board.colMin && col<Board.colMax; col+=dir)
				{
					if(board.getCellAt(row, col) == null)
					{	System.out.println("Exception in castling King."
							+row+col);
					}
					if(board.colourAt(row, col) != null)
					{
						canCastle = false;
					}
					if(board.isUnderAttack(row, col, this))
					{
						canCastle = false;
					}	//if any of the middle cells are in check,
					//the king can't castle.
				}

				Piece pieceOnRookCell = board.getCellAt(row, col).getPiece();
				if(canCastle && pieceOnRookCell != null &&
					pieceOnRookCell instanceof Rook)
				{
					Rook rook = (Rook) pieceOnRookCell;
					if(rook.canCastle())
					{
						kingSideCastling = true;
						this.moves.add(board.getCellAt(row, col-dir));
					}
				}
			}
			
			{	
				int dir=1;
				boolean canCastle = true;
				char cc= this.getCell().col, row =this.getCell().row, col;
				for(col = (char)(cc+dir); 
						col>Board.colMin && col<Board.colMax; col+=dir)
				{
					if(board.getCellAt(row, col) == null)
					{	System.out.println("Exception in castling King."
											+row+col);
					}
					if(board.colourAt(row, col) != null)
					{
						canCastle = false;
					}
					if(board.isUnderAttack(row, col, this))
					{
						canCastle = false;
					}	//if any of the middle cells are in check,
					//the king can't castle.
				}

				Piece pieceOnRookCell = board.getCellAt(row, col).getPiece();
				if(canCastle && pieceOnRookCell != null &&
					pieceOnRookCell instanceof Rook)
				{
					Rook rook = (Rook) pieceOnRookCell;
					if(rook.canCastle())
					{
						queenSideCastling = true;
						this.moves.add(board.getCellAt(row, col-2*dir));
					}
				}
			}
		}
		final char cr = currentPos.row,	cc= currentPos.col;
		
		for(char row = (char)(cr-1); row<=cr+1; row++)
		{	for(char col=(char) (cc-1); col<=cc+1; col++)
			{
				Cell dest = board.getCellAt(row, col);
				if(dest == null || dest.equals(currentPos))
					continue;
			
				if( board.colourAt(dest.row, dest.col) != this.colour )
				{
					Cell thisCell = this.getCell();
					thisCell.setPiece(null);
					
					//Store the piece on the cell in a temporary variable.
					Piece currentlyOnDest = dest.getPiece();
					dest.setPiece(this);
					
					if(!board.isUnderAttack(dest.row, dest.col, this))
					{
						moves.add(dest);
					}

					thisCell.setPiece(this);
					dest.setPiece(currentlyOnDest);
				}
			}
		}
		
		return this.moves;
	}
	
	/**
	 * Calls the parent class moveTo method.
	 * If a move is made, it states inInitialState and castling flags
	 * to false. So that one can't castle after a move.
	 * 
	 * If the destination cell is a castling cell, moveTo() castles the king.
	 * 
	 * @return true if the king can move to such a cell in given board.
	 * False otherwise.
	 * */
	@Override
	public boolean moveTo(Cell dest, Board board)
	{
		boolean moved = super.moveTo(dest, board);
		if(moved)
		{	
			inInitialState = false;
			kingSideCastling = false;
			queenSideCastling = false;
		}
		return moved;
	}
	
	public boolean canKingSideCastle()
	{
		return kingSideCastling;
	}
	
	public boolean canQueenSideCastle()
	{
		return queenSideCastling;
	}
	
}

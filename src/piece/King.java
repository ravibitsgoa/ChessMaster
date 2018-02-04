package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class King extends Piece 
{	
	private boolean inInitialState;
	//This flag is true if king has not moved yet from the start.
	private boolean kingSideCastling, queenSideCastling;
	//These flags denote whether castling is possible in the next move.
	
	public King(String col) throws Exception
	{	
		super(col);
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
	 * Calls the parent class moveTo method.
	 * If a move is made, it states inInitialState and castling flags
	 * to false. So that one can't castle after a move.
	 * 
	 * If the destination cell is a castling cell, moveTo() castles the king.
	 * 
	 * @return true if the king can move to such a cell in given board.
	 * False otherwise.
	 * */
	/*@Override
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
	}*/
	
	public boolean canKingSideCastle()
	{
		return kingSideCastling;
	}
	
	public boolean canQueenSideCastle()
	{
		return queenSideCastling;
	}
/*
	@Override
	public ArrayList<Cell> getAllMoves(Movement movement) 
	{
		ArrayList<Cell> moves = new ArrayList<Cell>();	//Make the list of moves empty.
		
		if(inInitialState)
		{
			{	
				//If the king and either of the rooks are in initial state,
				//and nothing is between them, they can castle.
				int dir=-1;
				boolean canCastle = true;
				char cc= this.getCell().col, row =this.getCell().row, col;
				//System.out.println(cc+" "+row);
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

				Cell rookCell = board.getCellAt(row, col);
				if(rookCell != null)
				{	Piece pieceOnRookCell = rookCell.getPiece();
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
		return moves;
	}
*/	
}

package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class Rook extends Piece 
{
	private boolean inInitialState;
	public Rook(String col) throws Exception
	{	super(col);
		inInitialState = true;
	}
	
	/**
	 * Returns the rook as a string.
	 * @return "WR" for a white rook. Returns "BR" otherwise.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"R";
	}
	
	public boolean canCastle()
	{
		return inInitialState;
	}
	
	/** 
	 * A piece can move to dest cell only if 
	 * 1. it's either empty or
	 * 2. occupied by a piece of opposite colour.
	 * */
	/*@Override
	public ArrayList<Cell> getAllMoves(Movement movement) 
	{
		ArrayList<Cell> moves = new ArrayList<Cell>();
		
		
		if(this.canCastle())
		{
			King king = board.getKing(this.colour);
			
			if( king!=null && king.canKingSideCastle() &&
				this.currentPos.col < king.currentPos.col)
			{
				char row = this.currentPos.row, col= this.currentPos.col;
				moves.add(board.getCellAt(row, col+2));
			}
			else if( king!= null && king.canQueenSideCastle() &&
					this.currentPos.col > king.currentPos.col)
			{
				char row = this.currentPos.row, col= this.currentPos.col;
				moves.add(board.getCellAt(row, col-3));
			}

		}
		
		return moves;
	}*/
	/**
	 * Castles the rook by moving it to appropriate cell.
	 * @return true if castling is completed,
	 * false otherwise.
	 * */
	/*
	public boolean castle(Board board)
	{
		if(this.canCastle())
		{
			King king = board.getKing(this.colour);
			
			char row = this.currentPos.row, col= this.currentPos.col;
			if(	//king.canQueenSideCastle() &&
				this.currentPos.col < king.currentPos.col)
			{	
				this.currentPos.setPiece(null);
				Cell dest = (board.getCellAt(row, col+2));
				this.currentPos = dest;
				dest.setPiece(this);
				return true;
			}
			else if(//king.canKingSideCastle() &&
					this.currentPos.col > king.currentPos.col)
			{
				this.currentPos.setPiece(null);
				Cell dest = (board.getCellAt(row, col-3));
				this.currentPos = dest;
				dest.setPiece(this);
				return true;
			}
			else
				System.out.println("Exception in castle() of Rook");
		}
		return false;
	}*/
}

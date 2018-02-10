package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class Rook extends Piece 
{
	public Rook(String col) throws Exception
	{	super(col);
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
	
	/**
	 * Castles the rook by moving it to appropriate cell.
	 * @return true if castling is completed,
	 * false otherwise.
	 * */
	
}

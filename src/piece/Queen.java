package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class Queen extends Piece 
{	
	public Queen(String col) throws Exception
	{	super(col);
	}
	
	/**
	 * Returns the queen as a string.
	 * @return "WQ" for a white queen. Returns "BQ" otherwise.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"Q";
	}

}

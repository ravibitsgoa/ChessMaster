package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class King extends Piece 
{	
	public King(String col) throws Exception
	{	
		super(col);
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
	
}

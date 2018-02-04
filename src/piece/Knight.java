package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class Knight extends Piece 
{
	public Knight(String col) throws Exception
	{	super(col);
	}
	
	/**
	 * Returns the string representation of a Knight.
	 * @return WN for white knight. BN for black knight.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"N";
	}
}

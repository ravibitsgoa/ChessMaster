package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class Bishop extends Piece 
{	
	public Bishop(String col) throws Exception
	{	super(col);
	}
	
	/**
	 * Returns the string representation of the bishop.
	 * @return WB for a white bishop.
	 * BB for a black bishop.
	 * */
	@Override
	public String toString()
	{
		return colour.charAt(0)+"B";
	}
}

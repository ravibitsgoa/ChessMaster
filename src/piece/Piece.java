package piece;

/**
 * @author Ravishankar P. Joshi
 * */
public abstract class Piece
{
	public final String colour;
	
	/**
	 * Constructor of this abstract class to avoid repetition 
	 * 
	 * @throws null cell exception
	 * @throws cell non-empty exception
	 * @throws null colour exception
	 **/
	public Piece(String col) throws Exception
	{	
		if(col == null)
			throw new Exception("Colour null exception.");
		
		this.colour = col;
	}
	
	/**
	 * An abstract method to be over-ridden by child classes.
	 * */
	public abstract String toString();
	
	/**
	 * @return the colour of the piece as a string.
	 * */
	public String getColour() 
	{
		return colour;
	}
	
}

package piece;
import chess.*;

public abstract class Piece 
{
	protected String colour;
	protected Cell currentPos;
	
	protected abstract boolean moveTo(Cell c);
	
}

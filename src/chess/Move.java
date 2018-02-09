package chess;

import piece.King;
import piece.Pawn;
import piece.Piece;

public class Move 
{
	private final Cell source, destination;
	private final Piece onSource, onDestination;
	public final String moveType;
	
    public Move( Cell source, Cell destination, Piece onSource, 
    			 Piece onDestination, String moveType) 
    {
        this.source = source;
        this.destination = destination;
        this.onSource = onSource;
        this.onDestination = onDestination;
        this.moveType = moveType;
    }

    public Cell getSource() 
    {
        return source;
    }

    public Cell getDestination() 
    {
        return destination;
    }
    
    public Piece getSourcePiece()
    {
    	return onSource;
    }
    
    public Piece getDestinationPiece()
    {
    	return onDestination;
    }
    
    @Override
    public String toString()
    {
    	String moveString = "";
		if(moveType == Movement.castling)
		{
			if(onSource instanceof King)
			{
				if(destination.col == Board.colMax-1)
				{	//castling king-side, king moved to g column.
					return "o-o";
				}
				else
				{	//castling queen-side, king moved to c column.
					return "o-o-o";
				}
			}
			else	//piece is a rook.
			{
				if(destination.col == source.col-2)
				{	//castling king-side, rook moved 2 steps.
					return "o-o";
				}
				else
				{	//castling queen-side, rook moved 3 steps.
					return "o-o-o";
				}
			}
		}
		else
		{	//Pawns are not written in move notations.
			if(!(onSource instanceof Pawn))
				moveString += onSource.toString().charAt(1);
			if(onDestination != null)
				moveString += "x";	//killing move.
			moveString+= destination.toString();
		
			return moveString;
		}
    }
}

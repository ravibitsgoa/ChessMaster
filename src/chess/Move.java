package chess;

import piece.Pawn;
import piece.Piece;

public class Move 
{
	private Cell source, destination;
	private Piece onSource, onDestination;
	
    public Move( Cell source, Cell destination, Piece onSource, Piece onDestination) 
    {
        this.source = source;
        this.destination = destination;
        this.onSource = onSource;
        this.onDestination = onDestination;
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
		//Pawns are not written in move notations.
		if(!(onSource instanceof Pawn))
			moveString += onSource.toString().charAt(1);
		if(onDestination != null)
			moveString += "x";	//killing move.
		moveString+= destination.toString();
		
		return moveString;
    }
}

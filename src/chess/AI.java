package chess;

import java.util.ArrayList;

import piece.*;

public class AI 
{
	private Board board;
	private String colour;
	public AI(Board board, String colour) throws Exception
	{
		//System.out.println(colour);
		if(colour!= Board.White && colour!=Board.Black)
			throw new Exception("Colour invalid exception in AI class.");
		if(board == null)
			throw new Exception("Board empty exception in AI class.");
		
		this.board = board;
		this.colour = colour;
	}
	
	private int valueOf(Piece piece)
	{
		if(piece instanceof King)
			return 900;
		else if(piece instanceof Queen)
			return 90;
		else if(piece instanceof Rook)
			return 50;
		else if(piece instanceof Bishop)
			return 30;
		else if(piece instanceof Knight)
			return 30;
		else if(piece instanceof Pawn)
			return 10;
		else
			return 0;
	}
	
	private int evaluate (Board board)
	{
		int valueOfBoard= 0;
		ArrayList<Piece> ownPieces = board.getPieces(colour);
		ArrayList<Piece> oppPieces = board.getPieces(Board.opposite(colour));
		int ownPieceValue= 0;
		for(Piece ownPiece : ownPieces)
		{
			ownPieceValue += valueOf(ownPiece);			
		}
		int oppPieceValue = 0;
		for(Piece oppPiece: oppPieces)
		{	
			oppPieceValue += valueOf(oppPiece);			
		}
		valueOfBoard = ownPieceValue - oppPieceValue;
		return valueOfBoard;
	}
	
	public void playNextMove()
	{
		Cell from = null, to=null;
		Cell[] move = getNextMove();
		from = move[0];
		to = move[1];
		board.clicked(from.row, from.col);
		board.clicked(to.row, to.col);
	}
	
	private Cell[] getNextMove()
	{
		Cell from =null, to = null;
		final int inf = 10000000;
		int maxValue = -inf;
		ArrayList<Piece> ownPieces = board.getPieces(colour);
		//for(Piece own : ownPieces)
		//	System.out.println(own);
		
		for(Piece ownPiece: ownPieces)
		{	
			Cell thisCell = ownPiece.getCell();
			ArrayList<Cell> moves = ownPiece.getAllMoves(board);
			for(Cell dest: moves)
			{
				//System.out.print(dest);
				Piece currentlyOnDest = dest.getPiece();
				//System.out.println(currentlyOnDest);
				if(	currentlyOnDest != null &&
					currentlyOnDest.getColour() == colour)
					continue;
				
				board.killPieceAt(dest);	//remove the piece at dest.
				dest.setPiece(ownPiece);	//set piece on destination to this.
				thisCell.setPiece(null);	//empty the current cell.
				
				int value = evaluate(board);
				//System.out.print(value);
				if(maxValue < value)
				{
					maxValue = value;
					from = thisCell;
					to = dest;
				}
				dest.setPiece(currentlyOnDest);
				thisCell.setPiece(ownPiece);
				board.addPiece(currentlyOnDest);
			}
			//System.out.println(ownPiece);
		}
		
		Cell[] move = {from, to};
		return move;
	}
	
	public String getColour()
	{
		return this.colour;
	}
}

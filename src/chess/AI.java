package chess;

import java.util.ArrayList;

import piece.*;

public class AI 
{
	private Board board;
	private String colour;
	private Cell[] bestMove;
	public AI(Board board, String colour) throws Exception
	{
		//System.out.println(colour);
		if(colour!= Board.White && colour!=Board.Black)
			throw new Exception("Colour invalid exception in AI class.");
		if(board == null)
			throw new Exception("Board empty exception in AI class.");
		
		this.board = board;
		this.colour = colour;
		System.out.println(colour);
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
			if(board.isKilled(ownPiece))
				continue;
			ownPieceValue += valueOf(ownPiece);			
		}
		int oppPieceValue = 0;
		for(Piece oppPiece: oppPieces)
		{	
			if(board.isKilled(oppPiece))
				continue;
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
	
	/**
	 * This tries to maximize (as +ve as possible) the board value 
	 * for itself (AI colour) and
	 * minimize (i.e. as negative as possible) for the other player.
	 * i.e. it finds best move for itself.
	 * */
	private int minimax(int depth, Board board, String playerColour)
	{
		if(depth == 0)
			return -evaluate(board);
		else if(playerColour == this.colour)
		{	
			final int inf = 10000000;
			Cell from = null, to = null;
			int maxValue = -inf;
			ArrayList<Piece> ownPieces = board.getPieces(playerColour);
			
			for(Piece ownPiece: ownPieces)
			{	
				if(board.isKilled(ownPiece))
					continue;
				
				Cell thisCell = ownPiece.getCell();
				ArrayList<Cell> moves = ownPiece.getAllMoves(board);
				for(Cell dest: moves)
				{
					Piece currentlyOnDest = dest.getPiece();
					if(	currentlyOnDest != null &&
						currentlyOnDest.getColour() == colour)
						assert(false);
					
					board.killPieceAt(dest);	//remove the piece at dest.
					//dest.setPiece(ownPiece);	//set piece on destination to this.
					//thisCell.setPiece(null);	//empty the current cell.
					ownPiece.moveTo(dest, board);
					
					int value = minimax(depth-1, board, Board.opposite(playerColour));
					if(maxValue < value)
					{
						maxValue = value;
						from = thisCell;
						to = dest;
					}
					ownPiece.undoMove();
					dest.setPiece(currentlyOnDest);
					//thisCell.setPiece(ownPiece);
					board.addPiece(currentlyOnDest);
				}
			}
			Cell[] move = {from, to};
			if(depth == 2)
				bestMove = move;
			return maxValue;
		}
		else
		{
			final int inf = 10000000;
			//Cell from = null, to = null;
			int maxValue = inf;
			ArrayList<Piece> oppPieces = board.getPieces(playerColour);
			
			for(Piece oppPiece: oppPieces)
			{	
				if(board.isKilled(oppPiece))
					continue;
				
				Cell thisCell = oppPiece.getCell();
				ArrayList<Cell> moves = oppPiece.getAllMoves(board);
				for(Cell dest: moves)
				{
					Piece currentlyOnDest = dest.getPiece();
					if(	currentlyOnDest != null &&
						currentlyOnDest.getColour() == colour)
						continue;
					
					board.killPieceAt(dest);	//remove the piece at dest.
					dest.setPiece(oppPiece);	//set piece on destination to this.
					thisCell.setPiece(null);	//empty the current cell.
					
					int value = minimax(depth-1, board, Board.opposite(playerColour));
					if(maxValue > value)
					{
						maxValue = value;
						//from = thisCell;
						//to = dest;
					}
					dest.setPiece(currentlyOnDest);
					thisCell.setPiece(oppPiece);
					board.addPiece(currentlyOnDest);
				}
			}
			return maxValue;
		}
	}
	
	private Cell[] getNextMove()
	{
		this.minimax(2, board, this.colour);
		//Cell[] move = {from, to};
		return bestMove;
	}
	
	public String getColour()
	{
		return this.colour;
	}
}

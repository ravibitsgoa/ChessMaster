package chess;

import java.util.ArrayList;

import piece.*;

public class AI 
{
	private final Board board;
	private final String AIcolour;
	private Move bestMove;
	private final Movement movement;
	public AI(Board board, String colour, Movement movment) 
			throws Exception
	{
		//System.out.println(colour);
		if(colour!= Board.White && colour!=Board.Black)
			throw new Exception("Colour invalid exception in AI class.");
		if(board == null)
			throw new Exception("Board empty exception in AI class.");
		
		this.board = board;
		this.AIcolour = colour;
		this.movement = movment;
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
		ArrayList<Piece> ownPieces = board.getPieces(AIcolour);
		int ownPieceValue= 0;
		for(Piece ownPiece : ownPieces)
		{
			if(board.isKilled(ownPiece))
				continue;
			ownPieceValue += valueOf(ownPiece);			
		}
		int oppPieceValue = 0;
		ArrayList<Piece> oppPieces = board.getPieces(Board.opposite(AIcolour));
		for(Piece oppPiece: oppPieces)
		{	
			if(board.isKilled(oppPiece))
				continue;
			oppPieceValue += valueOf(oppPiece);			
		}
		valueOfBoard = oppPieceValue - ownPieceValue;
		//System.out.printf("%d ",valueOfBoard);
		return valueOfBoard;
	}
	
	public void playNextMove()
	{
		Cell from = null, to=null;
		Move move = getNextMove();
		from = move.getSource();
		to = move.getDestination();
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
		else if(playerColour == this.AIcolour)
		{	
			final int inf = 10000000;
			Move move = null;
			int maxValue = -inf;
			ArrayList<Piece> ownPieces = board.getPieces(playerColour);
			
			for(Piece ownPiece: ownPieces)
			{	
				if(board.isKilled(ownPiece))
					continue;
				
				ArrayList<Cell> moves = movement.getAllMoves(ownPiece);
				for(Cell dest: moves)
				{
					Move tempMove = movement.moveTo(ownPiece, dest);
					
					int value = minimax(depth-1, board, 
							Board.opposite(playerColour));
					System.out.printf("%d ", value);
					if(maxValue < value)
					{
						maxValue = value;
						move = tempMove;
					}
					movement.undoMove();
				}
			}
			if(depth == 4)
				bestMove = move;
			System.out.println();
			System.out.printf("best: %d\n",maxValue);
			return maxValue;
		}
		else
		{
			final int inf = 10000000;
			Move move = null;
			int minValue = inf;
			ArrayList<Piece> oppPieces = board.getPieces(playerColour);
			
			for(Piece oppPiece: oppPieces)
			{	
				if(board.isKilled(oppPiece))
					continue;
				
				ArrayList<Cell> moves = movement.getAllMoves(oppPiece);
				for(Cell dest: moves)
				{
					Move tempMove = movement.moveTo(oppPiece, dest);
					
					int value = minimax(depth-1, board, Board.opposite(playerColour));
					if(minValue > value)
					{
						minValue = value;
						move = tempMove;
					}
					movement.undoMove();
				}
			}
			return minValue;
		}
	}
	
	private Move getNextMove()
	{
		this.minimax(4, board, this.AIcolour);
		return bestMove;
	}
	
	public String getColour()
	{
		return this.AIcolour;
	}
}

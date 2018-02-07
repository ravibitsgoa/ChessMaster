package chess;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import piece.*;

public class AI 
{
	private final Board board;
	private final String AIcolour;
	private Move bestMove;
	private final Movement movement;
	private final int startingDepth;
	private int nodeCount;
	
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
		startingDepth = 4;
		System.out.println(colour);
		nodeCount=0;
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
		CopyOnWriteArrayList<Piece> ownPieces = board.getPieces(AIcolour);
		int ownPieceValue= 0;
		for(Piece ownPiece : ownPieces)
		{
			if(board.isKilled(ownPiece))
				continue;
			ownPieceValue += valueOf(ownPiece);			
		}
		int oppPieceValue = 0;
		CopyOnWriteArrayList<Piece> oppPieces = board.getPieces(Board.opposite(AIcolour));
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
	private int minimax(int depth, Board board, String playerColour, 
						int alpha, int beta)
	{
		if(depth == 0)
		{
			if(playerColour == AIcolour)
				return -evaluate(board);
			else
				return evaluate(board);
		}
		//if it's playing for itself, it will maximize board value (heuristic).
		else if(playerColour == this.AIcolour)
		{	
			Move move = null;
			CopyOnWriteArrayList<Piece> ownPieces = board.getPieces(playerColour);
			
			for(Piece ownPiece: ownPieces)
			{	
				if(board.isKilled(ownPiece))
					continue;
				
				ArrayList<Cell> moves = movement.getAllMoves(ownPiece);
				for(Cell dest: moves)
				{
					Move tempMove = movement.moveTo(ownPiece, dest);
					//System.out.println(ownPiece+" "+moves+" "+tempMove+" "+dest);
					if(tempMove == null)
					{	continue;
						//tempMove.getSource();
					}
					if(move == null)
						move = tempMove;
					int value = minimax(depth-1, board, 
							Board.opposite(playerColour), alpha, beta);
					//System.out.printf("%d ", value);
					nodeCount++;
					if(alpha < value)
					{
						alpha = value;
						move = tempMove;
					}
					movement.undoMove();

					if(alpha == beta)	//If we have already found the optimal path.
						break;	//no need to change anything else.
				}
				if(alpha == beta)	//If we have already found the optimal path.
					break;	//no need to change anything else.
			
			}
			if(depth == startingDepth)	//If we're back to the start node,
				bestMove = move;		//play the best known move.
			
			//System.out.println();
			//System.out.printf("best: %d\n",maxValue);
			return alpha;
		}
		//for the other player, it will minimize the value.
		else
		{
			Move move = null;
			CopyOnWriteArrayList<Piece> oppPieces = board.getPieces(playerColour);
			
			for(Piece oppPiece: oppPieces)
			{	
				if(board.isKilled(oppPiece))
					continue;
				
				ArrayList<Cell> moves = movement.getAllMoves(oppPiece);
				for(Cell dest: moves)
				{
					Move tempMove = movement.moveTo(oppPiece, dest);
					//System.out.println(oppPiece+" "+moves+" "+tempMove+" "+dest);
					if(tempMove == null)
					{	continue;
						//tempMove.getSource();
					}
					int value = minimax(depth-1, board, Board.opposite(playerColour), 
							alpha, beta);
					nodeCount++;
					if(move == null)
						move = tempMove;
					if(beta > value)
					{
						beta = value;
						move = tempMove;
					}
					movement.undoMove();

					if(alpha == beta)	//If we have already found the optimal path.
						break;	//No need to change anything else.
				}
				if(alpha == beta)	//If we have already found the optimal path.
					break;	//No need to change anything else.
			}
			return beta;
		}
	}
	
	private Move getNextMove()
	{
		long startTime = System.nanoTime();
		nodeCount=0;	//The number of visited nodes in the minimax tree.
		final int inf = 1000000;
		System.out.println(this.minimax(startingDepth, board, this.AIcolour, -inf, inf));
		System.out.println(nodeCount);	
		long stopTime = System.nanoTime();
		System.out.println((stopTime-startTime)/(1e9));	
		//Prints the number of seconds AI took for thinking.
		return bestMove;
	}
	
	public String getColour()
	{
		return this.AIcolour;
	}
}

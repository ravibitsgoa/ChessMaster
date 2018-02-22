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
	
	/**
	 * Sets board, movement object, AI's own colour and search depth of AI.
	 * Currently, AI search-depth is 4, which can be modified here.
	 * @throws invalid colour exception,
	 * @throws null board exception,
	 * @throws null movement object exception
	 * */
	
	public AI(Board board, String colour, Movement movment) 
			throws Exception
	{
		//System.out.println(colour);
		if(colour!= Board.White && colour!=Board.Black)
			throw new Exception("Colour invalid exception in AI class.");
		if(board == null)
			throw new Exception("Null Board exception in AI class.");
		if(movment == null)
			throw new Exception("Null movement exception in AI class.");
		
		this.board = board;
		this.AIcolour = colour;
		this.movement = movment;
		startingDepth = 4;
		System.out.println(colour);
		nodeCount=0;
	}
	
	/**
	 * @return value of pieces.
	 * 900 for a king, 90 for a queen, 50 for a rook, 30 for a bishop,
	 * 30 for a knight, 10 for a pawn.
	 * If it's none of these, returns 0.
	 * */
	
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
	
	/**
	 * Evaluates the passed board based on the material (piece) present.
	 * (Doesn't take into account useful positions like castling.)
	 * @return (total value of opponent pieces) - (total value of own pieces).
	 * */
	
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
	
	/**
	 * Calls getNextMove() to find the best next move, and
	 * Plays the next move, by clicking on the board.
	 * */
	
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
	 * This tries to maximise (as positive as possible) the board value
	 * for itself (AI colour) and
	 * minimise (i.e. as negative as possible) for the other player.
	 * i.e. it finds best move for itself.
	 * 
	 * Adds a value of 50 for castling move. Hence, AI tries to castle, and
	 * stop the human from castling.
	 * 
	 * Implements minimax and alpha-beta pruning algorithm, for given depth.
	 * @return the number of nodes of game tree visited for this move.
	 * */
	private int minimax(int depth, Board board, String playerColour, 
						int alpha, int beta)
	{
		if(depth == 0)
		{
			if(playerColour.equals(AIcolour))
				return -evaluate(board);
			else
				return evaluate(board);
		}
		//if it's playing for itself, it will maximize board value (heuristic).
		else if(playerColour.equals(this.AIcolour))
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
					nodeCount++;
					Move tempMove = movement.moveTo(ownPiece, dest);
					//System.out.println(ownPiece+" "+moves+" "+tempMove+" "+dest);
					if(tempMove == null)
					{	continue;
						//tempMove.getSource();
					}
					//if(move == null)
					//	move = tempMove;
					int value = minimax(depth-1, board, 
							Board.opposite(playerColour), alpha, beta);
					//System.out.printf("%d ", value);
					if(tempMove.moveType.equals(Movement.castlingMove))
					{	
						value+= 50;
					}
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
			//Move move = null;
			CopyOnWriteArrayList<Piece> oppPieces = board.getPieces(playerColour);
			
			for(Piece oppPiece: oppPieces)
			{	
				if(board.isKilled(oppPiece))
					continue;
				
				ArrayList<Cell> moves = movement.getAllMoves(oppPiece);
				for(Cell dest: moves)
				{
					nodeCount++;
					Move tempMove = movement.moveTo(oppPiece, dest);
					//System.out.println(oppPiece+" "+moves+" "+tempMove+" "+dest);
					if(tempMove == null)
					{	continue;
						//tempMove.getSource();
					}
					int value = minimax(depth-1, board, Board.opposite(playerColour), 
							alpha, beta);
					//if(move == null)
					//	move = tempMove;
					if(tempMove.moveType.equals(Movement.castlingMove))
					{	
						value-= 50;
					}
					if(beta > value)
					{
						beta = value;
						//move = tempMove;
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
	
	/**
	 * Prints the number of seconds AI took to find the move, and
	 * number of nodes of the game tree visited to find the move.
	 * @return best next move for AI.
	 * */
	
	private Move getNextMove()
	{
		long startTime = System.nanoTime();
		nodeCount=0;	//The number of visited nodes in the minimax tree.
		final int inf = 1000000;
		System.out.println(this.minimax(startingDepth, board, this.AIcolour, -inf, inf));
		System.out.println(nodeCount+" nodes visited.");	
		long stopTime = System.nanoTime();
		System.out.println((stopTime-startTime)/(1e9)+ " second(s)");	
		//Prints the number of seconds AI took for thinking.
		System.gc();
		return bestMove;
	}
	
	/**
	 * @return AI colour.
	 * */
	
	public String getColour()
	{
		return this.AIcolour;
	}
}

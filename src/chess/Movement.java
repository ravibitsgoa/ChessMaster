package chess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import piece.*;

public class Movement 
{
	private Board board;
	private HashMap<Cell, Piece> onCell;
	private HashMap<Piece, Cell> cellOf;
	private HashMap<Piece, ArrayList<Cell> > moves;
	Stack<Move> pastMoves;
	
	public Movement(Board board) throws Exception
	{
		if(board == null)
			throw new Exception("Null board exception in Movement class.");
		this.board = board;
		onCell = new HashMap<>();
		cellOf = new HashMap<>();
		moves = new HashMap<>();
		pastMoves = new Stack<>();
		
		for(char row=Board.rowMin; row<=Board.rowMax; row++)
		{
			for(char col=Board.colMin; col<=Board.colMax; col++)
			{
				onCell.put(board.getCellAt(row, col), null);
			}
		}
	}
	
	/**
	 * Undo the last move.
	 * Reincarnate the killed piece.
	 * */
	public void undoMove()
	{
		if(pastMoves.isEmpty())
			return;
		Move lastMove = pastMoves.peek();
		pastMoves.pop();
		
		Piece onSource = lastMove.getSourcePiece();
		Piece onDestination = lastMove.getDestinationPiece();
		Cell source = lastMove.getSource();
		Cell destination = lastMove.getDestination();

		board.reincarnate(onDestination);
		
		this.add(onDestination, destination);
		this.add(onSource, source);
		this.recomputeMoves(onDestination);
		this.recomputeMoves(onSource);
	}
	
	/** 
	 * Checks whether the Piece can be moved into cell dest or not.
	 * @return True if it can be moved to dest, it moves itself, 
	 * changes occupant Piece of the cells.
	 * (It calls canMoveTo method to decide this.)
	 * Returns true if the piece is a pawn to be promoted.
	 * Returns false otherwise, without any modifying anything.
	 * Returns false if either of the arguments are null.
	 * */
	/**
	 * A pawn can get upgraded to a higher piece (Queen or Knight 
	 * or Rook or Bishop) if it reaches the terminal cell of any column.
	 * Returns false if either of the arguments are null.
	 * */

	public Move moveTo(Piece ownPiece, Cell dest) 
	{
		return this.moveTo(cellOf.get(ownPiece), dest);
	}
	
	public Move moveTo(Cell from, Cell to)
	{
		if(	to == null || this.board == null || 
			from == null || board.isKilled(onCell.get(from)))
			return null;
		Piece pieceToMove = onCell.get(from);
		
		if( pieceToMove instanceof Pawn && (to.row == Board.rowMax ||
			to.row == Board.rowMin)	&& this.canMoveTo(pieceToMove, to) )
		{
			Move move = new Move(from, to, pieceToMove, onCell.get(to));
			pastMoves.add(move);
			//Note the current move.
			
			board.kill(pieceToMove);		//Kill the pawn which is to be promoted.
			onCell.put(from, null);			//empty the current position.
			//If destination cell contains anything,
			//make it empty.
			board.kill(onCell.get(to));
			try 
			{
				board.promotePawn((Pawn)pieceToMove, new Queen(pieceToMove.colour), to);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("Something went wrong while promoting the pawn");
			}
			return move;
		}
		
		if(this.canMoveTo(pieceToMove, to))
		{	
			Move move = new Move(from, to, pieceToMove, onCell.get(to));
			pastMoves.add(move);
			
			onCell.put(from, null);			//empty the current position.
			if(onCell.get(to) != null)		//if the destination contains a piece,
				board.kill(onCell.get(to));	//kill it.
			onCell.put(to, pieceToMove);	//fill the destination position.
			cellOf.put(pieceToMove, to);	//set position of this cell as dest.
			//this.recomputeMoves(pieceToMove);//find all moves reachable from here.
			return move;
		}
		else
			return null;
	}

	/**
	 * A rook can move only in same row/ column.
	 * @returns All moves along the same row/ column for a rook or Queen.
	 * otherwise returns null.
	 * */
	private ArrayList<Cell> getRookMoves(Piece piece)
	{
		//if(!(piece instanceof Rook || piece instanceof Queen) 
		//	|| piece == null)
		//	return null;
		
		ArrayList<Cell> rookMoves = new ArrayList<>();
		//If the rook is to be moved along a row.
		rookMoves.addAll(this.movesInDir(piece, 1, 0));
		rookMoves.addAll(this.movesInDir(piece, -1, 0));
		
		//If the rook is to be moved along a column.
		rookMoves.addAll(this.movesInDir(piece, 0, 1));
		rookMoves.addAll(this.movesInDir(piece, 0, -1));
		//System.out.println(rookMoves);
		return rookMoves;
	}
	
	/** 
	 * A bishop can move only diagonally, hence
	 * sum or difference of (row, col) of current and 
	 * destination cell must be the same.
	 * */
	private ArrayList<Cell> getBishopMoves(Piece piece)
	{
		if(!(piece instanceof Bishop || piece instanceof Queen) 
			|| piece == null)
			return null;
	
		ArrayList<Cell> bishopMoves = new ArrayList<>();
		//If it is to be moved along a constant sum diagonal.
		//final int sum = currentPos.row + currentPos.col;
		bishopMoves.addAll(this.movesInDir(piece, 1, -1));
		bishopMoves.addAll(this.movesInDir(piece, -1, 1));
		
		//If it is to be moved along a constant difference diagonal.
		//final int diff = currentPos.row - currentPos.col;
		bishopMoves.addAll(this.movesInDir(piece, 1, 1));
		bishopMoves.addAll(this.movesInDir(piece, -1, -1));
		return bishopMoves;
	}
	
	/**
	 * A knight can move only 2 in one dimension and 1 in the other.
	 * i.e. it can move only by 5 in squared Euclidean distance.
	 * */
	private ArrayList<Cell> getKnightMoves(Knight knight)
	{
		if(knight == null)
			return null;
		
		ArrayList<Cell> knightMoves = new ArrayList<>();
		Cell currentPos = cellOf.get(knight);
		
		final char cr= currentPos.row, cc= currentPos.col;
		Cell possibleCells[]= 
		{	
			board.getCellAt((char)(cr-2), (char)(cc-1)),
			board.getCellAt((char)(cr-2), (char)(cc+1)),
			board.getCellAt((char)(cr-1), (char)(cc-2)),
			board.getCellAt((char)(cr-1), (char)(cc+2)),
			board.getCellAt((char)(cr+1), (char)(cc-2)),
			board.getCellAt((char)(cr+1), (char)(cc+2)),
			board.getCellAt((char)(cr+2), (char)(cc-1)),
			board.getCellAt((char)(cr+2), (char)(cc+1)),
		};
		for(Cell cell : possibleCells)
		{	
			if(this.isValidMove(knight, cell))
				knightMoves.add(cell);
		}
		return knightMoves;
	}
	

	/** 
	 * A King can move only to immediately adjacent (at most 8) cells.
	 * Also, the destination cell must not be under attack, right now.
	 * (King doesn't want himself to be killed.) :P
	 * For which, isUnderAttack() method is called while moving around.
	 * */
	private ArrayList<Cell> getKingMoves(King king)
	{
		if(king == null)
			return null;
		
		ArrayList<Cell> kingMoves = new ArrayList<>();
		Cell currentPos = cellOf.get(king);
		
		final char cr= currentPos.row, cc= currentPos.col;
		for(char row = (char)(cr-1); 	row<=cr+1; row++)
		{	
			for(char col=(char)(cc-1); 	col<=cc+1; col++)
			{	
				if(row == cr && col == cc)	//dest!=source.
					continue;
				Cell dest = board.getCellAt(row, col);
				if(this.isValidMove(king, dest))
				{	
					//this.moveTo(king, dest);
					Piece onDest= onCell.get(dest);
					onCell.put(currentPos, null);
					onCell.put(dest, king);
					cellOf.put(king, dest);
					board.kill(onDest);
					//System.out.println(dest);
					boolean isValid = !isUnderAttack(dest, king.colour);
					if(isValid)
						kingMoves.add(dest);
					//this.undoMove();
					this.add(king, currentPos);
					this.add(onDest, dest);
					board.reincarnate(onDest);
				}
			}
		}
		this.moves.put(king, kingMoves);
		return kingMoves;
	}
	
	/** 
	 * A pawn can move only in the direction 'dir' fixed by its colour, 
	 * it can go to immediate next column (in that direction), and 
	 * move straight or one step diagonally to kill a piece.
	 * 
	 * It can also move 2 steps in an initial move.
	 * */
	private ArrayList<Cell> getPawnMoves(Pawn pawn)
	{
		ArrayList<Cell> pawnMoves = new ArrayList<Cell>();
		
		Cell currentPos = this.cellOf.get(pawn);
		
		if(currentPos == null || board.isKilled(pawn))
			return pawnMoves;
		
		//Case 1: Normal move : 1 cell forward.
		if(this.colourAt((char)(currentPos.row + pawn.dir), 
			currentPos.col)	== null)
		{	
			pawnMoves.add(board.getCellAt((char)(currentPos.row + pawn.dir), 
				currentPos.col));
		}
		
		//Case 2: killing move.
		//The diagonally opposite cells must be occupied by a piece
		//of the opposite colour, for the pawn to attack it.
		if(	this.colourAt((char)(currentPos.row + pawn.dir), 
			(char)(currentPos.col-1)) == Board.opposite(pawn.colour))
		{	
			pawnMoves.add(board.getCellAt((char)(currentPos.row + pawn.dir), 
				(char)(currentPos.col-1)));
		}
		if(	this.colourAt((char)(currentPos.row + pawn.dir), 
			(char)(currentPos.col+1)) == Board.opposite(pawn.colour))
		{	
			pawnMoves.add(board.getCellAt((char)(currentPos.row + pawn.dir), 
				(char)(currentPos.col+1)));
		}
		
		//Case 3: initial move.
		//Both, destination and middle cell should be empty.
		if( currentPos.equals(pawn.orig) && 
			this.colourAt((char)(currentPos.row + 2*pawn.dir), 
				currentPos.col)	== null &&
			this.colourAt((char)(currentPos.row + pawn.dir), 
				currentPos.col)	== null)
		{	
			pawnMoves.add(board.getCellAt((char)(currentPos.row + 2*pawn.dir), 
				currentPos.col));
		}
		return pawnMoves;
	}
	
	/**
	 * Recomputes the moves of this piece and returns the list.
	 * 
	 * @return an ArrayList of Cells to which the piece can
	 * move, from current position.
	 * */
	private ArrayList<Cell> recomputeMoves(Piece piece) 
	{
		if(piece == null)
			return null;
		
		ArrayList<Cell> newMoves = new ArrayList<Cell>();
		if(piece instanceof Queen || piece instanceof Rook)
		{
			newMoves.addAll(this.getRookMoves(piece));
		}
		
		if(piece instanceof Queen || piece instanceof Bishop)
		{
			newMoves.addAll(this.getBishopMoves(piece));		
		}
		
		if(piece instanceof Knight)
		{
			newMoves.addAll(this.getKnightMoves((Knight) piece));
		}
		
		if(piece instanceof Pawn)
		{
			newMoves.addAll(this.getPawnMoves((Pawn)piece));
		}
		
		if(piece instanceof King)
		{
			newMoves.addAll(this.getKingMoves((King) piece));
		}
		this.moves.put(piece, newMoves);
		return newMoves;
	}

	private boolean isValidMove(Piece piece, Cell dest)
	{
		if(dest == null)
			return false;

		if(this.colourAt(dest) != piece.getColour())
			return true;
		else
			return false;
	}

	/** 
	 * This method doesn't modify anything.
	 * It just checks whether destination cell is contained in
	 * the list of moves.
	 * 
	 * @return true if this piece can move to destination cell,
	 * Returns false otherwise.
	 * Returns false if either of the arguments are null.
	 * */
	public boolean canMoveTo(Piece piece, Cell to)
	{
		if(to == null || piece == null || board == null)
			return false;
		
		//Moves of pieces can change possibly after each move of
		//a piece on the board.
		//System.out.println("canMoveTo "+moves.get(piece)
		//+" "+cellOf.get(piece)+" "+to);
		ArrayList<Cell> movesOfThisPiece = this.recomputeMoves(piece);
		moves.put(piece, movesOfThisPiece);
		//System.out.println(moves.get(piece));
		return movesOfThisPiece.contains(to);
	}
	
	/**
	 * A player can select only a cell that contains his/her piece.
	 * */
	public boolean canSelect(Cell cell, String playerColour)
	{
		if(cell == null || playerColour == null)
			return false;
		
		Piece piece = onCell.get(cell);
		return (piece != null && piece.getColour() == playerColour);
	}
	
	/**
	 * @return all moves of the piece on this cell.
	 * */
	public ArrayList<Cell> getAllMoves(Cell cell)
	{
		return this.getAllMoves(onCell.get(cell));
	}
	
	/**
	 * @return all moves of this piece.
	 * */
	public ArrayList<Cell> getAllMoves(Piece piece)
	{
		//System.out.println(piece);
		this.recomputeMoves(piece);
		ArrayList<Cell> allMoves = this.moves.get(piece);
		ArrayList<Cell> validMoves = new ArrayList<Cell>();
		for(Cell dest: allMoves)
		{
			if(!(piece instanceof King))
			{	
				//System.out.println(dest);
				//this.moveTo(cellOf.get(piece), dest);
				Cell from = cellOf.get(piece);
				Piece onDestination = onCell.get(dest);
				onCell.put(from, null);		//empty the current position.
				//System.out.println(from+" "+onCell.get(from));
				//if the destination contains a piece,
				board.kill(onDestination);	//kill it.
				//fill the destination position.
				//set position of this cell as dest.
				this.add(piece, dest);
				//board.print();
				//System.out.println();
				//System.out.println(dest);
				if(!isUnderCheck(piece.colour))
				{
					validMoves.add(dest);
				}
				else
				{	
					//we can't allow this cell.
				}
				this.add(onDestination, dest);
				this.add(piece, from);
				board.reincarnate(onDestination);
				//System.out.println(dest);
			}
			else
			{
				validMoves.add(dest);
			}
		}
		
		return validMoves;
	}
	
	/** 
	 * @return the colour of the piece
	 * on the cell with given (row, column).
	 **/
	public String colourAt(char row, char col)
	{	
		Cell cell= board.getCellAt(row, col);
		if(cell == null)
			return null;
		
		Piece onThisCell = onCell.get(cell);
		//System.out.println(onThisCell);
		if(onThisCell == null)
			return null;
		else
			return onThisCell.getColour();
	}
	
	/** 
	 * @return the colour of the piece
	 * on the cell equivalent to the parameter cell.
	 **/
	public String colourAt(Cell dest)
	{
		return this.colourAt(dest.row, dest.col);
	}
	
	public Piece getPieceOn(Cell cell)
	{
		return onCell.get(cell);
	}
	
	/**
	 * @return true iff current player has got a check-mate.
	 * Returns false if the king of this player has at least one move,
	 * or it is not under check.
	 * */
	public boolean isCheckMate(String playerColour)
	{
		if(!this.isUnderCheck(playerColour))
			return false;
	
		King king = board.getKing(playerColour);
		//System.out.println(king);
		this.recomputeMoves(king);
		//System.out.println(moves.get(king));
		int size = moves.get(king).size();
		
		if(size!=0)
			return false;
		else
		{
			//Try moving some piece of current player to see if it can
			//avoid the check.
			//If there is any such move, return false.
			//Otherwise return true.
			CopyOnWriteArrayList<Piece> ownPieces = board.getPieces(playerColour);
			
			for(Piece piece: ownPieces)	
			{			
				ArrayList<Cell> moveList = moves.get(piece);
				Cell thisCell = cellOf.get(piece);
				for(Cell dest: moveList)
				{
					//Piece currentlyOnC = onCell.get(c);
					//c.setPiece(piece);
					this.moveTo(thisCell, dest);
					//thisCell.setPiece(null);
					boolean lifeSavingMove =
						!(this.isUnderCheck(playerColour));
					//c.setPiece(currentlyOnC);
					//thisCell.setPiece(piece);
					this.undoMove();
					if(lifeSavingMove == true)
						return false;
				}
			}
			
			return true;
		}
	}
	
	/**
	 * @return true iff the king of given colour is under check.
	 * Returns false otherwise.
	 * */
	public boolean isUnderCheck(String playerColour)
	{
		//System.out.println(colourOfPlayer);
		King king = board.getKing(playerColour);
		if(king == null)	//if there is no king, it's not under attack :P
			return false;
		if(!this.isUnderAttack(cellOf.get(king), king.colour))
			return false;
		else
		{	
			//if(highlight)
				//this.clicked(kingCell.row, kingCell.col);
			return true;
		}
	}
	
	
	/** 
	 * @return an ArrayList of Cells from current cell, 
	 * in the the direction of the vector (rowDir, colDir),
	 * to which this piece can move.
	 * */
	private ArrayList<Cell> movesInDir( Piece piece, int rowDir, int colDir)
	{
		//System.out.println(rowDir+" "+colDir);
		ArrayList<Cell> listOfMoves = new ArrayList<Cell>();
		Cell currentPos = cellOf.get(piece);
		char row =(char)(currentPos.row + rowDir);
		char col =(char)(currentPos.col + colDir);
		String pieceColour = piece.getColour();
		for(; row<=Board.rowMax && col<=Board.colMax &&
			row>=Board.rowMin && col>=Board.colMin; row+=rowDir, col+=colDir)
		{
			//System.out.println(row+" "+col+" "+this.colourAt(row, col));
			if(this.colourAt(row, col) == pieceColour)
			{	
				break;
			}
			//if this piece is blocked by one of its own colour,
			//it can't move ahead.
			
			else if(this.colourAt(row, col) == Board.opposite(pieceColour))
			{	
				listOfMoves.add(board.getCellAt(row, col));
				break;
			}
			//if this piece is blocked by one of opposite colour,
			//it can't move ahead.
			else
				listOfMoves.add(board.getCellAt(row, col));
			//If that cell is empty, we can easily go there.
		}
		return listOfMoves;
	}

	
	public void add(Piece piece, Cell cell) 
	{
		//if(piece instanceof King)
		//System.out.println(piece+" "+cell);
		if(cell == null)
			return;
		if(piece != null)
			cellOf.put(piece, cell);
		onCell.put(cell, piece);
		//moves.put(piece, recomputeMoves(piece));
	}
	
	/**
	 * This method is to be used only by king piece, to check 
	 * whether cell dest is under attack by any piece or not.
	 * 
	 * @return true if the cell is under attack by 
	 * any piece other than the king itself (passed as argument).
	 * Returns false if this cells is free from threat.
	 * */
	public boolean isUnderAttack(Cell cell, String ownColour)
	{
		if(cell == null)
			return false;
		
		CopyOnWriteArrayList<Piece> oppositeColourPieces;
		oppositeColourPieces = board.getPieces(Board.opposite(ownColour));
		for(Piece piece: oppositeColourPieces)	
		{	
			if(board.isKilled(piece))
				continue;
			
			if( !(piece instanceof King) &&
				this.canMoveTo(piece, cell) )
			{	//System.out.println(dest+" is under"
				//	+ " attack by piece at "+ cells[i][j]);
				return true;
			}
			else if(piece instanceof King)
			{	//if this cell contains the other king.
				Cell thisCell = cellOf.get(piece);
				int distSquared = 
					(thisCell.row-cell.row)*(thisCell.row-cell.row) +
					(thisCell.col-cell.col)*(thisCell.col-cell.col);
				if(distSquared <= 2)
				{
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * @return The class of the piece on the given cell.
	 * */
	public String getPieceType(Cell cell)
	{
		if(cell == null || onCell.get(cell) == null)
			return null;
		
		return onCell.get(cell).toString();
	}
	
	public Class<? extends Piece> getPieceClass(Cell cell) 
	{
		if(cell == null || onCell.get(cell) == null)
			return null;
		return onCell.get(cell).getClass();
	}

}

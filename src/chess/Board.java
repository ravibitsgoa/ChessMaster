package chess;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import piece.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Board
{	
	public static final char rowMax='8', colMax='h', rowMin='1', colMin='a';
	public static final String White="White", Black = "Black";
	private Movement movement;
	private Cell cells[][];
	private String currentPlayerColour;
	private boolean selected;
	private King whiteKing, blackKing;
	private Cell selectedCell;
	private CopyOnWriteArrayList<Piece> whitePieces, blackPieces,
										killedPieces;
	
	private void emptyBoard()
	{	
		currentPlayerColour = Board.White;
		selected = false;
		selectedCell = null;
		whitePieces = new CopyOnWriteArrayList<>();
		blackPieces = new CopyOnWriteArrayList<>();
		killedPieces = new CopyOnWriteArrayList<>();
		try 
		{
			cells = new Cell[8][8];
			for(int i=0; i<8; i++)
			{	for(int j=0; j<8; j++)
				{	cells[i][j]= new Cell((char)(rowMin+i), (char)(colMin+j));
				}
			}
			//Columns are 'a' to 'h'.
			//Rows are '1' to '8', with row 1 nearest to white.
			movement = new Movement(this);
		}
		catch(Exception e)
		{
			System.out.println("Exception in empty board constructor.");
		}
	}
	
	/**
	 * Constructor for an empty board.
	 * */
	public Board()
	{
		this.emptyBoard();
	}
	
	/** 
	 * Constructor for a filled board.
	 * */
	public Board(Boolean x)
	{	
		this.emptyBoard();
		for(int j=0; j<8; j++)
		{	
			try 
			{
				this.construct(new Pawn(White, cells[1][j]), cells[1][j]);	
				//the second nearest row to white is filled with white pawns
				this.construct(new Pawn(Black, cells[6][j]), cells[6][j]);
				//the second nearest row to black is filled with black pawns.
			}
			catch(Exception e)
			{
				System.out.println("Something went wrong while constructing pawns.");
			}
		}
		
		try 
		{
			//giving 2 rooks to both the player on respective corners.
			this.construct(new Rook(White), cells[0][0]);	
			this.construct(new Rook(White), cells[0][7]);
			this.construct(new Rook(Black), cells[7][0]);
			this.construct(new Rook(Black), cells[7][7]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing rooks.");
		}
		
		try
		{
			//giving 2 knights to both players on cells just beside the rooks.
			this.construct(new Knight(White), cells[0][1]);
			this.construct(new Knight(White), cells[0][6]);
			this.construct(new Knight(Black), cells[7][1]);
			this.construct(new Knight(Black), cells[7][6]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing knights.");
		}
		
		try
		{	//giving 2 bishops to both players on cells just beside the knights.
			this.construct(new Bishop(White), cells[0][2]);
			this.construct(new Bishop(White), cells[0][5]);
			this.construct(new Bishop(Black), cells[7][2]);
			this.construct(new Bishop(Black), cells[7][5]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing bishops.");
		}
		
		try
		{
			//giving a queen to both players on cells just beside a bishop.
			this.construct(new Queen(White), cells[0][3]);
			this.construct(new Queen(Black), cells[7][3]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing queens.");
		}
		
		try
		{
			//giving a king to both players on cells just beside the queen.
			whiteKing = new King(White);
			blackKing = new King(Black);
			this.construct(whiteKing, cells[0][4]);
			this.construct(blackKing, cells[7][4]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing kings.");
		}
		
	}

	public String getCurrentTurn()
	{
		return this.currentPlayerColour;
	}
	
	public void flipTurn()
	{
		this.currentPlayerColour = opposite(currentPlayerColour);
	}
	
	public void construct(Piece piece, Cell cell) throws Exception
	{
		if(piece == null || cell == null)
			throw new Exception("null exception in add() of board.");
		if(piece.colour.equals(White))
			whitePieces.add(piece);
		else
			blackPieces.add(piece);
		movement.construct(piece, cell);
	}

	/**
	 * Returns the reference to the cell at (row, col) in the board.
	 * Returns null if the row or column are not valid (out of bounds).
	 * */
	public Cell getCellAt(char row, char col)
	{
		if(row>=rowMin && col>=colMin && row<=rowMax && col<=colMax)
			return cells[row-rowMin][col-colMin];
		else
			return null;
	}
	
	public Cell getCellAt(int row, int col)
	{	
		return this.getCellAt((char)row, (char)col);
	}
	
	public CopyOnWriteArrayList<Piece> getPieces(String colour)
	{
		if(colour.equals(White))
			return whitePieces;
		else
			return blackPieces;
	}
	
	public static String opposite(String colour)
	{
		if(colour.equals(White))
			return Black;
		else
			return White;
	}
	
	public void kill(Piece piece)
	{
		if(piece!= null)
		{
			killedPieces.add(piece);
		}
	}
	
	public boolean isKilled(Piece piece)
	{
		//System.out.println(piece + " isKilled");
		if(piece == null)
			return true;
		return killedPieces.contains(piece);
	}
	
	public void reincarnate(Piece piece)
	{
		if(piece!= null)
		{
			killedPieces.remove(piece);
			//if(piece.getColour() == White)
			//	whitePieces.add(piece);
			//else
			//	blackPieces.add(piece);
		}
	}
	
	/** 
	 * This method just prints current board situation to terminal.
	 * Used for debugging.
	 **/
	public void print()
	{	
		for(int i=0; i<8; i++)
		{	
			for(int j=0; j<8; j++)
			{	
				Piece onThis = movement.getPieceOn(cells[i][j]);
				if(onThis == null)
					System.out.print(" - ");
				else
					System.out.print(onThis.toString()+" ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * This function is called whenever the user clicks on the cell 
	 * having this (row, col).
	 * 
	 * It highlights the clicked cell if none is highlighted currently.
	 * 
	 * If some cell is highlighted, it moves the piece on that cell to
	 * this cell.
	 * 
	 * @return true if a move is made,
	 * Returns false if either the cell is empty, or contains an opponent
	 * piece or is not a valid destination.
	 * */
	public boolean clicked(int row, int col)
	{
		return this.clicked((char)row, (char)col);
	}
	
	public boolean clicked(char row, char col)
	{
		Cell thisCell = this.getCellAt(row, col);
		if(thisCell == null)
		{	
			System.out.println("Invalid cell in clicked() of Board");
			return false;
		}
		
		//Piece piece = thisCell.getPiece();
		
		if(!selected)
		{	
			//if player clicks on his own piece, mark it as selected.
			if(movement.canSelect(thisCell, currentPlayerColour))
			{	
				selectedCell = thisCell;
				selected = true;
				thisCell.select(true);
				
				//pinning: a piece can't move if it makes 
				//it's own king vulnerable in the immediate next move.
				
				ArrayList<Cell> allMoves = movement.getAllMoves(thisCell);
				for(Cell c: allMoves)
				{
					c.setNextMove(true);
				}
			}
			//If he clicks on an opponent piece, don't do anything.
			return false;
		}
		else 
		{
			selected = false;
			selectedCell.select(false);
			if(selectedCell != null)
			{	
				ArrayList<Cell> allMoves = movement.getAllMoves(selectedCell);
				for(Cell dest: allMoves)
				{
					dest.setNextMove(false);
				}
				
				boolean move = false;
				String moveString = null;
				if(moveString == null)
				{	
					Move temp = movement.moveTo(selectedCell, thisCell);
					if(temp != null)
						moveString = temp.toString();
					
				}
				if(moveString != null)//the player has made a move.
				{
					move = true;
					this.flipTurn();
					System.out.println(moveString);					
				}
				return move;
			}
			selectedCell = null;
			return false;
		}
	}

	public King getKing(String colour)
	{
		if( colour.equals(White) )
			return whiteKing;
		else
			return blackKing;
	}
	
	public void promotePawn(Pawn pawn, Queen queen, Cell queenCell)
	{
		kill(pawn);
		try 
		{
			this.construct(queen, queenCell);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Something went wrong in promotePawn() of Board");
		}
	}
	
	public boolean isCheckMate(String playerColour)
	{
		return movement.isCheckMate(playerColour);
	}
	
	public boolean isUnderCheck(String playerColour)
	{
		return movement.isUnderCheck(playerColour);
	}

	public String getPieceType(Cell thisCell) 
	{
		return movement.getPieceType(thisCell);
	}

	public Class<? extends Piece> getPieceClass(Cell thisCell) 
	{
		return movement.getPieceClass(thisCell);
	}
	/*
	public ArrayList<Cell> getAllMoves(Piece piece) 
	{
		return movement.getAllMoves(piece);
	}*/

	public Movement getMovement() 
	{
		return movement;
	}

}

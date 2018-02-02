package chess;

import java.util.ArrayList;

import piece.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Board 
{	
	private Cell cells[][];
	public static final char rowMax='8', colMax='h', rowMin='1', colMin='a';
	public static final String White="White", Black = "Black";
	private String currentPlayerColour;
	private boolean selected;
	private King whiteKing, blackKing;
	private Cell selectedCell;
	private ArrayList<Piece> whitePieces, blackPieces, killedPieces;
	
	private void emptyBoard()
	{	
		currentPlayerColour = Board.White;
		selected = false;
		selectedCell = null;
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		killedPieces = new ArrayList<>();
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
				whitePieces.add(new Pawn(White, cells[1][j]));	
				//the second nearest row to white is filled with white pawns
				blackPieces.add(new Pawn(Black, cells[6][j]));
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
			whitePieces.add(new Rook(White, cells[0][0]));	
			whitePieces.add(new Rook(White, cells[0][7]));
			blackPieces.add(new Rook(Black, cells[7][0]));
			blackPieces.add(new Rook(Black, cells[7][7]));
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing rooks.");
		}
		
		try
		{
			//giving 2 knights to both players on cells just beside the rooks.
			whitePieces.add(new Knight(White, cells[0][1]));
			whitePieces.add(new Knight(White, cells[0][6]));
			blackPieces.add(new Knight(Black, cells[7][1]));
			blackPieces.add(new Knight(Black, cells[7][6]));
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing knights.");
		}
		
		try
		{	//giving 2 bishops to both players on cells just beside the knights.
			whitePieces.add(new Bishop(White, cells[0][2]));
			whitePieces.add(new Bishop(White, cells[0][5]));
			blackPieces.add(new Bishop(Black, cells[7][2]));
			blackPieces.add(new Bishop(Black, cells[7][5]));
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing bishops.");
		}
		
		try
		{
			//giving a queen to both players on cells just beside a bishop.
			whitePieces.add(new Queen(White, cells[0][4]));
			blackPieces.add(new Queen(Black, cells[7][4]));
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing queens.");
		}
		
		try
		{
			//giving a king to both players on cells just beside the queen.
			whiteKing = new King(White, cells[0][3]);
			blackKing = new King(Black, cells[7][3]);
			whitePieces.add(whiteKing);
			blackPieces.add(blackKing);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing kings.");
		}
		
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
	
	public ArrayList<Piece> getPieces(String colour)
	{
		if(colour == White)
			return whitePieces;
		else
			return blackPieces;
	}
	
	public static String opposite(String colour)
	{
		if(colour == White)
			return Black;
		else
			return White;
	}
	
	public void killPieceAt(Cell cell)
	{
		Piece piece = cell.getPiece();
		//System.out.println("hmm"+piece);
		if(piece!= null)
		{
			killedPieces.add(piece);
			//if(piece.getColour() == White)
			//	whitePieces.remove(piece);
			//else
			//	blackPieces.remove(piece);
		}
	}
	
	public boolean isKilled(Piece piece)
	{
		return killedPieces.contains(piece);
	}
	
	public void addPiece(Piece piece)
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
	 * This method is to be used only by king piece, to check 
	 * whether cell dest is under attack by any piece or not.
	 * 
	 * @return true if the cell is under attack by 
	 * any piece other than the king itself (passed as argument).
	 * Returns false if this cells is free from threat.
	 * */
	public boolean isUnderAttack(char row, char col, King otherThanThis)
	{
		Cell dest = getCellAt(row, col);
		if(dest == null)
			return false;
		
		ArrayList<Piece> oppositeColourPieces;
		String ownColour = otherThanThis.getColour();
		oppositeColourPieces = this.getPieces(opposite(ownColour));
		for(Piece piece: oppositeColourPieces)	
		{	
			if(isKilled(piece))
				continue;
			
			if( !(piece instanceof King) &&
				piece.canMoveTo(dest, this) )
			{	//System.out.println(dest+" is under"
				//	+ " attack by piece at "+ cells[i][j]);
				return true;
			}
			else if(piece instanceof King)
			{	//if this cell contains the other king.
				int distSquared =0;
				Cell thisCell = ((King) piece).getCell();
				distSquared = (thisCell.row-dest.row)*(thisCell.row-dest.row) +
						(thisCell.col-dest.col)*(thisCell.col-dest.col);
				if(distSquared <= 2)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/** 
	 * @return the colour of the piece
	 * on the cell with given (row, column).
	 **/
	public String colourAt(char row, char col)
	{	
		Cell c= this.getCellAt(row, col);
		if(c== null)
			return null;
		
		Piece onThisCell = c.getPiece();
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
	
	/** 
	 * This method just prints current board situation to terminal.
	 * Used for debugging.
	 **/
	public void print()
	{	for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	Piece onThis = cells[i][j].getPiece();
				if(onThis == null)
					System.out.print(" - ");
				else
					System.out.print(onThis.toString()+" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * @return true iff current player has got a check-mate.
	 * Returns false if the king of this player has at least one move,
	 * or it is not under check.
	 * */
	public boolean isCheckMate(String colourOfPlayer)
	{
		if(!this.isUnderCheck(colourOfPlayer))
			return false;
	
		King king = this.getKing(colourOfPlayer);
		int size = king.getAllMoves(this).size();
		
		if(size!=0)
			return false;
		else
		{
			//Try moving some piece of current player to see if it can
			//avoid the check.
			//If there is any such move, return false.
			//Otherwise return true.
			ArrayList<Piece> ownPieces = getPieces(colourOfPlayer);
			
			for(Piece piece: ownPieces)	
			{			
				ArrayList<Cell> moves = piece.getAllMoves(this);
				Cell thisCell = piece.getCell();
				for(Cell c: moves)
				{
					Piece currentlyOnC = c.getPiece();
					c.setPiece(piece);
					
					thisCell.setPiece(null);
					boolean lifeSavingMove =
						!(this.isUnderCheck(colourOfPlayer));
					c.setPiece(currentlyOnC);
					thisCell.setPiece(piece);
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
	public boolean isUnderCheck(String colourOfPlayer)
	{
		//System.out.println(colourOfPlayer);
		King king = getKing(colourOfPlayer);
		Cell kingCell = king.getCell();
		if(!this.isUnderAttack(kingCell.row, kingCell.col, king))
			return false;
		else
		{	
			//if(highlight)
				//this.clicked(kingCell.row, kingCell.col);
			return true;
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
		
		Piece piece = thisCell.getPiece();
		
		if(!selected)
		{	
			//if player clicks on his own piece, mark it as selected.
			if(piece != null && piece.getColour() == currentPlayerColour)
			{	
				selectedCell = thisCell;
				selected = true;
				thisCell.select(currentPlayerColour, true);
				
				//pinning: a piece can't move if it makes 
				//it's own king vulnerable in the immediate next move.
				
				ArrayList<Cell> allMoves = piece.getAllMoves(this);
				for(Cell c: allMoves)
				{
					if(!(piece instanceof King))
					{	thisCell.setPiece(null);
					
						//Store the piece on the cell in a temporary variable.
						Piece currentlyOnDest = c.getPiece();
						c.setPiece(piece);
						if(!isUnderCheck(currentPlayerColour))
						{
							c.setNextMove(true);
						}
						else
						{	
							//we can't allow this cell.
						}
						thisCell.setPiece(piece);
						c.setPiece(currentlyOnDest);
					}
					else
					{
						c.setNextMove(true);
					}
				}
			}
			//If he clicks on an opponent piece, don't do anything.
			return false;
		}
		else 
		{
			selected = false;
			selectedCell.select(currentPlayerColour, false);
			if(selectedCell != null && selectedCell.getPiece() != null)
			{	
				ArrayList<Cell> allMoves = selectedCell.getPiece().getAllMoves(this);
				for(Cell c: allMoves)
				{
					c.setNextMove(false);
				}
				
				boolean move = false;
				String moveString = null;
				Piece selectedPiece = selectedCell.getPiece();
				if(selectedPiece instanceof King)
				{
					King king = (King) selectedPiece;
					if(	king.canKingSideCastle() && 
						thisCell.col == (Board.colMin +1) && 
						thisCell.row == selectedCell.row)
					{
						king.moveTo(thisCell, this);
						
						Cell rookCell = this.getCellAt(thisCell.row, 
								thisCell.col-1);
						Rook kingSideRook = (Rook) rookCell.getPiece();
						
						kingSideRook.castle(this);
						move = true;
						moveString = "o-o";
					}
					else if(king.canQueenSideCastle() && 
							thisCell.col == (Board.colMax - 2) && 
							thisCell.row == selectedCell.row)
					{
						king.moveTo(thisCell, this);
						
						Cell rookCell = this.getCellAt(thisCell.row, 
								thisCell.col+2);
						Rook queenSideRook = (Rook) rookCell.getPiece();
						
						queenSideRook.castle(this);
						move = true;
						moveString = "o-o-o";
					}
				}
				
				if(!move)
				{	
					moveString = "";
					//Pawns are not written in move notations.
					if(!(selectedPiece instanceof Pawn))
						moveString += selectedPiece.toString().charAt(1);
					if(thisCell.getPiece()!= null)
						moveString +="x";	//killing move.
					moveString+= thisCell.toString();
					
					move = selectedPiece.moveTo(thisCell, this);
				}
				if(move)//the player has made a move.
				{
					currentPlayerColour = opposite(currentPlayerColour);
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
		if(colour == White)
			return whiteKing;
		else
			return blackKing;
	}
	
	public void promotePawn(Pawn p, Queen q)
	{
		killPieceAt(p.getCell());
		if(q.getColour() == White)
			whitePieces.add(q);
		else
			blackPieces.add(q);
	}
	
}

package chess;
import piece.*;

public class Board {
	
	private Cell cells[][];
	public static final char rowMax='8', colMax='h', rowMin='1', colMin='a';
	public static final String White="White", Black = "Black";
	
	private void emptyBoard()
	{	cells = new Cell[8][8];
		for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	cells[i][j]= new Cell((char)(rowMin+i), (char)(colMin+j));
			}
		}
		//Columns are 'a' to 'h'.
		//Rows are '1' to '8', with row 1 nearest to white.
	}
	
	/* Constructor for an empty board.
	 **/
	public Board()
	{
		this.emptyBoard();
	}
	
	/* Constructor for a filled board.
	 * */
	public Board(Boolean x)
	{	
		this.emptyBoard();
		for(int j=0; j<8; j++)
		{	cells[1][j].setPiece(new Pawn(White, cells[1][j]));	
			//the second nearest row to white is filled with white pawns
			cells[6][j].setPiece(new Pawn(Black, cells[6][j]));
			//the second nearest row to black is filled with black pawns.
		}
		
		//giving 2 rooks to both the player on respective corners.
		cells[0][0].setPiece(new Rook(White, cells[0][0]));	
		cells[0][7].setPiece(new Rook(White, cells[0][7]));
		cells[7][0].setPiece(new Rook(Black, cells[7][0]));
		cells[7][7].setPiece(new Rook(Black, cells[7][7]));
		
		//giving 2 knights to both players on cells just beside the rooks.
		cells[0][1].setPiece(new Knight(White, cells[0][1]));
		cells[0][6].setPiece(new Knight(White, cells[0][6]));
		cells[7][1].setPiece(new Knight(Black, cells[7][1]));
		cells[7][6].setPiece(new Knight(Black, cells[7][6]));
		
		//giving 2 bishops to both players on cells just beside the knights.
		cells[0][2].setPiece(new Bishop(White, cells[0][2]));
		cells[0][5].setPiece(new Bishop(White, cells[0][5]));
		cells[7][2].setPiece(new Bishop(Black, cells[7][2]));
		cells[7][5].setPiece(new Bishop(Black, cells[7][5]));
		
		//giving a queen to both players on cells just beside a bishop.
		cells[0][4].setPiece(new Queen(White, cells[0][4]));
		cells[7][4].setPiece(new Queen(Black, cells[7][4]));
		
		//giving a king to both players on cells just beside the queen.
		cells[0][3].setPiece(new King(White, cells[0][3]));
		cells[7][3].setPiece(new King(Black, cells[7][3]));
		
	}
	
	public Cell getCellAt(char row, char col)
	{
		if(row>=rowMin && col>=colMin && row<=rowMax && col<=colMax)
			return cells[row-rowMin][col-colMin];
		else
			return null;
	}

	/* This method is used only by king piece, to check 
	 * whether cell dest is under attack by any piece or not.
	 * This method returns true if the cell is under attack by 
	 * any piece other than the king itself (passed as argument).
	 * Returns false if this cells is free from threat.
	 * */
	public boolean isUnderAttack(char row, char col, Piece otherThanThis)
	{
		Cell dest = getCellAt((char)(row-rowMin), (char)(col-colMin));
		for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	Piece onThis = cells[i][j].getPiece();
				if((dest != cells[i][j]) && (onThis != null))
				{	if( (onThis != otherThanThis) && (onThis.canMoveTo(dest, this)))
					{	System.out.println(dest.row + " " + dest.col+
							" is under attack by piece at " 
							+ cells[i][j].row + " " + cells[i][j].col);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/* This method returns the color of the piece,
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
	
	public String colourAt(Cell dest)
	{
		return this.colourAt(dest.row, dest.col);
	}
	
	/* This method just prints current board situation to terminal.
	 * Used for debugging.
	 */
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
	
	public static void main(String args[])
	{
		Board b = new Board(true);
		b.print();
		//System.out.println(b.isUnderAttack((char)(rowMin+2), (char)(colMin+1), null));
	}
}

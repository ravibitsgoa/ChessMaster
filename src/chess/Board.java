package chess;
//import java.util.ArrayList;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import piece.*;

/**
 * @author Ravishankar P. Joshi
 * */
public class Board 
{	
	private Cell cells[][];
	public static final char rowMax='8', colMax='h', rowMin='1', colMin='a';
	public static final String White="White", Black = "Black";
	//private ArrayList<Piece> pieces;
	
	private void emptyBoard()
	{	
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
				new Pawn(White, cells[1][j]);	
				//the second nearest row to white is filled with white pawns
				new Pawn(Black, cells[6][j]);
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
			new Rook(White, cells[0][0]);	
			new Rook(White, cells[0][7]);
			new Rook(Black, cells[7][0]);
			new Rook(Black, cells[7][7]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing rooks.");
		}
		
		try
		{
			//giving 2 knights to both players on cells just beside the rooks.
			new Knight(White, cells[0][1]);
			new Knight(White, cells[0][6]);
			new Knight(Black, cells[7][1]);
			new Knight(Black, cells[7][6]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing knights.");
		}
		
		try
		{	//giving 2 bishops to both players on cells just beside the knights.
			new Bishop(White, cells[0][2]);
			new Bishop(White, cells[0][5]);
			new Bishop(Black, cells[7][2]);
			new Bishop(Black, cells[7][5]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing bishops.");
		}
		
		try
		{
			//giving a queen to both players on cells just beside a bishop.
			new Queen(White, cells[0][4]);
			new Queen(Black, cells[7][4]);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while constructing queens.");
		}
		
		try
		{
			//giving a king to both players on cells just beside the queen.
			new King(White, cells[0][3]);
			new King(Black, cells[7][3]);
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
	
	/**
	 * This method is to be used only by king piece, to check 
	 * whether cell dest is under attack by any piece or not.
	 * 
	 * @return true if the cell is under attack by 
	 * any piece other than the king itself (passed as argument).
	 * Returns false if this cells is free from threat.
	 * */
	public boolean isUnderAttack(char row, char col, Piece otherThanThis)
	{
		Cell dest = getCellAt(row, col);
		if(dest == null)
			return false;
		
		for(int i=0; i<8; i++)
		{	for(int j=0; j<8; j++)
			{	Piece onThis = cells[i][j].getPiece();
				//if(onThis != null)
				//	System.out.println(onThis);
				if((dest != cells[i][j]) && (onThis != null))
				{	if( (onThis != otherThanThis) &&(onThis.canMoveTo(dest, this)))
					{	//System.out.println(dest+" is under"
						//	+ " attack by piece at "+ cells[i][j]);
						return true;
					}
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
	
	public static void main(String args[])
	{
		Board b = new Board(true);
		b.print();

		JFrame frame = new JFrame("title");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsHandler g= new GraphicsHandler(b, 0, 0, 75, 75, 1);
		frame.add(g);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		//System.out.print(b.getClass().getSimpleName());
		//System.out.println(b.isUnderAttack((char)(rowMin+2), (char)(colMin+1), null));
	}
}

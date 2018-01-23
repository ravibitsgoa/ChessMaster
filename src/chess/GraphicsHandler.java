
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import piece.Piece;

/**
 * @author Ravishankar P. Joshi
 * */
public class GraphicsHandler extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Board board;
	private final int x0, y0, rowLen, colLen, border;
	private final Color HIGHLIGHT, NEXTMOVE;
	private MouseHandler mouseHandler;
	
	public GraphicsHandler(Board b, int x, int y, int rowLen, 
			int colLen, int border)
	{
		board = b;
		x0= x;
		y0= y;
		this.rowLen = rowLen;
		this.colLen = colLen;
		HIGHLIGHT = Color.YELLOW;
		NEXTMOVE = Color.orange;
		this.border = border;
		
		mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
	}
	
	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		for(int i=0; i<=(Board.rowMax-Board.rowMin); i++)
		{
			for(int j=0; j<=(Board.colMax-Board.colMin); j++)
			{
				Cell thisCell = board.getCellAt(j+Board.rowMin, i+Board.colMin);
				graphics.setColor(Color.BLACK);
				int x= x0+ i*rowLen, y= y0+ j*colLen;
				
				graphics.drawRect(x, y, colLen, rowLen);
				//Draw the border of the cell.
				
				//paint the cell LIGHT_GRAY if it's even numbered.
				if((i+j) % 2 ==0)
				{	graphics.setColor(Color.LIGHT_GRAY);
					graphics.fillRect(x+border, y+border, colLen-border,
						rowLen-border);
				}
				//otherwise paint it white.
				else
				{	graphics.setColor(Color.WHITE);
					graphics.fillRect(x+border, y+border, colLen-border,
						rowLen-border);
				}
				
				if(thisCell.isSelected())	
				{
					//If the cell is selected, paint it
					//with HIGHLIGHT colour.
					graphics.setColor(HIGHLIGHT);
					graphics.fillRect(x, y, colLen, rowLen);
				}
				else if(thisCell.isNextMove())
				{
					//If the cell is selected, paint it
					//with NEXTMOVE colour.
					graphics.setColor(NEXTMOVE);
					graphics.fillRect(x, y, colLen, rowLen);			
				}
				Piece thisPiece = thisCell.getPiece();
				//draw the image of the piece contained by this cell.
				if(thisPiece != null)
				{
					Class<? extends Piece> type = thisPiece.getClass();
					BufferedImage img = null;
					
					try 
					{
						img = ImageIO.read(type.
								getResource(thisPiece.toString()+".png"));
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
						System.out.println("Exception in paintComponents()"
								+ " of GraphicsHandler.");
					}
					
					Image scaledImg = img.getScaledInstance(colLen, 
							rowLen, Image.SCALE_SMOOTH);
					//Scale the icon to the size of this cell.
					Icon pieceIcon = new ImageIcon(scaledImg);
					
					pieceIcon.paintIcon(this, graphics, x, y);
				}
			}
		}
	}

	private void checkMate(String playerColour)
	{
		JFrame message = new JFrame("Sorry !!");
		message.add(new JLabel(playerColour+", you lost the game :'("));
		message.setVisible(true);
		message.setSize(300, 300);
	}
	
	/**
	 * Calls board.clicked() with the row and column of the clicked cell.
	 * Redraws all the graphics of the window.
	 * Checks whether the game has ended, and shows a message if that is true.
	 * */
	private void clicked(int x, int y)
	{
		int col = (x-x0) / rowLen;
		int row = (y-y0) / colLen;
		
		board.clicked(row +Board.rowMin, col +Board.colMin);
		this.repaint();
		if(board.isCheckMate(Board.White))
			this.checkMate(Board.White);
		if(board.isCheckMate(Board.Black))
			this.checkMate(Board.Black);
	}
	
	/**
	 * MouseHandler class which calls clicked() of GraphicsHandler object,
	 * with the x and y coordinates of a mouse click.
	 * */
	private class MouseHandler implements MouseListener, MouseMotionListener
	{
		@Override
		public void mouseDragged(MouseEvent e) 
		{}

		@Override
		public void mouseMoved(MouseEvent e) 
		{}

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			clicked(e.getX(), e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{}

		@Override
		public void mouseReleased(MouseEvent e) 
		{}

		@Override
		public void mouseEntered(MouseEvent e) 
		{}

		@Override
		public void mouseExited(MouseEvent e) 
		{}
	}
}

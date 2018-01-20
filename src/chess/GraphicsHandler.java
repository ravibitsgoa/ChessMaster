
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
	private final Color HIGHLIGHT;
	public GraphicsHandler(Board b, int x, int y, int rowLen, 
			int colLen, int border)
	{
		board = b;
		x0= x;
		y0= y;
		this.rowLen = rowLen;
		this.colLen = colLen;
		HIGHLIGHT = Color.YELLOW;
		this.border = border;
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
				
				if(thisCell.isHighlighted())	
				{
					//If the cell is in highlighted state, paint it
					//with HIGHLIGHT colour.
					graphics.setColor(HIGHLIGHT);
					graphics.drawRect(x, y, colLen, rowLen);
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
	
}

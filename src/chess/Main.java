/**
 * 	The main class of the ChessMaster project.
 */
package chess;

import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Ravishankar P. Joshi
 */
public class Main 
{
	private GameModeWindow gameModeWindow;
	private SelectPlayerWindow selectPlayerWindow;
	private SelectColourWindow selectColourWindow;
	private JFrame chessWindow;
	private Player player, player2;
	private String colour, colour2;
	private GraphicsHandler graphicsHandler;
	private Board board;
	private Movement movement;
	
	private int gameMode;
	private JFrame[] windowList;
	
	public Main()
	{
		//welcomeWindow = new WelcomeWindow();
		//welcomeWindow.setSize(300, 300);
		//welcomeWindow.setVisible(true);
		
		gameModeWindow = new GameModeWindow();
		gameModeWindow.setSize(300, 300);
		gameModeWindow.setVisible(true);
		
		board = new Board(true);
		board.print();
		
		chessWindow = new JFrame("chess");
		graphicsHandler= new GraphicsHandler(board, 50, 50, 75, 75, 1);
		chessWindow.add(graphicsHandler);
		chessWindow.setSize(700, 700);
		chessWindow.setVisible(false);
	}

	public static void main(String args[])
	{
		new Main();
	}
	
	private void setHumanPlayerColour(String colour)
	{
		this.colour = colour;

		if(colour == Board.White)
			this.colour2 = Board.Black;
		else
			this.colour2 = Board.White;
		
		try 
		{
			movement = board.getMovement();
			graphicsHandler.setAI(new AI(board, colour2, movement));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in setHumanPlayerColour() of Main.");
		}
	}
	
	public void nextWindow(JFrame window)
	{
		int order=0;
		
		while(windowList[order] != window)
			order++;
		windowList[order].setVisible(false);
		windowList[order+1].setVisible(true);
	}
	
	public void setWindowList()
	{
		try 
		{
			graphicsHandler.setGameMode(gameMode);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception in setWindowList() of Main"
					+ "while setting game mode.");
		}
		
		if(gameMode == 1)
		{
			selectPlayerWindow = new SelectPlayerWindow(1);
			selectPlayerWindow.setSize(300, 300);
			
			selectColourWindow = new SelectColourWindow();
			selectColourWindow.setSize(300, 300);
			
			JFrame[] arr = {selectPlayerWindow, selectColourWindow, chessWindow};
			windowList = arr;
		}
		else
		{	
			selectPlayerWindow = new SelectPlayerWindow(2);
			selectPlayerWindow.setSize(300, 300);
	
			JFrame[] arr = {selectPlayerWindow,	chessWindow};
			windowList = arr;
		}	
		windowList[0].setVisible(true);
	}
	
	private class SelectColourWindow extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private ButtonGroup colourChoice;
		
		public SelectColourWindow()
		{
			super("Select colour");
			setLayout(new FlowLayout());
			
			colourChoice = new ButtonGroup();
			
			JRadioButton whiteButton = new JRadioButton(Board.White);
			JRadioButton blackButton = new JRadioButton(Board.Black);
			add(whiteButton);
			add(blackButton);
			colourChoice.add(whiteButton);
			colourChoice.add(blackButton);
			
			whiteButton.addItemListener(new ColourHandler(Board.White));
			blackButton.addItemListener(new ColourHandler(Board.Black));
		}
		
		public void CloseFrame()
		{
			Main.this.nextWindow(this);
			super.dispose();
		}
		
		private class ColourHandler implements ItemListener
		{
			private String color;
			
			public ColourHandler(String s)
			{
				color = s;
			}
			
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				Main.this.setHumanPlayerColour(this.color);
				CloseFrame();
			}
		}
	}
	
	private class SelectPlayerWindow extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JComboBox<String> selectPlayer, selectPlayer2;
		private int mode;
		
		public SelectPlayerWindow(int mode)
		{
			super("Please select your name");
			setLayout(new FlowLayout());
			
			this.mode = mode;
			
			selectPlayer = new JComboBox<>(Player.getPlayerList());
			selectPlayer.setEditable(true);
			add(selectPlayer);
			HandlerClass handler = new HandlerClass();
			selectPlayer.addActionListener(handler);
	
			if(mode == 2)
			{
				JLabel white = new JLabel("White");
				add(white);
				
				selectPlayer2 = new JComboBox<>(Player.getPlayerList());
				selectPlayer2.setEditable(true);
				add(selectPlayer2);
				selectPlayer2.addActionListener(handler);
				JLabel black = new JLabel("Black");
				add(black);
				
				colour = "White";
				colour2 = "Black";
			}
		}
		
		public void CloseFrame()
		{
			Main.this.nextWindow(this);
			super.dispose();
		}
		
		private class HandlerClass implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				String name = (String)selectPlayer.getSelectedItem();
				player = Player.getPlayer(name);
				if(player == null)
					player = new Player(name);
				
				if(mode == 2)
				{
					String name2 = (String)selectPlayer2.getSelectedItem();
					
					if(name == name2)
						return;
					//both names must be different.
					
					player2 = Player.getPlayer(name2);
					if(player2 == null)
						player2 = new Player(name2);
				}
				
				CloseFrame();
			}
		}
	}
	
	private class GameModeWindow extends JFrame
	{
		private static final long serialVersionUID = 1L;

		public GameModeWindow()
		{
			super("Welcome to chess");
			setLayout(new FlowLayout());
			
			ButtonGroup group = new ButtonGroup();
			JRadioButton singlePlayer = new JRadioButton("Single player mode");
			JRadioButton multiPlayer = new JRadioButton("Multi player mode");
			add(singlePlayer);
			add(multiPlayer);
			group.add(singlePlayer);
			group.add(multiPlayer);
			
			singlePlayer.addItemListener(new HandlerClass(1));
			multiPlayer.addItemListener(new HandlerClass(2));
		}
		
		public void CloseFrame()
		{
			super.dispose();
			Main.this.setWindowList();
		}
		
		private class HandlerClass implements ItemListener
		{
			private int mode;
			public HandlerClass (int x)
			{
				this.mode = x;
			}
			
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				Main.this.gameMode = this.mode;
				CloseFrame();
			}
		}
	}
	
}

/**
 * 	The main class of the ChessMaster project.
 */
package chess;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.ArrayList;

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
	
	/**
	 * Creates gameModeWindow, a new board, chessWindow.
	 * Launches gameModeWindow.
	 * On closing of the chessWindow, updates the statistics of the player(s).
	 * */
	
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
		chessWindow.addWindowListener(
			new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e) 
				{
					if(board.isCheckMate(colour) && player2 != null)
					{	player2.won();
						player.lost();
					}
					else if(board.isCheckMate(colour2))
					{	player.won();
						player2.lost();
					}
					
					player.gamePlayed();
					player.storePlayer();
				    if(player2 != null)
				    {	
				    	player2.gamePlayed();
				    	player2.storePlayer();
				    }
				}
			}
		);
		chessWindow.setSize(700, 700);
		chessWindow.setVisible(false);
	}

	public static void main(String args[])
	{
		new Main();
	}
	
	/**
	 * This method is relevant only in 1 player mode.
	 * Sets the human colour to given colour.
	 * Creates an AI with the opposite colour.
	 * */
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
	
	/**
	 * Finds the next window after this window in the window list, 
	 * Sets this window as invisible, and next window as visible.
	 * */
	public void nextWindow(JFrame window)
	{
		int order=0;
		
		while(windowList[order] != window)
			order++;
		windowList[order].setVisible(false);
		windowList[order+1].setVisible(true);
	}
	
	/**
	 * Informs about the game mode to graphics handler class.
	 * Sets the game's windows' sequence.
	 * 
	 * If game is in single player mode, the sequence will be as follows:
	 * player selection window, human colour selection window, main chess window.
	 * 
	 * If game is in multiplayer mode, the sequence will be as follows:
	 * both player selection window, main chess window.
	 * 
	 * Sets the first of the windows to visible.
	 * All the windows call nextWindow method to enable next window.
	 * */
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
	
	/**
	 * This window has relevance only when game mode is 1 player.
	 * Lets the user select its colour. 
	 * Sets the AI colour to opposite of the player colour.
	 * */
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
		
		/**
		 * Closes the current window, and calls nextWindow() from main, to
		 * display the next window in the sequence.
		 * */
		public void CloseFrame()
		{
			Main.this.nextWindow(this);
			super.dispose();
		}
		
		/**
		 * As soon as user selects the colour, set the colour for it, and
		 * close this window.
		 * */
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
	
	/**
	 * Displays a window to let user(s) select their name(s) from the saved 
	 * players' statistics file.
	 * 
	 * Also supports creation of a new Player (with a new name).
	 * (Two players can't have same name.)
	 * 
	 * If game is single player, shows only one drop down menu for name.
	 * Otherwise shows two drop-down menus for selecting names.
	 * 
	 * Sets the colours of the two players too.
	 * 
	 * Creates a new player iff one with such a name doesn't exist.
	 * */
	private class SelectPlayerWindow extends JFrame
	{
		private static final long serialVersionUID = 1L;
		private JComboBox<Player> selectPlayer, selectPlayer2;
		private int mode;
		private ArrayList<Player> allPlayers;
		
		public SelectPlayerWindow(int mode)
		{
			super("Please select your name");
			setLayout(new FlowLayout());
			
			this.mode = mode;
			
			allPlayers = Player.getPlayersList();
			Player players[] = allPlayers.toArray(new Player[allPlayers.size()]);
			
			selectPlayer = new JComboBox<>(players);
			selectPlayer.setEditable(true);
			add(selectPlayer);
			HandlerClass handler = new HandlerClass();
			selectPlayer.addActionListener(handler);
	
			if(mode == 2)
			{
				JLabel white = new JLabel("White");
				add(white);
				
				selectPlayer2 = new JComboBox<>(players);
				selectPlayer2.setEditable(true);
				add(selectPlayer2);
				selectPlayer2.addActionListener(handler);
				JLabel black = new JLabel("Black");
				add(black);
				
				colour = "White";
				colour2 = "Black";
			}
		}
		
		/**
		 * Closes the current window, and calls nextWindow() from main, to
		 * display the next window in the sequence.
		 * */
		public void CloseFrame()
		{
			Main.this.nextWindow(this);
			super.dispose();
		}
		
		/**
		 * If user enters an already existing name, it loads the player object
		 * from the stored file.
		 * Otherwise creates a new Player with given (typed) name.
		 * 
		 * Validates whether two names are equal in 2 player mode.
		 * 
		 * If both names are valid, creates/sets the required players, and
		 * closes the window.
		 * */
		private class HandlerClass implements ActionListener
		{
			public void actionPerformed(ActionEvent event) 
			{
				String name = selectPlayer.getSelectedItem().toString();
				player = Player.getPlayer(allPlayers, name);
				if(player == null)	//not yet selected.
					return;
				
				if(mode == 2)
				{
					String name2 = selectPlayer2.getSelectedItem().toString();
					
					if(name.equals(name2))
						return;
					//both names must be different.
					
					player2 = Player.getPlayer(allPlayers, name2);
					if(player2 == null)	//not yet selected.
						return;
				}
				
				CloseFrame();
			}
		}
	}
	
	/**
	 * Displays a window asking user for game mode, and sets the game mode.
	 * The game has two modes currently : single player and multiplayer.
	 * After setting the game mode successfully, this class calls the 
	 * setWindowList() method of the main class, which continues the flow.
	 * 
	 * In multiplayer mode, both the players play on the same PC using mouse,
	 * alternatively (as per turns).
	 * 
	 * In single player mode, an AI plays with the human player.
	 * */
	private class GameModeWindow extends JFrame
	{
		private static final long serialVersionUID = 1L;
		
		/**
		 * Creates a new welcome window, having buttons for single-player and
		 * multiplayer game mode.
		 * 
		 * Whenever the user clicks the button, the game mode should be set.
		 * */
		
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
		
		/**
		 * Close the frame and set the windows list for the UI.
		 * */
		public void CloseFrame()
		{
			super.dispose();
			Main.this.setWindowList();
		}
		
		/**
		 * Set the game mode according to the clicked button.
		 * On successful set, immediately close the frame.
		 * */
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

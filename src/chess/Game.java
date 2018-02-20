package chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Game implements Serializable 
{
	private static final long serialVersionUID = -456686249905185825L;
	public final String player1, player2, colour1, colour2;
	public final Integer mode;
	private ArrayList<String[]> allMoves;
	private final long startTime;
	private String toPlay;
	
	/**
	 * Sets game mode (1 or 2), names of the players*, colours of the players,
	 * and the player who is about to play.
	 * 
	 * Stores the startTime of the game as the time of creation of this object.
	 * (It is useful in identifying a unique game while loading / storing.)
	 * 
	 * * : in case of a single player game, "AI" is added as player2.
	 * @throws null player exception
	 * @throws invalid colour exception
	 * @throws invalid game mode exception.
	 * */
	public Game(int mode, String player1, String player2, 
			String colour1, String colour2, String toPlay) throws Exception
	{
		System.out.println(player1+" "+player2+" "+colour1+" "+colour2); 
		if(player1 == null || player2 == null)
			throw new Exception("Null player exception in Game");
		
		if(colour1 != Board.White && colour1 != Board.Black 
			&& colour2 != Board.White && colour2 != Board.Black)
			throw new Exception("Invalid colour exception in Game");
		
		if(mode != 1 && mode != 2)
			throw new Exception("Invalid mode exception in Game");
		
		this.player1 = player1;
		this.player2 = player2;
		this.mode = mode;
		this.colour1 = colour1;
		this.colour2 = colour2;
		this.startTime = System.nanoTime();
		
		allMoves = new ArrayList<>();
		this.toPlay = toPlay;
	}
	
	/**
	 * @return player1's name vs player2's name @ starting time.
	 * e.g. Ravi vs Anish @ 1231546546
	 * */
	@Override
	public String toString()
	{
		return player1 + " vs " + player2 + " @ "+ startTime;
	}
	
	/**
	 * In the following code, two games that have same
	 * players and start time, are assumed to be same and stored only once
	 * in the game statistics file (gameData.dat).
	 * */
	/*
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	@Override 
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Game))
			return false;
		return this.hashCode() == obj.hashCode();
	}*/
	
	/**
	 * Fetches previous games from a file named gameData.dat as an ArrayList.
	 * */
	public static ArrayList<Game> getGamesList()         
	{
		Game tempGame;
		ObjectInputStream input = null;
		ArrayList<Game> games = new ArrayList<Game>();
		try
		{
			File infile = new File(System.getProperty("user.dir")+ 
					File.separator + "gameData.dat");
			input = new ObjectInputStream(new FileInputStream(infile));
			try
			{
				while(true)
				{
					tempGame = (Game) input.readObject();
					games.add(tempGame);
				}
			}
			catch(EOFException e)
			{
				input.close();
			}
		}
		catch (FileNotFoundException e)
		{
			games.clear();
			return games;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			try {input.close();} 
			catch (IOException e1) {}
			JOptionPane.showMessageDialog(null, 
					"Unable to read the required Game files!");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,	"Game Data File Corrupted!");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return games;
	}
	
	/**
	 * Stores the the current game at the end.
	 * If current one is a new game, it stores it, otherwise, if
	 * custom hashCode method is enabled, it removes the old copy and 
	 * stores new copy.
	 * */
	public void storeGame()
	{
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		Game tempGame;
		File inputFile=null;
		File outputFile=null;
		try
		{
			inputFile = new File(System.getProperty("user.dir")+ File.separator 
						+ "gameData.dat");
			outputFile = new File(System.getProperty("user.dir")+ File.separator 
						+ "tempFile.dat");
		} 
		catch (SecurityException e)
		{
			JOptionPane.showMessageDialog(null, "Read-Write Permission Denied.");
			e.printStackTrace();
			assert(false);
		} 
		boolean gameDoesntExist;
		try
		{
			if(outputFile.exists() == false)
				outputFile.createNewFile();
			if(inputFile.exists() == false)
			{
				output = new ObjectOutputStream(
						new java.io.FileOutputStream(outputFile,true));
				output.writeObject(this);
			}
			else
			{
				input = new ObjectInputStream(new FileInputStream(inputFile));
				output = new ObjectOutputStream(
						new FileOutputStream(outputFile));
				gameDoesntExist=true;
				try
				{
					while(true)
					{
						tempGame = (Game)input.readObject();
						if (tempGame.equals(this))
						{
							output.writeObject(this);
							gameDoesntExist = false;
						}
						else
							output.writeObject(tempGame);
					}
				}
				catch(EOFException e)
				{
					input.close();
				}
				if(gameDoesntExist)
					output.writeObject(this);
			}
			inputFile.delete();
			output.close();
			File newf = new File(System.getProperty("user.dir")+ File.separator 
					+ "gameData.dat");
			if(outputFile.renameTo(newf) == false)
				System.out.println("File Renameing Unsuccessful");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					"Unable to read/write the required Game files!");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Stores move stack into ArrayList of moves of the game, so that
	 * reloading the game becomes easy.
	 * */
	public void storeMoves(Movement movement)
	{
		Stack<Move> moves = movement.getPastMoves();
		toPlay = Board.White;
		allMoves = new ArrayList<>();	//this has to be emptied.
		for(int i=0; i<moves.size(); i++)
		{
			String mov[] = new String[2];
			mov[0] = moves.get(i).getSource().toString();
			mov[1] = moves.get(i).getDestination().toString();
			//System.out.println(mov[0]+" "+mov[1]);
			allMoves.add(mov);
			toPlay = Board.opposite(toPlay);
		}
		//converting stack to ArrayList.
	}
	
	/**
	 * Plays moves sequentially from an (assumed) starting board from 
	 * its own move list, so that the players can continue the game.
	 * Also, the player who was to play while saving the game will get 
	 * to play after reloading.
	 * */
	public Movement reloadGame(Board board)
	{
		Movement movement = board.getMovement();
		for(String[] move : allMoves)
		{
			//System.out.println(move[0] + " " + move[1]);
			//board.print();
			movement.playMove(move[0], move[1]);
		}
		return movement;
	}
}

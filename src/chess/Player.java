package chess;

/**
 * @author Ravishankar P. Joshi
 */
public class Player 
{
	private String name;
	private int gamesPlayed;
	private int gamesWon;
	
	public Player(String name)
	{
		this.name = name;
		gamesPlayed = 0;
		gamesWon = 0;
		System.out.println("Cons "+name);
	}
	
	public static String[] getPlayerList()
	{
		String arr[] = {"abc", "bcd"};
		return arr;
	}
	
	public static Player getPlayer(String name)
	{
		return null;
	}
	
	public String toString()
	{
		return name;
	}
}

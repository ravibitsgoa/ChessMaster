package chess;

public class AI 
{
	private Board board;
	private String colour;
	public AI(Board board, String colour) throws Exception
	{
		//System.out.println(colour);
		if(colour!= Board.White && colour!=Board.Black)
			throw new Exception("Colour invalid exception in AI class.");
		if(board == null)
			throw new Exception("Board empty exception in AI class.");
		
		this.board = board;
		this.colour = colour;
	}
	
	public void playNextMove()
	{
		
	}
}

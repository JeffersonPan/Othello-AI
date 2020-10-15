import java.util.*;
public class BoardViewerNoGUI {
	public static void main(String[] args)	{
		Board board=new Board();
		Scanner s=new Scanner(System.in);
		String[] arr;
		board.printBoard();
		OthelloAI ai=new OthelloAI();
		Coord ai_choice=new Coord(0,0);
		while(board.validMoves().size()>0)	{
			board.printBoard();
			System.out.println("it's "+board.getTurn()+"'s turn");
			if(board.getTurn()=='b')	{
				ArrayList<Coord> vm=board.validMoves();
				System.out.println("valid moves: "+vm);
				System.out.println("next move (written like x,y): ");
				//String usr_input=s.nextLine();
				//arr=usr_input.split(",");
				board.selectMove(vm.get(0).getX(), vm.get(0).getY());
			}
			else	{
				ai_choice=ai.chooseMove(board);
				board.selectMove(ai_choice.getX(), ai_choice.getY());
				System.out.println("AI went ("+ai_choice.getX()+", "+ai_choice.getY()+")");
			}
		}
		if(board.getBlackScore()>board.getWhiteScore())
			System.out.println("black won");
		else
			System.out.println("white won");
	}
}

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
public class BoardViewerAI extends JFrame{
	private static Board board;
	
	private JPanel north, center;
	private JButton[][] button_board;
	private JLabel score_label, turn_label;
	public static void main(String[] args)	{
		board=new Board();
		
		SwingUtilities.invokeLater(new Runnable() {
        	public void run() {
        		try {
        			BoardViewerAI window = new BoardViewerAI();
            
        			// Resize to fit content
        			//window.pack();
            
        			// Display the Window
        			window.setVisible(true);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        });
		/*Board board=new Board();
		Scanner s=new Scanner(System.in);
		String[] arr;
		board.printBoard();
		while(board.validMoves().size()>0)	{
			System.out.println("it's "+board.getTurn()+"'s turn");
			System.out.println("valid moves: "+board.validMoves());
			System.out.println("next move (written like x,y): ");
			String usr_input=s.nextLine();
			arr=usr_input.split(",");
			board.selectMove(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
			board.printBoard();
		}*/
	}
	public BoardViewerAI()	{
		initialize();
	}
	private void initialize()	{
		setSize(1500, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0,0));
		
		north=new JPanel();
		FlowLayout flow=new FlowLayout();
		flow.setHgap(100);
		north.setLayout(flow);
		
		turn_label=new JLabel("It is "+board.getTurn()+"'s turn.");
		score_label=new JLabel("Would you like to play a game?");
		turn_label.setPreferredSize(new Dimension(200, 70));
		score_label.setPreferredSize(new Dimension(500, 70));
		turn_label.setFont (turn_label.getFont().deriveFont(25.0f));
		score_label.setFont (score_label.getFont().deriveFont(25.0f));
		north.add(turn_label);
		north.add(score_label);
		
		getContentPane().add(north, BorderLayout.NORTH);
		
		center=new JPanel();
		GridLayout grid=new GridLayout(8,8);
		center.setLayout(grid);
		
		button_board=new JButton[8][8];
		for(int i=0; i<button_board.length; i++)	{
			for(int j=0; j<button_board.length; j++)	{
				button_board[i][j]=new JButton();
			}
		}
		char[][] init_board=board.getBoard();
		for(int i=0; i<init_board.length; i++)	{
			for(int j=0; j<init_board.length; j++)	{
				if(init_board[i][j]=='e')
					button_board[i][j].setBackground(Color.LIGHT_GRAY);
				if(init_board[i][j]=='b')
					button_board[i][j].setBackground(Color.black);
				if(init_board[i][j]=='w')
					button_board[i][j].setBackground(Color.white);
				button_board[i][j].addActionListener(new ButtonBoardListener(i,j));
				center.add(button_board[i][j]);
			}
		}
		ArrayList<Coord> legal=board.validMoves();
		for(int i=0; i<legal.size(); i++)	{
			button_board[legal.get(i).getX()][legal.get(i).getY()].setEnabled(true);
			button_board[legal.get(i).getX()][legal.get(i).getY()].setBackground(Color.green);
		}
		
		getContentPane().add(center, BorderLayout.CENTER);
		
		
	}
	private void updateBoard()	{
		char[][] b=board.getBoard();
		for(int i=0; i<b.length; i++)	{
			for(int j=0; j<b.length; j++)	{
				if(b[i][j]=='e')
					button_board[i][j].setBackground(Color.LIGHT_GRAY);
				if(b[i][j]=='b')
					button_board[i][j].setBackground(Color.black);
				if(b[i][j]=='w')
					button_board[i][j].setBackground(Color.white);
				button_board[i][j].setEnabled(false);
			}
		}
	}
	private class ButtonBoardListener implements ActionListener	{
		
		private int myX, myY;
		public ButtonBoardListener(int x, int y)	{
			button_board[x][y].setEnabled(false);
			myX=x;
			myY=y;
		}
		public void actionPerformed(ActionEvent e)	{
			OthelloAI ai=new OthelloAI();
			board.selectMove(myX, myY);
			updateBoard();
			
			Coord ai_choice=ai.chooseMove(board);
			System.out.println("ai choice: "+ai_choice);
			//button_board[ai_choice.getX()][ai_choice.getY()].setBackground(Color.yellow);
			
			try	{
				TimeUnit.MILLISECONDS.sleep(500);
			}
			catch(InterruptedException f)	{
				System.out.println("INTERRUPTED EXCEPTION");
			}
			board.selectMove(ai_choice.getX(), ai_choice.getY());
			updateBoard();
			ArrayList<Coord> legal=board.validMoves();
			
			for(int i=0; i<legal.size(); i++)	{
				button_board[legal.get(i).getX()][legal.get(i).getY()].setEnabled(true);
				button_board[legal.get(i).getX()][legal.get(i).getY()].setBackground(Color.green);
			}
			turn_label.setText("It is "+board.getTurn()+"'s turn.");
			score_label.setText("w: "+board.getWhiteScore()+" b: "+board.getBlackScore());
			board.printBoard();
			if(board.validMoves().size()==0)	{
				String who_won="";
				if(board.getWhiteScore()>board.getBlackScore())
					who_won="The computer won!";
				if(board.getWhiteScore()<board.getBlackScore())
					who_won="The human won!";
				if(board.getWhiteScore()==board.getBlackScore())
					who_won="It's a tie!";
				score_label.setText(who_won);
			}
		}
	}
}

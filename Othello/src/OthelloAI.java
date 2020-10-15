import java.util.*;
import java.lang.Math.*;
public class OthelloAI {
	public OthelloAI()	{
		
	}
	public Coord chooseMove(Board b)	{
		Coord[] res=alphaBetaMiniMax(b, 3, -999999, 999999);
		return res[1];
	}
	private Coord[] alphaBetaMiniMax(Board board, int depth, int a, int b)	{
		ArrayList<Coord> poss=board.validMoves();
		Coord bestMove=new Coord(-999, -999);
		Coord worstMove=new Coord(-999,-999);
		Coord[] ret=new Coord[2];
		if(depth==0 || poss.size()==0)	{
			int e=evaluate(board);
			ret[0]=new Coord(e,e);
			ret[1]=new Coord(-1,-1);
			return ret;
		}
		char turn=board.getTurn();
		if(turn=='b')	{
			int v=-999999;
			for(int i=0; i<poss.size(); i++)	{
				Board temp_b=new Board(board);
				temp_b.selectMove(poss.get(i).getX(),poss.get(i).getY());
				Coord[] out=alphaBetaMiniMax(temp_b, depth-1, a, b);
				int score=out[0].getX();
				if(score>v)	{
					v=score;
					bestMove=poss.get(i);
				}
				a=Math.max(a, v);
				if(b<=a)
					break;
			}
			ret[0]=new Coord(v,v);
			ret[1]=bestMove;
			return ret;
		}
		else	{
			int v=999999;
			for(int i=0; i<poss.size(); i++)	{
				Board temp_b=new Board(board);
				temp_b.selectMove(poss.get(i).getX(),poss.get(i).getY());
				Coord[] out=alphaBetaMiniMax(temp_b, depth-1, a, b);
				int score=out[0].getX();
				if(score<v)	{
					v=score;
					worstMove=poss.get(i);
				}
				a=Math.min(a, v);
				if(b<=a)
					break;
			}
			ret[0]=new Coord(v,v);
			ret[1]=worstMove;
			return ret;
		}
	}
	public int evaluate(Board b)	{
		int score=0;
		char turn=b.getTurn();
		char opp;
		if(turn=='w')
			opp='b';
		else
			opp='w';
		
		ArrayList<Coord> corner=new ArrayList<Coord>();
		corner.add(new Coord(0,0));
		corner.add(new Coord(0,7));
		corner.add(new Coord(7,7));
		corner.add(new Coord(7,0));
		
		ArrayList<Coord> edge=new ArrayList<Coord>();
		int j=1;
		while(j<8)	{
			edge.add(new Coord(j,0));
			edge.add(new Coord(j,7));
			edge.add(new Coord(0,j));
			edge.add(new Coord(7,j));
			j=j+1;
		}
		Coord coord;
		int x, y;
		char[][] c_b=b.getBoard();
		for(int i=0; i<corner.size(); i++)	{
			coord=corner.get(i);
			x=coord.getX();
			y=coord.getY();
			if(c_b[x][y]==turn)
				score+=20;
			if(c_b[x][y]==opp)
				score-=20;
		}
		
		for(int i=0; i<edge.size(); i++)	{
			coord=edge.get(i);
			x=coord.getX();
			y=coord.getY();
			if(c_b[x][y]==turn)
				score+=5;
			if(c_b[x][y]==opp)
				score-=5;
		}
		ArrayList<Coord> bad=new ArrayList<Coord>();
		
		j=1;
		while(j<7)	{
			bad.add(new Coord(1,j));
			bad.add(new Coord(j,1));
			bad.add(new Coord(j,6));
			bad.add(new Coord(6,j));
			j=j+1;
		}
		
		for(int i=0; i<bad.size(); i++)	{
			coord=bad.get(i);
			x=coord.getX();
			y=coord.getY();
			if(c_b[x][y]==turn)
				score-=2;
			if(c_b[x][y]==opp)
				score+=2;
		}
		return score;
	}
	
}

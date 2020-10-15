import java.util.*;
public class Board {
	private char[][] board;
	private char turn;
	private ArrayList<Coord> white_moves;
	private ArrayList<Coord> black_moves;
	private ArrayList<Coord> directions;
	public Board()	{
		board=new char[8][8];
		for(int i=0; i<board.length; i++)	{
			for(int j=0; j<board[0].length; j++)	{
				board[i][j]='e';
			}
		}
		board[3][3]='w';
		board[3][4]='b';
		board[4][3]='b';
		board[4][4]='w';
		
		white_moves=new ArrayList<Coord>();
		black_moves=new ArrayList<Coord>();
		directions=new ArrayList<Coord>();
		
		white_moves.add(new Coord(3,3));
		white_moves.add(new Coord(4,4));
		black_moves.add(new Coord(3,4));
		black_moves.add(new Coord(4,3));
		
		directions.add(new Coord(1,0));
		directions.add(new Coord(-1,0));
		directions.add(new Coord(0,1));
		directions.add(new Coord(0,-1));
		directions.add(new Coord(1,1));
		directions.add(new Coord(1,-1));
		directions.add(new Coord(-1,1));
		directions.add(new Coord(-1,-1));
		
		turn='b';
	}
	public Board(Board b)	{
		board=new char[8][8];
		char[][] b_board=b.getBoard();
		for(int i=0; i<b_board.length; i++)	{
			for(int j=0; j<b_board[0].length; j++)	{
				board[i][j]=b_board[i][j];
			}
		}
		
		white_moves=new ArrayList<Coord>();
		black_moves=new ArrayList<Coord>();
		
		for(int i=0; i<board.length; i++)	{
			for(int j=0; j<board[0].length; j++)	{
				if(board[i][j]=='w')
					white_moves.add(new Coord(i,j));
				if(board[i][j]=='b')
					black_moves.add(new Coord(i, j));
			}
		}
		turn=b.getTurn();
		directions=new ArrayList<Coord>();
		directions.add(new Coord(1,0));
		directions.add(new Coord(-1,0));
		directions.add(new Coord(0,1));
		directions.add(new Coord(0,-1));
		directions.add(new Coord(1,1));
		directions.add(new Coord(1,-1));
		directions.add(new Coord(-1,1));
		directions.add(new Coord(-1,-1));
	}
	public void setBoard(char[][] new_board)	{
		board=new_board;
	}
	public char[][] getBoard()	{
		return board;
	}
	public void printBoard()	{
		System.out.println("  0 1 2 3 4 5 6 7");
		String line="";
		for(int i=0; i<board.length; i++) {
			line=""+i;
			for(int j=0; j<board[0].length; j++)	{
				line+=" "+board[i][j];
			}
			System.out.println(line);
		}
		System.out.println("--------------");
	}
	public void selectMove(int x, int y)	{
		char opp;
		if(turn=='w')
			opp='b';
		else
			opp='w';
		int temp_x=0;
		int temp_y=0;
		ArrayList<Coord> validMoves=validMoves();
		ArrayList<Coord> flip=new ArrayList<Coord>();
		ArrayList<Coord> flip_direct=new ArrayList<Coord>();
		boolean should_flip=false;
		Coord move=new Coord(x,y);
		int count=0;
		boolean is_valid=false;
		//System.out.println(validMoves);
		//System.out.println(move);
		for(int i=0; i<validMoves.size(); i++)	{
			if(validMoves.get(i).equals(move))
				is_valid=true;
		}
		if(is_valid==false)	{
			System.out.println("unable to make move, not legal");
		}
		else	{
			for(int i=0; i<directions.size(); i++)	{
				temp_x=move.getX();
				temp_y=move.getY();
				flip_direct.clear();
				should_flip=false;
				count=0;
				flip_direct.add(move);
				while(isInBound(temp_x, temp_y))	{
					if(board[temp_x][temp_y]=='e' && count!=0)	{
						should_flip=false;
						break;
					}
					if(board[temp_x][temp_y]==turn)	{
						should_flip=true;
						break;
					}
					if(board[temp_x][temp_y]==opp)
						flip_direct.add(new Coord(temp_x, temp_y));
					temp_x=temp_x+directions.get(i).getX();
					temp_y=temp_y+directions.get(i).getY();
					count=count+1;
				}
				if(should_flip)	{
					for(int j=0; j<flip_direct.size(); j++)
						flip.add(flip_direct.get(j));
				}
			}
		}

		for(int i=0; i<flip.size(); i++)	{
			board[flip.get(i).getX()][flip.get(i).getY()]=turn;
			if(turn=='w')	{
				if(!white_moves.contains(flip.get(i)))
					white_moves.add(flip.get(i));
				black_moves.remove(flip.get(i));
			}
			else	{
				if(!black_moves.contains(flip.get(i)))
					black_moves.add(flip.get(i));
				white_moves.remove(flip.get(i));
			}
		}
		changeTurn();
	}
	public void changeTurn()	{
		if(turn=='w')
			turn='b';
		else
			turn='w';
	}
	public char getTurn()	{
		return turn;
	}
	private boolean isTaken(int x, int y)	{
		if(board[x][y]=='e')
			return false;
		return true;
	}
	private boolean isInBound(int x, int y)	{
		if(x>=0 && x<=7 && y>=0 && y<=7)
			return true;
		return false;
	}
	public ArrayList<Coord> validMoves()	{
		//System.out.println("white moves: "+white_moves);
		//System.out.println("black moves: "+black_moves);
		ArrayList<Coord> valid=new ArrayList<Coord>();
		ArrayList<Coord> opp_moves;
		int poss_x=0;
		int poss_y=0;
		int dx=0;
		int dy=0;
		int temp_x=0;
		int temp_y=0;
		boolean is_valid=false;
		if(turn=='w')
			opp_moves=black_moves;
		else
			opp_moves=white_moves;
		int count = 0;
		for(int i=0; i<opp_moves.size(); i++)	{
			for(int j=0; j<directions.size(); j++)	{
				is_valid=false;
				dx=directions.get(j).getX();
				dy=directions.get(j).getY();
				poss_x=opp_moves.get(i).getX()+dx;
				poss_y=opp_moves.get(i).getY()+dy;
				if(isInBound(poss_x, poss_y) && !isTaken(poss_x, poss_y))	{
					count=0;
					//System.out.println("here: "+poss_x+" "+poss_y);
					temp_x=poss_x;
					temp_y=poss_y;
					while(isInBound(temp_x, temp_y))	{
						//System.out.println("here 2: "+temp_x+" "+temp_y);
						if(board[temp_x][temp_y]=='e' && count!=0)	{
							is_valid=false;
							break;
						}
						if(board[temp_x][temp_y]==turn && count!=0)	{
							is_valid=true;
							break;
						}
						temp_x=temp_x-dx;
						temp_y=temp_y-dy;
						count=count+1;
					}
					Coord new_move=new Coord(poss_x, poss_y);
					if(!valid.contains(new_move)&&is_valid)
						valid.add(new_move);
				}
			}
		}
		return valid;
	}
	public int getWhiteScore()	{
		return white_moves.size();
	}
	public int getBlackScore()	{
		return black_moves.size();
	}
}


public class Coord implements Comparable<Coord> {
	private int myX, myY;
	public Coord(int x, int y)	{
		myX=x;
		myY=y;
	}
	public int getX()	{
		return myX;
	}
	public int getY()	{
		return myY;
	}
	
	@Override
	public boolean equals(Object o)	{
		if(!(o instanceof Coord))
			return false;
		Coord c=(Coord)o;
		return myX==c.getX() && myY==c.getY();
	}
	public String toString()	{
		return "("+myX+", "+myY+")";
	}
	public int compareTo(Coord c)	{
		if(myX==c.getX() && myY==c.getY())
			return 0;
		if(myX-c.getX()>0)
			return 1;
		else
			return 0;
	}
}

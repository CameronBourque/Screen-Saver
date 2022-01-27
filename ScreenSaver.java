/*
 *Cameron Bourque
 *ScreenSaver Lab
 *11/30/16
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


/**
 * Creates an instance of the GfxApp class, which uses the Circle class, Coord class, and
 * a queue to create a screen saver.
 * @param args not used
 */
public class ScreenSaver
{    
	public static void main(String args[])  
	{
		GfxApp gfx = new GfxApp();
	}
}


/**
 * Creates a Screen Saver by placing Circle coordinates in a queue
 */
class GfxApp extends JFrame
{
	
	private int circleCount, circleSize;
	public static final int TIME_DELAY = 5; // controls the speed
	public static final int TOTAL_NUM_CIRCLES = 100; // controls how long it goes
	
	/**
	 * Creates a GfxApp with 50 circles with diameter 30
	 */
	public GfxApp()
	{
		circleCount = 15;
		circleSize  = 75;
		
		setSize(1920,1048);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
			
   	/**
   	 * Draws a stream of circleCount circles of size circleSize.  Uses a queue to erase circles
   	 * at the end of the stream.  The total number of circles that will be drawn is 2000.
   	 * @param g the Graphics object
   	 */
	public void paint(Graphics g) 
	{
		int incX = 5;	// initial x increment for circle locations
		int incY = 5;	// initial y increment for circle locations
		
		Circle c = new Circle(g,circleSize,incX,incY,TIME_DELAY);
		Circle d = new Circle(g,circleSize,0,0,TIME_DELAY);
		int countD = 0;
		Queue<Coord> queue = new LinkedList<Coord>();
		try
		{
			g.setColor(Color.black);
			g.fillRect(0,0,1920,1080);
			for(int i = 1; i <= TOTAL_NUM_CIRCLES; i++)
			{
				queue.add(new Coord(c.getTLX(),c.getTLY()));
				c.drawCircle();
				c.hitEdge();
				if(queue.size()>circleCount)
				{
					i--;
					c.eraseDraw(queue.remove());
				}
				if(c.hitEdge())
				{
					d.setTL(c.getTLX(),c.getTLY());
					d.drawCircle();
				}
				if(countD<5)
				{
					d.setSize(d.getSize()+3);
					d.drawCircle();
				}
			}
				
		}
		catch(InterruptedException e){}
		// complete this method
	} 
}
      
      
/**
 * A class to represent Circle objects.  Circles can be drawn and erased.
 */
class Circle
{
	private int tlX;		// top-left X coordinate
	private int tlY;		// top-left Y coordinate
	private int incX;		// increment movement of X coordinate
	private int incY;		// increment movement of Y coordinate
	private boolean addX;	// flag to determine add/subtract of increment for X
	private boolean addY;	// flag to determine add/subtract of increment for Y
	private int size;		// diameter of the circle
	private int timeDelay;	// time delay until next circle is drawn
    private Graphics g;
    private Color col;
    /**
     * Creates a Circle with a specified Graphics, size, x increment, y increment and time delay
     */ 
	public Circle(Graphics g, int s, int x, int y, int td)
	{
		incX = x;
		incY = y;
		size = s;
		addX = true;
		addY = false;
		tlX = (int)(Math.random() * g.getClipBounds().getWidth());		
		tlY = (int)(Math.random() * g.getClipBounds().getHeight());	
		timeDelay = td;
		this.g = g;
		col = Color.red;
	}
	/**
	 * returns the top left X of this circle
	 * @return tlX
	 */
   	public int getTLX() { return tlX;}
   	
   	/**
   	 * returns the top left Y of this circle
   	 * @return tlY
   	 */
	public int getTLY() { return tlY;}
	
	public void setTLX(int x){	tlX = x; }
	public void setTLY(int y){	tlY = y; }
	public void setSize(int s){	size = s; }
	public int getSize(){	return size; }
	public void setTL(int x, int y)
	{
		tlX = x;
		tlY = y;
	}
   	/**
   	 * delays the program for a specified number of miliseconds
   	 * @param n number of miliseconds
   	 */
	public void delay(int n) throws InterruptedException
	{
		Thread.sleep(n);
	}

	/**
	 * draws a blue circle and sets the tlX and tlY for the next drawing
	 * @param g Graphics object
	 */
	public void drawCircle() throws InterruptedException
	{
		g.setColor(col);
		g.drawOval(tlX,tlY,size,size);
		delay(timeDelay);
		if (addX)
			tlX+=incX;
		else
			tlX-=incX;
		if (addY)
			tlY+=incY;
		else
			tlY-=incY;
	}
    public void fillCircle() throws  InterruptedException
    {
    	g.setColor(col);
    	g.fillOval(tlX,tlY,size,size);
    	delay(timeDelay);
    	if(addX)
    		tlX+=incX;
    	else
    		tlX-=incX;
    	if(addY)
    		tlY+=incY;
    	else
    		tlY-=incY;
    }
   	/**
   	 * Randomly sets a new direction for the circle by randomly setting
   	 * the x increment and y increment
   	 */ 
	public void newData()
	{
		incX = (int) Math.round(Math.random() * 7 + 5);
		incY = (int) Math.round(Math.random() * 7 + 5);
	}

	/**
	 * Determines if any of the four edges have been hit, and if so, reverses the
	 * appropriate flags (addX and addY) and calls newData
	 */
	public boolean hitEdge()
	{
		// complete this method
		boolean hit = false;
		if(tlX<=8)
		{
			addX = true;
			newData();
			changeColor();
			hit = true;
		}
		if(tlY<=28)
		{
			addY = true;
			newData();
			changeColor();
			hit = true;
		}
		if(tlX+size>=g.getClipBounds().getWidth()+8)
		{
			addX = false;
			newData();
			changeColor();
			hit = true;
		}
		if(tlY+size>=g.getClipBounds().getHeight()+35)
		{
			addY = false;
			newData();
			changeColor();
			hit = true;
		}
		return hit;
	}
	public void changeColor()
	{
		if(col.equals(Color.red))
		{
			g.setColor(Color.orange);
			col = Color.orange;
		}
		else if(col.equals(Color.orange))
		{
			g.setColor(Color.yellow);
			col = Color.yellow;
		}
		else if(col.equals(Color.yellow))
		{
			g.setColor(Color.green);
			col = Color.green;
		}
		else if(col.equals(Color.green))
		{
			g.setColor(Color.cyan);
			col = Color.cyan;
		}
		else if(col.equals(Color.cyan))
		{
			g.setColor(Color.blue);
			col = Color.blue;
		}
		else if(col.equals(Color.blue))
		{
			g.setColor(Color.magenta);
			col = Color.magenta;
		}
		else if(col.equals(Color.magenta))
		{
			g.setColor(Color.red);
			col = Color.red;
		}
		else
		{
			g.setColor(Color.black);
			col = Color.black;
		}
	}
	// add an eraseCircle method
	public void eraseCircle(Coord c)
	{
		
		g.setColor(Color.black);
		g.fillOval(c.getX(),c.getY(),size,size);
		g.setColor(col);
	}
	public void eraseDraw(Coord c)
	{
		g.setColor(Color.black);
		g.drawOval(c.getX(),c.getY(),size,size);
		g.setColor(col);
	}
}


// Create a Coord class, so that coordinates of drawn circles can be placed in the queue.
// As coordinates are removed from the queue, circles are erased with eraseCircle.
class Coord
{
	private int x;
	private int y;
	
	public Coord(int xCoord, int yCoord)
	{
		x = xCoord;
		y = yCoord;
	}
	public void setX(int xCoord)
	{
		x = xCoord;
	}
	public void setY(int yCoord)
	{
		y = yCoord;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}









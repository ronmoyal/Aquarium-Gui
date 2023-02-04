import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Fish extends Swimmable implements MarineAnimal {

	private final int EAT_DISTANCE = 4;
	private int size;
	private Color col;
	private int eatCount;
	private int x_front, y_front, x_dir, y_dir;
	private String ColorAnimal ;
	
	boolean isSuspended=false;
	private CyclicBarrier barrier=null;
	private HungerState currentState;
	

	public Fish(AquaPanel panel, int size, int horSpeed, int verSpeed, Color col, int foodFreq) {
		super(horSpeed, verSpeed, foodFreq,panel);
        this.size = size;
        this.col = col;
		this.x_dir=1;
		this.y_dir=1;
		currentState=new Satiated();
		
		

        start();
	}
//	public Fish(AquaPanel panel, int size, int horSpeed, int verSpeed, Color col, int ID) {
//		super(horSpeed, verSpeed);
//        this.panel = panel;
//        this.ID=ID;
//        this.size = size;
//        this.col = col;
//		this.x_dir=1;
//		this.y_dir=1;
//
//        start();
//	}


	public void run()
	
	{
		
		int count=0;
		while(true)
		{

			
			try{
				
				Thread.sleep(50); //sleep thread for 0.5 second
				if(isSuspended){ //if the animals stop (sleep)
					if(barrier!=null) {
						barrier.await();
					}
					synchronized(this){
						wait();
					}
				}
				if(!isSuspended) { //if the animals are not sleep
					synchronized(this){
						notify();
					}
				}
				
				
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			panel.repaint();
			
			this.x_front+=horSpeed*x_dir;
			this.y_front+=horSpeed*y_dir;
			if(x_front>panel.getWidth()) {
				x_dir=-1;
				count++;
				if(count==this.foodFreq) {
					this.fireHungry();
					count=0;
					currentState=new Hungry();
					currentState.setState(this);
				}
			}
			if(y_front>panel.getHeight())
				y_dir=-1;
				
			if(x_front<0) {
				x_dir=1;
				count++;
				if(count==this.foodFreq) {
					this.fireHungry();
					count=0;
					currentState=new Hungry();
					currentState.setState(this);
					
				}
			}
			if(y_front<0)
				y_dir=1;
			
			if(Singleton.get()!=null && getHungerState()=="Hungry") { //if worm is in the aquarium - change the direction
				if(x_front > panel.getWidth()/2)
					  x_dir = -1;
				else
					  x_dir=1;
				if(y_front > panel.getHeight()/2)
					  y_dir = -1;
				else
					  y_dir=1;
				synchronized(this){
					if((Math.abs(panel.getWidth()/2-x_front)<=5) && (Math.abs(panel.getHeight()/2-y_front)<=5)){ // if the object is close to the worm minimum 5 pixels
						panel.eatFood(this);
						panel.setWormInstance(); //set wormInstance to null
						panel.repaint(); //remove the worm from aquarium
					}
				}
			}
		}
	}
	


	@Override
	//get fish color
	public String getColor() {
		if (col == Color.BLUE)
			ColorAnimal = "Blue";
		else if (col == Color.RED)
			ColorAnimal = "Red";
		else if (col == Color.GREEN)
			ColorAnimal = "Green";
		else if (col == Color.ORANGE)
			ColorAnimal = "Orange";
		else if (col == Color.CYAN)
			ColorAnimal = "Cyan";
		else if (col == Color.PINK)
			ColorAnimal = "Pink";
		else if (col == Color.BLACK)
			ColorAnimal = "Black";
		
		else 
			return ("("+col.getRed()+","+col.getGreen()+","+col.getBlue()+")");
		return ColorAnimal;
	}

	
	//set the count of fish eat
	public boolean setEatCount(int eatCount) {
		this.eatCount = eatCount;
		return true;
	}

	//change fish size
	public void changeFish() {
		this.size+=1;
	}


	//update eat counter and size
	@Override
	public void eatInc() {
		this.eatCount = eatCount + 1;
		
		if (eatCount == EAT_DISTANCE) {
			changeFish();
			this.eatCount = 0;
		}
		currentState = new Satiated();
	}

	/**
	 * get EAT_DISTANCE
	 * @return
	 */
	public int getEAT_DISTANCE() {
		return EAT_DISTANCE;
	}

	/**
	 * a method that get the animal name
	 * @return "Fish"
	 */
	@Override
	public String getAnimalName() {
		return "Fish";
	}

	/**
	 * get eat count food
	 */
	@Override
	public int getEatCount() {

		return this.eatCount;
	}

	/**
	 * get size of fish
	 */
	@Override
	public int getSize() {
		return this.size;
	}

	//set fish size
	public boolean setSize(int size) {
		this.size = size;
		return true;
	}

	
	public void drawAnimal(Graphics g)
	{
	   g.setColor(col);
	   if(x_dir==1) // fish swims to right side
	   {
		// Body of fish
		g.fillOval(x_front - size, y_front - size/4, size, size/2);

		// Tail of fish
		int[] x_t={x_front-size-size/4,x_front-size-size/4,x_front-size};
		int [] y_t = {y_front - size/4, y_front + size/4, y_front};
		Polygon t = new Polygon(x_t,y_t,3);		
		g.fillPolygon(t);

		// Eye of fish
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(255-col.getRed(),255-col.getGreen(),255- col.getBlue()));
		g2.fillOval(x_front-size/5, y_front-size/10, size/10, size/10);
				
		// Mouth of fish
		if(size>70)
			g2.setStroke(new BasicStroke(3));
		else if(size>30)
			g2.setStroke(new BasicStroke(2));
		else
			g2.setStroke(new BasicStroke(1));
	      g2.drawLine(x_front, y_front, x_front-size/10, y_front+size/10);
	      g2.setStroke(new BasicStroke(1));
	   }
	   else // fish swims to left side
	   {
		// Body of fish
		g.fillOval(x_front, y_front - size/4, size, size/2);

		// Tail of fish
		int[] x_t={x_front+size+size/4,x_front+size+size/4,x_front+size};
		int [] y_t = {y_front - size/4, y_front + size/4, y_front};
		Polygon t = new Polygon(x_t,y_t,3);		
		g.fillPolygon(t);

		// Eye of fish
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(255-col.getRed(),255-col.getGreen(),255-col.getBlue()));
		g2.fillOval(x_front+size/10, y_front-size/10, size/10, size/10);
				
		// Mouth of fish
		if(size>70)
			g2.setStroke(new BasicStroke(3));
		else if(size>30)
			g2.setStroke(new BasicStroke(2));
		else
			g2.setStroke(new BasicStroke(1));
	      g2.drawLine(x_front, y_front, x_front+size/10, y_front+size/10);
	      g2.setStroke(new BasicStroke(1));
	   }
	}

	
	//set fish to suspend (sleep)
	@Override
	public void setSuspend() {
		isSuspended=true;

	}
	
	//check if fish suspend (get function)
	public boolean getSuspend(){return isSuspended;}

	
	@Override
	public void setResume() {
		synchronized(this){
			isSuspended=false;
			notify();
		}

	}

	
	@Override
	public void setBarrier(CyclicBarrier b) {
		this.barrier=b;

	}


	@Override
	public void drawCreatrue(Graphics g) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Swimmable clone() {
		return new Fish(panel,size,horSpeed,verSpeed,col,foodFreq);
		
	}
	@Override
	public void paintFish(Color col) {
		this.col=col;
		
	}
	
	public Color getClr()
	{
		return col;
	}
	public void setColor(Color c) {
		this.col = c;
	}


	@Override
	public int getX_front() {
		// TODO Auto-generated method stub
		return x_front;
	}


	@Override
	public int getY_front() {
		// TODO Auto-generated method stub
		return y_front;
	}


	@Override
	public int getX_dir() {
		// TODO Auto-generated method stub
		return x_dir;
	}


	@Override
	public int getY_dir() {
		// TODO Auto-generated method stub
		return y_dir;
	}


	@Override
	public void setState(Color col, int size, int x_front, int y_front, int horSpeed, int verSpeed, int x_dir, int y_dir) {
		this.col=col;
		this.size=size;
		this.x_front=x_front;
		this.y_front=y_front;
		this.horSpeed=horSpeed;
		this.verSpeed=verSpeed;
		this.x_dir=x_dir;
		this.y_dir=y_dir;
		
	}


	@Override
	public void setHungeryState(HungerState state) {
		this.currentState=state;

	}


	@Override
	public String getHungerState() {
		
		return currentState.toString();
	}

}

	

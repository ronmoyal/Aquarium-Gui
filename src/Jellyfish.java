import java.awt.Color;

import java.awt.Graphics;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Jellyfish extends Swimmable implements MarineAnimal{

	private final int EAT_DISTANCE=4;
	private int size;
	private Color col;
	private int eatCount;
	private int x_front, y_front, x_dir, y_dir;
	
	boolean isSuspended=false;
	private CyclicBarrier barrier=null;
	private String ColorAnimal;
	private HungerState currentState;
	
	public Jellyfish(AquaPanel panel,int size,int horSpeed,int verSpeed,Color col, int foodFreq)
	{
		super(horSpeed, verSpeed, foodFreq,panel);

        this.size = size;
        this.col = col;
        this.x_dir = 1;
        this.y_dir = 1;
        currentState=new Satiated();
        
 
		start();
	}
	
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
			
			if(Singleton.get()!=null&&getHungerState()=="Hungry") { //if worm is in the aquarium - change the direction
				if(x_front > panel.getWidth()/2)
					  x_dir = -1;
				else
					  x_dir=1;
				if(y_front > panel.getHeight()/2)
					  y_dir = -1;
				else
					  y_dir=1;
				if((Math.abs(panel.getWidth()/2-x_front)<=5) && (Math.abs(panel.getHeight()/2-y_front)<=5)){ //if the object is close to the worm minimum 5 pixels
					panel.eatFood(this);
					panel.setWormInstance(); //set the wormInstance to null		
					panel.repaint(); //remove the worm from aquarium
				}
			}
		}
	}
	
	
	
	/**
	 * get jellyfish
	 * @return jellyfish color
	 */
	@Override
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
	
	//change Jellyfish size
	public void changeJellyfish() {
		this.size+=1;
	}


	//update eat counter and size
	@Override
	public void eatInc() {
		this.eatCount = eatCount + 1;
		if (eatCount == EAT_DISTANCE) {
			changeJellyfish();
			this.eatCount = 0;
		}
		currentState = new Satiated();

		
	}


	/**
	 * a method that get the animal name
	 * @return "jellyfish"
	 */
	@Override
	public String getAnimalName() {
		return "Jellyfish";
	}

	/**
	 * a method that get the count of jellyfish food
	 * @return eat count
	 * 
	 */
	@Override
	public int getEatCount() {
		
		return this.eatCount;
	}

	/**
	 * a method that get the size of the jellyfish
	 * @return jellyfish size
	 */
	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public void drawAnimal(Graphics g) {
		int numLegs;
		   if(size<40)
		    	numLegs = 5;
		   else if(size<80)
		    	numLegs = 9;
		   else
		    	numLegs = 12;

		   g.setColor(col);
		   g.fillArc(x_front - size/2, y_front - size/4, size, size/2, 0, 180);
				
		   for(int i=0; i<numLegs; i++)
			g.drawLine(x_front - size/2 + size/numLegs + size*i/(numLegs+1), y_front, x_front - size/2 + size/numLegs + size*i/(numLegs+1), y_front+size/3);
		
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
		return new Jellyfish(panel,size,horSpeed,verSpeed,col,foodFreq);
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
		currentState=state;
		
	}

	@Override
	public String getHungerState() {
		return currentState.toString();
	}
}

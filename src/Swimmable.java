

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.CyclicBarrier;

public abstract class Swimmable extends Thread implements SeaCreature{
 
	protected int horSpeed, verSpeed, foodFreq;
	protected int swimID;
	private static int count=0;
	protected AquaPanel panel=null;
	protected PropertyChangeSupport support;
	
		
	public Swimmable() {
		this.swimID=++count;
			horSpeed = 0;
			verSpeed = 0;
	}
	
	
	public Swimmable(int hor, int ver, int food, AquaPanel panel) {
		this.swimID=++count;
			foodFreq = food;
			horSpeed = hor;
			verSpeed = ver;
			this.panel=panel;
			this.support=new PropertyChangeSupport(this);
			this.addListener(panel);
			
	} 
	public int getHorSpeed() { return horSpeed; }
	public int getVerSpeed() { return verSpeed; }
	public void setHorSpeed(int hor) { horSpeed  = hor; }
	public void setVerSpeed(int ver) { verSpeed  = ver; }

	abstract public String getAnimalName();
	abstract public void setHungeryState(HungerState state);
	abstract public String getHungerState();
	public int getID(){return swimID;}
	public void setCount() {count=0; swimID=0;}
	abstract public void drawAnimal(Graphics g);
	abstract public void setSuspend();
	abstract public boolean getSuspend();
	abstract public void setResume();
	abstract public void setBarrier(CyclicBarrier b);
	abstract public int getSize();
	abstract public void eatInc();
	abstract public int getEatCount();
	abstract public String getColor();
	abstract public Color getClr();
	abstract void setColor(Color color);
	abstract public Swimmable clone();
	abstract public int getX_front();
	abstract public int getY_front();
	abstract public int getX_dir();
	abstract public int getY_dir();
	abstract public void setState(Color col,int size,int x_front,int y_front,int horSpeed,int verSpeed,int x_dir,int y_dir);

	
	public void addListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void fireHungry() {
		this.support.firePropertyChange("Hungry",1,0);
	}
}

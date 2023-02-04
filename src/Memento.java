import java.awt.Color;


public class Memento {

	private Swimmable swim=null;
	private Color col;
	private String color,name;
	private int size,horSpeed,verSpeed,ID,x_front,y_front,x_dir,y_dir;
	
	
	
	
	
	public Memento(Swimmable swim) {
		this.swim = swim;
		this.col = swim.getClr();
		this.size=swim.getSize();
		this.horSpeed = swim.getHorSpeed();
		this.verSpeed = swim.getVerSpeed();
		this.ID = swim.getID();
		this.name = swim.getAnimalName();
		this.x_front = swim.getX_front();
		this.y_front = swim.getY_front();
		this.x_dir = swim.getX_dir();
		this.y_dir = swim.getY_dir();
		this.color=swim.getColor();
}
	public Memento(Immobile plant) {
		this.size = plant.getSize();
		this.ID = plant.getID();
		this.name = plant.getPlantName();
		this.x_front = plant.getX();
		this.y_front = plant.getY();
		this.col = plant.getColorPlant();
		this.color=plant.getColor();
	}
	
	public Color getClr() { return col;}
	public String getColor() { return color;}
	public int getX_front() { return x_front;}
	public int getX_dir() { return x_dir;}
	public int getY_front() { return y_front;}
	public int getY_dir() { return y_dir;}
	public int getHoSpeed() { return horSpeed;}
	public int getVerSpeed() { return verSpeed;}
	public int getID() { return ID;}
	public int getSize() { return size;}
	public String getName() { return name;}
}
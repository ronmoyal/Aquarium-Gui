import java.awt.Color;

public abstract class Immobile implements SeaCreature {
	private String name;
	private static int count=0;
	public final int objectID;
	protected int size,x,y;
	protected Color clr;
	protected AquaPanel panel;
	public Immobile(AquaPanel panel,int size,String name,int x,int y){
		this.objectID=++count;
		this.name=name;
		this.panel=panel;
		this.size=size;
		this.x=x;
		this.y=y;
		this.clr=Color.green;
	}
	public int getID(){return objectID;}
	public String getPlantName(){return name;}
	public String getColor(){return "green";}
	public int getSize(){return this.size;}
	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public Color getColorPlant(){return this.clr;}
	public  void setState(Color clr,int size,int x,int y) {
		this.clr=clr;
		this.size=size;
		this.x=x;
		this.y=y;
	
	}

}

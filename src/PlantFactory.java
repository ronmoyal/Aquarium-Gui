import java.awt.Color;

public class PlantFactory implements AbstractSeaFactory {

	Color clr;
	int size,x,y;
	AquaPanel panel;
	
	public PlantFactory(AquaPanel panel,int size,int x,int y){
		this.clr=Color.green;
		this.size=size;
		this.x=x;
		this.y=x;
		this.panel=panel;
		
	}
	public SeaCreature produceSeaCreature(String type){
		if(type.equalsIgnoreCase("Laminaria"))
			return new Laminaria(panel,size,x,y);
		
		else if(type.equalsIgnoreCase("Zostera")){
			return new Zostera(panel,size,x,y); 
		}
		return null;

	}

}
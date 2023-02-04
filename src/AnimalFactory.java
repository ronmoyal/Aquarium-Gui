import java.awt.Color;


public class AnimalFactory implements AbstractSeaFactory {
	
	private Color clr;
	private int size,HSpeed,VSpeed,foodFreq;
	private AquaPanel panel;
	
	public AnimalFactory(AquaPanel panel,int size,int horSpeed,int verSpeed,Color clr,int foodFreq)
	{
		this.foodFreq=foodFreq;
		this.clr=clr;
		this.size=size;
		this.HSpeed=horSpeed;
		this.VSpeed=verSpeed;
		this.foodFreq=foodFreq;
		this.panel=panel;
		
	}
		


	@Override
	public SeaCreature produceSeaCreature(String type){
		if(type.equalsIgnoreCase("Fish")){
			return new Fish(panel,size,HSpeed,VSpeed,clr,foodFreq); 
		}
			
		else if(type.equalsIgnoreCase("Jellyfish"))
			return new Jellyfish(panel,size,HSpeed,VSpeed,clr,foodFreq);
			
		return null;
	}
		
}
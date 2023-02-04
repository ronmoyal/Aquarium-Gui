import java.awt.Color;

public class MarineAnimalDecorator implements MarineAnimal{
	
	MarineAnimal animal;
	
	public MarineAnimalDecorator(MarineAnimal marin) {
		this.animal=marin;
	}

	@Override
	public void paintFish(Color col) {
		animal.paintFish(col);
		
	}
	
}

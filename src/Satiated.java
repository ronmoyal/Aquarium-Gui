public class Satiated implements HungerState {

	@Override
	public String toString(){
		return "Satiated";
	}

	@Override
	public void setState(Swimmable objSwim) {
		objSwim.setHungeryState(this);
	}

	@Override
	public String getState() {
		return "Satiated";
		
	}
}
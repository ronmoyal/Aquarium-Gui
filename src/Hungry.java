public class Hungry implements HungerState {

	@Override
	public String toString(){
		return "Hungry";
	}

	@Override
	public void setState(Swimmable objSwim) {
		objSwim.setHungeryState(this);
	}

	@Override
	public String getState() {
		return "Hungry";
		
	}

}
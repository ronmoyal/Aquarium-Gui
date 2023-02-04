//import java.util.HashMap;
import java.util.HashSet;


public class State {

		private HashSet<Memento> swimmableMementoHash;
		private HashSet<Memento> immobileMementoHash;
		private static int counter=0;
		public final int objectID;
		
		public State()
		{
			this.objectID=++counter;
			
			swimmableMementoHash=new HashSet<Memento>();
			immobileMementoHash=new HashSet<Memento>();
			
		}
		public int getID(){return objectID;}

		public void addSwimmableMemento(int idObj,Memento state)
		{

			swimmableMementoHash.add(state);

		}
		public void addImmobileMemento(int idObj,Memento state)
		{

			immobileMementoHash.add(state);
		}
		
		public HashSet<Memento> getSwimmableMementohash(){return swimmableMementoHash;}
		public HashSet<Memento> getImmobileMementohash(){return immobileMementoHash;}

	

}

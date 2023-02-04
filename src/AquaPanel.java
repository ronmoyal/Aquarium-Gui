import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CyclicBarrier;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class AquaPanel extends JPanel implements ActionListener,PropertyChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int PANEL_WIDTH=1100;
	final int PANEL_HIGH=700;
	int xVel=1;
	int yVel=1;
	int x=0;
	int y=0;
	Image background; //aquarium background
	private int animalNum=0; //number of animals on the aquarium
	private JButton addAnimal, addPlant ,duplicateAnimal, decorator, sleep, wakeUp, reset, food, info, exit;
	private HashSet<Swimmable> animalHashSet; //animal hash set
	private HashSet<Immobile> plantHashSet;
	
	private String[] infoStrings;
	private String[] mementoStrings;
	private String[] immobileStrings;

	
	private Iterator <Swimmable>iterAnimals;
	private Iterator <Immobile>iterPlants;
	
	private int totalEating = 0, numOfClicks = 0;
	private String[] infoLabels = { "ID", "Animal", "Color", "Size", "Hor.Speed", "Ver.Speed", "Eat Counter"};
	private String[] mementoLabels = { "ID", "Animal", "Color", "Size", "Hor.Speed", "Ver.Speed"};
	private String[] immobileLabels = { "ID", "Sea Planet", "Color", "Size", "x : ", "y : "};

	private JTable infoTable;
	private JTable mementoTable;
	private JTable immobileTable;

	private JFrame frm;
	private JRadioButton Animal, SeaPlanet;
	private String typeName=null;
	
	
	private JFrame tempFrame;
	private JScrollPane scrollPane;
	
	private CyclicBarrier barrier;
	private Singleton worm = null; //worm object
	private int flagInfo=0;
	private State state  = null;
	private boolean flgSaveState=false;
	
	
	private boolean flagColor=false,flagSave=false;
	
	AquaPanel() { 
		
		//set animal hash set 
		animalHashSet = new HashSet<Swimmable>();
		plantHashSet=new HashSet<Immobile>();
		state = new State();
		
		//panel setting
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HIGH));
		this.setLayout(new BorderLayout());
		
//		//set worm object
//		wormInstance = new Worm();
		
		//set buttons in panel
		addAnimal = new JButton("Add Animal");
		addPlant = new JButton("Add Plant");
		duplicateAnimal = new JButton("Duplicate Animal");
		decorator = new JButton("Decorator");
		sleep = new JButton("Sleep");
		wakeUp = new JButton("Wake Up");
		reset = new JButton("Reset");
		food = new JButton("Food");
		info = new JButton("Info");
		exit = new JButton("Exit");
		
		addAnimal.setPreferredSize(new Dimension(109,27));
		addPlant.setPreferredSize(new Dimension(109,27));
		duplicateAnimal.setPreferredSize(new Dimension(109,27));
		
		//set button panel in panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,10,0,0)); 

		
		buttonPanel.add(addAnimal);
		buttonPanel.add(addPlant);
		buttonPanel.add(duplicateAnimal);
		buttonPanel.add(decorator);
		buttonPanel.add(sleep);
		buttonPanel.add(wakeUp);
		buttonPanel.add(reset);
		buttonPanel.add(food);
		buttonPanel.add(info);
		buttonPanel.add(exit);
		
		//add the button panel in the south
		this.add(buttonPanel, BorderLayout.SOUTH);

		//action listener for the button panel
		addAnimal.addActionListener(this);
		addPlant.addActionListener(this);
		duplicateAnimal.addActionListener(this);
		decorator.addActionListener(this);
		sleep.addActionListener(this);
		wakeUp.addActionListener(this);
		reset.addActionListener(this);
		info.addActionListener(this);
		food.addActionListener(this);
		exit.addActionListener(this);
		
		

	}
	
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(background!= null) {
			g.drawImage(background, 0, 0, this.getWidth(),  this.getHeight(), null);
		}
		
		for (Swimmable e:animalHashSet)
		{
			e.drawAnimal(g);
		}
		iterAnimals= animalHashSet.iterator(); //init he iterator
		iterPlants=plantHashSet.iterator();
		while(iterAnimals.hasNext()){
	 		iterAnimals.next().drawAnimal(g);
	 	}
		while(iterPlants.hasNext()){
	 		iterPlants.next().drawCreatrue(g);
	 	}
		if(worm!=null){ //if worm is on the screen
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.red);
			g2d.drawArc(getWidth()/2, getHeight()/2-5, 10, 10, 30, 210);
			g2d.drawArc(getWidth()/2, getHeight()/2+5, 10, 10, 180, 270);
			g2d.setStroke(new BasicStroke(1));
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == addAnimal) 
		{
			try {
				if(animalHashSet.size()>=5) 
					throw new Exception("you can add only 5 animals!"); //the limit of the aquarium is 5 animals
				else
				{
					new AddAnimalDialog(this,animalHashSet); //set animal dialog
				}
			}
			catch(Exception e1){
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		}
		
		else if(e.getSource() == addPlant) {
			try {
				if(plantHashSet.size()>=5) 
					throw new Exception("you can add only 5 plants!"); //the limit of the aquarium is 5 animals
				else
				{
					new AddPlantDialog(this, plantHashSet); //set animal dialog
				}
			}
			catch(Exception e1){
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		}
		
		else if(e.getSource() == duplicateAnimal) {
			try{
				if(animalHashSet.size()>=5)
					throw new Exception("duplicate ERROR!, you can add only 5 animals!");
				else if(animalHashSet.size()==0)
					throw new Exception("duplicate ERROR!, no animals in aqurium");
				else{
					DuplicateAnimalDialog duplicateAnimal=new DuplicateAnimalDialog(this,animalHashSet);	
				}
			}
			catch(Exception e1){
				 JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		}
		
		else if(e.getSource() == decorator) {
			this.flagInfo=1;
			infoTable();
			
			
			
		}
		
		else if(e.getSource() == info) {
			this.flagInfo=0;
			infoTable(); //function of info
		}
		
		else if ( e.getSource()== exit) {
			System.exit(0);
		}
		
		else if (e.getSource()== reset) {
			this.animalNum=0; //reset the number of animals
			animalHashSet.clear(); //clear details animal hash set
			
			
			
			
			this.repaint(); //clear all the animals in the frame
		}
		
		else if (e.getSource()== sleep) {
			iterAnimals = animalHashSet.iterator();
			while(iterAnimals.hasNext()) {
				iterAnimals.next().setSuspend(); //stop the animals
			}
		}
		
		else if(e.getSource() == wakeUp) {
			iterAnimals= animalHashSet.iterator(); 
		 	while(iterAnimals.hasNext()){
		 		iterAnimals.next().setResume(); //cancel the stop animals
		 	}
		}
		
		else if(e.getSource()==food)
		{
			try {
				if(animalHashSet.size()==0) {
					throw new Exception("food ERROR!, no animals in aqurium");
				}
				else {
					worm = Singleton.getInstance();
//					wormInstance.setTrue(); //the worm is on the aquarium
					if(animalNum>0) {
						barrier=new CyclicBarrier(animalHashSet.size());
					}
					iterAnimals=animalHashSet.iterator(); 
					Swimmable objSwim = (Swimmable) iterAnimals.next();
					if(!objSwim.getSuspend()) {
						objSwim.setBarrier(barrier);
					}
				}
			}
			catch(Exception e1){
				 JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		}
	}
	
	
	//details animals function
	public void infoTable()
	{
		
		++this.numOfClicks;
		DefaultTableModel model = new DefaultTableModel(infoLabels,0);
		
		infoStrings = new String[this.infoLabels.length];
		
		//set table
		for(Swimmable s : animalHashSet) {
			infoStrings[0] = Integer.toString(s.getID());
			infoStrings[1] = s.getAnimalName();
			infoStrings[2] = s.getColor();
			infoStrings[3] = Integer.toString(s.getSize());
			infoStrings[4] = Integer.toString(s.getVerSpeed());
			infoStrings[5] = Integer.toString(s.getHorSpeed());
			infoStrings[6] = Integer.toString(s.getEatCount());
			totalEating = totalEating + s.getEatCount();
			model.addRow(infoStrings);
		}
		this.infoTable = new JTable(model);
		infoTable.setVisible(true);
		this.scrollPane = new JScrollPane(infoTable);
		if(this.numOfClicks % 2 == 1) {
			tempFrame = new JFrame("Info");
			tempFrame.setSize(500,350);
			tempFrame.setTitle("Info");
			infoStrings[0] = "Total";
			infoStrings[1] = "";
			infoStrings[2] = "";
			infoStrings[3] = "";
			infoStrings[4] = "";
			infoStrings[5] = "";
			infoStrings[6] = Integer.toString(totalEating);//sum of eating
			
			model.addRow(infoStrings);
			tempFrame.setVisible(true);
			tempFrame.add(scrollPane,BorderLayout.CENTER);
		}else {
			tempFrame.dispose();
			totalEating = 0;
		}
		
		if(this.flagInfo==1) {
			tempFrame.setTitle("JPanelDecorator");
			panColor();
		}

		if(flgSaveState==true && flagInfo==2)
		{
			tempFrame.setTitle("SaveObjectState");
			numOfClicks=0;//to even clicks
			tempFrame.add(scrollPane,BorderLayout.WEST);
			tempFrame.setSize(1000,350);

			immoblieTable();
			panSave();
		}
		
	}
	
	public void mementoTable() {
		
		DefaultTableModel model = new DefaultTableModel(mementoLabels,0);
		
		mementoStrings = new String[this.mementoLabels.length];
		
		//set table
		for(Memento m: state.getSwimmableMementohash()) {
			mementoStrings[0] = Integer.toString(m.getID());
			mementoStrings[1] = m.getName();
			mementoStrings[2] = m.getColor();
			mementoStrings[3] = Integer.toString(m.getSize());
			mementoStrings[4] = Integer.toString(m.getVerSpeed());
			mementoStrings[5] = Integer.toString(m.getHoSpeed());
			model.addRow(mementoStrings);
		}
		this.mementoTable = new JTable(model);
		mementoTable.setVisible(true);
		this.scrollPane = new JScrollPane(mementoTable);
		if(this.numOfClicks % 2 == 1) {
			tempFrame = new JFrame("Restore");
			tempFrame.setSize(500,350);

			tempFrame.setVisible(true);
			tempFrame.add(scrollPane,BorderLayout.CENTER);
		}else {
			tempFrame.dispose();
		}
		JPanel pan = new JPanel();
		JTextField idText = new JTextField("Enter ID");
		JButton sub = new JButton("Restore Object");
		pan.setLayout(new GridLayout(0,2,0,0)); 
		
		idText.addMouseListener((MouseListener) new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){
	        	idText.setText("");
	        }
	    });
		
		pan.add(idText);
		pan.add(sub);
		tempFrame.add(pan, BorderLayout.SOUTH);
		
		sub.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg)
			{
				for(Memento m : state.getSwimmableMementohash()) {
					if(m.getID()==Integer.parseInt(idText.getText()))
					{
						for(Swimmable s : animalHashSet) {
							if(m.getID()==s.getID()) {
								s.setState(m.getClr(), m.getSize(), m.getX_front(), m.getY_front(), m.getHoSpeed(), m.getVerSpeed(), m.getX_dir(), m.getY_dir());
								break;
							}
							
						}
					}
				}
			}
		});
	}
		
	public void panColor()
	{
		JPanel pan = new JPanel();
		JTextField idText = new JTextField("Enter ID");
		JButton sub = new JButton("Change Color");
		pan.setLayout(new GridLayout(0,2,0,0)); 
		
		idText.addMouseListener((MouseListener) new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){
	        	idText.setText("");
	        }
	    });
		
		pan.add(idText);
		pan.add(sub);
		tempFrame.add(pan, BorderLayout.SOUTH);
		
		sub.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg)
			{
				for(Swimmable s : animalHashSet) {
					if(s.getID()==Integer.parseInt(idText.getText()))
					{
						flagColor=true;
						changeColor(s);
						tempFrame.dispose();
						break;


					}
				}
					if(!flagColor)
					{
						JOptionPane.showMessageDialog(null,"This ID isn't in Aquarium");
						tempFrame.dispose();


					}
				}
			
		});
	
	flagColor=false;
	
	}
	
	public void setStateSave()
	{
		this.flgSaveState=true;
		this.flagInfo=2;
		//set title frame
		frm=new JFrame("Choose Type");
		frm.setLocationRelativeTo(null);
		
		//set radio button fish
		Animal = new JRadioButton("Animal");
		Animal.setActionCommand("Animal");
		
		//set radio button jellyfish
		SeaPlanet = new JRadioButton("SeaPlanet");
		SeaPlanet.setActionCommand("SeaPlanet");

		//add submit button
		JButton submit = new JButton("Submit");
		submit.setBounds(0, 200, 100, 30);
		Animal.setBounds(0,0,100,30);    
		SeaPlanet.setBounds(0,20,100,30);  

		 
		ButtonGroup bg=new ButtonGroup();    
		bg.add(Animal);bg.add(SeaPlanet);    
		frm.add(Animal);frm.add(SeaPlanet);    
		frm.add(submit);
		
		
		submit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg)
			{
				if(Animal.isSelected()) 
				{
					typeName="Animal";
					
				}
				else if(SeaPlanet.isSelected()) 
				{
					typeName="SeaPlanet";
					
				}
				frm.dispose();
				infoTable();

			}
			
		});
		frm.setSize(300,300);    
		frm.setLayout(null);    
		frm.setVisible(true);

	}
	
	public void panSave()
	{
		JPanel panS = new JPanel();
		JTextField idText = new JTextField("Enter ID");
		JButton save = new JButton("Save");
		panS.setLayout(new GridLayout(0,2,0,0)); 
		
		idText.addMouseListener((MouseListener) new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e){
	        	idText.setText("");
	        }
	    });
		
		panS.add(idText);
		panS.add(save);
		tempFrame.add(panS, BorderLayout.SOUTH);
		
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg)
			{
				if(typeName=="Animal")
				{
				for(Swimmable s : animalHashSet) {
					if(s.getID()==Integer.parseInt(idText.getText()))
					{
						flagSave=true;
						saveStateS(s,s.getID());
						tempFrame.dispose();
						break;


					}
				}}
				else if(typeName=="SeaPlanet")
				{
				for(Immobile p : plantHashSet) {
					if(p.getID()==Integer.parseInt(idText.getText()))
					{
						flagSave=true;
						saveStateP(p,p.getID());
						tempFrame.dispose();
						break;


					}
				}
				}
					if(!flagSave)
					{
						JOptionPane.showMessageDialog(null,"This ID isn't in Aquarium");
						tempFrame.dispose();

					}
				}
		});
		
		flagSave=false;
		flagInfo=0;
		
	}
	
	public void saveStateS(Swimmable s,int id)
	{
		Memento object_ = null;
		object_ = new Memento(s);
		
		state.addSwimmableMemento(id, object_);
		
		JOptionPane.showMessageDialog(null, s.getID()+" "+s.getAnimalName() +" state saved!");

		
	}
	public void saveStateP(Immobile p,int id)
	{
		Memento object_ = null;
		object_ = new Memento(p);
		
		state.addSwimmableMemento(id, object_);
		
		JOptionPane.showMessageDialog(null, p.getID()+" "+p.getPlantName()+ " state saved!");

		
	}
	public void changeColor(Swimmable s)
	{
		MarineAnimal Animal=new MarineAnimalDecorator((MarineAnimal) s);
		Color clr=JColorChooser.showDialog(null, "Pick a Color", Color.black);
		s.setColor(clr);
		Animal.paintFish(clr);

		 JOptionPane.showMessageDialog(null,"the color of "+s.getAnimalName()+" #" +s.getID()+ " changed");
		 repaint();

	}
	
	
	
	public void immoblieTable()
	{
		
		DefaultTableModel model = new DefaultTableModel(immobileLabels,0);
		
		immobileStrings = new String[this.immobileLabels.length];
		
		//set table
		for(Immobile p : plantHashSet) {
			immobileStrings[0] = Integer.toString(p.getID());
			immobileStrings[1] = p.getPlantName();
			immobileStrings[2] = p.getColor();
			immobileStrings[3] = Integer.toString(p.getSize());
			immobileStrings[4] = Integer.toString(p.getX());
			immobileStrings[5] = Integer.toString(p.getY());
			model.addRow(immobileStrings);
		}
		this.immobileTable = new JTable(model);
		immobileTable.setVisible(true);
		this.scrollPane = new JScrollPane(immobileTable);
		
			tempFrame.setVisible(true);
			tempFrame.add(scrollPane,BorderLayout.EAST);
			//tempFrame.dispose();
		
	
	}
	
	//function that check if the hashset is empty
	public Boolean isHashEmpty() {
		if(this.animalHashSet == null) {
			return true;
		}
		return false;
	}
	
	//function that add to the hashset
	public void addToHash(Swimmable obj)
	{
		if(obj instanceof Swimmable)
		{
			animalHashSet.add(obj);
		}
//		animalHashSet.add(obj);
		this.repaint();
		
		new Thread(obj).start(); //start the thread
	}
	
	
	
	public void addPlant(Immobile swimmable)
	{
		plantHashSet.add(swimmable);
		repaint();
	}
	
	public HashSet<Swimmable> getAnimalHashSet() {
		return animalHashSet;
	}
	
	public HashSet<Immobile> getPlanetSet(){
		return plantHashSet;
		}
	
	public void setAnimalHashSet(HashSet<Swimmable> animalHashSet) {
		this.animalHashSet = animalHashSet;
	}
	
	public void setWormInstance() {
		Singleton.setNull();
		worm = null;
	}
	
	public synchronized void eatFood(Swimmable swimm)
	{
		swimm.eatInc();
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		JDialog dialog=new JDialog(this.tempFrame,"Massage");
		JPanel panel=new JPanel();
		JLabel label=new JLabel("The Animal is Hungrey");
		panel.add(label);
		dialog.add(panel);
		dialog.pack();
		dialog.setVisible(true);
	}
	
}
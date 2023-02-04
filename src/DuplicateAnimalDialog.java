import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DuplicateAnimalDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame, edit_frame,id_frame;
	private JRadioButton fish, jellyfish;
	private JButton submit, edit_submit,idSubmit;
	private JTextField sizeText, Hspeed, Vspeed, foodFreq,idText;
	private ButtonGroup bg;
	private String animalName=null;
	
	
	private String[] colorStrings = {"Red","Blue","Green","Orange","Cyan","Pink","Black"};
	private JComboBox<String> colorList;
	private AquaPanel refAquaPanel;
	private HashSet<Swimmable> animalHashSet;
	private Iterator <Swimmable>iterAnimals;
	private Swimmable swim;
	private int input;
	
	private String[] infoStrings;
	private int totalEating = 0, numOfClicks = 0;
	private String[] infoLabels = { "ID", "Animal", "Color", "Size", "Hor.Speed", "Ver.Speed", "Eat Counter"};
	private JTable infoTable;
	private JFrame tempFrame;
	private JScrollPane scrollPane;
	
	
	int sizeInfo, verInfo, horInfo;
	String colorInfo, animalNameInfo;
	
	DuplicateAnimalDialog(AquaPanel aquapanel,HashSet <Swimmable> animalHashSet){
		super();
		
		this.animalHashSet=new HashSet<Swimmable>(animalHashSet);
		this.refAquaPanel=aquapanel;
		
		
		//set title frame
		this.frame=new JFrame("Duplicate Animal");
		frame.setLocationRelativeTo(null);
		
		//set radio button fish
		fish = new JRadioButton("Fish");
		fish.setActionCommand("Fish");
		
		//set radio button jellyfish
		jellyfish = new JRadioButton("JellyFish");
		jellyfish.setActionCommand("JellyFish");

		//add submit button
		submit = new JButton("Submit");
		
		
		submit.setBounds(0, 200, 100, 30);
		fish.setBounds(0,0,100,30);    
		jellyfish.setBounds(0,20,100,30);  
		bg=new ButtonGroup();    
		bg.add(fish);bg.add(jellyfish);    
		frame.add(fish);frame.add(jellyfish);    
		frame.add(submit);
		frame.setSize(300,300);    
		frame.setLayout(null);    
		frame.setVisible(true); 
		
		submit.addActionListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submit) { 
			if(fish.isSelected()) 
			{
				this.animalName="Fish";
				
			}
			
			else if(jellyfish.isSelected()) 
			{
				this.animalName="Jellyfish";
				
			}
			
			input = JOptionPane.showConfirmDialog(null, "Do you want to edit your animal?", "Select an Option",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			editDuplicateDialog();
			frame.dispose();
		  }
		
	}
	
	public void duplicateAnimal(String animalType)
	{
		iterAnimals= animalHashSet.iterator(); 
		while(iterAnimals.hasNext()){
			Swimmable objSwim=iterAnimals.next();
			try {
				if(animalType=="Fish"){
					if(objSwim.getAnimalName()=="Fish") {
						if(objSwim.getID()==Integer.parseInt(idText.getText())) {
							this.swim=objSwim.clone();
							break;
						}
						else
						{
					        JOptionPane.showMessageDialog(null,"This ID isn't in Aquarium");

						}
					}
					
				}
				else if(animalType=="Jellyfish") {
					if(objSwim.getAnimalName()=="Jellyfish") {
						if(objSwim.getID()==Integer.parseInt(idText.getText())) {
							this.swim=objSwim.clone();
							break;
						}
						else
						{
					        JOptionPane.showMessageDialog(null,"This ID isn't in Aquarium");

						}
					}
				}
				else{
					throw new Exception("this animal is not in the aquarium!");
				}
			} 
			catch(Exception e1){
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
			
		}		
		
	}
	
	public void editDuplicateDialog() {
		if(input==JOptionPane.YES_OPTION) {
			edit_frame = new JFrame("Edit Animal");
			//add submit button
			edit_submit = new JButton("Submit");
			
			//set text field for animal details
			sizeText = new JTextField("Enter New Size");
			Hspeed = new JTextField("Enter New Horizontal Speed");
			Vspeed = new JTextField("Enter New Vertical Speed");
			foodFreq = new JTextField("Enter Food Frequency");
			
			sizeText.addMouseListener((MouseListener) new MouseAdapter()
					{
	            @Override
	            public void mouseClicked(MouseEvent e){
	            	sizeText.setText("");
	            }
	        });
			
			Hspeed.addMouseListener((MouseListener) new MouseAdapter()
					{
	            @Override
	            public void mouseClicked(MouseEvent e){
	            	Hspeed.setText("");
	            }
	        });
			
			Vspeed.addMouseListener((MouseListener) new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e){
					Vspeed.setText("");
				}
			});
			
			foodFreq.addMouseListener((MouseListener) new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e){
					foodFreq.setText("");
				}
			});
			
			//set combox frame
			colorList = new JComboBox<String>(colorStrings);
			
			colorList.setSelectedIndex(6);
			colorList.addActionListener(colorList);
			colorList.setBounds(0, 140, 100, 30);
			
			edit_submit.setBounds(0, 200, 100, 30);
			
			sizeText.setBounds(0, 60, 200, 30);
			Hspeed.setBounds(0, 80, 200, 30);
			Vspeed.setBounds(0, 100, 200, 30);	
			foodFreq.setBounds(0, 120, 200, 30);
			
			edit_frame.add(edit_submit);
			edit_frame.add(sizeText);
			edit_frame.add(Hspeed);
			edit_frame.add(Vspeed);
			edit_frame.add(colorList);
			edit_frame.add(foodFreq);
			   
			edit_frame.setSize(300,300);    
			edit_frame.setLayout(null);    
			edit_frame.setVisible(true);  
			edit_frame.setLocationRelativeTo(null);
			
			
			 
			
			
			edit_submit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg)
				{
					edit_frame.dispose();
					editSubmit();
					
				}
			});

	
		}
		else if(input==JOptionPane.NO_OPTION){
			DuplicateNo();
			JPanel pan = new JPanel();
			idText = new JTextField("Enter ID");
			idSubmit = new JButton("Submit");
			pan.setLayout(new GridLayout(0,2,0,0)); 

			
			
			pan.add(idText);
			pan.add(idSubmit);
			tempFrame.add(pan, BorderLayout.SOUTH);
			
			idText.addMouseListener((MouseListener) new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e){
		        	idText.setText("");
		        }
		    });
			
			idSubmit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg)
				{
					tempFrame.dispose();
										
					duplicateAnimal(animalName);
					
					refAquaPanel.addToHash(swim);
			        JOptionPane.showMessageDialog(null,"The Animal Is Duplicate");
					
					
				}
			});
			
			
			
	        this.dispose();
	       
		}
		else {
			this.dispose();
		}
	}
	
	public void DuplicateNo()
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
	}
		
	public void editSubmit() {
		Color clr=null;
		int size=0,hor=0,ver=0,food=0;;
		
		try {
			size = Integer.parseInt(sizeText.getText());
			hor = Integer.parseInt(Hspeed.getText());
			ver = Integer.parseInt(Vspeed.getText());
			food = Integer.parseInt(foodFreq.getText());
			
			if(size<20 || size>320) { //check animal size (must to be 20-320)
				throw new Exception("size have to be 20-320");
			}
			
			if(hor<1 || hor>10) { //check horizontal speed (must to be 1-10)
				throw new Exception("Horizental have to be 1-10");
			}
			
			if(ver<1 || ver>10) { //check vertical speed (must to be 1-10)
				throw new Exception("Vertical have to be 1-10");
			}
			
			String colorType = colorList.getItemAt(colorList.getSelectedIndex());
			switch(colorType)
			{
				case "Red":
					clr=Color.RED;
					break;
				case "Blue":
					clr=Color.BLUE;
					break;
				case "Green":
					clr=Color.GREEN;
					break;
				case "Orange":
					clr=Color.ORANGE;
					break;
				case "Cyan":
					clr=Color.CYAN;
					break;
				case "Pink":
					clr=Color.PINK;
					break;
				case "Black":
					clr=Color.BLACK;
					break;
			}
			iterAnimals= animalHashSet.iterator(); 
			while(iterAnimals.hasNext()){
				Swimmable objSwim=iterAnimals.next();
				try {
					if(animalName=="Fish"){
						if(objSwim.getAnimalName()=="Fish") {
							this.swim=new Fish(refAquaPanel,size, hor, ver, clr,food);
							refAquaPanel.addToHash(this.swim);
							break;
						}
						
					}
					else if(animalName=="Jellyfish") {
						if(objSwim.getAnimalName()=="Jellyfish") {
							this.swim=new Jellyfish(refAquaPanel,size, hor, ver, clr,food);
							refAquaPanel.addToHash(this.swim); 
							break;
						}
					}
					else{
						throw new Exception("this animal is not in the aquarium!");
					}
				} 
				catch(Exception e1){
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}
				
			}		
			
			edit_frame.dispose();
		}
		catch(Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}
	
}

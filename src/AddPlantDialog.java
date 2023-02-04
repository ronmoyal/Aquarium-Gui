import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddPlantDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	final int PANEL_WIDTH=700;
	final int PANEL_HIGH=700;
	private JFrame frame;
	private JRadioButton laminaria, zostera;
	private JButton submit;
	private JTextField sizeText;
	private ButtonGroup bg;
	private AquaPanel panel;
	private AbstractSeaFactory seaFactory;
	private SeaCreature SeaCreature;
	HashSet<Immobile> plantHashSet;
	
	public AddPlantDialog(AquaPanel panel, HashSet<Immobile> plantHashSet2) {
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HIGH));
		this.setLayout(new BorderLayout());
		
		this.frame = new JFrame("Add Planet Dialog");
		this.panel=panel;
		
		laminaria = new JRadioButton("Laminaria");
		laminaria.setActionCommand("Laminaria");
		
		zostera = new JRadioButton("Zostera");
		zostera.setActionCommand("Zostera");
		
		submit = new JButton("Submit");
		
		sizeText = new JTextField("Enter Size");
		
		submit.setBounds(0, 200, 100, 30);
		sizeText.setBounds(0, 60, 170, 30);
		
		frame.add(submit);
		frame.add(sizeText);
		frame.setLayout(null); 
		
		sizeText.addMouseListener((MouseListener) new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e){
				sizeText.setText("");
			}
		});
		
		laminaria.setBounds(0,0,100,30);    
		zostera.setBounds(0,20,100,30);  
		bg=new ButtonGroup();    
		bg.add(laminaria);bg.add(zostera);    
		frame.add(laminaria);frame.add(zostera);      
		frame.setSize(300,300);    
		frame.setLayout(null);    
		frame.setVisible(true);  
		frame.setLocationRelativeTo(null);
		
		
		submit.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		  if(e.getSource()==submit) { 
			  submitPlant(); 
		  }
	}
	
	public void submitPlant(){
		Color clr = Color.green;
		int size=0;
		int x,y;
	    Random rand = new Random();
	    x=rand.nextInt((500 - 50) + 1) + 150;
	    y=rand.nextInt((500 - 50) + 1) + 50;
		
		try {
			size = Integer.parseInt(sizeText.getText());
			
			
			if(size<20 || size>320) { //check animal size (must to be 20-320)
				throw new Exception("size have to be 20-320");
			}
			
			if(laminaria.isSelected()) 
			{
				seaFactory=new PlantFactory(panel,size,x,y);
				SeaCreature=seaFactory.produceSeaCreature("Laminaria");
				panel.addPlant((Immobile)SeaCreature);
			}
			
			if(zostera.isSelected()) 
			{
				seaFactory=new PlantFactory(panel,size,x,y);
				SeaCreature=seaFactory.produceSeaCreature("Zostera");
				panel.addPlant((Immobile)SeaCreature);
			}
			
			frame.dispose();
		}
		catch(Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		
	}
	

}

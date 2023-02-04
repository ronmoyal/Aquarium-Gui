import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.w3c.dom.Text;

public class AddAnimalDialog extends JDialog implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JRadioButton fish, jellyfish;
	private  JButton submit;
	private JTextField sizeText, Hspeed, Vspeed, foodFreq;
	private ButtonGroup bg;
	
	private String[] colorStrings = {"Red","Blue","Green","Orange","Cyan","Pink","Black"};
	private JComboBox<String> colorList;
	private AquaPanel refAquaPanel;
	private HashSet<Swimmable> animalHashSet;
	private Swimmable swim;
	private int ID=0;
	
	AddAnimalDialog(AquaPanel aquapanel,HashSet <Swimmable> animalHashSet){
		
		this.animalHashSet=animalHashSet;
		this.refAquaPanel=aquapanel;
		
		//set title frame
		this.frame=new JFrame("Add Animal Dialog");
		
		//set radio button fish
		fish = new JRadioButton("Fish");
		fish.setActionCommand("Fish");
		
		//set radio button jellyfish
		jellyfish = new JRadioButton("JellyFish");
		jellyfish.setActionCommand("JellyFish");

		//add submit button
		submit = new JButton("Submit");
		
		//set text field for animal details
		sizeText = new JTextField("Enter Size (20-320)");
		Hspeed = new JTextField("Enter Horizontal Speed (1-10)");
		Vspeed = new JTextField("Enter Vertical Speed (1-10)");
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
		colorList.setBounds(0, 150, 100, 30);
		
		submit.setBounds(0, 200, 100, 30);
		
		sizeText.setBounds(0, 60, 200, 30);
		Hspeed.setBounds(0, 80, 200, 30);
		Vspeed.setBounds(0, 100, 200, 30);	
		foodFreq.setBounds(0, 120, 200, 30);
		
		frame.add(submit);
		frame.add(sizeText);
		frame.add(Hspeed);
		frame.add(Vspeed);
		frame.add(colorList);
		frame.add(foodFreq);
		
		fish.setBounds(0,0,100,30);    
		jellyfish.setBounds(0,20,100,30);  
		bg=new ButtonGroup();    
		bg.add(fish);bg.add(jellyfish);    
		frame.add(fish);frame.add(jellyfish);      
		frame.setSize(300,300);    
		frame.setLayout(null);    
		frame.setVisible(true);  
		
		frame.setLocationRelativeTo(null);
		
		submit.addActionListener(this);
	}


	  @Override
	  public void actionPerformed(ActionEvent e) {
		  if(e.getSource()==submit) { 
			  submitAnimal(); 
		  }
	}
	 
	
	// method that update all the details of the animal
	public void submitAnimal() {
		Color clr=null;
		int size=0,hor=0,ver=0, food=0;
		
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
			
			if(fish.isSelected()) 
			{
				this.swim=new Fish(refAquaPanel,size, hor, ver, clr, food);
				refAquaPanel.addToHash(this.swim);
			}
			
			if(jellyfish.isSelected()) 
			{
				this.swim=new Jellyfish(refAquaPanel,size, hor, ver, clr, food);
				refAquaPanel.addToHash(this.swim); 
			}
			
			frame.dispose();
		}
		catch(Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}
}
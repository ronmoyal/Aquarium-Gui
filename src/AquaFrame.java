import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class AquaFrame extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AquaPanel panel;
	private JMenuItem exit, image, blue, none, helpItem,save,restore;
	
	
	AquaFrame(){ 
		
		//set title of frame
		this.setTitle("My Aquarium");
		
		//set panel to frame
		this.panel= new AquaPanel();
		this.add(panel);
		
		//frame setting
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set menu bar to frame
		JMenuBar menuBar= new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu background = new JMenu("Background");
		JMenu help = new JMenu("Help");
		JMenu memento=new JMenu("Memento");
		
		exit = new JMenuItem("Exit");
		image = new JMenuItem("Image");
		blue = new JMenuItem("Blue");
		none = new JMenuItem("None");
		helpItem = new JMenuItem("Help");
		save=new JMenuItem("Save Object State");
		restore=new JMenuItem("Restore Object State");
		
		
		file.add(exit);
		background.add(image);
		background.add(blue);
		background.add(none);
		help.add(helpItem);
		memento.add(save);
		memento.add(restore);
		
		menuBar.add(file);
		menuBar.add(background);
		menuBar.add(memento);
		menuBar.add(help);
		this.setJMenuBar(menuBar);
		
		//action listener for the menu bar
		exit.addActionListener(this);
		blue.addActionListener(this);
		none.addActionListener(this);
		image.addActionListener(this);
		helpItem.addActionListener(this);
		save.addActionListener(this);
		restore.addActionListener(this);
		

		this.pack();
		this.setVisible(true);
		}
	
	
	public static void main(String[] args) {
		new AquaFrame(); 
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource()== exit) {
			System.exit(0);
		}
		if ( e.getSource()== blue) {
			panel.background=null;
			panel.setBackground(Color.blue);
			
		}
		if ( e.getSource()== none) {
			panel.background=null;
			panel.setBackground(null);
			
		}
		if ( e.getSource()== image) {
			panel.setBackground(null);
			panel.background = new ImageIcon("C:\\Users\\IMOE001\\Desktop\\Java GUI Projectt\\aqua.jpg").getImage();
			panel.repaint();
		}
		if(e.getSource()==helpItem)
		  {
			  JOptionPane.showMessageDialog(null, "Home Work 3"+" \n "+"GUI @ Threads","Massage",JOptionPane.INFORMATION_MESSAGE);
		  }
		if(e.getSource()==save)
		{
			panel.setStateSave();

		}
		if(e.getSource()==restore)
		{
			panel.mementoTable();
		}

	}
	
}

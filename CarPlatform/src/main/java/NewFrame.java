import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class NewFrame extends JFrame{
	
	JPanel personP=new JPanel();
	JPanel carP=new JPanel();
	JPanel rentaP=new JPanel();
	JPanel sprP=new JPanel();
	
	JTabbedPane tab=new JTabbedPane();
	
	public NewFrame() {
		this.setSize(400, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tab.add(personP,"�������");
		tab.add(carP,"����");
		tab.add(rentaP,"����");
		tab.add(sprP,"������� �� ...");
		
		
		this.add(tab);
		
		
		this.setVisible(true);
	}

}

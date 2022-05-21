import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyFrame extends JFrame{
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	int id = -1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	// Labels
	JLabel nameLabel = new JLabel("Name:");
	JLabel fullNameLabel = new JLabel("Full Name:");
	JLabel founderLabel = new JLabel("Founder:");
	JLabel foundedInLabel = new JLabel("FoundedIn:");
	JLabel headquartersLabel = new JLabel("Headquarters:");
	JLabel descriptionLabel = new JLabel("Description:");
	
	// Fields
	JTextField nameInput = new JTextField();
	JTextField fullNameInput = new JTextField();
	JTextField founderInput = new JTextField();
	JTextField headquartersInput = new JTextField();
	JTextField descriptionInput = new JTextField();
	
	// String[] item= {"male", "female"};
	// JComboBox<String> sexCombo=new JComboBox<String>(item);
	// JComboBox<String> personCombo=new JComboBox<String>();
	
	JButton addButton = new JButton("Add");
	JButton deleteButton = new JButton("Delete");
	JButton editButton = new JButton("Edit");
	JButton searchButton = new JButton("Search By Name");
	JButton refreshButton = new JButton("Refresh");
	
	JTable table = new JTable();
	JScrollPane myScroll = new JScrollPane(table);
	
	
	public MyFrame() {
		this.setSize(400, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		
		//upPanel-----------------------------------------
		upPanel.setLayout(new GridLayout(5, 2));
		
		upPanel.add(nameLabel);
		upPanel.add(nameInput);
		upPanel.add(fullNameLabel);
		upPanel.add(fullNameInput);
		upPanel.add(foundedInLabel);
		upPanel.add(founderInput);
		upPanel.add(headquartersLabel);
		upPanel.add(headquartersInput);
		upPanel.add(descriptionLabel);
		upPanel.add(descriptionInput);
		
		this.add(upPanel);
		
		//midPanel------------------------------------
		
		midPanel.add(addButton);
		midPanel.add(deleteButton);
		midPanel.add(editButton);
		midPanel.add(searchButton);
		midPanel.add(refreshButton);
		//midPanel.add(personCombo);
		
		this.add(midPanel);
		
		//downPanel-----------------------------------
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		
		this.add(downPanel);
		
		
		
		addButton.addActionListener(new AddAction());
		// deleteButton.addActionListener(new DeleteAction());
		// searchButton.addActionListener(new SearchAction());
		// refreshButton.addActionListener(new RefreshAction());
		
		// table.addMouseListener(new MouseAction());
				
		
		// refreshTable();
		// refreshComboPerson();
		
		this.setVisible(true);
		
	}
	
	public void refreshTable() {
		conn=DBConnection.getConnection();
		
		try {
			state=conn.prepareStatement("select * from carmake");
			result=state.executeQuery();
			table.setModel(new MyModel(result));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// public void refreshComboPerson() {
		
	// 	String sql="select id, fname, lname from person";
	// 	conn=DBConnection.getConnection();
	// 	String item="";
		
	// 	try {
	// 		state=conn.prepareStatement(sql);
	// 		result=state.executeQuery();
	// 		personCombo.removeAllItems();
	// 		while(result.next()) {
	// 			item=result.getObject(1).toString()+"."+
	// 					result.getObject(2).toString()+" "+
	// 					result.getObject(3).toString();
	// 			personCombo.addItem(item);
	// 		}
	// 	} catch (SQLException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	}
		
	// }
	
	public void clearForm() {
		nameInput.setText("");
		fullNameInput.setText("");
		founderInput.setText("");
		headquartersInput.setText("");
		descriptionInput.setText("");
	}
	
	class AddAction implements ActionListener{

	 	@Override
	 	public void actionPerformed(ActionEvent e) {
			/**
			  
			    nameInput = new JTextField();
	JTextField fullNameInput = new JTextField();
	JTextField founderInput = new JTextField();
	JTextField headquartersInput = new JTextField();
	JTextField descriptionInput = new JTextField();
			 */
	 		conn=DBConnection.getConnection();
	 		String sql="insert into carmake(name, fullname, founder, headquarters, description) values(?,?,?,?,?)";
			
	 		try {
	 			state=conn.prepareStatement(sql);
	 			state.setString(1, nameInput.getText());
	 			state.setString(2, fullNameInput.getText());
	 			state.setString(3, founderInput.getText());
	 			state.setString(4, headquartersInput.getText());
	 			state.setString(5, descriptionInput.getText());
				
	 			state.execute();
	 			refreshTable();
	 			clearForm();
				
	 		} catch (SQLException e1) {
	// 			// TODO Auto-generated catch block
	 			e1.printStackTrace();
	 		}	
	 	}	
	 }
	
	// class MouseAction implements MouseListener{

	// 	@Override
	// 	public void mouseClicked(MouseEvent e) {
	// 		int row=table.getSelectedRow();
	// 		id=Integer.parseInt(table.getValueAt(row, 0).toString());
	// 		fnameTF.setText(table.getValueAt(row, 1).toString());
	// 		lnameTF.setText(table.getValueAt(row, 2).toString());
	// 		ageTF.setText(table.getValueAt(row, 4).toString());
	// 		salaryTF.setText(table.getValueAt(row, 5).toString());
	// 		if(table.getValueAt(row, 3).toString().equals("male")) {
	// 			sexCombo.setSelectedIndex(0);
	// 		}
	// 		else {
	// 			sexCombo.setSelectedIndex(1);
	// 		}
			
	// 	}

	// 	@Override
	// 	public void mousePressed(MouseEvent e) {
	// 		// TODO Auto-generated method stub
			
	// 	}

	// 	@Override
	// 	public void mouseReleased(MouseEvent e) {
	// 		// TODO Auto-generated method stub
			
	// 	}

	// 	@Override
	// 	public void mouseEntered(MouseEvent e) {
	// 		// TODO Auto-generated method stub
			
	// 	}

	// 	@Override
	// 	public void mouseExited(MouseEvent e) {
	// 		// TODO Auto-generated method stub
			
	// 	}
		
	// }
	
	// class DeleteAction implements ActionListener{

	// 	@Override
	// 	public void actionPerformed(ActionEvent e) {
			
	// 		conn=DBConnection.getConnection();
	// 		String sql="delete from person where id=?";
			
	// 		try {
	// 			state=conn.prepareStatement(sql);
	// 			state.setInt(1, id);
	// 			state.execute();
	// 			refreshTable();
	// 			refreshComboPerson();
	// 			clearForm();
	// 			id=-1;
	// 		} catch (SQLException e1) {
	// 			// TODO Auto-generated catch block
	// 			e1.printStackTrace();
	// 		}
			
	// 	}
		
	// }
	
	// class SearchAction implements ActionListener{

	// 	@Override
	// 	public void actionPerformed(ActionEvent e) {
	// 		conn=DBConnection.getConnection();
	// 		String sql="select * from person where age=?";
			
	// 		try {
	// 			state=conn.prepareStatement(sql);
	// 			state.setInt(1, Integer.parseInt(ageTF.getText()));
	// 			result=state.executeQuery();
	// 			table.setModel(new MyModel(result));
	// 		} catch (SQLException e1) {
	// 			// TODO Auto-generated catch block
	// 			e1.printStackTrace();
	// 		} catch (Exception e1) {
	// 			// TODO Auto-generated catch block
	// 			e1.printStackTrace();
	// 		}
			
	// 	}
		
	// }
	
	// class RefreshAction implements ActionListener{

	// 	@Override
	// 	public void actionPerformed(ActionEvent e) {
	// 		refreshTable();
			
	// 	}
		
	// }
}

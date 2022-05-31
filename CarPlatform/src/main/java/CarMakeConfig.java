package main.java;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class CarMakeConfig extends BaseConfig {
    JTabbedPane mainTab;

    // Panels
    JPanel mainPanel = new JPanel();
    JPanel formPanel = new JPanel();
    JPanel filterPanel = new JPanel();
    JPanel tablePanel = new JPanel();

    // Form Labels
    JLabel nameLabel = new JLabel("Name:");
    JLabel fullNameLabel = new JLabel("Full Name:");
    JLabel founderLabel = new JLabel("Founder:");
    JLabel foundedInLabel = new JLabel("FoundedIn:");
    JLabel headquartersLabel = new JLabel("Headquarters:");
    JLabel descriptionLabel = new JLabel("Description:");

    // Form Fields
    JTextField nameInput = new JTextField();
    JTextField fullNameInput = new JTextField();
    JTextField founderInput = new JTextField();
    JDateChooser foundedInInput = new JDateChooser();;
    JTextField headquartersInput = new JTextField();
    JTextField descriptionInput = new JTextField();

    JPasswordField passwordInput = new JPasswordField();
    JTextField searchByNameInput = new JTextField();
    JTextField searchByHeadquartersInput = new JTextField();

    // Table
    JTable table= new JTable();
    JScrollPane scroll = new JScrollPane(table);

    // Buttons
    JButton addButton = new JButton("Add Car Make");
    JButton deleteButton = new JButton("Delete Car Make");
    JButton editButton = new JButton("Edit Car Make");
    JButton searchByNameButton = new JButton("Search Car Make By Name");
    JButton searchByHeadquarterButton = new JButton("Search Car Make By Headquarter");
    JButton refreshButton = new JButton("Refresh Car Make");
    JButton addRandomButton = new JButton("Add Random Car Make");
    JButton orderByCreatedOnButton = new JButton("Order By Created On");
    JButton orderByLastModifiedOnButton = new JButton("Order By Last Modified On");
    JButton clearFormButton = new JButton("Clear Car Make Form");
    JButton authorizeButton = new JButton("Authorize");
    JButton logoutButton = new JButton("Logout...");

    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result = null;
    int selectedId = -1;
    boolean isAuthorized = false;

    public CarMakeConfig(JTabbedPane mainTab) {
        this.mainTab = mainTab;
        this.refreshTable();
    }

    public void configureScreen() {
        mainTab.add("Car Make", mainPanel);

        mainPanel.setLayout(new GridLayout(3,1));
        mainPanel.add(formPanel);
        this.configureFormPanel();
        mainPanel.add(filterPanel);
        this.configureFilterPanel();
        mainPanel.add(tablePanel);
        this.configureTablePanel();

        this.configureButtons();

        this.addStyles();
    }

    private void addStyles() {
        formPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    }

    private void configureFormPanel(){
        formPanel.setLayout(new GridLayout(8, 2));
        formPanel.add(nameLabel);
        formPanel.add(nameInput);
        formPanel.add(fullNameLabel);
        formPanel.add(fullNameInput);
        formPanel.add(founderLabel);
        formPanel.add(founderInput);
        formPanel.add(foundedInLabel);
        formPanel.add(foundedInInput);
        formPanel.add(headquartersLabel);
        formPanel.add(headquartersInput);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionInput);

        formPanel.add(addButton);
        formPanel.add(editButton);

        formPanel.add(deleteButton);
        formPanel.add(addRandomButton);
    }

    private void configureFilterPanel() {
        filterPanel.setLayout(new GridLayout(6, 2));

        filterPanel.add(refreshButton);
        filterPanel.add(clearFormButton);

        filterPanel.add(searchByNameButton);
        filterPanel.add(searchByNameInput);

        filterPanel.add(searchByHeadquarterButton);
        filterPanel.add(searchByHeadquartersInput);

        filterPanel.add(orderByCreatedOnButton);
        filterPanel.add(orderByLastModifiedOnButton);

        filterPanel.add(authorizeButton);
        filterPanel.add(passwordInput);

        filterPanel.add(logoutButton);
    }

    private void configureTablePanel(){
        scroll.setPreferredSize(new Dimension(1400, 1400));
        tablePanel.add(scroll);
        table.addMouseListener(new MouseAction());
    }

    private void configureButtons(){
        addButton.addActionListener(new AddAction());
        editButton.addActionListener(new UpdateAction());
        deleteButton.addActionListener(new DeleteAction());

        refreshButton.addActionListener(new RefreshAction());
        clearFormButton.addActionListener(new ClearAction());

        searchByNameButton.addActionListener(new SearchByNameAction());
        searchByHeadquarterButton.addActionListener(new SearchByHeadquartersAction());

        orderByCreatedOnButton.addActionListener(new OrderByCreatedAction());
        orderByLastModifiedOnButton.addActionListener(new OrderByModifiedAction());

        addRandomButton.addActionListener(new AddRandomAction());
        authorizeButton.addActionListener(new AuthorizeAction());
        logoutButton.addActionListener(new LogoutAction());
    }

    private void disableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.getColumnModel().getColumn(7).setMinWidth(0);
        table.getColumnModel().getColumn(7).setMaxWidth(0);
        table.getColumnModel().getColumn(7).setWidth(0);

        table.getColumnModel().getColumn(8).setMinWidth(0);
        table.getColumnModel().getColumn(8).setMaxWidth(0);
        table.getColumnModel().getColumn(8).setWidth(0);

        isAuthorized = false;
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addRandomButton.setEnabled(false);
        logoutButton.setEnabled(false);
        authorizeButton.setEnabled(true);
        passwordInput.setEnabled(true);


        table.getColumnModel().getColumn(1).setMinWidth(220);
        table.getColumnModel().getColumn(1).setMaxWidth(220);
        table.getColumnModel().getColumn(1).setWidth(220);

        table.getColumnModel().getColumn(2).setMinWidth(220);
        table.getColumnModel().getColumn(2).setMaxWidth(220);
        table.getColumnModel().getColumn(2).setWidth(220);

        table.getColumnModel().getColumn(3).setMinWidth(220);
        table.getColumnModel().getColumn(3).setMaxWidth(220);
        table.getColumnModel().getColumn(3).setWidth(220);

        table.getColumnModel().getColumn(4).setMinWidth(220);
        table.getColumnModel().getColumn(4).setMaxWidth(220);
        table.getColumnModel().getColumn(4).setWidth(220);

        table.getColumnModel().getColumn(5).setMinWidth(220);
        table.getColumnModel().getColumn(5).setMaxWidth(220);
        table.getColumnModel().getColumn(5).setWidth(220);

        table.getColumnModel().getColumn(6).setMinWidth(220);
        table.getColumnModel().getColumn(6).setMaxWidth(220);
        table.getColumnModel().getColumn(6).setWidth(220);
    }

    private void enableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setWidth(50);

        table.getColumnModel().getColumn(7).setMinWidth(150);
        table.getColumnModel().getColumn(7).setMaxWidth(150);
        table.getColumnModel().getColumn(7).setWidth(150);

        table.getColumnModel().getColumn(8).setMinWidth(150);
        table.getColumnModel().getColumn(8).setMaxWidth(150);
        table.getColumnModel().getColumn(8).setWidth(150);

        isAuthorized = true;
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addRandomButton.setEnabled(true);
        logoutButton.setEnabled(true);
        authorizeButton.setEnabled(false);
        passwordInput.setEnabled(false);

        table.getColumnModel().getColumn(1).setMinWidth(150);
        table.getColumnModel().getColumn(1).setMaxWidth(150);
        table.getColumnModel().getColumn(1).setWidth(150);

        table.getColumnModel().getColumn(2).setMinWidth(150);
        table.getColumnModel().getColumn(2).setMaxWidth(150);
        table.getColumnModel().getColumn(2).setWidth(150);

        table.getColumnModel().getColumn(3).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMaxWidth(150);
        table.getColumnModel().getColumn(3).setWidth(150);

        table.getColumnModel().getColumn(4).setMinWidth(150);
        table.getColumnModel().getColumn(4).setMaxWidth(150);
        table.getColumnModel().getColumn(4).setWidth(150);

        table.getColumnModel().getColumn(5).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(200);
        table.getColumnModel().getColumn(5).setWidth(200);

        table.getColumnModel().getColumn(6).setMinWidth(200);
        table.getColumnModel().getColumn(6).setMaxWidth(200);
        table.getColumnModel().getColumn(6).setWidth(200);
    }

    private void refreshTable() {
        conn = DBConnection.getConnection();

        try {
            state = conn.prepareStatement("select id, name, fullname, founder, foundedin, description, headquaters, createdon, lastmodifiedon from carmake");
            result = state.executeQuery();
            table.setModel(new MyModel(result));
            if (!isAuthorized){
                disableFields();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void clearForm() {
        nameInput.setText("");
        fullNameInput.setText("");
        founderInput.setText("");
        foundedInInput.setCalendar(null);
        headquartersInput.setText("");
        descriptionInput.setText("");

        selectedId = -1;
    }

    private boolean checkPassword() {
        char[] correctPass = new char[]{'c', 'a', 'r', 'm', 'a', 'k', 'e', '1', '2', '3'};
        char[] password = (passwordInput.getPassword());
        return super.checkPassword(correctPass, password);
    }

    private class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row=table.getSelectedRow();
            selectedId=Integer.parseInt(table.getValueAt(row, 0).toString());
            nameInput.setText(table.getValueAt(row, 1).toString());
            fullNameInput.setText(table.getValueAt(row, 2).toString());
            founderInput.setText(table.getValueAt(row, 3).toString());
            try {
                foundedInInput.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 4).toString()));
            }
            catch (Exception error){

            }
            descriptionInput.setText(table.getValueAt(row, 5).toString());
            headquartersInput.setText(table.getValueAt(row, 6).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
    }

    private class AddAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "insert into carmake(name, fullname, founder, foundedin, headquaters, description, createdon, lastmodifiedon) values(?,?,?,?,?,?,?,?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, nameInput.getText());
                state.setString(2, fullNameInput.getText());
                state.setString(3, founderInput.getText());
                state.setDate(4, new java.sql.Date(foundedInInput.getDate().getTime()));
                state.setString(5, headquartersInput.getText());
                state.setString(6, descriptionInput.getText());
                state.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(8, new java.sql.Date(new java.util.Date().getTime()));

                state.execute();
                refreshTable();
                clearForm();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class AddRandomAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "insert into carmake(name, fullname, founder, foundedin, headquaters, description, createdon, lastmodifiedon) values(?,?,?,?,?,?,?,?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, "Make{" + generateRandomString(3) + "}");
                state.setString(2, "Manufacturer# " + generateRandomString(6));
                state.setString(3, "MR." + generateRandomString(8));
                state.setDate(4, new java.sql.Date(generateRandomDate().getTime()));
                state.setString(5, generateRandomString(10) + " INC.");
                state.setString(6, generateRandomString(30));
                state.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(8, new java.sql.Date(new java.util.Date().getTime()));

                state.execute();
                refreshTable();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class UpdateAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "update carmake set name = ?, fullname = ?, founder = ?, foundedin = ?, headquaters = ?, description = ?, lastmodifiedon = ? where id = ?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, nameInput.getText());
                state.setString(2, fullNameInput.getText());
                state.setString(3, founderInput.getText());
                state.setDate(4, new java.sql.Date(foundedInInput.getDate().getTime()));
                state.setString(5, headquartersInput.getText());
                state.setString(6, descriptionInput.getText());
                state.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
                state.setInt(8, selectedId);

                state.execute();
                refreshTable();
                selectedId = -1;
                clearForm();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn=DBConnection.getConnection();
            String sql = "";


            sql="delete from carmake where id=?";

            try {
                state=conn.prepareStatement(sql);
                state.setInt(1, selectedId);
                state.execute();
                refreshTable();
                clearForm();
                selectedId=-1;
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }

    private class SearchByNameAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchByNameInput.getText() == ""){
                return;
            }

            conn=DBConnection.getConnection();
            String sql="select id, name, fullname, founder, foundedin, description, headquaters, createdon, lastmodifiedon from carmake where name like ?";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, searchByNameInput.getText() + "%");
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                searchByNameInput.setText("");
                if(!isAuthorized){
                    disableFields();
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class SearchByHeadquartersAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchByHeadquartersInput.getText() == ""){
                return;
            }

            conn=DBConnection.getConnection();
            String sql="select id, name, fullname, founder, foundedin, description, headquaters, createdon, lastmodifiedon from carmake where headquaters like ?";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, searchByHeadquartersInput.getText() + "%");
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                searchByHeadquartersInput.setText("");
                if (!isAuthorized){
                    disableFields();
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class OrderByCreatedAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn=DBConnection.getConnection();
            String sql="select id, name, fullname, founder, foundedin, description, headquaters, createdon, lastmodifiedon from carmake order by createdon desc";

            try {
                state=conn.prepareStatement(sql);
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                if (!isAuthorized){
                    disableFields();
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class OrderByModifiedAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn=DBConnection.getConnection();
            String sql="select id, name, fullname, founder, foundedin, description, headquaters, createdon, lastmodifiedon from carmake order by lastmodifiedon desc";

            try {
                state=conn.prepareStatement(sql);
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                if (!isAuthorized){
                    disableFields();
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class ClearAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            clearForm();
        }
    }

    private class RefreshAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }

    private class AuthorizeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkPassword()){
                enableFields();
            }

            passwordInput.setText("");
        }
    }

    private class LogoutAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            disableFields();
            passwordInput.setText("");
        }
    }
}



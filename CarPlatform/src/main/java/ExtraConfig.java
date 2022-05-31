package main.java;

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

public class ExtraConfig extends BaseConfig {
    JTabbedPane mainTab;

    // Panels
    JPanel mainPanel = new JPanel();
    JPanel formPanel = new JPanel();
    JPanel filterPanel = new JPanel();
    JPanel tablePanel = new JPanel();

    // Form Labels
    JLabel nameLabel = new JLabel("Name:");
    JLabel brandLabel = new JLabel("Brand:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel descriptionLabel = new JLabel("Description:");
    JLabel carLabel = new JLabel("Car:");

    // Form Fields
    JTextField nameInput = new JTextField();
    JTextField brandInput = new JTextField();
    JTextField priceInput = new JTextField();
    JTextField descriptionInput = new JTextField();
    JComboBox<String> carCombo = new JComboBox();

    JPasswordField passwordInput = new JPasswordField();
    JTextField searchByNameInput = new JTextField();
    JTextField searchByBrandInput = new JTextField();

    // Table
    JTable table= new JTable();
    JScrollPane scroll = new JScrollPane(table);

    // Buttons
    JButton addButton = new JButton("Add Extra");
    JButton deleteButton = new JButton("Delete Extra");
    JButton editButton = new JButton("Edit Extra");
    JButton searchByNameButton = new JButton("Search Extra By Name");
    JButton searchByBrandButton = new JButton("Search Extra By Brand");
    JButton refreshButton = new JButton("Refresh Extra");
    JButton addRandomButton = new JButton("Add Random Extra");
    JButton orderByCreatedOnButton = new JButton("Order By Created On");
    JButton orderByLastModifiedOnButton = new JButton("Order By Last Modified On");
    JButton clearFormButton = new JButton("Clear Extra Form");
    JButton authorizeButton = new JButton("Authorize");
    JButton logoutButton = new JButton("Logout...");

    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result = null;
    int selectedId = -1;
    boolean isAuthorized = false;
    boolean isFormEnabled = false;

    public ExtraConfig(JTabbedPane mainTab) {
        this.mainTab = mainTab;
        this.refreshAll();
    }

    public void configureScreen() {
        mainTab.add("Extra", mainPanel);

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
        formPanel.setLayout(new GridLayout(7, 2));
        formPanel.add(nameLabel);
        formPanel.add(nameInput);

        formPanel.add(brandLabel);
        formPanel.add(brandInput);

        formPanel.add(priceLabel);
        formPanel.add(priceInput);

        formPanel.add(descriptionLabel);
        formPanel.add(descriptionInput);

        formPanel.add(carLabel);
        formPanel.add(carCombo);

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

        filterPanel.add(searchByBrandButton);
        filterPanel.add(searchByBrandInput);

        filterPanel.add(orderByCreatedOnButton);
        filterPanel.add(orderByLastModifiedOnButton);

        filterPanel.add(authorizeButton);
        filterPanel.add(passwordInput);

        filterPanel.add(logoutButton);
    }

    private void refreshCarCombo(){
        String sql = "select model from car";
        conn = DBConnection.getConnection();
        String item ="";

        try {
            state=conn.prepareStatement(sql);
            result = state.executeQuery();
            carCombo.removeAllItems();
            while(result.next()) {
                item = result.getObject(1).toString();
                carCombo.addItem(item);
                isFormEnabled = true;
                this.setEnableForm();
            }

            if (!isFormEnabled){
                this.setDisableForm();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setDisableForm(){
        addButton.setEnabled(false);
        authorizeButton.setEnabled(false);
    }

    private void setEnableForm(){
        addButton.setEnabled(true);
        authorizeButton.setEnabled(true);
    }

    private void disableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);

        table.getColumnModel().getColumn(7).setMinWidth(0);
        table.getColumnModel().getColumn(7).setMaxWidth(0);
        table.getColumnModel().getColumn(7).setWidth(0);

        isAuthorized = false;
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addRandomButton.setEnabled(false);
        logoutButton.setEnabled(false);
        authorizeButton.setEnabled(true);
        passwordInput.setEnabled(true);

        table.getColumnModel().getColumn(1).setMinWidth(200);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setWidth(200);

        table.getColumnModel().getColumn(2).setMinWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setWidth(200);

        table.getColumnModel().getColumn(3).setMinWidth(200);
        table.getColumnModel().getColumn(3).setMaxWidth(200);
        table.getColumnModel().getColumn(3).setWidth(200);

        table.getColumnModel().getColumn(5).setMinWidth(400);
        table.getColumnModel().getColumn(5).setMaxWidth(400);
        table.getColumnModel().getColumn(5).setWidth(400);

        table.getColumnModel().getColumn(4).setMinWidth(200);
        table.getColumnModel().getColumn(4).setMaxWidth(200);
        table.getColumnModel().getColumn(4).setWidth(200);
    }

    private void enableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setWidth(50);

        table.getColumnModel().getColumn(6).setMinWidth(150);
        table.getColumnModel().getColumn(6).setMaxWidth(150);
        table.getColumnModel().getColumn(6).setWidth(150);

        table.getColumnModel().getColumn(7).setMinWidth(150);
        table.getColumnModel().getColumn(7).setMaxWidth(150);
        table.getColumnModel().getColumn(7).setWidth(150);

        isAuthorized = true;
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addRandomButton.setEnabled(true);
        logoutButton.setEnabled(true);
        authorizeButton.setEnabled(false);
        passwordInput.setEnabled(false);

        table.getColumnModel().getColumn(1).setMinWidth(200);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setWidth(200);

        table.getColumnModel().getColumn(2).setMinWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setWidth(200);

        table.getColumnModel().getColumn(3).setMinWidth(200);
        table.getColumnModel().getColumn(3).setMaxWidth(200);
        table.getColumnModel().getColumn(3).setWidth(200);

        table.getColumnModel().getColumn(4).setMinWidth(200);
        table.getColumnModel().getColumn(4).setMaxWidth(200);
        table.getColumnModel().getColumn(4).setWidth(200);

        table.getColumnModel().getColumn(5).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(200);
        table.getColumnModel().getColumn(5).setWidth(200);
    }

    private void configureTablePanel(){
        scroll.setPreferredSize(new Dimension(1200, 1200));
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
        searchByBrandButton.addActionListener(new SearchByBrandAction());

        orderByCreatedOnButton.addActionListener(new OrderByCreatedAction());
        orderByLastModifiedOnButton.addActionListener(new OrderByModifiedAction());

        addRandomButton.addActionListener(new AddRandomAction());
        authorizeButton.addActionListener(new AuthorizeAction());
        logoutButton.addActionListener(new LogoutAction());
    }

    private void refreshAll() {
        this.refreshCarCombo();
        this.refreshTable();
    }

    private void refreshTable() {
        conn = DBConnection.getConnection();

        try {
            state = conn.prepareStatement("select t.id, c.Model AS Car, t.name, t.brand, t.price, t.description, t.createdon, t.lastmodifiedon from extra t JOIN Car c ON c.ID = t.carid");
            result = state.executeQuery();
            table.setModel(new MyModel(result));
            if (!isAuthorized){
                this.disableFields();
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
        brandInput.setText("");
        priceInput.setText("");
        descriptionInput.setText("");
        this.refreshCarCombo();
        selectedId = -1;
    }

    private boolean checkPassword() {
        char[] correctPass = new char[]{'e', 'x', 't', 'r', 'a', '1', '2', '3'};
        char[] password = (passwordInput.getPassword());
        return super.checkPassword(correctPass, password);
    }

    private class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            int row=table.getSelectedRow();
            selectedId=Integer.parseInt(table.getValueAt(row, 0).toString());
            nameInput.setText(table.getValueAt(row, 2).toString());
            brandInput.setText(table.getValueAt(row, 3).toString());
            priceInput.setText(table.getValueAt(row, 4).toString());
            descriptionInput.setText(table.getValueAt(row, 5).toString());
            carCombo.setSelectedItem(table.getValueAt(row, 1).toString());
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
            int carId = getCarId(carCombo.getSelectedItem().toString());
            conn = DBConnection.getConnection();
            String sql = "insert into extra(name, brand, price, description, createdon, lastmodifiedon, carid) values(?,?,?,?,?,?,?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, nameInput.getText());
                state.setString(2, brandInput.getText());
                state.setDouble(3, Double.parseDouble(priceInput.getText()));
                state.setString(4, descriptionInput.getText());
                state.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
                state.setInt(7, carId);

                state.execute();
                refreshAll();
                clearForm();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private int getCarId(String selectedCar) {
        conn = DBConnection.getConnection();
        String sql = "select id from car where model like ?";

        try {
            state = conn.prepareStatement(sql);
            state.setString(1, selectedCar);

            result = state.executeQuery();
            while(result.next()) {
                String id = result.getObject(1).toString();
                return Integer.parseInt(id);
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return 0;
    }

    private class AddRandomAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int carId = getCarId(carCombo.getSelectedItem().toString());
            conn = DBConnection.getConnection();
            String sql = "insert into extra(name, brand, price, description, createdon, lastmodifiedon, carid) values(?,?,?,?,?,?,?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, "Extra{" + generateRandomString(6) + "}");
                state.setString(2, "Brand# " + generateRandomString(4));
                state.setDouble(3, generateRandomInt(10, 10000));
                state.setString(4, "Help with " + generateRandomString(30));
                state.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
                state.setInt(7, carId);

                state.execute();
                refreshAll();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private class UpdateAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int carId = getCarId(carCombo.getSelectedItem().toString());
            conn = DBConnection.getConnection();
            String sql = "update extra set name = ?, brand = ?, price = ?, description = ?, lastmodifiedon = ?, carid = ? where id = ?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, nameInput.getText());
                state.setString(2, brandInput.getText());
                state.setDouble(3, Double.parseDouble(priceInput.getText()));
                state.setString(4, descriptionInput.getText());
                state.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                state.setInt(6, carId);
                state.setInt(7, selectedId);

                state.execute();
                refreshAll();
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
            String sql="delete from extra where id=?";

            try {
                state=conn.prepareStatement(sql);
                state.setInt(1, selectedId);
                state.execute();
                refreshAll();
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
            String sql="select t.id, c.Model AS Car, t.name, t.brand, t.price, t.description, t.createdon, t.lastmodifiedon from extra t JOIN Car c ON c.ID = t.carid where t.name like ?";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, searchByNameInput.getText() + "%");
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                searchByNameInput.setText("");
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

    private class SearchByBrandAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchByBrandInput.getText() == ""){
                return;
            }

            conn=DBConnection.getConnection();
            String sql="select t.id, c.Model AS Car, t.name, t.brand, t.price, t.description, t.createdon, t.lastmodifiedon from extra t JOIN Car c ON c.ID = t.carid where t.brand like ?";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, searchByBrandInput.getText() + "%");
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                searchByBrandInput.setText("");
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
            String sql="select t.id, c.Model AS Car, t.name, t.brand, t.price, t.description, t.createdon, t.lastmodifiedon from extra t JOIN Car c ON c.ID = t.carid order by t.createdon desc";

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
            String sql="select t.id, c.Model AS Car, t.name, t.brand, t.price, t.description, t.createdon, t.lastmodifiedon from extra t JOIN Car c ON c.ID = t.carid order by t.lastmodifiedon desc";

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
            refreshAll();
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

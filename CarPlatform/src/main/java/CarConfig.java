package main.java;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
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

public class CarConfig extends BaseConfig {
    JTabbedPane mainTab;
    TuningConfig tuningConfig;
    ExtraConfig extraConfig;

    // Panels
    JPanel mainPanel = new JPanel();
    JTabbedPane functionTab = new JTabbedPane();

    JPanel formPanel = new JPanel();
    JPanel filterPanel = new JPanel();

    JPanel tablePanel = new JPanel();

    // Form Labels
    JLabel modelLabel = new JLabel("Model:");
    JLabel carMakeLabel = new JLabel("Car Make:");
    JLabel powerLabel = new JLabel("Power ( HrsPw ):");
    JLabel doorsLabel = new JLabel("Doors:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel consumptionLabel = new JLabel("Consumption ( Per 100 km):");
    JLabel maxSpeedLabel = new JLabel("Max Speed ( km/h ):");
    JLabel colorLabel = new JLabel("Color:");
    JLabel yearLabel = new JLabel("Year:");
    JLabel descriptionLabel = new JLabel("Description:");

    // Form Fields
    JTextField modelInput = new JTextField();
    JTextField powerInput = new JTextField();
    JTextField doorsInput = new JTextField();
    JTextField priceInput = new JTextField();
    JTextField consumptionInput = new JTextField();
    JTextField maxSpeedInput = new JTextField();
    JTextField colorInput = new JTextField();
    JDateChooser yearInput = new JDateChooser();
    JTextField descriptionInput = new JTextField();
    JComboBox<String> carMakeCombo = new JComboBox();


    JPasswordField passwordInput = new JPasswordField();
    JTextField searchByModelInput = new JTextField();
    JTextField searchByDoorsInput = new JTextField();

    // Table
    JTable table= new JTable();
    JScrollPane scroll = new JScrollPane(table);

    // Buttons
    JButton addButton = new JButton("Add Car");
    JButton deleteButton = new JButton("Delete Car");
    JButton editButton = new JButton("Edit Car");
    JButton searchByModelButton = new JButton("Search Car By Model");
    JButton searchByDoorsButton = new JButton("Search Car By Doors");
    JButton refreshButton = new JButton("Refresh");
    JButton addRandomButton = new JButton("Add Random Car");
    JButton orderByCreatedOnButton = new JButton("Order By Created On");
    JButton orderByLastModifiedOnButton = new JButton("Order By Last Modified On");
    JButton clearFormButton = new JButton("Clear Car Form");
    JButton authorizeButton = new JButton("Authorize");
    JButton logoutButton = new JButton("Logout...");

    Connection conn = null;
    PreparedStatement state = null;
    ResultSet result = null;
    int selectedId = -1;
    boolean isAuthorized = false;
    boolean isFormEnabled = false;

    public CarConfig(JTabbedPane mainTab,TuningConfig tuningConfig, ExtraConfig extraConfig) {
        this.mainTab = mainTab;
        this.tuningConfig = tuningConfig;
        this.extraConfig = extraConfig;
        this.refreshAll();
    }

    private void setDisableForm(){
        addButton.setEnabled(false);
        authorizeButton.setEnabled(false);
    }

    private void setEnableForm(){
        addButton.setEnabled(true);
        authorizeButton.setEnabled(true);
    }

    public void configureScreen() {
        mainTab.add("Car", mainPanel);

        mainPanel.setLayout(new GridLayout(2,1));

        mainPanel.add(functionTab);
        this.configureFunctionTab();
        mainPanel.add(tablePanel);
        this.configureTablePanel();

        this.configureButtons();
        this.addStyles();
    }

    private void configureFunctionTab() {
        functionTab.add("Form", formPanel);
        this.configureFormPanel();
        functionTab.add("Filter", filterPanel);
        this.configureFilterPanel();
    }

    private void addStyles() {
        formPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    private void configureFormPanel(){
        formPanel.setLayout(new GridLayout(7, 4));

        formPanel.add(modelLabel);
        formPanel.add(modelInput);
        formPanel.add(powerLabel);
        formPanel.add(powerInput);

        formPanel.add(doorsLabel);
        formPanel.add(doorsInput);
        formPanel.add(priceLabel);
        formPanel.add(priceInput);

        formPanel.add(consumptionLabel);
        formPanel.add(consumptionInput);
        formPanel.add(colorLabel);
        formPanel.add(colorInput);

        formPanel.add(maxSpeedLabel);
        formPanel.add(maxSpeedInput);
        formPanel.add(yearLabel);
        formPanel.add(yearInput);

        formPanel.add(descriptionLabel);
        formPanel.add(descriptionInput);

        formPanel.add(carMakeLabel);
        formPanel.add(carMakeCombo);

        formPanel.add(addButton);
        formPanel.add(addRandomButton);
        formPanel.add(editButton);
        formPanel.add(deleteButton);
    }

    private void configureFilterPanel() {
        filterPanel.setLayout(new GridLayout(6, 2));

        filterPanel.add(refreshButton);
        filterPanel.add(clearFormButton);

        filterPanel.add(searchByModelButton);
        filterPanel.add(searchByModelInput);

        filterPanel.add(searchByDoorsButton);
        filterPanel.add(searchByDoorsInput);

        filterPanel.add(orderByCreatedOnButton);
        filterPanel.add(orderByLastModifiedOnButton);

        filterPanel.add(authorizeButton);
        filterPanel.add(passwordInput);

        filterPanel.add(logoutButton);
    }

    private void disableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.getColumnModel().getColumn(11).setMinWidth(0);
        table.getColumnModel().getColumn(11).setMaxWidth(0);
        table.getColumnModel().getColumn(11).setWidth(0);

        table.getColumnModel().getColumn(12).setMinWidth(0);
        table.getColumnModel().getColumn(12).setMaxWidth(0);
        table.getColumnModel().getColumn(12).setWidth(0);

        isAuthorized = false;
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addRandomButton.setEnabled(false);
        logoutButton.setEnabled(false);
        authorizeButton.setEnabled(true);
        passwordInput.setEnabled(true);

        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setWidth(100);

        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setWidth(100);

        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setWidth(100);

        table.getColumnModel().getColumn(4).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setWidth(100);

        table.getColumnModel().getColumn(5).setMinWidth(100);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
        table.getColumnModel().getColumn(5).setWidth(100);

        table.getColumnModel().getColumn(6).setMinWidth(100);
        table.getColumnModel().getColumn(6).setMaxWidth(100);
        table.getColumnModel().getColumn(6).setWidth(100);

        table.getColumnModel().getColumn(7).setMinWidth(100);
        table.getColumnModel().getColumn(7).setMaxWidth(100);
        table.getColumnModel().getColumn(7).setWidth(100);

        table.getColumnModel().getColumn(8).setMinWidth(100);
        table.getColumnModel().getColumn(8).setMaxWidth(100);
        table.getColumnModel().getColumn(8).setWidth(100);

        table.getColumnModel().getColumn(9).setMinWidth(100);
        table.getColumnModel().getColumn(9).setMaxWidth(100);
        table.getColumnModel().getColumn(9).setWidth(100);

        table.getColumnModel().getColumn(10).setMinWidth(100);
        table.getColumnModel().getColumn(10).setMaxWidth(100);
        table.getColumnModel().getColumn(10).setWidth(100);
    }

    private void enableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setWidth(50);

        table.getColumnModel().getColumn(11).setMinWidth(120);
        table.getColumnModel().getColumn(11).setMaxWidth(120);
        table.getColumnModel().getColumn(11).setWidth(120);

        table.getColumnModel().getColumn(12).setMinWidth(120);
        table.getColumnModel().getColumn(12).setMaxWidth(120);
        table.getColumnModel().getColumn(12).setWidth(120);

        isAuthorized = true;
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        addRandomButton.setEnabled(true);
        logoutButton.setEnabled(true);
        authorizeButton.setEnabled(false);
        passwordInput.setEnabled(false);

        table.getColumnModel().getColumn(1).setMinWidth(80);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setWidth(80);

        table.getColumnModel().getColumn(2).setMinWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setWidth(80);

        table.getColumnModel().getColumn(3).setMinWidth(80);
        table.getColumnModel().getColumn(3).setMaxWidth(80);
        table.getColumnModel().getColumn(3).setWidth(80);

        table.getColumnModel().getColumn(4).setMinWidth(80);
        table.getColumnModel().getColumn(4).setMaxWidth(80);
        table.getColumnModel().getColumn(4).setWidth(80);

        table.getColumnModel().getColumn(5).setMinWidth(80);
        table.getColumnModel().getColumn(5).setMaxWidth(80);
        table.getColumnModel().getColumn(5).setWidth(80);

        table.getColumnModel().getColumn(6).setMinWidth(80);
        table.getColumnModel().getColumn(6).setMaxWidth(80);
        table.getColumnModel().getColumn(6).setWidth(80);

        table.getColumnModel().getColumn(7).setMinWidth(80);
        table.getColumnModel().getColumn(7).setMaxWidth(80);
        table.getColumnModel().getColumn(7).setWidth(80);

        table.getColumnModel().getColumn(8).setMinWidth(80);
        table.getColumnModel().getColumn(8).setMaxWidth(80);
        table.getColumnModel().getColumn(8).setWidth(80);

        table.getColumnModel().getColumn(9).setMinWidth(80);
        table.getColumnModel().getColumn(9).setMaxWidth(80);
        table.getColumnModel().getColumn(9).setWidth(80);

        table.getColumnModel().getColumn(10).setMinWidth(80);
        table.getColumnModel().getColumn(10).setMaxWidth(80);
        table.getColumnModel().getColumn(10).setWidth(80);
    }

    private void configureTablePanel(){
        scroll.setPreferredSize(new Dimension(1200, 900));
        tablePanel.add(scroll);
        table.addMouseListener(new MouseAction());
    }

    private void configureButtons(){
        addButton.addActionListener(new AddAction());
        editButton.addActionListener(new UpdateAction());
        deleteButton.addActionListener(new DeleteAction());

        refreshButton.addActionListener(new RefreshAction());
        clearFormButton.addActionListener(new ClearAction());

        searchByModelButton.addActionListener(new SearchByModelAction());
        searchByDoorsButton.addActionListener(new SearchByDoorsAction());

        orderByCreatedOnButton.addActionListener(new OrderByCreatedAction());
        orderByLastModifiedOnButton.addActionListener(new OrderByModifiedAction());

        addRandomButton.addActionListener(new AddRandomAction());
        authorizeButton.addActionListener(new AuthorizeAction());
        logoutButton.addActionListener(new LogoutAction());
    }

    public void refreshAll() {
        this.refreshCarMakeCombo();
        this.refreshTable();
        tuningConfig.refreshTable();
        tuningConfig.refreshCarCombo();
        extraConfig.refreshCarCombo();
        extraConfig.refreshTable();
    }

    public void refreshTable(){
        conn = DBConnection.getConnection();

        try {
            state = conn.prepareStatement("select c.ID, c.MODEL , c2.NAME AS CarMake, c.POWER , c.DOORS , c.PRICE , c.CONSUMPTION , c.MAXSPEED , c.COLOR , c.\"YEAR\" , c.DESCRIPTION, c.CREATEDON , c.LASTMODIFIEDON from car c JOIN CARMAKE c2 ON c.CARMAKEID = c2.ID");
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

    public void refreshCarMakeCombo(){
        String sql = "select name from carmake";
        conn = DBConnection.getConnection();
        String item ="";

        try {
            state=conn.prepareStatement(sql);
            result = state.executeQuery();
            carMakeCombo.removeAllItems();
            while(result.next()) {
                item = result.getObject(1).toString();
                carMakeCombo.addItem(item);
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

    private void clearForm() {
        modelInput.setText("");
        powerInput.setText("");
        doorsInput.setText("");
        priceInput.setText("");
        consumptionInput.setText("");
        maxSpeedInput.setText("");
        colorInput.setText("");
        yearInput.setDate(null);
        descriptionInput.setText("");
        refreshCarMakeCombo();
        selectedId = -1;
    }

    private boolean checkPassword() {
        char[] correctPass = new char[]{'c', 'a', 'r', '1', '2', '3'};
        char[] password = (passwordInput.getPassword());
        return super.checkPassword(correctPass, password);
    }

    private class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row=table.getSelectedRow();
            selectedId=Integer.parseInt(table.getValueAt(row, 0).toString());
            modelInput.setText(table.getValueAt(row, 1).toString());
            carMakeCombo.setSelectedItem(table.getValueAt(row, 2).toString());
            powerInput.setText(table.getValueAt(row, 3).toString());
            doorsInput.setText(table.getValueAt(row, 4).toString());
            priceInput.setText(table.getValueAt(row, 5).toString());
            consumptionInput.setText(table.getValueAt(row, 6).toString());
            maxSpeedInput.setText(table.getValueAt(row, 7).toString());
            colorInput.setText(table.getValueAt(row, 8).toString());
            try {
                yearInput.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(row, 9).toString()));
            }
            catch (Exception error){

            }
            descriptionInput.setText(table.getValueAt(row, 10).toString());
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
            int carMakeId = getCarMakeId(carMakeCombo.getSelectedItem().toString());

            conn = DBConnection.getConnection();
            String sql = "insert into car(MODEL, CARMAKEID , POWER , DOORS , PRICE , CONSUMPTION , MAXSPEED , COLOR , \"YEAR\" , DESCRIPTION , CREATEDON , LASTMODIFIEDON) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, modelInput.getText());
                state.setInt(2, carMakeId);
                state.setInt(3, Integer.parseInt(powerInput.getText()));
                state.setInt(4, Integer.parseInt(doorsInput.getText()));
                state.setDouble(5, Double.parseDouble(priceInput.getText()));
                state.setDouble(6, Double.parseDouble(consumptionInput.getText()));
                state.setInt(7, Integer.parseInt(maxSpeedInput.getText()));
                state.setString(8, colorInput.getText());
                state.setDate(9,  new java.sql.Date(yearInput.getDate().getTime()));
                state.setString(10, descriptionInput.getText());
                state.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(12, new java.sql.Date(new java.util.Date().getTime()));

                state.execute();
                refreshAll();
                clearForm();

            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private int getCarMakeId(String selectedCarMake) {
        conn = DBConnection.getConnection();
        String sql = "select id from carmake where name like ?";

        try {
            state = conn.prepareStatement(sql);
            state.setString(1, selectedCarMake);

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
            int carMakeId = getCarMakeId(carMakeCombo.getSelectedItem().toString());
            conn = DBConnection.getConnection();
            String sql = "insert into car(MODEL, CARMAKEID , POWER , DOORS , PRICE , CONSUMPTION , MAXSPEED , COLOR , \"YEAR\" , DESCRIPTION , CREATEDON , LASTMODIFIEDON) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, "Model#" + generateRandomString(5));
                state.setInt(2, carMakeId);
                state.setInt(3, generateRandomInt(40, 600));
                state.setInt(4, generateRandomInt(2, 10));
                state.setDouble(5, generateRandomDouble(100, 100000));
                state.setDouble(6, generateRandomDouble(2, 40));
                state.setInt(7,  generateRandomInt(60, 400));
                state.setString(8, "code#" + generateRandomString(6));
                state.setDate(9,  new java.sql.Date(new java.util.Date().getTime()));
                state.setString(10, generateRandomString(50));
                state.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(12, new java.sql.Date(new java.util.Date().getTime()));

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
            int carMakeId = getCarMakeId(carMakeCombo.getSelectedItem().toString());
            conn = DBConnection.getConnection();
            String sql = "update car set MODEL = ?, CARMAKEID = ?, POWER = ?, DOORS = ?, PRICE = ?, CONSUMPTION = ?, MAXSPEED = ?, COLOR = ?, \"YEAR\" = ?, DESCRIPTION = ? , lastmodifiedon = ? where id = ?";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, modelInput.getText());
                state.setInt(2, carMakeId);
                state.setInt(3, Integer.parseInt(powerInput.getText()));
                state.setInt(4, Integer.parseInt(doorsInput.getText()));
                state.setDouble(5, Double.parseDouble(priceInput.getText()));
                state.setDouble(6, Double.parseDouble(consumptionInput.getText()));
                state.setInt(7, Integer.parseInt(maxSpeedInput.getText()));
                state.setString(8, colorInput.getText());
                state.setDate(9,  new java.sql.Date(yearInput.getDate().getTime()));
                state.setString(10, descriptionInput.getText());
                state.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
                state.setInt(12, selectedId);

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
            String sql="delete from car where id=?";

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

    private class SearchByModelAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchByModelInput.getText() == ""){
                return;
            }

            conn=DBConnection.getConnection();
            String sql="select c.ID, c.MODEL , c2.NAME AS CarMake, c.POWER , c.DOORS , c.PRICE , c.CONSUMPTION , c.MAXSPEED , c.COLOR , c.\"YEAR\" , c.DESCRIPTION, c.CREATEDON , c.LASTMODIFIEDON from car c JOIN CARMAKE c2 ON c.CARMAKEID = c2.ID where c.model like ?";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, searchByModelInput.getText() + "%");
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                searchByModelInput.setText("");
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

    private class SearchByDoorsAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchByDoorsInput.getText() == ""){
                return;
            }

            conn=DBConnection.getConnection();
            String sql="select c.ID, c.MODEL , c2.NAME AS CarMake, c.POWER , c.DOORS , c.PRICE , c.CONSUMPTION , c.MAXSPEED , c.COLOR , c.\"YEAR\" , c.DESCRIPTION, c.CREATEDON , c.LASTMODIFIEDON from car c JOIN CARMAKE c2 ON c.CARMAKEID = c2.ID where c.doors = ?";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, searchByDoorsInput.getText());
                result=state.executeQuery();
                table.setModel(new MyModel(result));
                searchByDoorsInput.setText("");
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
            String sql="select c.ID, c.MODEL , c2.NAME AS CarMake, c.POWER , c.DOORS , c.PRICE , c.CONSUMPTION , c.MAXSPEED , c.COLOR , c.\"YEAR\" , c.DESCRIPTION, c.CREATEDON , c.LASTMODIFIEDON from car c JOIN CARMAKE c2 ON c.CARMAKEID = c2.ID order by c.createdon desc";

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
            String sql="select c.ID, c.MODEL , c2.NAME AS CarMake, c.POWER , c.DOORS , c.PRICE , c.CONSUMPTION , c.MAXSPEED , c.COLOR , c.\"YEAR\" , c.DESCRIPTION, c.CREATEDON , c.LASTMODIFIEDON from car c JOIN CARMAKE c2 ON c.CARMAKEID = c2.ID order by c.lastmodifiedon desc";

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






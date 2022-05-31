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
import java.util.ArrayList;

public class CarConfig extends BaseConfig {
    JTabbedPane mainTab;

    // Panels
    JPanel mainPanel = new JPanel();
    JTabbedPane functionTab = new JTabbedPane();

    JPanel formPanel = new JPanel();
    JPanel filterPanel = new JPanel();
    JPanel orderPanel = new JPanel();
    JPanel otherPanel = new JPanel();

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
    JLabel extrasLabel = new JLabel("Extras:");
    JLabel tuningLabel = new JLabel("Tuning:");

    // Form Fields
    JTextField modelInput = new JTextField();
    JTextField powerInput = new JTextField();
    JTextField doorsInput = new JTextField();
    JCheckBox isAutomaticCheckbox = new JCheckBox("Is Automatic");
    JCheckBox isLeftSteeringCheckbox = new JCheckBox("Is Left Steering");
    JTextField priceInput = new JTextField();
    JTextField consumptionInput = new JTextField();
    JTextField maxSpeedInput = new JTextField();
    JTextField colorInput = new JTextField();
    JDateChooser yearInput = new JDateChooser();
    JTextField descriptionInput = new JTextField();
    JComboBox<String> carMakeCombo = new JComboBox();
    JComboBox<String> extraCombo = new JComboBox();
    JComboBox<String> tuningCombo = new JComboBox();


    JPasswordField passwordInput = new JPasswordField();
    JTextField searchByNameInput = new JTextField();

    // Table
    JTable table= new JTable();
    JScrollPane scroll = new JScrollPane(table);

    JTable tableExtras= new JTable();
    JScrollPane scrollExtras = new JScrollPane(tableExtras);

    JTable tableTunings = new JTable();
    JScrollPane scrollTunings = new JScrollPane(tableTunings);

    // Buttons
    JButton addButton = new JButton("Add Car");
    JButton deleteButton = new JButton("Delete Car");
    JButton editButton = new JButton("Edit Car");
    JButton searchByNameButton = new JButton("Search Car By Name");
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
    int selectedCarId = -1;
    int selectedTuningId = -1;
    int selectedExtraId = -1;
    boolean isAuthorized = false;

    public CarConfig(JTabbedPane mainTab) {
        this.mainTab = mainTab;
        this.refreshAll();
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
        //configure
        functionTab.add("Order", orderPanel);
        //configure
        functionTab.add("Other", otherPanel);
        //configure
    }

    private void addStyles() {
        formPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        otherPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        orderPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        table.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        tableExtras.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        tableTunings.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    private void configureFormPanel(){
        formPanel.setLayout(new GridLayout(8, 4));

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

        formPanel.add(isAutomaticCheckbox);
        formPanel.add(isLeftSteeringCheckbox);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionInput);

        formPanel.add(extrasLabel);
        formPanel.add(extraCombo);
        formPanel.add(tuningLabel);
        formPanel.add(tuningCombo);

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

        filterPanel.add(searchByNameButton);
        filterPanel.add(searchByNameInput);

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

        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);

        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);

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

        table.getColumnModel().getColumn(4).setMinWidth(400);
        table.getColumnModel().getColumn(4).setMaxWidth(400);
        table.getColumnModel().getColumn(4).setWidth(400);
    }

    private void enableFields() {
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setWidth(50);

        table.getColumnModel().getColumn(5).setMinWidth(150);
        table.getColumnModel().getColumn(5).setMaxWidth(150);
        table.getColumnModel().getColumn(5).setWidth(150);

        table.getColumnModel().getColumn(6).setMinWidth(150);
        table.getColumnModel().getColumn(6).setMaxWidth(150);
        table.getColumnModel().getColumn(6).setWidth(150);

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
    }

    private void configureTablePanel(){
        tablePanel.setLayout(new GridLayout(3, 1));

        scrollExtras.setPreferredSize(new Dimension(1200, 300));
        scrollTunings.setPreferredSize(new Dimension(1200, 300));
        scroll.setPreferredSize(new Dimension(1200, 300));


        tablePanel.add(scrollExtras);
        tablePanel.add(scrollTunings);
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

        orderByCreatedOnButton.addActionListener(new OrderByCreatedAction());
        orderByLastModifiedOnButton.addActionListener(new OrderByModifiedAction());

        addRandomButton.addActionListener(new AddRandomAction());
        authorizeButton.addActionListener(new AuthorizeAction());
        logoutButton.addActionListener(new LogoutAction());
    }

    private void refreshAll(){
        this.refreshTable();
        this.refreshCarMakeCombo();
        this.refreshExtraCombo();
        this.refreshTuningCombo();
        this.refreshTable();
    }

    private void refreshTable() {
        this.refreshExtraTable();
        this.refreshTuningTable();
    }

    private void refreshTuningTable(){
        conn = DBConnection.getConnection();

        try {
            state = conn.prepareStatement("select id, name, brand, function, description, createdon, lastmodifiedon from tuning");
            result = state.executeQuery();
            tableTunings.setModel(new MyModel(result));
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

    private void refreshExtraTable(){
        conn = DBConnection.getConnection();

        try {
            state = conn.prepareStatement("select id, name, brand, price, description, createdon, lastmodifiedon from extra");
            result = state.executeQuery();
            tableExtras.setModel(new MyModel(result));
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

    private void refreshExtraCombo(){
        String sql = "select name, brand from extra";
        conn = DBConnection.getConnection();
        ArrayList<Object> items = new ArrayList();
        String item = "";

        try {
            state=conn.prepareStatement(sql);
            result = state.executeQuery();
            extraCombo.removeAllItems();
            while(result.next()) {
                item = result.getObject(1).toString() + " - " + result.getObject(2).toString();
                items.add(item);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SelectionManager manager = new SelectionManager();
        MultiRenderer renderer = new MultiRenderer(manager);
        DefaultComboBoxModel model = new DefaultComboBoxModel(items.toArray());
        extraCombo = new JComboBox(model);
        extraCombo.addActionListener(manager);
        extraCombo.setRenderer(renderer);
    }

    private void refreshTuningCombo(){
        String sql = "select name, brand from tuning";
        conn = DBConnection.getConnection();
        ArrayList<Object> items = new ArrayList();
        String item ="";

        try {
            state=conn.prepareStatement(sql);
            result = state.executeQuery();
            tuningCombo.removeAllItems();
            while(result.next()) {
                item = result.getObject(1).toString() + " - " + result.getObject(2).toString();
                items.add(item);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SelectionManager manager = new SelectionManager();
        MultiRenderer renderer = new MultiRenderer(manager);
        DefaultComboBoxModel model = new DefaultComboBoxModel(items.toArray());
        tuningCombo = new JComboBox(model);
        tuningCombo.addActionListener(manager);
        tuningCombo.setRenderer(renderer);
    }

    private void refreshCarMakeCombo(){
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
        isAutomaticCheckbox.setSelected(false);
        isLeftSteeringCheckbox.setText("");
        priceInput.setText("");
        consumptionInput.setText("");
        maxSpeedInput.setText("");
        colorInput.setText("");
        yearInput.setDate(null);
        descriptionInput.setText("");
        refreshTuningCombo();
        refreshExtraCombo();
        refreshCarMakeCombo();

        selectedCarId = -1;
        selectedTuningId = -1;
        selectedExtraId = -1;
    }

    private boolean checkPassword() {
        char[] correctPass = new char[]{'c', 'a', 'r', '1', '2', '3'};
        char[] password = (passwordInput.getPassword());
        return super.checkPassword(correctPass, password);
    }

    private class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

//            int row=table.getSelectedRow();
//            selectedId=Integer.parseInt(table.getValueAt(row, 0).toString());
//            nameInput.setText(table.getValueAt(row, 1).toString());
//            brandInput.setText(table.getValueAt(row, 2).toString());
//            priceInput.setText(table.getValueAt(row, 3).toString());
//            descriptionInput.setText(table.getValueAt(row, 4).toString());
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
//            conn = DBConnection.getConnection();
//            String sql = "insert into extra(name, brand, price, description, createdon, lastmodifiedon) values(?,?,?,?,?,?)";
//
//            try {
//                state = conn.prepareStatement(sql);
//                state.setString(1, nameInput.getText());
//                state.setString(2, brandInput.getText());
//                state.setDouble(3, Double.parseDouble(priceInput.getText()));
//                state.setString(4, descriptionInput.getText());
//                state.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
//                state.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
//
//                state.execute();
//                refreshTable();
//                clearForm();
//
//            } catch (SQLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
        }
    }

    private class AddRandomAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "insert into extra(name, brand, price, description, createdon, lastmodifiedon) values(?,?,?,?,?,?)";

            try {
                state = conn.prepareStatement(sql);
                state.setString(1, "Extra{" + generateRandomString(6) + "}");
                state.setString(2, "Brand# " + generateRandomString(4));
                state.setDouble(3, generateRandomDouble(10, 1000));
                state.setString(4, "Makes it easier to " + generateRandomString(30));
                state.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                state.setDate(6, new java.sql.Date(new java.util.Date().getTime()));

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
//            conn = DBConnection.getConnection();
//            String sql = "update extra set name = ?, brand = ?, price = ?, description = ?, lastmodifiedon = ? where id = ?";
//
//            try {
//                state = conn.prepareStatement(sql);
//                state.setString(1, nameInput.getText());
//                state.setString(2, brandInput.getText());
//                state.setDouble(3, Double.parseDouble(priceInput.getText()));
//                state.setString(4, descriptionInput.getText());
//                state.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
//                state.setInt(6, selectedId);
//
//                state.execute();
//                refreshTable();
//                selectedId = -1;
//                clearForm();
//
//            } catch (SQLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
        }
    }

    private class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn=DBConnection.getConnection();
            String sql="delete from extra where id=?";

            try {
                state=conn.prepareStatement(sql);
                state.setInt(1, selectedCarId);
                state.execute();
                refreshTable();
                clearForm();
                selectedCarId=-1;
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
            String sql="select id, name, brand, price, description, createdon, lastmodifiedon from extra where name like ?";

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
//            if(searchByBrandInput.getText() == ""){
//                return;
//            }
//
//            conn=DBConnection.getConnection();
//            String sql="select id, name, brand, price, description, createdon, lastmodifiedon from extra where brand like ?";
//
//            try {
//                state=conn.prepareStatement(sql);
//                state.setString(1, searchByBrandInput.getText() + "%");
//                result=state.executeQuery();
//                table.setModel(new MyModel(result));
//                searchByBrandInput.setText("");
//                if (!isAuthorized){
//                    disableFields();
//                }
//            } catch (SQLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
        }
    }

    private class OrderByCreatedAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn=DBConnection.getConnection();
            String sql="select id, name, brand, price, description, createdon, lastmodifiedon from extra order by createdon desc";

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
            String sql="select id, name, brand, price, description, createdon, lastmodifiedon from extra order by lastmodifiedon desc";

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

    private class SelectionManager implements ActionListener {
        JComboBox combo = null;
        ArrayList<Object> selectedItems = new ArrayList();
        ArrayList<Object> nonSelectables = new ArrayList();

        public void actionPerformed(ActionEvent e) {
            if(combo == null) {
                combo = (JComboBox)e.getSource();
            }
            Object item = combo.getSelectedItem();
            if(selectedItems.contains(item)) {
                selectedItems.remove(item);
            } else if(!nonSelectables.contains(item)) {
                selectedItems.add(item);
            }
        }

        public void setNonSelectables(Object... args) {
            for(int j = 0; j < args.length; j++) {
                nonSelectables.add(args[j]);
            }
        }

        public boolean isSelected(Object item) {
            return selectedItems.contains(item);
        }
    }

    class MultiRenderer extends BasicComboBoxRenderer {
        SelectionManager selectionManager;

        public MultiRenderer(SelectionManager sm) {
            selectionManager = sm;
        }

        public Component getListCellRendererComponent(JList list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            if (selectionManager.isSelected(value)) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setFont(list.getFont());

            if (value instanceof Icon) {
                setIcon((Icon)value);
            } else {
                setText((value == null) ? "" : value.toString());
            }
            return this;
        }
    }
}

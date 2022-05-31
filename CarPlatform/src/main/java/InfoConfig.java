package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class InfoConfig extends BaseConfig {
    private JTabbedPane mainTab;
    private JPanel mainPanel = new JPanel();
    private JPanel formPanel = new JPanel();
    private JPanel infoPanel = new JPanel();

    private boolean isAuthorized = false;
    private JPasswordField passwordInput = new JPasswordField();

    private JButton authorizeButton = new JButton("Authorize");
    private JButton logoutButton = new JButton("Logout...");

    private JLabel dbImage = new JLabel();

    public InfoConfig(JTabbedPane mainTab) {
        this.mainTab = mainTab;
    }

    public void configureScreen() {
        mainTab.add("Info", mainPanel);

        mainPanel.add(formPanel);
        this.configureFormPanel();
        mainPanel.add(infoPanel);
        this.configureInfoPanel();

        this.configureButtons();
        this.disableFields();
    }

    private void configureFormPanel() {
        formPanel.setMaximumSize( new Dimension(
                Integer.MAX_VALUE,
                200
        ));

        formPanel.setLayout(new GridLayout(2, 2));

        formPanel.add(authorizeButton);
        formPanel.add(passwordInput);

        formPanel.add(logoutButton);
    }

    private void configureInfoPanel() {
        infoPanel.setLayout(new GridLayout(1, 1));
        try {
            BufferedImage wPic = ImageIO.read(this.getClass().getResource("../resources/ErDiagram.png"));
            ImageIcon image = new ImageIcon(wPic);
            image.setImage(image.getImage().getScaledInstance(1300, 800, Image.SCALE_DEFAULT));
            dbImage = new JLabel(image);
            infoPanel.add(dbImage);
        }
        catch (Exception exception){

        }

    }

    private void configureButtons() {
        authorizeButton.addActionListener(new AuthorizeAction());
        logoutButton.addActionListener(new LogoutAction());
    }

    private boolean checkPassword() {
        char[] correctPass = new char[]{'i', 'n', 'f', 'o', '1', '2', '3'};
        char[] password = (passwordInput.getPassword());
        return super.checkPassword(correctPass, password);
    }

    private void disableFields() {
        isAuthorized = false;
        logoutButton.setEnabled(false);
        authorizeButton.setEnabled(true);
        passwordInput.setEnabled(true);
        infoPanel.setVisible(false);
    }

    private void enableFields() {
        isAuthorized = true;
        logoutButton.setEnabled(true);
        authorizeButton.setEnabled(false);
        passwordInput.setEnabled(false);
        infoPanel.setVisible(true);
    }

    private class AuthorizeAction implements ActionListener {

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

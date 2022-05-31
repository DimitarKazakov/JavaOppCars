package main.java;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import main.java.CarMakeConfig;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.*;

//TODO write pass to see admin properties -> ID, CreatedOn, LastModifiedOn, Deleted
// order by dates, names, numbers
// search by multiple
// full info with password
// generate random row and add it
// change colors
// make table better with fewer columns
// check slujebni poleta
public class MyFrame extends JFrame {
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;

	JTabbedPane mainTab = new JTabbedPane(JTabbedPane.TOP);

	CarMakeConfig carMakeConfig = new CarMakeConfig(mainTab);
	TuningConfig tuningConfig = new TuningConfig(mainTab);
	ExtraConfig extraConfig = new ExtraConfig(mainTab);
	InfoConfig infoConfig = new InfoConfig(mainTab);
	CarConfig carConfig = new CarConfig(mainTab);

	public MyFrame() {
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.getContentPane().setLayout(new GridLayout(1, 1));

		this.add(mainTab);
		this.carMakeConfig.configureScreen();
		this.tuningConfig.configureScreen();
		this.extraConfig.configureScreen();
		this.infoConfig.configureScreen();
		this.carConfig.configureScreen();
	}
}

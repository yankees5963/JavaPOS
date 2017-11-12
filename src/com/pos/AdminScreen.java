package com.pos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.xml.bind.DatatypeConverter;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class AdminScreen
{
	
	JFrame AdminScreen = new JFrame();
	private JTextField ID_Field;
	private JPasswordField password1;
	private JTextField FirstName;
	private JTextField LastName;
	private JPasswordField Password2;
	private JTextPane textPane;
	private JTextField Barcode;
	private JTextField ProdName;
	private JTextField Cost;
	private JTextField textField_1;

	public AdminScreen()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					initialize();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}});
		
	}
		
	private void initialize() {	
		AdminScreen.getContentPane().setBackground(Color.WHITE);
		AdminScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		AdminScreen.getContentPane().add(tabbedPane);
		
		JPanel adPanel = new JPanel();
		tabbedPane.addTab("Add User", null, adPanel, null);
		adPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login ID:");
		lblNewLabel.setBounds(10, 38, 74, 14);
		adPanel.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 64, 74, 14);
		adPanel.add(lblPassword);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(10, 125, 74, 14);
		adPanel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(10, 150, 74, 14);
		adPanel.add(lblLastName);
		
		ID_Field = new JTextField();
		ID_Field.setBounds(117, 35, 104, 20);
		adPanel.add(ID_Field);
		ID_Field.setColumns(10);
		
		password1 = new JPasswordField();
		password1.setBounds(117, 61, 104, 20);
		adPanel.add(password1);
		
		FirstName = new JTextField();
		FirstName.setBounds(117, 122, 104, 20);
		adPanel.add(FirstName);
		FirstName.setColumns(10);
		
		LastName = new JTextField();
		LastName.setBounds(117, 147, 104, 20);
		adPanel.add(LastName);
		LastName.setColumns(10);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CheckData();
			}
		});
		btnAddUser.setBounds(132, 177, 89, 23);
		adPanel.add(btnAddUser);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(10, 211, 211, 92);
		adPanel.add(textPane);
		
		JLabel lblReenterPassword = new JLabel("Re-Enter Password:");
		lblReenterPassword.setBounds(10, 95, 97, 14);
		adPanel.add(lblReenterPassword);
		
		Password2 = new JPasswordField();
		Password2.setBounds(117, 92, 104, 20);
		adPanel.add(Password2);
		
		JLabel lblRoomForAdding = new JLabel("Room for adding Permission options");
		lblRoomForAdding.setBounds(540, 111, 223, 89);
		adPanel.add(lblRoomForAdding);
		
		JPanel Inventory = new JPanel();
		tabbedPane.addTab("AddItems", null, Inventory, null);
		Inventory.setLayout(null);
		
		JLabel lblBarcode = new JLabel("BarCode:");
		lblBarcode.setBounds(10, 11, 84, 14);
		Inventory.add(lblBarcode);
		
		Barcode = new JTextField();
		Barcode.setBounds(104, 8, 162, 20);
		Inventory.add(Barcode);
		Barcode.setColumns(10);
		
		JLabel lblProductName = new JLabel("Product Name:");
		lblProductName.setBounds(10, 86, 84, 14);
		Inventory.add(lblProductName);
		
		ProdName = new JTextField();
		ProdName.setBounds(104, 83, 162, 20);
		Inventory.add(ProdName);
		ProdName.setColumns(10);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(160, 460, 89, 23);
		Inventory.add(btnAddItem);
		
		JLabel lblDepartment = new JLabel("Department:");
		lblDepartment.setBounds(10, 36, 84, 14);
		Inventory.add(lblDepartment);
		
		JComboBox Dept = new JComboBox();
		Dept.setModel(new DefaultComboBoxModel(new String[] {"Food", "Drink"}));
		Dept.setToolTipText("");
		Dept.setBounds(104, 33, 162, 20);
		Inventory.add(Dept);
		
		JLabel lblSubDepartment = new JLabel("Sub Department:");
		lblSubDepartment.setBounds(10, 61, 84, 14);
		Inventory.add(lblSubDepartment);
		
		JComboBox SubDpt = new JComboBox();
		SubDpt.setModel(new DefaultComboBoxModel(new String[] {"Pepsi", "Coke"}));
		SubDpt.setBounds(104, 58, 162, 20);
		Inventory.add(SubDpt);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(10, 111, 46, 14);
		Inventory.add(lblCost);
		
		Cost = new JTextField();
		Cost.setBounds(104, 108, 162, 20);
		Inventory.add(Cost);
		Cost.setColumns(10);
		
		JLabel lblTaxable = new JLabel("Taxable:");
		lblTaxable.setBounds(10, 139, 84, 14);
		Inventory.add(lblTaxable);
		
		JCheckBox chckbxNoTax = new JCheckBox("No");
		chckbxNoTax.setSelected(true);
		chckbxNoTax.setBounds(104, 135, 46, 23);
		Inventory.add(chckbxNoTax);
		
		JCheckBox chckbxTax = new JCheckBox("Tax 1");
		chckbxTax.setBounds(152, 135, 53, 23);
		Inventory.add(chckbxTax);
		
		JCheckBox chckbxTax2 = new JCheckBox("Tax 2");
		chckbxTax2.setBounds(207, 135, 59, 23);
		Inventory.add(chckbxTax2);
		
		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 164, 46, 14);
		Inventory.add(lblStock);
		
		textField_1 = new JTextField();
		textField_1.setBounds(104, 161, 162, 20);
		Inventory.add(textField_1);
		textField_1.setColumns(10);
		AdminScreen.setTitle("CMSC 451 POS  -- Admin Screen");
		AdminScreen.setBounds(100, 100, 915, 663);
		AdminScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		AdminScreen.setVisible(true);
		
	}
	protected void CheckData() 
	{
		String p1 = password1.getText().toString();
		String p2 = Password2.getText().toString();
		if(p1.equals(p2))
		{
			//TODO: Add more filters for id/first&LastName sanity check
			Connection con = new SQLConnection().openSQL();
			try {
				
				String passhash = DatatypeConverter.printHexBinary(MessageDigest.getInstance("SHA-256").digest(password1.getText().toString().getBytes("UTF-8")));
				String fname = FirstName.getText().toString();
				String lname = LastName.getText().toString();
				String id = ID_Field.getText().toString();
				if(id.trim().isEmpty())
				{
					textPane.setText("Login ID Can not Be Blank!");
				}
				else if(p1.trim().isEmpty())
				{
					textPane.setText("Password Can not Be Blank!");
				}
				else if(fname.trim().isEmpty())
				{
					textPane.setText("FirstName Name Can not Be Blank!");
				}
				else if(lname.trim().isEmpty())
				{
					textPane.setText("LastName Name Can not Be Blank!");
				}
				else
				{
					String insert = "INSERT INTO USERS ([LoginID],[Password],[FirstName],[LastName]) VALUES (?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(insert);
					ps.setString(1, id);
					ps.setString(2, passhash);
					ps.setString(3,fname);
					ps.setString(4,lname);
					ps.execute();
					con.close();
					textPane.setText(fname + " " + lname + " Has been added successfully!");
				}
			}
			catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			textPane.setText("Passwords do not match!");
		}
		
	}
}

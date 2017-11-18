package posScreens;

import sql.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Font;

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
	protected JComboBox SubDpt;
	private JTextField textField;
	private JTextField textField_2;

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
				AddUser();
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
		tabbedPane.addTab("Add Items", null, Inventory, null);
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
		btnAddItem.setBounds(177, 192, 89, 23);
		Inventory.add(btnAddItem);
		
		JLabel lblDepartment = new JLabel("Department:");
		lblDepartment.setBounds(10, 36, 84, 14);
		Inventory.add(lblDepartment);
		
		
		ArrayList<String> depts = getDept();
		JComboBox Dept = new JComboBox(depts.toArray());
		Dept.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<String> subdept = getSubDept(Dept.getSelectedItem().toString());
				SubDpt.removeAllItems();
				for(String items : subdept)
				{
					SubDpt.addItem(items);
				}
				
			}
		});
		Dept.setToolTipText("");
		Dept.setBounds(104, 33, 162, 20);
		Inventory.add(Dept);
		
		JLabel lblSubDepartment = new JLabel("Sub Department:");
		lblSubDepartment.setBounds(10, 61, 84, 14);
		Inventory.add(lblSubDepartment);
		
		ArrayList<String> subdept = getSubDept(Dept.getSelectedItem().toString());
		SubDpt= new JComboBox(subdept.toArray());
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
		
		ButtonGroup taxgroup = new ButtonGroup();
		
		JCheckBox chckbxNoTax = new JCheckBox("No", true);
		taxgroup.add(chckbxNoTax);
		chckbxNoTax.setBounds(104, 135, 39, 23);
		Inventory.add(chckbxNoTax);
		
		JCheckBox chckbxTax = new JCheckBox("Tax 1", false);
		chckbxTax.setBounds(145, 135, 62, 23);
		Inventory.add(chckbxTax);
		
		JCheckBox chckbxTax2 = new JCheckBox("Tax 2", false);
		chckbxTax2.setBounds(204, 135, 62, 23);
		Inventory.add(chckbxTax2);
		
		taxgroup.add(chckbxNoTax);
		taxgroup.add(chckbxTax);
		taxgroup.add(chckbxTax2);
		
		JLabel lblStock = new JLabel("Stock On Hand:");
		lblStock.setBounds(10, 164, 84, 14);
		Inventory.add(lblStock);
		
		textField_1 = new JTextField();
		textField_1.setBounds(104, 161, 162, 20);
		Inventory.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblAddDepartments = new JLabel("Add Department");
		lblAddDepartments.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddDepartments.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddDepartments.setBounds(412, 11, 243, 14);
		Inventory.add(lblAddDepartments);
		
		JLabel lblDepartmentName = new JLabel("Department Name:");
		lblDepartmentName.setBounds(412, 36, 97, 14);
		Inventory.add(lblDepartmentName);
		
		textField = new JTextField();
		textField.setBounds(507, 33, 148, 20);
		Inventory.add(textField);
		textField.setColumns(10);
		
		JButton btnAddDepartment = new JButton("Add Department");
		btnAddDepartment.setBounds(538, 57, 117, 23);
		Inventory.add(btnAddDepartment);
		
		JLabel lblAddSubDepartments = new JLabel("Add  Sub-Department");
		lblAddSubDepartments.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddSubDepartments.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddSubDepartments.setBounds(412, 105, 243, 23);
		Inventory.add(lblAddSubDepartments);
		
		JLabel label = new JLabel("Department Name:");
		label.setBounds(412, 164, 97, 14);
		Inventory.add(label);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(507, 161, 148, 20);
		Inventory.add(textField_2);
		
		JButton btnAddSubdepartment = new JButton("Add Sub-Department");
		btnAddSubdepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAddSubdepartment.setBounds(517, 192, 138, 23);
		Inventory.add(btnAddSubdepartment);
		
		JComboBox comboBox = new JComboBox(new Object[]{});
		comboBox.setToolTipText("");
		comboBox.setBounds(507, 136, 148, 20);
		Inventory.add(comboBox);
		
		JLabel label_1 = new JLabel("Department:");
		label_1.setBounds(412, 139, 84, 14);
		Inventory.add(label_1);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBounds(298, 379, 211, 92);
		Inventory.add(textPane_1);
		AdminScreen.setTitle("CMSC 451 POS  -- Admin Screen");
		AdminScreen.setBounds(100, 100, 915, 663);
		AdminScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		AdminScreen.setVisible(true);
		
	}
	private ArrayList<String> getSubDept(String Dept) 
	{
		ArrayList<String> depts = new ArrayList<String>();
		Connection con = new SQLConnection().openSQL();
		try 
		{
			String sq= "SELECT SubName from Departments WHERE [isSub] = ? AND [DeptName] = ?";
			PreparedStatement ps = con.prepareStatement(sq);
			ps.setInt(1, 1);
			ps.setString(2, Dept);
			ResultSet r = ps.executeQuery();
			while(r.next())
			{
				depts.add(r.getString("SubName"));
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return depts;
	}

	private ArrayList<String> getDept() 
	{
		ArrayList<String> depts = new ArrayList<String>();
		Connection con = new SQLConnection().openSQL();
		try 
		{
			String sq= "SELECT DeptName from Departments WHERE [isSub] = 0";
			PreparedStatement ps = con.prepareStatement(sq);
			ResultSet r = ps.executeQuery();
			while(r.next())
			{
				depts.add(r.getString("DeptName"));
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return depts;
	}

	protected void AddUser() 
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

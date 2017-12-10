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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.xml.bind.DatatypeConverter;

import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
	
	JFrame AdminScreen;
	JTabbedPane tabbedPane;
	JPanel Inventory;
	ButtonGroup taxgroup;
	private JTextField ID_Field;
	private JPasswordField password1;
	private JTextField FirstName;
	private JTextField LastName;
	private JPasswordField Password2;
	private JTextPane textPane;
	private JTextField Barcode;
	private JTextField ProdName;
	private JTextField Cost;
	private JTextField StockOnHand;
	private JTextField deptInput;
	private JTextPane OutputBox;
	private JComboBox Dept;
	/**
	 * @wbp.parser.constructor
	 */
	public AdminScreen()
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					AdminScreen = new JFrame();
					initialize();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}});
		
	}
	public AdminScreen(JFrame inputFrame)
	{
		AdminScreen = inputFrame;
	}
	protected JFrame initialize() 
	{	
		createWindow();
		addUserPanel();
		addInvPanel();
		
		AdminScreen.setVisible(true);
		return AdminScreen;
	}
	
	protected void addUserPanel() 
	{
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
		ID_Field.setBounds(152, 35, 104, 20);
		adPanel.add(ID_Field);
		ID_Field.setColumns(10);
		
		password1 = new JPasswordField();
		password1.setBounds(152, 61, 104, 20);
		adPanel.add(password1);
		
		FirstName = new JTextField();
		FirstName.setBounds(152, 122, 104, 20);
		adPanel.add(FirstName);
		FirstName.setColumns(10);
		
		LastName = new JTextField();
		LastName.setBounds(152, 147, 104, 20);
		adPanel.add(LastName);
		LastName.setColumns(10);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				AddUser();
			}
		});
		btnAddUser.setBounds(167, 178, 89, 23);
		adPanel.add(btnAddUser);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(10, 235, 211, 92);
		adPanel.add(textPane);
		
		JLabel lblReenterPassword = new JLabel("Re-Enter Password:");
		lblReenterPassword.setBounds(10, 95, 115, 14);
		adPanel.add(lblReenterPassword);
		
		Password2 = new JPasswordField();
		Password2.setBounds(152, 92, 104, 20);
		adPanel.add(Password2);
		
	}
	protected void createWindow()
	{
		AdminScreen.getContentPane().setBackground(Color.WHITE);
		AdminScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		AdminScreen.getContentPane().add(tabbedPane);
	}
	protected void addInvPanel()
	{
		Inventory = new JPanel();
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
		lblProductName.setBounds(10, 61, 84, 14);
		Inventory.add(lblProductName);
		
		ProdName = new JTextField();
		ProdName.setBounds(104, 58, 162, 20);
		Inventory.add(ProdName);
		ProdName.setColumns(10);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddItem();
			}
		});
		btnAddItem.setBounds(177, 164, 89, 23);
		Inventory.add(btnAddItem);
		
		JLabel lblDepartment = new JLabel("Department:");
		lblDepartment.setBounds(10, 36, 84, 14);
		Inventory.add(lblDepartment);
		
		
		ArrayList<String> depts = getDept();
		Dept = new JComboBox(depts.toArray());
		Dept.setToolTipText("");
		Dept.setBounds(104, 33, 162, 20);
		Inventory.add(Dept);
		
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(10, 86, 46, 14);
		Inventory.add(lblCost);
		
		Cost = new JTextField();
		Cost.setBounds(104, 83, 162, 20);
		Inventory.add(Cost);
		Cost.setColumns(10);
		
		JLabel lblTaxable = new JLabel("Taxable:");
		lblTaxable.setBounds(10, 111, 84, 14);
		Inventory.add(lblTaxable);
		
		taxgroup = new ButtonGroup();
		
		JRadioButton chckbxNoTax = new JRadioButton("Non-Tax", true);
		chckbxNoTax.setActionCommand("Non-Taxable");
		taxgroup.add(chckbxNoTax);
		chckbxNoTax.setBounds(104, 107, 93, 23);
		Inventory.add(chckbxNoTax);
		
		JRadioButton chckbxTax = new JRadioButton("Taxable", false);
		chckbxTax.setActionCommand("Taxable");
		chckbxTax.setBounds(199, 107, 71, 23);
		Inventory.add(chckbxTax);
		
		taxgroup.add(chckbxNoTax);
		taxgroup.add(chckbxTax);
		
		JLabel lblStock = new JLabel("Stock On Hand:");
		lblStock.setBounds(10, 136, 100, 14);
		Inventory.add(lblStock);
		
		StockOnHand = new JTextField();
		StockOnHand.setBounds(127, 133, 139, 20);
		Inventory.add(StockOnHand);
		StockOnHand.setColumns(10);
		
		JLabel lblAddDepartments = new JLabel("Add Department");
		lblAddDepartments.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddDepartments.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddDepartments.setBounds(412, 11, 243, 14);
		Inventory.add(lblAddDepartments);
		
		JLabel lblDepartmentName = new JLabel("Department Name:");
		lblDepartmentName.setBounds(412, 36, 113, 14);
		Inventory.add(lblDepartmentName);
		
		deptInput = new JTextField();
		deptInput.setBounds(535, 33, 148, 20);
		Inventory.add(deptInput);
		deptInput.setColumns(10);
		
		JButton btnAddDepartment = new JButton("Add Department");
		btnAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddDept();
			}
		});
		btnAddDepartment.setBounds(535, 57, 148, 23);
		Inventory.add(btnAddDepartment);
		
		OutputBox = new JTextPane();
		OutputBox.setEditable(false);
		OutputBox.setBounds(298, 379, 211, 92);
		Inventory.add(OutputBox);
	}
	
	private ArrayList<String> getDept() 
	{
		ArrayList<String> depts = new ArrayList<String>();
		Connection con = new SQLConnection().openSQL();
		try 
		{
			String sq= "SELECT DeptName from Departments";
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
					OutputBox.setText("Login ID Can not Be Blank!");
				}
				else if(p1.trim().isEmpty())
				{
					OutputBox.setText("Password Can not Be Blank!");
				}
				else if(fname.trim().isEmpty())
				{
					OutputBox.setText("FirstName Name Can not Be Blank!");
				}
				else if(lname.trim().isEmpty())
				{
					OutputBox.setText("LastName Name Can not Be Blank!");
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
					OutputBox.setText(fname + " " + lname + " Has been added successfully!");
				}
			}
			catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			OutputBox.setText("Passwords do not match!");
		}
		
	}protected void AddItem() 
	{
		//TODO: Add more filters for id/first&LastName sanity check
		Connection con = new SQLConnection().openSQL();
		try {
			
			if(Barcode.getText().toString().trim().isEmpty())
			{
				OutputBox.setText("Barcode cannot be blank!");
			}
			else if(ProdName.getText().toString().trim().isEmpty())
			{
				OutputBox.setText("ProductName cannot be blank!");
			}
			else if(Cost.getText().toString().trim().isEmpty())
			{
				OutputBox.setText("Cost cannot be blank!");
			}
			else if(StockOnHand.getText().toString().trim().isEmpty())
			{
				OutputBox.setText("Stock On Hand cannot be blank!");
			}
			else
			{
				String insert = "INSERT INTO PRODUCT ([BarCode],[DepartmentID],[ProductName],[Cost],[Taxable],[StockOnHand]) VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(insert);
				ps.setString(1,Barcode.getText().toString());
				ps.setInt(2,getDeptID(Dept.getSelectedItem().toString()));
				ps.setString(3, ProdName.getText().toString());
				ps.setBigDecimal(4, new BigDecimal(Cost.getText()));
				ps.setBoolean(5, getTaxable());
				ps.setInt(6, Integer.parseInt(StockOnHand.getText()));
				ps.execute();
				con.close();
				OutputBox.setText(ProdName.getText().toString() + " has been added to Products");
			}
		}
		catch (SQLException e) 
		{
			if (e.getSQLState().startsWith("23"))
			{
				OutputBox.setText(Barcode.getText().toString() + " already exists, please use another barcode.");
			}
		}
	
	}
	private boolean getTaxable() 
	{
		if(taxgroup.getSelection().getActionCommand().toString() == "Taxable")
		{
			return true;
		}
		return false;
	}
	private int getDeptID(String DeptName) 
	{
		Connection con = new SQLConnection().openSQL();
		try 
		{
			String sq= "SELECT DeptID from Departments where DeptName = ?";
			PreparedStatement ps = con.prepareStatement(sq);
			ps.setString(1,DeptName);
			ResultSet r = ps.executeQuery();
			if(r.next())
			{
				return r.getInt("DeptID");
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 1;
	}
	protected void AddDept() 
	{
		String inDept = deptInput.getText().toString();
			//TODO: Add more filters for id/first&LastName sanity check
			Connection con = new SQLConnection().openSQL();
			try {
				
				if(inDept.trim().isEmpty())
				{
					OutputBox.setText("Deptmartment cannot be blank!");
				}
				else if(deptExists(inDept))
				{
					OutputBox.setText("Department already exists");
				}
				else
				{
					String insert = "INSERT INTO Departments ([DeptName]) VALUES (?)";
					PreparedStatement ps = con.prepareStatement(insert);
					ps.setString(1, inDept);
					ps.execute();
					con.close();
					OutputBox.setText(inDept + " has been added to Deptartments");
					refreshDDL(inDept);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		
	}
	private void refreshDDL(String inDept) 
	{
		Dept.addItem(inDept);
		Dept.repaint();
		
	}
	protected boolean deptExists(String inDept) 
	{
		Connection con = new SQLConnection().openSQL();
		try 
		{
			String sq= "SELECT * from Departments where DeptName = ?";
			PreparedStatement ps = con.prepareStatement(sq);
			ps.setString(1,inDept);
			ResultSet r = ps.executeQuery();
			if(r.next())
			{
				return true;
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}

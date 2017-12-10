package posScreens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import sql.SQLConnection;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

/*
 *	Group Charlie 
 * 	CMSC 495
 * 	University of Maryland, University College
 * 
 * 	Description: This Class is the main screen that users will be utilizing
 * 					Has Transaction log/ cashout buttons/ ability to open new windows
 */
public class MainPOSScreen 
{
	private JFrame POSframe = new JFrame();
	private int UserID;
	private String Fname,Lname;
	private JTextField textField;
	private JTextField TotalField;
	private JTable table;
	
	public MainPOSScreen(int id)
	{
		UserID = id;
		Connection con = new SQLConnection().openSQL();
		String stmt = "Select FirstName,LastName from USERS WHERE UserID = " + UserID;
		try {
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery(stmt);
			r.next();
			Fname = r.getString("FirstName");
			Lname = r.getString("LastName");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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

	
	protected void initialize() 
	{
		createWindow();
		addPanel();
		
		
		
		
		POSframe.setVisible(true);
		//return POSframe;
	
	}
	protected void createWindow()
	{
		POSframe.getContentPane().setBackground(Color.WHITE);
		POSframe.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
		POSframe.setTitle("CMSC 495 POS  -- LOGGED IN AS: " + Fname + " "+ Lname);
		POSframe.setBounds(100, 100, 915, 663);
		POSframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		POSframe.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
	}
	protected void addPanel()
	{
		JPanel panel = new JPanel();
		POSframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		
		JLabel lblEnterBarcode = new JLabel("Enter Barcode:");
		lblEnterBarcode.setBounds(10, 64, 93, 14);
		panel.add(lblEnterBarcode);
		
		textField = new JTextField();
		textField.setBounds(98, 61, 302, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				addItemToCart();
			}
		});
		
		btnAddItem.setBounds(435, 60, 89, 23);
		panel.add(btnAddItem);
		
		JButton btnLookUp = new JButton("Item Lookup");
		btnLookUp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				ItemLookUp items=new ItemLookUp();			
			}
		});
		btnLookUp.setBounds(525, 60, 89, 23);
		panel.add(btnLookUp);

		
		JButton btnNewButton = new JButton("Pay");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(panel, "Purchase has been completed! Thank you!");
				//TODO Clear Order & Write to Transaction table
			}
		});
		btnNewButton.setBounds(602, 175, 287, 83);
		panel.add(btnNewButton);
		
		TotalField = new JTextField();
		TotalField.setFont(new Font("Tahoma", Font.PLAIN, 60));
		TotalField.setText("$0.00");
		TotalField.setBounds(602, 88, 287, 76);
		panel.add(TotalField);
		TotalField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(10, 89, 582, 503);
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Barcode", "Item Name", "Quantity", "Price"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setMinWidth(14);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);
		
		panel.add(scrollPane);
		
		addMenuBar();
	}
	protected void addMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		POSframe.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		 mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					System.exit(0);
					
				}
	        });
		
		JMenuItem mntmLogOut = new JMenuItem("LogOut");
		
		 mntmLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent f) 
				{
					logout();
				}
	        });
		
		JMenuItem mntmAdmin = new JMenuItem("Administrative Tasks");
		mntmAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent g) 
			{
				POSframe.getContentPane().removeAll();
				POSframe = new AdminScreen(POSframe).initialize();
				POSframe.revalidate();
				POSframe.repaint();
			}
        });

		mnFile.add(mntmAdmin);
		mnFile.add(mntmLogOut);
		mnFile.add(mntmExit);
	}

	private void addItemToCart()
	{
		Connection con = new SQLConnection().openSQL();
		String stmt = "dbo.getItem '"+textField.getText()+"'";
		try {
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(stmt);
			
			if(r.next())
			{			
				Vector rows=new Vector();			
				rows.addElement(r.getString(1));
				rows.addElement(r.getString(2));
				rows.addElement(1);
				rows.addElement(r.getFloat(3));
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(rows);			
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	protected void logout() 
	{
		this.POSframe.dispose();
		new LoginScreen();
	}
}

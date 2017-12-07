package posScreens;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import sql.SQLConnection;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import java.awt.Font;

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
	private JTable table;
	private JTextField textField;
	private JTextField TotalField;
	
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
		
		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Barcode", "Item Name", "Quantity", "Price"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(160);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(320);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.setBounds(10, 107, 513, 469);
		panel.add(table);
		
		JLabel lblEnterBarcode = new JLabel("Enter Barcode:");
		lblEnterBarcode.setBounds(10, 64, 93, 14);
		panel.add(lblEnterBarcode);
		
		textField = new JTextField();
		textField.setBounds(98, 61, 302, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(435, 60, 89, 23);
		panel.add(btnAddItem);
		
		JButton btnNewButton = new JButton("Pay");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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


	protected void logout() 
	{
		this.POSframe.dispose();
		new LoginScreen();
	}
}

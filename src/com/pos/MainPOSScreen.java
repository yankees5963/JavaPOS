package com.pos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import sql.SQLConnection;

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

	
	private void initialize() 
	{
		POSframe.getContentPane().setBackground(Color.WHITE);
		POSframe.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
		POSframe.setTitle("CMSC 495 POS  -- LOGGED IN AS: " + Fname + " "+ Lname);
		POSframe.setBounds(100, 100, 915, 663);
		POSframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		POSframe.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		POSframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblDemo = new JLabel("This is where POS program goes");
		lblDemo.setBounds(291, 219, 264, 107);
		panel.add(lblDemo);
		
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
				addAdminScreen();
			}

			private void addAdminScreen() 
			{
				new AdminScreen();
				//TODO fix bug where closing window closes out of program completely.
				//Bug will cause both windows to close when closing admin screen, but leaves admin screen open when closing Main Screen.
				
			}
        });
		mnFile.add(mntmAdmin);
		mnFile.add(mntmLogOut);
		mnFile.add(mntmExit);
		
		
		
		POSframe.setVisible(true);
	
	}



	protected void logout() 
	{
		this.POSframe.dispose();
		new LoginScreen();
	}
}

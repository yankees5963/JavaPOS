package posScreens;

/*
 *	Group Charlie 
 * 	CMSC 495
 * 	University of Maryland, University College
 * 
 * 	Description: This Class starts the program with a login screen, to then transfer to the POS program when login is successful
 */

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.xml.bind.DatatypeConverter;

import sql.SQLConnection;

import javax.swing.JPasswordField;
import java.awt.Toolkit;

public class LoginScreen {

	private JFrame frmCmscPos;
	private JTextField UserIDField;
	private JPasswordField passwordField;
	private JLabel lblErrorbox;
	

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					new LoginScreen();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}});
		
	}
	
	public LoginScreen() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCmscPos = new JFrame();
		frmCmscPos.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
		frmCmscPos.getContentPane().setBackground(Color.WHITE);
		frmCmscPos.setTitle("CMSC 495 POS");
		frmCmscPos.setBounds(100, 100, 683, 498);
		frmCmscPos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCmscPos.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		JPanel panel = new JPanel();
		frmCmscPos.getContentPane().add(panel);
		panel.setLayout(null);
		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setBounds(237, 208, 52, 14);
		panel.add(lblUserId);
		
		UserIDField = new JTextField();
		UserIDField.setBounds(316, 198, 166, 27);
		panel.add(UserIDField);
		UserIDField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(237, 246, 69, 14);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(316, 236, 166, 27);
		panel.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				checkLogin();
				
			}

		});
		btnLogin.setBounds(393, 313, 89, 23);
		panel.add(btnLogin);
		
		lblErrorbox = new JLabel("");
		lblErrorbox.setEnabled(false);
		lblErrorbox.setBounds(393, 347, 89, 27);
		panel.add(lblErrorbox);
		frmCmscPos.setVisible(true);
	}

private void checkLogin()
{
		Connection con = new SQLConnection().openSQL();
		try {
			String hash = DatatypeConverter.printHexBinary(MessageDigest.getInstance("SHA-256").digest(passwordField.getText().toString().getBytes("UTF-8")));
			String sq= "SELECT UserId from USERS WHERE [LoginID] = ? AND [Password] = ? AND [Active] = ?";
			PreparedStatement ps = con.prepareStatement(sq);
			//Check LoginID value
			ps.setString(1, UserIDField.getText().toString());
			//Password Hash to check in database
			ps.setString(2, hash);
			//Only check for Active Users, Inactive should not be able to login
			ps.setInt(3, 1);
			ResultSet r = ps.executeQuery();
			if(r.next())
			{
				lblErrorbox.setText("");
				lblErrorbox.setEnabled(false);
				int id = r.getInt("UserID");
				con.close();
				Login(id);
			}
			else 
			{
				lblErrorbox.setText("INVALID LOGIN");
				lblErrorbox.setEnabled(true);
				con.close();
			}
			
			
		} catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
}

private void Login(int id) 
{
	this.frmCmscPos.dispose();
	new MainPOSScreen(id);
	
}
}

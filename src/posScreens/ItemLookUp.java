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
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import sql.SQLConnection;

/*
 *	Group Charlie 
 * 	CMSC 495
 * 	University of Maryland, University College
 * 
 * 	Description: Lists all products
 * */

public class ItemLookUp {
	private JFrame ItemLookUpFrame = new JFrame();
	private JTable itemTable;
	private JScrollPane scrollPane;
	
	public ItemLookUp()
	{
		createWindow();
		fillTable();
		ItemLookUpFrame.setVisible(true);
	}
	
	
	protected void createWindow()
	{
		ItemLookUpFrame.getContentPane().setBackground(Color.WHITE);
		ItemLookUpFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
		ItemLookUpFrame.setTitle("Item Look Up");
		ItemLookUpFrame.setBounds(100, 100, 915, 663);
		ItemLookUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ItemLookUpFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		scrollPane=new JScrollPane();
		scrollPane.setViewportView(null);
		scrollPane.setBounds(10,89,582,503);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
	private void fillTable()
	{
		//Gets all products from view vwItemLookup and show it in the
		//table		
		itemTable=new JTable();
		itemTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	
		Connection con = new SQLConnection().openSQL();
		String stmt = "Select * from vwItemLookUp";
		try {
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(stmt);
			ResultSetMetaData metData=r.getMetaData();
			int columns=metData.getColumnCount();
			DefaultTableModel tblModel=new DefaultTableModel();
			Vector colNames=new Vector();
			Vector rows=new Vector();
			for(int i=1;i<=columns;i++)
			{
				colNames.addElement(metData.getColumnName(i));
			}
			tblModel.setColumnIdentifiers(colNames);
			while(r.next())
			{
				rows=new Vector();
				for(int j=1;j<=columns;j++)
				{
					rows.addElement(r.getString(j));
				}
				tblModel.addRow(rows);
			}
			itemTable.setModel(tblModel);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		scrollPane.setViewportView(itemTable);
		ItemLookUpFrame.add(scrollPane);
		
	}
}

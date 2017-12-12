package posScreens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.util.Locale;


import javax.swing.*;


import sql.SQLConnection;

/*
 *	Group Charlie 
 * 	CMSC 495
 * 	University of Maryland, University College
 * 
 * 	Description: Lists all products
 * */


public class Receipt {
	private JFrame ReceiptFrame = new JFrame();	
	private JLabel lblReceipt=new JLabel("Receipt");
	private int transID;
	private JLabel lblDateTime=new JLabel("Date: ");
	private JLabel lblsalesPerson=new JLabel("Sales Person: ");
	private JLabel lblsubTotal=new JLabel("Subtotal: ");
	private JLabel lblTax=new JLabel("Tax: ");
	private JLabel lblTotal=new JLabel("Total: ");
	private JPanel receiptPanel;
	private JTextArea purchasedItems;
	private JButton printBtn;
	
	public Receipt(int transID)
	{
		this.transID=transID;
		purchasedItems=new JTextArea();
		createWindow();
		fillTable();	
		createReceipt();
		ReceiptFrame.setVisible(true);
	}
	
	private void fillTable()
	{
		Connection con = new SQLConnection().openSQL();
		String stmt = "dbo.spReceiptItems "+transID;
		try {
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(stmt);
			while(r.next())
			{			
				purchasedItems.append(r.getString(1)+" - ");
				purchasedItems.append(r.getString(2)+"\n");
				purchasedItems.append(r.getString(4)+" @ ");
				purchasedItems.append("$"+String.format(Locale.US,"%.2f", r.getFloat(3))+"\n\n");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void createReceipt()
	{	
		Connection con = new SQLConnection().openSQL();
		String stmt = "dbo.spReceipt "+transID;
		try {
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(stmt);
			if(r.next())
			{
				lblDateTime.setText(lblDateTime.getText()+r.getString(1));
				lblsalesPerson.setText(lblsalesPerson.getText()+r.getString(5));
				lblsubTotal.setText(lblsubTotal.getText()+r.getString(3));
				lblTax.setText(lblTax.getText()+r.getString(4));
				lblTotal.setText(lblTotal.getText()+r.getString(2));
			}
		}
		catch (SQLException e)
		{
			
		}
	}
	
	protected void createWindow()
	{
		ReceiptFrame.getContentPane().setBackground(Color.WHITE);
		ReceiptFrame.setTitle("Receipt");
		ReceiptFrame.setBounds(100, 100, 400, 700);
		ReceiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ReceiptFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		receiptPanel=new JPanel();
		receiptPanel.setLayout(new BoxLayout(receiptPanel,BoxLayout.PAGE_AXIS));
		lblReceipt.setFont(new Font("Courier New", Font.BOLD, 20));
		receiptPanel.add(lblReceipt);
		receiptPanel.add(lblDateTime);
		receiptPanel.add(lblsalesPerson);
		receiptPanel.add(purchasedItems);
		receiptPanel.add(lblsubTotal);
		receiptPanel.add(lblTax);
		receiptPanel.add(lblTotal);
		
		printBtn=new JButton("Print");
		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				PrinterJob job=PrinterJob.getPrinterJob();

				job.setJobName("Print Receipt");
				job.setPrintable(new Printable() {
					public int print(Graphics pg, PageFormat pf, int numPages)
					{
						if(numPages>0) return Printable.NO_SUCH_PAGE;
						
						Graphics2D g2 = (Graphics2D) pg;
						g2.translate(pf.getImageableX(), pf.getImageableY());
						ReceiptFrame.paint(g2);
						return Printable.PAGE_EXISTS;
					}
				});
				
				if(job.printDialog()==false) return;
				
				try 
				{
					job.print();					
				} 
				catch(PrinterException e)
				{
					
				}				
				
			}		
		});
	receiptPanel.add(printBtn);
	ReceiptFrame.add(receiptPanel);
	}
}

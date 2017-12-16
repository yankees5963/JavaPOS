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
 * 	Description: Creates a report to show all sales between a specific time frame
 * */

public class SalesReport {
	private JFrame SalesFrame = new JFrame();	
	private JLabel lblSales=new JLabel("Sales Report");
	private JLabel lblStartDate=new JLabel("Start Date: ");
	private JLabel lblEndDate=new JLabel("End Date: ");		
	private JTextField startDate=new JTextField();
	private JTextField endDate=new JTextField();
	private JLabel lblsubTotal=new JLabel("Subtotal: ");
	private JLabel lblTax=new JLabel("Tax: ");
	private JLabel lblTotal=new JLabel("Total: ");
	private JPanel searchPanel;
	private JPanel salesPanel;
	private JButton printBtn;
	private JTable salesTbl;
	
	public SalesReport()
	{
		createWindow();
		SalesFrame.setVisible(true);
	}
	
	protected void createWindow()
	{
		SalesFrame.getContentPane().setBackground(Color.WHITE);
		SalesFrame.setTitle("Sales Report");
		SalesFrame.setBounds(100, 100, 600, 700);
		SalesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SalesFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		salesPanel=new JPanel();
		searchPanel=new JPanel();
		salesPanel.setLayout(new BoxLayout(salesPanel,BoxLayout.PAGE_AXIS));
		lblSales.setFont(new Font("Courier New", Font.BOLD, 20));
		salesPanel.add(lblSales);
		searchPanel.add(lblStartDate);
		startDate=new JTextField("",7);
		endDate=new JTextField("",7);
		searchPanel.add(startDate);
		searchPanel.add(lblEndDate);
		searchPanel.add(endDate);
		JButton searchBtn=new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				fillTable();
				getSalesInfo();
			}
		});   
		searchPanel.add(searchBtn);		
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportBorder(null);
        scrollPane.setBounds(10, 89, 582, 503);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);		
		salesTbl = new JTable();
		salesTbl.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Date", "Sales Person", "Total", "Sub Total", "Tax"
                }
        ) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            Class[] columnTypes = new Class[]{
                String.class, String.class, String.class, String.class, String.class
            };

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });
		salesTbl.getColumnModel().getColumn(0).setResizable(false);
		salesTbl.getColumnModel().getColumn(0).setMinWidth(14);
		salesTbl.getColumnModel().getColumn(1).setResizable(false);
		salesTbl.getColumnModel().getColumn(2).setResizable(false);
		salesTbl.getColumnModel().getColumn(3).setResizable(false);
		salesTbl.getColumnModel().getColumn(4).setResizable(false);
		salesTbl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		salesTbl.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(salesTbl);       
		salesPanel.add(searchPanel);
		salesPanel.add(scrollPane);
		salesPanel.add(lblsubTotal);
		salesPanel.add(lblTax);
		salesPanel.add(lblTotal);		
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
						SalesFrame.paint(g2);
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
	salesPanel.add(printBtn);
	SalesFrame.add(salesPanel);
	}
	
	private void getSalesInfo()
	{
		//Gets the total, subtotal and tax of all sales during period
		Connection con = new SQLConnection().openSQL();
		String stmt = "dbo.spSaleReportInfo '"+startDate.getText()+"', '"+endDate.getText()+"'";
		
		try {
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(stmt);
			if(r.next())
			{
				lblTotal.setText(lblTotal.getText()+r.getFloat(1));
				lblsubTotal.setText(lblsubTotal.getText()+r.getFloat(2));
				lblTax.setText(lblTax.getText()+r.getFloat(3));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
			
	}
	
	private void fillTable()
	{
		//Gets each transaction for the selected period of time
		Connection con = new SQLConnection().openSQL();
		String stmt = "dbo.spSalesReport '"+startDate.getText()+"', '"+endDate.getText()+"'";
		
		try {
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(stmt);
			Vector rows=new Vector();
			DefaultTableModel model = (DefaultTableModel) salesTbl.getModel();
			while(r.next())
			{			
				rows=new Vector();
				for(int i=1;i<=5;i++)
				{
					rows.addElement(r.getString(i));
				}
				model.addRow(rows);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}	
}

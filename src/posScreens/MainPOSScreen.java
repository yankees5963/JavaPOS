package posScreens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;

import sql.SQLConnection;
import java.awt.Font;
import java.text.NumberFormat;
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
public class MainPOSScreen {

    private JFrame POSframe = new JFrame();
    private int UserID;
    private String Fname, Lname;
    private JTextField textField;
    private JTextField TotalField;
    private JTable table;
    private double totalPurchase = 0.0;
    private float tax=0;

    public MainPOSScreen(int id) {
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

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    initialize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void initialize() {
        createWindow();
        addPanel();
        getTax();

        POSframe.setVisible(true);
        //return POSframe;

    }
    
    protected void createWindow() {
        POSframe.getContentPane().setBackground(Color.WHITE);
        POSframe.setIconImage(Toolkit.getDefaultToolkit().getImage("lib/POS.png"));
        POSframe.setTitle("CMSC 495 POS  -- LOGGED IN AS: " + Fname + " " + Lname);
        POSframe.setBounds(100, 100, 915, 663);
        POSframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        POSframe.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        
        // center window
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - POSframe.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - POSframe.getHeight()) / 2);
        POSframe.setLocation(x, y);        
    }

    protected void addPanel() {
        JPanel panel = new JPanel();
        POSframe.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblEnterBarcode = new JLabel("Enter Barcode:");
        lblEnterBarcode.setBounds(10, 64, 93, 14);
        panel.add(lblEnterBarcode);

        textField = new JTextField();
        textField.setBounds(98, 61, 240, 20);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnAddItem = new JButton("Add Item");
        btnAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addItemToCart();
            }
        });

        btnAddItem.setBounds(348, 60, 89, 23);
        panel.add(btnAddItem);

        JButton btnLookUp = new JButton("Item Lookup");
        btnLookUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ItemLookUp items = new ItemLookUp();
            }
        });
        btnLookUp.setBounds(476, 60, 116, 23);
        panel.add(btnLookUp);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportBorder(null);
        scrollPane.setBounds(10, 89, 582, 503);

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Barcode", "Item Name", "Quantity", "Price", "Taxable"
                }
        ) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            Class[] columnTypes = new Class[]{
                Integer.class, String.class, Integer.class, Double.class, Boolean.class
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

        JButton btnNewButton = new JButton("Pay");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(panel, "Purchase has been completed! Thank you!");
                int transID=createOrder();
                completeOrder(transID);
                Receipt newReceipt = new Receipt(transID);

                //TODO Clear Order & Write to Transaction table
                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                dtm.setRowCount(0);
                // Reset the total to zero
                totalPurchase = 0.0;
                addToTotal(0.0f,false);
            }
        });
        btnNewButton.setBounds(602, 175, 287, 83);
        panel.add(btnNewButton);

        TotalField = new JTextField();
        TotalField.setFont(new Font("Tahoma", Font.PLAIN, 60));
        TotalField.setText("$0.00");
        TotalField.setEditable(false);
        TotalField.setBounds(602, 88, 287, 76);
        panel.add(TotalField);
        TotalField.setColumns(10);

        panel.add(scrollPane);

        addMenuBar();
    }

    protected void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        POSframe.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        JMenuItem mntmLogOut = new JMenuItem("LogOut");

        mntmLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent f) {
                logout();
            }
        });
        

        JMenuItem mntmAdmin = new JMenuItem("Administrative Tasks");
        mntmAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent g) {
                POSframe.getContentPane().removeAll();
                POSframe = new AdminScreen(POSframe).initialize();
                POSframe.revalidate();
                POSframe.repaint();
            }
        });
        
        JMenuItem salesReport = new JMenuItem("Sales Report");
        salesReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent g) {
            	SalesReport sR=new SalesReport();
            }
        });

        mnFile.add(mntmAdmin);
        mnFile.add(salesReport);
        mnFile.add(mntmLogOut);
        mnFile.add(mntmExit);
    }

    private void addItemToCart() {
        Connection con = new SQLConnection().openSQL();
        String stmt = "dbo.getItem '" + textField.getText() + "'";
        try {
            Statement s = con.createStatement();
            ResultSet r = s.executeQuery(stmt);

            if (r.next()) {
                Vector rows = new Vector();
                rows.addElement(r.getString(1));
                rows.addElement(r.getString(2));
                rows.addElement(1);
                rows.addElement(r.getFloat(3));
                rows.addElement(r.getBoolean(4));
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(rows);
                addToTotal(r.getFloat(3), r.getBoolean(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void getTax()
    {
    	Connection con=new SQLConnection().openSQL();
    	String stmt="dbo.spGetTax";
		try {
			Statement s=con.createStatement();
			ResultSet r = s.executeQuery(stmt);
			if(r.next())
			{
				this.tax=r.getFloat(1);
			}
		}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private void completeOrder(int transID) {
    	Connection con=new SQLConnection().openSQL();
    	DefaultTableModel tableModel=new DefaultTableModel();
    	String barCode="";
    	float salesPrice=0;
    	int qty=0;
    	int numRows=table.getModel().getRowCount();
    	for(int i=0;i<numRows;i++)
    	{
    		String stmt="dbo.spAddItemTrans "+transID+", '"+table.getModel().getValueAt(i, 0)+"', "+table.getModel().getValueAt(i, 3)+", "+table.getModel().getValueAt(i, 2)+", "+table.getModel().getValueAt(i, 4);
    		try {
    			Statement s=con.createStatement();
    			s.execute(stmt);
    			
    		}
    		catch(SQLException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    	String stmt="dbo.spCompleteTrans "+transID;
    	try {
    		Statement s=con.createStatement();
    		s.execute(stmt);
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    private int createOrder() {
    	int transID=0;
    	//Creates Transaction
    	Connection con=new SQLConnection().openSQL();
    	String stmt = "dbo.sqCreateTrans "+UserID;
    	try {
    		Statement s=con.createStatement();
    		ResultSet r=s.executeQuery(stmt);
    		if(r.next()) {
    			transID=r.getInt(1);
    		}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return transID;
    }

    protected void logout() {
        this.POSframe.dispose();
        new LoginScreen();
    }

    private void addToTotal(float amount, boolean taxable) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        if(taxable)
        {
        	totalPurchase += amount+Math.round((amount*tax)*100f)/100f;
        }
        else
        {
        	totalPurchase += amount;
        }      
        TotalField.setText(formatter.format(totalPurchase));
    }
}

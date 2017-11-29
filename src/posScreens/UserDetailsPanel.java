
package posScreens;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import sql.SQLConnection;

/**
 *
 * @author renea
 */
public class UserDetailsPanel extends JPanel {

    private boolean status;
    private JTable usersTable;

    public UserDetailsPanel() {

        setLayout(new BorderLayout());

        // Creating JTable object passing data and header
        usersTable = new JTable(new UserTableModel());
        usersTable.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(usersTable);

//        add(usersTable.getTableHeader(), BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
    }

    class UserTableModel extends AbstractTableModel {
        // Column Header

        String[] columns = {
            "User Name", "First Name", "Last Name",
            "Active", "Admin"};

        Object[][] data = null;

        final String QUERY = "Select LoginID, FirstName, LastName, Active, Admin from dbo.USERS";

        public UserTableModel() {
            data = getUserData();
        }

        public Object[][] getUserData() {
            Object[][] rsData = null;
            try {
                // Loading the Driver

                // Getting Database Connection Object by Passing URL, Username and Password
                Connection connection = new SQLConnection().openSQL();

                Statement statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                ResultSet rs = statement.executeQuery(QUERY);

                int rowCount = getRowCountFromRs(rs); // Row Count
                int columnCount = getColumnCountFromRs(rs); // Column Count

                rsData = new Object[rowCount][columnCount];

                // Starting from First Row for Iteration
                rs.beforeFirst();

                int i = 0;

                while (rs.next()) {

                    int j = 0;

                    rsData[i][j++] = rs.getString("LoginID");
                    rsData[i][j++] = rs.getString("FirstName");
                    rsData[i][j++] = rs.getString("LastName");
                    rsData[i][j++] = rs.getBoolean("Active");
                    rsData[i][j++] = rs.getBoolean("Admin");

                    i++;
                }

                status = true;

                // Closing the Resources;
                statement.close();
                connection.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
            return rsData;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        //
        // This method is used by the JTable to define the default
        // renderer or editor for each cell. For example if you have
        // a boolean data it will be rendered as a check box. A
        // number value is right aligned.
        //
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return data[0][columnIndex].getClass();
        }
    }

    // Method to get Row Count from ResultSet Object
    private int getRowCountFromRs(ResultSet rs) {

        try {
            if (rs != null) {
                rs.last();
                return rs.getRow();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Method to get Column Count from ResultSet Object
    private int getColumnCountFromRs(ResultSet rs) {

        try {
            if (rs != null) {
                return rs.getMetaData().getColumnCount();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public String toString() {
        return (status) ? "Data Listed Successfully" : "Application Error Occured";
    }

}

package posScreens;

import javax.swing.JFrame;

/**
 *
 * @author renea
 */
public class UserDetailsPanelDisplayGUI {


    public UserDetailsPanelDisplayGUI() {
        // Creating Window using JFrame
        JFrame frame = new JFrame();
        frame.setTitle("User Data");
        frame.setSize(800, 500);

        // Adding Table View
        frame.add(new UserDetailsPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public static void main(String[] args) {
        UserDetailsPanelDisplayGUI employeeDetails = new UserDetailsPanelDisplayGUI();
        System.out.println(employeeDetails);
    }   
}

package QuickChat;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // Display welcome message
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        
        // Check if user is logged in (simplified for this example)
        boolean isLoggedIn = loginUser();
        
        if (isLoggedIn) {
            // Create MessageSender and start the application
            MessageSender messageSender = new MessageSender();
            messageSender.startApplication();
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Application exiting.");
        }
    }
    
    private static boolean loginUser() {
        // Simplified login - in a real application, this would have proper authentication
        int option = JOptionPane.showConfirmDialog(null, 
            "Are you logged in successfully?", "Login Check", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }
}
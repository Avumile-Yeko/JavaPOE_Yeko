package QuickChat;

import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;
    
    public static void main(String[] args) {
        // Add a default user for testing
        users.put("admin", new User("Admin", "User", "admin", "password123"));
        
        // Start with registration and login flow
        boolean authenticated = startAuthenticationFlow();
        
        if (authenticated) {
            // Display welcome message
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            
            // Create MessageSender and start the application
            MessageSender messageSender = new MessageSender();
            messageSender.startApplication();
        } else {
            JOptionPane.showMessageDialog(null, "Authentication failed. Application exiting.");
        }
    }
    
    private static boolean startAuthenticationFlow() {
        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,
                "Welcome to QuickChat Authentication\nPlease choose an option:",
                "Authentication",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
            
            switch (choice) {
                case 0: // Register
                    registerUser();
                    break;
                case 1: // Login
                    if (loginUser()) {
                        return true;
                    }
                    break;
                case 2: // Exit
                    return false;
                default:
                    return false;
            }
        }
    }
    
    private static void registerUser() {
        JOptionPane.showMessageDialog(null, "User Registration");
        
        String name = JOptionPane.showInputDialog("Enter your Name:");
        if (name == null || name.trim().isEmpty()) return;
        
        String surname = JOptionPane.showInputDialog("Enter your Surname:");
        if (surname == null || surname.trim().isEmpty()) return;
        
        String username = JOptionPane.showInputDialog("Enter your Username:");
        if (username == null || username.trim().isEmpty()) return;
        
        String password = JOptionPane.showInputDialog("Enter your Password:");
        if (password == null || password.trim().isEmpty()) return;
        
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Registration failed. Username already exists.");
            return;
        }
        
        User newUser = new User(name, surname, username, password);
        users.put(username, newUser);
        JOptionPane.showMessageDialog(null, "Registration successful! You can now login.");
    }
    
    private static boolean loginUser() {
        JOptionPane.showMessageDialog(null, "User Login");
        
        String username = JOptionPane.showInputDialog("Enter your Username:");
        if (username == null || username.trim().isEmpty()) return false;
        
        String password = JOptionPane.showInputDialog("Enter your Password:");
        if (password == null || password.trim().isEmpty()) return false;
        
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            JOptionPane.showMessageDialog(null, "Login successful! Welcome " + user.getName() + "!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
            return false;
        }
    }
    
    // Inner User class
    static class User {
        private String name;
        private String surname;
        private String username;
        private String password;
        
        public User(String name, String surname, String username, String password) {
            this.name = name;
            this.surname = surname;
            this.username = username;
            this.password = password;
        }
        
        // Getters
        public String getName() { return name; }
        public String getSurname() { return surname; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        
        @Override
        public String toString() {
            return name + " " + surname + " (" + username + ")";
        }
    }
}
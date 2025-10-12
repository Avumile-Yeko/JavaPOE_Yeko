package QuickChat;

import QuickChat.Login;
import QuickChat.MessageSender;
import QuickChat.Message;

import java.util.regex.Pattern;

public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    
    // Constructor
    public Login() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhoneNumber = "";
    }
    
    // Existing login methods...
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }
        
        return hasCapital && hasNumber && hasSpecialChar;
    }
    
    public boolean checkCellPhoneNumber() {
        String regex = "^\\+[0-9]{1,3}[0-9]{7,10}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }
    
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }
        
        return "User registered successfully.";
    }
    
    public boolean loginUser(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
    
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + "," + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    // Messaging functionality methods (used by MessageSender)
    public String checkMessageLength(String message) {
        if (message.length() > 250) {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
        return "Message ready to send.";
    }
    
    public String checkRecipientNumber(String recipient) {
        String regex = "^\\+[0-9]{1,3}[0-9]{7,10}$";
        if (Pattern.matches(regex, recipient)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }
    public void setCellPhoneNumber(String cellPhoneNumber) { this.cellPhoneNumber = cellPhoneNumber; }
}
import javax.swing.JOptionPane;
import java.util.*;
import java.util.regex.Pattern;

// Message class to handle message-related functionality
class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageContent;
    private String messageHash;
    private boolean sent;
    private boolean stored;
    
    public Message(int messageNumber, String recipient, String messageContent) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageHash = "";
        this.sent = false;
        this.stored = false;
    }
    
    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }
    
    // Check if message ID is valid (exactly 10 characters)
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }
    
    // Check recipient cell number format
    public int checkRecipientCell() {
        // Reuse the phone validation logic from Login class
        String regex = "^\\+[0-9]{1,3}[0-9]{7,10}$";
        if (Pattern.matches(regex, recipient)) {
            return 1; // Success
        } else {
            return 0; // Failure
        }
    }
    
    // Create message hash
    public String createMessageHash() {
        String[] words = messageContent.split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        String firstTwoDigits = messageID.substring(0, 2);
        this.messageHash = (firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }
    
    // Handle message sending options
    public String sentMessage(int choice) {
        switch (choice) {
            case 1: // Send Message
                this.sent = true;
                this.stored = false;
                return "Message successfully sent.";
            case 2: // Disregard Message
                this.sent = false;
                this.stored = false;
                return "Press 0 to delete message.";
            case 3: // Store Message
                this.sent = false;
                this.stored = true;
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }
    
    // Format message for display
    public String printMessage() {
        return String.format(
            "MessageID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s",
            messageID, messageHash, recipient, messageContent
        );
    }
    
    // Getters
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
    public boolean isSent() { return sent; }
    public boolean isStored() { return stored; }
}

// Extended Login class with messaging functionality
public class MainProg {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    private List<Message> messages;
    private int totalMessagesSent;
    
    // Constructor
    public MainProg() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhoneNumber = "";
        this.messages = new ArrayList<>();
        this.totalMessagesSent = 0;
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
    
    // Messaging functionality methods
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
    
    public void addMessage(Message message) {
        messages.add(message);
        if (message.isSent()) {
            totalMessagesSent++;
        }
    }
    
    public String printAllMessages() {
        if (messages.isEmpty()) {
            return "No messages sent.";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Message msg : messages) {
            if (msg.isSent()) {
                sb.append(msg.printMessage()).append("\n\n");
            }
        }
        return sb.toString();
    }
    
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    // QuickChat application with pop-up dialogs
    public void startQuickChat() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        
        // Get number of messages using pop-up
        int numMessages = 0;
        while (numMessages <= 0) {
            String input = JOptionPane.showInputDialog("How many messages do you wish to enter?");
            if (input == null) {
                // User clicked cancel
                JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
                return;
            }
            try {
                numMessages = Integer.parseInt(input);
                if (numMessages <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
        
        boolean running = true;
        int messageCount = 0;
        
        while (running && messageCount < numMessages) {
            // Create menu options for pop-up
            String[] options = {"Send Messages", "Show recently sent messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "QuickChat Menu - Message " + (messageCount + 1) + " of " + numMessages + "\nChoose an option:",
                "QuickChat",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
            );
            
            switch (choice) {
                case 0: // Send Messages
                    if (messageCount < numMessages) {
                        sendMessagePopup(messageCount + 1);
                        messageCount++;
                    } else {
                        JOptionPane.showMessageDialog(null, "You have reached your message limit.");
                    }
                    break;
                case 1: // Show recently sent messages
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case 2: // Quit
                case -1: // User closed the window
                    running = false;
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose again.");
            }
        }
        
        // Display summary
        if (totalMessagesSent > 0) {
            String summary = "=== Message Summary ===\n" +
                           "Total messages sent: " + totalMessagesSent + "\n\n" +
                           "All sent messages:\n" + printAllMessages();
            JOptionPane.showMessageDialog(null, summary);
        }
    }
    
    private void sendMessagePopup(int messageNumber) {
        // Get recipient number using pop-up
        String recipient = "";
        boolean validRecipient = false;
        while (!validRecipient) {
            recipient = JOptionPane.showInputDialog(
                "Creating Message " + messageNumber + "\n\n" +
                "Enter recipient's cell number (with international code, e.g., +27838968976):"
            );
            
            if (recipient == null) {
                // User clicked cancel
                return;
            }
            
            String recipientCheck = checkRecipientNumber(recipient);
            if (recipientCheck.equals("Cell phone number successfully captured.")) {
                validRecipient = true;
                JOptionPane.showMessageDialog(null, recipientCheck);
            } else {
                JOptionPane.showMessageDialog(null, recipientCheck + "\nPlease try again.");
            }
        }
        
        // Get message content using pop-up
        String messageContent = "";
        boolean validMessage = false;
        while (!validMessage) {
            messageContent = JOptionPane.showInputDialog(
                "Enter your message (max 250 characters):"
            );
            
            if (messageContent == null) {
                // User clicked cancel
                return;
            }
            
            String messageCheck = checkMessageLength(messageContent);
            if (messageCheck.equals("Message ready to send.")) {
                validMessage = true;
                JOptionPane.showMessageDialog(null, messageCheck);
            } else {
                JOptionPane.showMessageDialog(null, messageCheck + "\nPlease try again.");
            }
        }
        
        // Create message object
        Message message = new Message(messageNumber, recipient, messageContent);
        message.createMessageHash();
        
        // Display message details for confirmation
        String messageDetails = "Message Details:\n\n" +
                              "Message ID: " + message.getMessageID() + "\n" +
                              "Message Hash: " + message.getMessageHash() + "\n" +
                              "Recipient: " + message.getRecipient() + "\n" +
                              "Message: " + message.getMessageContent();
        
        JOptionPane.showMessageDialog(null, messageDetails);
        
        // Send options using pop-up
        String[] sendOptions = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose what to do with this message:",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            sendOptions,
            sendOptions[0]
        );
        
        if (choice == -1) {
            // User closed the window, treat as disregard
            choice = 1;
        }
        
        String result = message.sentMessage(choice + 1); // +1 because array starts at 0 but method expects 1,2,3
        JOptionPane.showMessageDialog(null, result);
        
        if (choice == 0) { // Send message
            addMessage(message);
            // Display final confirmation
            JOptionPane.showMessageDialog(null, 
                "Message Sent Successfully!\n\n" + message.printMessage());
        } else if (choice == 2) { // Store message
            addMessage(message);
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
    
    // Main method with pop-up registration and login
    public static void main(String[] args) {
        MainProg loginSystem = new MainProg();
        
        // Registration using pop-ups
        JOptionPane.showMessageDialog(null, "=== User Registration ===");
        
        // Get user details
        loginSystem.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        if (loginSystem.getFirstName() == null) return; // User cancelled
        
        loginSystem.setLastName(JOptionPane.showInputDialog("Enter last name:"));
        if (loginSystem.getLastName() == null) return; // User cancelled
        
        // Get and validate username
        boolean validUsername = false;
        while (!validUsername) {
            String username = JOptionPane.showInputDialog(
                "Enter username (must contain '_' and be ≤5 characters):"
            );
            if (username == null) return; // User cancelled
            
            loginSystem.setUsername(username);
            
            if (loginSystem.checkUserName()) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                validUsername = true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }
        
        // Get and validate password
        boolean validPassword = false;
        while (!validPassword) {
            String password = JOptionPane.showInputDialog(
                "Enter password (≥8 chars, capital letter, number, special char):"
            );
            if (password == null) return; // User cancelled
            
            loginSystem.setPassword(password);
            
            if (loginSystem.checkPasswordComplexity()) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                validPassword = true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }
        
        // Get and validate cell phone number
        boolean validPhone = false;
        while (!validPhone) {
            String phone = JOptionPane.showInputDialog(
                "Enter cell phone number (with international code, e.g., +27838968976):"
            );
            if (phone == null) return; // User cancelled
            
            loginSystem.setCellPhoneNumber(phone);
            
            if (loginSystem.checkCellPhoneNumber()) {
                JOptionPane.showMessageDialog(null, "Cell number successfully captured.");
                validPhone = true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
            }
        }
        
        // Complete registration
        String registrationResult = loginSystem.registerUser();
        JOptionPane.showMessageDialog(null, registrationResult);
        
        // Login using pop-ups
        JOptionPane.showMessageDialog(null, "=== User Login ===");
        
        boolean loggedIn = false;
        while (!loggedIn) {
            String inputUsername = JOptionPane.showInputDialog("Enter username:");
            if (inputUsername == null) return; // User cancelled
            
            String inputPassword = JOptionPane.showInputDialog("Enter password:");
            if (inputPassword == null) return; // User cancelled
            
            loggedIn = loginSystem.loginUser(inputUsername, inputPassword);
            String loginMessage = loginSystem.returnLoginStatus(loggedIn);
            JOptionPane.showMessageDialog(null, loginMessage);
            
            if (!loggedIn) {
                int retry = JOptionPane.showConfirmDialog(null, 
                    "Login failed. Would you like to try again?", 
                    "Login Failed", 
                    JOptionPane.YES_NO_OPTION);
                if (retry != JOptionPane.YES_OPTION) {
                    return;
                }
            }
        }
        
        // Start QuickChat after successful login
        loginSystem.startQuickChat();
    }
}
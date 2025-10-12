package QuickChat;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class MessageSender {
    private List<Message> messages;
    private int totalMessagesSent;
    
    public MessageSender() {
        this.messages = new ArrayList<>();
        this.totalMessagesSent = 0;
    }
    
    public void startApplication() {
        boolean running = true;
        
        while (running) {
            // Display numeric menu
            String menu = "Please choose an option:\n" +
                         "1) Send Messages\n" +
                         "2) Show recently sent messages\n" +
                         "3) Quit";
            
            String choice = JOptionPane.showInputDialog(menu);
            
            if (choice == null) {
                // User closed the dialog
                break;
            }
            
            switch (choice.trim()) {
                case "1":
                    sendMessagesFlow();
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case "3":
                    running = false;
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
    
    private void sendMessagesFlow() {
        try {
            // Get number of messages user wants to send
            String numMessagesStr = JOptionPane.showInputDialog(
                "How many messages do you wish to enter?");
            
            if (numMessagesStr == null) return;
            
            int numMessages = Integer.parseInt(numMessagesStr);
            
            for (int i = 0; i < numMessages; i++) {
                Message message = createMessage(i + 1);
                if (message != null) {
                    handleMessageAction(message);
                }
            }
            
            // Display total messages sent
            JOptionPane.showMessageDialog(null, 
                "Total number of messages sent: " + totalMessagesSent);
                
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }
    
    private Message createMessage(int messageNumber) {
        // Get recipient number
        String recipient = JOptionPane.showInputDialog(
            "Enter recipient cell number (with international code):");
        
        if (recipient == null) return null;
        
        // Validate recipient
        Message tempMessage = new Message("", recipient, messageNumber);
        if (tempMessage.checkRecipientCell() != 1) {
            JOptionPane.showMessageDialog(null, 
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            return null;
        }
        
        // Get message content
        String messageContent = JOptionPane.showInputDialog(
            "Enter your message (max 250 characters):");
        
        if (messageContent == null) return null;
        
        // Create message object
        Message message = new Message(messageContent, recipient, messageNumber);
        
        // Validate message length
        if (messageContent.length() > 250) {
            JOptionPane.showMessageDialog(null, 
                "Please enter a message of less than 250 characters.");
            return null;
        }
        
        return message;
    }
    
    private void handleMessageAction(Message message) {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null,
            "Choose an action for the message:",
            "Message Action",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]);
        
        switch (choice) {
            case 0: // Send Message
                messages.add(message);
                totalMessagesSent++;
                displayMessageDetails(message);
                JOptionPane.showMessageDialog(null, "Message successfully sent.");
                break;
            case 1: // Disregard Message
                JOptionPane.showMessageDialog(null, "Press 0 to delete message.");
                break;
            case 2: // Store Message
                message.storeMessage();
                JOptionPane.showMessageDialog(null, "Message successfully stored.");
                break;
            default:
                // Do nothing
        }
    }
    
    private void displayMessageDetails(Message message) {
        String details = "Message Details:\n" +
                        "Message ID: " + message.getMessageId() + "\n" +
                        "Message Hash: " + message.createMessageHash() + "\n" +
                        "Recipient: " + message.getRecipient() + "\n" +
                        "Message: " + message.getMessage();
        
        JOptionPane.showMessageDialog(null, details);
    }
    
    // Getter for testing
    public int getTotalMessagesSent() {
        return totalMessagesSent;
    }
    
    public List<Message> getMessages() {
        return messages;
    }
}
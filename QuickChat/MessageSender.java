package QuickChat;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class MessageSender {
    private List<Message> sentMessages;
    private List<Message> disregardedMessages;
    private List<Message> storedMessages;
    private List<String> messageHashes;
    private List<String> messageIDs;
    private int totalMessagesSent;
    
    public MessageSender() {
        this.sentMessages = new ArrayList<>();
        this.disregardedMessages = new ArrayList<>();
        this.storedMessages = new ArrayList<>();
        this.messageHashes = new ArrayList<>();
        this.messageIDs = new ArrayList<>();
        this.totalMessagesSent = 0;
        
        // Populate with test data
        populateTestData();
    }
    
    /**
     * Populates arrays with test data as specified in requirements
     */
    private void populateTestData() {
        // Test Data Message 1 - Sent
        Message msg1 = new Message("Did you get the cake?", "+27834557896", 1);
        sentMessages.add(msg1);
        messageHashes.add(msg1.createMessageHash());
        messageIDs.add(msg1.getMessageId());
        totalMessagesSent++;
        
        // Test Data Message 2 - Stored
        Message msg2 = new Message("Where are you? You are late! I have asked you to be on time.", "+27838884567", 2);
        storedMessages.add(msg2);
        messageHashes.add(msg2.createMessageHash());
        messageIDs.add(msg2.getMessageId());
        msg2.storeMessage();
        
        // Test Data Message 3 - Disregarded
        Message msg3 = new Message("Yohoooo, I am at your gate.", "+27834484567", 3);
        disregardedMessages.add(msg3);
        messageHashes.add(msg3.createMessageHash());
        messageIDs.add(msg3.getMessageId());
        
        // Test Data Message 4 - Sent
        Message msg4 = new Message("It is dinner time !", "0838884567", 4);
        sentMessages.add(msg4);
        messageHashes.add(msg4.createMessageHash());
        messageIDs.add(msg4.getMessageId());
        totalMessagesSent++;
        
        // Test Data Message 5 - Stored
        Message msg5 = new Message("Ok, I am leaving without you.", "+27838884567", 5);
        storedMessages.add(msg5);
        messageHashes.add(msg5.createMessageHash());
        messageIDs.add(msg5.getMessageId());
        msg5.storeMessage();
    }
    
    /**
     * Main application loop with simplified menu options
     */
    public void startApplication() {
        boolean running = true;
        
        while (running) {
            // Display simplified numeric menu (removed Array Features option)
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
                    showRecentlySentMessagesMenu(); // Now includes all array features
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
    
    /**
     * Enhanced menu for "Show recently sent messages" that includes all array features
     */
    private void showRecentlySentMessagesMenu() {
        while (true) {
            String menu = "Message Management Features:\n" +
                         "1) Display sender and recipient of all sent messages\n" +
                         "2) Display the longest sent message\n" +
                         "3) Search for message by ID\n" +
                         "4) Search messages by recipient\n" +
                         "5) Delete message by hash\n" +
                         "6) Display full report of sent messages\n" +
                         "7) Return to main menu";
            
            String choice = JOptionPane.showInputDialog(menu);
            
            if (choice == null || choice.equals("7")) {
                break;
            }
            
            switch (choice.trim()) {
                case "1":
                    displaySentMessagesSendersRecipients();
                    break;
                case "2":
                    displayLongestMessage();
                    break;
                case "3":
                    searchMessageByID();
                    break;
                case "4":
                    searchMessagesByRecipient();
                    break;
                case "5":
                    deleteMessageByHash();
                    break;
                case "6":
                    displayFullReport();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1-7.");
            }
        }
    }
    
    /**
     * Displays sender and recipient of all sent messages
     */
    private void displaySentMessagesSendersRecipients() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("Sent Messages - Senders and Recipients:\n\n");
        for (Message msg : sentMessages) {
            sb.append("Recipient: ").append(msg.getRecipient())
              .append("\nMessage: ").append(msg.getMessage())
              .append("\n---\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
    /**
     * Finds and displays the longest sent message
     */
    private void displayLongestMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }
        
        Message longestMessage = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessage().length() > longestMessage.getMessage().length()) {
                longestMessage = msg;
            }
        }
        
        String result = "Longest Sent Message:\n\n" +
                       "Recipient: " + longestMessage.getRecipient() + "\n" +
                       "Message: " + longestMessage.getMessage() + "\n" +
                       "Length: " + longestMessage.getMessage().length() + " characters";
        
        JOptionPane.showMessageDialog(null, result);
    }
    
    /**
     * Searches for a message by its ID and displays details
     */
    private void searchMessageByID() {
        String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (searchID == null || searchID.trim().isEmpty()) return;
        
        // Search in all message arrays
        Message foundMessage = null;
        String messageType = "";
        
        for (Message msg : sentMessages) {
            if (msg.getMessageId().equals(searchID)) {
                foundMessage = msg;
                messageType = "Sent";
                break;
            }
        }
        
        if (foundMessage == null) {
            for (Message msg : storedMessages) {
                if (msg.getMessageId().equals(searchID)) {
                    foundMessage = msg;
                    messageType = "Stored";
                    break;
                }
            }
        }
        
        if (foundMessage == null) {
            for (Message msg : disregardedMessages) {
                if (msg.getMessageId().equals(searchID)) {
                    foundMessage = msg;
                    messageType = "Disregarded";
                    break;
                }
            }
        }
        
        if (foundMessage != null) {
            String result = "Message Found (" + messageType + "):\n\n" +
                           "Message ID: " + foundMessage.getMessageId() + "\n" +
                           "Recipient: " + foundMessage.getRecipient() + "\n" +
                           "Message: " + foundMessage.getMessage() + "\n" +
                           "Hash: " + foundMessage.createMessageHash();
            JOptionPane.showMessageDialog(null, result);
        } else {
            JOptionPane.showMessageDialog(null, "Message with ID '" + searchID + "' not found.");
        }
    }
    
    /**
     * Searches for all messages sent to a specific recipient
     */
    private void searchMessagesByRecipient() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number to search:");
        if (recipient == null || recipient.trim().isEmpty()) return;
        
        List<Message> foundMessages = new ArrayList<>();
        
        // Search in all message arrays
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                foundMessages.add(msg);
            }
        }
        
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                foundMessages.add(msg);
            }
        }
        
        for (Message msg : disregardedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                foundMessages.add(msg);
            }
        }
        
        if (foundMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient: " + recipient);
        } else {
            StringBuilder sb = new StringBuilder("Messages for recipient " + recipient + ":\n\n");
            for (Message msg : foundMessages) {
                sb.append("Message: ").append(msg.getMessage())
                  .append("\nStatus: ").append(getMessageStatus(msg))
                  .append("\n---\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
    
    /**
     * Deletes a message using its hash value
     */
    private void deleteMessageByHash() {
        String hash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
        if (hash == null || hash.trim().isEmpty()) return;
        
        boolean deleted = false;
        Message deletedMessage = null;
        
        // Search and remove from sent messages
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).createMessageHash().equals(hash)) {
                deletedMessage = sentMessages.remove(i);
                deleted = true;
                totalMessagesSent--;
                break;
            }
        }
        
        // Search and remove from stored messages
        if (!deleted) {
            for (int i = 0; i < storedMessages.size(); i++) {
                if (storedMessages.get(i).createMessageHash().equals(hash)) {
                    deletedMessage = storedMessages.remove(i);
                    deleted = true;
                    break;
                }
            }
        }
        
        // Search and remove from disregarded messages
        if (!deleted) {
            for (int i = 0; i < disregardedMessages.size(); i++) {
                if (disregardedMessages.get(i).createMessageHash().equals(hash)) {
                    deletedMessage = disregardedMessages.remove(i);
                    deleted = true;
                    break;
                }
            }
        }
        
        // Remove from hash and ID arrays
        if (deleted) {
            messageHashes.remove(hash);
            messageIDs.remove(deletedMessage.getMessageId());
            
            String result = "Message successfully deleted.\n\n" +
                           "Message: " + deletedMessage.getMessage() + "\n" +
                           "Recipient: " + deletedMessage.getRecipient() + "\n" +
                           "Hash: " + hash;
            JOptionPane.showMessageDialog(null, result);
        } else {
            JOptionPane.showMessageDialog(null, "Message with hash '" + hash + "' not found.");
        }
    }
    
    /**
     * Displays a comprehensive report of all sent messages
     */
    private void displayFullReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages to display.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("FULL SENT MESSAGES REPORT\n\n");
        sb.append("Total Sent Messages: ").append(sentMessages.size()).append("\n\n");
        
        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);
            sb.append("Message ").append(i + 1).append(":\n")
              .append("  Message ID: ").append(msg.getMessageId()).append("\n")
              .append("  Message Hash: ").append(msg.createMessageHash()).append("\n")
              .append("  Recipient: ").append(msg.getRecipient()).append("\n")
              .append("  Message: ").append(msg.getMessage()).append("\n")
              .append("  Length: ").append(msg.getMessage().length()).append(" characters\n\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
    /**
     * Helper method to determine message status
     */
    private String getMessageStatus(Message message) {
        if (sentMessages.contains(message)) return "Sent";
        if (storedMessages.contains(message)) return "Stored";
        if (disregardedMessages.contains(message)) return "Disregarded";
        return "Unknown";
    }
    
    /**
     * Original message sending flow (unchanged from previous version)
     */
    private void sendMessagesFlow() {
        try {
            String numMessagesStr = JOptionPane.showInputDialog("How many messages do you wish to enter?");
            if (numMessagesStr == null) return;
            
            int numMessages = Integer.parseInt(numMessagesStr);
            
            for (int i = 0; i < numMessages; i++) {
                Message message = createMessage(i + 1);
                if (message != null) {
                    handleMessageAction(message);
                }
            }
            
            JOptionPane.showMessageDialog(null, "Total number of messages sent: " + totalMessagesSent);
                
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }
    
    private Message createMessage(int messageNumber) {
        String recipient = JOptionPane.showInputDialog("Enter recipient cell number (with international code):");
        if (recipient == null) return null;
        
        Message tempMessage = new Message("", recipient, messageNumber);
        int checkResult = tempMessage.checkRecipientCell();
        if (checkResult == 0) {
            JOptionPane.showMessageDialog(null, 
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            return null;
        }
        
        String messageContent = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
        if (messageContent == null) return null;
        
        if (messageContent.length() > 250) {
            JOptionPane.showMessageDialog(null, 
                "Message exceeds 250 characters by " + (messageContent.length() - 250) + ", please reduce size.");
            return null;
        }
        
        return new Message(messageContent, recipient, messageNumber);
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
        
        String result = "";
        switch (choice) {
            case 0: // Send Message
                sentMessages.add(message);
                messageHashes.add(message.createMessageHash());
                messageIDs.add(message.getMessageId());
                totalMessagesSent++;
                displayMessageDetails(message);
                result = "Message successfully sent.";
                break;
            case 1: // Disregard Message
                disregardedMessages.add(message);
                messageHashes.add(message.createMessageHash());
                messageIDs.add(message.getMessageId());
                result = "Press 0 to delete message.";
                break;
            case 2: // Store Message
                storedMessages.add(message);
                messageHashes.add(message.createMessageHash());
                messageIDs.add(message.getMessageId());
                message.storeMessage();
                result = "Message successfully stored.";
                break;
            default:
                result = "No action selected.";
        }
        
        JOptionPane.showMessageDialog(null, result);
    }
    
    private void displayMessageDetails(Message message) {
        String details = "Message Details:\n" +
                        "Message ID: " + message.getMessageId() + "\n" +
                        "Message Hash: " + message.createMessageHash() + "\n" +
                        "Recipient: " + message.getRecipient() + "\n" +
                        "Message: " + message.getMessage();
        
        JOptionPane.showMessageDialog(null, details);
    }
    
    // Getters for testing
    public int getTotalMessagesSent() { return totalMessagesSent; }
    public List<Message> getSentMessages() { return sentMessages; }
    public List<Message> getDisregardedMessages() { return disregardedMessages; }
    public List<Message> getStoredMessages() { return storedMessages; }
    public List<String> getMessageHashes() { return messageHashes; }
    public List<String> getMessageIDs() { return messageIDs; }
}
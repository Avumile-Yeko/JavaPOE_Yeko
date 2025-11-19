package QuickChat;

import java.util.Random;

public class Message {
    private String message;
    private String recipient;
    private String messageId;
    private int messageNumber;
    
    /**
     * Constructor for creating a new Message
     * @param message The message content
     * @param recipient The recipient's phone number
     * @param messageNumber The sequential message number
     */
    public Message(String message, String recipient, int messageNumber) {
        this.message = message;
        this.recipient = recipient;
        this.messageNumber = messageNumber;
        this.messageId = generateMessageId();
    }
    
    /**
     * Generates a random 10-digit message ID
     * @return 10-digit message ID as String
     */
    private String generateMessageId() {
        Random random = new Random();
        long id = 1000000000L + random.nextInt(900000000);
        return String.valueOf(id);
    }
    
    /**
     * Validates that the message ID is exactly 10 characters
     * @return true if valid, false otherwise
     */
    public boolean checkMessageId() {
        return messageId != null && messageId.length() == 10;
    }
    
    /**
     * Validates recipient cell number format
     * @return 1 if valid, -1 if invalid
     */
    public int checkRecipientCell() {
        if (recipient == null || recipient.trim().isEmpty()) {
            return -1; // Invalid: empty recipient
        }
        
        // Remove any non-digit characters except +
        String cleanNumber = recipient.replaceAll("[^+0-9]", "");
        
        // Check if starts with international code (+ followed by digits)
        if (cleanNumber.startsWith("+") && cleanNumber.length() <= 13) {
            String digitsOnly = cleanNumber.substring(1);
            if (digitsOnly.matches("\\d+") && digitsOnly.length() <= 12) {
                return 1; // Valid international number
            }
        }
        
        // Check if it's a local number (10 digits)
        if (cleanNumber.matches("\\d{10}")) {
            return 1; // Valid local number
        }
        
        return -1; // Invalid format
    }
    
    /**
     * Returns true if recipient cell is valid, false otherwise
     * @return true if recipient cell is valid, false otherwise
     */
    public boolean isRecipientCellValid() {
        return checkRecipientCell() == 1;
    }
    
    /**
     * Creates a message hash in format: first2DigitsOfID:messageNumber:firstWordLastWord
     * @return formatted message hash in uppercase
     */
    public String createMessageHash() {
        if (message == null || message.isEmpty()) {
            return "00:0:EMPTY";
        }
        
        String[] words = message.split("\\s+");
        String firstWord = words.length > 0 ? words[0].replaceAll("[^a-zA-Z]", "") : "";
        String lastWord = words.length > 0 ? words[words.length - 1].replaceAll("[^a-zA-Z]", "") : "";
        
        String messageIdPrefix = messageId.length() >= 2 ? messageId.substring(0, 2) : "00";
        
        return (messageIdPrefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }
    
    /**
     * Returns message ready status
     * @return "Message ready to send."
     */
    public String sentMessage() {
        return "Message ready to send.";
    }
    
    /**
     * Formats message details for display
     * @return formatted string with message details
     */
    public String printMessages() {
        return "Message ID: " + messageId + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + message + "\n" +
               "Hash: " + createMessageHash();
    }
    
    /**
     * Returns total messages count (always 1 for single message)
     * @return 1
     */
    public int returnTotalMessages() {
        return 1;
    }
    
    /**
     * Stores message as JSON format (simulated)
     */
    public void storeMessage() {
        // Simple JSON storage simulation
        String json = String.format(
            "{\"messageId\": \"%s\", \"recipient\": \"%s\", \"message\": \"%s\", \"hash\": \"%s\"}",
            messageId, recipient, message.replace("\"", "\\\""), createMessageHash()
        );
        
        // In a real application, this would write to a file or database
        System.out.println("Stored message as JSON: " + json);
    }
    
    // Getters
    public String getMessage() { return message; }
    public String getRecipient() { return recipient; }
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
}
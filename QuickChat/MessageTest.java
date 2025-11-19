package QuickChat;

import org.junit.jupiter.api.Test;

import QuickChat.Main.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MessageTest {

    // Test Message class functionality
    @Test
    public void testCheckMessageId() {
        Message message = new Message("Test message", "+27718693002", 1);
        assertTrue(message.checkMessageId(), "Message ID should be exactly 10 characters");
    }

    private void assertTrue(boolean checkMessageId, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertTrue'");
    }

    @Test
    public void testCheckRecipientCell_Success() {
        Message message1 = new Message("Test", "+27718693002", 1);
        assertEquals(1, message1.checkRecipientCell(), "International number should be valid");
        
        Message message2 = new Message("Test", "0857597588", 1);
        assertEquals(1, message2.checkRecipientCell(), "Local number should be valid");
    }

    private void assertEquals(int i, int checkRecipientCell, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        Message message1 = new Message("Test", "123", 1);
        assertEquals(-1, message1.checkRecipientCell(), "Short number should be invalid");
        
        Message message2 = new Message("Test", "+271234567890123", 1);
        assertEquals(-1, message2.checkRecipientCell(), "Too long international number should be invalid");
        
        Message message3 = new Message("Test", "abc", 1);
        assertEquals(-1, message3.checkRecipientCell(), "Non-numeric should be invalid");
    }

    @Test
    public void testCreateMessageHash() {
        Message message = new Message("Hi Mike, can you join us for dinner tonight", "+27718693002", 1);
        String hash = message.createMessageHash();
        assertNotNull(hash);
        assertTrue(hash.matches("\d{2}:\d+:\w+\w+"));
    }

    private void assertNotNull(String hash) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertNotNull'");
    }

    @Test
    public void testMessageLength_Success() {
        String shortMessage = "This is a short message";
        Message message = new Message(shortMessage, "+27718693002", 1);
        assertEquals(shortMessage, message.getMessage());
    }

    private void assertEquals(String shortMessage, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    public void testMessageLength_Failure() {
        String longMessage = "A".repeat(251);
        Message message = new Message(longMessage, "+27718693002", 1);
        assertTrue(message.getMessage().length() > 250);
    }

    @Test
    public void testSentMessage() {
        Message message = new Message("Test message", "+27718693002", 1);
        assertEquals("Message ready to send.", message.sentMessage());
    }

    @Test
    public void testPrintMessages() {
        Message message = new Message("Test message", "+27718693002", 1);
        String printed = message.printMessages();
        assertTrue(printed.contains("Message ID"));
        assertTrue(printed.contains("Recipient"));
        assertTrue(printed.contains("Message"));
        assertTrue(printed.contains("Hash"));
    }

    private void assertTrue(boolean contains) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertTrue'");
    }

    @Test
    public void testReturnTotalMessages() {
        Message message = new Message("Test message", "+27718693002", 1);
        assertEquals(1, message.returnTotalMessages());
    }

    private void assertEquals(int i, int returnTotalMessages) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message("Test message", "+27718693002", 1);
        assertDoesNotThrow(() -> message.storeMessage());
    }

    private void assertDoesNotThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertDoesNotThrow'");
    }

    @Test
    public void testSpecificMessageHash() {
        Message message = new Message("Hi Mike, can you join us for dinner tonight", "+27718693002", 1);
        String hash = message.createMessageHash();
        assertTrue(hash.endsWith("HITONIGHT"));
    }

    // Test Array Management Features
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        MessageSender sender = new MessageSender();
        List<Message> sentMessages = sender.getSentMessages();
        
        // Should contain test messages 1 and 4
        assertEquals(2, sentMessages.size(), "Sent messages array should contain 2 messages");
        
        // Check if contains expected messages
        boolean foundMessage1 = false;
        boolean foundMessage4 = false;
        
        for (Message msg : sentMessages) {
            if (msg.getMessage().equals("Did you get the cake?")) {
                foundMessage1 = true;
            }
            if (msg.getMessage().equals("It is dinner time !")) {
                foundMessage4 = true;
            }
        }
        
        assertTrue(foundMessage1, "Should contain 'Did you get the cake?'");
        assertTrue(foundMessage4, "Should contain 'It is dinner time!'");
    }

    @Test
    public void testDisplayLongestMessage() {
        MessageSender sender = new MessageSender();
        List<Message> sentMessages = sender.getSentMessages();
        
        Message longestMessage = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessage().length() > longestMessage.getMessage().length()) {
                longestMessage = msg;
            }
        }
        
        assertEquals("Where are you? You are late! I have asked you to be on time.", 
                    longestMessage.getMessage(), 
                    "Longest message should be message 2");
    }

    private void assertEquals(String string, String message, String string2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    public void testSearchForMessageID() {
        MessageSender sender = new MessageSender();
        List<Message> sentMessages = sender.getSentMessages();
        
        // Find message with recipient "0838884567" (Message 4)
        Message foundMessage = null;
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals("0838884567")) {
                foundMessage = msg;
                break;
            }
        }
        
        assertNotNull(foundMessage, "Should find message with recipient 0838884567");
        assertEquals("It is dinner time !", foundMessage.getMessage());
    }

    private void assertNotNull(Message foundMessage, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertNotNull'");
    }

    @Test
    public void testSearchMessagesByRecipient() {
        MessageSender sender = new MessageSender();
        
        // Search for recipient +27838884567 (should find messages 2 and 5)
        int count = 0;
        for (Message msg : sender.getStoredMessages()) {
            if (msg.getRecipient().equals("+27838884567")) {
                count++;
            }
        }
        
        assertEquals(2, count, "Should find 2 messages for recipient +27838884567");
    }

    @Test
    public void testDeleteMessageByHash() {
        MessageSender sender = new MessageSender();
        List<Message> storedMessages = sender.getStoredMessages();
        
        // Get hash of first stored message
        String hashToDelete = storedMessages.get(0).createMessageHash();
        int initialSize = storedMessages.size();
        
        // Simulate deletion (in actual implementation, this would call the delete method)
        Message messageToDelete = null;
        for (Message msg : storedMessages) {
            if (msg.createMessageHash().equals(hashToDelete)) {
                messageToDelete = msg;
                break;
            }
        }
        
        assertNotNull(messageToDelete, "Should find message with the specified hash");
        assertEquals("Where are you? You are late! I have asked you to be on time.", 
                    messageToDelete.getMessage());
    }

    @Test
    public void testDisplayReport() {
        MessageSender sender = new MessageSender();
        List<Message> sentMessages = sender.getSentMessages();
        
        // Verify report contains all required fields for each message
        for (Message msg : sentMessages) {
            assertNotNull(msg.getMessageId(), "Message ID should not be null");
            assertNotNull(msg.createMessageHash(), "Message hash should not be null");
            assertNotNull(msg.getRecipient(), "Recipient should not be null");
            assertNotNull(msg.getMessage(), "Message content should not be null");
            assertTrue(msg.getMessage().length() > 0, "Message should not be empty");
        }
    }

    private void assertNotNull(String messageId, String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertNotNull'");
    }

    // Test authentication functionality
    @Test
    public void testUserRegistration() {
        Main.User user = new Main.User("John", "Doe", "johndoe", "password123");
        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    private void assertNotNull(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertNotNull'");
    }

    @Test
    public void testUserAuthentication() {
        Main.User user = new Main.User("John", "Doe", "johndoe", "password123");
        assertTrue(user.getPassword().equals("password123"));
        assertFalse(user.getPassword().equals("wrongpassword"));
    }

    private void assertFalse(boolean equals) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertFalse'");
    }

    @Test
    public void testUserToString() {
        Main.User user = new Main.User("John", "Doe", "johndoe", "password123");
        String userString = user.toString();
        assertTrue(userString.contains("John"));
        assertTrue(userString.contains("Doe"));
        assertTrue(userString.contains("johndoe"));
    }
}
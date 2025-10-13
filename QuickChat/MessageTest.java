package QuickChat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JOptionPane;

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
        assertTrue(hash.matches("\\d{2}:\\d+:\\w+\\w+"), hash);
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

    private void assertTrue(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertTrue'");
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
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> message.storeMessage());
    }
    @Test
    public void testSpecificMessageHash() {
        Message message = new Message("Hi Mike, can you join us for dinner tonight", "+27718693002", 1);
        String hash = message.createMessageHash();
        assertTrue(hash.endsWith("HITONIGHT"));
    }

    // Test authentication functionality (mocked)
    @Test
    public void testUserRegistration() {
        // Mock user registration logic
        Main.User user = new Main.User("John", "Doe", "johndoe", "password123");
        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testUserAuthentication() {
        // Test user creation and password matching
        Main.User user = new Main.User("John", "Doe", "johndoe", "password123");
        
        // Test correct password
        assertTrue(user.getPassword().equals("password123"));
        
        // Test incorrect password
        assertFalse(user.getPassword().equals("wrongpassword"));
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
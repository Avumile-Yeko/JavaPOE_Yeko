package QuickChat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

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
        assertTrue(hash.matches("\\d{2}:\\d+:\\w+\\w+"));
    }

    private void assertTrue(boolean matches) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertTrue'");
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

    @Test
    public void testReturnTotalMessages() {
        Message message = new Message("Test message", "+27718693002", 1);
        assertEquals(1, message.returnTotalMessages(), null);
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message("Test message", "+27718693002", 1);
        // This test just ensures the method doesn't throw an exception
        assertDoesNotThrow(() -> message.storeMessage());
    }

    private void assertDoesNotThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertDoesNotThrow'");
    }

    private void assertDoesNotThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertDoesNotThrow'");
    }

    @Test
    public void testSpecificMessageHash() {
        Message message = new Message("Hi Mike, can you join us for dinner tonight", "+27718693002", 1);
        String hash = message.createMessageHash();
        // The hash should follow the pattern: first 2 digits of messageId : messageNumber : firstWord + lastWord
        assertTrue(hash.endsWith("HITONIGHT"));
    }
}
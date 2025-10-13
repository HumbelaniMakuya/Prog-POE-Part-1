/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;

/**
 * Unit tests for the Message class.
 * All tests use provided data and expected outputs.
 * Static values reset before each test to ensure independence.
 * 
 * @author RC_Student_Lab
 */
public class MessageNGTest {

    // Reset static counters and clear JSON file before every test
    @BeforeMethod
    public void resetMessages() {
        try {
            // Reset totalMessages
            java.lang.reflect.Field totalField = Message.class.getDeclaredField("totalMessages");
            totalField.setAccessible(true);
            totalField.setInt(null, 0);

            // Reset allMessages
            java.lang.reflect.Field allField = Message.class.getDeclaredField("allMessages");
            allField.setAccessible(true);
            allField.set(null, "");

            // Clear stored JSON file
            File jsonFile = new File("stored_messages.json");
            if (jsonFile.exists()) jsonFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    Message msg1 = new Message(
            "user1",
            "Mike Jones",
            "+27718693002",
            "Hi Mike, can you join us for dinner tonight",
            1
    );

    Message msg2 = new Message(
            "user2",
            "Keegan Smith",
            "08575975889",
            "Hi Keegan, did you receive the payment?",
            2
    );

    // ------------------------------------------------------------
    // TEST 1 – Check Message ID length
    // ------------------------------------------------------------
    @Test
    public void testCheckMessageID() {
        assertTrue(msg1.checkMessageID(), "Message ID should not exceed 10 characters.");
    }

    // ------------------------------------------------------------
    // TEST 2 – Check Recipient Cell (valid + invalid)
    // ------------------------------------------------------------
    @Test
    public void testCheckRecipientCell_Success() {
        assertEquals(msg1.checkRecipientCell(), 1,
                "Cell phone number successfully captured.");
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        assertEquals(msg2.checkRecipientCell(), 0,
                "Cell phone number is incorrectly formatted or missing +27.");
    }

    // ------------------------------------------------------------
    // TEST 3 – Check Message Length (Success and Failure)
    // ------------------------------------------------------------
    @Test
    public void testCheckMessageLength_Success() {
        assertTrue(msg1.checkMessageLength());
        assertEquals(msg1.getMessageLengthFeedback(), "Message ready to send.");
    }

    @Test
    public void testCheckMessageLength_Failure() {
        String longText = "A".repeat(260);
        Message longMsg = new Message("user3", "Test User", "+27711111111", longText, 3);
        assertFalse(longMsg.checkMessageLength());
        int excess = longText.length() - 250;
        assertEquals(longMsg.getMessageLengthFeedback(),
                "❌ Message too long. Max 250 characters. Reduce by " + excess + " characters.");
    }

    // ------------------------------------------------------------
    // TEST 4 – Create Message Hash
    // ------------------------------------------------------------
    @Test
    public void testCreateMessageHash() {
        String hash = msg1.createMessageHash();
        assertNotNull(hash, "Message hash should not be null.");
        assertTrue(hash.startsWith("MSG-"), "Message hash should start with MSG-");
    }

    // ------------------------------------------------------------
    // TEST 5 – SentMessage Options (Send, Disregard, Store)
    // ------------------------------------------------------------
    @Test
    public void testSentMessage_Send() {
        String result = msg1.SentMessage("1");
        assertEquals(result, "Message successfully sent.");
        assertEquals(Message.returnTotalMessages(), 1);
    }

    @Test
    public void testSentMessage_Disregard() {
        String result = msg2.SentMessage("2");
        assertEquals(result, "Message disregarded.");
        assertEquals(Message.returnTotalMessages(), 0);
    }

    @Test
    public void testSentMessage_Store() {
        Message msg3 = new Message("user3", "John Doe", "+27734567890", "Testing store message", 3);
        String result = msg3.SentMessage("3");
        assertEquals(result, "Message successfully stored.");
        assertEquals(Message.returnTotalMessages(), 0);
    }

    // ------------------------------------------------------------
    // TEST 6 – Print Messages
    // ------------------------------------------------------------
    @Test
    public void testPrintMessages() {
        msg1.SentMessage("1");
        String printed = Message.printMessages();
        assertTrue(printed.contains("Hi Mike, can you join us for dinner tonight"));
    }

    // ------------------------------------------------------------
    // TEST 7 – Read Stored Messages (JSON)
    // ------------------------------------------------------------
    @Test
    public void testReadStoredMessages() {
        msg1.SentMessage("3"); // store message
        String json = Message.readStoredMessages();
        assertTrue(json.contains("Hi Mike, can you join us for dinner tonight"));
    }

    // ------------------------------------------------------------
    // TEST 8 – Return Total Messages
    // ------------------------------------------------------------
    @Test
    public void testReturnTotalMessages() {
        msg1.SentMessage("1");
        assertEquals(Message.returnTotalMessages(), 1);
    }

    // ------------------------------------------------------------
    // TEST 9 – Static validation methods
    // ------------------------------------------------------------
    @Test
    public void testCheckRecipientCellStatic() {
        assertTrue(Message.checkRecipientCellStatic("+27718693002"));
        assertFalse(Message.checkRecipientCellStatic("0812345678"));
    }

    @Test
    public void testCheckMessageLengthStatic() {
        assertTrue(Message.checkMessageLengthStatic("Short message"));
        assertFalse(Message.checkMessageLengthStatic("A".repeat(300)));
    }

    // ------------------------------------------------------------
    // TEST 10 – Message Details Output
    // ------------------------------------------------------------
    @Test
    public void testGetMessageDetails() {
        String details = msg1.getMessageDetails();
        assertTrue(details.contains("Message ID"));
        assertTrue(details.contains("Recipient"));
        assertTrue(details.contains("Message Hash"));
    }
}

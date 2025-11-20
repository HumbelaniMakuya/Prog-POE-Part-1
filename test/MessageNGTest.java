/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Unit tests for the Message class aligned to the current implementation.
 * - Validates ID length, recipient format, and message length
 * - Verifies hash format per spec
 * - Confirms JSON storage contains expected fields
 * - Checks SentMessage actions (send/disregard/store)
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {

    private static final String JSON_FILE = "stored_messages.json";

    @BeforeMethod
    public void cleanJsonFile() {
        File f = new File(JSON_FILE);
        if (f.exists()) {
            assertTrue(f.delete(), "Failed to clear stored_messages.json before test.");
        }
    }

    // Reusable messages
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
            "08575975889", // invalid: missing +27
            "Hi Keegan, did you receive the payment?",
            2
    );

    // ------------------------------------------------------------
    // TEST 1 – Message ID length (<= 10)
    // ------------------------------------------------------------
    @Test
    public void testCheckMessageID() {
        assertTrue(msg1.checkMessageID(), "Message ID should be 10 characters or fewer.");
        assertEquals(msg1.getMessageID().length(), 10, "Message ID should be zero-padded to 10 digits.");
    }

    // ------------------------------------------------------------
    // TEST 2 – Recipient cell validation
    // ------------------------------------------------------------
    @Test
    public void testCheckRecipientCell_Success() {
        assertEquals(msg1.checkRecipientCell(), 1, "Valid +27 recipient should pass.");
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        assertEquals(msg2.checkRecipientCell(), 0, "Invalid recipient should fail (+27 followed by 9 digits required).");
    }

    // ------------------------------------------------------------
    // TEST 3 – Message length validation and feedback
    // ------------------------------------------------------------
    @Test
    public void testCheckMessageLength_Success() {
        assertTrue(msg1.checkMessageLength(), "Short messages should be valid.");
        assertEquals(msg1.getMessageLengthFeedback(), "Message ready to send.");
    }

    @Test
    public void testCheckMessageLength_Failure() {
        String longText = "A".repeat(260);
        Message longMsg = new Message("user3", "Test User", "+27711111111", longText, 3);
        assertFalse(longMsg.checkMessageLength(), "Messages over 250 chars should be invalid.");
        int excess = longText.length() - 250;
        assertEquals(longMsg.getMessageLengthFeedback(),
                "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    // ------------------------------------------------------------
    // TEST 4 – Create Message Hash format
    // ------------------------------------------------------------
    @Test
    public void testCreateMessageHash_FormatAndContent() {
        String id = msg1.getMessageID();
        String hash = msg1.createMessageHash();

        String prefix = id.substring(0, 2) + ":" + msg1.getMessageNumber() + ":";
        assertTrue(hash.startsWith(prefix), "Hash should start with first two ID digits + ':' + messageNumber + ':'");

        String content = msg1.getMessageContent();
        String[] words = content.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        String expectedSuffix = (firstWord + lastWord).toUpperCase();

        assertEquals(hash, prefix + expectedSuffix, "Hash suffix should match FIRST+LAST words uppercase.");
    }

    // ------------------------------------------------------------
    // TEST 5 – SentMessage actions
    // ------------------------------------------------------------
    @Test
    public void testSentMessage_Send_WritesJson() throws Exception {
        String result = msg1.SentMessage("1");
        assertTrue(result.startsWith("Message sent successfully."), "Send action should confirm success.");

        File f = new File(JSON_FILE);
        assertTrue(f.exists(), "JSON file should be created after sending.");

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            assertNotNull(line, "JSON file should have at least one line.");
            assertTrue(line.contains("\"messageID\":\"" + msg1.getMessageID() + "\""));
            assertTrue(line.contains("\"messageHash\":\"" + msg1.getMessageHash() + "\""));
            assertTrue(line.contains("\"recipientCell\":\"" + msg1.getRecipientCell() + "\""));
            assertTrue(line.contains("\"messageContent\":\"" + msg1.getMessageContent().replace("\"", "\\\"") + "\""));
        }
    }

    @Test
    public void testSentMessage_Disregard_NoJsonWrite() {
        String result = msg2.SentMessage("2");
        assertEquals(result, "Message disregarded.");

        File f = new File(JSON_FILE);
        assertFalse(f.exists(), "Disregard should not write to JSON.");
    }

    @Test
    public void testSentMessage_Store_WritesJson() throws Exception {
        Message msg3 = new Message("user3", "John Doe", "+27734567890", "Testing store message", 3);
        String result = msg3.SentMessage("3");
        assertTrue(result.startsWith("Message stored for later."), "Store action should confirm storage.");

        File f = new File(JSON_FILE);
        assertTrue(f.exists(), "JSON file should be created after storing.");

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            assertNotNull(line, "JSON file should have content after storing.");
            assertTrue(line.contains("\"messageID\":\"" + msg3.getMessageID() + "\""));
            assertTrue(line.contains("\"messageHash\":\"" + msg3.getMessageHash() + "\""));
        }
    }

    // ------------------------------------------------------------
    // TEST 6 – Message details output
    // ------------------------------------------------------------
    @Test
    public void testGetMessageDetails() {
        String details = msg1.getMessageDetails();
        assertTrue(details.contains("MessageID:"), "Details should include MessageID label.");
        assertTrue(details.contains("Message Hash:"), "Details should include Message Hash label.");
        assertTrue(details.contains("Recipient:"), "Details should include Recipient label.");
        assertTrue(details.contains("Message:"), "Details should include Message label.");
    }

    // ------------------------------------------------------------
    // TEST 7 – JSON constructor preserves ID/hash
    // ------------------------------------------------------------
    @Test
    public void testJsonConstructorPreservesIdAndHash() {
        String fixedId = "1234567890";
        String fixedHash = "12:99:HELLOWORLD";
        Message loaded = new Message("userX", "X Y", "+27700000000", "Hello world", 99, fixedId, fixedHash);

        assertEquals(loaded.getMessageID(), fixedId);
        assertEquals(loaded.getMessageHash(), fixedHash);
    }

    // ------------------------------------------------------------
    // TEST 8 – setMessageID recomputes hash
    // ------------------------------------------------------------
    @Test
    public void testSetMessageIDRecomputesHash() {
        Message m = new Message("u", "Name", "+27712345678", "alpha omega", 5);
        String oldHash = m.getMessageHash();

        m.setMessageID("9988776655");
        String newHash = m.getMessageHash();

        assertNotEquals(newHash, oldHash);
        assertTrue(newHash.startsWith("99:" + m.getMessageNumber() + ":"));
    }

    // ------------------------------------------------------------
    // TEST 9 – setMessageContent recomputes hash
    // ------------------------------------------------------------
    @Test
    public void testSetMessageContentRecomputesHash() {
        Message m = new Message("u", "Name", "+27712345678", "alpha omega", 5);
        String oldHash = m.getMessageHash();

        m.setMessageContent("first last");
        String newHash = m.getMessageHash();

        assertNotEquals(newHash, oldHash);
        assertTrue(newHash.endsWith("FIRSTLAST"));
    }
}

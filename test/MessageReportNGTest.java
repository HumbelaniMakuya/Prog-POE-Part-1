/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class MessageReportNGTest {

    private List<Message> sentMessages;

    @BeforeMethod
    public void setup() {
        sentMessages = new ArrayList<>();
        sentMessages.add(new Message("user1", "Kyle Smith", "+27718693002",
                "Hi Mike, can you join us for dinner tonight", 1));
        sentMessages.add(new Message("user2", "Keegan Smith", "+27734567890",
                "Hi Keegan, did you receive the payment?", 2));
        sentMessages.add(new Message("user3", "John Doe", "+27798765432",
                "This is a longer message to test longest message functionality", 3));
    }

    // ------------------------------------------------------------
    // TEST 1 – Recently sent messages
    // ------------------------------------------------------------
    @Test
    public void testRecentlySentMessages() {
        // last message should be John Doe’s
        Message last = sentMessages.get(sentMessages.size() - 1);
        assertEquals(last.getRecipientCell(), "+27798765432");
        assertTrue(last.getMessageContent().contains("longer message"));
    }

    // ------------------------------------------------------------
    // TEST 2 – Display sender + recipient
    // ------------------------------------------------------------
    @Test
    public void testSenderRecipientPairs() {
        Message m = sentMessages.get(0);
        assertEquals(m.getSenderFullName(), "Kyle Smith");
        assertEquals(m.getRecipientCell(), "+27718693002");
    }

    // ------------------------------------------------------------
    // TEST 3 – Longest message
    // ------------------------------------------------------------
    @Test
    public void testLongestMessage() {
        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = msg;
            }
        }
        assertTrue(longest.getMessageContent().contains("longest message functionality"));
    }

    // ------------------------------------------------------------
    // TEST 4 – Search by Message ID
    // ------------------------------------------------------------
    @Test
    public void testSearchByMessageID() {
        String id = sentMessages.get(1).getMessageID();
        Message found = null;
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(id)) {
                found = msg;
                break;
            }
        }
        assertNotNull(found);
        assertEquals(found.getSenderFullName(), "Keegan Smith");
    }

    // ------------------------------------------------------------
    // TEST 5 – Search by recipient
    // ------------------------------------------------------------
    @Test
    public void testSearchByRecipient() {
        String recipient = "+27718693002";
        List<Message> results = new ArrayList<>();
        for (Message msg : sentMessages) {
            if (msg.getRecipientCell().equals(recipient)) {
                results.add(msg);
            }
        }
        assertEquals(results.size(), 1);
        assertEquals(results.get(0).getSenderFullName(), "Kyle Smith");
    }

    // ------------------------------------------------------------
    // TEST 6 – Delete by hash
    // ------------------------------------------------------------
    @Test
    public void testDeleteByHash() {
        String hash = sentMessages.get(0).getMessageHash();
        boolean deleted = sentMessages.removeIf(m -> m.getMessageHash().equals(hash));
        assertTrue(deleted, "Message should be deleted by hash.");
        assertEquals(sentMessages.size(), 2);
    }

    // ------------------------------------------------------------
    // TEST 7 – Full report contains all messages
    // ------------------------------------------------------------
    @Test
    public void testFullMessageReport() {
        StringBuilder report = new StringBuilder();
        for (Message msg : sentMessages) {
            report.append("Message ID: ").append(msg.getMessageID()).append("\n")
                  .append("Message Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("Recipient: ").append(msg.getRecipientCell()).append("\n")
                  .append("Message: ").append(msg.getMessageContent()).append("\n\n");
        }
        String output = report.toString();
        assertTrue(output.contains("Message ID"));
        assertTrue(output.contains("Recipient"));
        assertTrue(output.contains("Message Hash"));
    }
}

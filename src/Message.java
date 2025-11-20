/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Message class for creating, validating, hashing, and storing messages.
 */
public class Message {
    private final String senderUsername;
    private final String senderFullName;
    private final String recipientCell;
    private String messageContent;
    private final int messageNumber;

    private String messageID;
    private String messageHash;

    private static final String JSON_FILE = "stored_messages.json";

    // Constructor for new messages
    public Message(String senderUsername, String senderFullName, String recipientCell,
                   String messageContent, int messageNumber) {
        this.senderUsername = senderUsername;
        this.senderFullName = senderFullName;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageNumber = messageNumber;
        this.messageID = generate10DigitID();
        this.messageHash = createMessageHash();
    }

    // Constructor for loading from JSON (preserves ID/hash)
    public Message(String senderUsername, String senderFullName, String recipientCell,
                   String messageContent, int messageNumber, String messageID, String messageHash) {
        this.senderUsername = senderUsername;
        this.senderFullName = senderFullName;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageNumber = messageNumber;
        this.messageID = (messageID == null || messageID.isEmpty()) ? generate10DigitID() : messageID;
        this.messageHash = (messageHash == null || messageHash.isEmpty()) ? createMessageHash() : messageHash;
    }

    // Generate random 10-digit ID
    private String generate10DigitID() {
        Random rnd = new Random();
        long n = Math.abs(rnd.nextLong()) % 10_000_000_000L;
        return String.format("%010d", n);
    }

    // Validation methods
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public int checkRecipientCell() {
        return (recipientCell != null && recipientCell.matches("^\\+27\\d{9}$")) ? 1 : 0;
    }

    // Hash creation: first 2 digits of ID + ":" + messageNumber + ":" + FIRST+LAST words uppercase
    public String createMessageHash() {
        String id = (messageID == null) ? "" : messageID;
        String prefix = (id.length() >= 2) ? id.substring(0, 2) : "00";
        String trimmed = (messageContent == null) ? "" : messageContent.trim();
        if (trimmed.isEmpty()) return prefix + ":" + messageNumber + ":";
        String[] words = trimmed.split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return prefix + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
    }

    public boolean checkMessageLength() {
        return messageContent != null && messageContent.length() <= 250;
    }

    public String getMessageLengthFeedback() {
        if (checkMessageLength()) return "Message ready to send.";
        int excess = (messageContent == null) ? 0 : (messageContent.length() - 250);
        return "Message exceeds 250 characters by " + excess + ", please reduce size.";
    }

    // Send/Disregard/Store actions
    public String SentMessage(String action) {
        switch (action) {
            case "1":
                if (!checkMessageLength()) return getMessageLengthFeedback();
                storeMessageJSON();
                return "Message sent successfully.\nID: " + messageID + "\nHash: " + messageHash;
            case "2":
                return "Message disregarded.";
            case "3":
                storeMessageJSON();
                return "Message stored for later.\nID: " + messageID + "\nHash: " + messageHash;
            default:
                return "Invalid action.";
        }
    }

    // Store message in JSON file
    private void storeMessageJSON() {
        String jsonString = "{"
                + "\"senderUsername\":\"" + escapeJson(senderUsername) + "\","
                + "\"senderFullName\":\"" + escapeJson(senderFullName) + "\","
                + "\"recipientCell\":\"" + escapeJson(recipientCell) + "\","
                + "\"messageContent\":\"" + escapeJson(messageContent) + "\","
                + "\"messageNumber\":" + messageNumber + ","
                + "\"messageID\":\"" + escapeJson(messageID) + "\","
                + "\"messageHash\":\"" + escapeJson(messageHash) + "\""
                + "}";
        try (FileWriter writer = new FileWriter(JSON_FILE, true)) {
            writer.write(jsonString + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Failed to store message: " + e.getMessage());
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // Details output
    public String getMessageDetails() {
        return "MessageID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipientCell + "\n" +
               "Message: " + messageContent;
    }

    // Getters
    public String getSenderUsername() { return senderUsername; }
    public String getSenderFullName() { return senderFullName; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageContent() { return messageContent; }
    public int getMessageNumber() { return messageNumber; }
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }

    // ------------------------------------------------------------
    // Setter methods (added for tests)
    // ------------------------------------------------------------
    public void setMessageContent(String content) {
        this.messageContent = content;
        this.messageHash = createMessageHash(); // recompute hash
    }

    public void setMessageID(String id) {
        this.messageID = id;
        this.messageHash = createMessageHash(); // recompute hash
    }
}

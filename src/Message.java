/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Message {
    private String senderUsername;
    private String senderFullName;
    private String recipientCell;
    private String messageContent;
    private int messageNumber;
    private String messageID; // Auto-generated

    // Static variables to track sent messages and total
    private static String allMessages = "";
    private static int totalMessages = 0;

    // File for JSON storage
    private static final String JSON_FILE = "stored_messages.json";

    // ----------------------------
    // Constructor
    // ----------------------------
    public Message(String senderUsername, String senderFullName, String recipientCell,
                   String messageContent, int messageNumber) {
        this.senderUsername = senderUsername;
        this.senderFullName = senderFullName;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageNumber = messageNumber;
        this.messageID = "MSG" + messageNumber;
    }

    // ----------------------------
    // 1️⃣ checkMessageID()
    // ----------------------------
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // ----------------------------
    // 2️⃣ checkRecipientCell() - +27 format
    // ----------------------------
    public int checkRecipientCell() {
        return (recipientCell != null && recipientCell.matches("^\\+27\\d{9}$")) ? 1 : 0;
    }

    // ----------------------------
    // 3️⃣ createMessageHash()
    // ----------------------------
    public String createMessageHash() {
        int hash = (senderUsername + recipientCell + messageContent).hashCode();
        return "MSG-" + Math.abs(hash);
    }

    // ----------------------------
    // 4️⃣ SentMessage() - prevents >250 chars
    // ----------------------------
    public String SentMessage(String action) {
        switch (action) {
            case "1": // Send
                if (!checkMessageLength()) {
                    return "❌ Message too long. Max 250 characters.";
                }
                totalMessages++;
                allMessages += "Message " + totalMessages + ": " + messageContent + "\n";
                storeMessageJSON();
                return "Message successfully sent.";
            case "2": // Disregard
                return "Message disregarded.";
            case "3": // Store
                storeMessageJSON();
                return "Message successfully stored.";
            default:
                return "Invalid action.";
        }
    }

    // ----------------------------
    // Store message in JSON file
    // ----------------------------
    private void storeMessageJSON() {
        String jsonString = "{"
            + "\"senderUsername\":\"" + escapeJson(senderUsername) + "\","
            + "\"senderFullName\":\"" + escapeJson(senderFullName) + "\","
            + "\"recipientCell\":\"" + escapeJson(recipientCell) + "\","
            + "\"messageContent\":\"" + escapeJson(messageContent) + "\","
            + "\"messageNumber\":" + messageNumber + ","
            + "\"messageID\":\"" + messageID + "\""
            + "}";
        try (FileWriter writer = new FileWriter(JSON_FILE, true)) {
            writer.write(jsonString + "\n");
        } catch (IOException e) {
            System.out.println("Failed to store message: " + e.getMessage());
        }
    }

    // ----------------------------
    // Read stored messages
    // ----------------------------
    public static String readStoredMessages() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(JSON_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            return "No stored messages found.";
        }
        return sb.toString();
    }

    private String escapeJson(String str) {
        return str.replace("\"", "\\\"");
    }

    // ----------------------------
    // 5️⃣ printMessages()
    // ----------------------------
    public static String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        return allMessages;
    }

    // ----------------------------
    // 6️⃣ returnTotalMessages()
    // ----------------------------
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // ----------------------------
    // Optional: message length check
    // ----------------------------
    public boolean checkMessageLength() {
        return messageContent.length() <= 250;
    }

    public String getMessageLengthFeedback() {
        if (checkMessageLength()) {
            return "Message ready to send.";
        } else {
            int excess = messageContent.length() - 250;
            return "❌ Message too long. Max 250 characters. Reduce by " + excess + " characters.";
        }
    }

    // ----------------------------
    // Optional: message details
    // ----------------------------
    public String getMessageDetails() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + recipientCell + "\n" +
               "Message: " + messageContent;
    }

    // ----------------------------
    // Static validation methods
    // ----------------------------
    public static boolean checkRecipientCellStatic(String recipientCell) {
        return recipientCell != null && recipientCell.matches("^\\+27\\d{9}$");
    }

    public static boolean checkMessageLengthStatic(String messageContent) {
        return messageContent != null && messageContent.length() <= 250;
    }
}

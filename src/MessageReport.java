/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.*;
import javax.swing.JOptionPane;

/**
 * Reporting and utilities for Part 3.
 * Delegated from LoginApp to keep the main class clean.
 */
public class MessageReport {

    public static void showRecentlySentMessages(List<Message> sentMessages, int n) {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages yet.");
            return;
        }
        int start = Math.max(0, sentMessages.size() - n);
        StringBuilder sb = new StringBuilder("=== RECENTLY SENT MESSAGES ===\n");
        for (int i = start; i < sentMessages.size(); i++) {
            Message m = sentMessages.get(i);
            sb.append("[").append(i + 1).append("] ")
              .append(m.getRecipientCell()).append(" | ")
              .append(m.getMessageContent()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void displaySendersAndRecipients(List<Message> sentMessages, String senderFullName) {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== SENDER AND RECIPIENT LIST ===\n");
        for (Message msg : sentMessages) {
            sb.append("Sender: ").append(senderFullName)
              .append(" | Recipient: ").append(msg.getRecipientCell()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void displayLongestMessage(List<Message> sentMessages) {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages.");
            return;
        }
        Message longest = sentMessages.get(0);
        for (Message msg : sentMessages) {
            if (msg.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = msg;
            }
        }
        JOptionPane.showMessageDialog(null, "Longest Sent Message:\n" + longest.getMessageContent());
    }

    public static void searchByMessageID(List<Message> sentMessages, String searchID) {
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(searchID)) {
                JOptionPane.showMessageDialog(null,
                        "Recipient: " + msg.getRecipientCell()
                                + "\nMessage: " + msg.getMessageContent());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    public static void searchMessagesForRecipient(List<Message> sentMessages, String cellphone) {
        StringBuilder result = new StringBuilder();
        for (Message msg : sentMessages) {
            if (msg.getRecipientCell().equals(cellphone)) {
                result.append("- ").append(msg.getMessageContent()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null,
                result.length() == 0 ? "No messages for this recipient." : result.toString());
    }

    public static void deleteMessageUsingHash(List<Message> sentMessages, String hash) {
        Iterator<Message> iterator = sentMessages.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            if (msg.getMessageHash().equals(hash)) {
                String deletedMsg = msg.getMessageContent();
                iterator.remove();
                JOptionPane.showMessageDialog(null, "Message \"" + deletedMsg + "\" successfully deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No sent message found with that hash.");
    }

    public static void displayMessageReport(List<Message> sentMessages) {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages.");
            return;
        }
        StringBuilder report = new StringBuilder("===== SENT MESSAGE REPORT =====\n");
        for (Message msg : sentMessages) {
            report.append("Message ID: ").append(msg.getMessageID()).append("\n")
                  .append("Message Hash: ").append(msg.getMessageHash()).append("\n")
                  .append("Recipient: ").append(msg.getRecipientCell()).append("\n")
                  .append("Message: ").append(msg.getMessageContent()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
import javax.swing.JOptionPane;

public class LoginApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to the registration portal ===");

        // -------------------------------
        // Registration
        // -------------------------------
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Username
        String username;
        while (true) {
            System.out.print("Create a username (must contain '_' and <= 5 characters): ");
            username = scanner.nextLine();
            if (User.checkUserNameStatic(username)) {
                System.out.println("✅ Username successfully captured.");
                break;
            } else {
                System.out.println("❌ Invalid username. Must contain '_' and <= 5 characters.");
            }
        }

        // Password
        String password;
        while (true) {
            System.out.print("Create a password (min 8 chars, 1 uppercase, 1 number, 1 special char): ");
            password = scanner.nextLine();
            if (User.checkPasswordComplexityStatic(password)) {
                System.out.println("✅ Password successfully captured.");
                break;
            } else {
                System.out.println("❌ Invalid password. Try again.");
            }
        }

        // Cell number
        String cellNumber;
        while (true) {
            System.out.print("Enter your cell number (must start with +27): ");
            cellNumber = scanner.nextLine();
            if (User.checkCellPhoneNumberStatic(cellNumber)) {
                System.out.println("✅ Cell number successfully added.");
                break;
            } else {
                System.out.println("❌ Invalid cell number. Must start with +27.");
            }
        }

        User user = new User(username, password, cellNumber, firstName, lastName);

        // -------------------------------
        // Strict login
        // -------------------------------
        boolean loggedIn = false;
        System.out.println("\n=== Login ===");

        // Username first
        while (true) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();
            if (inputUsername.equals(user.getUsername())) {
                System.out.println("✅ Username is correct.");
                break;
            } else {
                System.out.println("❌ Incorrect username. Try again.");
            }
        }

        // Then password
        while (!loggedIn) {
            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();
            if (inputPassword.equals(user.getPassword())) {
                loggedIn = true;
                System.out.println("✅ Login successful.");
            } else {
                System.out.println("❌ Incorrect password. Try again.");
            }
        }

        // -------------------------------
        // Welcome
        // -------------------------------
        String welcomeMsg = "Welcome to QuickChat.";
        System.out.println("\n" + welcomeMsg);
        JOptionPane.showMessageDialog(null, welcomeMsg, "Welcome", JOptionPane.INFORMATION_MESSAGE);

        // -------------------------------
        // Message limit
        // -------------------------------
        int messageLimit = 0;
        while (true) {
            System.out.print("How many messages would you like to send today? ");
            try {
                messageLimit = Integer.parseInt(scanner.nextLine());
                if (messageLimit > 0) break;
            } catch (Exception e) {}
            System.out.println("❌ Please enter a valid positive number.");
            JOptionPane.showMessageDialog(null, "❌ Please enter a valid positive number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }

        int messageCount = 0;

        // -------------------------------
        // Menu loop
        // -------------------------------
        while (true) {
            String menu = "Choose an option:\n1) Send Messages\n2) Show Recently Sent Messages\n3) Quit";
            System.out.println("\n" + menu);

            String choice = JOptionPane.showInputDialog(null, menu, "QuickChat Menu", JOptionPane.QUESTION_MESSAGE);
            if (choice == null) choice = "3"; // treat cancel as quit

            System.out.println("You selected option: " + choice);

            switch (choice) {
                case "1":
                    if (messageCount >= messageLimit) {
                        String limitMsg = "🚫 You’ve reached your message limit for today.";
                        System.out.println(limitMsg);
                        JOptionPane.showMessageDialog(null, limitMsg, "Limit Reached", JOptionPane.WARNING_MESSAGE);
                        break;
                    }

                    // Recipient
                    String recipient;
                    while (true) {
                        String recipientPrompt = "Enter recipient cell number (+27xxxxxxxxx):";
                        System.out.println(recipientPrompt);
                        recipient = JOptionPane.showInputDialog(null, recipientPrompt, "Recipient", JOptionPane.QUESTION_MESSAGE);
                        if (recipient == null) break;
                        System.out.println("Recipient entered: " + recipient);
                        if (Message.checkRecipientCellStatic(recipient)) break;
                        String msg = "❌ Invalid cell number. Try again.";
                        System.out.println(msg);
                        JOptionPane.showMessageDialog(null, msg, "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    }
                    if (recipient == null) break;

                    // Message content
                    String messageContent;
                    while (true) {
                        String messagePrompt = "Enter your message (max 250 characters):";
                        System.out.println(messagePrompt);
                        messageContent = JOptionPane.showInputDialog(null, messagePrompt, "Message Content", JOptionPane.QUESTION_MESSAGE);
                        if (messageContent == null) break;
                        System.out.println("Message entered: " + messageContent);
                        if (Message.checkMessageLengthStatic(messageContent)) break;
                        String msg = "❌ Message too long. Max 250 characters.";
                        System.out.println(msg);
                        JOptionPane.showMessageDialog(null, msg, "Message Too Long", JOptionPane.WARNING_MESSAGE);
                    }
                    if (messageContent == null) break;

                    Message message = new Message(username, user.getFullName(), recipient, messageContent, messageCount + 1);

                    // Show details
                    String messageDetails = message.getMessageDetails();
                    System.out.println("\n📨 Message Details:\n" + messageDetails);
                    JOptionPane.showMessageDialog(null, messageDetails, "📨 Message Details", JOptionPane.INFORMATION_MESSAGE);

                    // Action
                    String action;
                    while (true) {
                        String actionMenu = "Choose what to do with this message:\n1) Send Message\n2) Disregard Message\n3) Store Message for later";
                        System.out.println(actionMenu);
                        action = JOptionPane.showInputDialog(null, actionMenu, "Message Action", JOptionPane.QUESTION_MESSAGE);
                        if (action == null) break;
                        action = action.trim();
                        System.out.println("You selected action: " + action);
                        if (action.equals("1") || action.equals("2") || action.equals("3")) break;
                        String msg = "❌ Invalid option. Enter 1, 2, or 3.";
                        System.out.println(msg);
                        JOptionPane.showMessageDialog(null, msg, "Invalid Action", JOptionPane.WARNING_MESSAGE);
                    }
                    if (action == null) break;

                    String result = message.SentMessage(action);
                    System.out.println(result);
                    JOptionPane.showMessageDialog(null, result, "Message Status", JOptionPane.INFORMATION_MESSAGE);

                    if (result.equals("Message successfully sent.")) {
                        messageCount++;
                    }
                    break;

                case "2":
                    // Placeholder for future feature
                    String comingSoon = "Coming Soon.";
                    System.out.println(comingSoon);
                    JOptionPane.showMessageDialog(null, comingSoon, "Feature Pending", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case "3":
                    String goodbye = "Goodbye, " + firstName + "! Total messages sent: " + messageCount;
                    System.out.println(goodbye);
                    JOptionPane.showMessageDialog(null, goodbye, "Exit", JOptionPane.INFORMATION_MESSAGE);
                    scanner.close();
                    return;

                default:
                    String invalid = "❌ Invalid option. Choose 1, 2, or 3.";
                    System.out.println(invalid);
                    JOptionPane.showMessageDialog(null, invalid, "Invalid Choice", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}

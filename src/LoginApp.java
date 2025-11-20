/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoginApp {
    static Scanner scanner = new Scanner(System.in);

    static List<Message> sentMessages = new ArrayList<>();
    static List<Message> disregardedMessages = new ArrayList<>();
    static List<Message> storedMessages = new ArrayList<>();

    // Dual I/O helpers (used only after login)
    public static String getInput(String prompt) {
        String input = JOptionPane.showInputDialog(null, prompt);
        if (input != null && !input.trim().isEmpty()) {
            return input.trim();
        }
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
        System.out.println(message);
    }

    public static void main(String[] args) {
        // -------------------------------
        // Registration (console only)
        // -------------------------------
        System.out.println("=== Welcome to the registration portal ===");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine().trim();

        String username;
        while (true) {
            System.out.print("Create a username (must contain '_' and <= 5 characters): ");
            username = scanner.nextLine().trim();
            if (User.checkUserNameStatic(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Invalid username format.");
            }
        }

        String password;
        while (true) {
            System.out.print("Create a password (min 8 chars, include uppercase, lowercase, number, special char): ");
            password = scanner.nextLine();
            if (User.checkPasswordComplexityStatic(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Invalid password format.");
            }
        }

        String cellNumber;
        while (true) {
            System.out.print("Enter your cell number (+27XXXXXXXXX): ");
            cellNumber = scanner.nextLine();
            if (User.checkCellPhoneNumberStatic(cellNumber)) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Invalid cell number format.");
            }
        }

        User user = new User(username, password, cellNumber, firstName, lastName);

        // -------------------------------
        // Login (console only)
        // -------------------------------
        System.out.println("\n=== Login ===");
        while (true) {
            System.out.print("Enter username: ");
            String iu = scanner.nextLine();
            System.out.print("Enter password: ");
            String ip = scanner.nextLine();
            if (user.loginUser(iu, ip)) {
                System.out.println("Welcome " + user.getFirstName() + "," + user.getLastName() + " it is great to see you again.");
                // First popup after login
                showMessage("Login successful! Welcome " + user.getFullName());
                break;
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }
        }

        // -------------------------------
        // ChatApp (dual console + popups)
        // -------------------------------
        showMessage("Welcome to QuickChat.");

        int messageLimit = 0;
        while (true) {
            try {
                messageLimit = Integer.parseInt(getInput("How many messages would you like to send today?"));
                if (messageLimit > 0) break;
            } catch (Exception ignored) {}
            showMessage("Please enter a valid positive number.");
        }

        int messageCount = 0;

        // Menu loop
        while (true) {
            String menu = "Choose an option:\n"
                    + "1) Send Messages\n"
                    + "2) Show Recently Sent Messages\n"
                    + "3) Quit\n"
                    + "4) View sender + recipient\n"
                    + "5) Longest message\n"
                    + "6) Search by Message ID\n"
                    + "7) Search by recipient\n"
                    + "8) Delete message by hash\n"
                    + "9) Full Message Report\n"
                    + "10) Load Stored Messages (JSON)";
            String choice = getInput(menu);

            switch (choice) {
                case "1": {
                    if (messageCount >= messageLimit) {
                        showMessage("You’ve reached your message limit for today.");
                        break;
                    }
                    String recipient = getInput("Enter recipient cell number (+27XXXXXXXXX):");
                    if (!recipient.matches("^\\+27\\d{9}$")) {
                        showMessage("Invalid recipient number.");
                        break;
                    }
                    String messageContent = getInput("Enter your message (max 250 characters):");
                    if (messageContent.length() > 250) {
                        showMessage("Message too long. Please keep under 250 characters.");
                        break;
                    }
                    Message message = new Message(user.getUsername(), user.getFullName(), recipient, messageContent, messageCount + 1);
                    showMessage(message.getMessageDetails());
                    String action = getInput("Choose action:\n1) Send\n2) Disregard\n3) Store");
                    showMessage(message.SentMessage(action));
                    if (action.equals("1")) { sentMessages.add(message); messageCount++; }
                    else if (action.equals("2")) { disregardedMessages.add(message); }
                    else if (action.equals("3")) { storedMessages.add(message); }
                    break;
                }
                case "2": MessageReport.showRecentlySentMessages(sentMessages, 5); break;
                case "3": showMessage("Goodbye, " + user.getFirstName() + "! Total messages sent: " + messageCount); return;
                case "4": MessageReport.displaySendersAndRecipients(sentMessages, user.getFullName()); break;
                case "5": MessageReport.displayLongestMessage(sentMessages); break;
                case "6": MessageReport.searchByMessageID(sentMessages, getInput("Enter Message ID:")); break;
                case "7": MessageReport.searchMessagesForRecipient(sentMessages, getInput("Enter recipient number:")); break;
                case "8": MessageReport.deleteMessageUsingHash(sentMessages, getInput("Enter Message Hash:")); break;
                case "9": MessageReport.displayMessageReport(sentMessages); break;
                case "10": loadStoredMessagesFromJSON(); break;
                default: showMessage("Invalid option. Choose 1–10.");
            }
        }
    }

    // JSON loading (unchanged, uses showMessage for output)
    public static void loadStoredMessagesFromJSON() {
        int loaded = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("stored_messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, String> obj = parseJsonLine(line);
                Message m = new Message(
                        obj.getOrDefault("senderUsername", ""),
                        obj.getOrDefault("senderFullName", ""),
                        obj.getOrDefault("recipientCell", ""),
                        obj.getOrDefault("messageContent", ""),
                        parseIntSafe(obj.get("messageNumber"), 0),
                        obj.getOrDefault("messageID", ""),
                        obj.getOrDefault("messageHash", "")
                );
                storedMessages.add(m);
                loaded++;
            }
            showMessage("Loaded " + loaded + " stored messages from JSON.");
        } catch (Exception e) {
            showMessage("No stored messages found or error reading file.");
        }
    }

    private static Map<String, String> parseJsonLine(String json) {
        Map<String, String> map = new HashMap<>();
        if (json == null) return map;
        String trimmed = json.trim();
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        }
        String[] pairs = trimmed.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String p : pairs) {
            String[] kv = p.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", 2);
            if (kv.length == 2) {
                String key = kv[0].trim().replaceAll("^\"|\"$", "");
                String val = kv[1].trim();
                if (val.startsWith("\"") && val.endsWith("\"")) {
                    val = val.substring(1, val.length() - 1);
                }
                val = val.replace("\\\"", "\"").replace("\\\\", "\\");
                map.put(key, val);
            }
        }
        return map;
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_lab
 */
import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to the registration portal ===");

        // Collect user's first and last name
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Collect and validate username
        String username;
        while (true) {
            System.out.print("Create a username (must contain '_' and be <= 5 characters): ");
            username = scanner.nextLine();
            User tempUser = new User(username, "", "", "", "");
            if (tempUser.isUsernameValid()) {
                break;
            }
            System.out.println("Username is not correctly formatted. Please ensure it contains an underscore and is no more than five characters.");
        }

        // Collect and validate password
        String password;
        while (true) {
            System.out.print("Create a password (min 8 chars, 1 uppercase, 1 number, 1 special char): ");
            password = scanner.nextLine();
            User tempUser = new User(username, password, "", "", "");
            if (tempUser.isPasswordComplex()) {
                break;
            }
            System.out.println("Password is not correctly formatted. Please ensure it has at least 8 characters, one uppercase letter, one number, and one special character.");
        }

        // Collect and validate cell number
        String cellNumber;
        while (true) {
            System.out.print("Enter your cell number (must start with +27): ");
            cellNumber = scanner.nextLine();
            User tempUser = new User(username, password, cellNumber, "", "");
            if (tempUser.isCellNumberValid()) {
                break;
            }
            System.out.println("Cell phone number incorrectly formatted. It must start with +27.");
        }

        // Create a User object with validated details
        User user = new User(username, password, cellNumber, firstName, lastName);
        System.out.println("Hey " + firstName + " " + lastName + ", welcome on board!");

        // Login section
        System.out.println("\n=== Login ===");

        String loginUsername;
        while (true) {
            System.out.print("Enter username: ");
            loginUsername = scanner.nextLine();
            if (loginUsername.equals(username)) {
                System.out.println("Username is correct.");
                break;
            }
            System.out.println("Username is incorrect. Try again.");
        }

        String loginPassword;
        while (true) {
            System.out.print("Enter password: ");
            loginPassword = scanner.nextLine();
            if (loginPassword.equals(password)) {
                System.out.println("Password is correct.");
                break;
            }
            System.out.println("Password is incorrect. Try again.");
        }

        // Final login check
        if (user.login(loginUsername, loginPassword)) {
            System.out.println(user.getWelcomeMessage(loginUsername,loginPassword));
        } else {
            System.out.println("Login failed. Please check your credentials and try again.");
        }
        //This is to avoid memory leaks
        scanner.close();
    }
}


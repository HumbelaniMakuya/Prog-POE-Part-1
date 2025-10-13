/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_lab
 */
public class User {
    private String username;
    private String password;
    private String cellNumber;
    private String firstName;
    private String lastName;

    public User(String username, String password, String cellNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Instance methods (keep your current ones)
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public String getUsernameFeedback() {
        return checkUserName() ? "Username successfully captured." :
               "Username is not correctly formatted. Please ensure it contains an underscore and is no more than five characters.";
    }

    public boolean checkPasswordComplexity() {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,}$");
    }

    public String getPasswordFeedback() {
        return checkPasswordComplexity() ? "Password successfully captured." :
               "Password is not correctly formatted. Please ensure it has at least 8 characters, one uppercase letter, one number, and one special character.";
    }

    public boolean checkCellPhoneNumber() {
        return cellNumber.matches("^\\+27\\d{9}$");
    }

    public String getCellNumberFeedback() {
        return checkCellPhoneNumber() ? "Cell phone number successfully added." :
               "Cell phone number incorrectly formatted. It must start with +27.";
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public String returnLoginStatus(String inputUsername, String inputPassword) {
        if (loginUser(inputUsername, inputPassword)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Login failed. Please check your credentials.";
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // ----------------------------
    // Static validation methods for JOptionPane flow
    // ----------------------------

    public static boolean checkUserNameStatic(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexityStatic(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,}$");
    }

    public static boolean checkCellPhoneNumberStatic(String cellNumber) {
        return cellNumber != null && cellNumber.matches("^\\+27\\d{9}$");
    }
}

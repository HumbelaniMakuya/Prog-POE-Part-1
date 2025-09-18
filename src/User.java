/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_lab
 */
// This class stores user details and handles validation and login logic
public class User {
    private String username;
    private String password;
    private String cellNumber;
    private String firstName;
    private String lastName;

    // This is used to  to initialise user details
    public User(String username, String password, String cellNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Checks if the username contains "_" and is no more than 5 characters
    public boolean isUsernameValid() {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks if the password is complex enough
    
    public boolean isPasswordComplex() {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,}$");
    }

    // Validates that the cell number meets stated requirements
    public boolean isCellNumberValid() {
        return cellNumber.matches("^\\+27\\d{9}$");
    }

    // Checks if the login credentials match the stored ones
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    // Returns a personalised welcome message
    public String getWelcomeMessage(String inputUsername, String inputPassword) {
    if (login(inputUsername, inputPassword)) {
        return "Welcome " + firstName + "  " + lastName + " great to have you back";
    } else {
        return "Incorrect username or password, please attempt again";
    }


    }
}

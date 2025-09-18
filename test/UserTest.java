/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserTest {

    private User user;

    @BeforeMethod
    public void setUp() {
        //      User setup 
        user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
    }

    // This section tests username validation
    @Test
    public void testValidUsername() {
        Assert.assertTrue(user.isUsernameValid());
    }

    @Test
    public void testInvalidUsernameTooLong() {
        user = new User("kyle_123", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(user.isUsernameValid());
    }

    @Test
    public void testInvalidUsernameMissingUnderscore() {
        user = new User("kyle", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(user.isUsernameValid());
    }

    // This section tests  password validation 
    @Test
    public void testValidPassword() {
        Assert.assertTrue(user.isPasswordComplex());
    }

    @Test
    public void testInvalidPasswordTooShort() {
        user = new User("ky_1", "Ch@1", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(user.isPasswordComplex());
    }

    @Test
    public void testInvalidPasswordMissingUppercase() {
        user = new User("ky_1", "ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(user.isPasswordComplex());
    }

    @Test
    public void testInvalidPasswordMissingSpecialChar() {
        user = new User("ky_1", "Chessecake99", "+27838968976", "Kyle", "Smith");
        Assert.assertFalse(user.isPasswordComplex());
    }

    // This section tests for Cell Number Validation 
    @Test
    public void testValidCellNumber() {
        Assert.assertTrue(user.isCellNumberValid());
    }

    @Test
    public void testInvalidCellNumberWrongPrefix() {
        user = new User("ky_1", "Ch&&sec@ke99", "0838968976", "Kyle", "Smith");
        Assert.assertFalse(user.isCellNumberValid());
    }

    @Test
    public void testInvalidCellNumberTooShort() {
        user = new User("ky_1", "Ch&&sec@ke99", "+2783", "Kyle", "Smith");
        Assert.assertFalse(user.isCellNumberValid());
    }

    //This is the  login validation test
    @Test
    public void testSuccessfulLogin() {
        Assert.assertTrue(user.login("ky_1", "Ch&&sec@ke99"));
    }

    @Test
    public void testFailedLoginWrongPassword() {
        Assert.assertFalse(user.login("ky_1", "WrongPass"));
    }

    @Test
    public void testFailedLoginWrongUsername() {
        Assert.assertFalse(user.login("wrong_user", "Ch&&sec@ke99"));
    }

    // Test for  welcome message 
    @Test
    public void testWelcomeMessageSuccess() {
        String expected = "Welcome Kyle  Smith great to have you back";
        Assert.assertEquals(user.getWelcomeMessage("ky_1", "Ch&&sec@ke99"), expected);
    }

    @Test
    public void testWelcomeMessageFailure() {
        String expected = "Incorrect username or password, please attempt again";
        Assert.assertEquals(user.getWelcomeMessage("wrong_user", "WrongPass"), expected);
    }
}

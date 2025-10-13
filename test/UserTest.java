

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 


/**
 *
 * @author RC_Student_Lab
 */
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class UserTest {

    @Test
    public void testCheckUserName() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexity() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumber() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testGetUsernameFeedback() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getUsernameFeedback(), "Username successfully captured.");
    }

    @Test
    public void testGetPasswordFeedback() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getPasswordFeedback(), "Password successfully captured.");
    }

    @Test
    public void testGetCellNumberFeedback() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getCellNumberFeedback(), "Cell phone number successfully added.");
    }

    @Test
    public void testLoginUser() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.loginUser("ky_1", "Ch&&sec@ke99"));
    }

    @Test
    public void testReturnLoginStatus() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.returnLoginStatus("ky_1", "Ch&&sec@ke99"), "Welcome Kyle Smith, it is great to see you again.");
    }

    @Test
    public void testGetFullName() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getFullName(), "Kyle Smith");
    }

    @Test
    public void testGetUsername() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getUsername(), "ky_1");
    }
}

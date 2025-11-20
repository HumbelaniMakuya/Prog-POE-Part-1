

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
        assertTrue(user.checkUserName(), "Username should contain '_' and be <= 5 characters.");
    }

    @Test
    public void testCheckPasswordComplexity() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.checkPasswordComplexity(), "Password should meet complexity requirements.");
    }

    @Test
    public void testCheckCellPhoneNumber() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.checkCellPhoneNumber(), "Cell number should be valid (+27 followed by 9 digits).");
    }

    @Test
    public void testLoginUserSuccess() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertTrue(user.loginUser("ky_1", "Ch&&sec@ke99"), "Login should succeed with correct credentials.");
    }

    @Test
    public void testLoginUserFailure() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertFalse(user.loginUser("wrong", "wrong"), "Login should fail with incorrect credentials.");
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        String expected = "Welcome Kyle,Smith it is great to see you again.";
        assertEquals(user.returnLoginStatus("ky_1", "Ch&&sec@ke99"), expected);
    }

    @Test
    public void testReturnLoginStatusFailure() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        String expected = "Username or password incorrect, please try again.";
        assertEquals(user.returnLoginStatus("wrong", "wrong"), expected);
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

    @Test
    public void testGetPassword() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getPassword(), "Ch&&sec@ke99");
    }

    @Test
    public void testGetCellNumber() {
        User user = new User("ky_1", "Ch&&sec@ke99", "+27838968976", "Kyle", "Smith");
        assertEquals(user.getCellNumber(), "+27838968976");
    }
}

package be.ucll.unit.model;

import be.ucll.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void givenValidValues_whenCreatingUser_thenUserIsCreatedWithThoseValues() {
        User user = new User("John Doe", 56, "john.doe@ucll.be", "john1234");

        assertEquals("John Doe", user.getName());
        assertEquals(56, user.getAge());
        assertEquals("john.doe@ucll.be", user.getEmail());
        assertEquals("john1234", user.getPassword());
    }

    @Test
    public void givenEmptyName_whenSettingName_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("", 56, "john.doe@ucll.be", "john1234"));
       
        Assertions.assertEquals("Name is required", ex.getMessage());
    }

    @Test
    public void givenNegativeAge_whenSettingAge_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", -56, "john.doe@ucll.be", "john1234"));

        Assertions.assertEquals("Age must be a positive integer between 0 and 101", ex.getMessage());
    }

    @Test
    public void givenAgeLargerThan101_whenSettingAge_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 120, "john.doe@ucll.be", "john1234"));

        Assertions.assertEquals("Age must be a positive integer between 0 and 101", ex.getMessage());
    }

    @Test
    public void givenInvalidEmailNull_whenSettingEmail_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 12, null, "john1234"));

        Assertions.assertEquals("E-mail must be a valid email format", ex.getMessage());
    }

    @Test
    public void givenInvalidEmailNoAt_whenSettingEmail_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 12, "john.doe.ucll.be", "john1234"));

        Assertions.assertEquals("E-mail must be a valid email format", ex.getMessage());
    }

    @Test
    public void givenInvalidEmailNoDot_whenSettingEmail_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 12, "john@doe@ucll@be", "john1234"));

        Assertions.assertEquals("E-mail must be a valid email format", ex.getMessage());
    }

    @Test
    public void givenInvalidPasswordToShort_whenSettingPassword_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 12, "john.doe@ucll.be", "234"));

        Assertions.assertEquals("Password must be at least 8 characters long", ex.getMessage());
    }

    @Test
    public void givenInvalidPasswordEmptyString_whenSettingPassword_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 12, "john.doe@ucll.be", ""));

        Assertions.assertEquals("Password must be at least 8 characters long", ex.getMessage());
    }

    @Test
    public void givenInvalidPasswordNull_whenSettingPassword_thenRuntimeExceptionIsThrown() {
        Exception ex = Assertions.assertThrows(RuntimeException.class,
                () -> new User("John Doe", 12, "john.doe@ucll.be", null));

        Assertions.assertEquals("Password must be at least 8 characters long", ex.getMessage());
    }
}

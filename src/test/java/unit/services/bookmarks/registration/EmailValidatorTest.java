package unit.services.bookmarks.registration;

import game.mightywarriors.services.registration.validators.EmailValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailValidatorTest {
    private static EmailValidator objectUnderTest;

    @BeforeClass
    public static void setUp() {
        objectUnderTest = new EmailValidator();
    }

    @Test
    public void isValidEmail() {
        assertEquals(true, objectUnderTest.isValidEmail("asd@wp.pl"));
    }

    @Test
    public void isValidEmail_1() {
        assertEquals(false, objectUnderTest.isValidEmail("asd@wp"));
    }
}

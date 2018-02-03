package unit.services.bookmarks.registration;

import game.mightywarriors.services.bookmarks.registration.validators.PasswordValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordValidatorTest {
    private static PasswordValidator objectUnderTest;

    @BeforeClass
    public static void setUp() {
        objectUnderTest = new PasswordValidator();
    }

    @Test
    public void isPasswordNotValid_1() {
        assertEquals(false, objectUnderTest.isPasswordValid("a"));
    }

    @Test
    public void isPasswordNotValid_2() {
        assertEquals(false, objectUnderTest.isPasswordValid("aaaaaaaa"));
    }

    @Test
    public void isPasswordNotValid_3() {
        assertEquals(false, objectUnderTest.isPasswordValid("aaaaaaaaaB"));
    }

    @Test
    public void isPasswordNotValid_4() {
        assertEquals(false, objectUnderTest.isPasswordValid("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void isPasswordValid() {
        assertEquals(true, objectUnderTest.isPasswordValid("aaaaaaaaaB1"));
    }
}

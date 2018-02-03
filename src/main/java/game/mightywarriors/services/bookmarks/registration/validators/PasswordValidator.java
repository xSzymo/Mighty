package game.mightywarriors.services.bookmarks.registration.validators;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import org.springframework.stereotype.Service;

@Service
public class PasswordValidator {
    private static final int minimumPasswordChars = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_CHARS;
    private static final int maximumPasswordChars = SystemVariablesManager.REGISTRATION_MAXIMUM_PASSWORD_CHARS;
    private static final int minimumDigitChars = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_DIGIT_CHARS;
    private static final int minimumUpperCase = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_UPPER_CASE;
    private static final int minimumLowerCase = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_LOWER_CASE;

    public boolean isPasswordValid(String password) {
        int digitalChar = 0;
        int upperCase = 0;
        int lowerCase = 0;

        if (password.length() >= minimumPasswordChars && password.length() <= maximumPasswordChars) {
            for (int i = 0; i < password.length(); i++)
                if (Character.isUpperCase(password.charAt(i)))
                    upperCase++;
                else if (Character.isLowerCase(password.charAt(i)))
                    lowerCase++;
                else if (Character.isDigit(password.charAt(i)))
                    digitalChar++;

            if (lowerCase >= minimumLowerCase && upperCase >= minimumUpperCase && digitalChar >= minimumDigitChars)
                return true;
        }
        return false;
    }
}

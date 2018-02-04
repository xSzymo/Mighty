package game.mightywarriors.services.registration.validators;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator {
    private static final Pattern validEmailRegex = SystemVariablesManager.VALID_EMAIL_ADDRESS_REGEX;

    public boolean isValidEmail(String email) {
        return isValidEmailAddress(email) && validateEmail(email);
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = validEmailRegex.matcher(emailStr);
        return matcher.find();
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException ex) {
            result = false;
        }

        return result;
    }
}

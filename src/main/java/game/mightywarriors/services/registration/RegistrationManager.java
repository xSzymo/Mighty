package game.mightywarriors.services.registration;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.services.registration.validators.EmailValidator;
import game.mightywarriors.services.registration.validators.PasswordValidator;
import game.mightywarriors.web.json.objects.bookmarks.RegistrationInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationManager {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderImpl sender;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private RandomCodeFactory codeFactory;

    private static final int minimumPasswordChars = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_CHARS;
    private static final int maximumPasswordChars = SystemVariablesManager.REGISTRATION_MAXIMUM_PASSWORD_CHARS;
    private static final int minimumDigitChars = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_DIGIT_CHARS;
    private static final int minimumUpperCase = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_UPPER_CASE;
    private static final int minimumLowerCase = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_LOWER_CASE;

    public void registerUser(RegistrationInformer informer) throws Exception {
        throwExceptionIf_InformerIsNotSetProperly(informer);
        throwExceptionIf_EmailIsNotValid(informer.email);
        throwExceptionIf_EmailIsAlreadyTaken(informer.email);
        throwExceptionIf_LoginIsAlreadyTaken(informer.login);
        throwExceptionIf_PasswordIsNotValid(informer.password);

        String code = codeFactory.getUniqueCodeToEnableAccount();

        User user = new User(informer.login, informer.password, informer.email);
        user.setAccountEnabled(false);
        user.setAccountNonLocked(false);
        user.setCodeToEnableAccount(code);
        userService.save(user);

        sender.sendMail(informer.email, SystemVariablesManager.EMAIL_REGISTRATION_SUBJECT, SystemVariablesManager.EMAIL_REGISTRATION_MESSAGE + code);
    }

    private void throwExceptionIf_InformerIsNotSetProperly(RegistrationInformer informer) throws Exception {
        if (informer.email == null || informer.login == null || informer.password == null)
            throw new Exception("Choose email, login and password");
    }

    private void throwExceptionIf_LoginIsAlreadyTaken(String login) throws Exception {
        if (userService.find(login) != null)
            throw new Exception("Login already taken");
    }

    private void throwExceptionIf_EmailIsAlreadyTaken(String email) throws Exception {
        if (userService.findByEmail(email) != null)
            throw new Exception("Email already taken");
    }

    private void throwExceptionIf_EmailIsNotValid(String email) throws Exception {
        if (!emailValidator.isValidEmail(email))
            throw new Exception("Email is not valid");
    }

    private void throwExceptionIf_PasswordIsNotValid(String password) throws Exception {
        if (!passwordValidator.isPasswordValid(password))
            throw new Exception("Password is not valid, You need to set : at least " + minimumPasswordChars + " chars, max : " + maximumPasswordChars +
                    ", minimum digit chars" + minimumDigitChars + ", minimum upper case : " + minimumUpperCase + ", minimum lower case : " + minimumLowerCase);
    }
}
package game.mightywarriors.services.bookmarks.options;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.AuthorizationCode;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.services.bookmarks.utilities.OptionsHelper;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.services.registration.validators.PasswordValidator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.PasswordInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordChanger {
    private UserService userService;
    private UsersRetriever usersRetriever;
    private MailSenderImpl sender;
    private PasswordValidator passwordValidator;
    private RandomCodeFactory randomCodeFactory;
    private OptionsHelper helper;

    private static final int minimumPasswordChars = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_CHARS;
    private static final int maximumPasswordChars = SystemVariablesManager.REGISTRATION_MAXIMUM_PASSWORD_CHARS;
    private static final int minimumDigitChars = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_DIGIT_CHARS;
    private static final int minimumUpperCase = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_UPPER_CASE;
    private static final int minimumLowerCase = SystemVariablesManager.REGISTRATION_MINIMUM_PASSWORD_LOWER_CASE;

    @Autowired
    public PasswordChanger(UserService userService, UsersRetriever usersRetriever, MailSenderImpl mailSender, PasswordValidator passwordValidator, RandomCodeFactory randomCodeFactory, OptionsHelper optionsHelper) {
        this.userService = userService;
        this.usersRetriever = usersRetriever;
        this.sender = mailSender;
        this.passwordValidator = passwordValidator;
        this.randomCodeFactory = randomCodeFactory;
        this.helper = optionsHelper;
    }

    public void prepareChangePassword(String authorization, PasswordInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_PasswordIsNotValid(informer.password);

        AuthorizationCode authorizationCode = getNewEmailAuthorizationCode(informer);
        helper.saveNewAuthorizationCodeForUserAndDeleteOld(user, authorizationCode, AuthorizationType.PASSWORD);

        sender.sendMail(user.geteMail(), SystemVariablesManager.EMAIL_PASSWORD_SUBJECT, SystemVariablesManager.EMAIL_PASSWORD_MESSAGE + authorizationCode.getAuthorizationCode());
    }

    public void changePassword(String authorization, CodeInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        AuthorizationCode authorizationCode = helper.getUserAuthorizationCodeWithSpecificType(user, AuthorizationType.PASSWORD);
        helper.throwExceptionIf_CodeToEnableAccExpired(authorizationCode, AuthorizationType.PASSWORD);
        helper.throwExceptionIf_CodesAreNotSame(informer.code, authorizationCode);

        user.setPassword(authorizationCode.getNewValue());
        userService.save(user);
    }

    private AuthorizationCode getNewEmailAuthorizationCode(PasswordInformer informer) {
        return new AuthorizationCode(randomCodeFactory.getUniqueCodeToAuthorizeChangeUserOperation(), AuthorizationType.PASSWORD, informer.password);
    }

    private void throwExceptionIf_PasswordIsNotValid(String password) throws Exception {
        if (!passwordValidator.isPasswordValid(password))
            throw new Exception("Password is not valid, You need to set : at least " + minimumPasswordChars + " chars, max : " + maximumPasswordChars +
                    ", minimum digit chars" + minimumDigitChars + ", minimum upper case : " + minimumUpperCase + ", minimum lower case : " + minimumLowerCase);
    }
}

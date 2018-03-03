package game.mightywarriors.services.bookmarks.options;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.AuthorizationCode;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.services.bookmarks.utilities.OptionsHelper;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.services.registration.validators.EmailValidator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailChanger {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private MailSenderImpl sender;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private RandomCodeFactory randomCodeFactory;
    @Autowired
    private OptionsHelper helper;

    public void prepareChangeEmail(String authorization, RemindInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_EmailIsNotValid(informer.email);
        throwExceptionIf_EmailIsAlreadyTaken(informer.email);

        AuthorizationCode authorizationCode = getNewEmailAuthorizationCode(informer);
        helper.saveNewAuthorizationCodeForUserAndDeleteOld(user, authorizationCode, AuthorizationType.EMAIL);

        sender.sendMail(user.geteMail(), SystemVariablesManager.EMAIL_EMAIL_SUBJECT, SystemVariablesManager.EMAIL_EMAIL_MESSAGE + authorizationCode.getAuthorizationCode());
    }

    public void changeEmail(String authorization, CodeInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        AuthorizationCode authorizationCode = helper.getUserAuthorizationCodeWithSpecificType(user, AuthorizationType.EMAIL);
        helper.throwExceptionIf_CodeToEnableAccExpired(authorizationCode, AuthorizationType.EMAIL);
        helper.throwExceptionIf_CodesAreNotSame(informer.code, authorizationCode);

        user.setEMail(authorizationCode.getNewValue());
        userService.save(user);
    }

    private AuthorizationCode getNewEmailAuthorizationCode(RemindInformer informer) {
        return new AuthorizationCode(randomCodeFactory.getUniqueCodeToAuthorizeChangeUserOperation(), AuthorizationType.EMAIL, informer.email);
    }

    private void throwExceptionIf_EmailIsAlreadyTaken(String email) throws Exception {
        if (userService.findByEmail(email) != null)
            throw new Exception("Email already taken");
    }

    private void throwExceptionIf_EmailIsNotValid(String email) throws Exception {
        if (email == null)
            throw new Exception("Wrong email");
        if (!emailValidator.isValidEmail(email))
            throw new Exception("Email is not valid");
    }
}

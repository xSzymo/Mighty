package game.mightywarriors.services.bookmarks.options;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.AuthorizationCode;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.services.bookmarks.utilities.OptionsHelper;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.LoginInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginChanger {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private MailSenderImpl sender;
    @Autowired
    private RandomCodeFactory randomCodeFactory;
    @Autowired
    private OptionsHelper helper;

    public void prepareChangeLogin(String authorization, LoginInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_LoginIsAlreadyTaken(informer.login);

        AuthorizationCode authorizationCode = getNewEmailAuthorizationCode(informer);
        helper.saveNewAuthorizationCodeForUserAndDeleteOld(user, authorizationCode, AuthorizationType.LOGIN);

        sender.sendMail(user.geteMail(), SystemVariablesManager.EMAIL_LOGIN_SUBJECT, SystemVariablesManager.EMAIL_LOGIN_MESSAGE + authorizationCode.getAuthorizationCode());
    }

    public void changeLogin(String authorization, CodeInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        AuthorizationCode authorizationCode = helper.getUserAuthorizationCodeWithSpecificType(user, AuthorizationType.LOGIN);
        helper.throwExceptionIf_CodeToEnableAccExpired(authorizationCode, AuthorizationType.LOGIN);
        helper.throwExceptionIf_CodesAreNotSame(informer.code, authorizationCode);

        user.setLogin(authorizationCode.getNewValue());
        userService.save(user);
    }

    private AuthorizationCode getNewEmailAuthorizationCode(LoginInformer informer) {
        return new AuthorizationCode(randomCodeFactory.getUniqueCodeToAuthorizeChangeUserOperation(), AuthorizationType.LOGIN, informer.login);
    }

    private void throwExceptionIf_LoginIsAlreadyTaken(String login) throws Exception {
        if (login == null)
            throw new Exception("Wrong login");

        if (userService.find(login) != null)
            throw new Exception("Login already taken");
    }
}

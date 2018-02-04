package game.mightywarriors.services.bookmarks.registration;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.web.json.objects.bookmarks.RegistrationInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeSender {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderImpl sender;

    public void sendCodeToEnableAccount(RegistrationInformer informer) throws Exception {
        User user = userService.findByEmail(informer.email);

        throwExceptionIf_UserIsNullOrIsNotLocked(user);

        sender.sendMail(informer.email, SystemVariablesManager.EMAIL_SUBJECT, SystemVariablesManager.EMAIL_MESSAGE + user.getCodeToEnableAccount());
    }

    private void throwExceptionIf_UserIsNullOrIsNotLocked(User user) throws Exception {
        if (user == null)
            throw new Exception("User is not locked");
        if (user.getCodeToEnableAccount() == null)
            throw new Exception("User is not locked");
    }
}

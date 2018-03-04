package game.mightywarriors.services.registration;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeSender {
    private UserService userService;
    private MailSenderImpl sender;

    @Autowired
    public CodeSender(UserService userService, MailSenderImpl sender) {
        this.userService = userService;
        this.sender = sender;
    }

    public void sendCodeToEnableAccount(RemindInformer informer) throws Exception {
        User user = userService.findByEmail(informer.email);

        throwExceptionIf_UserIsNullOrIsNotLocked(user);

        sender.sendMail(informer.email, SystemVariablesManager.EMAIL_REGISTRATION_SUBJECT, SystemVariablesManager.EMAIL_REGISTRATION_MESSAGE + user.getCodeToEnableAccount());
    }

    private void throwExceptionIf_UserIsNullOrIsNotLocked(User user) throws Exception {
        if (user == null)
            throw new Exception("User is not locked");
        if (user.getCodeToEnableAccount() == null)
            throw new Exception("User is not locked");
    }
}

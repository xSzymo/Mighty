package game.mightywarriors.services.remind;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.email.MailSenderImpl;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataReminder {
    @Autowired
    private UserService userService;
    @Autowired
    private MailSenderImpl sender;

    public void remindPassword(RemindInformer informer) {
        User user = userService.findByEmail(informer.email);

        if (user != null)
            sender.sendMail(user.geteMail(), SystemVariablesManager.EMAIL_REGISTRATION_SUBJECT, SystemVariablesManager.EMAIL_REMINDER_PASSWORD_MESSAGE + user.getPassword());
    }

    public void remindLogin(RemindInformer informer) {
        User user = userService.findByEmail(informer.email);

        if (user != null)
            sender.sendMail(user.geteMail(), SystemVariablesManager.EMAIL_REGISTRATION_SUBJECT, SystemVariablesManager.EMAIL_REMINDER_LOGIN_MESSAGE + user.getLogin());
    }
}

package game.mightywarriors.services.registration;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.LockedAccountException;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AccountEnabler {
    @Autowired
    private UserService userService;

    public void enableAccount(CodeInformer informer) throws Exception {
        User user = userService.findByCodeToEnableAccount(informer.code);

        throwExceptionIf_UserIsNull(user);
        throwExceptionIf_CodeToEnableAccExpired(user);

        user.setAccountEnabled(true);
        user.setCodeToEnableAccount(null);
        userService.save(user);
    }

    private void throwExceptionIf_UserIsNull(User user) throws Exception {
        if (user == null)
            throw new Exception("wrong code");
    }

    private void throwExceptionIf_CodeToEnableAccExpired(User user) throws LockedAccountException {
        if (((new Timestamp(System.currentTimeMillis()).getTime() - user.getCreatedDate().getTime()) / 1000 / 60) > SystemVariablesManager.EMAIL_REGISTRATION_CODE_EXPIRATION_TIME) {
            userService.delete(user.getId());
            throw new LockedAccountException("It already expired");
        }
    }
}

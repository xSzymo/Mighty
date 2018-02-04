package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Set;

@Service
public class UserCleaner {
    private UserService userService;

    @Autowired
    public UserCleaner(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public synchronized void deleteAllUsersWithExpiredTokens() {
        Set<String> codes = userService.findAllCodesToEnableAccount();

        for (String code : codes) {
            User user = userService.findByCodeToEnableAccount(code);
            if (((new Timestamp(System.currentTimeMillis()).getTime() - user.getCreatedDate().getTime()) / 1000 / 60) > SystemVariablesManager.EMAIL_CODE_EXPIRATION_TIME)
                userService.delete(user.getId());
        }
    }
}

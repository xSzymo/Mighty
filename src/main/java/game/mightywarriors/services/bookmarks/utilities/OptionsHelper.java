package game.mightywarriors.services.bookmarks.utilities;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.repositories.AuthorizationCodeRepository;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.AuthorizationCode;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.other.exceptions.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OptionsHelper {
    @Autowired
    private AuthorizationCodeRepository repository;
    @Autowired
    private UserService userService;

    public void throwExceptionIf_CodeToEnableAccExpired(AuthorizationCode authorizationCode, AuthorizationType authorizationType) throws Exception {
        int waitingTime;

        if (authorizationType.getType().equals(AuthorizationType.EMAIL.getType()))
            waitingTime = SystemVariablesManager.EMAIL_EMAIL_CODE_EXPIRATION_TIME;
        else if (authorizationType.getType().equals(AuthorizationType.LOGIN.getType()))
            waitingTime = SystemVariablesManager.EMAIL_LOGIN_CODE_EXPIRATION_TIME;
        else if (authorizationType.getType().equals(AuthorizationType.PASSWORD.getType()))
            waitingTime = SystemVariablesManager.EMAIL_PASSWORD_CODE_EXPIRATION_TIME;
        else throw new Exception("Wrong authorization type");

        if (((new Timestamp(System.currentTimeMillis()).getTime() - authorizationCode.getCreatedDate().getTime()) / 1000 / 60) > waitingTime) {
            repository.deleteById(authorizationCode.getId());
            throw new LockedAccountException("It already expired");
        }
    }

    public AuthorizationCode getUserAuthorizationCodeWithSpecificType(User user, AuthorizationType type) throws Exception {
        Optional<AuthorizationCode> code = user.getAuthorizationCodes().stream().filter(x -> x.getType().getType().equals(type.getType())).findFirst();
        if (!code.isPresent())
            throw new Exception("not found");

        return code.get();
    }

    public void throwExceptionIf_CodesAreNotSame(String code, AuthorizationCode authorizationCode) throws Exception {
        if (!code.equals(authorizationCode.getAuthorizationCode()))
            throw new Exception("not same");
    }

    public void saveNewAuthorizationCodeForUserAndDeleteOld(User user, AuthorizationCode authorizationCode, AuthorizationType type) {
        long id = 0;

        HashSet<AuthorizationCode> allAuthorizationCodesExceptPassword = getAllAuthorizationCodesExceptSpecificType(user, type);
        for (AuthorizationCode x : user.getAuthorizationCodes())
            if (!allAuthorizationCodesExceptPassword.contains(x))
                id = x.getId();

        user.setAuthorizationCodes(allAuthorizationCodesExceptPassword);
        user.getAuthorizationCodes().add(authorizationCode);

        userService.save(user);
        if (id != 0)
            repository.deleteById(id);
    }

    public HashSet<AuthorizationCode> getAllAuthorizationCodesExceptSpecificType(User user, AuthorizationType type) {
        Optional<AuthorizationCode> first = user.getAuthorizationCodes().stream().filter(x -> x.getType().getType().equals(type.getType())).findFirst();
        first.ifPresent(authorizationCode -> repository.deleteById(authorizationCode.getId()));

        return user.getAuthorizationCodes().stream().filter(x -> !x.getType().getType().equals(type.getType())).collect(Collectors.toCollection(HashSet::new));
    }
}

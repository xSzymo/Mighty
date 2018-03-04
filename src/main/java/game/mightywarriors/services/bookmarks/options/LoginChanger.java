package game.mightywarriors.services.bookmarks.options;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.repositories.RankingRepository;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.AuthorizationCode;
import game.mightywarriors.data.tables.Ranking;
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
    private UserService userService;
    private UsersRetriever usersRetriever;
    private MailSenderImpl sender;
    private RandomCodeFactory randomCodeFactory;
    private OptionsHelper helper;
    private RankingRepository rankingService;

    @Autowired
    public LoginChanger(UserService userService, UsersRetriever usersRetriever, MailSenderImpl mailSender, RandomCodeFactory randomCodeFactory, OptionsHelper optionsHelper, RankingRepository rankingService) {
        this.userService = userService;
        this.usersRetriever = usersRetriever;
        this.sender = mailSender;
        this.randomCodeFactory = randomCodeFactory;
        this.helper = optionsHelper;
        this.rankingService = rankingService;
    }

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

        changeNick(user, authorizationCode.getNewValue());
    }

    private void changeNick(User user, String newLogin) throws Exception {
        userService.changeNick(user.getId(), newLogin);

        Ranking ranking = rankingService.findByNickname(user.getLogin());
        long oldRanking = ranking.getRanking();
        rankingService.deleteByNickname(ranking.getNickname());

        Ranking newRanking = new Ranking(newLogin);
        newRanking.setRanking(oldRanking);
        rankingService.save(newRanking);
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

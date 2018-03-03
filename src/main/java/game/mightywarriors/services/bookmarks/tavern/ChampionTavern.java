package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NotEnoughGoldException;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChampionBuyInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ChampionTavern {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private UserService userService;

    public void buyChampion(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        checkCanBuyChampion(user);

        userService.save(user);
    }

    public ChampionBuyInformer getInformationForBuyChampion(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_userIsNotPresent(user);

        if (user.getChampions().size() == 0)
            return new ChampionBuyInformer(SystemVariablesManager.MINIMUM_LEVEL_FOR_FIRST_CHAMPION, SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);
        if (user.getChampions().size() == 1)
            return new ChampionBuyInformer(SystemVariablesManager.MINIMUM_LEVEL_FOR_SECOND_CHAMPION, SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);
        if (user.getChampions().size() == 2)
            return new ChampionBuyInformer(SystemVariablesManager.MINIMUM_LEVEL_FOR_THIRD_CHAMPION, SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION);

        return new ChampionBuyInformer(1000, 1);
    }

    private void checkCanBuyChampion(User user) throws Exception {
        throwExceptionIf_userIsNotPresent(user);

        if (user.getChampions().size() == 0) {
            checkUserGotEnoughLevel(user, SystemVariablesManager.MINIMUM_LEVEL_FOR_FIRST_CHAMPION);
            checkUserGotEnoughGold(user, SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);

            user.subtractGold(new BigDecimal(SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION));
            user.addChampion(new Champion());

        } else if (user.getChampions().size() == 1) {
            checkUserGotEnoughLevel(user, SystemVariablesManager.MINIMUM_LEVEL_FOR_SECOND_CHAMPION);
            checkUserGotEnoughGold(user, SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);

            user.subtractGold(new BigDecimal(SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION));
            user.addChampion(new Champion());

        } else if (user.getChampions().size() == 2) {
            checkUserGotEnoughLevel(user, SystemVariablesManager.MINIMUM_LEVEL_FOR_THIRD_CHAMPION);
            checkUserGotEnoughGold(user, SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION);

            user.subtractGold(new BigDecimal(SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION));
            user.addChampion(new Champion());
        } else {
            throw new Exception("User reached limit");
        }
    }

    private void throwExceptionIf_userIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new Exception("User not found");
    }

    private static void checkUserGotEnoughGold(User user, double gold) throws NotEnoughGoldException {
        if (user.getGold().doubleValue() < gold)
            throw new NotEnoughGoldException("not enough gold");
    }

    private static void checkUserGotEnoughLevel(User user, long level) throws NotEnoughGoldException {
        if (user.getUserChampionHighestLevel() < level)
            throw new NotEnoughGoldException("not enough level");
    }
}

package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NotEnoughGoldException;
import game.mightywarriors.services.background.tasks.ItemDrawer;
import game.mightywarriors.services.background.tasks.MissionAssigner;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChampionBuyInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ChampionTavern {
    private UsersRetriever usersRetriever;
    private UserService userService;
    private ChampionService championService;
    private ItemDrawer itemDrawer;
    private MissionAssigner missionAssigner;

    @Autowired
    public ChampionTavern(UsersRetriever usersRetriever, UserService userService, ChampionService championService, MissionAssigner missionAssigner, ItemDrawer itemDrawer) {
        this.userService = userService;
        this.itemDrawer = itemDrawer;
        this.usersRetriever = usersRetriever;
        this.championService = championService;
        this.missionAssigner = missionAssigner;
    }

    @Transactional
    public void buyChampion(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        buyChampion(user);

        userService.save(user);
        itemDrawer.drawItemsForUser(user.getId());
        missionAssigner.assignNewMissionForUsers(user.getId());
    }

    public ChampionBuyInformer getInformationForBuyChampion(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_UserIsNotPresent(user);

        if (user.getChampions().size() == 0)
            return new ChampionBuyInformer(SystemVariablesManager.MINIMUM_LEVEL_FOR_FIRST_CHAMPION, SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);
        if (user.getChampions().size() == 1)
            return new ChampionBuyInformer(SystemVariablesManager.MINIMUM_LEVEL_FOR_SECOND_CHAMPION, SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);
        if (user.getChampions().size() == 2)
            return new ChampionBuyInformer(SystemVariablesManager.MINIMUM_LEVEL_FOR_THIRD_CHAMPION, SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION);

        return new ChampionBuyInformer(1000, 1);
    }

    private void buyChampion(User user) throws Exception {
        throwExceptionIf_UserIsNotPresent(user);

        if (user.getChampions().size() == 0) {
            checkUserGotEnoughLevel(user, SystemVariablesManager.MINIMUM_LEVEL_FOR_FIRST_CHAMPION);
            checkUserGotEnoughGold(user, SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION);

            user.subtractGold(new BigDecimal(SystemVariablesManager.MINIMUM_GOLD_FOR_FIRST_CHAMPION));

        } else if (user.getChampions().size() == 1) {
            checkUserGotEnoughLevel(user, SystemVariablesManager.MINIMUM_LEVEL_FOR_SECOND_CHAMPION);
            checkUserGotEnoughGold(user, SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION);

            user.subtractGold(new BigDecimal(SystemVariablesManager.MINIMUM_GOLD_FOR_SECOND_CHAMPION));

        } else if (user.getChampions().size() == 2) {
            checkUserGotEnoughLevel(user, SystemVariablesManager.MINIMUM_LEVEL_FOR_THIRD_CHAMPION);
            checkUserGotEnoughGold(user, SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION);

            user.subtractGold(new BigDecimal(SystemVariablesManager.MINIMUM_GOLD_FOR_THIRD_CHAMPION));
        } else {
            throw new Exception("User reached limit");
        }

        Champion champion = new Champion();
        championService.save(champion);
        user.addChampion(champion);
    }

    private void throwExceptionIf_UserIsNotPresent(User user) throws Exception {
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

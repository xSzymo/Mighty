package game.mightywarriors.services.bookmarks.profile;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.other.exceptions.NotEnoughGoldException;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ChampionPointsManager {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    private static final int ONE = 1;

    public void addPoints(String authorization, StatisticType type, long id) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Champion champion = user.getChampions().stream().filter(x -> x.getId().equals(id)).findFirst().get();
        double gold = 0;

        if (champion == null)
            throw new Exception("Champion not found");

        if (type.getType().equals(StatisticType.ARMOR.getType())) {
            long armor = champion.getStatistic().getArmor() + ONE;
            gold = armor * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
            checkUserGotEnoughGold(user, gold);

            champion.getStatistic().setArmor(armor);
        } else if (type.getType().equals(StatisticType.MAGIC_RESIST.getType())) {
            long magicResist = champion.getStatistic().getMagicResist() + ONE;
            gold = magicResist * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
            checkUserGotEnoughGold(user, gold);

            champion.getStatistic().setMagicResist(magicResist);
        } else if (type.getType().equals(StatisticType.INTELLIGENCE.getType())) {
            long intelligence = champion.getStatistic().getIntelligence() + ONE;
            gold = intelligence * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
            checkUserGotEnoughGold(user, gold);

            champion.getStatistic().setIntelligence(intelligence);
        } else if (type.getType().equals(StatisticType.STRENGTH.getType())) {
            long strength = champion.getStatistic().getStrength() + ONE;
            gold = strength * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
            checkUserGotEnoughGold(user, gold);

            champion.getStatistic().setStrength(strength);
        } else if (type.getType().equals(StatisticType.CRITICAL_CHANCE.getType())) {
            long criticChance = champion.getStatistic().getCriticalChance() + ONE;
            gold = criticChance * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
            checkUserGotEnoughGold(user, gold);

            champion.getStatistic().setCriticalChance(criticChance);
        } else if (type.getType().equals(StatisticType.VITALITY.getType())) {
            long vitality = champion.getStatistic().getVitality() + ONE;
            gold = vitality * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
            checkUserGotEnoughGold(user, gold);

            champion.getStatistic().setVitality(vitality);
        }

        user.subtractGold(new BigDecimal(gold));
        userService.save(user);
    }

    private static void checkUserGotEnoughGold(User user, double gold) throws NotEnoughGoldException {
        if (user.getGold().doubleValue() < gold)
            throw new NotEnoughGoldException("not enough gold");
    }
}

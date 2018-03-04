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
import java.util.Optional;

@Service
public class ChampionPointsManager {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    private static final int ONE = 1;

    public void addPoints(String authorization, StatisticType type, long id, long howMany) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Optional<Champion> champion = user.getChampions().stream().filter(x -> x.getId().equals(id)).findFirst();
        double gold = 0;

        if (!champion.isPresent())
            throw new Exception("Champion not found");

        for(int i = 0; i < howMany; i++) {
            if (type.getType().equals(StatisticType.ARMOR.getType())) {
                long armor = champion.get().getStatistic().getArmor() + ONE;
                gold += armor * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
                checkUserGotEnoughGold(user, gold);

                champion.get().getStatistic().setArmor(armor);
            } else if (type.getType().equals(StatisticType.MAGIC_RESIST.getType())) {
                long magicResist = champion.get().getStatistic().getMagicResist() + ONE;
                gold += magicResist * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
                checkUserGotEnoughGold(user, gold);

                champion.get().getStatistic().setMagicResist(magicResist);
            } else if (type.getType().equals(StatisticType.INTELLIGENCE.getType())) {
                long intelligence = champion.get().getStatistic().getIntelligence() + ONE;
                gold += intelligence * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
                checkUserGotEnoughGold(user, gold);

                champion.get().getStatistic().setIntelligence(intelligence);
            } else if (type.getType().equals(StatisticType.STRENGTH.getType())) {
                long strength = champion.get().getStatistic().getStrength() + ONE;
                gold += strength * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
                checkUserGotEnoughGold(user, gold);

                champion.get().getStatistic().setStrength(strength);
            } else if (type.getType().equals(StatisticType.CRITICAL_CHANCE.getType())) {
                long criticChance = champion.get().getStatistic().getCriticalChance() + ONE;
                gold += criticChance * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
                checkUserGotEnoughGold(user, gold);

                champion.get().getStatistic().setCriticalChance(criticChance);
            } else if (type.getType().equals(StatisticType.VITALITY.getType())) {
                long vitality = champion.get().getStatistic().getVitality() + ONE;
                gold += vitality * SystemVariablesManager.GOLD_FOR_STATISTIC_RATE;
                checkUserGotEnoughGold(user, gold);

                champion.get().getStatistic().setVitality(vitality);
            }
        }

        user.subtractGold(new BigDecimal(gold));
        userService.save(user);
    }

    private static void checkUserGotEnoughGold(User user, double gold) throws NotEnoughGoldException {
        if (user.getGold().doubleValue() < gold)
            throw new NotEnoughGoldException("not enough gold");
    }
}

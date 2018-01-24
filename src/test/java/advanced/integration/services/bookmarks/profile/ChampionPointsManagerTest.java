package advanced.integration.services.bookmarks.profile;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.other.exceptions.NotEnoughGoldException;
import game.mightywarriors.services.bookmarks.profile.ChampionPointsManager;
import game.mightywarriors.services.security.UsersRetriever;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ChampionPointsManagerTest extends AuthorizationConfiguration {
    @Autowired
    private ChampionPointsManager championPointsManager;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private UserService userService;
    @Autowired
    private ChampionService championService;

    private User user;
    private Champion champion;
    private static final long RATIO = 10;
    private static final long ONE = 1;
    private static final long TWO = 2;
    private static final long THREE = 3;


    @Before
    public void setUp() throws Exception {
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();

        userService.save(user);
    }

    @Test(expected = NotEnoughGoldException.class)
    public void addPoints_not_enough_money() throws Exception {
        user.setGold(new BigDecimal("0"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());
    }

    @Test
    public void addPoints_magicResist_one_time() throws Exception {
        long armor = champion.getStatistic().getMagicResist();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());

        assertEquals(1000 - ((ONE + armor) * RATIO), usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + ONE, championService.find(champion.getId()).getStatistic().getMagicResist());
    }

    @Test
    public void addPoints_magicResist_two_time() throws Exception {
        long armor = champion.getStatistic().getMagicResist();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());
        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());

        long gold = 0;
        for (int i = 1; i < 3; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + TWO, championService.find(champion.getId()).getStatistic().getMagicResist());
    }

    @Test
    public void addPoints_magicResist_three_time() throws Exception {
        long armor = champion.getStatistic().getMagicResist();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());
        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());
        championPointsManager.addPoints(token, StatisticType.MAGIC_RESIST, champion.getId());

        long gold = 0;
        for (int i = 1; i < 4; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + THREE, championService.find(champion.getId()).getStatistic().getMagicResist());
    }


    @Test
    public void addPoints_armor_one_time() throws Exception {
        long armor = champion.getStatistic().getArmor();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.ARMOR, champion.getId());

        assertEquals(1000 - ((ONE + armor) * RATIO), usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + ONE, championService.find(champion.getId()).getStatistic().getArmor());
    }

    @Test
    public void addPoints_armor_two_time() throws Exception {
        long armor = champion.getStatistic().getArmor();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.ARMOR, champion.getId());
        championPointsManager.addPoints(token, StatisticType.ARMOR, champion.getId());

        long gold = 0;
        for (int i = 1; i < 3; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + TWO, championService.find(champion.getId()).getStatistic().getArmor());
    }

    @Test
    public void addPoints_armor_three_time() throws Exception {
        long armor = champion.getStatistic().getArmor();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.ARMOR, champion.getId());
        championPointsManager.addPoints(token, StatisticType.ARMOR, champion.getId());
        championPointsManager.addPoints(token, StatisticType.ARMOR, champion.getId());

        long gold = 0;
        for (int i = 1; i < 4; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + THREE, championService.find(champion.getId()).getStatistic().getArmor());
    }


    @Test
    public void addPoints_strength_one_time() throws Exception {
        long armor = champion.getStatistic().getStrength();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.STRENGTH, champion.getId());

        assertEquals(1000 - ((ONE + armor) * RATIO), usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + ONE, championService.find(champion.getId()).getStatistic().getStrength());
    }

    @Test
    public void addPoints_strength_two_time() throws Exception {
        long armor = champion.getStatistic().getStrength();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.STRENGTH, champion.getId());
        championPointsManager.addPoints(token, StatisticType.STRENGTH, champion.getId());

        long gold = 0;
        for (int i = 1; i < 3; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + TWO, championService.find(champion.getId()).getStatistic().getStrength());
    }

    @Test
    public void addPoints_strength_three_time() throws Exception {
        long armor = champion.getStatistic().getArmor();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.STRENGTH, champion.getId());
        championPointsManager.addPoints(token, StatisticType.STRENGTH, champion.getId());
        championPointsManager.addPoints(token, StatisticType.STRENGTH, champion.getId());

        long gold = 0;
        for (int i = 1; i < 4; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + THREE, championService.find(champion.getId()).getStatistic().getStrength());
    }


    @Test
    public void addPoints_intelligence_one_time() throws Exception {
        long armor = champion.getStatistic().getIntelligence();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.INTELLIGENCE, champion.getId());

        assertEquals(1000 - ((ONE + armor) * RATIO), usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + ONE, championService.find(champion.getId()).getStatistic().getIntelligence());
    }

    @Test
    public void addPoints_intelligence_two_time() throws Exception {
        long armor = champion.getStatistic().getIntelligence();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.INTELLIGENCE, champion.getId());
        championPointsManager.addPoints(token, StatisticType.INTELLIGENCE, champion.getId());

        long gold = 0;
        for (int i = 1; i < 3; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + TWO, championService.find(champion.getId()).getStatistic().getIntelligence());
    }

    @Test
    public void addPoints_intelligence_three_time() throws Exception {
        long armor = champion.getStatistic().getIntelligence();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.INTELLIGENCE, champion.getId());
        championPointsManager.addPoints(token, StatisticType.INTELLIGENCE, champion.getId());
        championPointsManager.addPoints(token, StatisticType.INTELLIGENCE, champion.getId());

        long gold = 0;
        for (int i = 1; i < 4; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + THREE, championService.find(champion.getId()).getStatistic().getIntelligence());
    }


    @Test
    public void addPoints_vitality_one_time() throws Exception {
        long armor = champion.getStatistic().getVitality();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.VITALITY, champion.getId());

        assertEquals(1000 - ((ONE + armor) * RATIO), usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + ONE, championService.find(champion.getId()).getStatistic().getVitality());
    }

    @Test
    public void addPoints_vitality_two_time() throws Exception {
        long armor = champion.getStatistic().getVitality();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.VITALITY, champion.getId());
        championPointsManager.addPoints(token, StatisticType.VITALITY, champion.getId());

        long gold = 0;
        for (int i = 1; i < 3; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + TWO, championService.find(champion.getId()).getStatistic().getVitality());
    }

    @Test
    public void addPoints_vitality_three_time() throws Exception {
        long armor = champion.getStatistic().getVitality();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.VITALITY, champion.getId());
        championPointsManager.addPoints(token, StatisticType.VITALITY, champion.getId());
        championPointsManager.addPoints(token, StatisticType.VITALITY, champion.getId());

        long gold = 0;
        for (int i = 1; i < 4; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + THREE, championService.find(champion.getId()).getStatistic().getVitality());
    }


    @Test
    public void addPoints_criticalChance_one_time() throws Exception {
        long armor = champion.getStatistic().getCriticalChance();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.CRITICAL_CHANCE, champion.getId());

        assertEquals(1000 - ((ONE + armor) * RATIO), usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + ONE, championService.find(champion.getId()).getStatistic().getCriticalChance());
    }

    @Test
    public void addPoints_criticalChance_two_time() throws Exception {
        long armor = champion.getStatistic().getCriticalChance();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.CRITICAL_CHANCE, champion.getId());
        championPointsManager.addPoints(token, StatisticType.CRITICAL_CHANCE, champion.getId());

        long gold = 0;
        for (int i = 1; i < 3; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + TWO, championService.find(champion.getId()).getStatistic().getCriticalChance());
    }

    @Test
    public void addPoints_criticalChance_three_time() throws Exception {
        long armor = champion.getStatistic().getCriticalChance();
        user.setGold(new BigDecimal("1000"));
        userService.save(user);

        championPointsManager.addPoints(token, StatisticType.CRITICAL_CHANCE, champion.getId());
        championPointsManager.addPoints(token, StatisticType.CRITICAL_CHANCE, champion.getId());
        championPointsManager.addPoints(token, StatisticType.CRITICAL_CHANCE, champion.getId());

        long gold = 0;
        for (int i = 1; i < 4; i++) gold += ((i + armor) * RATIO);
        assertEquals(1000 - gold, usersRetriever.retrieveUser(token).getGold().intValue());
        assertEquals(armor + THREE, championService.find(champion.getId()).getStatistic().getCriticalChance());
    }
}
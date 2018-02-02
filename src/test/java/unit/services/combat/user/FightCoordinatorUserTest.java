package unit.services.combat.user;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.FighterType;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

public class FightCoordinatorUserTest {
    private FightCoordinator fightCoordinator;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        fightCoordinator = new FightCoordinator();
        user1 = setUpUser1();
        user2 = setUpUser2();
    }

    @Test
    public void fightBetweenPlayerAndMonster() {
        FightResult fightResult = fightCoordinator.fight(user1, user2);

        int i = -1;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> championModels = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

            if (i == 0) {
                assertEquals(3.0, championModels.get(0).getHp());
                assertEquals(3.0, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 1) {
                assertEquals(3.0, championModels.get(0).getHp());
                assertEquals(1.69, opponentChampions.get(0).getHp());

                assertEquals(1.31, championModels.get(0).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 2) {
                assertEquals(1.43, championModels.get(0).getHp());
                assertEquals(1.69, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.57, opponentChampions.get(0).getDmg());
            } else if (i == 3) {
                assertEquals(1.43, championModels.get(0).getHp());
                assertEquals(0.38, opponentChampions.get(0).getHp());

                assertEquals(1.31, championModels.get(0).getDmg());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
            } else if (i == 4) {
                assertEquals(-0.14, championModels.get(0).getHp());
                assertEquals(0.38, opponentChampions.get(0).getHp());

                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.57, opponentChampions.get(0).getDmg());
            }
        }

        for (int j = 0; j < fightResult.getRounds().size(); j++) {
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getUserChampions().getFirst().getFighterType().getType());
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getOpponentChampions().getFirst().getFighterType().getType());
        }
        assertEquals(5, fightResult.getRounds().size());
        assertEquals(user1, fightResult.getLooser());
        assertEquals(user2, fightResult.getWinner());
    }

    private User setUpUser1() throws Exception {
        User user = new User("user", "user", "user");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(0, 0, 0, 0, 10, 10), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(0, 5, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(0, 0, 0, 0, 5, 5), 1));

        user.getChampions().add(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(1));

        return user;
    }

    private User setUpUser2() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(0, 0, 0, 0, 10, 10), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(0, 5, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(0, 0, 0, 0, 5, 5), 1));

        user.getChampions().add(new Champion(new Statistic(15, 5, 10, 0, 5, 5), equipment).setLevel(1));

        return user;
    }
}

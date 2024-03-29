package unit.services.combat.monster;

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

public class FightCoordinatorMultipleMonsterWonTest {
    private FightCoordinator fightCoordinator;
    private User user1;
    private LinkedList<Monster> monsters;

    @Before
    public void setUp() throws Exception {
        fightCoordinator = new FightCoordinator();
        user1 = setUpUser1();
        monsters = setUpMonster();
    }

    @Test
    public void fightBetweenPlayerAndMonster() {
        FightResult fightResult = fightCoordinator.fight(user1, monsters);

        int i = -1;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> championModels = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

            if (i == 0) {
                assertEquals(3.3, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());

                assertEquals(4.5, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(3.0, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(4.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 1) {
                assertEquals(3.3, championModels.get(0).getHp());
                assertEquals(4.44, championModels.get(0).getDmg());

                assertEquals(0.06, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(3.0, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(4.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 2) {
                assertEquals(0.44, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());

                assertEquals(0.06, opponentChampions.get(0).getHp());
                assertEquals(2.86, opponentChampions.get(0).getDmg());
                assertEquals(3.0, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(4.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 3) {
                assertEquals(0.44, championModels.get(0).getHp());
                assertEquals(4.44, championModels.get(0).getDmg());

                assertEquals(-4.38, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(3.0, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(4.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 4) {
                assertEquals(-2.42, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());

                assertEquals(-4.38, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(3.0, opponentChampions.get(1).getHp());
                assertEquals(2.86, opponentChampions.get(1).getDmg());
                assertEquals(4.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            }
        }

        for (int j = 0; j < fightResult.getRounds().size(); j++) {
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getUserChampions().getFirst().getFighterType().getType());
            assertEquals(FighterType.MONSTER.getType(), fightResult.getRounds().get(i).getOpponentChampions().getFirst().getFighterType().getType());
        }
        assertEquals(user1, fightResult.getLooser());
    }

    private User setUpUser1() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(ItemType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(ItemType.ARMOR, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setRing(new Item(ItemType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(ItemType.OFFHAND, new Statistic(5, 0, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(ItemType.NECKLACE, new Statistic(5, 0, 2, 0, 0, 0), 1));
        equipment.setLegs(new Item(ItemType.LEGS, new Statistic(0, 0, 2, 0, 10, 10), 1));
        equipment.setHelmet(new Item(ItemType.HELMET, new Statistic(0, 0, 1, 0, 5, 5), 1));
        equipment.setGloves(new Item(ItemType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(ItemType.BRACELET, new Statistic(0, 5, 1, 0, 0, 0), 1));
        equipment.setBoots(new Item(ItemType.BOOTS, new Statistic(0, 0, 0, 0, 5, 5), 1));

        user.getChampions().add(new Champion(new Statistic(5, 1, 5, 0, 5, 5), equipment).setLevel(1));

        return user;
    }

    private LinkedList<Monster> setUpMonster() {
        LinkedList<Monster> fighters = new LinkedList<>();
        fighters.add(new Monster(new Statistic(10, 10, 3, 0, 1, 5)).setLevel(5));
        fighters.add(new Monster(new Statistic(10, 10, 2, 0, 5, 1)).setLevel(5));
        fighters.add(new Monster(new Statistic(10, 10, 3, 0, 1, 5)).setLevel(5));

        return fighters;
    }
}

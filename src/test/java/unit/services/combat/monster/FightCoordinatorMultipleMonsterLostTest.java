package unit.services.combat.monster;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.TypeOfFighter;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

public class FightCoordinatorMultipleMonsterLostTest {
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
                assertEquals(18.0, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());

                assertEquals(0.9, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.6, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(0.9, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 1) {
                assertEquals(18.0, championModels.get(0).getHp());
                assertEquals(29.2, championModels.get(0).getDmg());

                assertEquals(-28.3, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.6, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(0.9, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 2) {
                assertEquals(17.72, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());

                assertEquals(-28.3, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.6, opponentChampions.get(1).getHp());
                assertEquals(0.28, opponentChampions.get(1).getDmg());
                assertEquals(0.9, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 3) {
                assertEquals(17.72, championModels.get(0).getHp());
                assertEquals(26.0, championModels.get(0).getDmg());

                assertEquals(-28.3, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(-25.4, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(0.9, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 4) {
                assertEquals(17.44, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());

                assertEquals(-28.3, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(-25.4, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(0.9, opponentChampions.get(2).getHp());
                assertEquals(0.28, opponentChampions.get(2).getDmg());
            } else if (i == 5) {
                assertEquals(17.44, championModels.get(0).getHp());
                assertEquals(29.2, championModels.get(0).getDmg());

                assertEquals(-28.3, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(-25.4, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(-28.3, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            }
        }

        for(int j = 0; j < fightResult.getRounds().size(); j++) {
            assertEquals(TypeOfFighter.CHAMPION.getType(), fightResult.getRounds().get(i).getUserChampions().getFirst().getTypeOfFighter().getType());
            assertEquals(TypeOfFighter.MONSTER.getType(), fightResult.getRounds().get(i).getOpponentChampions().getFirst().getTypeOfFighter().getType());
        }
        assertEquals(user1, fightResult.getWinner());
    }

    private User setUpUser1() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 10, 0, 5, 5), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 5, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(5, 5, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(5, 5, 10, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 10, 0, 10, 10), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 5, 0, 5, 5), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 5, 5), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 5, 5, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 5, 0, 5, 5), 1));

        user.getChampions().add(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(1));

        return user;
    }

    private LinkedList<Monster> setUpMonster() {
        LinkedList<Monster> fighters = new LinkedList<>();
        fighters.add(new Monster(new Statistic(5, 5, 3, 0, 1, 5)).setLevel(1));
        fighters.add(new Monster(new Statistic(5, 5, 2, 0, 5, 1)).setLevel(1));
        fighters.add(new Monster(new Statistic(5, 5, 3, 0, 1, 5)).setLevel(1));

        return fighters;
    }
}

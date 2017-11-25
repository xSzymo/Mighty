package unit.game.mightywarriors.services.fights.dungeon;

import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.jsonObjects.fights.ChampionModel;
import game.mightywarriors.other.jsonObjects.fights.FightResult;
import game.mightywarriors.other.jsonObjects.fights.RoundProcess;
import game.mightywarriors.services.fights.dungeon.MonsterFightCoordinator;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class MonsterFightCoordinatorTest {
    private MonsterFightCoordinator monsterFightCoordinator;
    private User user1;
    private Monster monster;

    @Before
    public void setUp() throws Exception {
        monsterFightCoordinator = new MonsterFightCoordinator();
        user1 = setUpUser1();
        monster = setUpMonster();
    }

    @Test
    public void fightBetweenPlayerAndMonster() throws Exception {
        LinkedList<Monster> monsters = new LinkedList<>();
        monsters.add(monster);

        FightResult fightResult = monsterFightCoordinator.fightBetweenPlayerAndMonster(user1, monsters);

        int i = 0;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<ChampionModel> championModels = roundProcess.getUserChampions();
            LinkedList<ChampionModel> opponentChampions = roundProcess.getOpponentChampions();

//            if(i == 1) {
//                assertEquals(50, championModels.get(0).getHp());
//                assertEquals(50, championModels.get(1).getHp());
//                assertEquals(50, championModels.get(2).getHp());
//                assertEquals(30, opponentChampions.get(0).getHp());
//                assertEquals(50, opponentChampions.get(1).getHp());
//                assertEquals(30, opponentChampions.get(2).getHp());
//
//                assertEquals(40, championModels.get(0).getDmg());
//                assertEquals(50, championModels.get(1).getDmg());
//                assertEquals(50, championModels.get(2).getDmg());
//                assertEquals(50, opponentChampions.get(0).getDmg());
//                assertEquals(50, opponentChampions.get(1).getDmg());
//                assertEquals(30, opponentChampions.get(2).getDmg());
//            }
//
//            if(i == 2) {
//                assertEquals(50, championModels.get(0).getHp());
//                assertEquals(50, championModels.get(1).getHp());
//                assertEquals(50, championModels.get(2).getHp());
//                assertEquals(30, opponentChampions.get(0).getHp());
//                assertEquals(50, opponentChampions.get(1).getHp());
//                assertEquals(30, opponentChampions.get(2).getHp());
//
//                assertEquals(40, championModels.get(0).getDmg());
//                assertEquals(50, championModels.get(1).getDmg());
//                assertEquals(50, championModels.get(2).getDmg());
//                assertEquals(50, opponentChampions.get(0).getDmg());
//                assertEquals(50, opponentChampions.get(1).getDmg());
//                assertEquals(30, opponentChampions.get(2).getDmg());
//            }
        }
        assertNotEquals(fightResult.getRounds().get(0).getUserChampions().get(0).getHp(), fightResult.getRounds().get(1).getUserChampions().get(0).getHp());
        assertEquals(user1, fightResult.getLooser());
    }

    private User setUpUser1() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(0, 0, 0, 0, 0, 0), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(0, 0, 0, 0, 0, 0), 1));

        user.getChampions().add(new Champion(new Statistic(10, 1, 10, 0, 5, 5), equipment).setLevel(2));

        return user;
    }
    private Monster setUpMonster() throws Exception {
        Monster monster = new Monster(new Statistic(20, 20, 20, 20, 20, 20));
        monster.setLevel(2);

        return monster;
    }
}

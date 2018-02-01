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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class FightCoordinatorUserMultipleSpecifyChampionsTest {
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
    public void fightBetweenPlayerAndMonster() throws Exception {
        long[] users = {1, 2, 3};
        long[] opponent = {6, 7, 8};
        FightResult fightResult = fightCoordinator.fight(user1, user2, users, opponent);

        int i = -1;
        for (RoundProcess roundProcess : fightResult.getRounds()) {
            i++;
            LinkedList<Fighter> championModels = roundProcess.getUserChampions();
            LinkedList<Fighter> opponentChampions = roundProcess.getOpponentChampions();

            if (i == 0) {
                assertEquals(1.5, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(1.5, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 1) {
                assertEquals(1.5, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(1.31, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(0.19, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 2) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(0.19, opponentChampions.get(0).getHp());
                assertEquals(1.57, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 3) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(1.5, championModels.get(1).getHp());
                assertEquals(1.31, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 4) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(-0.07, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(1.5, opponentChampions.get(1).getHp());
                assertEquals(1.57, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 5) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(-0.07, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(1.5, championModels.get(2).getHp());
                assertEquals(1.31, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.19, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(0.0, opponentChampions.get(2).getDmg());
            } else if (i == 6) {
                assertEquals(-0.07, championModels.get(0).getHp());
                assertEquals(0.0, championModels.get(0).getDmg());
                assertEquals(-0.07, championModels.get(1).getHp());
                assertEquals(0.0, championModels.get(1).getDmg());
                assertEquals(-0.07, championModels.get(2).getHp());
                assertEquals(0.0, championModels.get(2).getDmg());

                assertEquals(-1.12, opponentChampions.get(0).getHp());
                assertEquals(0.0, opponentChampions.get(0).getDmg());
                assertEquals(0.19, opponentChampions.get(1).getHp());
                assertEquals(0.0, opponentChampions.get(1).getDmg());
                assertEquals(1.5, opponentChampions.get(2).getHp());
                assertEquals(1.57, opponentChampions.get(2).getDmg());
            }
        }

        for(int j = 0; j < fightResult.getRounds().size(); j++) {
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getUserChampions().getFirst().getFighterType().getType());
            assertEquals(FighterType.CHAMPION.getType(), fightResult.getRounds().get(i).getOpponentChampions().getFirst().getFighterType().getType());
        }
        assertEquals(7, fightResult.getRounds().size());
        assertEquals(user1.getLogin(), fightResult.getLooser().getLogin());
        assertEquals(user2.getLogin(), fightResult.getWinner().getLogin());
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

        Champion champion1 = spy(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        Champion champion2 = spy(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        Champion champion3 = spy(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        Champion champion4 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(100));
        Champion champion5 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(100));

        when(champion1.getId()).thenReturn(1L);
        when(champion2.getId()).thenReturn(2L);
        when(champion3.getId()).thenReturn(3L);
        when(champion4.getId()).thenReturn(4L);
        when(champion5.getId()).thenReturn(5L);

        user.addChampion(champion1);
        user.addChampion(champion2);
        user.addChampion(champion3);
        user.addChampion(champion4);
        user.addChampion(champion5);

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

        Champion champion1 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        Champion champion2 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        Champion champion3 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        Champion champion4 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(100));
        Champion champion5 = spy(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(100));

        when(champion1.getId()).thenReturn(6L);
        when(champion2.getId()).thenReturn(7L);
        when(champion3.getId()).thenReturn(8L);
        when(champion4.getId()).thenReturn(9L);
        when(champion5.getId()).thenReturn(10L);

        user.addChampion(champion1);
        user.addChampion(champion2);
        user.addChampion(champion3);
        user.addChampion(champion4);
        user.addChampion(champion5);

        return user;
    }
}

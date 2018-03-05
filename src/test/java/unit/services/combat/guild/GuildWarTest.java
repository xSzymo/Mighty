package unit.services.combat.guild;

import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.combat.FightCoordinator;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class GuildWarTest {
    private FightCoordinator fightCoordinator;
    private User user1;
    private User user2;
    private Guild guild;
    private Guild guild1;
    private HashSet<User> users;
    private HashSet<User> opponents;

    @Before
    public void setUp() throws Exception {
        fightCoordinator = new FightCoordinator();
        guild = spy(new Guild());
        guild1 = spy(new Guild());
        user1 = setUpUser1();
        user2 = setUpUser2();

        users = new HashSet<>();
        opponents = new HashSet<>();

        users.add(user1);
        users.add(user2);

        opponents.add(user2);
        opponents.add(user1);
    }

    @Test
    public void fightBetweenGuilds() {
        FightResult fightResult = fightCoordinator.fight(users, opponents);

        assertEquals(6, fightResult.getWinner().getChampions().size());
        assertEquals(6, fightResult.getLooser().getChampions().size());
        assertTrue(fightResult.getRounds().size() > 15 && fightResult.getRounds().size() < 25);
    }

    private User setUpUser1() throws Exception {
        User user = spy(new User("user", "user", "user"));
        doReturn(guild).when(user).getGuild();
        doReturn("abc").when(guild).getName();

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

        user.getChampions().add(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(10, 1, 5, 0, 5, 5), equipment).setLevel(1));

        return user;
    }

    private User setUpUser2() throws Exception {
        User user = spy(new User("admin", "admin", "admin"));
        doReturn(guild1).when(user).getGuild();
        doReturn("abc1").when(guild1).getName();

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

        user.getChampions().add(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));
        user.getChampions().add(new Champion(new Statistic(15, 5, 5, 0, 5, 5), equipment).setLevel(1));

        return user;
    }
}

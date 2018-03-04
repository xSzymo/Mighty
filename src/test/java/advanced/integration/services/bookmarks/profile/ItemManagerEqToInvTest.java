package advanced.integration.services.bookmarks.profile;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.services.security.UsersRetriever;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ItemManagerEqToInvTest extends AuthorizationConfiguration {
    @Autowired
    private ItemManager objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private ItemService itemService;

    private User user;
    private Item item;
    private Champion champion;

    @After
    public void cleanUp() {
        try {
            userService.delete("justForTest");
        } catch (Exception e) {
        }
    }

    @Test
    public void moveEquipmentItemToInventory_ring() throws Exception {
        item = new Item(ItemType.RING);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setRing(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_bracelet() throws Exception {
        item = new Item(ItemType.BRACELET);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setBracelet(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getBracelet());
    }

    @Test
    public void moveEquipmentItemToInventory_necklace() throws Exception {
        item = new Item(ItemType.NECKLACE);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setNecklace(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getNecklace());
    }

    @Test
    public void moveEquipmentItemToInventory_boots() throws Exception {
        item = new Item(ItemType.BOOTS);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setBoots(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getBoots());
    }

    @Test
    public void moveEquipmentItemToInventory_legs() throws Exception {
        item = new Item(ItemType.LEGS);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setLegs(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getLegs());
    }

    @Test
    public void moveEquipmentItemToInventory_gloves() throws Exception {
        item = new Item(ItemType.GLOVES);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setGloves(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getGloves());
    }

    @Test
    public void moveEquipmentItemToInventory_armor() throws Exception {
        item = new Item(ItemType.ARMOR);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setArmor(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getArmor());
    }

    @Test
    public void moveEquipmentItemToInventory_helmet() throws Exception {
        item = new Item(ItemType.HELMET);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setHelmet(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getHelmet());
    }

    @Test
    public void moveEquipmentItemToInventory_offhand() throws Exception {
        item = new Item(ItemType.OFFHAND);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setOffhand(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getOffhand());
    }

    @Test
    public void moveEquipmentItemToInventory_weapon() throws Exception {
        item = new Item(ItemType.WEAPON);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setWeapon(item);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getWeapon());
    }

    @Test
    public void checkSavingProperlyInventoryItemsWithTheirPositions() throws Exception {
        User user = new User("justForTest", "justForTest", "justForTest").addChampion(new Champion());
        user.setAccountEnabled(true);
        user.setAccountNonLocked(true);
        userService.save(user);
        authorize(user.getLogin());

        item = new Item(ItemType.OFFHAND);
        itemService.save(item);
        Item item1 = new Item(ItemType.BOOTS);
        itemService.save(item1);
        Item item2 = new Item(ItemType.HELMET);
        itemService.save(item2);
        Item item3 = new Item(ItemType.NECKLACE);
        itemService.save(item3);
        user = usersRetriever.retrieveUser(token);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setOffhand(item);
        champion.getEquipment().setBoots(item1);
        champion.getEquipment().setHelmet(item2);
        champion.getEquipment().setNecklace(item3);
        userService.save(user);

        objectUnderTest.moveEquipmentItemToInventory(token, item.getId());
        objectUnderTest.moveEquipmentItemToInventory(token, item1.getId());
        objectUnderTest.moveEquipmentItemToInventory(token, item2.getId());

        objectUnderTest.moveEquipmentItemToInventory(token, item3.getId());
        user = userService.find(user);
        assertEquals(4, user.getInventory().getItems().size());
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 0));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 1));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 2));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 3));

        objectUnderTest.moveInventoryToEquipmentItem(token, item1.getId(), champion.getId());
        user = userService.find(user);
        assertEquals(3, user.getInventory().getItems().size());
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 0));
        assertTrue(user.getInventory().getItems().stream().noneMatch(x -> x.getPosition() == 1));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 2));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 3));

        objectUnderTest.moveEquipmentItemToInventory(token, item1.getId());
        user = userService.find(user);
        assertEquals(4, user.getInventory().getItems().size());
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 0));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 1));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 2));
        assertTrue(user.getInventory().getItems().stream().anyMatch(x -> x.getPosition() == 3));

        user = usersRetriever.retrieveUser(token);
        assertNotNull(user.getInventory().getItems().stream().filter(x -> x.getItem().getId().equals(item.getId())).findFirst().get());
        assertEquals(null, user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getOffhand());
    }
}
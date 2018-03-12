package advanced.integration.services.bookmarks.profile;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.services.security.UsersRetriever;
import org.junit.After;
import org.junit.Before;
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
    private static int last_max_items_in_inventory;

    @Before
    public void setUp() {
        last_max_items_in_inventory = SystemVariablesManager.MAX_ITEMS_IN_INVENTORY;
        SystemVariablesManager.MAX_ITEMS_IN_INVENTORY = 30;
    }

    @After
    public void cleanUp() {
        try {
            SystemVariablesManager.MAX_ITEMS_IN_INVENTORY = last_max_items_in_inventory;
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
}
package advanced.integration.services.bookmarks.guild;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.services.bookmarks.guild.GuildManager;
import game.mightywarriors.services.bookmarks.guild.GuildMasterService;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.CreateGuildInformer;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import game.mightywarriors.web.json.objects.bookmarks.GuildMasterInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class GuildMasterServiceTest extends AuthorizationConfiguration {
    @Autowired
    private GuildMasterService objectUnderTest;
    @Autowired
    private GuildManager guildManager;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private GuildService guildService;
    @Autowired
    private UserService userService;

    private User user;
    private GuildInformer informer;

    @Before
    public void setUp() throws Exception {
        user = usersRetriever.retrieveUser(token);

        informer = new GuildInformer();
        informer.userName = "user1";
        informer.guildName = "name of guild";
    }

    @After
    public void cleanUp() {
        guildService.delete("name of guild");
    }

    @Test
    public void addNewGuildMaster() throws Exception {
        createGuild(true);

        objectUnderTest.addNewGuildMaster(token, new GuildMasterInformer(informer.userName, informer.userId));

        Guild guild = usersRetriever.retrieveUser(token).getGuild();
        Optional<User> newMaster = guild.getUsers().stream().filter(x -> x.getLogin().equals("user1")).findFirst();
        User oldMaster = userService.find(this.user);

        assertTrue(newMaster.isPresent());
        assertEquals("owner", newMaster.get().getRole().getRole());
        assertEquals(1, newMaster.get().getChats().size());
        assertEquals(newMaster.get().getLogin(), newMaster.get().getChats().iterator().next().getAdmins().iterator().next().getLogin());
        assertEquals(ChatRole.OWNER, newMaster.get().getChats().iterator().next().getAdmins().iterator().next().getChatRole());

        assertEquals("member", oldMaster.getRole().getRole());
        assertEquals(1, oldMaster.getChats().size());
        assertEquals(1, oldMaster.getChats().iterator().next().getAdmins().size());
        assertNotEquals(oldMaster.getLogin(), oldMaster.getChats().iterator().next().getAdmins().iterator().next().getLogin());
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_no_guild() throws Exception {
        objectUnderTest.addNewGuildMaster(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_as_not_owner() throws Exception {
        createGuild(true);
        user.setGuild(null);
        userService.save(user);

        objectUnderTest.addNewGuildMaster(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_with_no_member() throws Exception {
        createGuild(true);
        informer.userName = "bla";

        objectUnderTest.addNewGuildMaster(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_with_no_member_1() throws Exception {
        createGuild(false);

        objectUnderTest.addNewGuildMaster(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test
    public void removeMember() throws Exception {
        createGuild(true);

        objectUnderTest.removeMember(token, new GuildMasterInformer(informer.userName, informer.userId));

        User user = userService.find("user1");
        assertNull(user.getGuild());
        assertEquals("user", user.getRole().getRole());
        assertEquals(0, user.getChats().size());
    }

    @Test(expected = Exception.class)
    public void removeMember_who_is_master() throws Exception {
        createGuild(true);
        informer.userName = user.getLogin();

        objectUnderTest.removeMember(token, new GuildMasterInformer(informer.userName, informer.userId));

        User user = userService.find("user1");
        assertNull(user.getGuild());
        assertEquals("user", user.getRole().getRole());
    }

    @Test(expected = Exception.class)
    public void removeMember_no_guild() throws Exception {
        objectUnderTest.removeMember(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test(expected = Exception.class)
    public void removeMember_as_not_owner() throws Exception {
        createGuild(true);
        user.setGuild(null);
        userService.save(user);

        objectUnderTest.removeMember(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test(expected = Exception.class)
    public void removeMember_with_no_member() throws Exception {
        createGuild(true);
        informer.userName = "bla";

        objectUnderTest.removeMember(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    @Test(expected = Exception.class)
    public void removeMember_with_no_member_1() throws Exception {
        createGuild(false);

        objectUnderTest.removeMember(token, new GuildMasterInformer(informer.userName, informer.userId));
    }

    private void createGuild(boolean addUser) throws Exception {
        guildManager.createGuild(token, new CreateGuildInformer(informer.guildName, informer.minimumLevel));

        user = usersRetriever.retrieveUser(token);
        Guild guild = user.getGuild();
        assertNotNull(guild);
        assertEquals(informer.guildName, guild.getName());
        assertEquals(informer.minimumLevel, guild.getMinimumLevel());
        assertEquals("owner", user.getRole().getRole());

        if (addUser) {
            User user = userService.find("user1");
            user.setGuild(guild);
            user.getChats().add(this.user.getGuild().getChat());
            userService.save(user);
        }
    }
}
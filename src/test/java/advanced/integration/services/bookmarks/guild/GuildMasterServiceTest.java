package advanced.integration.services.bookmarks.guild;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.guild.GuildManager;
import game.mightywarriors.services.bookmarks.guild.GuildMasterService;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
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

        objectUnderTest.addNewGuildMaster(token, informer);

        Guild guild = usersRetriever.retrieveUser(token).getGuild();
        Optional<User> user = guild.getUsers().stream().filter(x -> x.getLogin().equals("user1")).findFirst();
        assertTrue(user.isPresent());
        assertEquals("owner", user.get().getUserRole().getRole());
        assertEquals("member", userService.find(this.user).getUserRole().getRole());
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_no_guild() throws Exception {
        objectUnderTest.addNewGuildMaster(token, informer);
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_as_not_owner() throws Exception {
        createGuild(true);
        user.setGuild(null);
        userService.save(user);

        objectUnderTest.addNewGuildMaster(token, informer);
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_with_no_member() throws Exception {
        createGuild(true);
        informer.userName = "bla";

        objectUnderTest.addNewGuildMaster(token, informer);
    }

    @Test(expected = Exception.class)
    public void addNewGuildMaster_with_no_member_1() throws Exception {
        createGuild(false);

        objectUnderTest.addNewGuildMaster(token, informer);
    }

    @Test
    public void removeMember() throws Exception {
        createGuild(true);

        objectUnderTest.removeMember(token, informer);

        User user = userService.find("user1");
        assertNull(user.getGuild());
        assertEquals("user", user.getUserRole().getRole());
    }

    @Test(expected = Exception.class)
    public void removeMember_who_is_master() throws Exception {
        createGuild(true);
        informer.userName = user.getLogin();

        objectUnderTest.removeMember(token, informer);

        User user = userService.find("user1");
        assertNull(user.getGuild());
        assertEquals("user", user.getUserRole().getRole());
    }

    @Test(expected = Exception.class)
    public void removeMember_no_guild() throws Exception {
        objectUnderTest.removeMember(token, informer);
    }

    @Test(expected = Exception.class)
    public void removeMember_as_not_owner() throws Exception {
        createGuild(true);
        user.setGuild(null);
        userService.save(user);

        objectUnderTest.removeMember(token, informer);
    }

    @Test(expected = Exception.class)
    public void removeMember_with_no_member() throws Exception {
        createGuild(true);
        informer.userName = "bla";

        objectUnderTest.removeMember(token, informer);
    }

    @Test(expected = Exception.class)
    public void removeMember_with_no_member_1() throws Exception {
        createGuild(false);

        objectUnderTest.removeMember(token, informer);
    }

    private void createGuild(boolean addUser) throws Exception {
        guildManager.createGuild(token, informer);

        user = usersRetriever.retrieveUser(token);
        Guild guild = user.getGuild();
        assertNotNull(guild);
        assertEquals(informer.guildName, guild.getName());
        assertEquals(informer.minimumLevel, guild.getMinimumLevel());
        assertEquals("owner", user.getUserRole().getRole());

        if (addUser) {
            User user = userService.find("user1");
            user.setGuild(guild);
            userService.save(user);
        }
    }
}
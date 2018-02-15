package advanced.integration.services.bookmarks.guild;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.guild.GuildManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class GuildManagerTest extends AuthorizationConfiguration {
    @Autowired
    private GuildManager objectUnderTest;
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
        informer.guildName = "name of guild";
    }

    @After
    public void cleanUp() {
        guildService.delete("name of guild");
    }

    @Test
    public void createGuild() throws Exception {

        objectUnderTest.createGuild(token, informer);

        user = usersRetriever.retrieveUser(token);
        Guild guild = user.getGuild();
        assertNotNull(guild);
        assertEquals(informer.guildName, guild.getName());
        assertEquals(informer.minimumLevel, guild.getMinimumLevel());
        assertEquals("owner", user.getUserRole().getRole());
        assertEquals(1, user.getChats().size());
        assertEquals(guild.getChat(), user.getChats().stream().findFirst().get());
        assertEquals(guild.getChat().getAdmins().iterator().next().getLogin(), user.getLogin());
    }

    @Test(expected = Exception.class)
    public void createGuild_user_have_already_guild() throws Exception {
        createGuild();

        objectUnderTest.createGuild(token, informer);
    }

    @Test(expected = Exception.class)
    public void createGuild_guild_name_is_null() throws Exception {
        guildService.save(new Guild("abc", 1));
        informer.guildName = "abc";

        objectUnderTest.createGuild(token, informer);
    }

    @Test(expected = Exception.class)
    public void createGuild_guild_name_already_exist() throws Exception {
        informer.guildName = null;

        objectUnderTest.createGuild(token, informer);
    }

    @Test
    public void deleteGuild() throws Exception {
        createGuild();

        objectUnderTest.deleteGuild(token);

        user = usersRetriever.retrieveUser(token);
        assertNull(user.getGuild());
        assertEquals("user", user.getUserRole().getRole());
        assertEquals(0, user.getChats().size());
    }

    @Test(expected = Exception.class)
    public void deleteGuild_with_no_guild() throws Exception {
        objectUnderTest.deleteGuild(token);
    }

    @Test(expected = Exception.class)
    public void deleteGuild_as_not_owner() throws Exception {
        createGuild();
        user.setGuild(null);
        userService.save(user);

        objectUnderTest.deleteGuild(token);
    }

    @Test
    public void leaveGuild() throws Exception {
        createGuild(true);
        User user = userService.find("user1");
        authorize(user.getLogin());
        assertNotNull(user.getGuild());
        assertEquals(1, user.getChats().size());

        objectUnderTest.leaveGuild(token);

        user = userService.find("user1");
        assertNull(user.getGuild());
        assertEquals(0, user.getChats().size());
    }

    @Test(expected = Exception.class)
    public void leaveGuild_as_owner() throws Exception {
        createGuild();

        objectUnderTest.leaveGuild(token);
    }

    private void createGuild(boolean addUser) throws Exception {
        objectUnderTest.createGuild(token, informer);

        user = usersRetriever.retrieveUser(token);
        Guild guild = user.getGuild();
        assertNotNull(guild);
        assertEquals(informer.guildName, guild.getName());
        assertEquals(informer.minimumLevel, guild.getMinimumLevel());
        assertEquals("owner", user.getUserRole().getRole());

        if (addUser) {
            User user = userService.find("user1");
            user.setGuild(guild);
            user.getChats().add(this.user.getGuild().getChat());
            userService.save(user);
        }
    }
}
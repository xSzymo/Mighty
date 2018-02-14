package advanced.integration.services.bookmarks.guild;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserRoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.guild.GuildManager;
import game.mightywarriors.services.bookmarks.guild.GuildMasterService;
import game.mightywarriors.services.bookmarks.guild.GuildRequestManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class GuildRequestManagerTest extends AuthorizationConfiguration {
    @Autowired
    private GuildRequestManager objectUnderTest;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private GuildManager guildManager;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private GuildService guildService;
    @Autowired
    private UserService userService;

    private User user;
    private User user2;
    private GuildInformer informer;

    @Before
    public void setUp() throws Exception {
        user = usersRetriever.retrieveUser(token);
        user2 = userService.find("user1");

        informer = new GuildInformer();
        informer.userName = "user1";
        informer.guildName = "name of guild";
    }

    @After
    public void cleanUp() {
        guildService.delete("name of guild");
    }

    @Test
    public void sendRequest() throws Exception {
        createGuild();
        authorize(user2.getLogin());

        objectUnderTest.sendRequest(token, informer);

        Guild guild = userService.find(user).getGuild();
        Optional<Request> request = guild.getInvites().stream().filter(x -> x.getUser().getLogin().equals(user2.getLogin())).findFirst();
        assertTrue(request.isPresent());
        assertEquals(user2.getLogin(), request.get().getUser().getLogin());
    }

    @Test(expected = Exception.class)
    public void sendRequest_user_already_sent_request() throws Exception {
        createGuild();
        authorize(user2.getLogin());

        objectUnderTest.sendRequest(token, informer);
        objectUnderTest.sendRequest(token, informer);
    }


    @Test(expected = Exception.class)
    public void sendRequest_user_already_is_in_guild() throws Exception {
        createGuild();
        user2.setGuild(user.getGuild());
        userService.save(user2);
        authorize(user2.getLogin());

        objectUnderTest.sendRequest(token, informer);
    }

    @Test
    public void removeMember() throws Exception {
        sendRequest();
        authorize(user.getLogin());
        informer.userName = user2.getLogin();

        objectUnderTest.deleteRequest(token, informer);

        Guild guild = userService.find(user).getGuild();
        Optional<Request> request = guild.getInvites().stream().filter(x -> x.getUser().getLogin().equals(user2.getLogin())).findFirst();
        assertFalse(request.isPresent());
    }

    @Test(expected = Exception.class)
    public void removeMember_user_is_not_owner() throws Exception {
        sendRequest();
        user.setUserRole(userRoleService.find("user"));
        userService.save(user);
        authorize(user.getLogin());
        informer.userName = user2.getLogin();

        objectUnderTest.deleteRequest(token, informer);
    }

    @Test
    public void acceptRequest() throws Exception {
        sendRequest();
        authorize(user.getLogin());
        informer.userName = user2.getLogin();

        objectUnderTest.acceptRequest(token, informer);

        Guild guild = userService.find(user).getGuild();
        Optional<Request> request = guild.getInvites().stream().filter(x -> x.getUser().getLogin().equals(user2.getLogin())).findFirst();
        Optional<User> user = guild.getUsers().stream().filter(x -> x.getLogin().equals(user2.getLogin())).findFirst();
        assertFalse(request.isPresent());
        assertTrue(user.isPresent());
    }

    @Test(expected = Exception.class)
    public void acceptRequest_user_is_not_owner() throws Exception {
        sendRequest();
        informer.userName = user2.getLogin();
        user.setUserRole(userRoleService.find("user"));
        userService.save(user);
        authorize(user.getLogin());

        objectUnderTest.acceptRequest(token, informer);

        Guild guild = userService.find(user).getGuild();
        Optional<Request> request = guild.getInvites().stream().filter(x -> x.getUser().getLogin().equals(user2.getLogin())).findFirst();
        Optional<User> user = guild.getUsers().stream().filter(x -> x.getLogin().equals(user2.getLogin())).findFirst();
        assertFalse(request.isPresent());
        assertTrue(user.isPresent());
    }

    private void createGuild() throws Exception {
        guildManager.createGuild(token, informer);

        user = usersRetriever.retrieveUser(token);
        Guild guild = user.getGuild();
        assertNotNull(guild);
        assertEquals(informer.guildName, guild.getName());
        assertEquals(informer.minimumLevel, guild.getMinimumLevel());
        assertEquals("owner", user.getUserRole().getRole());
    }
}
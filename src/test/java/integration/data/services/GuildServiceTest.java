package integration.data.services;

import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.RequestService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import game.mightywarriors.data.tables.User;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GuildServiceTest extends IntegrationTestsConfig {
    @Autowired
    private GuildService objectUnderTest;
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;

    private LinkedList<Guild> guilds;

    @Before
    public void beforeEachTest() {
        guilds = new LinkedList<>();
        guilds.add(new Guild("admin"));
        guilds.add(new Guild("user"));
        guilds.add(new Guild("other"));
    }

    @After
    public void afterEachTest() {
        guilds.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(guilds.getFirst());

        assertNotNull(objectUnderTest.find(guilds.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(guilds);

        assertNotNull(objectUnderTest.find(guilds.get(0)));
        assertNotNull(objectUnderTest.find(guilds.get(1)));
        assertNotNull(objectUnderTest.find(guilds.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(guilds.getFirst());

        assertNotNull(objectUnderTest.find(guilds.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(guilds.getFirst());

        assertNotNull(objectUnderTest.find(guilds.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(guilds);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(guilds.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(guilds.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(guilds.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(guilds.getFirst());

        assertNotNull(objectUnderTest.find(guilds.getFirst()));

        objectUnderTest.delete(guilds.getFirst());

        assertNull(objectUnderTest.find(guilds.getFirst()));
        guilds.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(guilds.getFirst());

        assertNotNull(objectUnderTest.find(guilds.getFirst()));

        objectUnderTest.delete(guilds.getFirst().getId());

        assertNull(objectUnderTest.find(guilds.getFirst()));
        guilds.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(guilds);

        assertNotNull(objectUnderTest.find(guilds.get(0)));
        assertNotNull(objectUnderTest.find(guilds.get(1)));
        assertNotNull(objectUnderTest.find(guilds.get(2)));

        objectUnderTest.delete(guilds);

        assertNull(objectUnderTest.find(guilds.get(0)));
        assertNull(objectUnderTest.find(guilds.get(1)));
        assertNull(objectUnderTest.find(guilds.get(2)));
        guilds.clear();
    }

    @Test
    public void deleteWithRequest() {
        Guild guild = new Guild();
        Request request = new Request("test");
        guild.getInvites().add(request);
        objectUnderTest.save(guild);

        assertNotNull(objectUnderTest.find(guild));
        assertNotNull(requestService.find(request));

        objectUnderTest.delete(guild);

        assertNull(objectUnderTest.find(guild));
        assertNull(requestService.find(request));
    }

    @Test
    public void deleteFromUser() {
        User user = new User("guild", "guild", "guild");
        Guild guild = new Guild();
        Request request = new Request("test");
        guild.getInvites().add(request);
        user.setGuild(guild);
        userService.save(user);

        assertNotNull(objectUnderTest.find(guild));
        assertNotNull(requestService.find(request));
        assertNotNull(userService.find(user));

        objectUnderTest.delete(guild);

        assertNull(objectUnderTest.find(guild));
        assertNull(requestService.find(request));
        assertNotNull(userService.find(user));
        assertNull(userService.find(user).getGuild());
    }
}

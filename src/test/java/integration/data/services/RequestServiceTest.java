package integration.data.services;

import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.RequestService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RequestServiceTest extends IntegrationTestsConfig {
    @Autowired
    private RequestService objectUnderTest;
    @Autowired
    private GuildService guildService;

    private HashSet<Request> requests;

    @Before
    public void beforeEachTest() {
        requests = new HashSet<>();
        requests.add(new Request("t"));
        requests.add(new Request("t1"));
        requests.add(new Request("t2"));
    }

    @After
    public void afterEachTest() {
        requests.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(requests.iterator().next());

        assertNotNull(objectUnderTest.find(requests.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(requests);

        Iterator<Request> iterator = requests.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(requests.iterator().next());

        assertNotNull(objectUnderTest.find(requests.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(requests.iterator().next());

        assertNotNull(objectUnderTest.find(requests.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(requests);

        Iterator<Request> iterator = requests.iterator();
        List<Request> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(requests.iterator().next());

        assertNotNull(objectUnderTest.find(requests.iterator().next()));

        objectUnderTest.delete(requests.iterator().next());

        assertNull(objectUnderTest.find(requests.iterator().next()));
        requests.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(requests.iterator().next());

        assertNotNull(objectUnderTest.find(requests.iterator().next()));

        objectUnderTest.delete(requests.iterator().next().getId());

        assertNull(objectUnderTest.find(requests.iterator().next()));
        requests.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(requests);

        Iterator<Request> iterator = requests.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(requests);

        iterator = requests.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        requests.clear();
    }

    @Test
    public void deleteFromGuild() {
        Guild guild = new Guild();
        Request request = requests.iterator().next();
        guild.getInvites().add(request);
        guildService.save(guild);

        assertNotNull(guildService.find(guild));
        assertNotNull(objectUnderTest.find(request));

        objectUnderTest.delete(request);

        assertNull(objectUnderTest.find(request));
        assertNotNull(guildService.find(guild));
        assertEquals(0, guildService.find(guild).getInvites().size());
    }
}

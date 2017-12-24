package integration.data.services;

import game.mightywarriors.data.services.RankingService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Ranking;
import game.mightywarriors.data.tables.User;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class RankingServiceTest extends IntegrationTestsConfig {
    @Autowired
    private RankingService objectUnderTest;
    @Autowired
    private UserService userService;

    private LinkedList<Ranking> rankings;
    private LinkedList<User> users;


    @Before
    public void beforeEachTest() {
        rankings = new LinkedList<>();
        users = new LinkedList<>();
        rankings.add(new Ranking("admin"));
        rankings.add(new Ranking("user"));
        rankings.add(new Ranking("other"));
    }

    @After
    public void afterEachTest() {
        rankings.forEach(objectUnderTest::delete);
        users.forEach(x -> userService.delete(x));
    }

    @Test
    public void save() {
        objectUnderTest.save(rankings.getFirst());

        assertNotNull(objectUnderTest.findOne(rankings.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(rankings);

        assertNotNull(objectUnderTest.findOne(rankings.get(0)));
        assertNotNull(objectUnderTest.findOne(rankings.get(1)));
        assertNotNull(objectUnderTest.findOne(rankings.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(rankings.getFirst());

        assertNotNull(objectUnderTest.findOne(rankings.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(rankings.getFirst());

        assertNotNull(objectUnderTest.findOne(rankings.getFirst().getNickname()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(rankings);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(rankings.get(0).getNickname())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(rankings.get(1).getNickname())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getNickname().equals(rankings.get(2).getNickname())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(rankings.getFirst());

        Ranking one = objectUnderTest.findOne(rankings.getFirst());

        assertNotNull(one);

        objectUnderTest.delete(rankings.getFirst());

        one = objectUnderTest.findOne(rankings.getFirst());
        assertNull(one);
        rankings.clear();
    }

    @Test
    public void delete1() {
        objectUnderTest.save(rankings.getFirst());

        Ranking one = objectUnderTest.findOne(rankings.getFirst());

        assertNotNull(one);

        objectUnderTest.delete(rankings.getFirst().getNickname());

        one = objectUnderTest.findOne(rankings.getFirst());
        assertNull(one);
        rankings.clear();
    }

    @Test
    public void delete2() {
        objectUnderTest.save(rankings);

        assertNotNull(objectUnderTest.findOne(rankings.get(0)));
        assertNotNull(objectUnderTest.findOne(rankings.get(1)));
        assertNotNull(objectUnderTest.findOne(rankings.get(2)));

        objectUnderTest.delete(rankings);

        assertNull(objectUnderTest.findOne(rankings.get(0)));
        assertNull(objectUnderTest.findOne(rankings.get(1)));
        assertNull(objectUnderTest.findOne(rankings.get(2)));
        rankings.clear();
    }

    @Test
    public void saveNewUser() {
        User user = new User("examplo");
        users.add(user);
        userService.save(user);

        user = new User("examplo1");
        users.add(user);
        userService.save(user);

        user = new User("examplo2");
        users.add(user);
        userService.save(user);

        user = new User("examplo3");
        users.add(user);
        userService.save(user);

        user = userService.findOne(user);
        assertNotNull(userService.findOne(user));

        long maxRanking = 0;
        for (Ranking ranking : objectUnderTest.findAll()) {
            if (ranking.getRanking() > maxRanking)
                maxRanking = ranking.getRanking();
        }


        assertEquals(maxRanking, objectUnderTest.findOne(user.getLogin()).getRanking());
    }
}

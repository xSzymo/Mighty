package advanced.integration.services.bookmarks.work;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.Work;
import game.mightywarriors.other.exceptions.BusyChampionException;
import game.mightywarriors.services.bookmarks.utilities.Helper;
import game.mightywarriors.services.bookmarks.work.WorkerManager;
import game.mightywarriors.services.bookmarks.work.WorkerUtility;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class WorkerManagerTest extends AuthorizationConfiguration {
    @Autowired
    private WorkerUtility workerUtility;
    @Autowired
    private WorkService workService;
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private UserService userService;
    @Autowired
    private Helper helper;
    @Autowired
    private UsersRetriever usersRetriever;

    private User user;
    private Informer informer;
    private WorkerManager objectUnderTests;
    private int howManyHours;

    @Before
    public void setUp() throws Exception {
        workerUtility = spy(workerUtility);
        objectUnderTests = new WorkerManager(userService, retriever, workService, helper, workerUtility);

        user = retriever.retrieveUser(token);

        informer = new Informer();
        informer.championId = new long[]{user.getChampions().get(0).getId(), user.getChampions().get(1).getId()};
        informer.hours = 1;
    }

    @After
    public void cleanUp() {
        workService.findOne(user.getLogin()).forEach(workService::delete);
        user.getChampions().forEach(x -> x.setBlockTime(null));
        userService.save(user);
    }

    @Test
    public void setWorkForUser() throws Exception {
        objectUnderTests.setWorkForUser(token, informer);

        user = userService.findOne(user.getId());
        List<Work> works = workService.findOne(user.getLogin());
        long blockTime = new Timestamp(System.currentTimeMillis() + 60 * 60 * 1000).getTime() / 60 / 1000;
        assertEquals(1, works.get(0).getTime());
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[0])).findFirst().get().getBlockDate().getTime() / 60 / 1000);
        assertEquals(blockTime, user.getChampions().stream().filter(x -> x.getId().equals(informer.championId[1])).findFirst().get().getBlockDate().getTime() / 60 / 1000);
        assertEquals(blockTime, works.get(0).getChampion().getBlockDate().getTime() / 60 / 1000);
    }

    @Test(expected = BusyChampionException.class)
    public void setWorkForUser_throw_BusyChampionException() throws Exception {
        objectUnderTests.setWorkForUser(token, informer);
        objectUnderTests.setWorkForUser(token, informer);
    }

    @Test
    public void getPayment() throws Exception {
        getPayment(1, 20);
        getPayment(2, 40);
        getPayment(5, 100);
        getPayment(10, 200);
    }

    private void getPayment(int howManyHours, int expectedGold) throws Exception {
        this.howManyHours = howManyHours;
        when(workerUtility.getHours(howManyHours)).thenReturn(1000L);

        objectUnderTests.setWorkForUser(token, informer);
        Thread.sleep(2000);
        objectUnderTests.getPayment(token);

        user = userService.findOne(user.getId());
        user.getChampions().forEach(x -> assertNull(x.getBlockDate()));
        assertEquals(0, workService.findOne(user.getLogin()).size());
        assertEquals(expectedGold, getGold(user.getChampions().get(0)).add(getGold(user.getChampions().get(1))).intValue());
    }

    @Test
    public void cancelWork_all() throws Exception {
        setWorkForUser();

        objectUnderTests.cancelWork(token, informer);

        user = userService.findOne(user.getId());
        assertEquals(0, workService.findOne(user.getLogin()).size());
        user.getChampions().forEach(x -> assertNull(x.getBlockDate()));
    }

    @Test
    public void cancelWork_one() throws Exception {
        setWorkForUser();
        informer.championId = new long[]{user.getChampions().get(0).getId()};

        objectUnderTests.cancelWork(token, informer);

        user = userService.findOne(user.getId());
        List<Work> works = workService.findOne(user.getLogin());
        assertEquals(1, workService.findOne(user.getLogin()).size());
        assertNull(user.getChampions().stream().filter(x -> x.getId().equals(user.getChampions().get(0).getId())).findFirst().get().getBlockDate());
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(user.getChampions().get(1).getId())).findFirst().get().getBlockDate());
        assertEquals(user.getChampions().get(1).getId(), works.get(0).getChampion().getId());
    }

    private BigDecimal getGold(Champion champion) {
        return new BigDecimal(howManyHours * SystemVariablesManager.GOLD_FROM_WORK * champion.getLevel());
    }
}
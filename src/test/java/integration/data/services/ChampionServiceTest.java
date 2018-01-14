package integration.data.services;

import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ChampionServiceTest extends IntegrationTestsConfig {
    @Autowired
    private ChampionService objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private WorkService workService;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private MissionService missionService;

    private HashSet<Champion> champions;
    private LinkedList<Statistic> statistics;
    private LinkedList<Image> images;
    private User user;
    private Equipment equipment;
    private Work work;
    private MissionFight missionFight;

    @Before
    public void beforeEachTest() {
        champions = new HashSet<>();
        statistics = new LinkedList<>();
        images = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://www.url" + i + ".com"));
            champions.add(new Champion(statistics.get(i), images.get(i)));
        }
    }

    @After
    public void afterEachTest() {
        if (user != null)
            userService.delete(user);
        if (equipment != null)
            equipmentService.delete(equipment);
        if (missionFight != null)
            missionFightService.delete(missionFight);
        champions.forEach(objectUnderTest::delete);
    }

    @Test
    public void save() {
        objectUnderTest.save(champions.iterator().next());

        assertNotNull(objectUnderTest.findOne(champions.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(champions);

        Iterator<Champion> iterator = champions.iterator();
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(champions.iterator().next());

        assertNotNull(objectUnderTest.findOne(champions.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(champions.iterator().next());

        assertNotNull(objectUnderTest.findOne(champions.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(champions);

        Iterator<Champion> iterator = champions.iterator();
        List<Champion> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(champions.iterator().next());

        Champion one = objectUnderTest.findOne(champions.iterator().next());

        assertNotNull(one);

        objectUnderTest.delete(champions.iterator().next());
        one = objectUnderTest.findOne(champions.iterator().next());

        assertNull(one);
    }

    @Test
    public void delete1() {
        objectUnderTest.save(champions.iterator().next());

        Champion one = objectUnderTest.findOne(champions.iterator().next());

        assertNotNull(one);

        objectUnderTest.delete(one);

        assertNull(objectUnderTest.findOne(one));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(champions);

        Iterator<Champion> iterator = champions.iterator();
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));
        assertNotNull(objectUnderTest.findOne(iterator.next()));

        objectUnderTest.delete(champions);

        iterator = champions.iterator();
        assertNull(objectUnderTest.findOne(iterator.next()));
        assertNull(objectUnderTest.findOne(iterator.next()));
        assertNull(objectUnderTest.findOne(iterator.next()));
    }

    @Test
    public void deleteFromEquipment() {
        Champion champion = champions.iterator().next();
        equipment = new Equipment();
        champion.setEquipment(equipment);

        objectUnderTest.save(champion);

        assertNotNull(equipmentService.findOne(equipment));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNull(equipmentService.findOne(equipment));
        assertNull(objectUnderTest.findOne(champion));
    }

    @Test
    public void deleteFromUser() {
        Champion champion = champions.iterator().next();
        user = new User("simple login" + System.currentTimeMillis());
        user.addChampion(champion);

        userService.save(user);

        assertNotNull(userService.findOne(user));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNotNull(userService.findOne(user));
        assertNull(objectUnderTest.findOne(champion));
    }

    @Test
    public void deleteFromWork() {
        Champion champion = champions.iterator().next();
        work = new Work();
        work.setChampion(champion);

        objectUnderTest.save(champion);
        workService.save(work);

        assertNotNull(workService.findOne(work));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNull(objectUnderTest.findOne(champion));
        assertNull(workService.findOne(work));
    }

    @Test
    public void deleteFromMissionFight() {
        Champion champion = champions.iterator().next();
        Mission mission = new Mission();
        missionFight = new MissionFight();
        missionFight.setMission(mission);
        missionFight.setBlockTime(new Timestamp(System.currentTimeMillis()));
        missionFight.getChampion().add(champion);

        missionService.save(mission);
        objectUnderTest.save(champion);
        missionFightService.save(missionFight);

        assertNotNull(missionFightService.findOne(missionFight));
        assertNotNull(missionFightService.findOne(missionFight).getChampion().toArray()[0]);
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNull(objectUnderTest.findOne(champion));
        assertNotNull(missionFightService.findOne(missionFight));
        assertEquals(0, missionFightService.findOne(missionFight).getChampion().toArray().length);
    }

    @Test
    public void deleteFromStatisticAndImage() {
        Champion champion = champions.iterator().next();
        Statistic statistic = new Statistic();
        Image image = new Image();
        champion.setStatistic(statistic);
        champion.setImage(image);

        objectUnderTest.save(champion);

        assertNotNull(statisticService.findOne(statistic));
        assertNotNull(imageService.findOne(image));
        assertNotNull(objectUnderTest.findOne(champion));

        objectUnderTest.delete(champion);

        assertNull(statisticService.findOne(statistic));
        assertNull(imageService.findOne(image));
        assertNull(objectUnderTest.findOne(champion));
    }
}
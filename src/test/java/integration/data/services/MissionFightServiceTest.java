package integration.data.services;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import integration.config.IntegrationTestsConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class MissionFightServiceTest extends IntegrationTestsConfig {
    @Autowired
    private MissionFightService objectUnderTest;
    @Autowired
    private ChampionService championService;
    @Autowired
    private MissionService missionService;

    private HashSet<MissionFight> missionFights;
    private HashSet<Champion> champions;
    private Champion champion;
    private Mission mission;

    @Before
    public void beforeEachTest() {
        missionFights = new HashSet<>();
        champion = new Champion();
        mission = new Mission();

        setUp();
    }

    @After
    public void afterEachTest() {
        objectUnderTest.delete(missionFights);
        championService.delete(champion);
        championService.delete(champions);
        missionService.delete(mission);
    }

    @Test
    public void save() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(missionFights);

        Iterator<MissionFight> iterator = missionFights.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(missionFights);

        Iterator<MissionFight> iterator = missionFights.iterator();
        List<MissionFight> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(list.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));

        objectUnderTest.delete(missionFights.iterator().next());

        assertNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(missionFights.iterator().next());

        assertNotNull(objectUnderTest.find(missionFights.iterator().next()));

        objectUnderTest.delete(missionFights.iterator().next().getId());

        assertNull(objectUnderTest.find(missionFights.iterator().next()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(missionFights);

        Iterator<MissionFight> iterator = missionFights.iterator();
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));
        assertNotNull(objectUnderTest.find(iterator.next()));

        objectUnderTest.delete(missionFights);

        iterator = missionFights.iterator();
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
        assertNull(objectUnderTest.find(iterator.next()));
    }

    @Test
    public void findOneByChampionId() {
        MissionFight missionFight = new MissionFight();
        HashSet<Champion> champions = new HashSet<>();
        champions.add(champion);
        mission = new Mission();
        championService.save(champion);
        missionService.save(mission);

        missionFight.setChampions(champions);
        missionFight.setMission(mission);
        missionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + 4000));
        missionFights.add(missionFight);

        objectUnderTest.save(missionFights);

        MissionFight secondMissionFight = new MissionFight();
        secondMissionFight.setChampions(champions);
        secondMissionFight.setMission(mission);
        secondMissionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + 5000));
        missionFights.add(secondMissionFight);

        objectUnderTest.save(missionFights);

        MissionFight found = objectUnderTest.findLatestByChampionId(champion);

        assertTrue(secondMissionFight.getBlockDate().after(missionFight.getBlockDate()));
        assertEquals(secondMissionFight.getId(), found.getId());
    }

    @Test
    public void deleteFromChampionAndMission() {
        MissionFight missionFight = new MissionFight();
        champions = new HashSet<>();
        champions.add(champion);
        mission = new Mission();

        championService.save(champion);
        missionService.save(mission);

        missionFight.setChampions(champions);
        missionFight.setMission(mission);
        missionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + (1 * 1000)));
        missionFights.add(missionFight);

        objectUnderTest.save(missionFights);

        assertNotNull(objectUnderTest.find(missionFight));
        assertNotNull(championService.find(champion));
        assertNotNull(missionService.find(mission));

        objectUnderTest.delete(missionFight);

        champion = championService.find(champion);
        mission = missionService.find(mission);
        assertNull(objectUnderTest.find(missionFight));
        assertNotNull(championService.find(champion));
        assertNotNull(missionService.find(mission));
    }

    private void setUp() {
        MissionFight missionFight;

        for (int i = 0; i < 3; i++) {
            missionFight = new MissionFight();
            champions = new HashSet<>();
            champions.add(champion);
            mission = new Mission();

            championService.save(champion);
            missionService.save(mission);

            missionFight.setChampions(champions);
            missionFight.setMission(mission);
            missionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + (i * 1000)));

            missionFights.add(missionFight);
        }
    }
}
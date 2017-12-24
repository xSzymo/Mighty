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
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MissionFightServiceTest extends IntegrationTestsConfig {
    @Autowired
    private MissionFightService objectUnderTest;
    @Autowired
    private ChampionService championService;
    @Autowired
    private MissionService missionService;

    private LinkedList<MissionFight> missionFights;
    private LinkedList<Champion> champions;
    private Champion champion;
    private Mission mission;

    @Before
    public void beforeEachTest() {
        missionFights = new LinkedList<>();
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
        objectUnderTest.save(missionFights.getFirst());

        assertNotNull(objectUnderTest.findOne(missionFights.getFirst()));
    }

    @Test
    public void saveCollection() {
        objectUnderTest.save(missionFights);

        assertNotNull(objectUnderTest.findOne(missionFights.get(0)));
        assertNotNull(objectUnderTest.findOne(missionFights.get(1)));
        assertNotNull(objectUnderTest.findOne(missionFights.get(2)));
    }

    @Test
    public void findOne() {
        objectUnderTest.save(missionFights.getFirst());

        assertNotNull(objectUnderTest.findOne(missionFights.getFirst()));
    }

    @Test
    public void findOne1() {
        objectUnderTest.save(missionFights.getFirst());

        assertNotNull(objectUnderTest.findOne(missionFights.getFirst().getId()));
    }

    @Test
    public void findAll() {
        objectUnderTest.save(missionFights);

        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(missionFights.get(0).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(missionFights.get(1).getId())).findFirst().get());
        assertNotNull(objectUnderTest.findAll().stream().filter(x -> x.getId().equals(missionFights.get(2).getId())).findFirst().get());
    }

    @Test
    public void delete() {
        objectUnderTest.save(missionFights.getFirst());

        assertNotNull(objectUnderTest.findOne(missionFights.getFirst()));

        objectUnderTest.delete(missionFights.getFirst());

        assertNull(objectUnderTest.findOne(missionFights.getFirst()));
    }

    @Test
    public void delete1() {
        objectUnderTest.save(missionFights.getFirst());

        assertNotNull(objectUnderTest.findOne(missionFights.getFirst()));

        objectUnderTest.delete(missionFights.getFirst().getId());

        assertNull(objectUnderTest.findOne(missionFights.getFirst()));
    }

    @Test
    public void delete2() {
        objectUnderTest.save(missionFights);

        assertNotNull(objectUnderTest.findOne(missionFights.get(0)));
        assertNotNull(objectUnderTest.findOne(missionFights.get(1)));
        assertNotNull(objectUnderTest.findOne(missionFights.get(2)));

        objectUnderTest.delete(missionFights);

        assertNull(objectUnderTest.findOne(missionFights.get(0)));
        assertNull(objectUnderTest.findOne(missionFights.get(1)));
        assertNull(objectUnderTest.findOne(missionFights.get(2)));
    }

    @Test
    public void findOneByChampionId() {
        MissionFight missionFight = new MissionFight();
        LinkedList<Champion> champions = new LinkedList<>();
        champions.add(champion);
        mission = new Mission();
        championService.save(champion);
        missionService.save(mission);

        missionFight.setChampion(champions);
        missionFight.setMission(mission);
        missionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + 4000));
        missionFights.add(missionFight);

        objectUnderTest.save(missionFights);

        MissionFight secondMissionFight = new MissionFight();
        secondMissionFight.setChampion(champions);
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
        champions = new LinkedList<>();
        champions.add(champion);
        mission = new Mission();

        championService.save(champion);
        missionService.save(mission);

        missionFight.setChampion(champions);
        missionFight.setMission(mission);
        missionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + (1 * 1000)));
        missionFights.add(missionFight);

        objectUnderTest.save(missionFights);

        assertNotNull(objectUnderTest.findOne(missionFight));
        assertNotNull(championService.findOne(champion));
        assertNotNull(missionService.findOne(mission));

        objectUnderTest.delete(missionFight);

        assertNull(objectUnderTest.findOne(missionFight));
        assertNotNull(championService.findOne(champion));
        assertNotNull(missionService.findOne(mission));
    }

    private void setUp() {
        MissionFight missionFight;

        for (int i = 0; i < 3; i++) {
            missionFight = new MissionFight();
            champions = new LinkedList<>();
            champions.add(champion);
            mission = new Mission();

            championService.save(champion);
            missionService.save(mission);

            missionFight.setChampion(champions);
            missionFight.setMission(mission);
            missionFight.setBlockTime(new Timestamp(System.currentTimeMillis() + (i * 1000)));

            missionFights.add(missionFight);
        }
    }
}
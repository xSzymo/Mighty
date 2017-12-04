package unit.game.mightywarriors.services.combat;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Monster;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.other.casters.FighterModelCaster;
import game.mightywarriors.services.combat.RoundFightPerformer;
import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.web.json.objects.fights.RoundProcess;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;


public class RoundFightPerformerTest {
    private RoundFightPerformer objectUnderTest;
    private FighterModelCaster caster;
    private RoundProcess round;
    private LinkedList<Fighter> userChamps;
    private LinkedList<Fighter> opponents;

    @Before
    public void setUp() {
        objectUnderTest = new RoundFightPerformer();
        userChamps = new LinkedList<>();
        opponents = new LinkedList<>();
        caster = new FighterModelCaster();

        userChamps.add(caster.castChampionToChampionModel(new Champion(new Statistic(5, 5, 5, 0, 5, 5)).setLevel(4)));
        opponents.add(caster.castChampionToChampionModel(new Monster(new Statistic(5, 5, 5, 0, 5, 5)).setLevel(3)));

        round = new RoundProcess();
        round.setUserChampions(userChamps);
        round.setOpponentChampions(opponents);

        round.setUserChampions(userChamps);
        round.setOpponentChampions(opponents);
    }

    @Test
    public void performSingleRound_user_turn_true() {
        round = objectUnderTest.performSingleRound(round, 0, 0, true);

        assertEquals(6.0, round.getUserChampions().get(0).getHp());
        assertEquals(2.66, round.getUserChampions().get(0).getDmg());

        assertEquals(1.84, round.getOpponentChampions().get(0).getHp());
        assertEquals(0.0, round.getOpponentChampions().get(0).getDmg());
    }

    @Test
    public void performSingleRound_user_turn_false() {
        round = objectUnderTest.performSingleRound(round, 0, 0, false);

        assertEquals(4.5, round.getUserChampions().get(0).getHp());
        assertEquals(0.0, round.getUserChampions().get(0).getDmg());

        assertEquals(4.5, round.getOpponentChampions().get(0).getHp());
        assertEquals(1.5, round.getOpponentChampions().get(0).getDmg());
    }
}
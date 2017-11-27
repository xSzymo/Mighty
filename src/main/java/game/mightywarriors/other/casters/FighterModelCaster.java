package game.mightywarriors.other.casters;

import game.mightywarriors.services.combat.PointsInFightCounter;
import game.mightywarriors.web.json.objects.fights.Fighter;
import game.mightywarriors.other.enums.StatisticType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FighterModelCaster {
    private final PointsInFightCounter pointsInFightCounter = new PointsInFightCounter();

    public Fighter castChampionToChampionModel(game.mightywarriors.data.interfaces.Fighter fighter) {
        Fighter championModel = new Fighter();
        championModel.build()
                .setHp(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.VITALITY))
                .setArmor(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.ARMOR))
                .setMagicResist(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.MAGIC_RESIST))
                .setStrength(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.STRENGTH))
                .setIntelligence(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.INTELLIGENCE))
                .setCriticChance(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.CRITIC_CHANCE))
                .setLevel(fighter.getLevel());

        if (fighter.getId() != null)
            championModel.setId(fighter.getId());

        return championModel;
    }

    public LinkedList<Fighter> castChampionToChampionModel(List<game.mightywarriors.data.interfaces.Fighter> champions) {
        LinkedList<Fighter> championModels1 = new LinkedList<>();
        for (game.mightywarriors.data.interfaces.Fighter fighter : champions)
            championModels1.add(castChampionToChampionModel(fighter));

        return championModels1;
    }
}

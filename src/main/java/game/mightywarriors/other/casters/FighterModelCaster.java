package game.mightywarriors.other.casters;

import game.mightywarriors.data.interfaces.IFighter;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.combat.PointsInFightCounter;
import game.mightywarriors.web.json.objects.fights.Fighter;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FighterModelCaster {
    private final PointsInFightCounter pointsInFightCounter = new PointsInFightCounter();

    public Fighter castChampionToChampionModel(IFighter fighter) {
        Fighter championModel = new Fighter();
        championModel.build()
                .setHp(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.VITALITY))
                .setArmor(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.ARMOR))
                .setMagicResist(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.MAGIC_RESIST))
                .setStrength(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.STRENGTH))
                .setIntelligence(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.INTELLIGENCE))
                .setCriticalChance(pointsInFightCounter.getPointsForSpecificType(fighter, StatisticType.CRITICAL_CHANCE))
                .setLevel(fighter.getLevel());

        if (fighter.getId() != null)
            championModel.setId(fighter.getId());

        return championModel;
    }

    public LinkedList<Fighter> castChampionToChampionModel(List<IFighter> champions) {
        LinkedList<Fighter> championModels1 = new LinkedList<>();
        for (IFighter fighter : champions)
            championModels1.add(castChampionToChampionModel(fighter));

        return championModels1;
    }
}

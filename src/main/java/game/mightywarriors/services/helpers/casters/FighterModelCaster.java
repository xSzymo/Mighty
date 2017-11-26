package game.mightywarriors.services.helpers.casters;

import game.mightywarriors.services.helpers.counters.fight.StatisticCounter;
import game.mightywarriors.json.objects.fights.Fighter;
import game.mightywarriors.other.enums.StatisticType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FighterModelCaster {
    private final StatisticCounter statisticCounter = new StatisticCounter();

    public Fighter castChampionToChampionModel(game.mightywarriors.data.interfaces.Fighter fighter) {
        Fighter championModel = new Fighter();
        championModel.build()
                .setHp(statisticCounter.getPointsForSpecificType(fighter, StatisticType.VITALITY))
                .setArmor(statisticCounter.getPointsForSpecificType(fighter, StatisticType.ARMOR))
                .setMagicResist(statisticCounter.getPointsForSpecificType(fighter, StatisticType.MAGIC_RESIST))
                .setStrength(statisticCounter.getPointsForSpecificType(fighter, StatisticType.STRENGTH))
                .setIntelligence(statisticCounter.getPointsForSpecificType(fighter, StatisticType.INTELLIGENCE))
                .setCriticChance(statisticCounter.getPointsForSpecificType(fighter, StatisticType.CRITIC_CHANCE))
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

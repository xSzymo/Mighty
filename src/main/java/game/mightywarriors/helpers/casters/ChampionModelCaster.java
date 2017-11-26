package game.mightywarriors.helpers.casters;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.helpers.counters.SpecificStatisticInFightsCounter;
import game.mightywarriors.json.objects.fights.ChampionModel;
import game.mightywarriors.other.enums.StatisticType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ChampionModelCaster {
    private final SpecificStatisticInFightsCounter specificStatisticInFightsCounter = new SpecificStatisticInFightsCounter();

    public ChampionModel castChampionToChampionModel(Champion champion) {
        ChampionModel championModel = new ChampionModel();
        championModel.build()
                .setHp(specificStatisticInFightsCounter.countStatisticForSpecificType(champion, StatisticType.VITALITY))
                .setArmor(specificStatisticInFightsCounter.countStatisticForSpecificType(champion, StatisticType.ARMOR))
                .setMagicResist(specificStatisticInFightsCounter.countStatisticForSpecificType(champion, StatisticType.MAGIC_RESIST))
                .setStrength(specificStatisticInFightsCounter.countStatisticForSpecificType(champion, StatisticType.STRENGTH))
                .setIntelligence(specificStatisticInFightsCounter.countStatisticForSpecificType(champion, StatisticType.INTELLIGENCE))
                .setCriticChance(specificStatisticInFightsCounter.countStatisticForSpecificType(champion, StatisticType.CRITIC_CHANCE))
                .setLevel(champion.getLevel());

        if (champion.getId() != null)
            championModel.setId(champion.getId());

        return championModel;
    }

    public LinkedList<ChampionModel> castChampionToChampionModel(List<Champion> champions) {
        LinkedList<ChampionModel> championModels1 = new LinkedList<>();
        for (Champion championModel : champions)
            championModels1.add(castChampionToChampionModel(championModel));

        return championModels1;
    }
}
